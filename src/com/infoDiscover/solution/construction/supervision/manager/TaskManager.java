package com.infoDiscover.solution.construction.supervision.manager;

import com.infoDiscover.common.dimension.time.dimension.DayDimensionVO;
import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.arch.progress.constants.ProgressConstants;
import com.infoDiscover.solution.arch.progress.manager.ProgressRelationManager;
import com.infoDiscover.solution.arch.progress.manager.UserManager;
import com.infoDiscover.solution.common.dimension.DimensionManager;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.common.fact.FactManager;
import com.infoDiscover.solution.common.util.Constants;
import com.infoDiscover.solution.construction.supervision.constants.DatabaseConstants;
import com.infoDiscover.solution.construction.supervision.constants.JsonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

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

    public void createNewOrUpdateTaskInstance(String progressFactType, Map<String, Object>
            properties) {
        logger.info("Enter method createNewOrUpdateTaskInstance() with ids: {} with " +
                "progressFactType: {} and properties: {}", ids, progressFactType, properties);

        String progressId = properties.get(JsonConstants.JSON_PROJECT_ID).toString();
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
            Fact progressFact = progressManager.getProjectId(ids.getInformationExplorer(),
                    progressFactType, null, progressId);
            relationManager.attachTaskToProgress(progressFact, taskFact,
                    DatabaseConstants.RELATION_PROGRESS_HASTASK_WITH_PREFIX);

            // link worker to task
            String userId = properties.get(JsonConstants.JSON_WORKER_ID).toString();
            UserManager userManager = new UserManager();
            Dimension userDimension = userManager.getUserById(ids.getInformationExplorer(),
                    userId, DatabaseConstants.DIMENSION_USER_WITH_PREFIX);
            relationManager.attachUserToTask(taskFact, userDimension, DatabaseConstants
                    .RELATION_TASK_EXECUTE_BY_USER_WITH_PREFIX);

            // link executive department to task
            String departmentId = properties.get(JsonConstants.JSON_EXECUTIVE_DEPARTMENT_ID)
                    .toString();
            Dimension departmentDimension = new DimensionManager(ids).getDimension
                    (DatabaseConstants.DIMENSION_EXECUTIVE_DEPARTMENT_WITH_PREFIX,
                            Constants.DIMENSION_ID, departmentId);
            if (departmentDimension != null) {
                relationManager.attachRoleToTask(taskFact, departmentDimension,
                        DatabaseConstants.RELATION_TASK_EXECUTE_BY_DEPARTMENT_WITH_PREFIX);
            }

            // link startDate to task
            DayDimensionVO dayDimension = DayDimensionManager.getDayDimensionVO(DatabaseConstants
                    .SOLUTION_PREFIX, (Date)
                    properties.get(JsonConstants.JSON_START_DATE));
            relationManager.attachTimeToTask(taskFact, dayDimension, ProgressConstants
                    .RELATIONTYPE_STARTAT_WITHPREFIX);

            // link endDate to task
            if (properties.get(JsonConstants.JSON_END_DATE) != null) {
                dayDimension = DayDimensionManager.getDayDimensionVO(DatabaseConstants
                                .SOLUTION_PREFIX,
                        (Date) properties.get(JsonConstants.JSON_END_DATE));
                relationManager.attachTimeToTask(taskFact, dayDimension, ProgressConstants
                        .RELATIONTYPE_ENDAT_WITHPREFIX);
            }

            // link to company classification
            Object companyClassification = properties.get(JsonConstants
                    .JSON_COMPANY_CLASSIFICATION);
            if (companyClassification != null) {
                Dimension dimension = new DimensionManager(ids).getDimension
                        (DatabaseConstants.DIMENSION_COMPANY_CLASSIFICATION_WITH_PREFIX,
                                Constants.DIMENSION_NAME, companyClassification.toString());
                if (dimension != null) {
                    ids.attachFactToDimension(taskFact.getId(), dimension.getId(),
                            DatabaseConstants
                                    .RELATION_COMPANY_CLASSIFICATION_WITH_PREFIX);
                }
            }

            // execute by company
            taskExecutedByCompany(taskFact.getId(), properties);

            // is in charge of
            taskIsInChargeOf(taskFact.getId(), properties);

        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.error(e.getMessage());
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.error(e.getMessage());
        }

        logger.info("Exit createNewOrUpdateTaskInstance()...");
    }


    private void executeByCompany(String progressFactId, String
            companyName) {

        Dimension dimension = new DimensionManager(ids).getDimension
                (DatabaseConstants.DIMENSION_COMPANY_WITH_PREFIX,
                        "companyName", companyName);
        if (dimension != null) {
            try {
                ids.attachFactToDimension(progressFactId, dimension.getId(),
                        DatabaseConstants.RELATION_EXECUTE_BY_COMPANY_WITH_PREFIX);
            } catch (InfoDiscoveryEngineRuntimeException e) {
                logger.error("Failed to add relationship executeByCompany: {} with error: ",
                        companyName, e
                                .getMessage());
            }
        }

    }


    private void taskExecutedByCompany(String taskFactId,
                                       Map<String, Object> properties) {
        Object biddingAgency = properties.get("biddingAgency");
        if (biddingAgency != null) {
            executeByCompany(taskFactId, biddingAgency.toString());
        }

        Object projectPlanMakingCompany = properties.get("projectPlanMakingCompany");
        if (projectPlanMakingCompany != null) {
            executeByCompany(taskFactId, projectPlanMakingCompany.toString());
        }

        Object surveyCompany = properties.get("surveyCompany");
        if (surveyCompany != null) {
            executeByCompany(taskFactId, surveyCompany.toString());
        }

        Object designCompany = properties.get("designCompany");
        if (designCompany != null) {
            executeByCompany(taskFactId, designCompany.toString());
        }

        Object costConsultationCompany = properties.get("costConsultationCompany");
        if (costConsultationCompany != null) {
            executeByCompany(taskFactId, costConsultationCompany.toString());
        }

        Object constructionCompany = properties.get("constructionCompany");
        if (constructionCompany != null) {
            executeByCompany(taskFactId, constructionCompany.toString());
        }

        Object supervisionCompany = properties.get("supervisionCompany");
        if (supervisionCompany != null) {
            executeByCompany(taskFactId, supervisionCompany.toString());
        }
    }

    private void taskIsInChargeOf(String taskFactId, Map<String,
            Object>
            properties) {
        Object biddingAgencyLeader = properties.get("biddingAgencyLeader");
        if (biddingAgencyLeader != null) {
            isInChargeOf(taskFactId, biddingAgencyLeader.toString());
        }

        Object consultationCompanyLeader = properties.get("consultationCompanyLeader");
        if (consultationCompanyLeader != null) {
            isInChargeOf(taskFactId, consultationCompanyLeader.toString());
        }

        Object surveyCompanyLeader = properties.get("surveyCompanyLeader");
        if (surveyCompanyLeader != null) {
            isInChargeOf(taskFactId, surveyCompanyLeader.toString());
        }
        Object designCompanyLeader = properties.get("designCompanyLeader");
        if (designCompanyLeader != null) {
            isInChargeOf(taskFactId, designCompanyLeader.toString());
        }
        Object costConsultationCompanyLeader = properties.get("costConsultationCompanyLeader");
        if (costConsultationCompanyLeader != null) {
            isInChargeOf(taskFactId, costConsultationCompanyLeader.toString());
        }
        Object constructionCompanyLeader = properties.get("constructionCompanyLeader");
        if (constructionCompanyLeader != null) {
            isInChargeOf(taskFactId, constructionCompanyLeader.toString());
        }
        Object supervisionCompanyContact = properties.get("supervisionCompanyContact");
        if (supervisionCompanyContact != null) {
            isInChargeOf(taskFactId, supervisionCompanyContact.toString());
        }
    }

    private void isInChargeOf(String progressFactId, String
            userName) {

        Dimension dimension = new DimensionManager(ids).getDimension
                (DatabaseConstants.DIMENSION_EXTERNAL_USER_WITH_PREFIX,
                        "userName", userName);
        if (dimension != null) {
            try {
                ids.attachFactToDimension(progressFactId, dimension.getId(),
                        DatabaseConstants.RELATION_IN_CHARGE_OF_WITH_PREFIX);
            } catch (InfoDiscoveryEngineRuntimeException e) {
                logger.error("Failed to add relationship isInChargeOf: {} with error: ",
                        userName, e.getMessage());
            }
        }
    }
}
