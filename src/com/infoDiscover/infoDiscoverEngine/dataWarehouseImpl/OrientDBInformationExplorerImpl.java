package com.infoDiscover.infoDiscoverEngine.dataWarehouseImpl;

import com.infoDiscover.infoDiscoverEngine.dataMart.*;
import com.infoDiscover.infoDiscoverEngine.dataMartImpl.OrientDBFactImpl;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.*;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.common.path.OrientDBAllPaths;
import com.infoDiscover.solution.common.path.OrientDBShortestPath;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.*;
import com.infoDiscover.infoDiscoverEngine.dataMartImpl.OrientDBDimensionImpl;
import com.infoDiscover.infoDiscoverEngine.dataMartImpl.OrientDBRelationImpl;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class OrientDBInformationExplorerImpl implements InformationExplorer {

    private OrientGraph graph;

    public OrientDBInformationExplorerImpl(OrientGraph graph){
        this.graph=graph;
    }

    @Override
    public List<Fact> discoverFacts(ExploreParameters exploreParameters) throws InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException {
        String factType=exploreParameters.getType();
        informationTypeCheck(factType, InformationType.FACT);
        String sql= SQLBuilder.buildQuerySQL(InformationType.FACT, exploreParameters);
        List<Fact> factList=new ArrayList<>();
        String factRealType;
        for (Vertex v : (Iterable<Vertex>) graph.command(
                new OCommandSQL(sql)).execute()) {
            OrientVertex ov=(OrientVertex)v;
            if(factType==null){
                factRealType=ov.getType().getName().replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_FACT,"");
            }else{
                factRealType=factType;
            }
            OrientDBFactImpl targetFact=new OrientDBFactImpl(factRealType);
            targetFact.setFactVertex(ov);
            factList.add(targetFact);
        }
        return factList;
    }

    @Override
    public List<Dimension> discoverDimensions(ExploreParameters exploreParameters) throws InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException {
        String dimensionType=exploreParameters.getType();
        informationTypeCheck(dimensionType,InformationType.DIMENSION);
        String sql= SQLBuilder.buildQuerySQL(InformationType.DIMENSION,exploreParameters);
        List<Dimension> dimensionList=new ArrayList<>();
        String dimensionRealType;
        for (Vertex v : (Iterable<Vertex>) graph.command(
                new OCommandSQL(sql)).execute()) {
            OrientVertex ov=(OrientVertex)v;
            if(dimensionType==null) {
                dimensionRealType = ov.getType().getName().replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION, "");
            }else{
                dimensionRealType=dimensionType;
            }
            OrientDBDimensionImpl targetDimension=new OrientDBDimensionImpl(dimensionRealType);
            targetDimension.setDimensionVertex(ov);
            dimensionList.add(targetDimension);
        }
        return dimensionList;
    }

    @Override
    public List<Relation> discoverRelations(ExploreParameters exploreParameters) throws InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException{
        String relationType=exploreParameters.getType();
        informationTypeCheck(relationType,InformationType.RELATION);
        String sql= SQLBuilder.buildQuerySQL(InformationType.RELATION, exploreParameters);
        List<Relation> relationList=new ArrayList<>();
        String relationRealType;
        for (Edge e : (Iterable<Edge>) graph.command(
                new OCommandSQL(sql)).execute()) {
            OrientEdge oe=(OrientEdge)e;
            if(relationType==null){
                relationRealType=oe.getType().getName().replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_RELATION,"");

            }else{
                relationRealType=relationType;
            }
            OrientDBRelationImpl targetRelation=new OrientDBRelationImpl(relationRealType);
            targetRelation.setRelationEdge(oe);
            relationList.add(targetRelation);
        }
        return relationList;
    }

    @Override
    public List<Relationable> discoverRelationables(String sql) {
        List<Relationable> relationableList=new ArrayList<>();
        for (Vertex v : (Iterable<Vertex>) graph.command(
                new OCommandSQL(sql)).execute()) {
            OrientVertex ov=(OrientVertex)v;
            String ovName=ov.getType().getName();
            if(ovName.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_FACT)){
                String factRealType=ov.getType().getName().replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_FACT,"");
                OrientDBFactImpl targetFact=new OrientDBFactImpl(factRealType);
                targetFact.setFactVertex(ov);
                relationableList.add(targetFact);
            }
            if(ovName.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION)){
                String dimensionRealType = ov.getType().getName().replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION, "");
                OrientDBDimensionImpl targetDimension=new OrientDBDimensionImpl(dimensionRealType);
                targetDimension.setDimensionVertex(ov);
                relationableList.add(targetDimension);
            }
        }
        return relationableList;
    }

    @Override
    public List<Dimension> exploreDimensionsByRelatedFacts(RelationDirection relationDirection, List<String> factIds, ExploreParameters exploreParameters) throws InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException {
        if(factIds==null||factIds.size()==0){
            String exceptionMessage = "Fact ids not exists";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }else{
            for(String currentFactId:factIds){
                OrientVertex ov=graph.getVertex(currentFactId);
                if(ov==null){
                    String exceptionMessage = "Fact with id "+currentFactId+" not exist";
                    throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);

                }else{
                    String typeName=ov.getType().getName();
                    if(!typeName.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_FACT)){
                        String exceptionMessage = "Relationable with id "+currentFactId+" is not a fact";
                        throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
                    }
                }
            }
            String dimensionTypeStr=exploreParameters.getType();
            String dimensionType=null;
            if(dimensionTypeStr!=null){
                dimensionType=InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION+dimensionTypeStr;
                OrientVertexType dovt=this.graph.getVertexType(dimensionType);
                if(dovt==null){
                    String exceptionMessage = "Dimension Type "+dimensionTypeStr+" not exists";
                    throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
                }
            }
            String sql=SQLBuilder.buildExploreDimensionsByRelatedFactSQL(factIds, relationDirection, exploreParameters);
            List<Dimension> dimensionList=new ArrayList<>();
            String dimensionRealType;
            for (Vertex v : (Iterable<Vertex>) graph.command(
                        new OCommandSQL(sql)).execute()) {
                OrientVertex dov=(OrientVertex)v;
                if(dimensionType==null){
                    dimensionRealType=dov.getType().getName().replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION,"");
                }else{
                    dimensionRealType=dimensionType;
                }
                OrientDBDimensionImpl targetDimension=new OrientDBDimensionImpl(dimensionRealType);
                targetDimension.setDimensionVertex(dov);
                dimensionList.add(targetDimension);
            }
            return dimensionList;
        }
    }

    @Override
    public List<Fact> exploreSimilarFactsByRelatedDimensions(RelationDirection relationDirection,String factId,String relationType,ExploreParameters exploreParameters) throws InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {
        if(factId==null){
            String exceptionMessage = "Fact id is required";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }else{
            String factTypeStr=exploreParameters.getType();
            informationTypeCheck(factTypeStr,InformationType.FACT);
            informationTypeCheck(relationType,InformationType.RELATION);
            String factType=null;
            if(factTypeStr!=null){
                factType=InfoDiscoverEngineConstant.CLASSPERFIX_FACT+factTypeStr;
            }
            String sql=SQLBuilder.buildExploreSimilarFactsByRelatedDimensionsSQL(factId, relationType, relationDirection, exploreParameters);
            List<Fact> factList=new ArrayList<>();
            String factRealType;
            for (Vertex v : (Iterable<Vertex>) graph.command(
                    new OCommandSQL(sql)).execute()) {
                OrientVertex fov=(OrientVertex)v;
                if(factType==null){
                    factRealType=fov.getType().getName().replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_FACT,"");
                }else{
                    factRealType=factType;
                }
                OrientDBFactImpl targetFact=new OrientDBFactImpl(factRealType);
                targetFact.setFactVertex(fov);
                factList.add(targetFact);
            }
            return factList;
        }
    }

    @Override
    public List<Relationable> exploreRelatedRelationablesByRelationDepth(RelationDirection relationDirection, String sourceRelationableId, int depth, ExploreParameters exploreParameters) throws InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {
        if(sourceRelationableId==null){
            String exceptionMessage = "Source Relationable id is required";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }else{
            if(depth<1){
                String exceptionMessage = "the minimum depth value is 1";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }
            String sql=SQLBuilder.buildExploreRelatedRelationablesByRelationDepthSQL(relationDirection,sourceRelationableId,depth,exploreParameters);
            List<Relationable> relationableList=new ArrayList<>();
            String vertexType;
            String relationableType;
            for (Vertex v : (Iterable<Vertex>) graph.command(
                    new OCommandSQL(sql)).execute()) {
                OrientVertex ov=(OrientVertex)v;
                vertexType=ov.getType().getName();

                if(vertexType.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_FACT)){
                    relationableType=vertexType.replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_FACT,"");
                    OrientDBFactImpl targetFact=new OrientDBFactImpl(relationableType);
                    targetFact.setFactVertex(ov);
                    relationableList.add(targetFact);

                }else if(vertexType.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION)){
                    relationableType=vertexType.replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION,"");
                    OrientDBDimensionImpl targetDimension=new OrientDBDimensionImpl(relationableType);
                    targetDimension.setDimensionVertex(ov);
                    relationableList.add(targetDimension);
                }
            }
            return relationableList;
        }
    }

    @Override
    public List<Fact> exploreFactsByRelatedRelationTypes(List<RelationTypeFilter> relationTypeFilters, ExploreParameters exploreParameters) throws InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {
        String factTypeStr=exploreParameters.getType();
        String factType=null;
        if(factTypeStr!=null){
            factType=InfoDiscoverEngineConstant.CLASSPERFIX_FACT+factTypeStr;
            OrientVertexType dovt=this.graph.getVertexType(factType);
            if(dovt==null){
                String exceptionMessage = "Fact Type "+factTypeStr+" not exists";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }
        }
        String sql=SQLBuilder.buildExploreFactsByRelationTypesSQL(relationTypeFilters, exploreParameters);
        List<Fact> factList=new ArrayList<>();
        String factRealType;
        for (Vertex v : (Iterable<Vertex>) graph.command(
                new OCommandSQL(sql)).execute()) {
            OrientVertex fov=(OrientVertex)v;
            if(factType==null){
                factRealType=fov.getType().getName().replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_FACT,"");
            }else{
                factRealType=factType;
            }

            OrientDBFactImpl targetFact=new OrientDBFactImpl(factRealType);
            targetFact.setFactVertex(fov);
            factList.add(targetFact);
        }
        return factList;
    }

    @Override
    public List<Fact> exploreFactsByRelatedDimensions(List<DimensionsFilter> dimensionsFilters,ExploreParameters exploreParameters) throws InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {
        String factTypeStr=exploreParameters.getType();
        String factType=null;
        if(factTypeStr!=null){
            factType=InfoDiscoverEngineConstant.CLASSPERFIX_FACT+factTypeStr;
            OrientVertexType dovt=this.graph.getVertexType(factType);
            if(dovt==null){
                String exceptionMessage = "Fact Type "+factTypeStr+" not exists";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }
        }
        String sql= SQLBuilder.buildExploreFactsByRelatedDimensionsSQL(dimensionsFilters, exploreParameters);
        List<Fact> factList=new ArrayList<>();
        String factRealType;
        for (Vertex v : (Iterable<Vertex>) graph.command(
                new OCommandSQL(sql)).execute()) {
            OrientVertex fov=(OrientVertex)v;
            if(factType==null){
                factRealType=fov.getType().getName().replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_FACT,"");
            }else{
                factRealType=factType;
            }

            OrientDBFactImpl targetFact=new OrientDBFactImpl(factRealType);
            targetFact.setFactVertex(fov);
            factList.add(targetFact);
        }
        return factList;
    }

    @Override
    public List<Relation> exploreRelationsByRelatedRelationables(RelationDirection relationDirection, List<String> relationableIds, ExploreParameters exploreParameters) throws InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {
        if(relationableIds==null||relationableIds.size()==0){
            String exceptionMessage = "Relationable ids not exists";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }else {
            for(String currentRelationableId:relationableIds){
                OrientVertex ov=graph.getVertex(currentRelationableId);
                if(ov==null){
                    String exceptionMessage = "Relationable with id "+currentRelationableId+" not exist";
                    throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
                }else{
                    String typeName=ov.getType().getName();
                    if(typeName.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_FACT)||
                            typeName.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION)){}else{
                        String exceptionMessage = "Record with id "+currentRelationableId+" is not a relationable";
                        throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
                    }
                }
            }

            String sql = SQLBuilder.buildExploreRelationsByRelatedRelationableSQL(relationableIds, relationDirection, exploreParameters);
            List<Relation> relationList = new ArrayList<>();
            String relationType = exploreParameters.getType();
            String relationRealType;
            for (Edge e : (Iterable<Edge>) graph.command(
                    new OCommandSQL(sql)).execute()) {
                OrientEdge oe = (OrientEdge) e;
                if (relationType == null) {
                    relationRealType = oe.getType().getName().replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_RELATION, "");
                } else {
                    relationRealType = relationType;
                }
                OrientDBRelationImpl targetRelation = new OrientDBRelationImpl(relationRealType);
                targetRelation.setRelationEdge(oe);
                relationList.add(targetRelation);
            }
            return relationList;
        }
    }

    @Override
    public List<Fact> exploreFactsByRelations(RelationDirection relationDirection, List<String> relationIds, ExploreParameters exploreParameter) throws InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException{
        if(relationIds==null||relationIds.size()==0){
            String exceptionMessage = "Relation ids not exists";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }else{
            String factTypeStr=exploreParameter.getType();
            String factType=null;
            if(factTypeStr!=null){
                factType=InfoDiscoverEngineConstant.CLASSPERFIX_FACT+factTypeStr;
                OrientVertexType dovt=this.graph.getVertexType(factType);
                if(dovt==null){
                    String exceptionMessage = "Fact Type "+factTypeStr+" not exists";
                    throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
                }
            }
            String sql=SQLBuilder.buildExploreRelationablesByRelationSQL(relationIds, relationDirection, exploreParameter, InformationType.FACT);
            List<Fact> factList=new ArrayList<>();
            String factRealType;
            for (Vertex v : (Iterable<Vertex>) graph.command(
                    new OCommandSQL(sql)).execute()) {
                OrientVertex fov=(OrientVertex)v;
                if(factType==null){
                    factRealType=fov.getType().getName().replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_FACT,"");
                }else{
                    factRealType=factType;
                }

                OrientDBFactImpl targetFact=new OrientDBFactImpl(factRealType);
                targetFact.setFactVertex(fov);
                factList.add(targetFact);
            }
            return factList;
        }
    }

    @Override
    public List<Dimension> exploreDimensionsByRelations(RelationDirection relationDirection, List<String> relationIds, ExploreParameters exploreParameter) throws InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException{
        if(relationIds==null||relationIds.size()==0){
            String exceptionMessage = "Relation ids not exists";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }else{
            String dimensionTypeStr=exploreParameter.getType();
            String dimensionType=null;
            if(dimensionTypeStr!=null){
                dimensionType=InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION+dimensionTypeStr;
                OrientVertexType dovt=this.graph.getVertexType(dimensionType);
                if(dovt==null){
                    String exceptionMessage = "Dimension Type "+dimensionTypeStr+" not exists";
                    throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
                }
            }
            String sql=SQLBuilder.buildExploreRelationablesByRelationSQL(relationIds, relationDirection, exploreParameter, InformationType.DIMENSION);
            List<Dimension> dimensionList=new ArrayList<>();
            String dimensionRealType;
            for (Vertex v : (Iterable<Vertex>) graph.command(
                    new OCommandSQL(sql)).execute()) {
                OrientVertex dov=(OrientVertex)v;
                if(dimensionType==null){
                    dimensionRealType=dov.getType().getName().replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION,"");
                }else{
                    dimensionRealType=dimensionType;
                }
                OrientDBDimensionImpl targetDimension=new OrientDBDimensionImpl(dimensionRealType);
                targetDimension.setDimensionVertex(dov);
                dimensionList.add(targetDimension);
            }
            return dimensionList;
        }
    }

    @Override
    public List<Measurable> discoverMeasurablesByQuerySQL(InformationType informationType, String typeName, String querySQL) throws InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException{
        informationTypeCheck(typeName, informationType);
        List<Measurable> measurableList=new ArrayList<>();
        switch(informationType){
            case FACT:
                String factRealType;
                for (Vertex v : (Iterable<Vertex>) graph.command(
                        new OCommandSQL(querySQL)).execute()) {
                    OrientVertex ov=(OrientVertex)v;
                    if(typeName==null){
                        factRealType=ov.getType().getName().replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_FACT,"");
                    }else{
                        factRealType=typeName;
                    }
                    OrientDBFactImpl targetFact=new OrientDBFactImpl(factRealType);
                    targetFact.setFactVertex(ov);
                    measurableList.add(targetFact);
                }
                break;
            case DIMENSION:
                String dimensionRealType;
                for (Vertex v : (Iterable<Vertex>) graph.command(
                        new OCommandSQL(querySQL)).execute()) {
                    OrientVertex ov=(OrientVertex)v;
                    if(typeName==null) {
                        dimensionRealType = ov.getType().getName().replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION, "");
                    }else{
                        dimensionRealType=typeName;
                    }
                    OrientDBDimensionImpl targetDimension=new OrientDBDimensionImpl(dimensionRealType);
                    targetDimension.setDimensionVertex(ov);
                    measurableList.add(targetDimension);
                }
                break;
            case RELATION:
                String relationRealType;
                for (Edge e : (Iterable<Edge>) graph.command(
                        new OCommandSQL(querySQL)).execute()) {
                    OrientEdge oe=(OrientEdge)e;
                    if(typeName==null){
                        relationRealType=oe.getType().getName().replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_RELATION,"");

                    }else{
                        relationRealType=typeName;
                    }
                    OrientDBRelationImpl targetRelation=new OrientDBRelationImpl(relationRealType);
                    targetRelation.setRelationEdge(oe);
                    measurableList.add(targetRelation);
                }
                break;
        }
        return measurableList;
    }

    @Override
    public Stack<Relation> discoverRelationablesShortestPath(String firstRelationableId, String secondRelationableId, RelationDirection relationDirection) throws InfoDiscoveryEngineRuntimeException{
        OrientVertex firstOv = this.graph.getVertex(firstRelationableId);
        if(firstOv==null){
            String exceptionMessage = "Relationable id "+firstRelationableId+" not exists";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }
        OrientVertex secondOv = this.graph.getVertex(secondRelationableId);
        if(secondOv==null){
            String exceptionMessage = "Relationable id "+secondRelationableId+" not exists";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }
        List<Edge> relationPath= OrientDBShortestPath.getEdgesFromShortestPath(this.graph,firstRelationableId,secondRelationableId);
        if(relationPath==null||relationPath.size()==0){
            return null;
        }else{
            Stack<Relation> relationOnPathStack=new Stack();
            for(Edge currentEdge:relationPath){
                OrientEdge oe=(OrientEdge)currentEdge;
                String relationRealType=oe.getType().getName().replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_RELATION,"");
                OrientDBRelationImpl targetRelation=new OrientDBRelationImpl(relationRealType);
                targetRelation.setRelationEdge(oe);
                relationOnPathStack.push(targetRelation);
            }
            return relationOnPathStack;
        }
    }

    @Override
    public List<Stack<Relation>> discoverRelationablesAllPaths(String firstRelationableId, String secondRelationableId) throws InfoDiscoveryEngineRuntimeException {
        OrientVertex firstOv = this.graph.getVertex(firstRelationableId);
        if(firstOv==null){
            String exceptionMessage = "Relationable id "+firstRelationableId+" not exists";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }
        OrientVertex secondOv = this.graph.getVertex(secondRelationableId);
        if(secondOv==null){
            String exceptionMessage = "Relationable id "+secondRelationableId+" not exists";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }
        OrientDBAllPaths orientDBAllPaths=new OrientDBAllPaths(this.graph);
        List<Stack<Edge>> relationPaths= orientDBAllPaths.getEdgesOfAllPaths(firstRelationableId,secondRelationableId);
        if(relationPaths==null||relationPaths.size()==0){
            return null;
        }else{
            List<Stack<Relation>> relationOnPathStackList=new ArrayList<>();
            for(Stack<Edge> currentRelationStack:relationPaths){
                Stack<Relation> relationOnPathStack=new Stack();
                int stackSize=currentRelationStack.size();
                for(int i=0;i<stackSize;i++){
                    Edge currentEdge=currentRelationStack.elementAt(i);
                    OrientEdge oe=(OrientEdge)currentEdge;
                    String relationRealType=oe.getType().getName().replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_RELATION,"");
                    OrientDBRelationImpl targetRelation=new OrientDBRelationImpl(relationRealType);
                    targetRelation.setRelationEdge(oe);
                    relationOnPathStack.push(targetRelation);
                }
                relationOnPathStackList.add(relationOnPathStack);
            }
            return relationOnPathStackList;
        }
    }

    @Override
    public List<Relationable> discoverSimilarRelationablesRelatedToSameDimensions(String sourceRelationableId, List<String> dimensionIds,FilteringPattern filteringPattern) throws InfoDiscoveryEngineRuntimeException {
        OrientVertex firstOv = this.graph.getVertex(sourceRelationableId);
        if(firstOv==null){
            String exceptionMessage = "Relationable id "+sourceRelationableId+" not exists";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }
        List<Relationable> resultRelationableList=null;
        StringBuilder dimensionRids = new StringBuilder();
        for (String dimensionId: dimensionIds) {
            dimensionRids.append(dimensionId + ",");
        }
        String rids = dimensionRids.toString().substring(0, dimensionRids.toString().length() - 1);
        String innerQuery = null;
        switch(filteringPattern){
            case OR:
                innerQuery = "(select from " + InfoDiscoverEngineConstant.DIMENSION_ROOTCLASSNAME+ " where  @rid in [" + rids + "])";
                String sql = "select from (select from (TRAVERSE both() FROM " + innerQuery + " while " + "$depth < 2)) ";
                resultRelationableList = discoverRelationables(sql);
                Iterator<Relationable> relationableIterator= resultRelationableList.iterator();
                while(relationableIterator.hasNext()){
                    Relationable currentRelationable=relationableIterator.next();
                    if(currentRelationable.getId().equals(sourceRelationableId)||dimensionIds.contains(currentRelationable.getId())){
                        relationableIterator.remove();
                    }
                }
                return resultRelationableList;
            case AND:
                resultRelationableList = new ArrayList<>();
                innerQuery = "select from " + InfoDiscoverEngineConstant.DIMENSION_ROOTCLASSNAME+ " where @rid in [" + rids + "]";
                List<OrientVertex> verticesList = new ArrayList<>();
                for (OrientVertex v : (Iterable<OrientVertex>) graph.command(
                        new OCommandSQL(innerQuery)).execute()) {
                    verticesList.add(v);
                }
                List<Vertex> result = new ArrayList<>();
                if (verticesList != null && verticesList.size() > 0) {
                    for (int i = 0; i < verticesList.size(); i++) {
                        List<Vertex> list = new ArrayList<>();
                        Iterable<Vertex> it = verticesList.get(i).getVertices(Direction.BOTH);
                        for (Vertex v : it) {
                            String currentRecordId=((OrientVertex) v).getId().toString();
                            if(!sourceRelationableId.equals(currentRecordId)){
                                list.add(v);
                            }
                        }
                        if (i == 0) {
                            result = list;
                        }
                        result.retainAll(list);
                    }
                }
                for(Vertex currentVertex:result){
                    OrientVertex ov=(OrientVertex)currentVertex;
                    String ovName=ov.getType().getName();
                    if(ovName.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_FACT)){
                        String factRealType=ov.getType().getName().replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_FACT,"");
                        OrientDBFactImpl targetFact=new OrientDBFactImpl(factRealType);
                        targetFact.setFactVertex(ov);
                        resultRelationableList.add(targetFact);
                    }
                    if(ovName.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION)){
                        String dimensionRealType = ov.getType().getName().replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION, "");
                        OrientDBDimensionImpl targetDimension=new OrientDBDimensionImpl(dimensionRealType);
                        targetDimension.setDimensionVertex(ov);
                        resultRelationableList.add(targetDimension);
                    }
                }
                return resultRelationableList;
        }
        return null;
    }

    private void informationTypeCheck(String typeName,InformationType informationType)throws InfoDiscoveryEngineRuntimeException{
        if(typeName==null){
            //String exceptionMessage="Parameter type is required";
            //throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            return;
        }
        String orientDBClassName;
        switch(informationType){
            case FACT:
                orientDBClassName= InfoDiscoverEngineConstant.CLASSPERFIX_FACT+typeName;
                OrientVertexType ovtFact=this.graph.getVertexType(orientDBClassName);
                if(ovtFact==null){
                    String exceptionMessage = "Fact Type "+typeName+" does not exist";
                    throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
                }
                break;
            case DIMENSION:
                orientDBClassName= InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION+typeName;
                OrientVertexType ovtDimension=this.graph.getVertexType(orientDBClassName);
                if(ovtDimension==null){
                    String exceptionMessage = "Dimension Type "+typeName+" does not exist";
                    throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
                }
                break;
            case RELATION:
                orientDBClassName= InfoDiscoverEngineConstant.CLASSPERFIX_RELATION+typeName;
                OrientEdgeType oeRelation=this.graph.getEdgeType(orientDBClassName);
                if(oeRelation==null){
                    String exceptionMessage = "Relation Type "+typeName+" does not exist";
                    throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
                }
                break;
        }
    }
}
