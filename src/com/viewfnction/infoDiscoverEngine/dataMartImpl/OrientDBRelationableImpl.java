package com.viewfnction.infoDiscoverEngine.dataMartImpl;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.impls.orient.OrientEdge;
import com.tinkerpop.blueprints.impls.orient.OrientEdgeType;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import com.viewfnction.infoDiscoverEngine.dataMart.Relation;
import com.viewfnction.infoDiscoverEngine.dataMart.RelationDirection;
import com.viewfnction.infoDiscoverEngine.dataMart.Relationable;
import com.viewfnction.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.viewfnction.infoDiscoverEngine.util.exception.InfoDiscoveryEngineException;
import com.viewfnction.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrientDBRelationableImpl extends OrientDBMeasurableImpl implements Relationable{

    private OrientVertex relationVertex;

    public void setRelationVertex(OrientVertex relationVertex) {
        this.relationVertex = relationVertex;
    }

    public OrientVertex getRelationVertex() {
        if(this.relationVertex!=null){
            this.relationVertex.reload();
        }
        return this.relationVertex;
    }

    @Override
    public String getId() {
        return this.relationVertex.getId().toString();
    }

    @Override
    public List<Relation> getAllRelations() {
        if(this.getRelationVertex()!=null){
            List<Relation> relationList=new ArrayList<Relation>();
            Iterable<Edge> relationIterable=this.getRelationVertex().getEdges(Direction.BOTH, null);
            Iterator <Edge> edgeIterator=relationIterable.iterator();
            while(edgeIterator.hasNext()){
                Edge currentEdge=edgeIterator.next();
                OrientEdge targetEdge=(OrientEdge)currentEdge;
                String edgeOrientClassName=targetEdge.getType().getName();
                String relationType=edgeOrientClassName.replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_RELATION, "");
                OrientDBRelationImpl targetRelation=new OrientDBRelationImpl(relationType);
                targetRelation.setRelationEdge(targetEdge);
                relationList.add(targetRelation);
            }
            return relationList;
        }else{
            return null;
        }
    }

    @Override
    public List<Relation> getSpecifiedRelations(String relationType, RelationDirection relationDirection) throws InfoDiscoveryEngineRuntimeException {
        String relationTypeClassName=getRelationTypeClassName(relationType,true);
        Direction direction=Direction.BOTH;
        switch(relationDirection){
            case FROM:direction=Direction.OUT;
                break;
            case TO:direction=Direction.IN;
                break;
            case TWO_WAY:direction=Direction.BOTH;
                break;
        }
        List<Relation> relationList=new ArrayList<Relation>();
        Iterable<Edge> relationIterable;
        if(relationTypeClassName!=null){
            relationIterable=this.getRelationVertex().getEdges(direction, relationTypeClassName);
        }else{
            relationIterable=this.getRelationVertex().getEdges(direction, null);
        }
        Iterator <Edge> edgeIterator=relationIterable.iterator();
        while(edgeIterator.hasNext()){
            Edge currentEdge=edgeIterator.next();
            OrientEdge targetEdge=(OrientEdge)currentEdge;
            String edgeOrientClassName=targetEdge.getType().getName();
            String currentRelationType=edgeOrientClassName.replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_RELATION, "");
            OrientDBRelationImpl targetRelation=new OrientDBRelationImpl(currentRelationType);
            targetRelation.setRelationEdge(targetEdge);
            relationList.add(targetRelation);
        }
        return relationList;
    }

    @Override
    public long getRelationCount(String relationType, RelationDirection relationDirection) throws InfoDiscoveryEngineRuntimeException {
        String relationTypeClassName=getRelationTypeClassName(relationType,true);
        Direction direction=Direction.BOTH;
        switch(relationDirection){
            case FROM:direction=Direction.OUT;
                break;
            case TO:direction=Direction.IN;
                break;
            case TWO_WAY:direction=Direction.BOTH;
                break;
        }
        if(relationTypeClassName!=null){
            return this.getRelationVertex().countEdges(direction, relationTypeClassName);
        }else{
            return this.getRelationVertex().countEdges(direction, null);
        }
    }

    @Override
    public Relation addFromRelation(Relationable fromRelationable, String relationType) throws InfoDiscoveryEngineRuntimeException {
        String relationTypeClassName=getRelationTypeClassName(relationType,false);
        OrientDBRelationableImpl fromRelationableImpl=(OrientDBRelationableImpl)fromRelationable;
        OrientVertex fromVertex=  fromRelationableImpl.getRelationVertex();
        OrientEdge resultEdge=this.getRelationVertex().getGraph().addEdge(null, fromVertex, this.relationVertex, relationTypeClassName);
        this.getRelationVertex().getGraph().commit();
        OrientDBRelationImpl newRelation=new OrientDBRelationImpl(relationType);
        newRelation.setRelationEdge(resultEdge);
        return newRelation;
    }

    @Override
    public Relation addToRelation(Relationable toRelationable, String relationType) throws InfoDiscoveryEngineRuntimeException {
        String relationTypeClassName=getRelationTypeClassName(relationType, false);
        OrientDBRelationableImpl toRelationableImpl=(OrientDBRelationableImpl)toRelationable;
        OrientVertex toVertex=  toRelationableImpl.getRelationVertex();
        Edge resultEdge=this.getRelationVertex().addEdge(relationTypeClassName, toVertex);
        this.getRelationVertex().getGraph().commit();
        OrientEdge resultOrientEdge=(OrientEdge)resultEdge;
        OrientDBRelationImpl newRelation=new OrientDBRelationImpl(relationType);
        newRelation.setRelationEdge(resultOrientEdge);
        return newRelation;
    }

    @Override
    public boolean removeRelation(String relationId) throws InfoDiscoveryEngineRuntimeException {
        OrientEdge targetEdge=this.getRelationVertex().getGraph().getEdge(relationId);
        if(targetEdge==null){
            String exceptionMessage = "Relation with id "+relationId+" not exists";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }else{
            String toOrientVertexId=targetEdge.getInVertex().getIdentity().toString();
            String fromOrientVertexId=targetEdge.getOutVertex().getIdentity().toString();
            if(toOrientVertexId.equals(this.getRelationVertex().getId().toString())||
                    fromOrientVertexId.equals(this.getRelationVertex().getId().toString())){
                targetEdge.remove();
                this.getRelationVertex().getGraph().commit();
                return true;
            }else{
                String exceptionMessage = "Relation with id "+relationId+" is not related to Relationable with id "+this.relationVertex.getId();
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }
        }
    }

    @Override
    public List<String> removeAllRelations() {
        List<String> removedRelationIdList=new ArrayList<String>();
        Iterable<Edge> relationIterable=this.getRelationVertex().getEdges(Direction.BOTH, null);
        Iterator <Edge> edgeIterator=relationIterable.iterator();
        while(edgeIterator.hasNext()){
            Edge currentEdge=edgeIterator.next();
            OrientEdge targetEdge=(OrientEdge)currentEdge;
            removedRelationIdList.add(targetEdge.getId().toString());
            targetEdge.remove();
        }
        this.getRelationVertex().getGraph().commit();
        return removedRelationIdList;
    }

    @Override
    public List<String> removeSpecifiedRelations(String relationType, RelationDirection relationDirection) throws InfoDiscoveryEngineRuntimeException {
        String relationTypeClassName=getRelationTypeClassName(relationType,true);
        Direction direction=Direction.BOTH;
        switch(relationDirection){
            case FROM:direction=Direction.OUT;
                break;
            case TO:direction=Direction.IN;
                break;
            case TWO_WAY:direction=Direction.BOTH;
                break;
        }
        List<String> removedRelationIdList=new ArrayList<String>();
        Iterable<Edge> relationIterable;
        if(relationTypeClassName!=null){
            relationIterable=this.getRelationVertex().getEdges(direction, relationTypeClassName);
        }else{
            relationIterable=this.getRelationVertex().getEdges(direction, null);
        }
        Iterator <Edge> edgeIterator=relationIterable.iterator();
        while(edgeIterator.hasNext()){
            Edge currentEdge=edgeIterator.next();
            OrientEdge targetEdge=(OrientEdge)currentEdge;
            removedRelationIdList.add(targetEdge.getId().toString());
            targetEdge.remove();
        }
        this.getRelationVertex().getGraph().commit();
        return removedRelationIdList;
    }

    private String getRelationTypeClassName(String relationType,boolean canBeNullValue)throws InfoDiscoveryEngineRuntimeException{
        if(relationType!=null){
            String relationTypeClassName=InfoDiscoverEngineConstant.CLASSPERFIX_RELATION+relationType;
            OrientEdgeType ovt=this.getRelationVertex().getGraph().getEdgeType(relationTypeClassName);
            if(ovt==null){
                String exceptionMessage = "Relation Type "+relationType+" not exists";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }else{
                return relationTypeClassName;
            }
        }else{
            if(canBeNullValue){
                return null;
            }else{
                String exceptionMessage = "Relation Type can not be null";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }
        }
    }
}
