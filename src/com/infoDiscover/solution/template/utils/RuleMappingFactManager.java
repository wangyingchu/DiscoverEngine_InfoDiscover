package com.infoDiscover.solution.template.utils;

import com.info.discover.ruleengine.solution.pojo.DataDuplicateCopyMappingVO;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.FactType;
import com.infoDiscover.infoDiscoverEngine.dataMart.TypeProperty;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.builder.SolutionConstants;
import com.infoDiscover.solution.common.fact.FactManager;
import com.infoDiscover.solution.common.util.JsonNodeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sun on 11/4/17.
 */
public class RuleMappingFactManager {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Fact createNewOrUpdateFact(InfoDiscoverSpace ids, JsonNode jsonNode, boolean overwrite,
                                      List<DataDuplicateCopyMappingVO> factDuplicateCopyList,
                                      boolean ignoreNotMappingProperties)
            throws InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineDataMartException {
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

        JsonNode propertiesJsonNode = JsonNodeUtil.getPropertiesJsonNode(jsonNode);
        if (propertiesJsonNode == null) {
            logger.warn("Exit createNewOrUpdateFact() that propertiesJsonNode is null");
            return null;
        }

        Map<String, Object> uniqueKey = new HashMap<>();
        Map<String, Object> properties = new HashMap<>();
        getProperties(ids, jsonNode, properties, uniqueKey, typeName, ignoreNotMappingProperties);

        FactManager manager = new FactManager(ids);

        // create or update fact
        Fact fact;

        if (overwrite && uniqueKey.size() > 0) {
            fact = new DataImportHelper().getFactWithUniqueKeys(ids, typeName, uniqueKey);
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
//        Map<String, List<DataDuplicateCopyMappingVO>> map = new SolutionRelationMapping().getFactDuplicatedCopyMap();
//        List<DataDuplicateCopyMappingVO> factDuplicateCopyList = null;

        // if no duplicate copy mapping
//        if (MapUtils.isEmpty(map)) {
//            return fact;
//        }

        // if has duplicate copy mapping
//        factDuplicateCopyList = map.get(SolutionConstants.JSON_FACT_DUPLICATE_COPY_MAPPING);

        // if fact duplicate copy mapping is empty
        if (CollectionUtils.isEmpty(factDuplicateCopyList)) {
            return fact;
        }

        // update fact with rule
        updateFactWithRule(ids, fact, typeName, jsonNode, properties, factDuplicateCopyList);

        logger.info("Exit createNewOrUpdateFact()...");

        return fact;
    }

    public long updateFactWithRule(InfoDiscoverSpace ids,
                                   Fact fact, String typeName,
                                   JsonNode jsonNode,
                                   Map<String, Object> properties,
                                   List<DataDuplicateCopyMappingVO> factDuplicateCopyList)
            throws InfoDiscoveryEngineRuntimeException {

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
            return 0;
        }

        // copy properties from source to target
        long fromSourceToTargetRecords = 0;
        if (CollectionUtils.isNotEmpty(sourceToTargetList)) {
            fromSourceToTargetRecords = new PropertyCopyUtil().copyPropertiesFromInputToTargetFact(ids, properties, sourceToTargetList, jsonNode);
        }

        // copy properties from target to source
        long fromTargetToSource = 0;
        if (CollectionUtils.isNotEmpty(targetToSourceList)) {
            fromTargetToSource = new PropertyCopyUtil().copyPropertiesFromSourceFactToInput(ids, fact, targetToSourceList);
        }

        return fromSourceToTargetRecords + fromTargetToSource;
    }

    public void getProperties(InfoDiscoverSpace ids,
                              JsonNode propertiesJsonNode,
                              Map<String, Object> properties,
                              Map<String, Object> uniqueKey,
                              String typeName,
                              boolean ignoreNotMappingProperties) {

        if (!ignoreNotMappingProperties) {
            logger.info("All the properties type should map to the defined properties of the fact: {}", typeName);
            JsonNodeToPropertiesMapConverter.convertJsonNodeToPropertiesMap(propertiesJsonNode, properties, uniqueKey, null, false);
        } else {
            logger.info("Ignore the properties that property type is not mapping to the defined properties of the fact: {}", typeName);
            FactType factType = ids.getFactType(typeName);
            List<TypeProperty> typeProperties = factType.getTypeProperties();
            if (CollectionUtils.isEmpty(typeProperties)) {
                JsonNodeToPropertiesMapConverter.convertJsonNodeToPropertiesMap(propertiesJsonNode, properties, uniqueKey, typeProperties, false);
            } else {
                JsonNodeToPropertiesMapConverter.convertJsonNodeToPropertiesMap(propertiesJsonNode, properties, uniqueKey, typeProperties, true);
            }
        }
    }

    private String getTypeName(JsonNode jsonNode) {
        return jsonNode.get(SolutionConstants.JSON_TYPE_NAME).asText();
    }
}
