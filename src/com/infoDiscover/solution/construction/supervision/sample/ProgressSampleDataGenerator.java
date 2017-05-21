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
import com.infoDiscover.solution.arch.progress.constants.ProgressConstants;
import com.infoDiscover.solution.arch.progress.manager.ProgressManager;
import com.infoDiscover.solution.arch.progress.manager.ProgressRelationManager;
import com.infoDiscover.solution.arch.progress.manager.TaskManager;
import com.infoDiscover.solution.arch.progress.manager.UserManager;
import com.infoDiscover.solution.common.fact.FactManager;
import com.infoDiscover.solution.common.util.Constants;
import com.infoDiscover.solution.common.util.RandomData;
import com.infoDiscover.solution.construction.supervision.database.SupervisionSolutionConstants;
import com.infoDiscover.solution.construction.supervision.util.SampleFileUtil;
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
                SampleDataSet.TASKS_OF_MAINTENANCE_PROJECT.length,
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
                SampleDataSet.TASKS_OF_NEW_PROJECT.length,
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
                SampleDataSet.TASKS_OF_NEW_PROJECT.length,
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
                SampleDataSet.TASKS_OF_NEW_PROJECT.length,
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
                progressProperties.put(JsonConstants.PROGRESS_TYPE, SampleDataSet
                        .PROJECTTYPE_MAINTENANCE);
            } else if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_NEW)) {
                progressProperties.put(JsonConstants.PROGRESS_TYPE, SampleDataSet.PROJECTTYPE_NEW);
            } else if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_EXTENSION)) {
                progressProperties.put(JsonConstants.PROGRESS_TYPE, SampleDataSet
                        .PROJECTTYPE_EXTENSION);
            } else if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_REBUILD)) {
                progressProperties.put(JsonConstants.PROGRESS_TYPE, SampleDataSet
                        .PROJECTTYPE_REBUILD);
            }

            String progressId = progressProperties.get("progressId").toString();
            String factType = getFactType(projectType);

            if (firstNumberOfTasksToGenerate == 0) {
                // only create progress
                // TODO: check if the progress is already created and the task is running
                // update the progress name
                DateTime dateTime = DateUtil.getDateTime(startDateLongValue);
                String progressName = progressProperties.get(JsonConstants.PROGRESS_TYPE) + "_" +
                        dateTime.toString().substring(0, 10);
                progressProperties.put(JsonConstants.PROGRESS_NAME, progressName);
                createNewOrUpdateProgressInstance(ids, ids.getInformationExplorer(), factType,
                        progressProperties);
            } else {

                Map<String, Object>[] tasksPropertiesArray = TaskSampleDataGenerator
                        .generateTasksRandomData(projectJsonTemplate, projectType, progressId,
                                startDateLongValue, firstNumberOfTasksToGenerate);

                // generate progress name
                Map<String, Object> task1 = tasksPropertiesArray[0];
                String dateTime = DateUtil.getDateTime(startDateLongValue).toString().substring
                        (0, 10);
                String progressName = progressProperties.get(JsonConstants.PROGRESS_TYPE)
                        .toString();
                if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_MAINTENANCE)) {
                    String issue = task1.get(ClassificationConstants.ISSUE_CLASSIFICATION)
                            .toString();
                    progressName += "_" + issue + "_" + dateTime;
                } else {
                    String projectAddress = task1.get("projectAddress").toString();
                    progressName += "_" + projectAddress + "_" + dateTime;
                }
                progressProperties.put(JsonConstants.PROGRESS_NAME, progressName);

                // Append task properties to progress
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


                // to create or update progress
                createNewOrUpdateProgressInstance(ids, ids.getInformationExplorer(), factType,
                        progressProperties);

                // batch create or update tasks
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

        String progressType = progressProperties.get(JsonConstants.PROGRESS_TYPE).toString();
        Map<String, String> taskNameMap = new HashMap<>();
        if (progressType.equals(SampleDataSet.PROJECTTYPE_MAINTENANCE)) {
            taskNameMap = SampleFileUtil.readMaintenaceProjectTasks(null);
        } else {
            taskNameMap = SampleFileUtil.readNewProjectTasks(null);
        }

        for (Map<String, Object> taskProps : tasksPropertiesArray) {
            Set<String> keySet = taskProps.keySet();
            Iterator<String> it = keySet.iterator();

            String taskName = "";
            Date startDate = new Date();
            Date endDate = new Date();

            String workerId = "";
            String worker = "";
            String executiveDepartmentId = "";
            String executiveDepartment = "";
            String companyClassification = "";
            while (it.hasNext()) {
                String key = it.next();
                Object value = taskProps.get(key);
                if (key.equalsIgnoreCase(JsonConstants.TASK_NAME)) {
                    taskName = value.toString();
                    taskName = taskNameMap.get(taskName);
                }
                if (key.equalsIgnoreCase(JsonConstants.START_DATE)) {
                    startDate = (Date) value;
                }
                if (key.equalsIgnoreCase(JsonConstants.END_DATE)) {
                    endDate = (Date) value;
                }

                if (key.equalsIgnoreCase(JsonConstants.WORKER_ID)) {
                    workerId = value.toString();
                }

                if (key.equalsIgnoreCase(JsonConstants.WORKER)) {
                    worker = value.toString();
                }

                if (key.equalsIgnoreCase(JsonConstants.EXECUTIVE_DEPARTMENT_ID)) {
                    executiveDepartmentId = value.toString();
                }

                if (key.equalsIgnoreCase(JsonConstants.EXECUTIVE_DEPARTMENT)) {
                    executiveDepartment = value.toString();
                }

                if (key.equalsIgnoreCase(ClassificationConstants.COMPANY_CLASSIFICATION)) {
                    companyClassification = value.toString();
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
            progressProperties.put(taskName + "_workerId", workerId);
            progressProperties.put(taskName + "_worker", worker);
            progressProperties.put(taskName + "_executiveDepartmentId", executiveDepartmentId);
            progressProperties.put(taskName + "_executiveDepartment", executiveDepartment);
            if (companyClassification != null && !companyClassification.trim().isEmpty()) {
                progressProperties.put(taskName + "_companyClassification", companyClassification);
            }
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
        list.add("executiveDepartmentId");
        list.add("worker");
        list.add("workerId");
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
//            properties.remove(JsonConstants.JSON_TYPE);

            // create or update fact
            Fact progressFact = progressManager.getProgressById(ie, progressId, factType);
            FactManager factManager = new FactManager(ids);
            if (progressFact == null) {
                progressFact = factManager.createFact(factType, properties);
            } else {
                progressFact = factManager.updateFact(progressFact, properties);
            }

            ProgressRelationManager progressRelationManager = new ProgressRelationManager(ids);
            SampleDimensionGenerator dimensionGenerator = new SampleDimensionGenerator(ids);

            // link starter to progress
            Object starterId = properties.get(JsonConstants.PROGRESS_STARTER_ID);
            if (starterId != null && !starterId.toString().isEmpty()) {
                Dimension startDimension = dimensionGenerator.getUser(starterId.toString());
                if (startDimension != null) {
                    ids.attachFactToDimension(progressFact.getId(), startDimension.getId(),
                            SupervisionSolutionConstants.RELATION_START_BY_WITH_PREFIX);
                }
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

            // link dimensions
            Object constructionType = properties.get(ClassificationConstants.CONSTRUCTION_TYPE);
            if (constructionType != null) {
                Dimension dimension = dimensionGenerator.getDimension
                        (SupervisionSolutionConstants.DIMENSION_CONSTRUCTION_TYPE_WITH_PREFIX,
                                Constants.DIMENSION_NAME, constructionType.toString());
                if (dimension != null) {
                    ids.attachFactToDimension(progressFact.getId(), dimension.getId(),
                            SupervisionSolutionConstants.RELATION_CONSTRUCTION_TYPE_WITH_PREFIX);
                }
            }

            Object assignModel = properties.get(ClassificationConstants.ASSIGN_MODEL);
            if (assignModel != null) {
                Dimension dimension = dimensionGenerator.getDimension
                        (SupervisionSolutionConstants.DIMENSION_ASSIGN_MODEL_WITH_PREFIX,
                                Constants.DIMENSION_NAME, assignModel.toString());
                if (dimension != null) {
                    ids.attachFactToDimension(progressFact.getId(), dimension.getId(),
                            SupervisionSolutionConstants.RELATION_ASSIGN_MODEL_WITH_PREFIX);
                }
            }

            Object issueClassification = properties.get(ClassificationConstants
                    .ISSUE_CLASSIFICATION);
            if (issueClassification != null) {
                Dimension dimension = dimensionGenerator.getDimension
                        (SupervisionSolutionConstants.DIMENSION_ISSUE_CLASSIFICATION_WITH_PREFIX,
                                Constants.DIMENSION_NAME, issueClassification.toString());
                if (dimension != null) {
                    ids.attachFactToDimension(progressFact.getId(), dimension.getId(),
                            SupervisionSolutionConstants.RELATION_ISSUE_CLASSIFICATION_WITH_PREFIX);
                }
            }

            Object landProperty = properties.get(ClassificationConstants.LAND_PROPERTY);
            if (landProperty != null) {
                Dimension dimension = dimensionGenerator.getDimension
                        (SupervisionSolutionConstants.DIMENSION_LAND_PROPERTY_WITH_PREFIX,
                                Constants.DIMENSION_NAME, landProperty.toString());
                if (dimension != null) {
                    ids.attachFactToDimension(progressFact.getId(), dimension.getId(),
                            SupervisionSolutionConstants.RELATION_LAND_PROPERTY_WITH_PREFIX);
                }
            }

            Object projectClassification = properties.get(ClassificationConstants
                    .PROJECT_CLASSIFICATION);
            if (projectClassification != null) {
                Dimension dimension = dimensionGenerator.getDimension
                        (SupervisionSolutionConstants.DIMENSION_PROJECT_CLASSIFICATION_WITH_PREFIX,
                                Constants.DIMENSION_NAME, projectClassification.toString());
                if (dimension != null) {
                    ids.attachFactToDimension(progressFact.getId(), dimension.getId(),
                            SupervisionSolutionConstants
                                    .RELATION_PROJECT_CLASSIFICATION_WITH_PREFIX);
                }
            }

            Object projectSiteClassification = properties.get(ClassificationConstants
                    .PROJECT_SITE_CLASSIFICATION);
            if (projectSiteClassification != null) {
                Dimension dimension = dimensionGenerator.getDimension
                        (SupervisionSolutionConstants
                                        .DIMENSION_PROJECT_SITE_CLASSIFICATION_WITH_PREFIX,
                                Constants.DIMENSION_NAME, projectSiteClassification.toString());
                if (dimension != null) {
                    ids.attachFactToDimension(progressFact.getId(), dimension.getId(),
                            SupervisionSolutionConstants
                                    .RELATION_PROJECT_SITE_CLASSIFICATION_WITH_PREFIX);
                }
            }

            Object projectScope = properties.get(ClassificationConstants.PROJECT_SCOPE);
            if (projectScope != null) {
                Dimension dimension = dimensionGenerator.getDimension
                        (SupervisionSolutionConstants.DIMENSION_PROJECT_SCOPE_WITH_PREFIX,
                                Constants.DIMENSION_NAME, projectScope.toString());
                if (dimension != null) {
                    ids.attachFactToDimension(progressFact.getId(), dimension.getId(),
                            SupervisionSolutionConstants.RELATION_PROJECT_SCOPE_WITH_PREFIX);
                }
            }

            // link project to projectConstructionClassification
            Object projectConstructionClassification = properties.get(ClassificationConstants
                    .PROJECT_CONSTRUCTION_CLASSIFICATION);
            if (projectConstructionClassification != null) {
                Dimension dimension = dimensionGenerator.getDimension(SupervisionSolutionConstants
                        .DIMENSION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX, Constants
                        .DIMENSION_NAME, projectConstructionClassification.toString());
                if (dimension != null) {
                    ids.attachFactToDimension(progressFact.getId(), dimension.getId(),
                            SupervisionSolutionConstants
                                    .RELATION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX);
                }
            }

            Object projectAddress = properties.get(ClassificationConstants.PROJECT_ADDRESS);
            if (projectAddress != null) {
                Dimension dimension = dimensionGenerator.getDimension
                        (SupervisionSolutionConstants.DIMENSION_PROJECT_ADDRESS_WITH_PREFIX,
                                Constants.DIMENSION_NAME, projectAddress.toString());
                if (dimension != null) {
                    ids.attachFactToDimension(progressFact.getId(), dimension.getId(),
                            SupervisionSolutionConstants.RELATION_PROJECT_ADDRESS_AT_WITH_PREFIX);
                }
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
            //properties.remove(JsonConstants.JSON_TYPE);

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

            // link worker to task
            String userId = properties.get(JsonConstants.WORKER_ID).toString();
            UserManager userManager = new UserManager();
            Dimension userDimension = userManager.getUserById(ids.getInformationExplorer(),
                    userId, SupervisionSolutionConstants.DIMENSION_USER_WITH_PREFIX);
            relationManager.attachUserToTask(taskTact, userDimension, SupervisionSolutionConstants
                    .RELATION_TASK_EXECUTEBYUSER_WITH_PREFIX);

            // link executive department to task
            String departmentId = properties.get(JsonConstants.EXECUTIVE_DEPARTMENT_ID).toString();
            Dimension departmentDimension = new SampleDimensionGenerator(ids).getDimension
                    (SupervisionSolutionConstants.DIMENSION_EXECUTIVE_DEPARTMENT_WITH_PREFIX,
                            Constants.DIMENSION_ID, departmentId);
            if (departmentDimension != null) {
                relationManager.attachRoleToTask(taskTact, departmentDimension,
                        SupervisionSolutionConstants.RELATION_TASK_EXECUTEBYDEPARTMENT_WITH_PREFIX);
            }

            // link startDate to task
            DayDimensionVO dayDimension = getDayDimension(SupervisionSolutionConstants
                    .SOLUTION_PREFIX, (Date)
                    properties.get(JsonConstants.START_DATE));
            relationManager.attachTimeToTask(ids, taskTact, dayDimension, ProgressConstants
                    .RELATIONTYPE_STARTAT_WITHPREFIX);

            // link endDate to task
            if (properties.get(JsonConstants.END_DATE) != null) {
                dayDimension = getDayDimension(SupervisionSolutionConstants.SOLUTION_PREFIX,
                        (Date) properties.get(JsonConstants.END_DATE));
                relationManager.attachTimeToTask(ids, taskTact, dayDimension, ProgressConstants
                        .RELATIONTYPE_ENDAT_WITHPREFIX);
            }

            // link to company classification
            SampleDimensionGenerator dimensionGenerator = new SampleDimensionGenerator(ids);
            Object companyClassification = properties.get(ClassificationConstants
                    .COMPANY_CLASSIFICATION);
            if (companyClassification != null) {
                Dimension dimension = dimensionGenerator.getDimension
                        (SupervisionSolutionConstants.DIMENSION_COMPANY_CLASSIFICATION_WITH_PREFIX,
                                Constants.DIMENSION_NAME, companyClassification.toString());
                if (dimension != null) {
                    ids.attachFactToDimension(taskTact.getId(), dimension.getId(),
                            SupervisionSolutionConstants
                                    .RELATION_COMPANY_CLASSIFICATION_WITH_PREFIX);
                }
            }

            // execute by company
            Object biddingAgency = properties.get("biddingAgency");
            if (biddingAgency != null) {
                executeByCompany(ids, taskTact.getId(), biddingAgency.toString());
            }

            Object projectPlanMakingCompany = properties.get("projectPlanMakingCompany");
            if (projectPlanMakingCompany != null) {
                executeByCompany(ids, taskTact.getId(), projectPlanMakingCompany.toString());
            }

            Object surveyCompany = properties.get("surveyCompany");
            if (surveyCompany != null) {
                executeByCompany(ids, taskTact.getId(), surveyCompany.toString());
            }

            Object designCompany = properties.get("designCompany");
            if (designCompany != null) {
                executeByCompany(ids, taskTact.getId(), designCompany.toString());
            }

            Object costConsultationCompany = properties.get("costConsultationCompany");
            if (costConsultationCompany != null) {
                executeByCompany(ids, taskTact.getId(), costConsultationCompany.toString());
            }

            Object constructionCompany = properties.get("constructionCompany");
            if (constructionCompany != null) {
                executeByCompany(ids, taskTact.getId(), constructionCompany.toString());
            }

            Object supervisionCompany = properties.get("supervisionCompany");
            if (supervisionCompany != null) {
                executeByCompany(ids, taskTact.getId(), supervisionCompany.toString());
            }

            // is in charge of
            Object biddingAgencyLeader = properties.get("biddingAgencyLeader");
            if (biddingAgencyLeader != null) {
                isInChargeOf(ids, taskTact.getId(), biddingAgencyLeader.toString());
            }

            Object consultationCompanyLeader = properties.get("consultationCompanyLeader");
            if (consultationCompanyLeader != null) {
                isInChargeOf(ids, taskTact.getId(), consultationCompanyLeader.toString());
            }

            Object surveyCompanyLeader = properties.get("surveyCompanyLeader");
            if (surveyCompanyLeader != null) {
                isInChargeOf(ids, taskTact.getId(), surveyCompanyLeader.toString());
            }
            Object designCompanyLeader = properties.get("designCompanyLeader");
            if (designCompanyLeader != null) {
                isInChargeOf(ids, taskTact.getId(), designCompanyLeader.toString());
            }
            Object costConsultationCompanyLeader = properties.get("costConsultationCompanyLeader");
            if (costConsultationCompanyLeader != null) {
                isInChargeOf(ids, taskTact.getId(), costConsultationCompanyLeader.toString());
            }
            Object constructionCompanyLeader = properties.get("constructionCompanyLeader");
            if (constructionCompanyLeader != null) {
                isInChargeOf(ids, taskTact.getId(), constructionCompanyLeader.toString());
            }
            Object supervisionCompanyContact = properties.get("supervisionCompanyContact");
            if (supervisionCompanyContact != null) {
                isInChargeOf(ids, taskTact.getId(), supervisionCompanyContact.toString());
            }


        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.error(e.getMessage());
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.error(e.getMessage());
        }

        logger.info("Exit createNewOrUpdateTaskInstance()...");
    }


    private static void executeByCompany(InfoDiscoverSpace ids, String progressFactId, String
            companyName) {

        SampleDimensionGenerator dimensionGenerator = new SampleDimensionGenerator(ids);

        Dimension dimension = dimensionGenerator.getDimension
                (SupervisionSolutionConstants.DIMENSION_COMPANY_WITH_PREFIX,
                        "companyName", companyName);
        if (dimension != null) {
            try {
                ids.attachFactToDimension(progressFactId, dimension.getId(),
                        SupervisionSolutionConstants.RELATION_EXECUTE_BY_COMPANY_WITH_PREFIX);
            } catch (InfoDiscoveryEngineRuntimeException e) {
                logger.error("Failed to add relationship executeByCompany: {} with error: ",
                        companyName, e
                                .getMessage());
            }
        }

    }

    private static void isInChargeOf(InfoDiscoverSpace ids, String progressFactId, String
            userName) {

        SampleDimensionGenerator dimensionGenerator = new SampleDimensionGenerator(ids);

        Dimension dimension = dimensionGenerator.getDimension
                (SupervisionSolutionConstants.DIMENSION_EXTERNAL_USER_WITH_PREFIX,
                        "userName", userName);
        if (dimension != null) {
            try {
                ids.attachFactToDimension(progressFactId, dimension.getId(),
                        SupervisionSolutionConstants.RELATION_IN_CHARGE_OF_WITH_PREFIX);
            } catch (InfoDiscoveryEngineRuntimeException e) {
                logger.error("Failed to add relationship isInChargeOf: {} with error: ",
                        userName, e.getMessage());
            }
        }
    }

}
