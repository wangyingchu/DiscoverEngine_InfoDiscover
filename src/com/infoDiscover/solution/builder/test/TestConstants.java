package com.infoDiscover.solution.builder.test;

import com.infoDiscover.solution.builder.SolutionConstants;

/**
 * Created by sun.
 */
public class TestConstants {
    public static final String spaceName = "TEST_SOLUTION";
    public static final String prefix = "Test2_";
    public static final String factType = SolutionConstants.FACT_TYPE_SOLUTION_TEMPLATE;

    public static final String testDataRoot =
            "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover" +
                    "/solution/builder/testtemplate";

    public static final String templateFile = testDataRoot + "/SolutionTemplateDefinition.json";
    public static final String factDefinition = testDataRoot + "/FactDefinition.json";
    public static final String dimensionDefinition = testDataRoot + "/DimensionDefinition" +
            ".json";
    public static final String relationDefinition = testDataRoot + "/RelationDefinition" +
            ".json";
}
