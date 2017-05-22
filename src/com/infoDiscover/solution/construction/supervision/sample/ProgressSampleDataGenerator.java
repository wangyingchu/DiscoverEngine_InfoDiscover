package com.infoDiscover.solution.construction.supervision.sample;

import com.infoDiscover.common.util.DateUtil;
import com.infoDiscover.common.util.JsonUtil;
import com.infoDiscover.solution.arch.demo.UserRoleDataImporter;
import com.infoDiscover.solution.common.util.RandomData;
import com.infoDiscover.solution.construction.supervision.constants.JsonConstants;
import com.infoDiscover.solution.construction.supervision.util.ProgressJsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;

import java.util.*;

/**
 * Created by sun.
 */
public class ProgressSampleDataGenerator {
    private final static Logger logger = LogManager.getLogger(ProgressSampleDataGenerator
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
                        "projectName: {} and sequence: {}", projectJsonTemplate, projectType,
                projectName,
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
                        reservedStringPropertyNames(), null,
                        null);

        // update required properties
        updateRequiredProperties(properties, projectType, projectName, startDate, sequence);
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

        // TODO: now get starter as member of "Property_Department"
        String starterId = UserRoleDataImporter.selectRandomUserFromRole
                (SampleDataSet.FILE_USER_DEPARTMENT, SampleDataSet.DEPARTMENT_OF_TASK2_OF_MAINTENANCE_PROJECT);
        String starter = UserRoleDataImporter.getUserName(SampleDataSet
                .FILE_USER, starterId);

        if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_MAINTENANCE)) {
            progressId = SampleDataSet.PROJECTNAME_MAINTANENCE + "_" + sequence;
        } else if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_NEW)) {
            progressId = SampleDataSet.PROJECTNAME_NEW + "_" + sequence;
        } else if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_REBUILD)) {
            progressId = SampleDataSet.PROJECTNAME_REBUILD + "_" + sequence;
        } else {
            progressId = SampleDataSet.PROJECTNAME_EXTENSION + "_" + sequence;
        }

        String progressName = projectName;
        String status = "On Progress";

        properties.put(JsonConstants.JSON_TYPE, progressType);
        properties.put(JsonConstants.JSON_PROGRESS_ID, progressId);
        properties.put(JsonConstants.JSON_PROGRESS_TYPE, progressType);
        properties.put(JsonConstants.JSON_PROGRESS_NAME, progressName);
        properties.put(JsonConstants.JSON_PROGRESS_STARTER_ID, starterId);
        properties.put(JsonConstants.JSON_PROGRESS_STARTER, starter);
        properties.put(JsonConstants.JSON_START_DATE, startDate);
        properties.put(JsonConstants.JSON_END_DATE, null);
        properties.put(JsonConstants.JSON_STATUS, status);
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

    private static List<String> reservedStringPropertyNames() {
        List<String> list = new ArrayList<>();
        list.add(JsonConstants.JSON_PROGRESS_TYPE);
        return list;
    }

    public static void main(String[] args) {
        long startDate = RandomData.getRandomTime(2010, 2016, 0);
        Map<String, Object> properties = generateProgressRandomData(SampleDataSet
                .FILE_MAINTENANCE_PROJECT, SampleDataSet
                .PROJECTTYPE_MAINTENANCE, SampleDataSet.PROJECTNAME_MAINTANENCE, DateUtil
                .getDateTime(startDate).toDate(), 1);
        logger.info("result: {}", properties);
    }
}
