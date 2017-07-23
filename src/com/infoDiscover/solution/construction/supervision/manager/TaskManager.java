package com.infoDiscover.solution.construction.supervision.manager;

import com.infoDiscover.common.dimension.time.DayDimensionManager;
import com.infoDiscover.common.dimension.time.dimension.DayDimensionVO;
import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.arch.progress.constants.ProgressConstants;
import com.infoDiscover.solution.arch.progress.manager.ProgressRelationManager;
import com.infoDiscover.solution.common.dimension.DimensionManager;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.common.fact.FactManager;
import com.infoDiscover.solution.common.relationship.RelationshipManager;
import com.infoDiscover.solution.common.util.Constants;
import com.infoDiscover.solution.construction.supervision.constants.DatabaseConstants;
import com.infoDiscover.solution.construction.supervision.constants.JsonConstants;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by sun.
 */
public class TaskManager {
    private final static Logger logger = LoggerFactory.getLogger(TaskManager.class);

    private InfoDiscoverSpace ids;

    public TaskManager(InfoDiscoverSpace ids) {
        this.ids = ids;
    }

    public Fact getTaskById(InformationExplorer ie, String factType, String key, String taskId) {

        ExploreParameters ep = new ExploreParameters();
        ep.setType(factType);
        if (key == null) {
            key = JsonConstants.JSON_TASK_ID;
        }
        ep.setDefaultFilteringItem(new EqualFilteringItem(key, taskId));

        return QueryExecutor.executeFactQuery(ie, ep);
    }

    public void batchCreateNewOrUpdateTaskInstances(
            String progressFactType,
            Map<String, Object>[] tasksPropertiesArray) {

        if (tasksPropertiesArray != null) {
            for (Map<String, Object> taskProperties : tasksPropertiesArray) {
                createNewOrUpdateTaskInstance(progressFactType, taskProperties);
            }
        }
    }

    public void createNewOrUpdateTaskInstance(String projectFactType, Map<String, Object>
            properties) {
        logger.info("Enter method createNewOrUpdateTaskInstance() with ids: {} with " +
                "projectFactType: {} and properties: {}", ids, projectFactType, properties);

        String projectId = properties.get(JsonConstants.JSON_PROJECT_ID).toString();
        String taskId = properties.get(JsonConstants.JSON_TASK_ID).toString();
        try {
            String taskFactType = DatabaseConstants.FACT_TASK_WITH_PREFIX;
            logger.info("Fact type is: {}", taskFactType);

            // remove type from properties
            //properties.remove(JsonConstants.JSON_TYPE);


            // add properties types to task fact type
//            if (progressFactType.equalsIgnoreCase(SampleDataSet.FACTTYPE_MAINTENANCE_PROJECT)) {
//                updateFactTypeProperties(ids,taskFactType,properties, SampleAllProperties
//                        .getMaintenanceProjectProperties());
//            } else {
//                updateFactTypeProperties(ids,taskFactType,properties, SampleAllProperties
//                        .getNewProjectProperties());
//            }

            // create or update fact
            Fact taskFact = getTaskById(ids.getInformationExplorer(), taskFactType, null, taskId);
            FactManager factManager = new FactManager(ids);
            if (taskFact == null) {
                taskFact = factManager.createFact(taskFactType, properties);
            } else {
                taskFact = factManager.updateFact(taskFact, properties);
            }

            // link tasks to progress
            ProgressRelationManager relationManager = new ProgressRelationManager(ids);
            ProjectManager progressManager = new ProjectManager(ids);
            DimensionManager dimensionManager = new DimensionManager(ids);
            RelationshipManager relationshipManager = new RelationshipManager(ids);

            Fact projectFact = progressManager.getProjectId(ids.getInformationExplorer(),
                    projectFactType, null, projectId);
            if (projectFact == null) {
                logger.error("Project with id: {} is not existed.", projectId);
            }
            relationManager.attachTaskToProgress(projectFact, taskFact,
                    DatabaseConstants.RELATION_PROJECT_HASTASK_WITH_PREFIX);

            // link worker to task
            linkWorkerToProject(taskFact.getId(), properties, dimensionManager,
                    relationshipManager);

            // link executive department to task
            linkExecutiveDepartmentToTask(taskFact.getId(), properties, dimensionManager,
                    relationshipManager);

            // link startDate to task
            DayDimensionVO dayDimension = DayDimensionManager.getDayDimensionVOWithPrefix(DatabaseConstants
                    .SOLUTION_PREFIX, (Date) properties.get(JsonConstants.JSON_START_DATE));
            relationManager.attachTimeToTask(taskFact, dayDimension, ProgressConstants
                    .RELATIONTYPE_STARTAT_WITHPREFIX);

            // link endDate to task
            if (properties.get(JsonConstants.JSON_END_DATE) != null) {
                dayDimension = DayDimensionManager.getDayDimensionVOWithPrefix(DatabaseConstants
                                .SOLUTION_PREFIX,
                        (Date) properties.get(JsonConstants.JSON_END_DATE));
                relationManager.attachTimeToTask(taskFact, dayDimension, ProgressConstants
                        .RELATIONTYPE_ENDAT_WITHPREFIX);
            }

            // link to company classification
            linkCompanyToTask(taskFact.getId(), properties, dimensionManager, relationshipManager);

            // execute by company
            taskExecutedByCompany(taskFact.getId(), properties, dimensionManager,
                    relationshipManager);

            // is in charge of
            taskIsInChargeOf(taskFact.getId(), properties, dimensionManager, relationshipManager);

        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.error(e.getMessage());
        }

        logger.info("Exit createNewOrUpdateTaskInstance()...");
    }

    // link worker to project
    public void linkWorkerToProject(String taskFactId,
                                    Map<String, Object> properties,
                                    DimensionManager dimensionManager,
                                    RelationshipManager relationshipManager) {

        Object workerId = properties.get(JsonConstants.JSON_WORKER_ID);
        if (workerId != null && !workerId.toString().isEmpty()) {
            Dimension workerDimension = dimensionManager.getDimension
                    (DatabaseConstants.DIMENSION_USER_WITH_PREFIX,
                            DatabaseConstants.PROPERTY_USER_ID, workerId.toString());

            // if workerDimension is null, create it
            if (workerDimension == null) {
                Map<String, Object> props = new HashedMap();
                props.put(DatabaseConstants.PROPERTY_USER_ID, workerId);
                Object userName = properties.get(JsonConstants.JSON_WORKER);
                if (userName != null) {
                    props.put(DatabaseConstants.PROPERTY_USER_NAME, userName.toString());
                }

                workerDimension = dimensionManager.createDimension(DatabaseConstants
                        .DIMENSION_USER_WITH_PREFIX, props);
            }

            if (workerDimension != null) {
                relationshipManager.attachFactToDimension(taskFactId, workerDimension.getId(),
                        DatabaseConstants.RELATION_TASK_EXECUTE_BY_USER_WITH_PREFIX,
                        true);
            }
        }
    }

    //
    public void linkExecutiveDepartmentToTask(String taskFactId,
                                              Map<String, Object> properties,
                                              DimensionManager dimensionManager,
                                              RelationshipManager relationshipManager) {
        Object departmentId = properties.get(JsonConstants.JSON_EXECUTIVE_DEPARTMENT_ID);
        if (departmentId != null && !departmentId.toString().isEmpty()) {
            Dimension departmentDimension = new DimensionManager(ids).getDimension
                    (DatabaseConstants.DIMENSION_EXECUTIVE_DEPARTMENT_WITH_PREFIX,
                            Constants.DIMENSION_ID, departmentId.toString());

            // if departmentDimension is null, create it
            if (departmentDimension == null) {
                Map<String, Object> props = new HashedMap();
                props.put(Constants.DIMENSION_ID, departmentId);
                Object departmentName = properties.get(JsonConstants.JSON_EXECUTIVE_DEPARTMENT);
                if (departmentName != null) {
                    props.put(Constants.DIMENSION_NAME, departmentName.toString());
                }
                departmentDimension = dimensionManager.createDimension(DatabaseConstants
                        .DIMENSION_EXECUTIVE_DEPARTMENT_WITH_PREFIX, props);
            }

            if (departmentDimension != null) {
                relationshipManager.attachFactToDimension(taskFactId, departmentDimension.getId(),
                        DatabaseConstants.RELATION_TASK_EXECUTE_BY_DEPARTMENT_WITH_PREFIX, true);
            }
        }

    }

    private void linkCompanyToTask(String taskFactId,
                                   Map<String, Object> properties,
                                   DimensionManager dimensionManager,
                                   RelationshipManager relationshipManager) {

        Object companyClassification = properties.get(JsonConstants
                .JSON_COMPANY_CLASSIFICATION);
        if (companyClassification != null && !companyClassification.toString().isEmpty()) {
            Dimension dimension = new DimensionManager(ids).getDimension
                    (DatabaseConstants.DIMENSION_COMPANY_CLASSIFICATION_WITH_PREFIX,
                            Constants.DIMENSION_NAME, companyClassification.toString());

            // if dimension is null, create it
            if (dimension == null) {
                Map<String, Object> props = new HashedMap();
                props.put(Constants.DIMENSION_NAME, companyClassification.toString());
                dimension = dimensionManager.createDimension(DatabaseConstants
                        .DIMENSION_EXECUTIVE_DEPARTMENT_WITH_PREFIX, props);
            }

            if (dimension != null) {
                relationshipManager.attachFactToDimension(taskFactId, dimension.getId(),
                        DatabaseConstants.RELATION_COMPANY_CLASSIFICATION_WITH_PREFIX, true);
            }
        }
    }

    private void executeByCompany(String taskFactId, String companyName,
                                  DimensionManager dimensionManager,
                                  RelationshipManager relationshipManager) {

        Dimension dimension = new DimensionManager(ids).getDimension
                (DatabaseConstants.DIMENSION_COMPANY_WITH_PREFIX,
                        DatabaseConstants.PROPERTY_COMPANY_NAME, companyName);

        if (dimension == null) {
            Map<String, Object> props = new HashMap();
            props.put(DatabaseConstants.PROPERTY_COMPANY_NAME, companyName);
            dimension = dimensionManager.createDimension(DatabaseConstants
                            .DIMENSION_COMPANY_WITH_PREFIX,
                    props);
        }

        if (dimension != null) {
            relationshipManager.attachFactToDimension(taskFactId, dimension.getId(),
                    DatabaseConstants.RELATION_EXECUTE_BY_COMPANY_WITH_PREFIX, true);
        }

    }


    private void taskExecutedByCompany(String taskFactId,
                                       Map<String, Object> properties,
                                       DimensionManager dimensionManager,
                                       RelationshipManager relationshipManager) {
        Object biddingAgency = properties.get("biddingAgency");
        if (biddingAgency != null) {
            executeByCompany(taskFactId, biddingAgency.toString(), dimensionManager,
                    relationshipManager);
        }

        Object projectPlanMakingCompany = properties.get("projectPlanMakingCompany");
        if (projectPlanMakingCompany != null) {
            executeByCompany(taskFactId, projectPlanMakingCompany.toString(), dimensionManager,
                    relationshipManager);
        }

        Object surveyCompany = properties.get("surveyCompany");
        if (surveyCompany != null) {
            executeByCompany(taskFactId, surveyCompany.toString(), dimensionManager,
                    relationshipManager);
        }

        Object designCompany = properties.get("designCompany");
        if (designCompany != null) {
            executeByCompany(taskFactId, designCompany.toString(), dimensionManager,
                    relationshipManager);
        }

        Object costConsultationCompany = properties.get("costConsultationCompany");
        if (costConsultationCompany != null) {
            executeByCompany(taskFactId, costConsultationCompany.toString(), dimensionManager,
                    relationshipManager);
        }

        Object constructionCompany = properties.get("constructionCompany");
        if (constructionCompany != null) {
            executeByCompany(taskFactId, constructionCompany.toString(), dimensionManager,
                    relationshipManager);
        }

        Object supervisionCompany = properties.get("supervisionCompany");
        if (supervisionCompany != null) {
            executeByCompany(taskFactId, supervisionCompany.toString(), dimensionManager,
                    relationshipManager);
        }
    }

    private void taskIsInChargeOf(String taskFactId,
                                  Map<String, Object> properties,
                                  DimensionManager dimensionManager,
                                  RelationshipManager relationshipManager) {
        Object biddingAgencyLeader = properties.get("biddingAgencyLeader");
        if (biddingAgencyLeader != null) {
            isInChargeOf(taskFactId, biddingAgencyLeader.toString(), dimensionManager,
                    relationshipManager);
        }

        Object consultationCompanyLeader = properties.get("consultationCompanyLeader");
        if (consultationCompanyLeader != null) {
            isInChargeOf(taskFactId, consultationCompanyLeader.toString(), dimensionManager,
                    relationshipManager);
        }

        Object surveyCompanyLeader = properties.get("surveyCompanyLeader");
        if (surveyCompanyLeader != null) {
            isInChargeOf(taskFactId, surveyCompanyLeader.toString(), dimensionManager,
                    relationshipManager);
        }
        Object designCompanyLeader = properties.get("designCompanyLeader");
        if (designCompanyLeader != null) {
            isInChargeOf(taskFactId, designCompanyLeader.toString(), dimensionManager,
                    relationshipManager);
        }
        Object costConsultationCompanyLeader = properties.get("costConsultationCompanyLeader");
        if (costConsultationCompanyLeader != null) {
            isInChargeOf(taskFactId, costConsultationCompanyLeader.toString(), dimensionManager,
                    relationshipManager);
        }
        Object constructionCompanyLeader = properties.get("constructionCompanyLeader");
        if (constructionCompanyLeader != null) {
            isInChargeOf(taskFactId, constructionCompanyLeader.toString(), dimensionManager,
                    relationshipManager);
        }
        Object supervisionCompanyContact = properties.get("supervisionCompanyContact");
        if (supervisionCompanyContact != null) {
            isInChargeOf(taskFactId, supervisionCompanyContact.toString(), dimensionManager,
                    relationshipManager);
        }
    }

    private void isInChargeOf(String taskFactId, String userName,
                              DimensionManager dimensionManager,
                              RelationshipManager relationshipManager) {

        Dimension dimension = new DimensionManager(ids).getDimension
                (DatabaseConstants.DIMENSION_EXTERNAL_USER_WITH_PREFIX,
                        DatabaseConstants.PROPERTY_USER_NAME, userName);

        if (dimension == null) {
            Map<String, Object> props = new HashMap<>();
            props.put(DatabaseConstants.PROPERTY_USER_NAME, userName);
            dimension = dimensionManager.createDimension(DatabaseConstants
                    .DIMENSION_EXTERNAL_USER_WITH_PREFIX, props);
        }

        if (dimension != null) {
            relationshipManager.attachFactToDimension(taskFactId, dimension.getId(),
                    DatabaseConstants.RELATION_IN_CHARGE_OF_WITH_PREFIX, true);
        }
    }

    public Map<String, Object> appendTaskPropertiesToProject(
            Map<String, Object> projectProperties,
            Map<String, Object>[] tasksPropertiesArray) {

        if (tasksPropertiesArray == null || tasksPropertiesArray.length == 0) {
            return projectProperties;
        }

//        String progressType = progressProperties.get(JsonConstants.JSON_PROJECT_TYPE).toString();

        for (Map<String, Object> taskProps : tasksPropertiesArray) {
            appendTaskPropertiesToProject(projectProperties, taskProps);
        }

        return projectProperties;
    }

    public static void appendTaskPropertiesToProject(Map<String, Object> projectProperties,
                                                     Map<String, Object> taskProperties) {

        Set<String> keySet = taskProperties.keySet();
        Iterator<String> it = keySet.iterator();

        String taskName = "";
        String taskDisplayName = "";
        Date startDate = new Date();
        Date endDate = new Date();

        String workerId = "";
        String worker = "";
        String executiveDepartmentId = "";
        String executiveDepartment = "";
        String companyClassification = "";
        while (it.hasNext()) {
            String key = it.next();
            Object value = taskProperties.get(key);
            if (key.equalsIgnoreCase(JsonConstants.JSON_TASK_NAME)) {
                taskName = value.toString();
                //taskName = TaskSampleDataGenerator.taskNameMap.get(taskDisplayName);
            }
            if (key.equalsIgnoreCase(JsonConstants.JSON_TASK_DISPLAY_NAME)) {
                taskDisplayName = value.toString();
                //taskName = TaskSampleDataGenerator.taskNameMap.get(taskDisplayName);
            }
            if (key.equalsIgnoreCase(JsonConstants.JSON_START_DATE)) {
                startDate = (Date) value;
            }
            if (key.equalsIgnoreCase(JsonConstants.JSON_END_DATE)) {
                endDate = (Date) value;
            }

            if (key.equalsIgnoreCase(JsonConstants.JSON_WORKER_ID)) {
                workerId = value.toString();
            }

            if (key.equalsIgnoreCase(JsonConstants.JSON_WORKER)) {
                worker = value.toString();
            }

            if (key.equalsIgnoreCase(JsonConstants.JSON_EXECUTIVE_DEPARTMENT_ID)) {
                executiveDepartmentId = value.toString();
            }

            if (key.equalsIgnoreCase(JsonConstants.JSON_EXECUTIVE_DEPARTMENT)) {
                executiveDepartment = value.toString();
            }

            if (key.equalsIgnoreCase(JsonConstants.JSON_COMPANY_CLASSIFICATION)) {
                companyClassification = value.toString();
            }

            if (!reservedStringPropertyNames().contains(key)) {
                projectProperties.put(key, value);
            }
        }

        if (taskName == null || taskName.equalsIgnoreCase("")) {
            taskName = "taskName";
        }

        projectProperties.put(taskName + "_taskName", taskDisplayName);
        projectProperties.put(taskName + "_startDate", startDate);
        projectProperties.put(taskName + "_endDate", endDate);
        projectProperties.put(taskName + "_workerId", workerId);
        projectProperties.put(taskName + "_worker", worker);
        projectProperties.put(taskName + "_executiveDepartmentId", executiveDepartmentId);
        projectProperties.put(taskName + "_executiveDepartment", executiveDepartment);
        if (companyClassification != null && !companyClassification.trim().isEmpty()) {
            projectProperties.put(taskName + "_companyClassification", companyClassification);
        }

    }

    public static List<String> reservedStringPropertyNames() {
        List<String> list = new ArrayList<>();
        list.add(JsonConstants.JSON_TYPE);
        list.add(JsonConstants.JSON_PROJECT_ID);
        list.add(JsonConstants.JSON_TASK_ID);
        list.add(JsonConstants.JSON_TASK_NAME);
        list.add(JsonConstants.JSON_TASK_DISPLAY_NAME);
        list.add(JsonConstants.JSON_EXECUTIVE_DEPARTMENT);
        list.add(JsonConstants.JSON_EXECUTIVE_DEPARTMENT_ID);
        list.add(JsonConstants.JSON_WORKER);
        list.add(JsonConstants.JSON_WORKER_ID);
        list.add(JsonConstants.JSON_START_DATE);
        list.add(JsonConstants.JSON_END_DATE);

        return list;
    }
}
