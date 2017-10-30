package com.infoDiscover.solution.template.test;

import com.info.discover.ruleengine.solution.SolutionRelationMapping;
import com.info.discover.ruleengine.solution.contants.RelationMappingType;
import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.infoDiscoverEngine.dataMart.RelationType;
import com.infoDiscover.solution.template.DataImporter;

/**
 * Created by sun on 7/23/17.
 */
public class DataImporterTest {
    static String testDataFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/template/test/TestData.json";
    static String intTestDataFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/template/test/IntTestData.json";
    static String booleanTestDataFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/template/test/BooleanTestData.json";
    static String testDimensionDataFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/template/test/TestData_Dimension.json";
    static String duplicatedCopyTestDataFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/template/test/DuplicateCopyTestData.json";
    static String duplicatedCopyTargetTestDataFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/template/test/DuplicateCopyTargetTestData.json";
    static String spaceName = "test_1";


    public static void main(String[] args) throws Exception {
        DataImporter importer = new DataImporter(spaceName);


        // dimension to date mapping
//        String testDimensionDataFileJson = FileUtil.readFileContent(testDimensionDataFile);
//        importer.importData(testDimensionDataFileJson, true);

        // duplicatedCopyTestDataFileJson
//        String duplicatedCopyTestDataFileJson = FileUtil.readFileContent(duplicatedCopyTestDataFile);
//        importer.importData(duplicatedCopyTestDataFileJson, true);

        // test data
        String testDataJson = FileUtil.readFileContent(testDataFile);
        for (int i = 0; i < 100; i++) {
            importer.importData(testDataJson, true);
        }


        // int test data
//        String intTestDataJson = FileUtil.readFileContent(intTestDataFile);
//        importer.importData(intTestDataJson, true);

        // boolean test data
//        String booleanTestDataJson = FileUtil.readFileContent(booleanTestDataFile);
//        importer.importData(booleanTestDataJson, true);
    }
}
