package com.infoDiscover.solution.builder;

import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.solution.builder.vo.RelationMappingVO;
import com.infoDiscover.solution.common.util.JsonNodeUtil;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sun.
 */
public class SolutionTemplateParser {

    private final static Logger logger = LoggerFactory.getLogger(SolutionTemplateParser.class);

    private String spaceName;
    private String prefix;

    public SolutionTemplateParser(String spaceName, String prefix) {
        this.spaceName = spaceName;
        this.prefix = prefix;
    }

    private static Map<String, Fact> templateFactMap = new HashMap<>();

    private Map<String, List<RelationMappingVO>> factToDimensionMap = new HashMap<>();
    private Map<String, List<RelationMappingVO>> factToFactMap = new HashMap<>();
    private Map<String, List<RelationMappingVO>> dimensionToDimensionMap = new HashMap<>();
    private Map<String, List<RelationMappingVO>> factToDateDimension = new HashMap<>();

    public Map<String, List<RelationMappingVO>> getFactToDimensionMap() {
        init();
        return factToDimensionMap;
    }

    public Map<String, List<RelationMappingVO>> getFactToFactMap() {
        init();
        return factToFactMap;
    }

    public Map<String, List<RelationMappingVO>> getDimensionToDimensionMap() {
        init();
        return dimensionToDimensionMap;
    }

    public Map<String, List<RelationMappingVO>> getFactToDateDimension() {
        return factToDateDimension;
    }

    public Map<String, Fact> getTemplateFactMap() {
        if(templateFactMap == null || templateFactMap.isEmpty()) {
            SolutionTemplateBuilder builder = new SolutionTemplateBuilder(spaceName,prefix);
            Fact templateFact = builder.getSolutionTemplateByPrefix(prefix);
            templateFactMap.put(prefix, templateFact);
        }

        return templateFactMap;
    }

    public void init() {
        parseRelationMapping();
    }

    private void parseRelationMapping() {
        logger.info("Enter to parseRelationMapping");

        Fact templateFact = getTemplateFactMap().get(prefix);

        if (templateFact == null) {
            return;
        }

        JsonNode relationDefinition = readRelationDefinition(templateFact);
        if (relationDefinition == null) {
            return;
        }

        JsonNode relationsJsonNode = relationDefinition.get(SolutionConstants.JSON_RELATIONS);
        if (relationsJsonNode == null) {
            return;
        }

        JsonNode factToDimensionNode = relationsJsonNode.get(SolutionConstants
                .JSON_FACT_TO_DIMENSION_MAPPING);
        JsonNode factToFactNode = relationsJsonNode.get(SolutionConstants
                .JSON_FACT_TO_FACT_MAPPING);
        JsonNode dimensionToDimensionNode = relationsJsonNode.get(SolutionConstants
                .JSON_DIMENSION_TO_DIMENSION_MAPPING);
        JsonNode factToDateDimensionNode = relationsJsonNode.get(SolutionConstants
                .JSON_FACT_TO_DATE_DIMENSION_MAPPING);

        this.factToDimensionMap = getRelationMappingList(factToDimensionNode);
        this.factToFactMap = getRelationMappingList(factToFactNode);
        this.dimensionToDimensionMap = getRelationMappingList(dimensionToDimensionNode);
        this.factToDateDimension = getRelationMappingList(factToDateDimensionNode);

        logger.info("Exit parseRelationMapping()...");
    }

    public Map<String, List<RelationMappingVO>> getRelationMappingList(
            JsonNode factToDimensionNode) {

        Map<String, List<RelationMappingVO>> map = new HashMap<>();

        for (JsonNode node : factToDimensionNode) {

            List<RelationMappingVO> list = new ArrayList<>();

            String fromType = node.get(SolutionConstants.JSON_FROM_TYPE).getTextValue();

            JsonNode mappingsJsonNode = node.get(SolutionConstants.JSON_MAPPINGS);
            for (JsonNode mapping : mappingsJsonNode) {
                JsonNode fromPropertyNode = mapping.get(SolutionConstants.JSON_FROM_PROPERTY);
                String fromProperty = "";
                if (fromPropertyNode != null) {
                    fromProperty = fromPropertyNode.asText();
                }

                JsonNode fromPrimaryKeyNode = mapping.get(SolutionConstants.JSON_FROM_PRIMARY_KEY);
                String fromPrimaryKey = "";
                if (fromPrimaryKeyNode != null) {
                    fromPrimaryKey = fromPrimaryKeyNode.asText();
                } else {
                    fromPrimaryKey = fromProperty;
                }

                JsonNode toTypeNode = mapping.get(SolutionConstants.JSON_TO_TYPE);
                String toType = "";
                if (toTypeNode != null) {
                    toType = toTypeNode.asText();
                }

                JsonNode toPropertyNode = mapping.get(SolutionConstants
                        .JSON_TO_PROPERTY);
                String toProperty = "";
                if (toPropertyNode != null) {
                    toProperty = toPropertyNode.asText();
                }

                JsonNode toPrimaryKeyNode = mapping.get(SolutionConstants.JSON_TO_PRIMARY_KEY);
                String toPrimaryKey = "";
                if (toPrimaryKeyNode != null) {
                    toPrimaryKey = toPrimaryKeyNode.asText();
                } else {
                    toPrimaryKey = toProperty;
                }

                String relationTypeName = mapping.get(SolutionConstants
                        .JSON_RELATION_TYPE_NAME).asText();

                JsonNode propertyTypeNode = mapping.get(SolutionConstants.JSON_PROPERTY_TYPE);
                String propertyType = "";
                if (propertyTypeNode != null) {
                    propertyType = propertyTypeNode.asText();
                }

                RelationMappingVO vo = new RelationMappingVO(fromType,fromProperty,
                        fromPrimaryKey,toType,toProperty,toPrimaryKey,relationTypeName,
                        propertyType);

                list.add(vo);
            }

            map.put(fromType, list);
        }

        return map;
    }

    public static JsonNode readFactTypeDefinition(Fact templateFact) {
        return readDefinition(templateFact, SolutionConstants.PROPERTY_STD_FACT_DEFINITION);
    }

    public static JsonNode readDimensionTypeDefinition(Fact templateFact) {
        return readDefinition(templateFact, SolutionConstants.PROPERTY_STD_DIMENSION_DEFINITION);
    }

    public static JsonNode readRelationDefinition(Fact templateFact) {
        return readDefinition(templateFact, SolutionConstants.PROPERTY_STD_RELATION_DEFINITION);
    }

    public static JsonNode readDefinition(Fact templateFact, String propertyName) {
        logger.info("Start to readDefinition of propertyName: {}", propertyName);

        String factJson = templateFact.getProperty(propertyName).getPropertyValue()
                .toString();

        if (factJson == null || factJson.trim().isEmpty()) {
            return null;
        }

        try {
            return JsonNodeUtil.string2JsonNode(factJson);
        } catch (IOException e) {
            logger.error("Failed to parse fact json");
        }

        logger.info("End to readDefinition");

        return null;
    }

    public List<RelationMappingVO> getRelationMappingVOList(String factType, Map<String,
            List<RelationMappingVO>> map) {
        return map.get(factType);
    }
}
