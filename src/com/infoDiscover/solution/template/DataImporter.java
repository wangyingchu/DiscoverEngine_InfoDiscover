package com.infoDiscover.solution.template;

import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relationable;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.builder.SolutionConstants;
import com.infoDiscover.solution.common.database.DatabaseConnection;
import com.infoDiscover.solution.common.dimension.DimensionManager;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.common.fact.FactManager;
import com.infoDiscover.solution.common.util.JsonNodeUtil;
import com.infoDiscover.solution.construction.supervision.constants.JsonConstants;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by sun on 7/23/17.
 */
public class DataImporter {
    private final static Logger logger = LoggerFactory.getLogger(DataImporter.class);

    private String spaceName;
    private String solutionName;

    public DataImporter(String spaceName, String solutionName) {
        this.spaceName = spaceName;
        this.solutionName = solutionName;
    }

    public void importData(String dataJson, boolean overwrite) throws Exception {

        logger.info("Start to importData with data: {}", dataJson);

        // get relation mappings
        new RelationMapping(solutionName).getRelationMappings();

        InfoDiscoverSpace ids = DatabaseConnection.connectToSpace(spaceName);

        JsonNode dataJsonNode = JsonNodeUtil.getDataNode(dataJson);
        if (dataJson != null) {

            RelationMappingOperator operator = new RelationMappingOperator();

            for (JsonNode jsonNode : dataJsonNode) {

                String type = jsonNode.get(SolutionConstants.JSON_TYPE).asText();
                // create or update fact
                if (SolutionConstants.FACT_TYPE.equalsIgnoreCase(type)) {
                    Fact fact = (Fact) createRelationable(ids, jsonNode, overwrite);

                    // link relations
                    if (fact != null) {
                        operator.linkBetweenNodesFromFact(ids, fact);
                    }
                } else if (SolutionConstants.DIMENSION_TYPE.equalsIgnoreCase(type)) {
                    Dimension dimension = (Dimension) createRelationable(ids, jsonNode, overwrite);

                    // link relations
                    if (dimension != null) {
                        operator.linkBetweenNodesFromDimension(ids, dimension);
                    }
                }

            }
        }

        ids.closeSpace();
        logger.info("Exit to importData()...");
    }

    public Relationable createRelationable(InfoDiscoverSpace ids, JsonNode jsonNode, boolean override)
            throws
            Exception {
        String type = jsonNode.get(SolutionConstants.JSON_TYPE).asText();

        if (SolutionConstants.FACT_TYPE.equalsIgnoreCase(type)) {
            return createNewOrUpdateFact(ids, jsonNode, override);
        } else if (SolutionConstants.DIMENSION_TYPE.equalsIgnoreCase(type)) {
            return createNewOrUpdateDimension(ids,jsonNode,override);
        } else {
            throw new Exception("Wrong type, it should be: " + SolutionConstants.FACT_TYPE);
        }
    }

    private Fact createNewOrUpdateFact(InfoDiscoverSpace ids, JsonNode jsonNode, boolean overwrite)
            throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineDataMartException {
        logger.info("Start to createNewOrUpdateFact with jsonNode: {} and overwrite is: {}",
                jsonNode, overwrite);

        if (jsonNode == null) {
            logger.warn("Exit createNewOrUpdateFact() that jsonNode is null");
            return null;
        }

        Map<String, Object> uniqueKey = new HashMap<>();
        Map<String, Object> properties = new HashMap<>();

        JsonNode propertiesJsonNode = JsonNodeUtil.getPropertiesJsonNode(jsonNode);
        if (propertiesJsonNode == null) {
            logger.warn("Exit createNewOrUpdateFact() that propertiesJsonNode is null");
            return null;
        }

        // convert jsonNode to properties map
        convertJsonNodeToPropertiesMap(propertiesJsonNode, properties, uniqueKey);

        // create or update fact
        String typeName = getTypeName(jsonNode);
        FactManager manager = new FactManager(ids);
        Fact fact;

        if(!ids.hasFactType(typeName)) {
            ids.addFactType(typeName);
        }

        if (overwrite && uniqueKey.size() > 0) {
            fact = getFactWithUniqueKeys(ids, typeName, uniqueKey);
            if (fact != null) {
                return manager.updateFact(fact, properties);
            }
        }

        fact = manager.createFact(typeName, properties);

        logger.info("Exit createNewOrUpdateFact()...");

        return fact;
    }

    private Dimension createNewOrUpdateDimension(InfoDiscoverSpace ids, JsonNode jsonNode, boolean overwrite)
            throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineDataMartException {
        logger.info("Start to createNewOrUpdateDimension with jsonNode: {} and overwrite is: {}",
                jsonNode, overwrite);

        if (jsonNode == null) {
            logger.warn("Exit createNewOrUpdateDimension() that jsonNode is null");
            return null;
        }

        Map<String, Object> uniqueKey = new HashMap<>();
        Map<String, Object> properties = new HashMap<>();

        JsonNode propertiesJsonNode = JsonNodeUtil.getPropertiesJsonNode(jsonNode);
        if (propertiesJsonNode == null) {
            logger.warn("Exit createNewOrUpdateDimension() that propertiesJsonNode is null");
            return null;
        }

        // convert jsonNode to properties map
        convertJsonNodeToPropertiesMap(propertiesJsonNode, properties, uniqueKey);

        // create or update fact
        String typeName = getTypeName(jsonNode);
        DimensionManager manager = new DimensionManager(ids);
        Dimension dimension;

        if(!ids.hasDimensionType(typeName)) {
            ids.addDimensionType(typeName);
        }

        if (overwrite && uniqueKey.size() > 0) {
            dimension = getDimensionWithUniqueKeys(ids, typeName, uniqueKey);
            if (dimension != null) {
                return manager.updateDimension(dimension, properties);
            }
        }

        dimension = manager.createDimension(typeName, properties);

        logger.info("Exit createNewOrUpdateDimension()...");

        return dimension;
    }

    public void convertJsonNodeToPropertiesMap(JsonNode propertiesJsonNode, Map<String, Object>
            properties, Map<String, Object> uniqueKey) {
        for (JsonNode propertyNode : propertiesJsonNode) {
            String propertyName = propertyNode.get(JsonConstants.JSON_PROPERTY_NAME).asText();
            String propertyType = propertyNode.get(JsonConstants.JSON_PROPERTY_TYPE).asText();

            Object propertyValue = JsonNodeUtil.getPropertyValue(propertyType, propertyNode);
            if (propertyValue != null) {
                properties.put(propertyName, propertyValue);
            }

            JsonNode key = propertyNode.get(SolutionConstants.JSON_IS_UNIQUE_KEY);
            if (key != null) {
                uniqueKey.put(propertyName, propertyValue);
            }
        }
    }

    private String getTypeName(JsonNode jsonNode) {
        return jsonNode.get(SolutionConstants.JSON_TYPE_NAME).asText();
    }

    public Fact getFactWithUniqueKeys(InfoDiscoverSpace ids, String factType, Map<String, Object>
            uniqueKeys) {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(factType);

        Set<String> keySet = uniqueKeys.keySet();
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            String key = it.next();
            Object value = uniqueKeys.get(key);
            ep.addFilteringItem(new EqualFilteringItem(key, value), ExploreParameters
                    .FilteringLogic.AND);
        }

        return QueryExecutor.executeFactQuery(ids.getInformationExplorer(), ep);
    }

    public Dimension getDimensionWithUniqueKeys(InfoDiscoverSpace ids, String dimensionType, Map<String, Object>
            uniqueKeys) {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(dimensionType);

        Set<String> keySet = uniqueKeys.keySet();
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            String key = it.next();
            Object value = uniqueKeys.get(key);
            ep.addFilteringItem(new EqualFilteringItem(key, value), ExploreParameters
                    .FilteringLogic.AND);
        }

        return QueryExecutor.executeDimensionQuery(ids.getInformationExplorer(), ep);
    }

}
