package com.infoDiscover.solution.template.test;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.solution.template.DataImporter;

/**
 * Created by sun on 7/23/17.
 */
public class DataImporterByRulesTest {
    static String testDataFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/template/test/TestData.json";
    static String intTestDataFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/template/test/IntTestData.json";
    static String booleanTestDataFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/template/test/BooleanTestData.json";
    static String testDimensionDataFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/template/test/TestData_Dimension.json";
    static String duplicatedCopyTestDataFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/template/test/DuplicateCopyTestData.json";
    static String duplicatedCopyTargetTestDataFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/template/test/DuplicateCopyTargetTestData.json";
    static String spaceName = "test_1";

    static String rulesFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/template/test/TestRules.json";

    public static void main(String[] args) throws Exception {
        DataImporter importer = new DataImporter(spaceName);

        // test data
        String testDataJson = FileUtil.readFileContent(testDataFile);
        String rulesDataJson = FileUtil.readFileContent(rulesFile);

        importer.importData(testDataJson, rulesDataJson, true);

    }
}
