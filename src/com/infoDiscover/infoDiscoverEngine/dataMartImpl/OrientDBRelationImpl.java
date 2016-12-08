package com.infoDiscover.infoDiscoverEngine.dataMartImpl;

import com.infoDiscover.infoDiscoverEngine.dataMart.Relation;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relationable;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.tinkerpop.blueprints.impls.orient.OrientEdge;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

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
        return getRelationableChildImpl(fromVertex);
    }

    @Override
    public Relationable getToRelationable() {
        OrientVertex toVertex=  this.relationEdge.getGraph().getVertex(this.relationEdge.getInVertex().getIdentity());
        return getRelationableChildImpl(toVertex);
    }

    public void setRelationEdge(OrientEdge relationEdge) {
        this.relationEdge = relationEdge;
        super.setMeasureElement(this.relationEdge);
    }


    private Relationable getRelationableChildImpl(OrientVertex relationableVertex){
        String relationableTypeStr=relationableVertex.getType().getName();
        if(relationableTypeStr.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION)){
            String dimensionBusinessName=relationableTypeStr.replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION,"");
            OrientDBDimensionImpl targetDimension=new OrientDBDimensionImpl(dimensionBusinessName);
            targetDimension.setDimensionVertex(relationableVertex);
            return targetDimension;
        }else if(relationableTypeStr.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_FACT)){
            String factBusinessName=relationableTypeStr.replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_FACT,"");
            OrientDBFactImpl targetFact=new OrientDBFactImpl(factBusinessName);
            targetFact.setFactVertex(relationableVertex);
            return targetFact;
        }
        return null;
    }
}
