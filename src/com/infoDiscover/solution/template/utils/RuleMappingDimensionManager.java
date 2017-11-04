package com.infoDiscover.solution.template.utils;

import com.info.discover.ruleengine.solution.pojo.DataDuplicateCopyMappingVO;
import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.DimensionType;
import com.infoDiscover.infoDiscoverEngine.dataMart.TypeProperty;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.builder.SolutionConstants;
import com.infoDiscover.solution.common.dimension.DimensionManager;
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
public class RuleMappingDimensionManager {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Dimension createNewOrUpdateDimension(InfoDiscoverSpace ids, JsonNode jsonNode, boolean overwrite,
                                                 List<DataDuplicateCopyMappingVO> dimensionDuplicateCopyList,
                                                 boolean ignoreNotMappingProperties)
            throws InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineDataMartException {
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
            logger.info("All the properties type should map to the defined properties of the dimension: {}", typeName);
            JsonNodeToPropertiesMapConverter.convertJsonNodeToPropertiesMap(propertiesJsonNode, properties, uniqueKey, null, false);
        } else {
            logger.info("Ignore the properties that property type is not mapping to the defined properties of the dimension: {}", typeName);
            DimensionType dimensionType = ids.getDimensionType(typeName);
            List<TypeProperty> typeProperties = dimensionType.getTypeProperties();
            if (CollectionUtils.isEmpty(typeProperties)) {
                JsonNodeToPropertiesMapConverter.convertJsonNodeToPropertiesMap(propertiesJsonNode, properties, uniqueKey, typeProperties, false);
            } else {
                JsonNodeToPropertiesMapConverter.convertJsonNodeToPropertiesMap(propertiesJsonNode, properties, uniqueKey, typeProperties, true);
            }
        }

        DimensionManager manager = new DimensionManager(ids);
        Dimension dimension;

        if (overwrite && uniqueKey.size() > 0) {
            dimension = new DataImportHelper().getDimensionWithUniqueKeys(ids, typeName, uniqueKey);
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
//        Map<String, List<DataDuplicateCopyMappingVO>> map = new SolutionRelationMapping().getDimensionDuplicatedCopyMap();
//        List<DataDuplicateCopyMappingVO> dimensionDuplicateCopyList = null;
//
//        // if no duplicate copy mapping
//        if (MapUtils.isEmpty(map)) {
//            return dimension;
//        }

        // if has duplicate copy mapping
//        dimensionDuplicateCopyList = map.get(SolutionConstants.JSON_DIMENSION_DUPLICATE_COPY_MAPPING);

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
        new PropertyCopyUtil().copyPropertiesFromInputToTargetFact(ids, properties, sourceToTargetList, jsonNode);

        logger.info("Exit createNewOrUpdateDimension()...");

        return dimension;
    }


    private String getTypeName(JsonNode jsonNode) {
        return jsonNode.get(SolutionConstants.JSON_TYPE_NAME).asText();
    }
}
