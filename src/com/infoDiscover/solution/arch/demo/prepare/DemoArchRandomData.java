package com.infoDiscover.solution.arch.demo.prepare;

import com.infoDiscover.solution.common.util.RandomData;
import org.codehaus.jackson.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sun.
 */
public class DemoArchRandomData {

    public static int randomIntRange[] = {1, 20};
    public static double randomDoubleRange[] = {1000.0d, 10000.0d};
    public static int randomStringLength = 9;
    public static long longValue = 10000l;
    public static int randomYearRange[] = {2010, 2010};

    public static Map<String, Object> jsonNodeToMapWithRandomValue(JsonNode propertiesJsonNode) {
        return RandomData.propertiesJsonNodeToMapWithRandomValue(propertiesJsonNode, randomStringLength,
                randomIntRange, randomDoubleRange, longValue, randomYearRange,
                reservedPropertyNames());
    }

    public static List<String> reservedPropertyNames() {
        List<String> list = new ArrayList<>();
        list.addAll(reservedStringPropertyNames());
        list.addAll(reservedDoublePropertyNames());
        list.addAll(reservedTimePropertyNames());
        return list;
    }

    public static List<String> reservedStringPropertyNames() {
        List<String> list = new ArrayList<>();
        list.add("progressId");
//        list.add("taskId");
        list.add("taskName");
        list.add("type");
        list.add("progressName");
        list.add("starter");
        list.add("status");
        list.add("departmentId");
        list.add("assignee");

        return list;
    }

    public static List<String> reservedDoublePropertyNames() {
        List<String> list = new ArrayList<>();
        list.add("dituxinxi_jingdu");
        list.add("dituxinxi_weidu");

        return list;
    }

    public static List<String> reservedTimePropertyNames() {
        List<String> list = new ArrayList<>();
        list.add("startTime");
        list.add("endTime");

        return list;
    }

}
