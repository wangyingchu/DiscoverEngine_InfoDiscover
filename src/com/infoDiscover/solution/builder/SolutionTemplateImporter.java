package com.infoDiscover.solution.builder;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.common.util.PrefixSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sun.
 */
public class SolutionTemplateImporter {

    private final static Logger logger = LoggerFactory.getLogger(SolutionTemplateImporter.class);

    private String prefix;

    public SolutionTemplateImporter(String prefix) {
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
            String factType = PrefixSetting.getFactTypeWithPrefix(prefix, SolutionConstants
                    .FACT_TYPE_SOLUTION_TEMPLATE);
            SolutionTemplateBuilder builder = new SolutionTemplateBuilder(ids, factType);
            builder.createNewOrUpdateTemplate(templateJson);
        }

        ids.closeSpace();

        logger.info("Exit importSolutionTemplate()...");
    }

}
