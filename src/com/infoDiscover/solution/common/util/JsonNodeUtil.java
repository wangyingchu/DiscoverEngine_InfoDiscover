package com.infoDiscover.solution.common.util;

import com.infoDiscover.solution.demo.util.JsonConstants;
import org.codehaus.jackson.JsonNode;

/**
 * Created by sun.
 */
public class JsonNodeUtil {
    public static JsonNode getPropertiesJsonNode(JsonNode jsonNode) {
        if (jsonNode == null) {
            return null;
        }
        return jsonNode.get(JsonConstants.JSON_PROPERTIES);
    }
}
