package com.infoDiscover.solution.template.utils;

import com.info.discover.ruleengine.solution.pojo.DataDuplicateCopyMappingVO;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.Property;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relationable;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.common.fact.FactManager;
import com.infoDiscover.solution.common.util.JsonNodeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by sun on 11/4/17.
 */
public class PropertyCopyUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public long copyPropertiesFromInputToTargetFact(InfoDiscoverSpace ids,
                                                    Map<String, Object> inputProperties,
                                                    List<DataDuplicateCopyMappingVO> sourceToTargetList,
                                                    JsonNode jsonNode) throws InfoDiscoveryEngineRuntimeException {

        FactManager factManager = new FactManager(ids);

        // records how many records are executed by the rule
        long changedRecords = 0;

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
            } else if (targetDataPropertyType.equalsIgnoreCase("Int")
                    || targetDataPropertyType.equalsIgnoreCase("Short")) {
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

                    Set<String> keySet = inputProperties.keySet();
                    Iterator<String> inputIteratorKeys = keySet.iterator();

                    if (vo.getExistingPropertyHandleMethod().equalsIgnoreCase("Replace")) {
                        while (inputIteratorKeys.hasNext()) {
                            String key = inputIteratorKeys.next();
                            Object value = inputProperties.get(key);
                            targetPropertiesMap.put(key, value);
                        }

                        // remove the source primary key
//                        targetPropertiesMap.remove(vo.getSourceDataPropertyName());

                    } else {
                        while (inputIteratorKeys.hasNext()) {
                            String key = inputIteratorKeys.next();
                            Object value = inputProperties.get(key);
                            if (!targetPropertiesMap.containsKey(key)) {
                                targetPropertiesMap.put(key, value);
                            }
                        }
                    }

                    // get the latest target fact as it maybe update in the previous actions
                    targetFact = factManager.getFactByRID(targetFact.getId(), ((Fact) targetFact).getType());
                    // update target fact
                    new FactManager(ids).updateFact((Fact) targetFact, targetPropertiesMap);
                }

                // if targetList is not empty, add changeRecords + 1
                changedRecords++;
            }
        }

        return changedRecords;
    }

    public long copyPropertiesFromSourceFactToInput(InfoDiscoverSpace ids, Fact targetFact,
                                                    List<DataDuplicateCopyMappingVO> targetToSourceList
    ) throws InfoDiscoveryEngineRuntimeException {

        FactManager factManager = new FactManager(ids);

        // records how many records are changed with the rule
        long changedRecords = 0;

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
//                        targetPropertiesMap.remove(vo.getSourceDataPropertyName());

                    } else {
                        for (Property property : sourceFactProperties) {
                            if (!targetPropertiesMap.containsKey(property.getPropertyName())) {
                                targetPropertiesMap.put(property.getPropertyName(), property.getPropertyValue());
                            }
                        }
                    }

                    // get the latest version of the targetFact as it maybe update in the previous update
                    targetFact = factManager.getFactByRID(targetFact.getId(), targetFact.getType());
                    // update target fact
                    factManager.updateFact(targetFact, targetPropertiesMap);
                }
            }

            // if sourceFactList is not empty, add +1 to the changedRecords
            changedRecords ++;
        }

        return changedRecords;
    }

    private JsonNode getPropertyValue(JsonNode jsonNode, String propertyName) {
        return jsonNode.get(propertyName);
    }
}
