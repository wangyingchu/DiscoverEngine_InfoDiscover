package com.infoDiscover.solution.builder.test;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.builder.SolutionTemplateBuilder;

/**
 * Created by sun.
 */
public class TestSolutionTemplateBuilder {

    public static void main(String[] args) throws Exception {

        // create database if not existed
        createDatabase(TestConstants.spaceName);

        // create solution template
        String templateJson = FileUtil.readFileContent(TestConstants.templateFile);
        System.out.println("templateJson: " + templateJson);

        SolutionTemplateBuilder builder = new SolutionTemplateBuilder(TestConstants.spaceName,
                TestConstants.prefix);


        builder.createNewOrUpdateTemplate(templateJson, true);

        //update(builder, "2ff18dfd-ad4f-4c87-8fe6-e31026dcd9a4");
    }

    private static void createDatabase(String spaceName) {
        if (!DiscoverEngineComponentFactory.checkDiscoverSpaceExistence
                (spaceName)) {
            DiscoverEngineComponentFactory.createInfoDiscoverSpace(spaceName);
        }
    }

    private static void update(SolutionTemplateBuilder builder, String templateId) throws
            Exception {
        // update prefix
        builder.updatePrefix(templateId, TestConstants.prefix);

        // check prefix is existed
        boolean prefixExisted = builder.checkPrefixIsExisted(TestConstants.prefix);
        System.out.println("prefixExisted: " + prefixExisted);

        // update fact definition
        String factJson = FileUtil.readFileContent(TestConstants.factDefinition);
        builder.updateFactDefinition(templateId, factJson);

        // update dimension definition
        String dimensionJson = FileUtil.readFileContent(TestConstants.dimensionDefinition);
        builder.updateDimensionDefinition(templateId, dimensionJson);

        // update relation definition
        String relationJson = FileUtil.readFileContent(TestConstants.relationDefinition);
        builder.updateRelationDefinition(templateId, relationJson);


    }

    private static void delete(SolutionTemplateBuilder builder, String templateId) {
        // delete solution template
        boolean deleted = builder.deleteSolutionTemplateById(templateId);
        System.out.println("deleted: " + deleted);
    }
}
