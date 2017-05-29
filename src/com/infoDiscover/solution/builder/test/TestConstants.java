package com.infoDiscover.solution.builder.test;

import com.infoDiscover.solution.builder.SolutionConstants;

/**
 * Created by sun.
 */
public class TestConstants {
    public final static String spaceName = "TEST_SOLUTION";
    public final static String prefix = "Test2_";
    public final static String factType = SolutionConstants.FACT_TYPE_SOLUTION_TEMPLATE;

    public final static String testDataRoot =
            "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover" +
                    "/solution/builder/templatedata";

    public final static String templateFile = testDataRoot + "/SolutionTemplateDefinition.json";
    public final static String factDefinition = testDataRoot + "/FactDefinition.json";
    public final static String dimensionDefinition = testDataRoot + "/DimensionDefinition" +
            ".json";
    public final static String relationDefinition = testDataRoot + "/RelationDefinition" +
            ".json";
}
