package com.infoDiscover.solution.template;

import com.info.discover.ruleengine.solution.SolutionRelationMapping;
import com.info.discover.ruleengine.solution.pojo.DataDateMappingVO;
import com.info.discover.ruleengine.solution.pojo.DataDuplicateCopyMappingVO;
import com.info.discover.ruleengine.solution.pojo.RelationMappingVO;
import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relationable;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.solution.builder.SolutionConstants;
import com.infoDiscover.solution.common.database.DatabaseConnection;
import com.infoDiscover.solution.common.util.JsonNodeUtil;
import com.infoDiscover.solution.template.utils.RuleMappingDimensionManager;
import com.infoDiscover.solution.template.utils.RuleMappingFactManager;
import com.infoDiscover.solution.template.utils.RuleToPOJOConverter;
import org.apache.commons.collections.MapUtils;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sun on 7/23/17.
 */
public class DataImporter {
    private final static Logger logger = LoggerFactory.getLogger(DataImporter.class);

    private boolean ignoreNotMappingProperties = true;

    private String spaceName;

    public DataImporter(String spaceName) {
        this.spaceName = spaceName;
    }

    public void importData(String dataJson, boolean overwrite) throws IOException {
        importData(dataJson, null, overwrite);
    }

    public void importData(String dataJson, String rule, boolean overwrite) throws IOException {

        logger.info("Start to importData with data: {} and rule: {}", dataJson, rule);
        Map<String, List<RelationMappingVO>> relationMappingsMap = new HashMap<>();
        Map<String, List<DataDateMappingVO>> dateMappingsMap = new HashMap<>();
        List<DataDuplicateCopyMappingVO> factDuplicateCopyList = null;
        List<DataDuplicateCopyMappingVO> dimensionDuplicateCopyList = null;

        // get relation mappings from rule
        if (rule == null) {
            new SolutionRelationMapping(spaceName).getSolutionRelationMappings();
            Map<String, List<RelationMappingVO>> factToFactMap = new SolutionRelationMapping(spaceName).getFactToFactMap();
            Map<String, List<RelationMappingVO>> factToDimensionMap = new SolutionRelationMapping(spaceName).getFactToDimensionMap();
            Map<String, List<RelationMappingVO>> dimensionToFactMap = new SolutionRelationMapping(spaceName).getDimensionToFactMap();
            Map<String, List<RelationMappingVO>> dimensionToDimensionMap = new SolutionRelationMapping(spaceName).getDimensionToDimensionMap();

            relationMappingsMap.putAll(factToFactMap);
            relationMappingsMap.putAll(factToDimensionMap);
            relationMappingsMap.putAll(dimensionToFactMap);
            relationMappingsMap.putAll(dimensionToDimensionMap);

            Map<String, List<DataDateMappingVO>> factToDateMap = new SolutionRelationMapping(spaceName).getFactToDataMap();
            Map<String, List<DataDateMappingVO>> dimensionToDateMap = new SolutionRelationMapping(spaceName).getDimensionToDateMap();

            dateMappingsMap.putAll(factToDateMap);
            dateMappingsMap.putAll(dimensionToDateMap);

            Map<String, List<DataDuplicateCopyMappingVO>> factDuplicatedCopyMap = new SolutionRelationMapping(spaceName).getFactDuplicatedCopyMap();
            Map<String, List<DataDuplicateCopyMappingVO>> dimensionDuplicatedCopyMap = new SolutionRelationMapping(spaceName).getDimensionDuplicatedCopyMap();
            if (MapUtils.isNotEmpty(factDuplicatedCopyMap)) {
                factDuplicateCopyList = factDuplicatedCopyMap.get(SolutionConstants.JSON_FACT_DUPLICATE_COPY_MAPPING);
            }
            if (MapUtils.isNotEmpty(dimensionDuplicatedCopyMap)) {
                dimensionDuplicateCopyList = factDuplicatedCopyMap.get(SolutionConstants.JSON_DIMENSION_DUPLICATE_COPY_MAPPING);
            }
        } else {
            JsonNode rulesJsonNode = JsonNodeUtil.string2JsonNode(rule).get("data");
            for (JsonNode ruleNode : rulesJsonNode) {
                String mappingType = ruleNode.get("mappingType").asText();
                JsonNode mappingsJsonNode = ruleNode.get("rules");

                if (mappingType.equalsIgnoreCase(SolutionConstants.JSON_FACT_TO_FACT_MAPPING) ||
                        mappingType.equalsIgnoreCase(SolutionConstants.JSON_FACT_TO_DIMENSION_MAPPING) ||
                        mappingType.equalsIgnoreCase(SolutionConstants.JSON_DIMENSION_TO_FACT_MAPPING) ||
                        mappingType.equalsIgnoreCase(SolutionConstants.JSON_DIMENSION_TO_DIMENSION_MAPPING)) {

                    relationMappingsMap.put(mappingType, RuleToPOJOConverter.toRelationMappingPOJOList(mappingsJsonNode, mappingType));

                } else if (mappingType.equalsIgnoreCase(SolutionConstants.JSON_FACT_TO_DATE_DIMENSION_MAPPING) ||
                        mappingType.equalsIgnoreCase(SolutionConstants.JSON_DIMENSION_TO_DATE_DIMENSION_MAPPING)) {
                    dateMappingsMap.put(mappingType, RuleToPOJOConverter.toDataDateMappingPOJOList(mappingsJsonNode, mappingType));
                } else if (mappingType.equalsIgnoreCase(SolutionConstants.JSON_FACT_DUPLICATE_COPY_MAPPING)) {
                    factDuplicateCopyList = RuleToPOJOConverter.toDataDuplicateCopyMappingPOJOList(mappingsJsonNode, mappingType);
                } else {
                    dimensionDuplicateCopyList = RuleToPOJOConverter.toDataDuplicateCopyMappingPOJOList(mappingsJsonNode, mappingType);
                }
            }
        }

        InfoDiscoverSpace ids = null;
        try {
            ids = DatabaseConnection.connectToSpace(spaceName);

            JsonNode dataJsonNode = JsonNodeUtil.getDataNode(dataJson);
            if (dataJson != null) {

                RelationMappingOperator operator = new RelationMappingOperator();

                for (JsonNode jsonNode : dataJsonNode) {

                    String type = jsonNode.get(SolutionConstants.JSON_TYPE).asText();
                    // create or update fact
                    if (SolutionConstants.FACT_TYPE.equalsIgnoreCase(type)) {
                        Fact fact = (Fact) createRelationable(ids, jsonNode, overwrite, factDuplicateCopyList);

                        // link relations
                        if (fact != null) {
                            operator.linkBetweenNodesFromFact(ids, fact, relationMappingsMap, dateMappingsMap);
                        }
                    } else if (SolutionConstants.DIMENSION_TYPE.equalsIgnoreCase(type)) {
                        Dimension dimension = (Dimension) createRelationable(ids, jsonNode, overwrite, dimensionDuplicateCopyList);

                        // link relations
                        if (dimension != null) {
                            operator.linkBetweenNodesFromDimension(ids, dimension, relationMappingsMap, dateMappingsMap);
                        }
                    }

                }
            }
        } catch (Exception e) {
            logger.error("Failed to import data with error: {}", e.getMessage());
        } finally {
            ids.closeSpace();
        }

        logger.info("Exit to importData()...");
    }


    private Relationable createRelationable(InfoDiscoverSpace ids, JsonNode jsonNode, boolean override, List<DataDuplicateCopyMappingVO> duplicateCopyList)
            throws Exception {
        return createRelationable(ids, jsonNode, override, duplicateCopyList, ignoreNotMappingProperties);
    }

    private Relationable createRelationable(InfoDiscoverSpace ids,
                                            JsonNode jsonNode,
                                            boolean override,
                                            List<DataDuplicateCopyMappingVO> duplicateCopyList,
                                            boolean ignoreNotMappingProperties)
            throws Exception {
        String type = jsonNode.get(SolutionConstants.JSON_TYPE).asText();

        if (SolutionConstants.FACT_TYPE.equalsIgnoreCase(type)) {
            return new RuleMappingFactManager().createNewOrUpdateFact(ids, jsonNode, override, duplicateCopyList, ignoreNotMappingProperties);
        } else if (SolutionConstants.DIMENSION_TYPE.equalsIgnoreCase(type)) {
            return new RuleMappingDimensionManager().createNewOrUpdateDimension(ids, jsonNode, override, duplicateCopyList, ignoreNotMappingProperties);
        } else {
            throw new Exception("Wrong type, it should be: " + SolutionConstants.FACT_TYPE);
        }
    }

}
