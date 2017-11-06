package com.infoDiscover.solution.template.test;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.solution.template.BatchUpdateData;

/**
 * Created by sun on 7/23/17.
 */
public class BatchUpdateDataTest {

    static String spaceName = "test_1";

    static String ruleFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/template/test/TestRule.json";

    public static void main(String[] args) throws Exception {
        BatchUpdateData importer = new BatchUpdateData(spaceName);

        // test data
        String rulesDataJson = FileUtil.readFileContent(ruleFile);

        importer.batchUpdateWithRule(rulesDataJson);

    }
}
