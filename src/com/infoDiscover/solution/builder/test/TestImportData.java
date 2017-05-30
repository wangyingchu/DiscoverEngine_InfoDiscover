package com.infoDiscover.solution.builder.test;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.solution.builder.DataImporter;

import java.io.IOException;

/**
 * Created by sun.
 */
public class TestImportData {

    private static String testDataFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com" +
            "/infoDiscover/solution/builder/testdata/TestData.json";

    public static void main(String[] args) throws IOException {

        String json = FileUtil.readFileContent(testDataFile);

        DataImporter importData = new DataImporter(TestConstants.spaceName, TestConstants.prefix);
        try {
            importData.importData(json, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
