package com.infoDiscover.solution.template.test;

import com.infoDiscover.infoDiscoverEngine.util.config.PropertyHandler;
import com.infoDiscover.solution.template.DDLExporter;

/**
 * Created by sun on 7/17/17.
 */
public class DDLExporterTest {
    public static void main(String[] args) {
        String solutionName = "test";
        String metaSpace = PropertyHandler.getPropertyValue(PropertyHandler.META_CONFIG_DISCOVERSPACE);

        DDLExporter export = new DDLExporter(metaSpace,solutionName);

        String factTypeDefinitions = export.generateFactTypeDefinitionDDL();
        System.out.println("factTypeDefinitions: " + factTypeDefinitions);

        String dimensionTypeDefinitions = export.generateDimensionTypeDefinitionDDL();
        System.out.println("dimensionTypeDefinitions: " + dimensionTypeDefinitions);

        String relationTypeDefinitions = export.generateRelationTypeDefinitionDDL();
        System.out.println("relationTypeDefinitions: " + relationTypeDefinitions);

        String factToFactMapping = export.generateFactToFactDefinitionDDL();
        System.out.println("factToFactMapping: " + factToFactMapping);

        String factToDimensionMapping = export.generateFactToDimensionDefinitionDDL();
        System.out.println("factToDimensionMapping: " + factToDimensionMapping);

        String dimensionToDimensionMapping = export.generateDimensionToDimensionDefinitionDDL();
        System.out.println("dimensionToDimensionMapping: " + dimensionToDimensionMapping);

    }
}
