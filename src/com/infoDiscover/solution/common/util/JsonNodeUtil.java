package com.infoDiscover.solution.common.util;

import com.infoDiscover.common.util.DateUtil;
import com.infoDiscover.common.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun.
 */
public class JsonNodeUtil {
    private final static Logger logger = LogManager.getLogger(JsonNodeUtil.class);

    public static JsonNode getDataNode(String json) {
        if (json == null) {
            return null;
        }

        try {
            JsonNode jsonNode = JsonNodeUtil.string2JsonNode(json);
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

    public static JsonNode getJsonNode(String property, JsonNode dataJsonNode) {
        if (dataJsonNode == null) {
            return null;
        }
        return dataJsonNode.get(property);
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
           */
    public static Map<String, Object> convertToPropertyNameValueMap(JsonNode propertiesNode) {
        Map<String, Object> properties = new HashMap<String, Object>();

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

    public static Object getPropertyValue(String propertyType, JsonNode jsonNode) {

        JsonNode value = jsonNode.get(JsonConstants.JSON_PROPERTY_VALUE);

        Object propertyValue;
        if (propertyType.equalsIgnoreCase("String")) {
            propertyValue = (value == null) ? "" : value.asText();
        } else if (propertyType.equalsIgnoreCase("Int") || propertyType.equalsIgnoreCase
                ("Integer") || propertyType.equalsIgnoreCase("Short")) {
            propertyValue = (value == null) ? 0 : value.asInt();
        } else if (propertyType.equalsIgnoreCase("Long")) {
            propertyValue = (value == null) ? 0 : value.asLong();
        } else if (propertyType.equalsIgnoreCase("Float") || propertyType.equalsIgnoreCase
                ("Double")) {
            propertyValue = (value == null) ? 0 : value.asDouble();
        } else if (propertyType.equalsIgnoreCase("boolean") || propertyType.equalsIgnoreCase
                ("bool")) {
            propertyValue = (value == null) ? false : value.asBoolean();
        } else if (propertyType.equalsIgnoreCase("Date") || propertyType.equalsIgnoreCase
                ("DateTime")) {
            Long dateValueInLong = (value == null) ? 0 : value.asLong();
            propertyValue = DateUtil.getDateTime(dateValueInLong).toDate();
        } else {
            propertyValue = (value == null) ? "" : value.asText();
        }

        return propertyValue;
    }

    public static JsonNode string2JsonNode(String jsonStr)
            throws IOException {
        ObjectMapper objMapper = new ObjectMapper();
        return objMapper.readTree(jsonStr);
    }

    public static JsonNode loadJsonFile(String filePath) {

        JsonNode jsonNode = null;

        String jsonStr = FileUtil.readFileContent(filePath);

        try {
            jsonNode = string2JsonNode(jsonStr);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonNode;
    }

    public static boolean isEmptyJsonNode(JsonNode jsonNode) {
        return jsonNode == null || jsonNode.size() == 0;
    }
}
