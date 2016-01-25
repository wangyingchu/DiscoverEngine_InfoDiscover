package com.viewfnction.infoDiscoverEngine.dataMartImpl;

import com.tinkerpop.blueprints.impls.orient.OrientEdge;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import com.viewfnction.infoDiscoverEngine.dataMart.Relation;
import com.viewfnction.infoDiscoverEngine.dataMart.Relationable;

public class OrientDBRelationImpl extends OrientDBMeasurableImpl implements Relation {
    private String id;
    private String type;
    private OrientEdge relationEdge;

    public OrientDBRelationImpl(String type){
        this.type=type;
    }

    @Override
    public String getId() {
        if(id!=null){
            return id;
        }else{
            if(relationEdge!=null){
                id=relationEdge.getId().toString();
                return id;
            }else{
                return null;
            }
        }
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public Relationable getFromRelationable() {
        OrientVertex fromVertex=  this.relationEdge.getGraph().getVertex(this.relationEdge.getOutVertex().getIdentity());
        OrientDBRelationableImpl fromRelationable=new OrientDBRelationableImpl();
        fromRelationable.setRelationVertex(fromVertex);
        fromRelationable.setMeasureElement(fromVertex);
        return fromRelationable;
    }

    @Override
    public Relationable getToRelationable() {
        OrientVertex toVertex=  this.relationEdge.getGraph().getVertex(this.relationEdge.getInVertex().getIdentity());
        OrientDBRelationableImpl toRelationable=new OrientDBRelationableImpl();
        toRelationable.setRelationVertex(toVertex);
        toRelationable.setMeasureElement(toVertex);
        return toRelationable;
    }

    public void setRelationEdge(OrientEdge relationEdge) {
        this.relationEdge = relationEdge;
        super.setMeasureElement(this.relationEdge);
    }
}
