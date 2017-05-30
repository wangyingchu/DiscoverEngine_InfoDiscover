package com.infoDiscover.solution.builder;

import com.infoDiscover.common.util.CompressionUtil;
import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.common.util.StringUtil;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.solution.common.util.PrefixSetting;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sun.
 */
public class SolutionTemplateImporter {

    private final static Logger logger = LoggerFactory.getLogger(SolutionTemplateImporter.class);

    private String spaceName;
    private String prefix;

    public SolutionTemplateImporter(String spaceName, String prefix) {
        this.spaceName = spaceName;
        this.prefix = prefix;
    }

    public void exportSolutionTemplate(String targetFileDirectory) throws Exception {
        logger.info("Start exportSolutionTemplate with prefix: {} to file directory: {}", prefix,
                targetFileDirectory);
        SolutionTemplateBuilder builder = new SolutionTemplateBuilder(spaceName, prefix);
        Fact solutionTemplateFact = builder.getSolutionTemplateByPrefix(prefix);

        if (solutionTemplateFact == null) {
            throw new Exception("Solution Template with prefix: " + prefix + " is not found");
        }

        String prefix = solutionTemplateFact.getProperty(SolutionConstants.PROPERTY_STD_PREFIX)
                .getPropertyValue().toString();
        String json = convertFactToJson(solutionTemplateFact);

        String zipFile = targetFileDirectory + "/" + PrefixSetting.normalizePrefix(prefix) +
                SolutionConstants.SOLUTION_TEMPLATE_FILE_NAME + ".zip";

        CompressionUtil.zip(zipFile, SolutionConstants.SOLUTION_TEMPLATE_JSON_FILE, json);

        logger.info("Exit to exportSolutionTemplate()...");
    }

    public void importSolutionTemplateWithZipped(String zippedFile, boolean overwrite) throws
            Exception {
        logger.info("Start to importSolutionTemplateWithZipped to space: {} with prefix: {} and " +
                "zippedFile: {} and overwrite is: {}", spaceName, prefix, zippedFile, overwrite);

        String templateJson = CompressionUtil.unzip(zippedFile);
        logger.debug("templateJson: {}", templateJson);

        SolutionTemplateBuilder builder = new SolutionTemplateBuilder(spaceName, prefix);

        builder.createNewOrUpdateTemplate(templateJson, overwrite);

        logger.info("Exit importSolutionTemplate()...");
    }

    public void importSolutionTemplate(String templateFile, boolean overwrite) throws Exception {
        logger.info("Start to importSolutionTemplate to space: {} with prefix: {} and " +
                "templateFile: {} and overwrite is: {}", spaceName, prefix, templateFile, overwrite);

        String templateJson = FileUtil.readFileContent(templateFile);
        logger.debug("templateJson: {}", templateJson);

        SolutionTemplateBuilder builder = new SolutionTemplateBuilder(spaceName, prefix);

        builder.createNewOrUpdateTemplate(templateJson, overwrite);

        logger.info("Exit importSolutionTemplate()...");
    }

    private String convertFactToJson(Fact templateFact) {
        logger.info("Start to convertFactToJson");

        JsonNode factTypeDefinition = SolutionTemplateParser.readFactTypeDefinition(templateFact);
        logger.debug("factType: {}", factTypeDefinition);
        JsonNode dimensionTypeDefinition = SolutionTemplateParser.readDimensionTypeDefinition
                (templateFact);
        logger.debug("dimensionType: {}", dimensionTypeDefinition);
        JsonNode relationDefinition = SolutionTemplateParser.readRelationDefinition(templateFact);
        logger.debug("relation: {}", relationDefinition);

        String json = "{\"data\":" + "{" +
                StringUtil.removeFirstAndLastChar(factTypeDefinition.toString()) + "," +
                StringUtil.removeFirstAndLastChar(dimensionTypeDefinition.toString()) + "," +
                StringUtil.removeFirstAndLastChar(relationDefinition.toString()) + "}}";
        logger.debug(json);

        logger.info("Exit to convertFactToJson()...");

        return json;
    }
}
