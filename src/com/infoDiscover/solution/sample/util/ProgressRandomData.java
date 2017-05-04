package com.infoDiscover.solution.sample.util;

import com.infoDiscover.common.util.DateUtil;
import com.infoDiscover.common.util.JsonUtil;
import com.infoDiscover.solution.arch.demo.prepare.DemoDataConfig;
import com.infoDiscover.solution.arch.demo.UserRoleDataImporter;
import com.infoDiscover.solution.common.util.RandomData;
import com.infoDiscover.solution.construction.supervision.sample.SampleDataSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;

import java.util.Date;
import java.util.Map;

/**
 * Created by sun.
 */
public class ProgressRandomData {
    private final static Logger logger = LogManager.getLogger(ProgressRandomData
            .class);

    public static int minValue = 1;
    public static int maxValue = 20;
    public static double minDoubleValue = 1000.0d;
    public static double maxDoubleValue = 100000.0d;
    public static int randomStringLength = 9;
    public static long longValue = 10000l;

    public static Map<String, Object> generateProgressRandomData
            (String projectJsonTemplate, String projectType, String projectName, Date startDate,
             int sequence) {
        logger.info("Enter method generateProgressRandomData() with projectJsonTemplate: {} and " +
                "projectType: {} and " +
                "projectName: {} and sequence: {}", projectJsonTemplate, projectType,projectName,
                sequence);

        JsonNode json = JsonUtil.loadJsonFile(projectJsonTemplate);

        // get Progress properties node
        JsonNode progressPropertiesJsonNode = getProgressNode(json.toString());

        // if json is empty
        if (JsonUtil.isEmptyJsonNode(progressPropertiesJsonNode)) {
            logger.info("No progress and task data in the json");
            return null;
        }

        Map<String, Object> properties = RandomData.propertiesJsonNodeToMapWithRandomValue
                (progressPropertiesJsonNode, randomStringLength,
                minValue, maxValue, minDoubleValue, maxDoubleValue, longValue,
                null, null,
                null);

        updateRequiredProperties(properties,projectType,projectName,startDate, sequence);
        logger.info("properties: {}", properties);

        logger.info("Exit method generateProgressRandomData()...");
        return properties;
    }

    private static void updateRequiredProperties(Map<String, Object> properties, String projectType,
                                          String projectName,
                                          Date startDate,
                                          int sequence) {
        String progressType = "Progress";
        String progressId;
        String starter;
        if (projectType.equalsIgnoreCase(DemoDataConfig.PROJECTTYPE_MAINTAIN)) {
            progressId = "maintain" + sequence;
            starter = UserRoleDataImporter.selectRandomUserFromRole(SampleDataSet.FILE_USER_ROLE,
                    "Property_Department");
        } else {
            progressId = "newproject" + sequence;
            starter = UserRoleDataImporter.selectRandomUserFromRole(SampleDataSet.FILE_USER_ROLE,
                    "BuildingAndEnvironment_Bureau");
        }
        String progressName = projectName;

        String status = "On Progress";

        properties.put("type", progressType);
        properties.put("progressId", progressId);
        properties.put("progressName", progressName);
        properties.put("starter", starter);
        properties.put("startDate", startDate);
        properties.put("endDate", null);
        properties.put("status", status);
    }

    private static JsonNode getProgressNode(String json) {
        // check if "Progresses" node array
        JsonNode progressesNode = ProgressJsonParser.getProgressesNode(json);
        if (progressesNode == null) {
            // check if "Progress" node
            return ProgressJsonParser.getProgressNode(json);
        }

        return progressesNode.get(0).get(JsonConstants.JSON_PROGRESS);
    }

    public static void main(String[] args) {
        long startDate = RandomData.getRandomTime(2010, 2016, 0);
        Map<String, Object> properties = generateProgressRandomData(SampleDataSet
                .FILE_MAINTENANCE_PROJECT, SampleDataSet
                .PROJECTTYPE_MAINTENANCE, SampleDataSet.PROJECTNAME_MAINTANENCE, DateUtil
                .getDateTime(startDate).toDate(),1);
        logger.info("result: {}", properties);
    }
}
