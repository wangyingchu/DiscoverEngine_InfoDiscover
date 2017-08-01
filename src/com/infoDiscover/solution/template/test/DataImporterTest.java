package com.infoDiscover.solution.template.test;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.solution.template.DataImporter;

/**
 * Created by sun on 7/23/17.
 */
public class DataImporterTest {
    static String testDataFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/template/test/TestData.json";
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
        String duplicatedCopyTestDataFileJson = FileUtil.readFileContent(duplicatedCopyTestDataFile);
        importer.importData(duplicatedCopyTestDataFileJson, true);


    }
}
