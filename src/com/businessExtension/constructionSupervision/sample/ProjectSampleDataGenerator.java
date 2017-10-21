package com.businessExtension.constructionSupervision.sample;

import com.infoDiscover.common.util.DateUtil;
import com.infoDiscover.common.util.RandomUtil;
import com.infoDiscover.infoDiscoverEngine.dataMart.FactType;
import com.infoDiscover.infoDiscoverEngine.dataMart.PropertyType;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.common.util.RandomData;
import com.businessExtension.constructionSupervision.constants.DatabaseConstants;
import com.businessExtension.constructionSupervision.constants.JsonConstants;
import com.businessExtension.constructionSupervision.manager.ProjectManager;
import com.businessExtension.constructionSupervision.manager.TaskManager;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Created by sun.
 */
public class ProjectSampleDataGenerator {
    private final static Logger logger = LoggerFactory.getLogger(ProjectSampleDataGenerator
            .class);

    public static void generateMaintenanceProjectSampleData(
            InfoDiscoverSpace ids,
            int[] countOfProgressToGenerate) {

        generateProjectSampleData(
                ids,
                SampleDataSet.FILE_MAINTENANCE_PROJECT,
                SampleDataSet.PROJECTTYPE_MAINTENANCE,
                SampleDataSet.TASK_DISPLAY_NAMES_OF_MAINTENANCE_PROJECT.length,
                countOfProgressToGenerate);

    }

    public static void generateNewProjectSampleData(
            InfoDiscoverSpace ids,
            int[] countOfProgressToGenerate) {

        generateProjectSampleData(
                ids,
                SampleDataSet.FILE_NEW_PROJECT,
                SampleDataSet.PROJECTTYPE_NEW,
                SampleDataSet.TASK_DISPLAY_NAMES_OF_NEW_PROJECT.length,
                countOfProgressToGenerate);
    }

    public static void generateExtensionProjectSampleData(
            InfoDiscoverSpace ids,
            int[] countOfProgressToGenerate) {

        generateProjectSampleData(
                ids,
                SampleDataSet.FILE_EXTENSION_PROJECT,
                SampleDataSet.PROJECTTYPE_EXTENSION,
                SampleDataSet.TASK_DISPLAY_NAMES_OF_NEW_PROJECT.length,
                countOfProgressToGenerate);
    }

    public static void generateRebuildProjectSampleData(
            InfoDiscoverSpace ids,
            int[] countOfProgressToGenerate) {

        generateProjectSampleData(
                ids,
                SampleDataSet.FILE_REBUILD_PROJECT,
                SampleDataSet.PROJECTTYPE_REBUILD,
                SampleDataSet.TASK_DISPLAY_NAMES_OF_NEW_PROJECT.length,
                countOfProgressToGenerate);
    }


    public static void generateProjectSampleData(
            InfoDiscoverSpace ids,
            String projectJsonTemplate,
            String projectType,
            int maxTasksNumber,
            int[] countOfProgressToGenerate) {
        logger.info("Enter method generateProjectSampleData() with projectTemplate: {} and " +
                        "projectType: {} and countOfProgressToGenerate: {}",
                projectJsonTemplate,
                projectType, countOfProgressToGenerate);


        int completedProjectCount = countOfProgressToGenerate[0];
        if (completedProjectCount < 0) {
            completedProjectCount = 0;
        }
        int uncompletedProjectCount = countOfProgressToGenerate[1];
        if(uncompletedProjectCount < 0) {
            uncompletedProjectCount = 0;
        }

        for (int i = 1; i <= completedProjectCount; i++) {

            long startDateLongValue = RandomData.getRandomTime(2010, 2016, 0);
            Date startDate = DateUtil.getDateTime(startDateLongValue).toDate();

            // generate progress random data
            Map<String, Object> progressProperties = ProgressSampleDataGenerator
                    .generateProgressRandomData(projectJsonTemplate, projectType, getProjectName
                            (projectType), startDate, i, true);
            if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_MAINTENANCE)) {
                progressProperties.put(JsonConstants.JSON_PROJECT_TYPE, SampleDataSet
                        .PROJECTTYPE_MAINTENANCE);
            } else if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_NEW)) {
                progressProperties.put(JsonConstants.JSON_PROJECT_TYPE, SampleDataSet
                        .PROJECTTYPE_NEW);
            } else if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_EXTENSION)) {
                progressProperties.put(JsonConstants.JSON_PROJECT_TYPE, SampleDataSet
                        .PROJECTTYPE_EXTENSION);
            } else if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_REBUILD)) {
                progressProperties.put(JsonConstants.JSON_PROJECT_TYPE, SampleDataSet
                        .PROJECTTYPE_REBUILD);
            }

            String progressId = progressProperties.get(JsonConstants.JSON_PROJECT_ID).toString();
            String factType = getFactType(projectType);

            ProjectManager progressManager = new ProjectManager(ids);


            // get the tasks properties
            Map<String, Object>[] tasksPropertiesArray = TaskSampleDataGenerator
                    .generateTasksRandomData(projectJsonTemplate, projectType, progressId,
                            startDateLongValue, maxTasksNumber);

            // generate progress name
            Map<String, Object> task1 = tasksPropertiesArray[0];
            String dateTime = DateUtil.getDateTime(startDateLongValue).toString().substring
                    (0, 10);
            String progressName = progressProperties.get(JsonConstants.JSON_PROJECT_TYPE)
                    .toString();
            if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_MAINTENANCE)) {
                String issue = task1.get(JsonConstants.JSON_ISSUE_CLASSIFICATION)
                        .toString();
                progressName += "_" + issue + "_" + dateTime;
            } else {
                String projectAddress = task1.get("projectAddress").toString();
                progressName += "_" + projectAddress + "_" + dateTime;
            }
            progressProperties.put(JsonConstants.JSON_PROJECT_NAME, progressName);

            // Append task properties to progress
            progressProperties = new TaskManager(ids).appendTaskPropertiesToProject
                    (progressProperties,
                            tasksPropertiesArray);

            // if all tasks are run, so complete the progress
            progressProperties.put(JsonConstants.JSON_STATUS, "Completed");
            long taskEndDateLongValue = ((Date)
                    tasksPropertiesArray[maxTasksNumber - 1]
                            .get(JsonConstants.JSON_END_DATE)).getTime();
            long progressEndDateLongValue = DateUtil.getLongDateValue(taskEndDateLongValue,
                    RandomUtil.generateRandomInRange(1, 5));
            // set endTime with random (1, 5)
            progressProperties.put(JsonConstants.JSON_END_DATE, DateUtil.getDateTime
                    (progressEndDateLongValue).toDate());


            // to create or update progress
            progressManager.createNewOrUpdateProjectInstance(factType,
                    progressProperties);

            // batch create or update tasks
            new TaskManager(ids).batchCreateNewOrUpdateTaskInstances(factType,
                    tasksPropertiesArray);
        }

        for (int i = 1; i <= uncompletedProjectCount; i++) {

            int firstNumberOfTasksToGenerate = getFirstNumberOfTasksToGenerate(maxTasksNumber - 1,
                    true);
            long startDateLongValue = RandomData.getRandomTime(2010, 2016, 0);
            Date startDate = DateUtil.getDateTime(startDateLongValue).toDate();

            // generate progress random data
            Map<String, Object> progressProperties = ProgressSampleDataGenerator
                    .generateProgressRandomData(projectJsonTemplate, projectType, getProjectName
                            (projectType), startDate, i, false);
            if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_MAINTENANCE)) {
                progressProperties.put(JsonConstants.JSON_PROJECT_TYPE, SampleDataSet
                        .PROJECTTYPE_MAINTENANCE);
            } else if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_NEW)) {
                progressProperties.put(JsonConstants.JSON_PROJECT_TYPE, SampleDataSet
                        .PROJECTTYPE_NEW);
            } else if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_EXTENSION)) {
                progressProperties.put(JsonConstants.JSON_PROJECT_TYPE, SampleDataSet
                        .PROJECTTYPE_EXTENSION);
            } else if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_REBUILD)) {
                progressProperties.put(JsonConstants.JSON_PROJECT_TYPE, SampleDataSet
                        .PROJECTTYPE_REBUILD);
            }

            String progressId = progressProperties.get(JsonConstants.JSON_PROJECT_ID).toString();
            String factType = getFactType(projectType);

            ProjectManager progressManager = new ProjectManager(ids);

            if (firstNumberOfTasksToGenerate == 0) {
                // only create progress
                // TODO: check if the progress is already created and the task is running
                // update the progress name
                DateTime dateTime = DateUtil.getDateTime(startDateLongValue);
                String progressName = progressProperties.get(JsonConstants.JSON_PROJECT_TYPE) +
                        "_" +
                        dateTime.toString().substring(0, 10);
                progressProperties.put(JsonConstants.JSON_PROJECT_NAME, progressName);
                progressManager.createNewOrUpdateProjectInstance(factType,
                        progressProperties);
            } else {

                // get the tasks properties
                Map<String, Object>[] tasksPropertiesArray = TaskSampleDataGenerator
                        .generateTasksRandomData(projectJsonTemplate, projectType, progressId,
                                startDateLongValue, firstNumberOfTasksToGenerate);

                // generate progress name
                Map<String, Object> task1 = tasksPropertiesArray[0];
                String dateTime = DateUtil.getDateTime(startDateLongValue).toString().substring
                        (0, 10);
                String progressName = progressProperties.get(JsonConstants.JSON_PROJECT_TYPE)
                        .toString();
                if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_MAINTENANCE)) {
                    String issue = task1.get(JsonConstants.JSON_ISSUE_CLASSIFICATION)
                            .toString();
                    progressName += "_" + issue + "_" + dateTime;
                } else {
                    String projectAddress = task1.get("projectAddress").toString();
                    progressName += "_" + projectAddress + "_" + dateTime;
                }
                progressProperties.put(JsonConstants.JSON_PROJECT_NAME, progressName);

                // Append task properties to progress
                progressProperties = new TaskManager(ids).appendTaskPropertiesToProject
                        (progressProperties,
                                tasksPropertiesArray);

                // if all tasks are run, so complete the progress
                if (firstNumberOfTasksToGenerate == maxTasksNumber) {
                    progressProperties.put(JsonConstants.JSON_STATUS, "Completed");
                    long taskEndDateLongValue = ((Date)
                            tasksPropertiesArray[firstNumberOfTasksToGenerate - 1]
                                    .get(JsonConstants.JSON_END_DATE)).getTime();
                    long progressEndDateLongValue = DateUtil.getLongDateValue(taskEndDateLongValue,
                            RandomUtil.generateRandomInRange(1, 5));
                    // set endTime with random (1, 5)
                    progressProperties.put(JsonConstants.JSON_END_DATE, DateUtil.getDateTime
                            (progressEndDateLongValue).toDate());
                }


                // to create or update progress
                progressManager.createNewOrUpdateProjectInstance(factType,
                        progressProperties);

                // batch create or update tasks
                new TaskManager(ids).batchCreateNewOrUpdateTaskInstances(factType,
                        tasksPropertiesArray);
            }

        }

        logger.info("Exit method generateProjectDemoData()...");
    }

    private static int getFirstNumberOfTasksToGenerate(int maxTasksNumber, boolean
            toGenerateRandomTaskNumber) {
        return toGenerateRandomTaskNumber ? RandomUtil.generateRandomInRange(0,
                maxTasksNumber) : maxTasksNumber;
    }

    public static String getFactType(String projectType) {
        if (projectType.trim().equalsIgnoreCase(SampleDataSet.PROJECTTYPE_MAINTENANCE)) {
            return DatabaseConstants.FACTTYPE_MAINTENANCE_PROJECT;
        } else {
            return DatabaseConstants.FACTTYPE_CONSTRUCTION_PROJECT;
        }
    }

    private static String getProjectName(String projectType) {
        if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_MAINTENANCE)) {
            return SampleDataSet.PROJECTNAME_MAINTANENCE;
        } else if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_EXTENSION)) {
            return SampleDataSet.PROJECTNAME_EXTENSION;
        } else if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_NEW)) {
            return SampleDataSet.PROJECTNAME_NEW;
        } else {
            return SampleDataSet.PROJECTNAME_REBUILD;
        }
    }


    private static void updateFactTypeProperties(InfoDiscoverSpace ids, String factType,
                                                 Map<String, Object> properties,
                                                 Map<String, PropertyType> allProperties) {

        FactType taskFact = ids.getFactType(factType);
        Set<String> ketSet = properties.keySet();
        Iterator<String> iterator = ketSet.iterator();
        while (iterator.hasNext()) {
            String propertyName = iterator.next();
            PropertyType propertyType = allProperties.get(propertyName);
            if (propertyType != null) {
                try {
                    taskFact.addTypeProperty(propertyName, propertyType);
                } catch (InfoDiscoveryEngineRuntimeException e) {
                    logger.error("Failed to update fact type properties, propertyName: {} and " +
                            "propertyType: {}", propertyName, propertyType);
                }
            }
        }
    }


}
