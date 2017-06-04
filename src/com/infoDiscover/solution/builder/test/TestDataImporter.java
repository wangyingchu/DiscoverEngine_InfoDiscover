package com.infoDiscover.solution.builder.test;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.solution.builder.DataImporter;
import com.infoDiscover.solution.common.util.PrefixSetting;

import java.io.IOException;

/**
 * Created by sun.
 */
public class TestDataImporter {

    private static String testDataFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com" +
            "/infoDiscover/solution/builder/testdata/TestData.json";

    public static void main(String[] args) throws IOException {

        String json = FileUtil.readFileContent(testDataFile);

        String prefix = PrefixSetting.prefix;
        DataImporter importData = new DataImporter(TestConstants.spaceName, prefix);
        try {
            importData.importData(json, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
