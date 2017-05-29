package com.infoDiscover.solution.builder;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.common.util.PrefixSetting;

/**
 * Created by sun.
 */
public class TestSolutionTemplate {

    private final static String spaceName = "TEST_SOLUTION";
    private final static String prefix = "Test_";
    private final static String factType = SolutionConstants.FACT_TYPE_SOLUTION_TEMPLATE;

    private final static String testDataRoot =
            "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover" +
                    "/solution/builder/templatedata";

    private final static String templateFile = testDataRoot + "/SolutionTemplateDefinition.json";
    private final static String factDefinition = testDataRoot + "/FactDefinition.json";
    private final static String dimensionDefinition = testDataRoot + "/DimensionDefinition" +
            ".json";
    private final static String relationDefinition = testDataRoot + "/RelationDefinition" +
            ".json";


    public static void main(String[] args) throws Exception {

        // 1. initialize solution template space and fact type
        SolutionTemplateInitializer.initializeSolutionTemplate(spaceName, prefix);

        // 2. save solution template
        String templateJson = FileUtil.readFileContent(templateFile);
        System.out.println("templateJson: " + templateJson);

        InfoDiscoverSpace ids = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
        SolutionTemplateBuilder builder = new SolutionTemplateBuilder(ids, PrefixSetting
                .normalizePrefix(prefix) + factType);
        if (ids != null) {
            builder.createNewOrUpdateTemplate(templateJson);
        }

        //
        SolutionGenerator generator = new SolutionGenerator(ids, prefix);
        generator.generateSolutionFromTemplate();
        ids.closeSpace();
    }

    private static void update(SolutionTemplateBuilder builder, String templateId) throws Exception {
        // update prefix
        builder.updatePrefix(templateId, prefix);

        // check prefix is existed
        boolean prefixExisted = builder.checkPrefixIsExisted(prefix);
        System.out.println("prefixExisted: " + prefixExisted);

        // update fact definition
        String factJson = FileUtil.readFileContent(factDefinition);
        builder.updateFactDefinition(templateId, factJson);

        // update dimension definition
        String dimensionJson = FileUtil.readFileContent(dimensionDefinition);
        builder.updateDimensionDefinition(templateId, dimensionJson);

        // update relation definition
        String relationJson = FileUtil.readFileContent(relationDefinition);
        builder.updateRelationDefinition(templateId, relationJson);


    }

    private static void delete(SolutionTemplateBuilder builder, String templateId) {
        // delete solution template
        boolean deleted = builder.deleteSolutionTemplateById(templateId);
        System.out.println("deleted: " + deleted);
    }
}
