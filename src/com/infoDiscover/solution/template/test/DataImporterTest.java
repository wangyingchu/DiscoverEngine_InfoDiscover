package com.infoDiscover.solution.template.test;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.solution.template.DataImporter;

/**
 * Created by sun on 7/23/17.
 */
public class DataImporterTest {
    static String testDataFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/template/test/TestData.json";
    static String duplicatedCopyTestDataFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/template/test/DuplicateCopyTestData.json";
    static String duplicatedCopyTargetTestDataFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/template/test/DuplicateCopyTargetTestData.json";
    static String spaceName = "solution_test_2";

    public static void main(String[] args) {
        DataImporter importer = new DataImporter(spaceName);


        String json = FileUtil.readFileContent(duplicatedCopyTargetTestDataFile);
        try {
            importer.importData(json,true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
