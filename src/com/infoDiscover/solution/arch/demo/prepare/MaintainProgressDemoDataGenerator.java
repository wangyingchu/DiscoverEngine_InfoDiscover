package com.infoDiscover.solution.arch.demo.prepare;

import com.infoDiscover.common.PrefixConstant;
import com.infoDiscover.common.dimension.time.constants.TimeDimensionConstants;
import com.infoDiscover.common.dimension.time.dimension.DayDimension;
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
import com.infoDiscover.solution.arch.progress.constants.ProgressConstants;
import com.infoDiscover.solution.arch.progress.manager.ProgressManager;
import com.infoDiscover.solution.arch.progress.manager.ProgressRelationManager;
import com.infoDiscover.solution.arch.progress.manager.TaskManager;
import com.infoDiscover.solution.arch.progress.util.ProgressUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.Map;


/**
 * Created by sun.
 */
public class MaintainProgressDemoDataGenerator {
    private final static Logger logger = LogManager.getLogger(MaintainProgressDemoDataGenerator
            .class);

    static String roleFile = "/Users/sun/InfoDiscovery/Demodata/roles.csv";

    static String maintainProjectTemplate =
            "/Users/sun/InfoDiscovery/Demodata/jsonData/MaintainProject/SampleAllData.json";

    static int countOfProgressToGenerate = 1;


    public static void main(String[] args) {
        generateMainProjectDemoData(maintainProjectTemplate, roleFile, countOfProgressToGenerate,
                false);
    }

    public static void generateMainProjectDemoData(String maintainProjectTemplate, String
            roleFile, int countOfProgressToGenerate, boolean toGenerateRandomTaskNumber) {
        logger.debug("Enter method generateMainProjectDemoData() with maintainProjectTemplate: "
                + maintainProjectTemplate + " and roleFile: " + roleFile + " and " +
                "countOfProgressToGenerate: " + countOfProgressToGenerate);

        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();

        for (int i = 1; i < countOfProgressToGenerate + 1; i++) {
            int firstNumberOfTasksToGenerate = 11;
            if (toGenerateRandomTaskNumber) {
                firstNumberOfTasksToGenerate = RandomUtil.generateRandomInRange(1, 11);
            }

            Map<String, Object> progressProperties = MaintainProgressRandomData
                    .generateMainProjectProgressRandomData(maintainProjectTemplate, i);
            String progressId = progressProperties.get("progressId").toString();
            long startTime = (Long) progressProperties.get("startTime");

            Map<String, Object>[] tasksPropertiesArray = MaintainTaskRandomData
                    .generateMainProjectTasksRandomData(maintainProjectTemplate, roleFile,
                            progressId,
                            startTime, firstNumberOfTasksToGenerate);

            // if all tasks are run, so complete the progress
            if (firstNumberOfTasksToGenerate == 11) {
                progressProperties.put("status", "Completed");
                long taskEndTime = (Long) tasksPropertiesArray[firstNumberOfTasksToGenerate - 1].get
                        ("endTime");
                // set endTime with random (1, 5)
                progressProperties.put("endTime", DateUtil.getLongDateValue(taskEndTime,
                        RandomUtil.generateRandomInRange(1, 5)));
            }

            // to create progress
            // TODO: check if the progress is already created and the task is running
            createNewOrUpdateProgressInstance(ids, ids.getInformationExplorer(),
                    progressProperties);

            // to create tasks
            for (Map<String, Object> taskProperties : tasksPropertiesArray) {
                createNewOrUpdateTaskInstance(ids, ids.getInformationExplorer(), taskProperties);
            }
        }


        ids.closeSpace();
        logger.debug("Exit method generateMainProjectDemoData()...");
    }


    private static void createNewOrUpdateProgressInstance(InfoDiscoverSpace ids, InformationExplorer
            ie, Map<String, Object> properties) {
        logger.debug("Enter method createNewOrUpdateFactInstance() with ids: " + ids +
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
            DayDimension dayDimension = getDayDimension((Long) properties.get("startTime"));
            relationManager.attachTimeToProgress(ids, progressId, dayDimension, ProgressConstants
                    .RELATIONTYPE_STARTAT);

            // link endTime to progress
            if (properties.get("endTime") != null) {
                dayDimension = getDayDimension((Long) properties.get("endTime"));
                relationManager.attachTimeToProgress(ids, progressId, dayDimension,
                        ProgressConstants.RELATIONTYPE_ENDAT);
            }

        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.error(e.getMessage());
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.error(e.getMessage());
        }

        logger.debug("Exit createNewOrUpdateFactInstance()...");
    }

    private static DayDimension getDayDimension(long time) {
        DateTime date = DateUtil.getDateTime(time);
        int year = date.getYear();
        int month = date.getMonthOfYear();
        int day = date.getDayOfMonth();
        DayDimension dayDimension = new DayDimension(PrefixConstant.prefix +
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
        logger.debug("Enter method createNewOrUpdateTaskInstance() with ids: " + ids +
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
            DayDimension dayDimension = getDayDimension((Long) properties.get("startTime"));
            relationManager.attachTimeToTask(ids, taskId, dayDimension,ProgressConstants.RELATIONTYPE_STARTAT);

            // link endTime to task
            if (properties.get("endTime") != null) {
                dayDimension = getDayDimension((Long) properties.get("endTime"));
                relationManager.attachTimeToTask(ids, taskId, dayDimension, ProgressConstants.RELATIONTYPE_ENDAT);
            }

        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.error(e.getMessage());
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.error(e.getMessage());
        }

        logger.debug("Exit createNewOrUpdateTaskInstance()...");
    }
}
