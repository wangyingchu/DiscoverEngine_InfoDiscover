package com.infoDiscover.solution.construction.supervision.sample;

import com.infoDiscover.common.dimension.time.constants.TimeDimensionConstants;
import com.infoDiscover.common.dimension.time.dimension.DayDimensionVO;
import com.infoDiscover.common.util.DateUtil;
import com.infoDiscover.common.util.RandomUtil;
import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.arch.demo.FactTypeEnum;
import com.infoDiscover.solution.arch.demo.prepare.DemoDataConfig;
import com.infoDiscover.solution.arch.progress.constants.ProgressConstants;
import com.infoDiscover.solution.arch.progress.manager.*;
import com.infoDiscover.solution.arch.progress.util.ProgressUtil;
import com.infoDiscover.solution.common.util.RandomData;
import com.infoDiscover.solution.construction.supervision.database.SupervisionSolutionConstants;
import com.infoDiscover.solution.sample.util.JsonConstants;
import com.infoDiscover.solution.sample.util.ProgressRandomData;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.*;


/**
 * Created by sun.
 */
public class ProgressSampleDataGenerator {
    private final static Logger logger = LoggerFactory.getLogger(ProgressSampleDataGenerator
            .class);

    public static void generateMaintenanceProjectSampleData(
            InfoDiscoverSpace ids,
            int countOfProgressToGenerate,
            boolean toGenerateRandomTaskNumber) {

        generateProjectSampleData(
                ids,
                SampleDataSet.FILE_MAINTENANCE_PROJECT,
                SampleDataSet.PROJECTTYPE_MAINTENANCE,
                SampleDataSet.TASKS_OF_MAINTENANCE.length,
                countOfProgressToGenerate,
                toGenerateRandomTaskNumber);

    }

    public static void generateNewProjectSampleData(
            InfoDiscoverSpace ids,
            int countOfProgressToGenerate,
            boolean toGenerateRandomTaskNumber) {

        generateProjectSampleData(
                ids,
                SampleDataSet.FILE_NEW_PROJECT,
                SampleDataSet.PROJECTTYPE_NEW,
                SampleDataSet.TASKS_OF_NEW.length,
                countOfProgressToGenerate,
                toGenerateRandomTaskNumber);
    }



    public static void generateProjectSampleData(
            InfoDiscoverSpace ids,
            String projectJsonTemplate,
            String projectType,
            int maxTasksNumber,
            int countOfProgressToGenerate,
            boolean toGenerateRandomTaskNumber) {
        logger.info("Enter method generateProjectSampleData() with projectTemplate: {} and " +
                        "projectType: {} and countOfProgressToGenerate: {} and " +
                        "toGenerateRandomTaskNumber: {}", projectJsonTemplate,
                projectType, countOfProgressToGenerate, toGenerateRandomTaskNumber);

        for (int i = 1; i <= countOfProgressToGenerate; i++) {

            int firstNumberOfTasksToGenerate = getFirstNumberOfTasksToGenerate(maxTasksNumber,
                    toGenerateRandomTaskNumber);
            long startDateLongValue = RandomData.getRandomTime(2010, 2016, 0);
            Date startDate = DateUtil.getDateTime(startDateLongValue).toDate();

            Map<String, Object> progressProperties = ProgressRandomData
                    .generateProgressRandomData(projectJsonTemplate, projectType, getProjectName
                            (projectType), startDate, i);

            String progressId = progressProperties.get("progressId").toString();
            String factType = getFactType(projectType);

            if (firstNumberOfTasksToGenerate == 0) {
                // only create progress
                // TODO: check if the progress is already created and the task is running
                createNewOrUpdateProgressInstance(ids, ids.getInformationExplorer(), factType,
                        progressProperties);
            } else {

                Map<String, Object>[] tasksPropertiesArray = TaskSampleDataGenerator
                        .generateTasksRandomData(projectJsonTemplate, projectType, progressId,
                                startDateLongValue, firstNumberOfTasksToGenerate);

                // deal with the tasks array for some special properties
                if (factType.equalsIgnoreCase(DemoDataConfig.FACTTYPE_MAINTAIN_PROJECT)) {
                    List<Map<String, Object>> tasksPropertiesList = updateCustomizedTasksProperties
                            (tasksPropertiesArray);
                    tasksPropertiesArray = tasksPropertiesList.toArray(tasksPropertiesArray);
                }

                // append task properties to progress
                progressProperties = appendTaskPropertiesToProgress(progressProperties,
                        tasksPropertiesArray);

                // if all tasks are run, so complete the progress
                if (firstNumberOfTasksToGenerate == maxTasksNumber) {
                    progressProperties.put(JsonConstants.STATUS, "Completed");
                    long taskEndDateLongValue = ((Date)
                            tasksPropertiesArray[firstNumberOfTasksToGenerate - 1]
                                    .get(JsonConstants.END_DATE)).getTime();
                    long progressEndDateLongValue = DateUtil.getLongDateValue(taskEndDateLongValue,
                            RandomUtil.generateRandomInRange(1, 5));
                    // set endTime with random (1, 5)
                    progressProperties.put(JsonConstants.END_DATE, DateUtil.getDateTime
                            (progressEndDateLongValue).toDate());
                }


                // to create progress
                // TODO: check if the progress is already created and the task is running
                createNewOrUpdateProgressInstance(ids, ids.getInformationExplorer(), factType,
                        progressProperties);

                // batch create tasks
                batchCreateNewOrUpdateTaskInstances(ids, ids.getInformationExplorer(), factType,
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
        //TODO: to add rebuild/extension projectType
        return projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_MAINTENANCE) ?
                SampleDataSet.FACTTYPE_MAINTENANCE_PROJECT : SampleDataSet.FACTTYPE_NEW_PROJECT;
    }

    // TODO: to refine with the new project template
    private static List<Map<String, Object>> updateCustomizedTasksProperties(
            Map<String, Object>[] tasksPropertiesArray) {

        List<Map<String, Object>> tasksList = new ArrayList<>();

        String taskProjectType = "";
        // to create tasks
        for (Map<String, Object> taskProperties : tasksPropertiesArray) {
            if (taskProperties.get("taskName").toString().equalsIgnoreCase
                    ("WeiXiuShenqing")) {
                taskProjectType = taskProperties.get("projectType").toString();
            }

            if (taskProperties.get("taskName").toString().equalsIgnoreCase
                    ("KanchaBaojia")) {
                int index = Arrays.binarySearch(DemoDataConfig.projectTypeList,
                        taskProjectType);

                int min = DemoDataConfig.weixiubaojiaList[index];
                int max = DemoDataConfig.weixiubaojiaList[index + 1];
                double weixiubaojia = RandomUtil.generateRandomDouble(min, max);

                DecimalFormat df = new DecimalFormat("######0.00");
                taskProperties.put("weixiubaojia", Double.valueOf(df.format(weixiubaojia)));
            }

            tasksList.add(taskProperties);

        }

        return tasksList;
    }


    private static Map<String, Object> appendTaskPropertiesToProgress(
            Map<String, Object> progressProperties,
            Map<String, Object>[] tasksPropertiesArray) {
        if (tasksPropertiesArray == null || tasksPropertiesArray.length == 0) {
            return progressProperties;
        }

        for (Map<String, Object> taskProps : tasksPropertiesArray) {
            Set<String> keySet = taskProps.keySet();
            Iterator<String> it = keySet.iterator();

            String taskName = "";
            Date startDate = new Date();
            Date endDate = new Date();
            while (it.hasNext()) {
                String key = it.next();
                Object value = taskProps.get(key);
                if (key.equalsIgnoreCase(JsonConstants.TASK_NAME)) {
                    taskName = value.toString();
                }
                if (key.equalsIgnoreCase(JsonConstants.START_DATE)) {
                    startDate = (Date) value;
                }
                if (key.equalsIgnoreCase(JsonConstants.END_DATE)) {
                    endDate = (Date) value;
                }

                if (!reservedStringPropertyNames().contains(key)) {
                    progressProperties.put(key, value);
                }
            }

            if (taskName == null || taskName.equalsIgnoreCase("")) {
                taskName = "taskName";
            }

            progressProperties.put(taskName + "_startDate", startDate);
            progressProperties.put(taskName + "_endDate", endDate);
        }

        return progressProperties;
    }

    public static List<String> reservedStringPropertyNames() {
        List<String> list = new ArrayList<>();
        list.add("type");
        list.add("progressId");
        list.add("taskId");
        list.add("taskName");
        list.add("executiveDepartment");
        list.add("worker");
        list.add("startDate");
        list.add("endDate");

        return list;
    }

    private static String getProjectName(String projectType) {
        if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_MAINTENANCE)) {
            return SampleDataSet.PROJECTNAME_MAINTANENCE;
        } else {
            return SampleDataSet.PROJECTNAME_NEW;
        }
    }

    private static void createNewOrUpdateProgressInstance(InfoDiscoverSpace ids, InformationExplorer
            ie, String factType, Map<String, Object> properties) {
        logger.info("Enter method createNewOrUpdateProgressInstance() with ids: {} and factType: " +
                "{} " +
                "and properties: {}", ids, factType, properties);

        if (properties == null || properties.keySet().size() == 0) {
            logger.error("progress data is null");
            return;
        }

        String progressId = properties.get(JsonConstants.PROGRESS_ID).toString();
        ProgressManager progressManager = new ProgressManager();
        try {

            // remove type from properties
            properties.remove(JsonConstants.JSON_TYPE);

            // create or update fact
            Fact progressFact = progressManager.getProgressById(ie, progressId, factType);
            if (progressFact == null) {
                ProgressUtil.createFact(ids, factType, properties);
            } else {
                ProgressUtil.updateFact(progressFact, properties);
            }

            ProgressRelationManager relationManager = new ProgressRelationManager(ids);
            //TODO:
            // link starter to progress
            String starter = properties.get(JsonConstants.PROGRESS_STARTER).toString();

            // link startTime to progress
            DayDimensionVO dayDimension = getDayDimension(SupervisionSolutionConstants
                    .SOLUTION_PREFIX, (Date)
                    properties.get(JsonConstants
                            .START_DATE));
            relationManager.attachTimeToProgress(ids, progressId, factType, dayDimension,
                    ProgressConstants.RELATIONTYPE_STARTAT_WITHPREFIX);

            // link endTime to progress
            if (properties.get("endTime") != null) {
                dayDimension = getDayDimension(SupervisionSolutionConstants.SOLUTION_PREFIX,
                        (Date) properties.get
                                (JsonConstants.END_DATE));
                relationManager.attachTimeToProgress(ids, progressId, factType, dayDimension,
                        ProgressConstants.RELATIONTYPE_ENDAT_WITHPREFIX);
            }

        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.error(e.getMessage());
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.error(e.getMessage());
        }

        logger.info("Exit createNewOrUpdateProgressInstance()...");
    }

    public static DayDimensionVO getDayDimension(String factTypePrefix, Date date) {
        DateTime dateTime = DateUtil.getDateTime(date.getTime());

        int year = dateTime.getYear();
        int month = dateTime.getMonthOfYear();
        int day = dateTime.getDayOfMonth();
        DayDimensionVO dayDimension = new DayDimensionVO(factTypePrefix +
                TimeDimensionConstants.DAY, year, month, day);
        return dayDimension;
    }


    //TODO: to pass the factType prefix, not hard code to "ZHUHAI_"
    private static String getFact(String type) {

        if (type.trim().equalsIgnoreCase(FactTypeEnum.Progress.toString())) {
            return SupervisionSolutionConstants.FACT_PROGRESS_WITH_PREFIX;
        } else if (type.trim().equalsIgnoreCase(FactTypeEnum.Task.toString())) {
            return SupervisionSolutionConstants.FACT_TASK_WITH_PREFIX;
        }
        return "";
    }

    private static void batchCreateNewOrUpdateTaskInstances(
            InfoDiscoverSpace ids,
            InformationExplorer ie,
            String progressFactType,
            Map<String, Object>[] tasksPropertiesArray) {
        for (Map<String, Object> taskProperties : tasksPropertiesArray) {
            createNewOrUpdateTaskInstance(ids, ie, progressFactType, taskProperties);
        }
    }

    private static void createNewOrUpdateTaskInstance(InfoDiscoverSpace ids, InformationExplorer
            ie, String progressFactType, Map<String, Object> properties) {
        logger.info("Enter method createNewOrUpdateTaskInstance() with ids: {} with " +
                "progressFactType: {} and properties: {}", ids, progressFactType, properties);

        String progressId = properties.get(JsonConstants.PROGRESS_ID).toString();
        String taskId = properties.get(JsonConstants.TASK_ID).toString();
        TaskManager taskManager = new TaskManager();
        try {
            String taskFactType = getFact(properties.get(JsonConstants.JSON_TYPE).toString());
            logger.info("Fact type is: {}", taskFactType);

            // remove type from properties
            properties.remove(JsonConstants.JSON_TYPE);

            // create or update fact
            Fact taskTact = taskManager.getTaskById(ie, taskId, taskFactType);
            if (taskTact == null) {
                taskTact = ProgressUtil.createFact(ids, taskFactType, properties);
            } else {
                taskTact = ProgressUtil.updateFact(taskTact, properties);
            }

            // link tasks to progress
            ProgressRelationManager relationManager = new ProgressRelationManager(ids);
            ProgressManager progressManager = new ProgressManager();
            Fact progressFact = progressManager.getProgressById(ids.getInformationExplorer(),
                    progressId,
                    progressFactType);
            relationManager.attachTaskToProgress(progressFact, taskTact,
                    SupervisionSolutionConstants.RELATIONTYPE_PROGRESS_HASTASK_WITH_PREFIX);

            // link user to task
            String userId = properties.get(JsonConstants.WORKER).toString();
            UserManager userManager = new UserManager();
            Dimension userDimension = userManager.getUserById(ids.getInformationExplorer(),
                    userId, SupervisionSolutionConstants.DIMENSION_USER_WITH_PREFIX);
            relationManager.attachUserToTask(taskTact, userDimension, SupervisionSolutionConstants
                    .RELATIONTYPE_TASK_EXECUTEBYUSER_WITH_PREFIX);

            // link role to task
            String roleId = properties.get(JsonConstants.EXECUTIVEDEPARTMENT).toString();
            Dimension roleDimension = new RoleManager().getRoleById(ids.getInformationExplorer(),
                    roleId, SupervisionSolutionConstants.DIMENSION_ROLE_WITH_PREFIX);
            relationManager.attachRoleToTask(taskTact, roleDimension, SupervisionSolutionConstants
                    .RELATIONTYPE_TASK_EXECUTEBYROLE_WITH_PREFIX);

            // link startTime to task
            DayDimensionVO dayDimension = getDayDimension(SupervisionSolutionConstants
                    .SOLUTION_PREFIX, (Date)
                    properties.get(JsonConstants.START_DATE));
            relationManager.attachTimeToTask(ids, taskTact, dayDimension, ProgressConstants
                    .RELATIONTYPE_STARTAT_WITHPREFIX);

            // link endTime to task
            if (properties.get(JsonConstants.END_DATE) != null) {
                dayDimension = getDayDimension(SupervisionSolutionConstants.SOLUTION_PREFIX,
                        (Date) properties.get(JsonConstants.END_DATE));
                relationManager.attachTimeToTask(ids, taskTact, dayDimension, ProgressConstants
                        .RELATIONTYPE_ENDAT_WITHPREFIX);
            }

        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.error(e.getMessage());
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.error(e.getMessage());
        }

        logger.info("Exit createNewOrUpdateTaskInstance()...");
    }

}
