package com.infoDiscover.solution.construction.supervision.sample;

import com.infoDiscover.solution.common.util.RandomData;
import org.codehaus.jackson.JsonNode;

import java.util.Map;

/**
 * Created by sun.
 */
public class SampleRandomDataGenHelper {

    public static int minValue = 1;
    public static int maxValue = 20;
    public static double minDoubleValue = 1000.0d;
    public static double maxDoubleValue = 100000.0d;
    public static int randomStringLength = 9;
    public static long longValue = 10000l;

//    public static Map<String, Object> jsonNodeToMapWithRandomValue(JsonNode propertiesJsonNode) {
//        return RandomData.propertiesJsonNodeToMapWithRandomValue(propertiesJsonNode, randomStringLength,
//                minValue, maxValue, minDoubleValue, maxDoubleValue, longValue,
//                reservedStringPropertyNames(), reservedDoublePropertyNames(),
//                reservedTimePropertyNames());
//    }
}
