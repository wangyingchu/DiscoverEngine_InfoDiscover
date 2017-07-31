package com.infoDiscover.solution.template;

import com.info.discover.ruleengine.solution.SolutionRelationMapping;
import com.info.discover.ruleengine.solution.pojo.DataDuplicateCopyMappingVO;
import com.infoDiscover.infoDiscoverEngine.dataMart.*;
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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by sun on 7/23/17.
 */
public class DataImporter {
    private final static Logger logger = LoggerFactory.getLogger(DataImporter.class);

    private String spaceName;

    public DataImporter(String spaceName) {
        this.spaceName = spaceName;
    }

    public void importData(String dataJson, boolean overwrite) throws Exception {

        logger.info("Start to importData with data: {}", dataJson);

        // get relation mappings
        new SolutionRelationMapping().getSolutionRelationMappings();
//        new RelationMapping().getRelationMappings();

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

    private Relationable createRelationable(InfoDiscoverSpace ids, JsonNode jsonNode, boolean override)
            throws Exception {
        String type = jsonNode.get(SolutionConstants.JSON_TYPE).asText();

        return createRelationable(ids, jsonNode, override, true);
    }

    private Relationable createRelationable(InfoDiscoverSpace ids, JsonNode jsonNode, boolean override, boolean ignoreNotMappingProperties)
            throws
            Exception {
        String type = jsonNode.get(SolutionConstants.JSON_TYPE).asText();

        if (SolutionConstants.FACT_TYPE.equalsIgnoreCase(type)) {
            return createNewOrUpdateFact(ids, jsonNode, override, ignoreNotMappingProperties);
        } else if (SolutionConstants.DIMENSION_TYPE.equalsIgnoreCase(type)) {
            return createNewOrUpdateDimension(ids, jsonNode, override, ignoreNotMappingProperties);
        } else {
            throw new Exception("Wrong type, it should be: " + SolutionConstants.FACT_TYPE);
        }
    }

    private Fact createNewOrUpdateFact(InfoDiscoverSpace ids, JsonNode jsonNode, boolean overwrite, boolean ignoreNotMappingProperties)
            throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineDataMartException {
        logger.info("Start to createNewOrUpdateFact with jsonNode: {} and overwrite is: {}",
                jsonNode, overwrite);

        if (jsonNode == null) {
            logger.warn("Exit createNewOrUpdateFact() that jsonNode is null");
            return null;
        }

        String typeName = getTypeName(jsonNode);
        if (!ids.hasFactType(typeName)) {
//            ids.addFactType(typeName);
            logger.info("Fact type: {} is not existed and does not create it.", typeName);
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

        if (!ignoreNotMappingProperties) {
            logger.info("All the properties type should map to the defined properties of the fact: {}", typeName);
            convertJsonNodeToPropertiesMap(propertiesJsonNode, properties, uniqueKey,null, false);
        } else {
            logger.info("Ignore the properties that property type is not mapping to the defined properties of the fact: {}", typeName);
            FactType factType = ids.getFactType(typeName);
            List<TypeProperty> typeProperties = factType.getTypeProperties();
            convertJsonNodeToPropertiesMap(propertiesJsonNode, properties, uniqueKey, typeProperties, true);
        }

        FactManager manager = new FactManager(ids);

        // create or update fact
        Fact fact;

        if (overwrite && uniqueKey.size() > 0) {
            fact = getFactWithUniqueKeys(ids, typeName, uniqueKey);
            if (fact != null) {
                fact = manager.updateFact(fact, properties);
            } else {
                fact = manager.createFact(typeName, properties);
            }
        } else {
            fact = manager.createFact(typeName, properties);
        }

        // copy duplicate properties
//        Map<String, List<DataDuplicateCopyMappingVO>> map = RelationMapping.factDuplicatedCopyMap;
        Map<String, List<DataDuplicateCopyMappingVO>> map = new SolutionRelationMapping().getFactDuplicatedCopyMap();
        List<DataDuplicateCopyMappingVO> factDuplicateCopyList = null;

        // if no duplicate copy mapping
        if (MapUtils.isEmpty(map)) {
            return fact;
        }

        // if has duplicate copy mapping
        factDuplicateCopyList = map.get(SolutionConstants.JSON_FACT_DUPLICATE_COPY_MAPPING);

        // if fact duplicate copy mapping is empty
        if (CollectionUtils.isEmpty(factDuplicateCopyList)) {
            return fact;
        }

        List<DataDuplicateCopyMappingVO> sourceToTargetList = new ArrayList<>();
        for (DataDuplicateCopyMappingVO vo : factDuplicateCopyList) {
            if (vo.getSourceDataTypeName().equalsIgnoreCase(typeName)) {
                sourceToTargetList.add(vo);
            }
        }

        List<DataDuplicateCopyMappingVO> targetToSourceList = new ArrayList<>();
        for (DataDuplicateCopyMappingVO vo : factDuplicateCopyList) {
            if (vo.getTargetDataTypeName().equalsIgnoreCase(typeName)) {
                targetToSourceList.add(vo);
            }
        }

        if (CollectionUtils.isEmpty(sourceToTargetList) && CollectionUtils.isEmpty(targetToSourceList)) {
            return fact;
        }

        // copy properties from source to target
        if (CollectionUtils.isNotEmpty(sourceToTargetList)) {
            copyPropertiesFromInputToTargetFact(ids, properties, sourceToTargetList, jsonNode);
        }

        // copy properties from target to source
        if (CollectionUtils.isNotEmpty(targetToSourceList)) {
            copyPropertiesFromSourceFactToInput(ids, fact, targetToSourceList);
        }

        logger.info("Exit createNewOrUpdateFact()...");

        return fact;
    }

    private Dimension createNewOrUpdateDimension(InfoDiscoverSpace ids, JsonNode jsonNode, boolean overwrite, boolean ignoreNotMappingProperties)
            throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineDataMartException {
        logger.info("Start to createNewOrUpdateDimension with jsonNode: {} and overwrite is: {}",
                jsonNode, overwrite);

        if (jsonNode == null) {
            logger.warn("Exit createNewOrUpdateDimension() that jsonNode is null");
            return null;
        }

        // create or update dimension
        String typeName = getTypeName(jsonNode);
        if (!ids.hasDimensionType(typeName)) {
//            ids.addDimensionType(typeName);
            logger.info("Dimension type: {} is not existed and does not create it.", typeName);
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
        if (!ignoreNotMappingProperties) {
            logger.info("All the properties type should map to the defined properties of the fact: {}", typeName);
            convertJsonNodeToPropertiesMap(propertiesJsonNode, properties, uniqueKey,null, false);
        } else {
            logger.info("Ignore the properties that property type is not mapping to the defined properties of the fact: {}", typeName);
            FactType factType = ids.getFactType(typeName);
            List<TypeProperty> typeProperties = factType.getTypeProperties();
            convertJsonNodeToPropertiesMap(propertiesJsonNode, properties, uniqueKey, typeProperties, true);
        }

        DimensionManager manager = new DimensionManager(ids);
        Dimension dimension;

        if (overwrite && uniqueKey.size() > 0) {
            dimension = getDimensionWithUniqueKeys(ids, typeName, uniqueKey);
            if (dimension != null) {
                dimension = manager.updateDimension(dimension, properties);
            } else {
                dimension = manager.createDimension(typeName, properties);
            }
        } else {
            dimension = manager.createDimension(typeName, properties);
        }

        // copy duplicate properties
//        Map<String, List<DataDuplicateCopyMappingVO>> map = RelationMapping.factDuplicatedCopyMap;
        Map<String, List<DataDuplicateCopyMappingVO>> map = new SolutionRelationMapping().getDimensionDuplicatedCopyMap();
        List<DataDuplicateCopyMappingVO> dimensionDuplicateCopyList = null;

        // if no duplicate copy mapping
        if (MapUtils.isEmpty(map)) {
            return dimension;
        }

        // if has duplicate copy mapping
        dimensionDuplicateCopyList = map.get(SolutionConstants.JSON_DIMENSION_DUPLICATE_COPY_MAPPING);

        // if dimension duplicate copy mapping is empty
        if (CollectionUtils.isEmpty(dimensionDuplicateCopyList)) {
            return dimension;
        }

        List<DataDuplicateCopyMappingVO> sourceToTargetList = new ArrayList<>();
        for (DataDuplicateCopyMappingVO vo : dimensionDuplicateCopyList) {
            if (vo.getSourceDataTypeName().equalsIgnoreCase(typeName)) {
                sourceToTargetList.add(vo);
            }
        }

        if (CollectionUtils.isEmpty(sourceToTargetList)) {
            return dimension;
        }

        // copy properties to target fact
        copyPropertiesFromInputToTargetFact(ids, properties, sourceToTargetList, jsonNode);

        logger.info("Exit createNewOrUpdateDimension()...");

        return dimension;
    }

    private void copyPropertiesFromInputToTargetFact(InfoDiscoverSpace ids, Map<String, Object> inputProperties, List<DataDuplicateCopyMappingVO> sourceToTargetList,
                                                     JsonNode jsonNode) throws InfoDiscoveryEngineRuntimeException {

        Set<String> keySet = inputProperties.keySet();
        Iterator<String> inputIteratorKeys = keySet.iterator();

        // copy properties from source to target fact
        for (DataDuplicateCopyMappingVO vo : sourceToTargetList) {
            String targetDataTypeName = vo.getTargetDataTypeName();
            String targetDataPropertyName = vo.getTargetDataPropertyName();
            String targetDataPropertyType = vo.getTargetDataPropertyType();

            JsonNode propertiesJsonNode = JsonNodeUtil.getPropertiesJsonNode(jsonNode);
            JsonNode sourceDataPropertyValueJsonNode = null;
            boolean found = false;
            for (JsonNode pJsonNode : propertiesJsonNode) {
                if (!found && pJsonNode.get("propertyName").asText().equalsIgnoreCase(vo.getSourceDataPropertyName())) {
                    found = true;
                    sourceDataPropertyValueJsonNode = getPropertyValue(pJsonNode, "propertyValue");
                }
            }

            Object sourceDataPropertyValue = null;
            if (targetDataPropertyType.equalsIgnoreCase("String")) {
                sourceDataPropertyValue = sourceDataPropertyValueJsonNode.asText();
            } else if (targetDataPropertyType.equalsIgnoreCase("Int")) {
                sourceDataPropertyValue = sourceDataPropertyValueJsonNode.asInt();
            } else if (targetDataPropertyType.equalsIgnoreCase("float")) {
                sourceDataPropertyValue = sourceDataPropertyValueJsonNode.asDouble();
            } else if (targetDataPropertyType.equalsIgnoreCase("Double")) {
                sourceDataPropertyValue = sourceDataPropertyValueJsonNode.asDouble();
            } else if (targetDataPropertyType.equalsIgnoreCase("Long")) {
                sourceDataPropertyValue = sourceDataPropertyValueJsonNode.asLong();
            } else if (targetDataPropertyType.equalsIgnoreCase("Boolean")
                    || targetDataPropertyType.equalsIgnoreCase("Bool")) {
                sourceDataPropertyValue = sourceDataPropertyValueJsonNode.asBoolean();
            } else if (targetDataPropertyType.equalsIgnoreCase("Date")
                    || targetDataPropertyType.equalsIgnoreCase("DateTime")) {
                sourceDataPropertyValue = new Date(sourceDataPropertyValueJsonNode.asLong());
            }

            // if target is not existed, does to create target and copy properties
            if (!ids.hasFactType(targetDataTypeName)) {
                logger.debug("target type {} is not existed", targetDataTypeName);
                continue;
            }

            String fullTargetTypeName = "ID_FACT_" + targetDataTypeName;

            String sql = "select * from " + fullTargetTypeName + " where " + targetDataPropertyName + " = ";
            if (targetDataPropertyType.equalsIgnoreCase("String")) {
                sql += "'" + sourceDataPropertyValue + "'";
            } else {
                sql += sourceDataPropertyValue;
            }

            List<Relationable> targetList = QueryExecutor.getManyRelationables(ids.getInformationExplorer(), sql);
            if (CollectionUtils.isNotEmpty(targetList)) {
                for (Relationable targetFact : targetList) {
                    List<Property> propertyList = targetFact.getProperties();
                    Map<String, Object> targetPropertiesMap = new HashMap<>();
                    for (Property property : propertyList) {
                        targetPropertiesMap.put(property.getPropertyName(), property.getPropertyValue());
                    }

                    if (vo.getExistingPropertyHandleMethod().equalsIgnoreCase("Replace")) {
                        while (inputIteratorKeys.hasNext()) {
                            String key = inputIteratorKeys.next();
                            Object value = inputProperties.get(key);
                            targetPropertiesMap.put(key, value);
                        }

                        // remove the source primary key
                        targetPropertiesMap.remove(vo.getSourceDataPropertyName());

                    } else {
                        while (inputIteratorKeys.hasNext()) {
                            String key = inputIteratorKeys.next();
                            Object value = inputProperties.get(key);
                            if (!targetPropertiesMap.containsKey(key)) {
                                targetPropertiesMap.put(key, value);
                            }
                        }
                    }

                    // update target fact
                    new FactManager(ids).updateFact((Fact) targetFact, targetPropertiesMap);
                }
            }
        }

    }


    public void copyPropertiesFromSourceFactToInput(InfoDiscoverSpace ids, Fact targetFact,
                                                    List<DataDuplicateCopyMappingVO> targetToSourceList
    ) throws InfoDiscoveryEngineRuntimeException {

        // copy properties from source fact to input fact
        for (DataDuplicateCopyMappingVO vo : targetToSourceList) {
            String sourceDataTypeName = vo.getSourceDataTypeName();
            String sourceDataPropertyName = vo.getSourceDataPropertyName();
            String sourceDataPropertyType = vo.getSourceDataPropertyType();

            String targetDataPropertyName = vo.getTargetDataPropertyName();

            Object targetDataPropertyValue = targetFact.getProperty(targetDataPropertyName).getPropertyValue();

            // if source is not existed, does to create source and copy properties
            if (!ids.hasFactType(sourceDataTypeName)) {
                logger.debug("target type {} is not existed", sourceDataTypeName);
                continue;
            }

            String fullSourceTypeName = "ID_FACT_" + sourceDataTypeName;

            String sql = "select * from " + fullSourceTypeName + " where " + sourceDataPropertyName + " = ";
            if (sourceDataPropertyType.equalsIgnoreCase("String")) {
                sql += "'" + targetDataPropertyValue.toString() + "'";
            } else {
                sql += targetDataPropertyValue;
            }

            List<Relationable> sourceFactList = QueryExecutor.getManyRelationables(ids.getInformationExplorer(), sql);
            if (CollectionUtils.isNotEmpty(sourceFactList)) {
                List<Property> targetPropertiesList = targetFact.getProperties();
                Map<String, Object> targetPropertiesMap = new HashMap<>();
                for (Property property : targetPropertiesList) {
                    targetPropertiesMap.put(property.getPropertyName(), property.getPropertyValue());
                }

                for (Relationable sourceFact : sourceFactList) {

                    List<Property> sourceFactProperties = sourceFact.getProperties();

                    if (vo.getExistingPropertyHandleMethod().equalsIgnoreCase("Replace")) {
                        for (Property property : sourceFactProperties) {
                            targetPropertiesMap.put(property.getPropertyName(), property.getPropertyValue());
                        }

                        // remove the source primary key
                        targetPropertiesMap.remove(vo.getSourceDataPropertyName());

                    } else {
                        for (Property property: sourceFactProperties) {
                            if (!targetPropertiesMap.containsKey(property.getPropertyName())) {
                                targetPropertiesMap.put(property.getPropertyName(), property.getPropertyValue());
                            }
                        }
                    }

                    // update target fact
                    new FactManager(ids).updateFact(targetFact, targetPropertiesMap);
                }
            }
        }

    }

    public void convertJsonNodeToPropertiesMap(JsonNode propertiesJsonNode, Map<String, Object>
            properties, Map<String, Object> uniqueKey, List<TypeProperty> typeProperties, boolean ignoreNotMappingProperties) {

        if (ignoreNotMappingProperties) {
            if (CollectionUtils.isEmpty(typeProperties)) {
                return;
            }

            Iterator<TypeProperty> it = typeProperties.iterator();
            Map<String, String> definedTypeProperties = new HashMap<>();
            while (it.hasNext()) {
                TypeProperty typeProperty = it.next();
                String propertyName = typeProperty.getPropertyName();
                String propertyType = typeProperty.getPropertyType().toString();
                definedTypeProperties.put(propertyName, propertyType);
            }

            for (JsonNode propertyNode: propertiesJsonNode) {
                String propertyName = propertyNode.get(JsonConstants.JSON_PROPERTY_NAME).asText();
                String propertyType = propertyNode.get(JsonConstants.JSON_PROPERTY_TYPE).asText();
                Object propertyValue = JsonNodeUtil.getPropertyValue(propertyType, propertyNode);

                if(definedTypeProperties.containsKey(propertyName)) {
                    if(propertyType.equalsIgnoreCase(definedTypeProperties.get(propertyName))) {
                        if (propertyValue != null) {
                            properties.put(propertyName, propertyValue);
                        }
                    }
                } else {
                    if (propertyValue != null) {
                        properties.put(propertyName, propertyValue);
                    }
                }

                JsonNode key = propertyNode.get(SolutionConstants.JSON_IS_UNIQUE_KEY);
                if (key != null) {
                    uniqueKey.put(propertyName, propertyValue);
                }
            }

        } else {
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
    }

    private String getTypeName(JsonNode jsonNode) {
        return jsonNode.get(SolutionConstants.JSON_TYPE_NAME).asText();
    }

    private JsonNode getPropertyValue(JsonNode jsonNode, String propertyName) {
        return jsonNode.get(propertyName);
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
