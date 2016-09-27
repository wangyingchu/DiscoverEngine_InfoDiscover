package com.infoDiscover.infoDiscoverEngine.dataMartImpl;

import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;

public class OrientDBFactImpl extends OrientDBRelationableImpl implements Fact {
    private String id;
    private String type;

    public OrientVertex getFactVertex() {
        return factVertex;
    }

    private OrientVertex factVertex;
    public OrientDBFactImpl(String type){
        this.type=type;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getId() {
        if(id!=null){
            return id;
        }else{
            if(factVertex!=null){
                id=factVertex.getId().toString();
                return id;
            }else{
                return null;
            }
        }
    }

    public void setFactVertex(OrientVertex factVertex) {
        this.factVertex = factVertex;
        super.setMeasureElement(this.factVertex);
        super.setRelationVertex(this.factVertex);
    }
}
