package com.infoDiscover.solution.common.util;

import com.infoDiscover.common.dimension.time.TimeDimensionGenerator;
import com.infoDiscover.common.util.DateUtil;
import com.infoDiscover.common.util.RandomUtil;
import com.infoDiscover.solution.construction.supervision.constants.JsonConstants;
import org.codehaus.jackson.JsonNode;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sun.
 */
public class RandomData {

    /*
    * propertiesJsonNode:
    * "properties": [
            {
                "propertyName": "userID",
                "propertyValue": "",
                "propertyType": "String"
            },
            {
                "propertyName": "name",
                "propertyValue": "",
                "propertyType": "String"
            }
        ]
    * */
    public static Map<String, Object> propertiesJsonNodeToMapWithRandomValue(JsonNode
                                                                                     propertiesJsonNode,
                                                                             int randomStringLength,
                                                                             int minValue,
                                                                             int maxValue,
                                                                             double minDoubleValue,
                                                                             double maxDoubleValue,
                                                                             long longValue,
                                                                             List<String>
                                                                                     reservedStringPropertyNames,
                                                                             List<String>
                                                                                     reservedDoublePropertyNames,
                                                                             List<String>
                                                                                     reservedTimePropertyNames) {
        Map<String, Object> properties = new HashMap<String, Object>();

        JsonNode propertiesNode = JsonNodeUtil.getPropertiesJsonNode(propertiesJsonNode);
        if (propertiesNode == null) {
            return null;
        }

        for (JsonNode propertyNode : propertiesNode) {
            String propertyName = propertyNode.get(JsonConstants.JSON_PROPERTY_NAME).asText();
            Object propertyValue = generateRandomPropertyValue(propertyNode,
                    randomStringLength,
                    minValue, maxValue,
                    minDoubleValue, maxDoubleValue,
                    longValue, reservedStringPropertyNames, reservedDoublePropertyNames,
                    reservedTimePropertyNames);
            if (propertyValue != null) {
                properties.put(propertyName, propertyValue);
            }
        }

        return properties;
    }


    /*
        propertyJsonNode format:
        {
          "propertyType": "String",
          "propertyName": "type",
          "propertyValue": "Progress"
        }
     */
    public static Object generateRandomPropertyValue(JsonNode propertyJsonNode,
                                                     int randomStringLength,
                                                     int minValue,
                                                     int maxValue,
                                                     double minDoubleValue,
                                                     double maxDoubleValue,
                                                     long longValue,
                                                     List<String> reservedStringPropertyNames,
                                                     List<String>
                                                             reservedDoublePropertyNames,
                                                     List<String> reservedTimePropertyNames) {
        Object propertyValue = null;

        String propertyType = propertyJsonNode.get(JsonConstants.JSON_PROPERTY_TYPE).asText();
        String propertyName = propertyJsonNode.get(JsonConstants.JSON_PROPERTY_NAME).asText();

        JsonNode propertyValueNode = propertyJsonNode.get(JsonConstants.JSON_PROPERTY_VALUE);

        if (propertyType.equalsIgnoreCase("String")) {
            if (reservedStringPropertyNames == null || !reservedStringPropertyNames.contains(propertyName)) {
                propertyValue = getRandomString(propertyValueNode, randomStringLength);
            } else {
                propertyValue = propertyValueNode.asText();
            }
        } else if (propertyType.equalsIgnoreCase("Int") || propertyType.equalsIgnoreCase
                ("Integer")) {
            propertyValue = getRandomInt(propertyValueNode, minValue, maxValue);
        } else if (propertyType.equalsIgnoreCase("Long")) {
            propertyValue = getRandomLong(propertyValueNode, longValue);
        } else if (propertyType.equalsIgnoreCase("Float") || propertyType.equalsIgnoreCase
                ("Double")) {
            if (reservedDoublePropertyNames == null || !reservedDoublePropertyNames.contains(propertyName)) {
                propertyValue = getRandomDouble(propertyValueNode, minDoubleValue, maxDoubleValue);
            } else {
                propertyValue = propertyValueNode.asDouble();
            }

        } else if (propertyType.equalsIgnoreCase("boolean") || propertyType.equalsIgnoreCase
                ("bool")) {
            propertyValue = getBooleanValue(propertyValueNode);
        } else if (propertyType.equalsIgnoreCase("Date") || propertyType.equalsIgnoreCase
                ("DateTime")) {
            if (reservedTimePropertyNames == null) {
                propertyValue = getDateValue(propertyValueNode);
            } else {
                if (!reservedTimePropertyNames.contains(propertyName)) {
                    propertyValue = getDateValue(propertyValueNode);
                }
            }
        } else {
            if (reservedStringPropertyNames == null || !reservedStringPropertyNames.contains(propertyName)) {
                propertyValue = getRandomString(propertyValueNode, randomStringLength);
            } else {
               propertyValue = propertyValueNode.asText();
            }
        }

        return propertyValue;
    }

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

        DecimalFormat df = new DecimalFormat("######0.00");
        double randomDouble = Double.valueOf(df.format(RandomUtil.generateRandomDouble(min, max)));
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
}
