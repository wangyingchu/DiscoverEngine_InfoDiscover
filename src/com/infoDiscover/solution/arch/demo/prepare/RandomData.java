package com.infoDiscover.solution.arch.demo.prepare;

import com.infoDiscover.common.dimension.time.TimeDimensionGenerator;
import com.infoDiscover.common.util.DateUtil;
import com.infoDiscover.common.util.RandomUtil;
import com.infoDiscover.solution.arch.demo.JsonConstants;
import org.codehaus.jackson.JsonNode;

import java.util.*;

/**
 * Created by sun.
 */
public class RandomData {

    public static int minValue = 1;
    public static int maxValue = 20;
    public static double minDoubleValue = 1000.0d;
    public static double maxDoubleValue = 100000.0d;
    public static int randomStringLength = 9;
    public static long longValue = 10000l;

    public static long getRandomTime(int minYear, int maxYear, int plusDays) {
        int year = RandomUtil.generateRandomInRange(minYear, maxYear);
        int month = RandomUtil.generateRandomInRange(1, 12);
        int daysOfMonth = TimeDimensionGenerator.getDaysOfMonth(year).get(month);
        int day = RandomUtil.generateRandomInRange(1, daysOfMonth);

        return DateUtil.getLongDateValue(year, month, day, plusDays);
    }

    public static double generateRandomeMoney(double min, double max) {
        return RandomUtil.generateRandomDouble(min, max);
    }

    public static Map<String, Object> jsonNodeToMapWithRandomValue(JsonNode jsonNode) {
        Map<String, Object> properties = new HashMap<String, Object>();

        JsonNode propertiesNode = getPropertiesJsonNode(jsonNode);
        if (propertiesNode == null) {
            return null;
        }

        for (JsonNode propertyNode : propertiesNode) {
            String propertyName = propertyNode.get(JsonConstants.JSON_PROPERTY_NAME).asText();
            Object propertyValue = getRandomPropertyValue(propertyNode, randomStringLength,
                    minValue, maxValue,
                    minDoubleValue, maxDoubleValue,
                    longValue);
            if (propertyValue != null) {
                properties.put(propertyName, propertyValue);
            }
        }

        return properties;
    }

    public static JsonNode getPropertiesJsonNode(JsonNode jsonNode) {
        if (jsonNode == null) {
            return null;
        }
        return jsonNode.get(JsonConstants.JSON_PROPERTIES);
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

    public static String getRandomString(JsonNode valueNode, int randomLength) {
        String randomString = RandomUtil.generateRandomString(randomLength);
        return valueNode == null ? randomString : valueNode
                .asText() + "_" + randomString;
    }

    public static int getRandomInt(JsonNode valueNode, int minValue, int maxValue) {
        int randomInt = RandomUtil.generateRandomInRange(minValue, maxValue);
        return valueNode == null ? randomInt :
                valueNode.asInt() + randomInt;
    }

    public static long getRandomLong(JsonNode valueNode, long value) {
        long randomLong = RandomUtil.generateRandomLong(value);
        return valueNode == null ? randomLong : valueNode.asLong() + randomLong;
    }

    public static double getRandomDouble(JsonNode valueNode, double min, double max) {
        double randomDouble = RandomUtil.generateRandomDouble(min, max);
        return valueNode == null ? randomDouble : valueNode
                .asDouble() + randomDouble;
    }

    public static boolean getBooleanValue(JsonNode valueNode) {
        return valueNode == null ? false : valueNode.asBoolean();
    }

    public static Date getDateValue(JsonNode valueNode) {
        if (valueNode == null) {
            return null;
        }

        Long dateValueInLong = valueNode.asLong();
        return DateUtil.getDateTime(dateValueInLong).toDate();
    }

    /*
        propertyJsonNode format:
        {
          "propertyType": "String",
          "propertyName": "type",
          "propertyValue": "Progress"
        }
     */
    private static Object getRandomPropertyValue(JsonNode propertyJsonNode, int randomLength, int
            minValue, int maxValue, double minDoubleValue, double maxDoubleValue, long longValue) {
        Object propertyValue = null;

        String propertyType = propertyJsonNode.get(JsonConstants.JSON_PROPERTY_TYPE).asText();
        String propertyName = propertyJsonNode.get(JsonConstants.JSON_PROPERTY_NAME).asText();

        JsonNode propertyValueNode = propertyJsonNode.get(JsonConstants.JSON_PROPERTY_VALUE);

        if (propertyType.equalsIgnoreCase("String")) {

            if (!reservedStringPropertyNames().contains(propertyName)) {
                propertyValue = getRandomString(propertyValueNode, randomLength);
            }

        } else if (propertyType.equalsIgnoreCase("Int") || propertyType.equalsIgnoreCase
                ("Integer")) {
            propertyValue = getRandomInt(propertyValueNode, minValue, maxValue);
        } else if (propertyType.equalsIgnoreCase("Long")) {
            propertyValue = getRandomLong(propertyValueNode, longValue);
        } else if (propertyType.equalsIgnoreCase("Float") || propertyType.equalsIgnoreCase
                ("Double")) {
            if (!reservedDoublePropertyNames().contains(propertyName)) {
                propertyValue = getRandomDouble(propertyValueNode, minDoubleValue, maxDoubleValue);
            }
        } else if (propertyType.equalsIgnoreCase("boolean") || propertyType.equalsIgnoreCase
                ("bool")) {
            propertyValue = getBooleanValue(propertyValueNode);
        } else if (propertyType.equalsIgnoreCase("Date") || propertyType.equalsIgnoreCase
                ("DateTime")) {
            if (!reservedTimePropertyNames().contains(propertyName)) {
                propertyValue = getDateValue(propertyValueNode);
            }
        } else {
            if (!reservedStringPropertyNames().contains(propertyName)) {
                propertyValue = getRandomString(propertyValueNode, randomLength);
            }
        }

        return propertyValue;
    }

}
