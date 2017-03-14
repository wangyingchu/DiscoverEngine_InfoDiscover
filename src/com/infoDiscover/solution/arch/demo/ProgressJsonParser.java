package com.infoDiscover.solution.arch.demo;

import com.infoDiscover.common.util.DateUtil;
import com.infoDiscover.common.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun.
 */
public class ProgressJsonParser {

    private final static Logger logger = LogManager.getLogger(ProgressJsonParser.class);

    // get progresses JsonNode, it is an array
    public static JsonNode getProgressNodes(String json) {
        return getJsonNodes(JsonConstants.JSON_PROGRESSES, json);
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


    public static JsonNode getJsonNodes(String type, String json) {
        JsonNode dataNode = getDataNode(json);
        if (dataNode == null) {
            return null;
        }
        return dataNode.get(type);
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

        if (propertyType.equalsIgnoreCase("String")) {
            if (jsonNode.get(JsonConstants.JSON_PROPERTY_VALUE) != null) {
                propertyValue = jsonNode.get(JsonConstants.JSON_PROPERTY_VALUE).asText();
            }
        } else if (propertyType.equalsIgnoreCase("Int") || propertyType.equalsIgnoreCase
                ("Integer")) {
            if (jsonNode.get(JsonConstants.JSON_PROPERTY_VALUE) != null){
                propertyValue = jsonNode.get(JsonConstants.JSON_PROPERTY_VALUE).asInt();}
        } else if (propertyType.equalsIgnoreCase("Long")) {
            if (jsonNode.get(JsonConstants.JSON_PROPERTY_VALUE) != null) {
                propertyValue = jsonNode.get(JsonConstants.JSON_PROPERTY_VALUE).asLong();}
        } else if (propertyType.equalsIgnoreCase("Float") || propertyType.equalsIgnoreCase
                ("Double")) {
            if (jsonNode.get(JsonConstants.JSON_PROPERTY_VALUE) != null){
                propertyValue = jsonNode.get(JsonConstants.JSON_PROPERTY_VALUE).asDouble();}
        } else if (propertyType.equalsIgnoreCase("boolean") || propertyType.equalsIgnoreCase
                ("bool")) {
            if (jsonNode.get(JsonConstants.JSON_PROPERTY_VALUE) != null){
                propertyValue = jsonNode.get(JsonConstants.JSON_PROPERTY_VALUE).asBoolean();}
        } else if (propertyType.equalsIgnoreCase("Date") || propertyType.equalsIgnoreCase
                ("DateTime")) {
            if (jsonNode.get(JsonConstants.JSON_PROPERTY_VALUE) != null) {
                Long dateValueInLong = jsonNode.get(JsonConstants.JSON_PROPERTY_VALUE).asLong();
                propertyValue = DateUtil.getDateTime(dateValueInLong);
            }
        } else {
            if (jsonNode.get(JsonConstants.JSON_PROPERTY_VALUE) != null)
                propertyValue = jsonNode.get(JsonConstants.JSON_PROPERTY_VALUE).asText();
        }

        return propertyValue;
    }

}
