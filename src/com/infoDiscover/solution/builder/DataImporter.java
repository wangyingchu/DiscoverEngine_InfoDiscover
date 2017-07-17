package com.infoDiscover.solution.builder;

import com.infoDiscover.common.dimension.time.DayDimensionManager;
import com.infoDiscover.common.dimension.time.dimension.DayDimensionVO;
import com.infoDiscover.common.util.DataTypeChecker;
import com.infoDiscover.common.util.StringUtil;
import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.Property;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.*;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.builder.vo.RelationMappingVO;
import com.infoDiscover.solution.common.database.DatabaseConnection;
import com.infoDiscover.solution.common.dimension.DimensionManager;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.common.fact.FactManager;
import com.infoDiscover.solution.common.relationship.RelationshipManager;
import com.infoDiscover.solution.common.util.JsonNodeUtil;
import com.infoDiscover.solution.common.util.PrefixSetting;
import com.infoDiscover.solution.construction.supervision.constants.JsonConstants;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by sun.
 */
public class DataImporter {
    private final static Logger logger = LoggerFactory.getLogger(DataImporter.class);

    private String spaceName;
    private String prefix;

    public DataImporter(String spaceName, String prefix) {
        this.spaceName = spaceName;
        this.prefix = prefix;
    }

    public void importData(String dataJson, boolean overwrite) throws Exception {

        logger.info("Start to importData with data: {}", dataJson);

        InfoDiscoverSpace ids = DatabaseConnection.connectToSpace(spaceName);

        JsonNode dataJsonNode = JsonNodeUtil.getDataNode(dataJson);
        if (dataJson != null) {
            for (JsonNode jsonNode : dataJsonNode) {
                // create or update fact
                Fact fact = createRelationable(ids, jsonNode, overwrite);

                // link relations
                if (fact != null) {
                    linkBetweenNodes(ids, fact);
                }
            }
        }

        ids.closeSpace();
        logger.info("Exit to importData()...");
    }

    public Fact createRelationable(InfoDiscoverSpace ids, JsonNode jsonNode, boolean override)
            throws
            Exception {
        String type = jsonNode.get(SolutionConstants.JSON_TYPE).asText();

        if (SolutionConstants.FACT_TYPE.equalsIgnoreCase(type)) {
            return createNewOrUpdateFact(ids, jsonNode, override);
        } else {
            throw new Exception("Wrong type, it should be: " + SolutionConstants.FACT_TYPE);
        }
    }

    private Fact createNewOrUpdateFact(InfoDiscoverSpace ids, JsonNode jsonNode, boolean overwrite)
            throws
            InfoDiscoveryEngineRuntimeException {
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

    private void linkBetweenNodes(InfoDiscoverSpace ids, Fact fact) throws Exception {
        logger.info("Enter linkBetweenNodes with factType: {}", fact.getType());

        String rid = fact.getId();
        String factType = fact.getType();
        SolutionTemplateParser parser = new SolutionTemplateParser(spaceName, prefix);

        // fact to dimension mapping
        linkFactToDimension(ids, parser, fact);

        // fact to fact mapping
        linkFactToFact(ids, parser, rid, factType);

        // fact to date dimension
        linkFactToDateDimension(ids, parser, rid, factType);

        // link dimensions
        linkDimensions(ids, parser, fact);

        logger.info("Exit addRelation()...");
    }

    private void linkRelation(InfoDiscoverSpace ids, Fact fact, RelationMappingVO vo, String
            mappingType)
            throws Exception {

        String fromType = PrefixSetting.getFactTypeWithPrefix(prefix, vo.getSourceDataTypeName());
        String fromProperty = vo.getSourceDataPropertyName();
        String toType = PrefixSetting.getFactTypeWithPrefix(prefix, vo.getTargetDataPropertyType());
        String toProperty = vo.getTargetDataPropertyName();
        String relationType = PrefixSetting.getFactTypeWithPrefix(prefix, vo
                .getRelationTypeName());
        String propertyType = vo.getPropertyType();

        Property property = fact.getProperty(fromProperty);
        if (property != null) {

            DimensionManager dimensionManager = new DimensionManager(ids);
            RelationshipManager relationshipManager = new RelationshipManager(ids);

            Object propertyValue = property.getPropertyValue();

            if (mappingType.equalsIgnoreCase(SolutionConstants.JSON_FACT_TO_DIMENSION_MAPPING)) {

                Dimension dimension = null;
                if (DataTypeChecker.isStringType(propertyType)) {
                    dimension = QueryExecutor.executeDimensionQuery(
                            ids.getInformationExplorer(),
                            constructEqualExploreParameters(toType, toProperty, propertyValue));
                } else if (DataTypeChecker.isNumericType(propertyType)) {

                    if (toProperty.split(",").length == 1) {
                        dimension = QueryExecutor.executeDimensionQuery(ids
                                        .getInformationExplorer(),
                                constructEqualExploreParameters(toType, toProperty, propertyValue));
                    } else {
                        String firstProperty = "";
                        String secondProperty = "";

                        // if firstProperty == secondProperty, then use "="
                        if (toProperty.split(",").length == 2) {
                            firstProperty = StringUtil.removeFirstAndLastChar(toProperty)
                                    .split(",")[0];
                            secondProperty = StringUtil.removeFirstAndLastChar(toProperty)
                                    .split(",")[1];
                            if (firstProperty.equalsIgnoreCase(secondProperty)) {
                                dimension = QueryExecutor.executeDimensionQuery(
                                        ids.getInformationExplorer(),
                                        constructEqualExploreParameters(toType, firstProperty,
                                                propertyValue));
                            }
                        } else {
                            boolean validValue = validatePropertyType(toProperty);
                            if (validValue) {
                                String firstOperator = getOperator(toProperty.charAt(0));
                                String secondOperator = getOperator(toProperty.charAt(
                                        toProperty.length() - 1));

                                ExploreParameters ep = new ExploreParameters();
                                ep.setType(toType);

                                if (firstOperator == ">") {
                                    ep.setDefaultFilteringItem(new GreaterThanFilteringItem
                                            (firstProperty, propertyValue));
                                } else if (firstOperator == ">=") {
                                    ep.setDefaultFilteringItem(new GreaterThanEqualFilteringItem
                                            (firstProperty, propertyValue));
                                }

                                if (secondOperator == "<") {
                                    ep.addFilteringItem(new LessThanFilteringItem
                                            (secondProperty, propertyValue), ExploreParameters
                                            .FilteringLogic.AND);
                                } else if (secondOperator == "<=") {
                                    ep.addFilteringItem(new LessThanEqualFilteringItem
                                            (secondProperty, propertyValue), ExploreParameters
                                            .FilteringLogic.AND);
                                }

                                dimension = QueryExecutor.executeDimensionQuery(ids
                                        .getInformationExplorer(), ep);

                            }
                        }
                    }

                } else if (DataTypeChecker.isDateType(propertyType)) {
                    DayDimensionVO dayDimensionVO = DayDimensionManager.getDayDimensionVO
                            (prefix, (Date) propertyValue);
                    relationshipManager.linkFactToDateDimension(prefix, fact, dayDimensionVO,
                            relationType);
                } else {
                    String error = "Invalid property type: " + propertyType;
                    logger.error(error);
                    throw new Exception(error);
                }

                if (dimension == null) {
                    if (DataTypeChecker.isStringType(propertyType)) {
                        Map<String, Object> props = new HashMap<>();
                        props.put(toProperty, propertyValue);
                        dimension = dimensionManager.createDimension(toType, props);
                    }
                }

                if (dimension != null) {
                    relationshipManager.linkFactToDimension(fact, dimension, relationType);
                }

            } else if (mappingType.equalsIgnoreCase(SolutionConstants.JSON_FACT_TO_FACT_MAPPING)) {

                // check which is the fromFact, which is the toFact
                ExploreParameters ep = constructEqualExploreParameters(toType, toProperty,
                        propertyValue);

                if (fact.getType().equalsIgnoreCase(toType)) {
                    // the passing fact is a toFact, to get the fromFact
                    ep = constructEqualExploreParameters(fromType, fromProperty, propertyValue);
                }

                Fact targetFact = QueryExecutor.executeFactQuery(ids.getInformationExplorer(), ep);

                if (targetFact != null) {
                    if (fact.getType().equalsIgnoreCase(toType)) {
                        relationshipManager.linkFactsByRelationType(targetFact, fact, relationType);
                    } else {
                        relationshipManager.linkFactsByRelationType(fact, targetFact, relationType);
                    }
                }

            } else if (mappingType.equalsIgnoreCase(SolutionConstants
                    .JSON_FACT_TO_DATE_DIMENSION_MAPPING)) {
                DayDimensionVO dayDimensionVO = DayDimensionManager.getDayDimensionVO
                        (prefix, (Date) propertyValue);
                relationshipManager.linkFactToDateDimension(prefix, fact, dayDimensionVO,
                        relationType);
            }

        }
    }

    private boolean validatePropertyType(String value) throws Exception {
        String[] types = value.split(",");
        if (types.length == 1) {
            return true;
        }

        if (types.length > 2 || types.length < 1) {
            throw new Exception("Defined dimension property types's number should be 2.");
        }

        char firstChar = value.charAt(0);
        if (firstChar != '[' && firstChar != '(') {
            throw new Exception("Defined dimension property type is not correct, should be start " +
                    "with [ or (");
        }

        char lastChar = value.charAt(value.length() - 1);
        if (lastChar != ']' && lastChar != ')') {
            throw new Exception("Defined dimension property type is not correct, should be end " +
                    "with ] or )");
        }

        return true;

    }

    private String getOperator(char ch) {
        if (ch == '[') {
            return ">=";
        }

        if (ch == '(') {
            return ">";
        }

        if (ch == ')') {
            return "<";
        }

        if (ch == ']') {
            return ">=";
        }

        return "=";
    }


    private ExploreParameters constructEqualExploreParameters(String factType, String key, Object
            value) {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(factType);
        ep.setDefaultFilteringItem(new EqualFilteringItem(key, value));

        return ep;
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
        return PrefixSetting.normalizePrefix(prefix) + jsonNode.get(SolutionConstants
                .JSON_TYPE_NAME).asText();
    }

    private void linkFactToDimension(InfoDiscoverSpace ids, SolutionTemplateParser parser, Fact
            fact) throws
            Exception {

        Map<String, List<RelationMappingVO>> factToDimensionMap = parser.getFactToDimensionMap();
        List<RelationMappingVO> voList = parser.getRelationMappingVOList(PrefixSetting
                .removePrefix(prefix, fact.getType()), factToDimensionMap);

        if (voList != null) {
            for (RelationMappingVO vo : voList) {
                linkRelation(ids, fact, vo, SolutionConstants.JSON_FACT_TO_DIMENSION_MAPPING);
            }
        }
    }

    private void linkFactToFact(InfoDiscoverSpace ids, SolutionTemplateParser parser, String rid,
                                String factType)
            throws Exception {

        Map<String, List<RelationMappingVO>> factToFactMap = parser.getFactToFactMap();
        List<RelationMappingVO> voList = new ArrayList<>();

        Set<String> keySet = factToFactMap.keySet();
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            voList.addAll(factToFactMap.get(it.next()));
        }

        if (voList != null) {

            // retrieve fact again as its version is updated
            Fact latestFact = new FactManager(ids).getFactByRID(rid, factType);

            for (RelationMappingVO vo : voList) {
                linkRelation(ids, latestFact, vo, SolutionConstants.JSON_FACT_TO_FACT_MAPPING);
            }
        }
    }

    private void linkFactToDateDimension(InfoDiscoverSpace ids, SolutionTemplateParser parser,
                                         String rid, String
                                                 factType) throws Exception {
        Map<String, List<RelationMappingVO>> factToDateDimensionMap = parser
                .getFactToDateDimension();

        List<RelationMappingVO> voList = parser.getRelationMappingVOList(
                PrefixSetting.removePrefix(prefix, factType), factToDateDimensionMap);

        if (voList != null) {
            // retrieve fact again as its version is updated
            Fact latestFact = new FactManager(ids).getFactByRID(rid, factType);

            for (RelationMappingVO vo : voList) {
                linkRelation(ids, latestFact, vo, SolutionConstants
                        .JSON_FACT_TO_DATE_DIMENSION_MAPPING);
            }
        }
    }

    private void linkDimensions(InfoDiscoverSpace ids, SolutionTemplateParser parser, Fact fact) {
        Map<String, List<RelationMappingVO>> dimensionToDimensionMap = parser
                .getDimensionToDimensionMap();

        List<String> propertyNamesList = fact.getPropertyNames();

        Set<String> keySet = dimensionToDimensionMap.keySet();
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            String fromDimensionType = it.next();
            List<RelationMappingVO> list = dimensionToDimensionMap.get(fromDimensionType);
            for (RelationMappingVO vo : list) {
                String fromProperty = vo.getSourceDataPropertyName();
                String toProperty = vo.getTargetDataPropertyName();

                if (propertyNamesList.contains(fromProperty) && propertyNamesList.contains
                        (toProperty)) {
                    Object fromPropertyValue = fact.getProperty(fromProperty).getPropertyValue();
                    Object toPropertyValue = fact.getProperty(toProperty).getPropertyValue();

                    linkDimensions(ids, vo, fromPropertyValue, toPropertyValue);
                }
            }
        }


    }

    private void linkDimensions(InfoDiscoverSpace ids, RelationMappingVO vo,
                                Object fromPropertyValue, Object toPropertyValue) {

        String fromDimensionType = PrefixSetting.getFactTypeWithPrefix(prefix, vo.getSourceDataTypeName());
        String toDimensionType = PrefixSetting.getFactTypeWithPrefix(prefix, vo.getTargetDataPropertyType());
        String relationType = PrefixSetting.getFactTypeWithPrefix(prefix, vo.getRelationTypeName());

        // check if the dimension type is existed
        try {
            if (!ids.hasDimensionType(fromDimensionType)) {
                ids.addDimensionType(fromDimensionType);
            }

            if (!ids.hasDimensionType(toDimensionType)) {
                ids.addDimensionType(toDimensionType);
            }

        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.error("Failed to create dimension/relation type: {}", e.getMessage());
        }

        ExploreParameters fromEp = constructEqualExploreParameters(fromDimensionType, vo
                .getSourcePrimaryKey(), fromPropertyValue);
        Dimension fromDimension = QueryExecutor.executeDimensionQuery(ids.getInformationExplorer
                (), fromEp);

        DimensionManager manager = new DimensionManager(ids);
        if (fromDimension == null) {
            Map<String, Object> map = new HashMap<>();
            map.put(vo.getSourcePrimaryKey(), fromPropertyValue);
            fromDimension = manager.createDimension(fromDimensionType, map);
        }

        ExploreParameters toEp = constructEqualExploreParameters(toDimensionType, vo
                .getTargetPrimaryKey(), toPropertyValue);
        Dimension toDimension = QueryExecutor.executeDimensionQuery(ids.getInformationExplorer(),
                toEp);
        if (toDimension == null) {
            Map<String, Object> map = new HashMap<>();
            map.put(vo.getTargetPrimaryKey(), toPropertyValue);
            toDimension = manager.createDimension(toDimensionType, map);
        }

        //
        RelationshipManager relationshipManager = new RelationshipManager(ids);
        if (!relationshipManager.isDirectlyLinked(fromDimension, toDimension, relationType)) {
            relationshipManager.linkDimensionsByRelationType(fromDimension, toDimension,
                    relationType);
        }
    }
}