package com.infoDiscover.solution.template.test;

import com.infoDiscover.infoDiscoverEngine.util.config.PropertyHandler;
import com.infoDiscover.solution.template.TemplateImporter;

/**
 * Created by sun on 7/17/17.
 */
public class TemplateImporterTest {
    static String solutionName = "test";
    static String metaSpace = PropertyHandler.getPropertyValue(PropertyHandler.META_CONFIG_DISCOVERSPACE);

    public static void main(String[] args) {
        TemplateImporter importer=new TemplateImporter(metaSpace,solutionName);

        try {
            importer.importSolution("/Users/sun/Desktop/template/test_SolutionTemplateDefinition.zip", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
