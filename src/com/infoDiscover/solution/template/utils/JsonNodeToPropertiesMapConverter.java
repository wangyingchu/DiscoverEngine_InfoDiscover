package com.infoDiscover.solution.template.utils;

import com.infoDiscover.infoDiscoverEngine.dataMart.TypeProperty;
import com.infoDiscover.solution.builder.SolutionConstants;
import com.infoDiscover.solution.common.util.JsonConstants;
import com.infoDiscover.solution.common.util.JsonNodeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.JsonNode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by sun on 11/4/17.
 */
public class JsonNodeToPropertiesMapConverter {

    public static void convertJsonNodeToPropertiesMap(JsonNode propertiesJsonNode, Map<String, Object>
            properties, Map<String, Object> uniqueKey, List<TypeProperty> typeProperties, boolean ignoreNotMappingProperties) {

        if (ignoreNotMappingProperties) {
            if (CollectionUtils.isEmpty(typeProperties)) {
                return;
            }

            Iterator<TypeProperty> it = typeProperties.iterator();
            Map<String, String> definedTypeProperties = new HashMap<>();
            while (it.hasNext()) {
                TypeProperty typeProperty = it.next();
                String propertyName = typeProperty.getPropertyName();
                String propertyType = typeProperty.getPropertyType().toString();
                definedTypeProperties.put(propertyName, propertyType);
            }

            for (JsonNode propertyNode : propertiesJsonNode) {
                String propertyName = propertyNode.get(JsonConstants.JSON_PROPERTY_NAME).asText();
                String propertyType = propertyNode.get(JsonConstants.JSON_PROPERTY_TYPE).asText();
                Object propertyValue = JsonNodeUtil.getPropertyValue(propertyType, propertyNode);

                if (definedTypeProperties.containsKey(propertyName)) {
                    if (propertyType.equalsIgnoreCase(definedTypeProperties.get(propertyName))) {
                        if (propertyValue != null) {
                            properties.put(propertyName, propertyValue);
                        }
                    }
                } else {
                    if (propertyValue != null) {
                        properties.put(propertyName, propertyValue);
                    }
                }

                JsonNode key = propertyNode.get(SolutionConstants.JSON_IS_UNIQUE_KEY);
                if (key != null) {
                    uniqueKey.put(propertyName, propertyValue);
                }
            }

        } else {
            for (JsonNode propertyNode : propertiesJsonNode) {
                String propertyName = propertyNode.get(JsonConstants.JSON_PROPERTY_NAME).asText();
                String propertyType = propertyNode.get(JsonConstants.JSON_PROPERTY_TYPE).asText();

                Object propertyValue = JsonNodeUtil.getPropertyValue(propertyType, propertyNode);
                if (propertyValue != null) {
                    properties.put(propertyName, propertyValue);
                }

                JsonNode key = propertyNode.get(SolutionConstants.JSON_IS_UNIQUE_KEY);
                if (key != null) {
                    uniqueKey.put(propertyName, propertyValue);
                }
            }
        }
    }
}
