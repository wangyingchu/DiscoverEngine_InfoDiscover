package com.infoDiscover.solution.construction.supervision.manager;

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
public class ProjectManager {
    private final static Logger logger = LoggerFactory.getLogger(ProjectManager.class);

    private InfoDiscoverSpace ids;

    public ProjectManager(InfoDiscoverSpace ids) {
        this.ids = ids;
    }

    public Fact getProjectId(InformationExplorer ie, String factType, String key, String
            projectId) {

        ExploreParameters ep = new ExploreParameters();
        ep.setType(factType);
        if (key == null) {
            key = JsonConstants.JSON_PROJECT_ID;
        }
        ep.setDefaultFilteringItem(new EqualFilteringItem(key, projectId));

        return QueryExecutor.executeFactQuery(ie, ep);
    }

    public void createNewOrUpdateProjectInstance(String factType,
                                                 Map<String, Object> properties) {

        logger.info("Enter method createNewOrUpdateProjectInstance() " +
                "with ids: {} " +
                "and factType: {} " +
                "and properties: {}", ids, factType, properties);

        if (properties == null || properties.keySet().size() == 0) {
            logger.error("Project data is null");
            return;
        }

        String projectId = properties.get(JsonConstants.JSON_PROJECT_ID).toString();
        ProjectManager projectManager = new ProjectManager(ids);
        try {

            // remove type from properties
//            properties.remove(JsonConstants.JSON_TYPE);

            // add properties types to progress fact type
//            if(factType.equalsIgnoreCase(SampleDataSet.FACTTYPE_MAINTENANCE_PROJECT)) {
//                updateFactTypeProperties(ids,factType,properties,SampleAllProperties
// .getMaintenanceProjectProperties());
//            } else {
//                updateFactTypeProperties(ids, factType,properties,SampleAllProperties
// .getNewProjectProperties());
//            }

            // create or update fact
            Fact projectFact = projectManager.getProjectId(ids.getInformationExplorer(),
                    factType, null, projectId);
            FactManager factManager = new FactManager(ids);
            if (projectFact == null) {
                projectFact = factManager.createFact(factType, properties);
            } else {
                projectFact = factManager.updateFact(projectFact, properties);
            }

            DimensionManager dimensionManager = new DimensionManager(ids);
            RelationshipManager relationshipManager = new RelationshipManager(ids);

            // link starter to progress
            linkStarterToProject(projectFact.getId(), properties, dimensionManager,
                    relationshipManager);

            // link startDate to progress
            linkDateToProject(projectId, factType, properties);

            // link dimensions
            linkDimensionsToProject(projectFact.getId(), properties);

        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.error(e.getMessage());
        }

        logger.info("Exit createNewOrUpdateProjectInstance()...");
    }


    public void linkStarterToProject(String projectFactId,
                                     Map<String, Object> properties,
                                     DimensionManager dimensionManager,
                                     RelationshipManager relationshipManager) {
        // link starter to progress
        Object starterId = properties.get(JsonConstants.JSON_PROJECT_STARTER_ID);
        if (starterId != null && !starterId.toString().isEmpty()) {
            Dimension starterDimension = dimensionManager.getDimension
                    (DatabaseConstants.DIMENSION_USER_WITH_PREFIX,
                            DatabaseConstants.DIMENSION_USER_ID, starterId.toString());

            // if starterDimension is null, create it
            if (starterDimension == null) {
                Map<String, Object> props = new HashedMap();
                props.put(DatabaseConstants.DIMENSION_USER_ID, starterId);
                Object userName = properties.get(JsonConstants.JSON_PROJECT_STARTER);
                if (userName != null) {
                    props.put(DatabaseConstants.DIMENSION_USER_NAME, userName.toString());
                }

                starterDimension = dimensionManager.createDimension(DatabaseConstants
                        .DIMENSION_USER_WITH_PREFIX, props);
            }

            if (starterDimension != null) {
                relationshipManager.attachFactToDimension(projectFactId, starterDimension.getId(),
                        DatabaseConstants.RELATION_START_BY_WITH_PREFIX, true);
            }
        }
    }

    private void linkDateToProject(String progressId, String factType,
                                   Map<String, Object> properties) {

        ProgressRelationManager relationManager = new ProgressRelationManager(ids);

        if (properties.get(JsonConstants.JSON_START_DATE) != null) {
            DayDimensionVO dayDimensionVO = DayDimensionManager.getDayDimensionVO
                    (DatabaseConstants
                            .SOLUTION_PREFIX, (Date) properties.get(JsonConstants.JSON_START_DATE));
            relationManager.attachTimeToProgress(progressId, factType, dayDimensionVO,
                    ProgressConstants.RELATIONTYPE_STARTAT_WITHPREFIX);
        }

        // link endDate to progress
        if (properties.get(JsonConstants.JSON_END_DATE) != null) {
            DayDimensionVO dayDimensionVO = DayDimensionManager.getDayDimensionVO(
                    DatabaseConstants.SOLUTION_PREFIX,
                    (Date) properties.get(JsonConstants.JSON_END_DATE));
            relationManager.attachTimeToProgress(progressId, factType,
                    dayDimensionVO, ProgressConstants.RELATIONTYPE_ENDAT_WITHPREFIX);
        }
    }

    private void linkDimensionsToProject(String projectFactId, Map<String,
            Object> properties) {

        RelationshipManager relationshipManager = new RelationshipManager(ids);
        String dimensionKey = Constants.DIMENSION_NAME;

        Object constructionType = properties.get(JsonConstants.JSON_CONSTRUCTION_TYPE);
        if (constructionType != null) {
            relationshipManager.attachFactToDimension(projectFactId,
                    DatabaseConstants.DIMENSION_CONSTRUCTION_TYPE_WITH_PREFIX,
                    dimensionKey, constructionType.toString(),
                    DatabaseConstants.RELATION_CONSTRUCTION_TYPE_WITH_PREFIX,
                    true);
        }

        Object assignModel = properties.get(JsonConstants.JSON_ASSIGN_MODEL);
        if (assignModel != null) {
            relationshipManager.attachFactToDimension(projectFactId,
                    DatabaseConstants.DIMENSION_ASSIGN_MODEL_WITH_PREFIX,
                    dimensionKey, assignModel.toString(),
                    DatabaseConstants.RELATION_ASSIGN_MODEL_WITH_PREFIX,
                    true);
        }

        Object issueClassification = properties.get(JsonConstants
                .JSON_ISSUE_CLASSIFICATION);
        if (issueClassification != null) {
            relationshipManager.attachFactToDimension(projectFactId,
                    DatabaseConstants.DIMENSION_ISSUE_CLASSIFICATION_WITH_PREFIX,
                    dimensionKey, issueClassification.toString(),
                    DatabaseConstants.RELATION_ISSUE_CLASSIFICATION_WITH_PREFIX,
                    true);
        }

        Object landProperty = properties.get(JsonConstants.JSON_LAND_PROPERTY);
        if (landProperty != null) {
            relationshipManager.attachFactToDimension(projectFactId,
                    DatabaseConstants.DIMENSION_LAND_PROPERTY_WITH_PREFIX,
                    dimensionKey, landProperty.toString(),
                    DatabaseConstants.RELATION_LAND_PROPERTY_WITH_PREFIX,
                    true);
        }

        Object projectClassification = properties.get(JsonConstants
                .JSON_PROJECT_CLASSIFICATION);
        if (projectClassification != null) {
            relationshipManager.attachFactToDimension(projectFactId,
                    DatabaseConstants.DIMENSION_PROJECT_CLASSIFICATION_WITH_PREFIX,
                    dimensionKey, projectClassification.toString(),
                    DatabaseConstants.RELATION_PROJECT_CLASSIFICATION_WITH_PREFIX,
                    true);
        }

        Object projectSiteClassification = properties.get(JsonConstants
                .JSON_PROJECT_SITE_CLASSIFICATION);
        if (projectSiteClassification != null) {
            relationshipManager.attachFactToDimension(projectFactId,
                    DatabaseConstants.DIMENSION_PROJECT_SITE_CLASSIFICATION_WITH_PREFIX,
                    dimensionKey, projectSiteClassification.toString(),
                    DatabaseConstants.RELATION_PROJECT_SITE_CLASSIFICATION_WITH_PREFIX,
                    true);
        }

        Object projectScope = properties.get(JsonConstants.JSON_PROJECT_SCOPE);
        if (projectScope != null) {
            relationshipManager.attachFactToDimension(projectFactId,
                    DatabaseConstants.DIMENSION_PROJECT_SCOPE_WITH_PREFIX,
                    dimensionKey, projectScope.toString(),
                    DatabaseConstants.RELATION_PROJECT_SCOPE_WITH_PREFIX,
                    true);
        }

        // link project to projectConstructionClassification
        Object projectConstructionClassification = properties.get(JsonConstants
                .JSON_PROJECT_CONSTRUCTION_CLASSIFICATION);
        if (projectConstructionClassification != null) {
            relationshipManager.attachFactToDimension(projectFactId,
                    DatabaseConstants.DIMENSION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX,
                    dimensionKey, projectConstructionClassification.toString(),
                    DatabaseConstants.RELATION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX,
                    true);
        }

        Object projectAddress = properties.get(JsonConstants.JSON_PROJECT_ADDRESS);
        if (projectAddress != null) {
            relationshipManager.attachFactToDimension(projectFactId,
                    DatabaseConstants.DIMENSION_PROJECT_ADDRESS_WITH_PREFIX,
                    dimensionKey, projectAddress.toString(),
                    DatabaseConstants.RELATION_PROJECT_ADDRESS_AT_WITH_PREFIX,
                    true);
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
            Set<String> keySet = taskProps.keySet();
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
                Object value = taskProps.get(key);
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

        return projectProperties;
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
