package com.infoDiscover.solution.common.dimension;

import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.DimensionType;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.builder.SolutionConstants;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.common.util.PropertyTypeUtil;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by sun.
 */
public class DimensionManager {
    private final static Logger logger = LoggerFactory.getLogger(DimensionManager.class);
    private InfoDiscoverSpace ids;

    public DimensionManager(InfoDiscoverSpace ids) {
        this.ids = ids;
    }

    public Dimension createDimension(String type, Map<String,
            Object> properties) {
        logger.debug("Enter method createDimension() with InfoDiscoverSpace: {}" +
                " and type: {}" +
                " and properties: {}", ids, type, properties);
        try {
            Dimension dimension = DiscoverEngineComponentFactory.createDimension(type);
            dimension = ids.addDimension(dimension);
            dimension.addProperties(properties);
            return dimension;
        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.error("Failed to create dimension: {}", e.getMessage());
        }

        logger.debug("Exit createNewOrUpdateFact()...");
        return null;
    }

    public Dimension updateDimension(Dimension dimension, Map<String, Object> properties) throws
            InfoDiscoveryEngineRuntimeException {
        logger.debug("Enter method updateDimension() with dimensionId: " + dimension.getId() + "" +
                " and " +
                "properties: " +
                properties);
        dimension.addNewOrUpdateProperties(properties);
        logger.debug("End method updateDimension()...");

        return dimension;
    }

    public Dimension getDimension(String dimensionType, String key, String value) {

        ExploreParameters ep = new ExploreParameters();
        ep.setType(dimensionType);
        ep.setDefaultFilteringItem(new EqualFilteringItem(key, value));

        return QueryExecutor.executeDimensionQuery(ids.getInformationExplorer(), ep);
    }

    public void createDimensionType(String dimensionTypeName, JsonNode propertiesNode) throws
            InfoDiscoveryEngineDataMartException, InfoDiscoveryEngineRuntimeException {
        logger.info("Start to createDimensionType with dimensionTypeName: {} and propertiesNode: {}",
                dimensionTypeName, propertiesNode);

        if (!ids.hasDimensionType(dimensionTypeName)) {
            DimensionType dimensionType = ids.addDimensionType(dimensionTypeName);
            if (propertiesNode != null) {
                for (JsonNode property : propertiesNode) {
                    String propertyName = property.get(SolutionConstants.JSON_PROPERTY_NAME)
                            .asText();
                    String propertyType = property.get(SolutionConstants.JSON_PROPERTY_TYPE)
                            .asText();
                    dimensionType.addTypeProperty(propertyName, PropertyTypeUtil.getPropertyType
                            (propertyType));
                }
            }
        }

        logger.info("End to createDimensionType...");
    }
}
