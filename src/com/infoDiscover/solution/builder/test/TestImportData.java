package com.infoDiscover.solution.builder.test;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.builder.DataImporter;

import java.io.IOException;

/**
 * Created by sun.
 */
public class TestImportData {

    private static String file = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com" +
            "/infoDiscover/solution/builder/testdata/TestData.json";

    private final static String spaceName = "TEST_SOLUTION";
    private final static String prefix = "Test_";

    public static void main(String[] args) throws IOException {

        InfoDiscoverSpace ids = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);

        String json = FileUtil.readFileContent(file);

        DataImporter importData = new DataImporter(ids, prefix);
        try {
            importData.importData(json, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ids.closeSpace();
    }
}
