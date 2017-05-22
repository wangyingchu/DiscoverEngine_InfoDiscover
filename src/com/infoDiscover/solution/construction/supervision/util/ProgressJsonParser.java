package com.infoDiscover.solution.construction.supervision.util;

import com.infoDiscover.solution.common.util.JsonNodeUtil;
import com.infoDiscover.solution.construction.supervision.constants.JsonConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;

/**
 * Created by sun.
 */
public class ProgressJsonParser {

    private final static Logger logger = LogManager.getLogger(ProgressJsonParser.class);

    // get progresses JsonNode array
    public static JsonNode getProgressesNode(String json) {
        return JsonNodeUtil.getJsonNodes(JsonConstants.JSON_PROGRESSES, json);
    }

    // get progress JsonNode
    public static JsonNode getProgressNode(String json) {
        return JsonNodeUtil.getJsonNodes(JsonConstants.JSON_PROGRESS, json);
    }

    // get tasks JsonNode array
    public static JsonNode getTaskNodes(String json) {
        return JsonNodeUtil.getJsonNodes(JsonConstants.JSON_TASKS, json);
    }

}
