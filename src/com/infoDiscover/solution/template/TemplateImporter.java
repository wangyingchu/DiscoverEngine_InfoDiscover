package com.infoDiscover.solution.template;

import com.infoDiscover.common.util.CompressionUtil;
import com.infoDiscover.solution.builder.SolutionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by sun on 7/17/17.
 */
public class TemplateImporter {
    public static final Logger logger = LoggerFactory.getLogger(TemplateImporter.class);

    private String spaceName;
    private String solutionName;

    public TemplateImporter(String spaceName, String solutionName) {
        this.spaceName = spaceName;
        this.solutionName = solutionName;
    }

    public void importSolution(String zippedFile, boolean overwrite) throws
            Exception {
        logger.info("Start to importSolutionTemplateWithZipped to space: {} with solutionName: {} and " +
                "zippedFile: {} and overwrite is: {}", spaceName, solutionName, zippedFile, overwrite);

        Map<String, String> files = CompressionUtil.unzip(zippedFile);

        String solutionDefinitions = files.get(SolutionConstants.SOLUTION_TEMPLATE_SOLUTION_DEFINITION);
        logger.debug("solutionDefinitions: {}", solutionDefinitions);

        String solutionTypePropertyTypeDefinitions = files.get(SolutionConstants.SOLUTION_TEMPLATE_SOLUTION_TYPE_PROPERTY_TYPE_FILE_NAME);
        logger.debug("solutionTypePropertyTypeDefinitions: {}", solutionTypePropertyTypeDefinitions);

        String factTypeDefinitions = files.get(SolutionConstants.SOLUTION_TEMPLATE_FACT_TYPE_FILE_NAME);
        logger.debug("factTypeDefinitions: {}", factTypeDefinitions);

        String dimensionTypeDefinitions = files.get(SolutionConstants.SOLUTION_TEMPLATE_DIMENSION_TYPE_FILE_NAME);
        logger.debug("dimensionTypeDefinitions: {}", dimensionTypeDefinitions);

        String relationTypeDefinitions = files.get(SolutionConstants.SOLUTION_TEMPLATE_RELATION_TYPE_FILE_NAME);
        logger.debug("relationTypeDefinitions: {}", relationTypeDefinitions);

        String factToFactMappings = files.get(SolutionConstants.SOLUTION_TEMPLATE_FACT_TO_FACT_FILE_NAME);
        logger.debug("factToFactMappings: {}", factToFactMappings);

        String factToDimensionMappings = files.get(SolutionConstants.SOLUTION_TEMPLATE_FACT_TO_DIMENSION_FILE_NAME);
        logger.debug("factToDimensionMappings: {}", factToDimensionMappings);

        String dimensionToDimensionMappings = files.get(SolutionConstants.SOLUTION_TEMPLATE_DIMENSION_TO_DIMENSION_FILE_NAME);
        logger.debug("dimensionToDimensionMappings: {}", dimensionToDimensionMappings);

        String factToDateMappings = files.get(SolutionConstants.SOLUTION_TEMPLATE_FACT_TO_DATE_FILE_NAME);
        logger.debug("factToDateMappings: {}", factToDateMappings);

        String dimensionToDateMappings = files.get(SolutionConstants.SOLUTION_TEMPLATE_DIMENSION_TO_DATE_FILE_NAME);
        logger.debug("dimensionToDateMappings: {}", dimensionToDateMappings);

        logger.info("Exit importSolutionTemplate()...");
    }
}
