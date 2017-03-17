package com.infoDiscover.solution.arch.demo.prepare.progress;

import com.infoDiscover.common.PrefixConstant;
import com.infoDiscover.common.dimension.time.constants.TimeDimensionConstants;
import com.infoDiscover.common.dimension.time.dimension.DayDimensionVO;
import com.infoDiscover.common.util.DateUtil;
import com.infoDiscover.common.util.RandomUtil;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.arch.database.DatabaseManager;
import com.infoDiscover.solution.arch.demo.FactTypeEnum;
import com.infoDiscover.solution.arch.demo.JsonConstants;
import com.infoDiscover.solution.arch.demo.prepare.DemoDataConfig;
import com.infoDiscover.solution.arch.progress.constants.ProgressConstants;
import com.infoDiscover.solution.arch.progress.manager.ProgressManager;
import com.infoDiscover.solution.arch.progress.manager.ProgressRelationManager;
import com.infoDiscover.solution.arch.progress.manager.TaskManager;
import com.infoDiscover.solution.arch.progress.util.ProgressUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.text.DecimalFormat;
import java.util.*;


/**
 * Created by sun.
 */
public class ProgressDemoDataGenerator {
    private final static Logger logger = LogManager.getLogger(ProgressDemoDataGenerator
            .class);

    public static void generateMaintainProjectDemoData(int countOfProgressToGenerate, boolean
            toGenerateRandomTaskNumber) {
        generateProjectDemoData(DemoDataConfig.FILE_MAINTAIN_PROJECT, DemoDataConfig
                        .PROJECTTYPE_MAINTAIN, DemoDataConfig.MAINTAIN_TASKS.length,
                countOfProgressToGenerate, toGenerateRandomTaskNumber);
    }

    public static void generateNewProjectDemoData(int countOfProgressToGenerate, boolean
            toGenerateRandomTaskNumber) {
        generateProjectDemoData(DemoDataConfig.FILE_NEW_PROJECT, DemoDataConfig
                        .PROJECTTYPE_NEW, DemoDataConfig.NEWPROJECT_TASKS.length,
                countOfProgressToGenerate, toGenerateRandomTaskNumber);
    }

    public static void generateProjectDemoData(String projectTemplate, String projectType, int
            maxTasksNumber, int countOfProgressToGenerate, boolean toGenerateRandomTaskNumber) {
        logger.info("Enter method generateProjectDemoData() with projectTemplate: "
                + projectTemplate + " and projectType: " + projectType + " and " +
                "countOfProgressToGenerate: " + countOfProgressToGenerate);

        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();

        for (int i = 1; i < countOfProgressToGenerate + 1; i++) {
            int firstNumberOfTasksToGenerate = maxTasksNumber;
            if (toGenerateRandomTaskNumber) {
                firstNumberOfTasksToGenerate = RandomUtil.generateRandomInRange(0, 11);
            }


            Map<String, Object> progressProperties = ProgressRandomData
                    .generateProgressRandomData(projectTemplate, projectType, getProjectName
                            (projectType), i);

            String progressId = progressProperties.get("progressId").toString();
            long startTimeLongValue = ((Date) progressProperties.get("startTime")).getTime();

            if (firstNumberOfTasksToGenerate == 0) {
                // only create progress
                // TODO: check if the progress is already created and the task is running
                createNewOrUpdateProgressInstance(ids, ids.getInformationExplorer(),
                        progressProperties);
            } else {
                Map<String, Object>[] tasksPropertiesArray = TaskRandomData.generateTasksRandomData
                        (projectTemplate, projectType, progressId,
                                startTimeLongValue, firstNumberOfTasksToGenerate);

                // append task properties to progress
                progressProperties = appendTaskPropertiesToProgress(progressProperties,
                        tasksPropertiesArray);

                // if all tasks are run, so complete the progress
                if (firstNumberOfTasksToGenerate == maxTasksNumber) {
                    progressProperties.put("status", "Completed");
                    long taskEndTimeLong = ((Date)
                            tasksPropertiesArray[firstNumberOfTasksToGenerate - 1]
                                    .get("endTime")).getTime();
                    long progressEndTimeLongValue = DateUtil.getLongDateValue(taskEndTimeLong,
                            RandomUtil.generateRandomInRange(1, 5));
                    // set endTime with random (1, 5)
                    progressProperties.put("endTime", DateUtil.getDateTime
                            (progressEndTimeLongValue).toDate());
                }


                // to create progress
                // TODO: check if the progress is already created and the task is running
                createNewOrUpdateProgressInstance(ids, ids.getInformationExplorer(),
                        progressProperties);


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
                        double weixiubaojia = RandomUtil.generateRandomDouble((index + RandomUtil
                                .generateRandomInRange(index, 10)) * 1111, (index + RandomUtil
                                .generateRandomDouble(12.0, 63.3)) *
                                RandomUtil.generateRandomDouble(1234, 7620));

                        DecimalFormat df = new DecimalFormat("######0.00");
                        taskProperties.put("weixiubaojia", Double.valueOf(df.format(weixiubaojia)));
                    }

                    createNewOrUpdateTaskInstance(ids, ids.getInformationExplorer(),
                            taskProperties);
                }
            }

        }

        ids.closeSpace();
        logger.info("Exit method generateProjectDemoData()...");
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
            Date startTime = new Date();
            Date endTime = new Date();
            while (it.hasNext()) {
                String key = it.next();
                Object value = taskProps.get(key);
                if (key.equalsIgnoreCase("taskName")) {
                    taskName = value.toString();
                }
                if (key.equalsIgnoreCase("startTime")) {
                    startTime = (Date) value;
                }
                if (key.equalsIgnoreCase("endTime")) {
                    endTime = (Date) value;
                }

                if (!reservedStringPropertyNames().contains(key)) {
                    progressProperties.put(key, value);
                }
            }

            if (taskName == null || taskName.equalsIgnoreCase("")) {
                taskName = "taskName";
            }

            progressProperties.put(taskName + "_startTime", startTime);
            progressProperties.put(taskName + "_endTime", endTime);
        }

        return progressProperties;
    }

    public static List<String> reservedStringPropertyNames() {
        List<String> list = new ArrayList<>();
        list.add("type");
        list.add("progressId");
        list.add("taskId");
        list.add("taskName");
        list.add("departmentId");
        list.add("assignee");
        list.add("startTime");
        list.add("endTime");

        return list;
    }

    private static String getProjectName(String projectType) {
        if (projectType.equalsIgnoreCase(DemoDataConfig.PROJECTTYPE_MAINTAIN)) {
            return DemoDataConfig.PROJECTNAME_MAINTAIN;
        } else {
            return DemoDataConfig.PROJECTNAME_NEW;
        }
    }

    private static void createNewOrUpdateProgressInstance(InfoDiscoverSpace ids, InformationExplorer
            ie, Map<String, Object> properties) {
        logger.info("Enter method createNewOrUpdateFactInstance() with ids: " + ids +
                "and properties: " +
                properties);

        if (properties == null || properties.keySet().size() == 0) {
            logger.error("progress jsonNode is null");
            return;
        }

        String progressId = properties.get("progressId").toString();

        ProgressManager progressManager = new ProgressManager();
        try {
            String factType = getFact(properties.get(JsonConstants.JSON_TYPE).toString());
            logger.info("Fact type is : " + factType);

            // remove type from properties
            properties.remove("type");

            // create or update fact
            Fact progressFact = progressManager.getProgressById(ie, progressId);
            if (progressFact == null) {
                ProgressUtil.createFact(ids, factType, properties);
            } else {
                ProgressUtil.updateFact(progressFact, properties);
            }

            ProgressRelationManager relationManager = new ProgressRelationManager();
            // link starter to progress
            String starter = properties.get("starter").toString();

            // link startTime to progress
            DayDimensionVO dayDimension = getDayDimension((Date) properties.get("startTime"));
            relationManager.attachTimeToProgress(ids, progressId, dayDimension, ProgressConstants
                    .RELATIONTYPE_STARTAT);

            // link endTime to progress
            if (properties.get("endTime") != null) {
                dayDimension = getDayDimension((Date) properties.get("endTime"));
                relationManager.attachTimeToProgress(ids, progressId, dayDimension,
                        ProgressConstants.RELATIONTYPE_ENDAT);
            }

        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.error(e.getMessage());
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.error(e.getMessage());
        }

        logger.info("Exit createNewOrUpdateFactInstance()...");
    }

    public static DayDimensionVO getDayDimension(Date date) {
        DateTime dateTime = DateUtil.getDateTime(date.getTime());

        int year = dateTime.getYear();
        int month = dateTime.getMonthOfYear();
        int day = dateTime.getDayOfMonth();
        DayDimensionVO dayDimension = new DayDimensionVO(PrefixConstant.prefix +
                TimeDimensionConstants.DAY, year, month, day);
        return dayDimension;
    }


    private static String getFact(String type) {

        if (type.trim().equalsIgnoreCase(FactTypeEnum.Progress.toString())) {
            return ProgressConstants.FACT_PROGRESS;
        } else if (type.trim().equalsIgnoreCase(FactTypeEnum.Task.toString())) {
            return ProgressConstants.FACT_TASK;
        }
        return "";
    }

    private static void createNewOrUpdateTaskInstance(InfoDiscoverSpace ids, InformationExplorer
            ie, Map<String, Object> properties) {
        logger.info("Enter method createNewOrUpdateTaskInstance() with ids: " + ids +
                "and properties: " +
                properties);

        String progressId = properties.get("progressId").toString();
        String taskId = properties.get("taskId").toString();
        TaskManager taskManager = new TaskManager();
        try {
            String factType = getFact(properties.get(JsonConstants.JSON_TYPE).toString());
            logger.info("Fact type is : " + factType);

            // remove type from properties
            properties.remove("type");

            // create or update fact
            Fact taskTact = taskManager.getTaskById(ie, taskId);
            if (taskTact == null) {
                ProgressUtil.createFact(ids, factType, properties);
            } else {
                ProgressUtil.updateFact(taskTact, properties);
            }

            // link tasks to progress
            ProgressRelationManager relationManager = new ProgressRelationManager();
            relationManager.attachTaskToProgress(progressId, taskId);

            // link user to task
            String userId = properties.get(JsonConstants.TASK_ASSIGNEE).toString();
            relationManager.attachUserToTask(taskId, userId);

            // link role to task
            String roleId = properties.get(JsonConstants.TASK_DEPARTMENTID).toString();
            relationManager.attachRoleToTask(taskId, roleId);

            // link startTime to task
            DayDimensionVO dayDimension = getDayDimension((Date) properties.get("startTime"));
            relationManager.attachTimeToTask(ids, taskId, dayDimension, ProgressConstants
                    .RELATIONTYPE_STARTAT);

            // link endTime to task
            if (properties.get("endTime") != null) {
                dayDimension = getDayDimension((Date) properties.get("endTime"));
                relationManager.attachTimeToTask(ids, taskId, dayDimension, ProgressConstants
                        .RELATIONTYPE_ENDAT);
            }

        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.error(e.getMessage());
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.error(e.getMessage());
        }

        logger.info("Exit createNewOrUpdateTaskInstance()...");
    }

}
