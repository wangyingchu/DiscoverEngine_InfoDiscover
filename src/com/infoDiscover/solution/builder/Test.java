package com.infoDiscover.solution.builder;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.construction.supervision.util.PrefixManager;

/**
 * Created by sun.
 */
public class Test {

    private final static String spaceName = SolutionConstants.SOLUTION_SPACE_NAME;
    private final static String prefix = "Test_";
    private final static String factType = SolutionConstants.FACT_TYPE_SOLUTION_TEMPLATE;

    private final static String testDataRoot =
            "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/builder/testdata";

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
        SolutionTemplateBuilder builder = new SolutionTemplateBuilder(ids, PrefixManager
                .normalizePrefix(prefix) + factType);
        if (ids != null) {
            builder.createNewOrUpdateTemplate(templateJson);
        }


        // update prefix
        String templateId = "0b80aec2-572c-4d9f-a3bd-7b1918e7259e";
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
        String relationJson= FileUtil.readFileContent(relationDefinition);
        builder.updateRelationDefinition(templateId,relationJson);

        // delete solution template
        //boolean deleted = builder.deleteSolutionTemplateById(templateId);
        //System.out.println("deleted: " + deleted);



        //
        SolutionImporter importer = new SolutionImporter(prefix);
        importer.generateFromTemplate(ids);
        ids.closeSpace();
    }
}
