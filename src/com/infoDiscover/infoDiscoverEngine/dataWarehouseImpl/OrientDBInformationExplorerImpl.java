package com.infoDiscover.infoDiscoverEngine.dataWarehouseImpl;

import com.infoDiscover.infoDiscoverEngine.dataMart.*;
import com.infoDiscover.infoDiscoverEngine.dataMartImpl.OrientDBFactImpl;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.*;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.*;
import com.infoDiscover.infoDiscoverEngine.dataMartImpl.OrientDBDimensionImpl;
import com.infoDiscover.infoDiscoverEngine.dataMartImpl.OrientDBRelationImpl;
import com.infoDiscover.infoDiscoverEngine.dataMartImpl.OrientDBRelationableImpl;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;

import java.util.ArrayList;
import java.util.List;

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
            OrientDBRelationableImpl targetRelationable=new OrientDBRelationableImpl();
            targetRelationable.setRelationVertex(ov);
            relationableList.add(targetRelationable);
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
