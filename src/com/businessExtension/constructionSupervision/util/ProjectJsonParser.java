package com.businessExtension.constructionSupervision.util;

import com.infoDiscover.solution.common.util.JsonNodeUtil;
import com.businessExtension.constructionSupervision.constants.JsonConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;

/**
 * Created by sun.
 */
public class ProjectJsonParser {

    private final static Logger logger = LogManager.getLogger(ProjectJsonParser.class);

    // get projects JsonNode array
    public static JsonNode getProjectsNode(String json) {
        return JsonNodeUtil.getJsonNodes(JsonConstants.JSON_PROJECTS, json);
    }

    // get project JsonNode
    public static JsonNode getProjectNode(String json) {
        return JsonNodeUtil.getJsonNodes(JsonConstants.JSON_PROJECT, json);
    }

    // get tasks JsonNode array
    public static JsonNode getTasksNode(String json) {
        return JsonNodeUtil.getJsonNodes(JsonConstants.JSON_TASKS, json);
    }

    // get task JsonNode
    public static JsonNode getTaskNode(String json) {
        return JsonNodeUtil.getJsonNodes(JsonConstants.JSON_TASK, json);
    }
}
