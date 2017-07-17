package com.infoDiscover.solution.template;

import com.infoDiscover.common.util.CompressionUtil;
import com.infoDiscover.solution.builder.SolutionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun on 7/17/17.
 */
public class TemplateExporter {
    
    public static final Logger logger = LoggerFactory.getLogger(TemplateExporter.class);
    
    private String spaceName;
    private String solutionName;

    public TemplateExporter(String spaceName, String solutionName) {
        this.spaceName = spaceName;
        this.solutionName = solutionName;
    }

    public void exportSolutionTemplate(String targetFileDirectory) throws Exception {
        logger.info("Start exportSolutionTemplate of name: {} to file directory: {}", solutionName,
                targetFileDirectory);
        
        DDLExporter ddlExporter = new DDLExporter(spaceName, solutionName);
        String factTypeDefinitions = ddlExporter.generateFactTypeDefinitionDDL();
        logger.debug("Fact type definitions is: {}", factTypeDefinitions);

        String dimensionTypeDefinitions = ddlExporter.generateDimensionTypeDefinitionDDL();
        logger.debug("dimensionTypeDefinitions: " + dimensionTypeDefinitions);

        String relationTypeDefinitions = ddlExporter.generateRelationTypeDefinitionDDL();
        logger.debug("relationTypeDefinitions: " + relationTypeDefinitions);

        String factToFactMapping = ddlExporter.generateFactToFactDefinitionDDL();
        logger.debug("factToFactMapping: " + factToFactMapping);

        String factToDimensionMapping = ddlExporter.generateFactToDimensionDefinitionDDL();
        logger.debug("factToDimensionMapping: " + factToDimensionMapping);

        String dimensionToDimensionMapping = ddlExporter.generateDimensionToDimensionDefinitionDDL();
        logger.debug("dimensionToDimensionMapping: " + dimensionToDimensionMapping);

        String factToDateMapping = ddlExporter.generateFactToDateDefinitionDDL();
        logger.debug("factToDateMapping: " + factToDateMapping);

        Map<String, String> files = new HashMap<>();

        if (factTypeDefinitions != null) {
            files.put(SolutionConstants.SOLUTION_TEMPLATE_FACT_TYPE_FILE_NAME, factTypeDefinitions);
        }

        if (dimensionTypeDefinitions != null) {
            files.put(SolutionConstants.SOLUTION_TEMPLATE_DIMENSION_TYPE_FILE_NAME, dimensionTypeDefinitions);
        }
        if (relationTypeDefinitions != null) {
            files.put(SolutionConstants.SOLUTION_TEMPLATE_RELATION_TYPE_FILE_NAME, relationTypeDefinitions);
        }
        if (factToFactMapping != null) {
            files.put(SolutionConstants.SOLUTION_TEMPLATE_FACT_TO_FACT_FILE_NAME, factToFactMapping);
        }
        if (factToDimensionMapping != null) {
            files.put(SolutionConstants.SOLUTION_TEMPLATE_FACT_TO_DIMENSION_FILE_NAME, factToDimensionMapping);
        }
        if (dimensionToDimensionMapping != null) {
            files.put(SolutionConstants.SOLUTION_TEMPLATE_DIMENSION_TO_DIMENSION_FILE_NAME, dimensionToDimensionMapping);
        }
        if (factToDateMapping != null) {
            files.put(SolutionConstants.SOLUTION_TEMPLATE_FACT_TO_DATE_FILE_NAME, factToDateMapping);
        }

        String zipFile = targetFileDirectory + "/" + solutionName + "_" +
                SolutionConstants.SOLUTION_TEMPLATE_FILE_NAME + ".zip";

        CompressionUtil.zip(zipFile, files);

        logger.info("Exit to exportSolutionTemplate()...");
    }
}
