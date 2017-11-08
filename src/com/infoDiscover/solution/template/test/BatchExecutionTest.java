package com.infoDiscover.solution.template.test;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.solution.template.BatchExecution;

/**
 * Created by sun on 7/23/17.
 */
public class BatchExecutionTest {

    static String spaceName = "test-fact-to-dim";

    static String ruleFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/template/test/TestRule.json";

    public static void main(String[] args) throws Exception {
        BatchExecution importer = new BatchExecution(spaceName);

        // test data
        String rulesDataJson = FileUtil.readFileContent(ruleFile);

        importer.batchUpdateWithRule(rulesDataJson);

    }
}
