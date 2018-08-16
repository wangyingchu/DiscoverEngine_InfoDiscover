package com.infoDiscover.solution.template.test;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.solution.template.BatchExecution;

/**
 * Created by sun on 7/23/17.
 */
public class BatchExecutionTest {

    static String spaceName = "test-date-mapping";

    static String date_ruleFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/template/test/TestRule.json";
    static String string_ruleFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/template/test/TestRule_StringProp.json";

    public static void main(String[] args) throws Exception {
        BatchExecution importer = new BatchExecution(spaceName);

        // test data
        String rulesDataJson = FileUtil.readFileContent(string_ruleFile);

        importer.batchUpdateWithRule(rulesDataJson);

    }
}
