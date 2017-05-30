package com.infoDiscover.solution.builder.test;

import com.infoDiscover.solution.builder.SolutionTemplateImporter;

/**
 * Created by sun.
 */
public class TestSolutionTemplateImporter {

    public static void main(String[] args) throws Exception {
        SolutionTemplateImporter importer = new SolutionTemplateImporter(TestConstants
                .spaceName, TestConstants.prefix);

//        importer.importSolutionTemplate(TestConstants.templateFile, true);
//
//        importer.exportSolutionTemplate("/Users/sun/Desktop/template");
        importer.importSolutionTemplateWithZipped
                ("/Users/sun/Desktop/template/TEST2_SolutionTemplateDefinition.zip", true);
    }
}
