package com.infoDiscover.solution.template.test;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.solution.template.DataImporter;

/**
 * Created by sun on 7/23/17.
 */
public class DataImporter2Test {
    static String testDataFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/template/test/TestData.json";
    static String spaceName = "Test";
    static String solutionName = "test";

    public static void main(String[] args) {
        DataImporter importer2 = new DataImporter(spaceName,solutionName);


        String json = FileUtil.readFileContent(testDataFile);
        try {
            importer2.importData(json,true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
