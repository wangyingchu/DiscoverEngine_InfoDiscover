package com.infoDiscover.solution.template.utils;

import com.info.discover.ruleengine.solution.pojo.DataDateMappingVO;
import com.info.discover.ruleengine.solution.pojo.DataDuplicateCopyMappingVO;
import com.info.discover.ruleengine.solution.pojo.RelationMappingVO;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun on 11/4/17.
 */
public class RuleToPOJOConverter {

    public static RelationMappingVO toRelationMappingPOJO(JsonNode mappingRule, String relationMappingType) {

        JsonNode sourceDataTypeKind = mappingRule.get("sourceDataTypeKind");
        JsonNode sourceDataTypeName = mappingRule.get("sourceDataTypeName");
        JsonNode sourceDataPropertyName = mappingRule.get("sourceDataPropertyName");
        JsonNode sourceDataPropertyType = mappingRule.get("sourceDataPropertyType");
        JsonNode targetDataTypeKind = mappingRule.get("targetDataTypeKind");
        JsonNode targetDataTypeName = mappingRule.get("targetDataTypeName");
        JsonNode targetDataPropertyName = mappingRule.get("targetDataPropertyName");
        JsonNode targetDataPropertyType = mappingRule.get("targetDataPropertyType");
        JsonNode targetDataPropertyValue = mappingRule.get("targetDataPropertyValue") == null ? null :
                mappingRule.get("targetDataPropertyValue");
        JsonNode relationTypeName = mappingRule.get("relationTypeName");
        JsonNode relationDirection = mappingRule.get("relationDirection");
        JsonNode minValue = mappingRule.get("minValue") == null ? null : mappingRule.get("minValue");
        JsonNode maxValue = mappingRule.get("maxValue") == null ? null : mappingRule.get("maxValue");
        JsonNode mappingNotExistHandleMethod = mappingRule.get("mappingNotExistHandleMethod");

        RelationMappingVO pojo = new RelationMappingVO();
        pojo.setRelationMappingType(relationMappingType);
        pojo.setSourceDataTypeKind(sourceDataTypeKind == null ? null : sourceDataTypeKind.asText());
        pojo.setSourceDataTypeName(sourceDataTypeName == null ? null : sourceDataTypeName.asText());
        pojo.setSourceDataPropertyName(sourceDataPropertyName == null ? null : sourceDataPropertyName.asText());
        pojo.setSourceDataPropertyType(sourceDataPropertyType == null ? null : sourceDataPropertyType.asText());
        pojo.setTargetDataTypeKind(targetDataTypeKind == null ? null : targetDataTypeKind.asText());
        pojo.setTargetDataTypeName(targetDataTypeName == null ? null : targetDataTypeName.asText());
        pojo.setTargetDataPropertyName(targetDataPropertyName == null ? null : targetDataPropertyName.asText());
        pojo.setTargetDataPropertyType(targetDataPropertyType == null ? null : targetDataPropertyType.asText());
        pojo.setTargetDataPropertyValue(targetDataPropertyValue == null ? null : targetDataPropertyValue.asText());
        pojo.setRelationTypeName(relationTypeName == null ? null : relationTypeName.asText());
        pojo.setRelationDirection(relationDirection.asText());
        pojo.setMinValue(minValue == null ? null : minValue.asText());
        pojo.setMaxValue(maxValue == null ? null : maxValue.asText());
        pojo.setMappingNotExistHandleMethod(
                mappingNotExistHandleMethod == null ? null : mappingNotExistHandleMethod.asText());

        return pojo;
    }

    public static List<RelationMappingVO> toRelationMappingPOJOList(JsonNode mappingsJsonNode, String relationMappingType) {
        List<RelationMappingVO> list = new ArrayList<>();

        for (JsonNode mapping : mappingsJsonNode) {
            RelationMappingVO pojo = toRelationMappingPOJO(mapping, relationMappingType);
            list.add(pojo);
        }

        return list;
    }

    public static DataDateMappingVO toDataDateMappingPOJO(JsonNode mappingRule, String relationMappingType) {
        JsonNode sourceDataTypeKind = mappingRule.get("sourceDataTypeKind");
        JsonNode sourceDataTypeName = mappingRule.get("sourceDataTypeName");
        JsonNode sourceDataPropertyName = mappingRule.get("sourceDataPropertyName");
        JsonNode relationTypeName = mappingRule.get("relationTypeName");
        JsonNode relationDirection = mappingRule.get("relationDirection");
        JsonNode dateDimensionTypePrefix = mappingRule.get("dateDimensionTypePrefix");

        DataDateMappingVO pojo = new DataDateMappingVO();
        pojo.setRelationMappingType(relationMappingType);
        pojo.setSourceDataTypeKind(sourceDataTypeKind == null ? null : sourceDataTypeKind.asText());
        pojo.setSourceDataTypeName(sourceDataTypeName == null ? null : sourceDataTypeName.asText());
        pojo.setSourceDataPropertyName(sourceDataPropertyName == null ? null : sourceDataPropertyName.asText());
        pojo.setRelationTypeName(relationTypeName == null ? null : relationTypeName.asText());
        pojo.setRelationDirection(relationDirection.asText());
        pojo.setDateDimensionTypePrefix(
                dateDimensionTypePrefix == null ? null : dateDimensionTypePrefix.asText());

        return pojo;
    }

    public static List<DataDateMappingVO> toDataDateMappingPOJOList(JsonNode mappingsJsonNode, String relationMappingType) {
        List<DataDateMappingVO> list = new ArrayList<>();

        for (JsonNode mapping : mappingsJsonNode) {
            DataDateMappingVO pojo = toDataDateMappingPOJO(mapping, relationMappingType);
            list.add(pojo);
        }

        return list;
    }

    public static DataDuplicateCopyMappingVO toDataDuplicateCopyMappingPOJO(JsonNode mappingRule,
                                                                            String relationMappingType) {
        JsonNode sourceDataTypeKind = mappingRule.get("sourceDataTypeKind");
        JsonNode sourceDataTypeName = mappingRule.get("sourceDataTypeName");
        JsonNode sourceDataPropertyName = mappingRule.get("sourceDataPropertyName");
        JsonNode sourceDataPropertyType = mappingRule.get("sourceDataPropertyType");

        JsonNode targetDataTypeName = mappingRule.get("targetDataTypeName");
        JsonNode targetDataPropertyName = mappingRule.get("targetDataPropertyName");
        JsonNode targetDataPropertyType = mappingRule.get("targetDataPropertyType");
        JsonNode existingPropertyHandleMethod = mappingRule.get("existingPropertyHandleMethod");

        DataDuplicateCopyMappingVO pojo = new DataDuplicateCopyMappingVO();
        pojo.setRelationMappingType(relationMappingType);
        pojo.setSourceDataTypeKind(sourceDataTypeKind == null ? null : sourceDataTypeKind.asText());
        pojo.setSourceDataTypeName(sourceDataTypeName == null ? null : sourceDataTypeName.asText());
        pojo.setSourceDataPropertyName(sourceDataPropertyName == null ? null : sourceDataPropertyName.asText());
        pojo.setSourceDataPropertyType(sourceDataPropertyType == null ? null : sourceDataPropertyType.asText());
        pojo.setTargetDataTypeName(targetDataTypeName == null ? null : targetDataTypeName.asText());
        pojo.setTargetDataPropertyName(targetDataPropertyName == null ? null : targetDataPropertyName.asText());
        pojo.setTargetDataPropertyType(targetDataPropertyType == null ? null : targetDataPropertyType.asText());
        pojo.setExistingPropertyHandleMethod(
                existingPropertyHandleMethod == null ? null : existingPropertyHandleMethod.asText());

        return pojo;
    }

    public static List<DataDuplicateCopyMappingVO> toDataDuplicateCopyMappingPOJOList(JsonNode mappingsJsonNode,
                                                                                      String relationMappingType) {
        List<DataDuplicateCopyMappingVO> list = new ArrayList<>();

        for (JsonNode mapping : mappingsJsonNode) {
            DataDuplicateCopyMappingVO pojo = toDataDuplicateCopyMappingPOJO(mapping, relationMappingType);

            list.add(pojo);
        }

        return list;
    }
}
