package com.infoDiscover.solution.builder;

import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.common.dimension.DimensionManager;
import com.infoDiscover.solution.common.fact.FactManager;
import com.infoDiscover.solution.common.util.PrefixSetting;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sun.
 */
public class SolutionGenerator {
    private final static Logger logger = LoggerFactory.getLogger(SolutionGenerator.class);

    private InfoDiscoverSpace ids;
    private String prefix;

    public SolutionGenerator(InfoDiscoverSpace ids, String prefix) {
        this.ids = ids;
        this.prefix = prefix;
    }

    public void generateSolutionFromTemplate() throws Exception {
        logger.info("Enter generateFromTemplate with prefix: {}", prefix);

        String factType = PrefixSetting.getFactTypeWithPrefix(prefix, SolutionConstants
                .FACT_TYPE_SOLUTION_TEMPLATE);

        SolutionTemplateBuilder builder = new SolutionTemplateBuilder(ids, factType);

        // get solution template
        Fact templateFact = builder.getSolutionTemplateByPrefix(prefix);
        if (templateFact == null) {
            throw new Exception("Solution template with prefix: " + prefix + " not found");
        }

        // create fact type
        createFactType(ids, templateFact);

        // create dimension type
        createDimensionType(ids, templateFact);

        // create relation type
        createRelationType(ids, templateFact);
    }

    private void createFactType(InfoDiscoverSpace ids, Fact templateFact) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineDataMartException {
        logger.info("Start to createFactType");
        JsonNode factsNode = SolutionTemplateParser.readFactTypeDefinition(templateFact);

        if (factsNode != null) {
            FactManager factManager = new FactManager(ids);
            for (JsonNode factNode : factsNode.get(SolutionConstants.JSON_FACTS)) {
                String factType = PrefixSetting.getFactTypeWithPrefix(prefix, factNode.get
                        (SolutionConstants.JSON_TYPE_NAME).asText());
                JsonNode propertiesJsonNode = factNode.get(SolutionConstants.JSON_PROPERTIES);
                if (propertiesJsonNode != null) {
                    factManager.createFactType(factType, propertiesJsonNode);
                }
            }
        }

        logger.info("Exit to createFactType...");

    }

    private void createDimensionType(InfoDiscoverSpace ids, Fact templateFact) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineDataMartException {
        logger.info("Start to createDimensionType");
        JsonNode dimensionsJsonNode = SolutionTemplateParser.readDimensionTypeDefinition
                (templateFact);

        if (dimensionsJsonNode != null) {
            DimensionManager dimensionManager = new DimensionManager(ids);
            for (JsonNode dimensionNode : dimensionsJsonNode.get(SolutionConstants
                    .JSON_DIMENSIONS)) {
                String dimensionType = PrefixSetting.getFactTypeWithPrefix(prefix, dimensionNode.get
                        (SolutionConstants.JSON_TYPE_NAME).asText());
                JsonNode propertiesJsonNode = dimensionNode.get(SolutionConstants.JSON_PROPERTIES);
                if (propertiesJsonNode != null) {
                    dimensionManager.createDimensionType(dimensionType, propertiesJsonNode);
                }
            }
        }

        logger.info("Exit to createDimensionType...");

    }

    private void createRelationType(InfoDiscoverSpace ids, Fact templateFact) throws
            InfoDiscoveryEngineDataMartException {
        logger.info("Start to createRelationType");

        JsonNode relationDefinitionJsonNode = SolutionTemplateParser.readRelationDefinition
                (templateFact);

        if (relationDefinitionJsonNode != null) {
            JsonNode relationsJsonNode = relationDefinitionJsonNode.get(SolutionConstants
                    .JSON_RELATIONS);

            if (relationsJsonNode != null) {
                // fact to dimension mapping
                JsonNode factToDimensionJsonNode = relationsJsonNode.get(SolutionConstants
                        .JSON_FACT_TO_DIMENSION_MAPPING);
                if (factToDimensionJsonNode != null) {
                    for (JsonNode jsonNode : factToDimensionJsonNode) {
                        createRelationType(ids, jsonNode);
                    }
                }

                // fact to fact mapping
                JsonNode factToFactJsonNode = relationsJsonNode.get(SolutionConstants
                        .JSON_FACT_TO_FACT_MAPPING);
                if (factToFactJsonNode != null) {
                    for (JsonNode jsonNode : factToFactJsonNode) {
                        createRelationType(ids, jsonNode);
                    }
                }

                // dimension to dimension mapping
                JsonNode dimensionToDimensionJsonNode = relationsJsonNode.get(SolutionConstants
                        .JSON_DIMENSION_TO_DIMENSION_MAPPING);
                if (dimensionToDimensionJsonNode != null) {
                    for (JsonNode jsonNode : dimensionToDimensionJsonNode) {
                        createRelationType(ids, jsonNode);
                    }
                }

            }
        }

        logger.info("Exit to createRelationType");
    }


    private void createRelationType(InfoDiscoverSpace ids, JsonNode jsonNode) throws
            InfoDiscoveryEngineDataMartException {

        JsonNode mappingsNode = jsonNode.get(SolutionConstants.JSON_MAPPINGS);
        if (mappingsNode != null) {
            JsonNode relationType = mappingsNode.get(SolutionConstants.JSON_RELATION_TYPE_NAME);
            if (relationType != null) {
                String relationTypeName = PrefixSetting.normalizePrefix(prefix) +
                        relationType.asText();

                if (!ids.hasRelationType(relationTypeName)) {
                    ids.addRelationType(relationTypeName);
                }
            }
        }
    }

}
