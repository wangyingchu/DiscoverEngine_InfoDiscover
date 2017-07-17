package com.infoDiscover.solution.template.test;

import com.infoDiscover.infoDiscoverEngine.util.config.PropertyHandler;
import com.infoDiscover.solution.template.TemplateExporter;

/**
 * Created by sun on 7/17/17.
 */
public class TemplateExporterTest {
    static String solutionName = "test";
    static String metaSpace = PropertyHandler.getPropertyValue(PropertyHandler.META_CONFIG_DISCOVERSPACE);

    public static void main(String[] args) {
        TemplateExporter exporter = new TemplateExporter(metaSpace, solutionName);

        try {
            exporter.exportSolutionTemplate("/Users/sun/Desktop/template");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
