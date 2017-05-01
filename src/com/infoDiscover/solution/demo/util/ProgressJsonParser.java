package com.infoDiscover.solution.demo.util;

import com.infoDiscover.common.util.DateUtil;
import com.infoDiscover.common.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun.
 */
public class ProgressJsonParser {

    private final static Logger logger = LogManager.getLogger(ProgressJsonParser.class);

    // get progresses JsonNode array
    public static JsonNode getProgressesNode(String json) {
        return getJsonNodes(JsonConstants.JSON_PROGRESSES, json);
    }

    // get progress JsonNode
    public static JsonNode getProgressNode(String json) {
        return getJsonNodes(JsonConstants.JSON_PROGRESS, json);
    }

    public static JsonNode getTaskNodes(String json) {
        return getJsonNodes(JsonConstants.JSON_TASKS, json);
    }

    public static JsonNode getDataNode(String json) {
        if (json == null) {
            return null;
        }

        try {
            JsonNode jsonNode = JsonUtil.string2JsonNode(json);
            return jsonNode.get(JsonConstants.JSON_DATA);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return null;
    }


    public static JsonNode getJsonNodes(String property, String json) {
        JsonNode dataNode = getDataNode(json);
        if (dataNode == null) {
            return null;
        }
        return dataNode.get(property);
    }

    /*
       {
        "properties": [
                    {
                        "propertyType": "String",
                        "propertyName": "type",
                        "propertyValue": "Progress"
                    },
                    {
                        "propertyType": "Date",
                        "propertyName": "startTime",
                        "propertyValue": 1454256000000
                    }
                ]
         }
    * */
    public static Map<String, Object> jsonNodeToMap(JsonNode jsonNode) {
        Map<String, Object> properties = new HashMap<String, Object>();

        JsonNode propertiesNode = getPropertiesJsonNode(jsonNode);
        if (propertiesNode == null) {
            return null;
        }

        for (JsonNode propertyNode : propertiesNode) {
            String propertyName = propertyNode.get(JsonConstants.JSON_PROPERTY_NAME).asText();
            String propertyType = propertyNode.get(JsonConstants.JSON_PROPERTY_TYPE).asText();

            Object propertyValue = getPropertyValue(propertyType, propertyNode);
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

    private static Object getPropertyValue(String propertyType, JsonNode jsonNode) {
        Object propertyValue = null;

        JsonNode value = jsonNode.get(JsonConstants.JSON_PROPERTY_VALUE);
        boolean isNotNullValue = (value != null);

        if (propertyType.equalsIgnoreCase("String")) {
            propertyValue = isNotNullValue ? value.asText() : null;
        } else if (propertyType.equalsIgnoreCase("Int") || propertyType.equalsIgnoreCase
                ("Integer")) {
            propertyValue = isNotNullValue ? value.asInt() : null;
        } else if (propertyType.equalsIgnoreCase("Long")) {
            propertyValue = isNotNullValue ? value.asLong() : null;
        } else if (propertyType.equalsIgnoreCase("Float") || propertyType.equalsIgnoreCase
                ("Double")) {
            propertyValue = isNotNullValue ? value.asDouble() : null;
        } else if (propertyType.equalsIgnoreCase("boolean") || propertyType.equalsIgnoreCase
                ("bool")) {
            propertyValue = isNotNullValue ? value.asBoolean() : null;
        } else if (propertyType.equalsIgnoreCase("Date") || propertyType.equalsIgnoreCase
                ("DateTime")) {
            if (isNotNullValue) {
                Long dateValueInLong = value.asLong();
                propertyValue = DateUtil.getDateTime(dateValueInLong).toDate();
            }
        } else {
            propertyValue = isNotNullValue ? value.asText() : null;
        }

        return propertyValue;
    }

}
