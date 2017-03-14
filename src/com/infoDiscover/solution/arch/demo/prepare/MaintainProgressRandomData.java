package com.infoDiscover.solution.arch.demo.prepare;

import com.infoDiscover.common.util.JsonUtil2;
import com.infoDiscover.solution.arch.demo.JsonConstants;
import com.infoDiscover.solution.arch.demo.ProgressJsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;

import java.util.Map;

/**
 * Created by sun.
 */
public class MaintainProgressRandomData {
    private final static Logger logger = LogManager.getLogger(MaintainProgressRandomData
            .class);

    static String roleFile = "/Users/sun/InfoDiscovery/Demodata/roles.csv";

    static String maintainProjectTemplate =
            "/Users/sun/InfoDiscovery/Demodata/jsonData/MaintainProject/SampleProgress.json";

    public static Map<String, Object> generateMainProjectProgressRandomData
            (String maintainProjectTemplate,
             int sequence) {
        logger.debug("Enter method generateMainProjectProgressRandomData() with template: " + maintainProjectTemplate);

        JsonNode json = JsonUtil2.loadJsonFile(maintainProjectTemplate);
        JsonNode progressNodes = ProgressJsonParser.getProgressNodes(json.toString());

        // if json is empty
        if (JsonUtil2.isEmptyJsonNode(progressNodes)) {
            logger.info("No progress and task data in the json");
            return null;
        }

        // in template, only one progress
        JsonNode progressJsonNode = progressNodes.get(0).get(JsonConstants.JSON_PROGRESS);
        Map<String, Object> properties = RandomData.jsonNodeToMapWithRandomValue(progressJsonNode);
        logger.debug("properties: " + properties);

        String progressType = "Progress";
        String progressId = "maintain" + sequence;
        String progressName = "维修工程";
        String starter = UserRoleDataImporter.selectRandomUserFromRole(roleFile,
                "Proerty_Department");
        long startTime = RandomData.getRandomTime(2010,2016,0);
        String status = "On Progress";

        properties.put("type", progressType);
        properties.put("progressId", progressId);
        properties.put("progressName", progressName);
        properties.put("starter", starter);
        properties.put("startTime", startTime);
        properties.put("status", status);

        logger.debug("Exit method generateMainProjectProgressRandomData()...");
        return properties;
    }

    public static void main(String[] args) {
        generateMainProjectProgressRandomData(maintainProjectTemplate, 1);
    }
}
