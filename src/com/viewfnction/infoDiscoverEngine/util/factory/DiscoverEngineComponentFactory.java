package com.viewfnction.infoDiscoverEngine.util.factory;

import com.orientechnologies.orient.core.exception.OConfigurationException;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import com.viewfnction.infoDiscoverEngine.dataMart.*;
import com.viewfnction.infoDiscoverEngine.dataMartImpl.*;
import com.viewfnction.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.viewfnction.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverAdminSpace;
import com.viewfnction.infoDiscoverEngine.infoDiscoverBureauImpl.OrientDBInfoDiscoverAdminSpaceImpl;
import com.viewfnction.infoDiscoverEngine.infoDiscoverBureauImpl.OrientDBInfoDiscoverSpaceImpl;
import com.viewfnction.infoDiscoverEngine.util.helper.MeasurableContentHelper;
import com.viewfnction.infoDiscoverEngine.util.helperImpl.OrientDBMeasurableContentHelperImpl;

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
