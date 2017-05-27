package com.infoDiscover.solution.construction.supervision.util;

import com.infoDiscover.solution.common.util.JsonNodeUtil;
import com.infoDiscover.solution.construction.supervision.constants.JsonConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;

/**
 * Created by sun.
 */
public class ProjectJsonParser {

    private final static Logger logger = LogManager.getLogger(ProjectJsonParser.class);

    // get progresses JsonNode array
    public static JsonNode getProgressesNode(String json) {
        return JsonNodeUtil.getJsonNodes(JsonConstants.JSON_PROJECTS, json);
    }

    // get progress JsonNode
    public static JsonNode getProgressNode(String json) {
        return JsonNodeUtil.getJsonNodes(JsonConstants.JSON_PROJECT, json);
    }

    // get tasks JsonNode array
    public static JsonNode getTaskNodes(String json) {
        return JsonNodeUtil.getJsonNodes(JsonConstants.JSON_TASKS, json);
    }

    // get task JsonNode
    public static JsonNode getTaskNode(String json) {
        return JsonNodeUtil.getJsonNodes(JsonConstants.JSON_TASK, json);
    }
}
