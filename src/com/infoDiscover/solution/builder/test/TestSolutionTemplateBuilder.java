package com.infoDiscover.solution.builder.test;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.solution.builder.SolutionTemplateBuilder;
import com.infoDiscover.solution.builder.SolutionTemplateInitializer;
import com.infoDiscover.solution.common.util.PrefixSetting;

/**
 * Created by sun.
 */
public class TestSolutionTemplateBuilder {

    public static void main(String[] args) throws Exception {

        // 1. initialize solution template space and fact type
        SolutionTemplateInitializer.initializeSolutionTemplate(TestConstants.spaceName,
                TestConstants.prefix);

        // 2. save solution template
        String templateJson = FileUtil.readFileContent(TestConstants.templateFile);
        System.out.println("templateJson: " + templateJson);

        SolutionTemplateBuilder builder = new SolutionTemplateBuilder(TestConstants.spaceName,
                PrefixSetting
                        .normalizePrefix(TestConstants.prefix) + TestConstants.factType);
        builder.createNewOrUpdateTemplate(TestConstants.prefix, templateJson);
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
