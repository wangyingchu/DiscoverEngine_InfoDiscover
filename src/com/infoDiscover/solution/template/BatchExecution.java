package com.infoDiscover.solution.template;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.info.discover.ruleengine.solution.pojo.DataDateMappingVO;
import com.info.discover.ruleengine.solution.pojo.DataDuplicateCopyMappingVO;
import com.info.discover.ruleengine.solution.pojo.RelationMappingVO;
import com.infoDiscover.common.util.DataTypeChecker;
import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.Property;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relationable;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.builder.SolutionConstants;
import com.infoDiscover.solution.common.database.DatabaseConnection;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.common.util.JsonNodeUtil;
import com.infoDiscover.solution.template.utils.RuleMappingDimensionManager;
import com.infoDiscover.solution.template.utils.RuleMappingFactManager;
import com.infoDiscover.solution.template.utils.RuleToPOJOConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * Created by sun on 11/4/17.
 */
public class BatchExecution {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String spaceName;

    public BatchExecution(String spaceName) {
        this.spaceName = spaceName;
    }

    public String batchUpdateWithRule(String rule) throws Exception {
        logger.info("start to execute batchUpdateWithRule with rule {}", rule);
        if (StringUtils.isBlank(rule)) {
            logger.error("rule is empty");
            throw new Exception("Rule should not be empty");
        }

        JsonNode ruleJsonNode = JsonNodeUtil.string2JsonNode(rule);
        String mappingType = ruleJsonNode.get("mappingType").asText();

        String sourceDataTypeKind = ruleJsonNode.get("sourceDataTypeKind").asText();
        String sourceDataTypeName = ruleJsonNode.get("sourceDataTypeName").asText();

        String sql = "select * from " + getTypeName(sourceDataTypeKind, sourceDataTypeName);

        long totalCount = 0l;

        long changedRecords = 0l;

        InfoDiscoverSpace ids = null;
        try {
            ids = DatabaseConnection.connectToSpace(spaceName);
            List<Relationable> list = QueryExecutor.executeFactQuery(ids, sql);
            if (CollectionUtils.isEmpty(list)) {
                logger.warn("no data found in {}", sourceDataTypeName);
                return "0/0";
            }

            totalCount = list.size();

            for (Relationable relationable : list) {

                String rid = relationable.getId();
                logger.info("rid of relationable is {}", rid);

                if (mappingType.equalsIgnoreCase(SolutionConstants.JSON_FACT_DUPLICATE_COPY_MAPPING) ||
                        mappingType.equalsIgnoreCase(SolutionConstants.JSON_DIMENSION_DUPLICATE_COPY_MAPPING)) {
                    logger.info("update with copy duplicate mapping rule");
                    long changed = updateWithDuplicateMapping(ids, ruleJsonNode, mappingType, relationable, sourceDataTypeKind, sourceDataTypeName);
                    changedRecords += changed;
                } else {

                    RelationMappingOperator operator = new RelationMappingOperator();

                    if (mappingType.equalsIgnoreCase(SolutionConstants.JSON_FACT_TO_FACT_MAPPING) ||
                            mappingType.equalsIgnoreCase(SolutionConstants.JSON_FACT_TO_DIMENSION_MAPPING) ||
                            mappingType.equalsIgnoreCase(SolutionConstants.JSON_DIMENSION_TO_FACT_MAPPING) ||
                            mappingType.equalsIgnoreCase(SolutionConstants.JSON_DIMENSION_TO_DIMENSION_MAPPING)) {

                        RelationMappingVO relationMappingVO = RuleToPOJOConverter.toRelationMappingPOJO(ruleJsonNode, mappingType);
                        List<RelationMappingVO> relationMappingVOsList = new ArrayList<>();
                        relationMappingVOsList.add(relationMappingVO);
                        Map<String, List<RelationMappingVO>> relationMappingsMap = new HashMap<>();
                        relationMappingsMap.put(mappingType, relationMappingVOsList);

                        if (SolutionConstants.FACT_TYPE.equalsIgnoreCase(sourceDataTypeKind)) {
                            // link relations
                            changedRecords += operator.linkBetweenNodesFromFact(ids, (Fact) relationable, relationMappingsMap, null);
                        } else if (SolutionConstants.DIMENSION_TYPE.equalsIgnoreCase(sourceDataTypeKind)) {
                            // link relations
                            changedRecords += operator.linkBetweenNodesFromDimension(ids, (Dimension) relationable, relationMappingsMap, null);
                        }

                    } else if (mappingType.equalsIgnoreCase(SolutionConstants.JSON_FACT_TO_DATE_DIMENSION_MAPPING) ||
                            mappingType.equalsIgnoreCase(SolutionConstants.JSON_DIMENSION_TO_DATE_DIMENSION_MAPPING)) {
                        DataDateMappingVO dataDateMappingVO = RuleToPOJOConverter.toDataDateMappingPOJO(ruleJsonNode, mappingType);
                        List<DataDateMappingVO> dataDateMappingVOsList = new ArrayList<>();
                        dataDateMappingVOsList.add(dataDateMappingVO);
                        Map<String, List<DataDateMappingVO>> map = new HashMap<>();
                        map.put(mappingType, dataDateMappingVOsList);

                        // link relations
                        changedRecords += operator.linkToDateDimension(ids, rid, dataDateMappingVOsList, sourceDataTypeName, sourceDataTypeKind.toUpperCase());

                    }
                }


            }
        } catch (Exception e) {
            logger.error("Failed to import data with error: {}", e.getMessage());
        } finally {
            ids.closeSpace();
        }

        logger.info("end to execute batchUpdateWithRule method");
        String changedRowsRate = changedRecords + "/" + totalCount;
        logger.info("changed records rate is {}", changedRowsRate);
        return changedRowsRate;
    }

    private long updateWithDuplicateMapping(InfoDiscoverSpace ids,
                                            JsonNode ruleJsonNode,
                                            String mappingType,
                                            Relationable relationable,
                                            String sourceDataTypeKind,
                                            String sourceDataTypeName)
            throws InfoDiscoveryEngineRuntimeException {
        DataDuplicateCopyMappingVO duplicateCopyMappingVO = RuleToPOJOConverter.toDataDuplicateCopyMappingPOJO(ruleJsonNode, mappingType);
        List<DataDuplicateCopyMappingVO> voList = new ArrayList<>();
        voList.add(duplicateCopyMappingVO);

        JsonNode jsonNode = convertFactToJsonNode(relationable, sourceDataTypeKind, sourceDataTypeName);
        JsonNode propertiesJsonNode = JsonNodeUtil.getPropertiesJsonNode(jsonNode);

        if (propertiesJsonNode == null) {
            logger.warn("Exit copy duplicate properties that propertiesJsonNode is null");
            return 0;
        }

        if ("Fact".equalsIgnoreCase(sourceDataTypeKind)) {
            RuleMappingFactManager ruleMappingFactManager = new RuleMappingFactManager();
            Map<String, Object> uniqueKey = new HashMap<>();
            Map<String, Object> properties = new HashMap<>();
            ruleMappingFactManager.getProperties(ids, propertiesJsonNode, properties, uniqueKey, sourceDataTypeName, true);

            return ruleMappingFactManager.updateFactWithRule(ids, (Fact) relationable, sourceDataTypeName, jsonNode, properties, voList);
        } else {
            RuleMappingDimensionManager ruleMappingDimensionManager = new RuleMappingDimensionManager();
            Map<String, Object> uniqueKey = new HashMap<>();
            Map<String, Object> properties = new HashMap<>();
            ruleMappingDimensionManager.getProperties(ids, propertiesJsonNode, properties, uniqueKey, sourceDataTypeName, true);
            return ruleMappingDimensionManager.updateFactWithRule(ids, sourceDataTypeName, jsonNode, properties, voList);
        }
    }


    private String getTypeName(String sourceDataTypeKind, String sourceDataTypeName) {
        if (sourceDataTypeKind.equalsIgnoreCase("FACT")) {
            return InfoDiscoverEngineConstant.CLASSPERFIX_FACT + sourceDataTypeName;
        } else {
            return InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION + sourceDataTypeName;
        }
    }

    private JsonNode convertFactToJsonNode(Relationable relationable, String sourceDataTypeKind, String sourceDataTypeName) {
        List<Property> propertiesList = relationable.getProperties();

        JsonArray propertiesJsonArray = new JsonArray();
        for (Property property : propertiesList) {
            String name = property.getPropertyName();
            String type = property.getPropertyType().name();
            Object value = property.getPropertyValue();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("propertyName", name);
            jsonObject.addProperty("propertyType", type);
            if (DataTypeChecker.isDateType(type)) {
                jsonObject.addProperty("propertyValue", ((Date) value).getTime());
            } else if (DataTypeChecker.isBooleanType(type)) {
                jsonObject.addProperty("propertyValue", (Boolean) value);
            } else if (DataTypeChecker.isStringType(type)) {
                jsonObject.addProperty("propertyValue", value.toString());
            } else if (DataTypeChecker.isShortType(type)) {
                jsonObject.addProperty("propertyValue", (Short) value);
            } else if (DataTypeChecker.isIntType(type)) {
                jsonObject.addProperty("propertyValue", (Integer) value);
            } else if (DataTypeChecker.isFloatType(type)) {
                jsonObject.addProperty("propertyValue", (Float) value);
            } else if (DataTypeChecker.isDoubleType(type)) {
                jsonObject.addProperty("propertyValue", (Double) value);
            } else if (DataTypeChecker.isLongType(type)) {
                jsonObject.addProperty("propertyValue", (Long) value);
            }

            propertiesJsonArray.add(jsonObject);
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", sourceDataTypeKind);
        jsonObject.addProperty("typeName", sourceDataTypeName);
        jsonObject.add("properties", propertiesJsonArray);

        JsonNode jsonNode = null;
        try {
            jsonNode = JsonNodeUtil.string2JsonNode(jsonObject.toString());
            logger.debug("jsonNode: {}", jsonNode);
        } catch (IOException e) {
            logger.error("Failed to getProperties from fact with error: {}", e.getMessage());
            e.printStackTrace();
        }

        return jsonNode;
    }
}
