package com.infoDiscover.solution.builder;

import com.infoDiscover.common.util.FileUtil;
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

    // TODO:
    public void exportSolutionTemplate(String targetFile) {
        logger.info("Start exportSolutionTemplate with prefix: {} to file: {}", prefix, targetFile);
        SolutionTemplateBuilder builder = new SolutionTemplateBuilder(spaceName, prefix);


        logger.info("Exit to exportSolutionTemplate()...");
    }

    public void importSolutionTemplate(String templateFile) throws Exception {
        logger.info("Start to importSolutionTemplate to space: {} with prefix: {} and " +
                "templateFile: {}", spaceName, prefix, templateFile);

        String templateJson = FileUtil.readFileContent(templateFile);
        logger.debug("templateJson: {}", templateJson);


        SolutionTemplateBuilder builder = new SolutionTemplateBuilder(spaceName, prefix);

        builder.createNewOrUpdateTemplate(templateJson, true);

        logger.info("Exit importSolutionTemplate()...");
    }

}
