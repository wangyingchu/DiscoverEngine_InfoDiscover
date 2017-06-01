package com.infoDiscover.solution.arch.demo.prepare.progress;

import com.infoDiscover.common.PrefixConstant;
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
import com.infoDiscover.solution.arch.database.DatabaseManager;
import com.infoDiscover.solution.arch.demo.DemoArchJsonConstants;
import com.infoDiscover.solution.arch.demo.FactTypeEnum;
import com.infoDiscover.solution.arch.demo.prepare.DemoDataConfig;
import com.infoDiscover.solution.arch.progress.constants.ProgressConstants;
import com.infoDiscover.solution.arch.progress.manager.*;
import com.infoDiscover.solution.common.fact.FactManager;
import com.infoDiscover.solution.common.util.RandomData;
import com.infoDiscover.solution.construction.supervision.constants.JsonConstants;
import com.infoDiscover.solution.construction.supervision.sample.ProgressSampleDataGenerator;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.*;


/**
 * Created by sun.
 */
public class DemoArchProgressDemoDataGenerator {
    private final static Logger logger = LoggerFactory.getLogger(DemoArchProgressDemoDataGenerator
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
        logger.info("Enter method generateProjectDemoData() with projectTemplate: {} and " +
                        "projectType: {} and countOfProgressToGenerate: {} and " +
                        "toGenerateRandomTaskNumber: {}", projectTemplate,
                projectType, countOfProgressToGenerate, toGenerateRandomTaskNumber);

        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();

        for (int i = 1; i < countOfProgressToGenerate + 1; i++) {
            int firstNumberOfTasksToGenerate = maxTasksNumber;
            if (toGenerateRandomTaskNumber) {
                firstNumberOfTasksToGenerate = RandomUtil.generateRandomInRange(0,
                        firstNumberOfTasksToGenerate);
            }

            long startDate = RandomData.getRandomTime(2010, 2016, 0);
            Map<String, Object> progressProperties = ProgressSampleDataGenerator
                    .generateProgressRandomData(projectTemplate, projectType, getProjectName
                            (projectType), DateUtil.getDateTime(startDate).toDate(), i,
                            (firstNumberOfTasksToGenerate == maxTasksNumber));

            String progressId = progressProperties.get("progressId").toString();
            long startTimeLongValue = ((Date) progressProperties.get("startTime")).getTime();

            String factType = projectType.equalsIgnoreCase(DemoDataConfig.PROJECTTYPE_MAINTAIN) ?
                    DemoDataConfig.FACTTYPE_MAINTAIN_PROJECT : DemoDataConfig.FACTTYPE_NEW_PROJECT;

            if (firstNumberOfTasksToGenerate == 0) {
                // only create progress
                // TODO: check if the progress is already created and the task is running
                createNewOrUpdateProgressInstance(ids, ids.getInformationExplorer(), factType,
                        progressProperties);
            } else {

                Map<String, Object>[] tasksPropertiesArray = DemoArchTaskRandomData
                        .generateTasksRandomData
                                (projectTemplate, projectType, progressId,
                                        startTimeLongValue, firstNumberOfTasksToGenerate);

                // deal with the tasks array for some special properties
                if (factType.equalsIgnoreCase(DemoDataConfig.FACTTYPE_MAINTAIN_PROJECT)) {
                    List<Map<String, Object>> tasksPropertiesList = getTasksProperties
                            (tasksPropertiesArray);
                    tasksPropertiesArray = tasksPropertiesList.toArray(tasksPropertiesArray);
                }


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
                createNewOrUpdateProgressInstance(ids, ids.getInformationExplorer(), factType,
                        progressProperties);

                // batch create tasks
                batchCreateNewOrUpdateTaskInstances(ids, ids.getInformationExplorer(), factType,
                        tasksPropertiesArray);
            }

        }

        ids.closeSpace();
        logger.info("Exit method generateProjectDemoData()...");
    }

    private static List<Map<String, Object>> getTasksProperties(Map<String, Object>[]
                                                                        tasksPropertiesArray) {

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
            ie, String factType, Map<String, Object> properties) {
        logger.info("Enter method createNewOrUpdateFactInstance() with ids: {} and factType: {} " +
                "and properties: {}", ids, factType, properties);

        if (properties == null || properties.keySet().size() == 0) {
            logger.error("progress jsonNode is null");
            return;
        }

        String progressId = properties.get("progressId").toString();

        DemoProgressManager demoProgressManager = new DemoProgressManager();
        try {

            // remove type from properties
            properties.remove("type");

            // create or update fact
            Fact progressFact = demoProgressManager.getProgressById(ie, progressId, factType);
            FactManager factManager = new FactManager(ids);
            if (progressFact == null) {
                factManager.createFact(factType, properties);
            } else {
                factManager.updateFact(progressFact, properties);
            }

            ProgressRelationManager relationManager = new ProgressRelationManager(ids);
            // link starter to progress
            String starter = properties.get("starter").toString();

            // link startTime to progress
            DayDimensionVO dayDimension = getDayDimension((Date) properties.get("startTime"));
            relationManager.attachTimeToProgress(progressId, factType, dayDimension,
                    ProgressConstants
                            .RELATIONTYPE_STARTAT_WITHPREFIX);

            // link endTime to progress
            if (properties.get("endTime") != null) {
                dayDimension = getDayDimension((Date) properties.get("endTime"));
                relationManager.attachTimeToProgress(progressId, factType, dayDimension,
                        ProgressConstants.RELATIONTYPE_ENDAT_WITHPREFIX);
            }

        } catch (InfoDiscoveryEngineRuntimeException e) {
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
            return ProgressConstants.FACT_PROGRESS_WITHPREFIX;
        } else if (type.trim().equalsIgnoreCase(FactTypeEnum.Task.toString())) {
            return ProgressConstants.FACT_TASK_WITHPREFIX;
        }
        return "";
    }

    private static void batchCreateNewOrUpdateTaskInstances(InfoDiscoverSpace ids,
                                                            InformationExplorer ie, String
                                                                    progressFactType,
                                                            Map<String, Object>[]
                                                                    tasksPropertiesArray) {
        for (Map<String, Object> taskProperties : tasksPropertiesArray) {
            createNewOrUpdateTaskInstance(ids, ie, progressFactType, taskProperties);
        }
    }

    private static void createNewOrUpdateTaskInstance(InfoDiscoverSpace ids, InformationExplorer
            ie, String progressFactType, Map<String, Object> properties) {
        logger.info("Enter method createNewOrUpdateTaskInstance() with ids: {} with " +
                "progressFactType: {} and properties: {}", ids, progressFactType, properties);

        String progressId = properties.get("progressId").toString();
        String taskId = properties.get("taskId").toString();
        DemoTaskManager taskManager = new DemoTaskManager();
        try {
            String taskFactType = getFact(properties.get(JsonConstants.JSON_TYPE).toString());
            logger.info("Fact type is: {}", taskFactType);

            // remove type from properties
            properties.remove("type");

            // create or update fact
            Fact taskTact = taskManager.getTaskById(ie, taskId, taskFactType);
            FactManager factManager = new FactManager(ids);
            if (taskTact == null) {
                taskTact = factManager.createFact(taskFactType, properties);
            } else {
                taskTact = factManager.updateFact(taskTact, properties);
            }

            // link tasks to progress
            ProgressRelationManager relationManager = new ProgressRelationManager(ids);
            DemoProgressManager demoProgressManager = new DemoProgressManager();
            Fact progressFact = demoProgressManager.getProgressById(ids.getInformationExplorer(),
                    progressId, progressFactType);
            relationManager.attachTaskToProgress(progressFact, taskTact,
                    ProgressConstants.RELATIONTYPE_PROGRESS_HASTASK_WITHPREFIX);

            // link user to task
            String userId = properties.get(DemoArchJsonConstants.TASK_ASSIGNEE).toString();
            UserManager userManager = new UserManager();
            Dimension userDimension = userManager.getUserById(ids.getInformationExplorer(),
                    userId, ProgressConstants.DIMENSION_USER_WITHPREFIX);
            relationManager.attachUserToTask(taskTact, userDimension, ProgressConstants
                    .RELATIONTYPE_TASK_EXECUTEBYUSER_WITHPREFIX);

            // link role to task
            String roleId = properties.get(DemoArchJsonConstants.TASK_DEPARTMENTID).toString();
            RoleManager roleManager = new RoleManager();
            Dimension roleDimension = roleManager.getRoleById(ids.getInformationExplorer(),
                    roleId, ProgressConstants.DIMENSION_ROLE_WITHPREFIX);
            relationManager.attachRoleToTask(taskTact, roleDimension, ProgressConstants
                    .RELATIONTYPE_TASK_EXECUTEBYROLE_WITHPREFIX);

            // link startTime to task
            DayDimensionVO dayDimension = getDayDimension((Date) properties.get("startTime"));
            relationManager.attachTimeToTask(taskTact, dayDimension, ProgressConstants
                    .RELATIONTYPE_STARTAT_WITHPREFIX);

            // link endTime to task
            if (properties.get("endTime") != null) {
                dayDimension = getDayDimension((Date) properties.get("endTime"));
                relationManager.attachTimeToTask(taskTact, dayDimension, ProgressConstants
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
