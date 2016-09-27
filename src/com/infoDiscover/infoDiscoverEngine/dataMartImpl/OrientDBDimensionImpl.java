package com.infoDiscover.infoDiscoverEngine.dataMartImpl;


import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;

public class OrientDBDimensionImpl extends OrientDBRelationableImpl implements Dimension {
    private String id;
    private String type;
    private OrientVertex dimensionVertex;
    public OrientDBDimensionImpl(String type){
        this.type=type;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getId(){
        if(id!=null){
            return id;
        }else{
            if(dimensionVertex!=null){
                id=dimensionVertex.getId().toString();
                return id;
            }else{
                return null;
            }
        }
    }

    public void setDimensionVertex(OrientVertex factVertex) {
        this.dimensionVertex = factVertex;
        super.setMeasureElement(this.dimensionVertex);
        super.setRelationVertex(this.dimensionVertex);
    }

    public OrientVertex getDimensionVertex(){
        return this.dimensionVertex;
    }
}
