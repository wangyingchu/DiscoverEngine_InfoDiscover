package com.infoDiscover.solution.arch.demo.prepare.progress;

import com.infoDiscover.common.util.DateUtil;
import com.infoDiscover.common.util.JsonUtil;
import com.infoDiscover.solution.arch.demo.JsonConstants;
import com.infoDiscover.solution.arch.demo.ProgressJsonParser;
import com.infoDiscover.solution.arch.demo.prepare.DemoDataConfig;
import com.infoDiscover.solution.arch.demo.prepare.RandomData;
import com.infoDiscover.solution.arch.demo.prepare.UserRoleDataImporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;

import java.util.Map;

/**
 * Created by sun.
 */
public class ProgressRandomData {
    private final static Logger logger = LogManager.getLogger(ProgressRandomData
            .class);

    public static Map<String, Object> generateProgressRandomData
            (String projectTemplate, String projectType, String projectName,
             int sequence) {
        logger.info("Enter method generateProgressRandomData() with projectTemplate: {} and " +
                "projectName: {} and sequence: {}", projectTemplate, projectName, sequence);

        JsonNode json = JsonUtil.loadJsonFile(projectTemplate);
        JsonNode progressNodes = ProgressJsonParser.getProgressNodes(json.toString());

        // if json is empty
        if (JsonUtil.isEmptyJsonNode(progressNodes)) {
            logger.info("No progress and task data in the json");
            return null;
        }

        // in template, only one progress
        JsonNode progressJsonNode = progressNodes.get(0).get(JsonConstants.JSON_PROGRESS);
        Map<String, Object> properties = RandomData.jsonNodeToMapWithRandomValue(progressJsonNode);
        logger.info("properties: {}", properties);

        String progressType = "Progress";
        String progressId;
        String starter;
        if (projectType.equalsIgnoreCase(DemoDataConfig.PROJECTTYPE_MAINTAIN)) {
            progressId = "maintain" + sequence;
            starter = UserRoleDataImporter.selectRandomUserFromRole(DemoDataConfig.FILE_ROLE,
                    "Property_Department");
        } else {
            progressId = "newproject" + sequence;
            starter = UserRoleDataImporter.selectRandomUserFromRole(DemoDataConfig.FILE_ROLE,
                    "BuildingAndEnvironment_Bureau");
        }
        String progressName = projectName;

        long startTime = RandomData.getRandomTime(2010, 2016, 0);
        String status = "On Progress";

        properties.put("type", progressType);
        properties.put("progressId", progressId);
        properties.put("progressName", progressName);
        properties.put("starter", starter);
        properties.put("startTime", DateUtil.getDateTime(startTime).toDate());
        properties.put("status", status);

        logger.info("Exit method generateProgressRandomData()...");
        return properties;
    }

    public static void main(String[] args) {
        //generateProgressRandomData(maintainProjectTemplate, 1);
    }
}
