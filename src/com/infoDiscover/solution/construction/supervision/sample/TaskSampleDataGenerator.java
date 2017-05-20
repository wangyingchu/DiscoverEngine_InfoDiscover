package com.infoDiscover.solution.construction.supervision.sample;

import com.infoDiscover.common.util.DateUtil;
import com.infoDiscover.common.util.JsonUtil;
import com.infoDiscover.common.util.RandomUtil;
import com.infoDiscover.solution.arch.demo.UserRoleDataImporter;
import com.infoDiscover.solution.common.util.RandomData;
import com.infoDiscover.solution.construction.supervision.database.SupervisionSolutionConstants;
import com.infoDiscover.solution.sample.util.JsonConstants;
import com.infoDiscover.solution.sample.util.ProgressJsonParser;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by sun.
 */
public class TaskSampleDataGenerator {
    private final static Logger logger = LoggerFactory.getLogger(TaskSampleDataGenerator
            .class);

    public static int minValue = 1;
    public static int maxValue = 20;
    public static double minDoubleValue = 1000.0d;
    public static double maxDoubleValue = 100000.0d;
    public static int randomStringLength = 9;
    public static long longValue = 10000l;

    public static Map<String, Object>[] generateTasksRandomData(
            String projectJsonTemplate,
            String projectType,
            String progressId,
            long progressStartDate,
            int firstTasks) {

        logger.info("Enter method generateTasksRandomData() with projectJsonTemplate: {} and " +
                "projectType: {} and progressId: {} and progressStartDate: {} and " +
                "firstTasks: {}", projectJsonTemplate, projectType, progressId, new Date
                (progressStartDate), firstTasks);

        JsonNode json = JsonUtil.loadJsonFile(projectJsonTemplate);
        JsonNode taskNodes = ProgressJsonParser.getTaskNodes(json.toString());

        // if json is empty
        if (JsonUtil.isEmptyJsonNode(taskNodes)) {
            logger.info("No progress and task data in the json template: {}", projectJsonTemplate);
            return null;
        }

        int tasksNumber = firstTasks <= taskNodes.size() ? firstTasks : taskNodes
                .size();

        Map<String, Object>[] tasksArray = new HashMap[tasksNumber];

        long startDate = progressStartDate;

        for (int i = 0; i < tasksNumber; i++) {
            JsonNode propertiesJsonNode = taskNodes.get(i).get(JsonConstants.JSON_TASK);

            Map<String, Object> properties = RandomData.propertiesJsonNodeToMapWithRandomValue
                    (propertiesJsonNode, randomStringLength,
                            minValue, maxValue, minDoubleValue, maxDoubleValue, longValue,
                            reservedStringPropertyNames(), null,
                            null);

            updateRequiredPropertiesRandomData(properties,
                    progressId, projectType, startDate, i);

            updateCustomizedTasksProperties(properties);

            // setup longitude and latitude
            if (properties.containsKey("longitude")) {
                setRandomLongitudeLatitude(properties);
            }

            if (properties.containsKey("latitude")) {
                setRandomLongitudeLatitude(properties);
            }

            tasksArray[i] = properties;

            startDate = ((Date) properties.get(JsonConstants.END_DATE)).getTime();
        }

        logger.info("Exit method generateMainProjectTasksRandomData()...");
        return tasksArray;
    }

    private static Map<String, Object> updateRequiredPropertiesRandomData(
            Map<String, Object> properties,
            String progressId,
            String projectType,
            long startDate,
            int taskSequence) {

//        properties.put(JsonConstants.JSON_TYPE, "Task");
        properties.put(JsonConstants.PROGRESS_ID, progressId);
        properties.put(JsonConstants.TASK_ID, progressId + "_taskId" + (taskSequence + 1));

        String taskName = getTasksList(projectType)[taskSequence];
        properties.put(JsonConstants.TASK_NAME, taskName);

        // TODO: update attachment
        //properties.put(JsonConstants.ATTACHMENT, "");

        String departmentId = getDepartmentIdList(projectType)[taskSequence];
        String departmentName = getDepartmentNameList(projectType)[taskSequence];

        if (taskName.equalsIgnoreCase(SampleDataSet.TASK1_OF_MAINTENANCE_PROJECT) || taskName
                .equalsIgnoreCase(SampleDataSet.TASK1_OF_NEW_PROJECT)) {
            int randomIndex = RandomUtil.generateRandomInRange(0,SampleDataSet
                    .DEPARTMENTS_TO_ASSIGN_TASK.length);
            departmentId = SampleDataSet.DEPARTMENTS_TO_ASSIGN_TASK[randomIndex];
            departmentName = SampleDataSet.DEPARTMENT_NAMES_TO_ASSIGN_TASK[randomIndex];
        }

        properties.put(JsonConstants.EXECUTIVE_DEPARTMENT_ID, departmentId);
        properties.put(JsonConstants.EXECUTIVE_DEPARTMENT,departmentName);
        String userId = UserRoleDataImporter.selectRandomUserFromRole
                (SampleDataSet.FILE_USER_DEPARTMENT, departmentId);
        properties.put(JsonConstants.WORKER_ID, userId);
        properties.put(JsonConstants.WORKER,UserRoleDataImporter.getUserName(SampleDataSet
                .FILE_USER,userId));

        // 1~9 中取随机数
        long taskStartDate = DateUtil.getLongDateValue(startDate, RandomUtil
                .generateRandomInRange(1, 9));
        properties.put(JsonConstants.START_DATE, DateUtil.getDateTime(taskStartDate).toDate());
        long taskEndDate = DateUtil.getLongDateValue(taskStartDate, RandomUtil
                .generateRandomInRange(1, 30));
        properties.put(JsonConstants.END_DATE, DateUtil.getDateTime(taskEndDate).toDate());

        logger.debug("properties: {}", properties);
        return properties;
    }

    // TODO: to refine with the new project template
    private static Map<String, Object> updateCustomizedTasksProperties(
            Map<String, Object> taskProperties) {

        List<String> list = Arrays.asList(SampleDataSet.dimensionArray);

        Set<String> keySet = taskProperties.keySet();
        Iterator<String> it = keySet.iterator();

        while (it.hasNext()) {
            String key = it.next();
            String dimensionTypeName = "";
            if (list.contains(key)) {
                if (key.equalsIgnoreCase("constructionType")) {
                    dimensionTypeName = SupervisionSolutionConstants
                            .DIMENSION_CONSTRUCTION_TYPE_WITH_PREFIX;
                } else if (key.equalsIgnoreCase("companyClassification")) {
                    dimensionTypeName = SupervisionSolutionConstants
                            .DIMENSION_COMPANY_CLASSIFICATION_WITH_PREFIX;
                } else if (key.equalsIgnoreCase("assignModel")) {
                    dimensionTypeName = SupervisionSolutionConstants
                            .DIMENSION_ASSIGN_MODEL_WITH_PREFIX;
                } else if (key.equalsIgnoreCase("issueClassification")) {
                    dimensionTypeName = SupervisionSolutionConstants
                            .DIMENSION_ISSUE_CLASSIFICATION_WITH_PREFIX;
                } else if (key.equalsIgnoreCase("landProperty")) {
                    dimensionTypeName = SupervisionSolutionConstants
                            .DIMENSION_LAND_PROPERTY_WITH_PREFIX;
                } else if (key.equalsIgnoreCase("assetFirstClassification")) {
                    dimensionTypeName = SupervisionSolutionConstants
                            .DIMENSION_ASSET_FIRST_CLASSIFICATION_WITH_PREFIX;
                } else if (key.equalsIgnoreCase("assetSecondClassification")) {
                    dimensionTypeName = SupervisionSolutionConstants
                            .DIMENSION_ASSET_SECOND_CLASSIFICATION_WITH_PREFIX;
                } else if (key.equalsIgnoreCase("projectClassification")) {
                    dimensionTypeName = SupervisionSolutionConstants
                            .DIMENSION_PROJECT_CLASSIFICATION_WITH_PREFIX;
                } else if (key.equalsIgnoreCase("projectSiteClassification")) {
                    dimensionTypeName = SupervisionSolutionConstants
                            .DIMENSION_PROJECT_SITE_CLASSIFICATION_WITH_PREFIX;
                } else if (key.equalsIgnoreCase("projectScope")) {
                    dimensionTypeName = SupervisionSolutionConstants
                            .DIMENSION_PROJECT_SCOPE_WITH_PREFIX;
                } else if (key.equalsIgnoreCase("projectConstructionClassification")) {
                    dimensionTypeName = SupervisionSolutionConstants
                            .DIMENSION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX;
                }

                List<String> values = SampleDimensionGenerator.dimensionCache.get
                        (dimensionTypeName);
                if (values != null && values.size() > 0) {
                    String propertyValue = values.get(RandomUtil.generateRandomInRange(0,
                            values.size()));
                    taskProperties.put(key, propertyValue);
                }
            }
        }

        return taskProperties;
    }

    private static void setRandomLongitudeLatitude(Map<String, Object> properties) {
        double longitude = SampleDataSet.LONGITUDE + RandomUtil
                .generateRandomInRange(0, 999) / 100000d;
        double latitude = SampleDataSet.LATITUDE + +RandomUtil
                .generateRandomInRange(0, 999) / 100000d;

        properties.put("longitude", longitude);
        properties.put("latitude", latitude);
    }

    public static String[] getTasksList(String projectType) {
        if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_MAINTENANCE)) {
            return SampleDataSet.TASKS_OF_MAINTENANCE_PROJECT;
        } else {
            return SampleDataSet.TASKS_OF_NEW_PROJECT;
        }
    }

    public static String[] getDepartmentIdList(String projectType) {

        if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_MAINTENANCE)) {
            return SampleDataSet.DEPARTMENTS_OF_MAINTENANCE_PROJECT;
        } else {
            return SampleDataSet.DEPARTMENTS_OF_NEW_PROJECT;
        }
    }

    public static String[] getDepartmentNameList(String projectType) {

        if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_MAINTENANCE)) {
            return SampleDataSet.DEPARTMENT_NAMES_OF_MAINTENANCE_PROJECT;
        } else {
            return SampleDataSet.DEPARTMENT_NAMES_OF_NEW_PROJECT;
        }
    }

    private static List<String> reservedStringPropertyNames() {
        List<String> reservedStringPropertyNames = new ArrayList<>();
        reservedStringPropertyNames.add(ClassificationConstants.CONSTRUCTION_TYPE);
        reservedStringPropertyNames.add(ClassificationConstants.COMPANY_CLASSIFICATION);
        reservedStringPropertyNames.add(ClassificationConstants.ASSIGN_MODEL);
        reservedStringPropertyNames.add(ClassificationConstants.EXECUTIVE_DEPARTMENT);
        reservedStringPropertyNames.add(ClassificationConstants.ISSUE_CLASSIFICATION);
        reservedStringPropertyNames.add(ClassificationConstants.LAND_PROPERTY);
        reservedStringPropertyNames.add(ClassificationConstants.ASSET_FIRST_CLASSIFICATION);
        reservedStringPropertyNames.add(ClassificationConstants.ASSET_SECOND_CLASSIFICATION);
        reservedStringPropertyNames.add(ClassificationConstants.PROJECT_CLASSIFICATION);
        reservedStringPropertyNames.add(ClassificationConstants.PROJECT_SITE_CLASSIFICATION);
        reservedStringPropertyNames.add(ClassificationConstants.PROJECT_SCOPE);
        reservedStringPropertyNames.add(ClassificationConstants.PROJECT_CONSTRUCTION_CLASSIFICATION);

        return reservedStringPropertyNames;
    }

    public static void main(String[] args) {
        String projectTemplate = SampleDataSet.FILE_NEW_PROJECT;
        Map<String, Object>[] array = generateTasksRandomData(projectTemplate,
                SampleDataSet.PROJECTNAME_NEW,
                "newProject1",
                RandomData
                        .getRandomTime(2010, 2016, 0), 21);
//        logger.info("array.size: " + array.length);
        for (Map<String, Object> map : array) {
            System.out.println(map);
            logger.info("taskId: " + map.get("taskId"));
            logger.info("taskName: " + map.get("taskName"));
            logger.info("executiveDepartmentId: " + map.get(JsonConstants.EXECUTIVE_DEPARTMENT_ID));
            logger.info("EXECUTIVE_DEPARTMENT: " + map.get(JsonConstants.EXECUTIVE_DEPARTMENT));
            logger.info("workerId: " + map.get(JsonConstants.WORKER_ID));
            logger.info("worker: " + map.get(JsonConstants.WORKER));
            logger.info("=================================================");
        }
    }
}
