package com.infoDiscover.solution.builder;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.solution.common.util.PrefixSetting;
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
    public void exportSolutionTemplate(String prefix, String targetFile) {

    }

    public void importSolutionTemplate(String templateFile) throws Exception {
        logger.info("Start to importSolutionTemplate to space: {} with prefix: {} and " +
                "templateFile: {}", spaceName, prefix, templateFile);

        String templateJson = FileUtil.readFileContent(templateFile);
        logger.debug("templateJson: {}", templateJson);

        String factType = PrefixSetting.getFactTypeWithPrefix(prefix, SolutionConstants
                .FACT_TYPE_SOLUTION_TEMPLATE);
        SolutionTemplateBuilder builder = new SolutionTemplateBuilder(spaceName, factType);
        builder.createNewOrUpdateTemplate(prefix, templateJson);

        logger.info("Exit importSolutionTemplate()...");
    }

}
