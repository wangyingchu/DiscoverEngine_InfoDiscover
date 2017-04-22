package com.infoDiscover.solution.arch.progress.util;

import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.arch.database.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by sun.
 */
public class ProgressUtil {

    private final static Logger logger = LoggerFactory.getLogger(ProgressUtil.class);

    public static Fact createFact(String type, Map<String, Object>
            properties) throws InfoDiscoveryEngineRuntimeException {
        logger.debug("Enter method createFact() with type: " + type + " and properties: " +
                properties);
        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        Fact fact = createFact(ids, type, properties);
        ids.closeSpace();
        logger.debug("End method createFact()...");
        return fact;
    }

    public static Fact createFact(InfoDiscoverSpace ids, String type, Map<String,
            Object> properties) throws
            InfoDiscoveryEngineRuntimeException {
        logger.debug("Enter method createFact() with InfoDiscoverSpace: " + ids +
                " and type: " + type +
                " and properties: " + properties);
        Fact fact = DiscoverEngineComponentFactory.createFact(type);
        fact = ids.addFact(fact);
        fact.addProperties(properties);
        logger.debug("Exit createNewOrUpdateFact()...");
        return fact;
    }

    public static Fact updateFact(Fact fact, Map<String, Object> properties) throws
            InfoDiscoveryEngineRuntimeException {
        logger.debug("Enter method updateFact() with factId: " + fact.getId() + " and properties: " +
                properties);
        fact.addNewOrUpdateProperties(properties);
        logger.debug("End method updateFact()...");

        return fact;
    }

    public static Dimension createDimension(String type, Map<String, Object>
            properties) throws InfoDiscoveryEngineRuntimeException {
        logger.debug("Enter method createDimension() with type: " + type + " and properties: " +
                properties);
        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        Dimension dimension = createDimension(ids, type, properties);
        ids.closeSpace();
        logger.debug("End method createDimension()...");
        return dimension;
    }

    public static Dimension createDimension(InfoDiscoverSpace ids, String type, Map<String,
            Object> properties) throws
            InfoDiscoveryEngineRuntimeException {
        logger.debug("Enter method createDimension() with InfoDiscoverSpace: " + ids +
                " and type: " + type +
                " and properties: " + properties);
        Dimension dimension = DiscoverEngineComponentFactory.createDimension(type);
        dimension = ids.addDimension(dimension);
        dimension.addProperties(properties);
        logger.debug("Exit createNewOrUpdateFact()...");
        return dimension;
    }

    public static Dimension updateDimension(Dimension dimension, Map<String, Object> properties) throws
            InfoDiscoveryEngineRuntimeException {
        logger.debug("Enter method updateDimension() with dimensionId: " + dimension.getId() + "" +
                " and " +
                "properties: " +
                properties);
        dimension.addNewOrUpdateProperties(properties);
        logger.debug("End method updateDimension()...");

        return dimension;
    }
}
