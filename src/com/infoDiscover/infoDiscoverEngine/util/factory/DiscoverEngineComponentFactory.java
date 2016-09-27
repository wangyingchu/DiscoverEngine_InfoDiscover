package com.infoDiscover.infoDiscoverEngine.util.factory;

import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMartImpl.OrientDBDimensionImpl;
import com.infoDiscover.infoDiscoverEngine.dataMartImpl.OrientDBFactImpl;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureauImpl.OrientDBInfoDiscoverSpaceImpl;
import com.infoDiscover.infoDiscoverEngine.util.helper.MeasurableContentHelper;
import com.orientechnologies.orient.core.exception.OConfigurationException;
import com.tinkerpop.blueprints.TransactionalGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverAdminSpace;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureauImpl.OrientDBInfoDiscoverAdminSpaceImpl;
import com.infoDiscover.infoDiscoverEngine.util.helperImpl.OrientDBMeasurableContentHelperImpl;

public class DiscoverEngineComponentFactory {

    private static String serviceLocation="remote:localhost/";
    private static String userAccount="root";
    private static String userPWD="wyc";

    public static InfoDiscoverSpace connectInfoDiscoverSpace(String spaceName){
        try{
            OrientGraphFactory factory = new OrientGraphFactory(serviceLocation+spaceName,userAccount,userPWD).setupPool(1,10);
            OrientGraph graph = factory.getTx();
            return new OrientDBInfoDiscoverSpaceImpl(spaceName,graph);
        }catch(OConfigurationException e){
            return null;
        }
    }

    public static InfoDiscoverSpace connectNoTransactionInfoDiscoverSpace(String spaceName){
        try{
            OrientGraphFactory factory = new OrientGraphFactory(serviceLocation+spaceName,userAccount,userPWD).setupPool(1,10);
            OrientGraph graph = factory.getTx();
            graph.setRequireTransaction(false);
            graph.setAutoStartTx(false);
            graph.stopTransaction(TransactionalGraph.Conclusion.SUCCESS);
            return new OrientDBInfoDiscoverSpaceImpl(spaceName,graph);
        }catch(OConfigurationException e){
            return null;
        }
    }

    public static InfoDiscoverAdminSpace connectInfoDiscoverAdminSpace(String spaceName){
        try{
            OrientGraphFactory factory = new OrientGraphFactory(serviceLocation+spaceName,userAccount,userPWD).setupPool(1,10);
            OrientGraphNoTx graph = factory.getNoTx();
            return new OrientDBInfoDiscoverAdminSpaceImpl(spaceName,graph);
        }catch(OConfigurationException e){
            return null;
        }
    }

    public static MeasurableContentHelper getMeasurableContentHelper(){
        return new OrientDBMeasurableContentHelperImpl();
    }

    public static Fact createFact(String type){
        return new OrientDBFactImpl(type);
    }

    public static Dimension createDimension(String type){
        return new OrientDBDimensionImpl(type);
    }

    /*
    public static DimensionType createDimensionType(){
        return new OrientDBDimensionTypeImpl();
    }

    public static Relation createRelation(){
        return new OrientDBRelationImpl();
    }

    public static Property createProperty(){
        return new OrientDBPropertyImpl();
    }

    */

}
