package com.infoDiscover.solution.builder;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;

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

        ImportData importData = new ImportData(ids, prefix);
        try {
            importData.importData(json, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ids.closeSpace();
    }
}
