package com.infoDiscover.infoDiscoverEngine.dataMartImpl;

import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.FactType;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationType;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.SQLBuilder;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;

import java.util.ArrayList;
import java.util.List;

public class OrientDBFactTypeImpl extends OrientDBTypePropertyableImpl implements FactType {

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

    @Override
    public long countContainedFacts() {
        if(this.orientVertexType!=null){
            return this.orientVertexType.count();
        }else{
            return 0;
        }
    }

    @Override
    public Object removeContainedFaces() {
        String sql= "DELETE VERTEX "+this.orientVertexType.getName();
        Object deleteResult=graph.command(new OCommandSQL(sql)).execute();
        return deleteResult;
    }
}

