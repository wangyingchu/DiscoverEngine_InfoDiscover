package com.viewfnction.infoDiscoverEngine.dataMartImpl;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;
import com.viewfnction.infoDiscoverEngine.dataMart.FactType;
import com.viewfnction.infoDiscoverEngine.util.InfoDiscoverEngineConstant;

public class OrientDBFactTypeImpl extends OrientDBTypePropertyableImpl implements FactType{

    private OrientVertexType orientVertexType;
    private OrientGraph graph;

    public OrientDBFactTypeImpl(OrientVertexType orientVertexType,OrientGraph graph){
        super(orientVertexType,graph);
        this.orientVertexType=orientVertexType;
        this.graph=graph;
    }

    @Override
    public String getTypeName() {
        if(this.orientVertexType!=null){
            String orientDBVertexTypeName=this.orientVertexType.getName();
            String typeName=orientDBVertexTypeName.replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_FACT,"");
            return typeName;
        }else{
            return null;
        }
    }
}

