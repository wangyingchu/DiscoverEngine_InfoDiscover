package com.infoDiscover.infoDiscoverEngine.util.factory;

import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMartImpl.OrientDBDimensionImpl;
import com.infoDiscover.infoDiscoverEngine.dataMartImpl.OrientDBFactImpl;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverAdminSpace;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureauImpl
        .OrientDBInfoDiscoverAdminSpaceImpl;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureauImpl.OrientDBInfoDiscoverSpaceImpl;
import com.infoDiscover.infoDiscoverEngine.util.config.PropertyHandler;
import com.infoDiscover.infoDiscoverEngine.util.helper.DiscoverSpaceStatisticHelper;
import com.infoDiscover.infoDiscoverEngine.util.helper.MeasurableContentHelper;
import com.infoDiscover.infoDiscoverEngine.util.helperImpl.OrientDBDiscoverSpaceStatisticHelperImpl;
import com.infoDiscover.infoDiscoverEngine.util.helperImpl.OrientDBMeasurableContentHelperImpl;
import com.orientechnologies.orient.client.remote.OServerAdmin;
import com.orientechnologies.orient.core.exception.OConfigurationException;
import com.tinkerpop.blueprints.TransactionalGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DiscoverEngineComponentFactory {

    private static String serviceLocation = PropertyHandler.getPropertyValue(PropertyHandler
            .DISCOVER_ENGINE_SERVICE_LOCATION);
    private static String userAccount = PropertyHandler.getPropertyValue(PropertyHandler
            .DISCOVER_ENGINE_ADMIN_ACCOUNT);
    private static String userPWD = PropertyHandler.getPropertyValue(PropertyHandler
            .DISCOVER_ENGINE_ADMIN_PWD);
    private static String spaceDBType = PropertyHandler.getPropertyValue(PropertyHandler
            .DISCOVER_SPACE_DATABASE_TYPE);
    private static String spaceStorageMode = PropertyHandler.getPropertyValue(PropertyHandler
            .DISCOVER_SPACE_STORAGE_MODE);

    public static InfoDiscoverSpace connectInfoDiscoverSpace(String spaceName) {
        try {
            OrientGraphFactory factory = new OrientGraphFactory(serviceLocation + spaceName,
                    userAccount, userPWD).setupPool(1, 10);
            OrientGraph graph = factory.getTx();
            return new OrientDBInfoDiscoverSpaceImpl(spaceName, graph);
        } catch (OConfigurationException e) {
            return null;
        }
    }

    public static InfoDiscoverSpace connectNoTransactionInfoDiscoverSpace(String spaceName) {
        try {
            OrientGraphFactory factory = new OrientGraphFactory(serviceLocation + spaceName,
                    userAccount, userPWD).setupPool(1, 10);
            OrientGraph graph = factory.getTx();
            graph.setRequireTransaction(false);
            graph.setAutoStartTx(false);
            graph.stopTransaction(TransactionalGraph.Conclusion.SUCCESS);
            return new OrientDBInfoDiscoverSpaceImpl(spaceName, graph);
        } catch (OConfigurationException e) {
            return null;
        }
    }

    public static InfoDiscoverSpace connectInfoDiscoverSpace(String location,
                                                             String spaceName,
                                                             String userName,
                                                             String password) {
        try {
            OrientGraphFactory factory = new OrientGraphFactory(getConnectionUrl(location, spaceName),
                    userName, password).setupPool(1, 10);
            OrientGraph graph = factory.getTx();
            return new OrientDBInfoDiscoverSpaceImpl(spaceName, graph);
        } catch (OConfigurationException e) {
            return null;
        }
    }

    public static InfoDiscoverAdminSpace connectInfoDiscoverAdminSpace(String spaceName) {
        try {
            OrientGraphFactory factory = new OrientGraphFactory(serviceLocation + spaceName,
                    userAccount, userPWD).setupPool(1, 10);
            OrientGraphNoTx graph = factory.getNoTx();
            return new OrientDBInfoDiscoverAdminSpaceImpl(spaceName, graph);
        } catch (OConfigurationException e) {
            return null;
        }
    }

    public static MeasurableContentHelper getMeasurableContentHelper() {
        return new OrientDBMeasurableContentHelperImpl();
    }

    public static Fact createFact(String type) {
        return new OrientDBFactImpl(type);
    }

    public static Dimension createDimension(String type) {
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

    public static List<String> getDiscoverSpacesListInEngine() {
        List<String> discoverSpacesList = new ArrayList<String>();
        try {
            OServerAdmin serverAdmin = new OServerAdmin(serviceLocation).connect(userAccount,
                    userPWD);
            Map<String, String> spacesInEngineMap = serverAdmin.listDatabases();
            Set<String> spaceNameSet = spacesInEngineMap.keySet();
            discoverSpacesList.addAll(spaceNameSet);
            serverAdmin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return discoverSpacesList;
    }

    public static List<String> getDiscoverSpacesListInEngine(OServerAdmin serverAdmin) {
        List<String> discoverSpacesList = new ArrayList<String>();
        try {
            Map<String, String> spacesInEngineMap = serverAdmin.listDatabases();
            Set<String> spaceNameSet = spacesInEngineMap.keySet();
            discoverSpacesList.addAll(spaceNameSet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return discoverSpacesList;
    }

    public static boolean checkDiscoverSpaceExistence(String spaceName) {
        List<String> discoverSpacesList = getDiscoverSpacesListInEngine();
        if (discoverSpacesList.contains(spaceName)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkDiscoverSpaceExistence(OServerAdmin serverAdmin, String spaceName) {
        List<String> discoverSpacesList = getDiscoverSpacesListInEngine(serverAdmin);
        return discoverSpacesList.contains(spaceName);
    }

    public static boolean createInfoDiscoverSpace(String spaceName) {
        boolean createActionResult = false;
        try {
            OServerAdmin serverAdmin = new OServerAdmin(serviceLocation).connect(userAccount,
                    userPWD);
            Map<String, String> spacesInEngineMap = serverAdmin.listDatabases();
            if (spacesInEngineMap.get(spaceName) != null) {
                //space already exists
                createActionResult = false;
            } else {
                serverAdmin.createDatabase(spaceName, spaceDBType, spaceStorageMode);
                spacesInEngineMap = serverAdmin.listDatabases();
                if (spacesInEngineMap.get(spaceName) != null) {
                    createActionResult = true;
                } else {
                    createActionResult = false;
                }
            }
            serverAdmin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createActionResult;
    }

    public static boolean createInfoDiscoverSpace(String location,
                                                  String spaceName, String userName,
                                                  String password,
                                                  String databaseType,
                                                  String storageMode) {
        boolean createActionResult = false;
        try {
            OServerAdmin serverAdmin = new OServerAdmin(getConnectionUrl(location, spaceName))
                    .connect(userName, password);
            Map<String, String> spacesInEngineMap = serverAdmin.listDatabases();
            if (spacesInEngineMap.get(spaceName) != null) {
                //space already exists
                createActionResult = false;
            } else {
                serverAdmin.createDatabase(spaceName, databaseType,storageMode);
                spacesInEngineMap = serverAdmin.listDatabases();
                createActionResult = (spacesInEngineMap.get(spaceName)) != null;
            }
            serverAdmin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createActionResult;
    }

    public static String getConnectionUrl(String location, String spaceName) {
        if (location.charAt(location.length() - 1) != '/') {
            return location + "/" + spaceName;
        }
        return location + spaceName;
    }

    public static boolean deleteInfoDiscoverSpace(String spaceName) {
        boolean deleteActionResult = false;
        try {
            OServerAdmin serverAdmin = new OServerAdmin(serviceLocation + spaceName).connect
                    (userAccount, userPWD);
            deleteActionResult=deleteInfoDiscoverSpace(serverAdmin, spaceName);
            serverAdmin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deleteActionResult;
    }

    public static boolean deleteInfoDiscoverSpace(OServerAdmin serverAdmin,String spaceName) {
        boolean deleteActionResult = false;
        try {
            Map<String, String> spacesInEngineMap = serverAdmin.listDatabases();
            if (spacesInEngineMap.get(spaceName) == null) {
                //space not exist
                deleteActionResult = false;
            } else {
                serverAdmin.dropDatabase(spaceName);
                spacesInEngineMap = serverAdmin.listDatabases();
                if (spacesInEngineMap.get(spaceName) == null) {
                    deleteActionResult = true;
                } else {
                    deleteActionResult = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deleteActionResult;
    }

    public static DiscoverSpaceStatisticHelper getDiscoverSpaceStatisticHelper() {
        return new OrientDBDiscoverSpaceStatisticHelperImpl(serviceLocation, userAccount, userPWD);
    }
}
