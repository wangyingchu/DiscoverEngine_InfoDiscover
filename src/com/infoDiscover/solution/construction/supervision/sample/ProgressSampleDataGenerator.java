package com.infoDiscover.solution.construction.supervision.sample;

import com.infoDiscover.common.dimension.time.constants.TimeDimensionConstants;
import com.infoDiscover.common.dimension.time.dimension.DayDimensionVO;
import com.infoDiscover.common.util.DateUtil;
import com.infoDiscover.common.util.RandomUtil;
import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.arch.progress.constants.ProgressConstants;
import com.infoDiscover.solution.arch.progress.manager.ProgressManager;
import com.infoDiscover.solution.arch.progress.manager.ProgressRelationManager;
import com.infoDiscover.solution.arch.progress.manager.TaskManager;
import com.infoDiscover.solution.arch.progress.manager.UserManager;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.common.fact.FactManager;
import com.infoDiscover.solution.common.relationship.RelationshipManager;
import com.infoDiscover.solution.common.util.Constants;
import com.infoDiscover.solution.common.util.RandomData;
import com.infoDiscover.solution.construction.supervision.database.SupervisionSolutionConstants;
import com.infoDiscover.solution.sample.util.JsonConstants;
import com.infoDiscover.solution.sample.util.ProgressRandomData;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static void generateExtensionProjectSampleData(
            InfoDiscoverSpace ids,
            int countOfProgressToGenerate,
            boolean toGenerateRandomTaskNumber) {

        generateProjectSampleData(
                ids,
                SampleDataSet.FILE_EXTENSION_PROJECT,
                SampleDataSet.PROJECTTYPE_EXTENSION,
                SampleDataSet.TASKS_OF_NEW.length,
                countOfProgressToGenerate,
                toGenerateRandomTaskNumber);
    }

    public static void generateRebuildProjectSampleData(
            InfoDiscoverSpace ids,
            int countOfProgressToGenerate,
            boolean toGenerateRandomTaskNumber) {

        generateProjectSampleData(
                ids,
                SampleDataSet.FILE_REBUILD_PROJECT,
                SampleDataSet.PROJECTTYPE_REBUILD,
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

            // generate progress random data
            Map<String, Object> progressProperties = ProgressRandomData
                    .generateProgressRandomData(projectJsonTemplate, projectType, getProjectName
                            (projectType), startDate, i);
            if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_MAINTENANCE)) {
                progressProperties.put(JsonConstants.PROGRESS_TYPE, "MaintenanceProject");
            } else if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_NEW)) {
                progressProperties.put(JsonConstants.PROGRESS_TYPE, "NewProject");
            } else if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_EXTENSION)) {
                progressProperties.put(JsonConstants.PROGRESS_TYPE, "ExtensionProject");
            } else if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_REBUILD)) {
                progressProperties.put(JsonConstants.PROGRESS_TYPE, "RebuildingProject");
            }

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
        if (projectType.trim().equalsIgnoreCase(SampleDataSet.PROJECTTYPE_MAINTENANCE)) {
            return SampleDataSet.FACTTYPE_MAINTENANCE_PROJECT;
        } else if (projectType.trim().equalsIgnoreCase(SampleDataSet.PROJECTTYPE_NEW)) {
            return SampleDataSet.FACTTYPE_NEW_PROJECT;
        } else if (projectType.trim().equalsIgnoreCase(SampleDataSet.PROJECTTYPE_REBUILD)) {
            return SampleDataSet.FACTTYPE_REBUILD_PROJECT;
        } else {
            return SampleDataSet.FACTTYPE_EXTENSION_PROJECT;
        }
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
        } else if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_EXTENSION)) {
            return SampleDataSet.PROJECTNAME_EXTENSION;
        } else if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_NEW)) {
            return SampleDataSet.PROJECTNAME_NEW;
        } else {
            return SampleDataSet.PROJECTNAME_REBUILD;
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
            FactManager factManager = new FactManager(ids);
            if (progressFact == null) {
                progressFact = factManager.createFact(factType, properties);
            } else {
                progressFact = factManager.updateFact(progressFact, properties);
            }

            ProgressRelationManager progressRelationManager = new ProgressRelationManager(ids);
            //TODO:
            // link starter to progress
            String starter = properties.get(JsonConstants.PROGRESS_STARTER).toString();

            // link project to projectConstructionClassification
            if (properties.get(JsonConstants.PROGRESS_TYPE) != null) {
                String projectType = properties.get(JsonConstants.PROGRESS_TYPE).toString();

                RelationshipManager relationshipManager = new RelationshipManager();
                relationshipManager.attachFactToDimension(ids, progressFact.getId(), projectType,
                        SupervisionSolutionConstants
                                .DIMENSION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX,
                        SupervisionSolutionConstants
                                .RELATION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX);
            }

            // link startDate to progress
            DayDimensionVO dayDimension = getDayDimension(SupervisionSolutionConstants
                    .SOLUTION_PREFIX, (Date)
                    properties.get(JsonConstants
                            .START_DATE));
            progressRelationManager.attachTimeToProgress(ids, progressId, factType, dayDimension,
                    ProgressConstants.RELATIONTYPE_STARTAT_WITHPREFIX);

            // link endTime to progress
            if (properties.get(JsonConstants.END_DATE) != null) {
                dayDimension = getDayDimension(SupervisionSolutionConstants.SOLUTION_PREFIX,
                        (Date) properties.get(JsonConstants.END_DATE));
                progressRelationManager.attachTimeToProgress(ids, progressId, factType,
                        dayDimension, ProgressConstants.RELATIONTYPE_ENDAT_WITHPREFIX);
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
//    private static String getFact(String type) {
//
//        if (type.trim().equalsIgnoreCase(FactTypeEnum.Progress.toString())) {
//            return SupervisionSolutionConstants.FACT_PROGRESS_WITH_PREFIX;
//        } else if (type.trim().equalsIgnoreCase(FactTypeEnum.Task.toString())) {
//            return SupervisionSolutionConstants.FACT_TASK_WITH_PREFIX;
//        }
//        return "";
//    }

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
            String taskFactType = SupervisionSolutionConstants.FACT_TASK_WITH_PREFIX;
            logger.info("Fact type is: {}", taskFactType);

            // remove type from properties
            properties.remove(JsonConstants.JSON_TYPE);

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
            ProgressManager progressManager = new ProgressManager();
            Fact progressFact = progressManager.getProgressById(ids.getInformationExplorer(),
                    progressId,
                    progressFactType);
            relationManager.attachTaskToProgress(progressFact, taskTact,
                    SupervisionSolutionConstants.RELATION_PROGRESS_HASTASK_WITH_PREFIX);

            // link user to task
            String userId = properties.get(JsonConstants.WORKER).toString();
            UserManager userManager = new UserManager();
            Dimension userDimension = userManager.getUserById(ids.getInformationExplorer(),
                    userId, SupervisionSolutionConstants.DIMENSION_USER_WITH_PREFIX);
            relationManager.attachUserToTask(taskTact, userDimension, SupervisionSolutionConstants
                    .RELATION_TASK_EXECUTEBYUSER_WITH_PREFIX);

            // link executive department to task
            String departmentId = properties.get(JsonConstants.EXECUTIVEDEPARTMENT).toString();
            ExploreParameters ep = new ExploreParameters();
            ep.setType(SupervisionSolutionConstants.DIMENSION_EXECUTIVE_DEPARTMENT_WITH_PREFIX);
            ep.setDefaultFilteringItem(new EqualFilteringItem(Constants.DIMENSION_ID,
                    departmentId));
            Dimension departmentDimension = QueryExecutor.executeDimensionQuery(ids
                    .getInformationExplorer(), ep);

            relationManager.attachRoleToTask(taskTact, departmentDimension,
                    SupervisionSolutionConstants.RELATION_TASK_EXECUTEBYDEPARTMENT_WITH_PREFIX);

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

            // link dimensions
            String dimensionId = "";
            String dimensionTypeName = "";
            String relationType = "";

            RelationshipManager relationshipManager = new RelationshipManager();

            if (properties.get("constructionType") != null) {
                dimensionId = properties.get("constructionType").toString();
                dimensionTypeName = SupervisionSolutionConstants
                        .DIMENSION_CONSTRUCTION_TYPE_WITH_PREFIX;
                relationType = SupervisionSolutionConstants.RELATION_CONSTRUCTION_TYPE_WITH_PREFIX;
                if (dimensionId != "" && dimensionTypeName != "" && relationType != "") {
                    relationshipManager.attachFactToDimension(ids, taskTact.getId(), dimensionId,
                            dimensionTypeName, relationType);
                }
            }

            if (properties.get("companyClassification") != null) {
                dimensionId = properties.get("companyClassification").toString();
                dimensionTypeName = SupervisionSolutionConstants
                        .DIMENSION_COMPANY_CLASSIFICATION_WITH_PREFIX;
                relationType = SupervisionSolutionConstants
                        .RELATION_COMPANY_CLASSIFICATION_WITH_PREFIX;
                if (dimensionId != "" && dimensionTypeName != "" && relationType != "") {
                    relationshipManager.attachFactToDimension(ids, taskTact.getId(), dimensionId,
                            dimensionTypeName, relationType);
                }
            }
            if (properties.get("assignModel") != null) {
                dimensionId = properties.get("assignModel").toString();
                dimensionTypeName = SupervisionSolutionConstants
                        .DIMENSION_ASSIGN_MODEL_WITH_PREFIX;
                relationType = SupervisionSolutionConstants.RELATION_ASSIGN_MODEL_WITH_PREFIX;
                if (dimensionId != "" && dimensionTypeName != "" && relationType != "") {
                    relationshipManager.attachFactToDimension(ids, taskTact.getId(), dimensionId,
                            dimensionTypeName, relationType);
                }
            }
//            else if (properties.get("executiveDepartment") != null) {
//                dimensionId = properties.get("executiveDepartment").toString();
//                dimensionTypeName = SupervisionSolutionConstants
//                        .DIMENSION_EXECUTIVE_DEPARTMENT_WITH_PREFIX;
//                relationType = SupervisionSolutionConstants
//                        .RELATION_TASK_EXECUTEBYDEPARTMENT_WITH_PREFIX;
//            if (dimensionId != "" && dimensionTypeName != "" && relationType != "") {
//                relationshipManager.attachFactToDimension(ids, taskTact.getId(), dimensionId,
//                        dimensionTypeName, relationType);
//            }
//            }
            if (properties.get("issueClassification") != null) {
                dimensionId = properties.get("issueClassification").toString();
                dimensionTypeName = SupervisionSolutionConstants
                        .DIMENSION_ISSUE_CLASSIFICATION_WITH_PREFIX;
                relationType = SupervisionSolutionConstants
                        .RELATION_ISSUE_CLASSIFICATION_WITH_PREFIX;
                if (dimensionId != "" && dimensionTypeName != "" && relationType != "") {
                    relationshipManager.attachFactToDimension(ids, taskTact.getId(), dimensionId,
                            dimensionTypeName, relationType);
                }
            }
            if (properties.get("landProperty") != null) {
                dimensionId = properties.get("landProperty").toString();
                dimensionTypeName = SupervisionSolutionConstants
                        .DIMENSION_LAND_PROPERTY_WITH_PREFIX;
                relationType = SupervisionSolutionConstants.RELATION_LAND_PROPERTY_WITH_PREFIX;
                if (dimensionId != "" && dimensionTypeName != "" && relationType != "") {
                    relationshipManager.attachFactToDimension(ids, taskTact.getId(), dimensionId,
                            dimensionTypeName, relationType);
                }
            }
            if (properties.get("assetFirstClassification") != null) {
                dimensionId = properties.get("assetFirstClassification").toString();
                dimensionTypeName = SupervisionSolutionConstants
                        .DIMENSION_ASSET_FIRST_CLASSIFICATION_WITH_PREFIX;
                relationType = SupervisionSolutionConstants
                        .RELATION_ASSET_FIRST_CLASSIFICATION_WITH_PREFIX;
                if (dimensionId != "" && dimensionTypeName != "" && relationType != "") {
                    relationshipManager.attachFactToDimension(ids, taskTact.getId(), dimensionId,
                            dimensionTypeName, relationType);
                }
            }
            if (properties.get("assetSecondClassification") != null) {
                dimensionId = properties.get("assetSecondClassification").toString();
                dimensionTypeName = SupervisionSolutionConstants
                        .DIMENSION_ASSET_SECOND_CLASSIFICATION_WITH_PREFIX;
                relationType = SupervisionSolutionConstants
                        .RELATION_ASSET_SECOND_CLASSIFICATION_WITH_PREFIX;
                if (dimensionId != "" && dimensionTypeName != "" && relationType != "") {
                    relationshipManager.attachFactToDimension(ids, taskTact.getId(), dimensionId,
                            dimensionTypeName, relationType);
                }
            }
            if (properties.get("projectClassification") != null) {
                dimensionId = properties.get("projectClassification").toString();
                dimensionTypeName = SupervisionSolutionConstants
                        .DIMENSION_PROJECT_CLASSIFICATION_WITH_PREFIX;
                relationType = SupervisionSolutionConstants
                        .RELATION_PROJECT_CLASSIFICATION_WITH_PREFIX;
                if (dimensionId != "" && dimensionTypeName != "" && relationType != "") {
                    relationshipManager.attachFactToDimension(ids, taskTact.getId(), dimensionId,
                            dimensionTypeName, relationType);
                }
            }
            if (properties.get("projectSiteClassification") != null) {
                dimensionId = properties.get("projectSiteClassification").toString();
                dimensionTypeName = SupervisionSolutionConstants
                        .DIMENSION_PROJECT_SITE_CLASSIFICATION_WITH_PREFIX;
                relationType = SupervisionSolutionConstants
                        .RELATION_PROJECT_SITE_CLASSIFICATION_WITH_PREFIX;
                if (dimensionId != "" && dimensionTypeName != "" && relationType != "") {
                    relationshipManager.attachFactToDimension(ids, taskTact.getId(), dimensionId,
                            dimensionTypeName, relationType);
                }
            }
            if (properties.get("projectScope") != null) {
                dimensionId = properties.get("projectScope").toString();
                dimensionTypeName = SupervisionSolutionConstants
                        .DIMENSION_PROJECT_SCOPE_WITH_PREFIX;
                relationType = SupervisionSolutionConstants.RELATION_PROJECT_SCOPE_WITH_PREFIX;
                if (dimensionId != "" && dimensionTypeName != "" && relationType != "") {
                    relationshipManager.attachFactToDimension(ids, taskTact.getId(), dimensionId,
                            dimensionTypeName, relationType);
                }
            }
            if (properties.get("projectConstructionClassification") != null) {
                dimensionId = properties.get("projectConstructionClassification").toString();
                dimensionTypeName = SupervisionSolutionConstants
                        .DIMENSION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX;
                relationType = SupervisionSolutionConstants
                        .RELATION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX;
                if (dimensionId != "" && dimensionTypeName != "" && relationType != "") {
                    relationshipManager.attachFactToDimension(ids, taskTact.getId(), dimensionId,
                            dimensionTypeName, relationType);
                }
            }

        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.error(e.getMessage());
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.error(e.getMessage());
        }

        logger.info("Exit createNewOrUpdateTaskInstance()...");
    }

}
