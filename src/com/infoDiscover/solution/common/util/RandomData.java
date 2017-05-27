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
                                                                             int randomIntRange[],
                                                                             double
                                                                                     randomDoubleRange[],
                                                                             long longValue,
                                                                             int randomYearRange[],
                                                                             List<String>
                                                                                     reservedPropertyNames) {
        Map<String, Object> properties = new HashMap<String, Object>();

        JsonNode propertiesNode = JsonNodeUtil.getPropertiesJsonNode(propertiesJsonNode);
        if (propertiesNode == null) {
            return null;
        }

        for (JsonNode propertyNode : propertiesNode) {
            String propertyName = propertyNode.get(JsonConstants.JSON_PROPERTY_NAME).asText();
            Object propertyValue = generateRandomPropertyValue(propertyNode,
                    randomStringLength,
                    randomIntRange,
                    randomDoubleRange,
                    longValue, randomYearRange, reservedPropertyNames);
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
                                                     int randomIntRange[],
                                                     double randomDoubleRange[],
                                                     long longValue,
                                                     int randomYearRange[],
                                                     List<String> reservedPropertyNames) {
        Object propertyValue = null;

        String propertyType = propertyJsonNode.get(JsonConstants.JSON_PROPERTY_TYPE).asText();
        String propertyName = propertyJsonNode.get(JsonConstants.JSON_PROPERTY_NAME).asText();

        JsonNode propertyValueNode = propertyJsonNode.get(JsonConstants.JSON_PROPERTY_VALUE);

        if (propertyType.equalsIgnoreCase("String")) {
            if (reservedPropertyNames == null || !reservedPropertyNames.contains(propertyName)) {
                propertyValue = getRandomString(propertyValueNode, randomStringLength);
            } else {
                propertyValue = propertyValueNode.asText();
            }
        } else if (propertyType.equalsIgnoreCase("Int") || propertyType.equalsIgnoreCase
                ("Integer")) {
            if (reservedPropertyNames == null || !reservedPropertyNames.contains(propertyName)) {
                propertyValue = getRandomInt(propertyValueNode, randomIntRange);
            } else {
                propertyValue = propertyValueNode.asInt();
            }
        } else if (propertyType.equalsIgnoreCase("Long")) {
            if (reservedPropertyNames == null || !reservedPropertyNames.contains(propertyName)) {
                propertyValue = getRandomLong(propertyValueNode, longValue);
            } else {
                propertyValue = propertyValueNode.asLong();
            }
        } else if (propertyType.equalsIgnoreCase("Float") || propertyType.equalsIgnoreCase
                ("Double")) {
            if (reservedPropertyNames == null || !reservedPropertyNames.contains
                    (propertyName)) {
                propertyValue = getRandomDouble(propertyValueNode, randomDoubleRange);
            } else {
                propertyValue = propertyValueNode.asDouble();
            }

        } else if (propertyType.equalsIgnoreCase("boolean") || propertyType.equalsIgnoreCase
                ("bool")) {
            propertyValue = getBooleanValue(propertyValueNode);
        } else if (propertyType.equalsIgnoreCase("Date") || propertyType.equalsIgnoreCase
                ("DateTime")) {
            if (reservedPropertyNames == null || !reservedPropertyNames.contains
                    (propertyName)) {
                propertyValue = getRandomTime(randomYearRange);
            } else {
                propertyValue = getDateValue(propertyValueNode);
            }
        } else {
            if (reservedPropertyNames == null || !reservedPropertyNames.contains
                    (propertyName)) {
                propertyValue = getRandomString(propertyValueNode, randomStringLength);
            } else {
                propertyValue = propertyValueNode.asText();
            }
        }

        return propertyValue;
    }

    public static long getRandomTime(int yearRange[]) {
        int minYear = yearRange[0];
        int maxYear = yearRange[1];
        int plusDays = yearRange.length == 3 ? yearRange[2] : RandomUtil.generateRandomInRange
                (1, 365);
        return getRandomTime(minYear, maxYear, plusDays);
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

    public static int getRandomInt(JsonNode valueNode, int range[]) {
        int randomInt = RandomUtil.generateRandomInRange(range);
        return valueNode == null ? randomInt :
                valueNode.asInt() + randomInt;
    }

    public static long getRandomLong(JsonNode valueNode, long value) {
        long randomLong = RandomUtil.generateRandomLong(value);
        return valueNode == null ? randomLong : valueNode.asLong() + randomLong;
    }

    public static double getRandomDouble(JsonNode valueNode, double range[]) {

        DecimalFormat df = new DecimalFormat("######0.00");
        double randomDouble = Double.valueOf(df.format(RandomUtil.generateRandomDouble(range)));
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
