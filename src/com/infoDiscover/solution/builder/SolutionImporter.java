package com.infoDiscover.solution.builder;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.common.util.JsonUtil;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.common.dimension.DimensionManager;
import com.infoDiscover.solution.common.fact.FactManager;
import com.infoDiscover.solution.construction.supervision.util.PrefixManager;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by sun.
 */
public class SolutionImporter {

    private final static Logger logger = LoggerFactory.getLogger(SolutionImporter.class);

    private String prefix;

    public SolutionImporter(String prefix) {
        this.prefix = prefix;
    }

    // TODO:
    public void exportSolutionTemplate(String prefix, String targetFile) {

    }

    public void importSolutionTemplate(String spaceName, String templateFile) throws Exception {
        logger.info("Start to importSolutionTemplate to space: {} with prefix: {} and " +
                "templateFile: {}", spaceName, prefix, templateFile);

        String templateJson = FileUtil.readFileContent(templateFile);
        logger.debug("templateJson: {}", templateJson);

        InfoDiscoverSpace ids = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
        if (ids != null) {
            String factType = PrefixManager.normalizePrefix(prefix) + SolutionConstants
                    .FACT_TYPE_SOLUTION_TEMPLATE;
            SolutionTemplateBuilder builder = new SolutionTemplateBuilder(ids, factType);
            builder.createNewOrUpdateTemplate(templateJson);
        }

        ids.closeSpace();

        logger.info("Exit importSolutionTemplate()...");
    }

    public void generateFromTemplate(InfoDiscoverSpace ids) throws Exception {
        logger.info("Enter generateFromTemplate with prefix: {}", prefix);

        String factType = prefix + SolutionConstants.FACT_TYPE_SOLUTION_TEMPLATE;
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

    public JsonNode readFactTypeDefinition(Fact templateFact) {
        return readDefinition(templateFact, SolutionConstants.PROPERTY_STD_FACT_DEFINITION);
    }

    public JsonNode readDimensionTypeDefinition(Fact templateFact) {
        return readDefinition(templateFact, SolutionConstants.PROPERTY_STD_DIMENSION_DEFINITION);
    }

    public JsonNode readRelationDefinition(Fact templateFact) {
        return readDefinition(templateFact, SolutionConstants.PROPERTY_STD_RELATION_DEFINITION);
    }

    public JsonNode readDefinition(Fact templateFact, String propertyName) {
        logger.info("Start to readDefinition of propertyName: {}", propertyName);

        String factJson = templateFact.getProperty(propertyName).getPropertyValue()
                .toString();

        if (factJson == null || factJson.trim().isEmpty()) {
            return null;
        }

        try {
            return JsonUtil.string2JsonNode(factJson);
        } catch (IOException e) {
            logger.error("Failed to parse fact json");
        }

        logger.info("End to readDefinition");

        return null;
    }


    private void createFactType(InfoDiscoverSpace ids, Fact templateFact) throws InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineDataMartException {
        logger.info("Start to createFactType");
        JsonNode factsNode = readFactTypeDefinition(templateFact);

        if (factsNode != null) {
            FactManager factManager = new FactManager(ids);
            for(JsonNode factNode: factsNode.get(SolutionConstants.JSON_FACTS)) {
                String factType = PrefixManager.normalizePrefix(prefix) + factNode.get
                        (SolutionConstants.JSON_TYPE_NAME).asText();
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
        JsonNode dimensionsJsonNode = readDimensionTypeDefinition(templateFact);

        if (dimensionsJsonNode != null) {
            DimensionManager dimensionManager = new DimensionManager(ids);
            for(JsonNode dimensionNode: dimensionsJsonNode.get(SolutionConstants.JSON_DIMENSIONS)) {
                String dimensionType = PrefixManager.normalizePrefix(prefix) + dimensionNode.get
                        (SolutionConstants.JSON_TYPE_NAME).asText();
                JsonNode propertiesJsonNode = dimensionNode.get(SolutionConstants.JSON_PROPERTIES);
                if (propertiesJsonNode != null) {
                    dimensionManager.createDimensionType(dimensionType, propertiesJsonNode);
                }
            }
        }

        logger.info("Exit to createDimensionType...");

    }

    private void createRelationType(InfoDiscoverSpace ids, Fact templateFact) throws InfoDiscoveryEngineDataMartException {
        logger.info("Start to createRelationType");
        JsonNode relationsJsonNode = readRelationDefinition(templateFact);

        if (relationsJsonNode != null) {
            for (JsonNode relationNode: relationsJsonNode.get(SolutionConstants.JSON_RELATIONS)) {
                String relationType = PrefixManager.normalizePrefix(prefix) + relationNode.get
                        (SolutionConstants.JSON_RELATION_TYPE_NAME).asText();
                if(!ids.hasRelationType(relationType)) {
                    ids.addRelationType(relationType);
                }
            }
        }

        logger.info("Exit to createRelationType");
    }

}
