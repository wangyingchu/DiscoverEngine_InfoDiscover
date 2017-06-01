package com.infoDiscover.solution.construction.supervision.sample;

import com.infoDiscover.common.util.DateUtil;
import com.infoDiscover.common.util.JsonObjectUtil;
import com.infoDiscover.solution.arch.demo.UserRoleDataImporter;
import com.infoDiscover.solution.common.util.JsonNodeUtil;
import com.infoDiscover.solution.common.util.RandomData;
import com.infoDiscover.solution.construction.supervision.constants.JsonConstants;
import com.infoDiscover.solution.construction.supervision.util.ProjectJsonParser;
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

    public static Map<String, Object> generateProgressRandomData
            (String projectJsonTemplate, String projectType, String projectName, Date startDate,
             int sequence, boolean completed) {
        logger.info("Enter method generateProgressRandomData() with projectJsonTemplate: {} and " +
                        "projectType: {} and " +
                        "projectName: {} and sequence: {}", projectJsonTemplate, projectType,
                projectName,
                sequence);

        JsonNode json = JsonNodeUtil.loadJsonFile(projectJsonTemplate);

        // get Progress properties node
        JsonNode progressPropertiesJsonNode = getProgressNode(json.toString());

        // if json is empty
        if (JsonNodeUtil.isEmptyJsonNode(progressPropertiesJsonNode)) {
            logger.info("No progress and task data in the json");
            return null;
        }

        Map<String, Object> properties = RandomData.propertiesJsonNodeToMapWithRandomValue
                (progressPropertiesJsonNode, SampleDataSet.randomStringLength,
                        SampleDataSet.randomIntRange, SampleDataSet.randomDoubleRange,
                        SampleDataSet.longValue,
                        SampleDataSet.randomYearRange,
                        reservedStringPropertyNames());

        // update required properties
        updateRequiredProperties(properties, projectType, projectName, startDate, sequence, completed);
        logger.info("properties: {}", properties);

        logger.info("Exit method generateProgressRandomData()...");
        return properties;
    }

    private static void updateRequiredProperties(Map<String, Object> properties, String projectType,
                                                 String projectName,
                                                 Date startDate,
                                                 int sequence, boolean completed) {
        String progressType = "Progress";
        String progressId;

        // TODO: now get starter as member of "Property_Department"
        String starterId = UserRoleDataImporter.selectRandomUserFromRole
                (SampleDataSet.FILE_USER_DEPARTMENT, SampleDataSet.DEPARTMENT_OF_TASK2_OF_MAINTENANCE_PROJECT);
        String starter = UserRoleDataImporter.getUserName(SampleDataSet
                .FILE_USER, starterId);

        String completedValue = completed ? "c": "uc";

        if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_MAINTENANCE)) {
            progressId = SampleDataSet.PROJECTNAME_MAINTANENCE + completedValue + "_" + sequence;
        } else if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_NEW)) {
            progressId = SampleDataSet.PROJECTNAME_NEW + completedValue + "_" + sequence;
        } else if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_REBUILD)) {
            progressId = SampleDataSet.PROJECTNAME_REBUILD + completedValue + "_" + sequence;
        } else {
            progressId = SampleDataSet.PROJECTNAME_EXTENSION + completedValue + "_" + sequence;
        }

        String progressName = projectName;
        String status = "On Progress";

        properties.put(JsonConstants.JSON_TYPE, progressType);
        properties.put(JsonConstants.JSON_PROJECT_ID, progressId);
        properties.put(JsonConstants.JSON_PROJECT_TYPE, progressType);
        properties.put(JsonConstants.JSON_PROJECT_NAME, progressName);
        properties.put(JsonConstants.JSON_PROJECT_STARTER_ID, starterId);
        properties.put(JsonConstants.JSON_PROJECT_STARTER, starter);
        properties.put(JsonConstants.JSON_START_DATE, startDate);
        properties.put(JsonConstants.JSON_END_DATE, null);
        properties.put(JsonConstants.JSON_STATUS, status);
    }

    private static JsonNode getProgressNode(String json) {
        // check if "Progresses" node array
        JsonNode progressesNode = ProjectJsonParser.getProjectsNode(json);
        if (progressesNode == null) {
            // check if "Progress" node
            return ProjectJsonParser.getProjectNode(json);
        }

        return progressesNode.get(0).get(JsonConstants.JSON_PROJECT);
    }

    private static List<String> reservedStringPropertyNames() {
        List<String> list = new ArrayList<>();
        list.add(JsonConstants.JSON_PROJECT_TYPE);
        return list;
    }

    public static void main(String[] args) {
        long startDate = RandomData.getRandomTime(2010, 2016, 0);
        Map<String, Object> properties = generateProgressRandomData(SampleDataSet
                .FILE_MAINTENANCE_PROJECT, SampleDataSet
                .PROJECTTYPE_MAINTENANCE, SampleDataSet.PROJECTNAME_MAINTANENCE, DateUtil
                .getDateTime(startDate).toDate(), 1, true);
        logger.info("result: {}", properties);
    }
}
