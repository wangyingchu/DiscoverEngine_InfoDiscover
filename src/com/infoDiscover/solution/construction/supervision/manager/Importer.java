package com.infoDiscover.solution.construction.supervision.manager;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.common.fact.FactManager;
import com.infoDiscover.solution.common.util.JsonNodeUtil;
import com.infoDiscover.solution.construction.supervision.constants.DatabaseConstants;
import com.infoDiscover.solution.construction.supervision.constants.JsonConstants;
import com.infoDiscover.solution.construction.supervision.sample.SampleDataSet;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun.
 */
public class Importer {

    private final static Logger logger = LoggerFactory.getLogger(Importer.class);

    public void importProject(InfoDiscoverSpace ids, String projectJson) throws Exception {
        logger.info("Enter importProject() with projectJson: {}", projectJson);

        Map<String, Object> projectProperties = getPropertiesMap(projectJson);

        Object projectType = projectProperties.get(JsonConstants.JSON_PROJECT_TYPE);
        if (projectType == null) {
            throw new Exception("Project type is empty");
        }

        String projectFactType = getProjectFactType(projectType.toString());
        new ProjectManager(ids).createNewOrUpdateProjectInstance(projectFactType,
                projectProperties);

        logger.info("Exit importProject()...");
    }

    public void importTask(InfoDiscoverSpace ids, String taskJson) throws Exception {
        logger.info("Enter importTask() with taskJson: {}", taskJson);

        Map<String, Object> taskProperties = getPropertiesMap(taskJson);
        Object projectFactTypeObj = taskProperties.get(JsonConstants.JSON_PROJECT_TYPE);
        if (projectFactTypeObj == null) {
            throw new Exception("No projectType found, it is a required value.");
        }

        String projectFactType = getProjectFactType(projectFactTypeObj.toString());

        Object projectId = taskProperties.get(JsonConstants.JSON_PROJECT_ID);
        if (projectId == null) {
            throw new Exception("No projectId found, it is a required value.");
        }

        Fact projectFact = new ProjectManager(ids).getProjectId(ids.getInformationExplorer(),
                projectFactType, null, projectId.toString());
        if (projectFact == null) {
            logger.error("No related projectId: {} existed", projectId.toString());
            throw new Exception("No related project with id: " + projectId.toString() + "( " +
                    "exists, please create the project firstly.");
        }

        TaskManager taskManager = new TaskManager(ids);
        taskManager.createNewOrUpdateTaskInstance(projectFactType, taskProperties);

        // append task properties to project
        Map<String, Object> projectProperties = new HashMap<>();
        projectProperties.put(JsonConstants.JSON_PROJECT_ID, taskProperties.get(JsonConstants
                .JSON_PROJECT_ID).toString());
        taskManager.appendTaskPropertiesToProject(projectProperties, taskProperties);


        new ProjectManager(ids).createNewOrUpdateProjectInstance(projectFactType,
                projectProperties);

        logger.info("Exit importTask()...");
    }

    public void updateProjectStatus(InfoDiscoverSpace ids, String projectId, String projectType,
                                    String status) throws  Exception {
        logger.info("Enter updateProjectStatus() with projectId: {}, and statud: {}", projectId,
                status);
        Fact projectFact = new ProjectManager(ids).getProjectId(ids.getInformationExplorer(),
                getProjectFactType(projectType), null, projectId.toString());
        if (projectFact == null) {
            logger.error("No related projectId: {} existed", projectId.toString());
            throw new Exception("No related project with id: " + projectId.toString() + "( " +
                    "exists, please specify a correct id.");
        }

        Map<String, Object> props = new HashMap<>();
        props.put(JsonConstants.JSON_STATUS, status);

        new FactManager(ids).updateFact(projectFact, props);

        logger.info("Exit updateProjectStatus()...");
    }

    private static Map<String, Object> getPropertiesMap(String json) throws Exception {
        JsonNode propertiesNode = getPropertiesJsnNode(json);
        return JsonNodeUtil.convertToPropertyNameValueMap
                (propertiesNode);
    }

    private static JsonNode getPropertiesJsnNode(String json) throws Exception {
        JsonNode dataNode = JsonNodeUtil.getDataNode(json);
        if (dataNode == null) {
            throw new Exception("Project content is empty or format is invalid, project content " +
                    "is: " + json);
        }

        JsonNode propertiesNode = JsonNodeUtil.getPropertiesJsonNode(dataNode);
        if (propertiesNode == null) {
            throw new Exception("Project's properties is empty: " + propertiesNode);
        }

        return propertiesNode;
    }


    private void importProjectAndTasks(InfoDiscoverSpace ids, String projectJson) throws Exception {
        logger.info("Enter importProjectAndTasks() with projectJson: {}", projectJson);

        JsonNode dataNode = JsonNodeUtil.getDataNode(projectJson);
        if (dataNode == null) {
            throw new Exception("Project content is empty or format is invalid, project content " +
                    "is: " + projectJson);
        }

        JsonNode projectNode = JsonNodeUtil.getJsonNode(JsonConstants.JSON_PROJECT, dataNode);
        JsonNode tasksNode = JsonNodeUtil.getJsonNode(JsonConstants.JSON_TASKS, dataNode);
        JsonNode taskNode = JsonNodeUtil.getJsonNode(JsonConstants.JSON_TASK, dataNode);

        if (projectNode == null && tasksNode == null && taskNode == null) {
            throw new Exception("Project content is empty or format is invalid, project data " +
                    "is: " + projectJson);
        }

        if (projectNode != null) {
            createProjectWithTasks(ids, projectNode, tasksNode);
        } else {
            if (taskNode == null) {
                throw new Exception("Task content is empty or format is invalid, task data is: "
                        + taskNode);
            }

            //TODO: create task data

        }

        logger.info("Exit importProjectAndTasks()...");
    }

    //TODO:
    private void createProjectWithTasks(InfoDiscoverSpace ids, JsonNode projectNode, JsonNode
            tasksNode) throws
            Exception {

        JsonNode projectPropertiesNode = JsonNodeUtil.getPropertiesJsonNode(projectNode);

        Map<String, Object> projectProperties = JsonNodeUtil.convertToPropertyNameValueMap
                (projectPropertiesNode);

        Object projectType = projectProperties.get(JsonConstants.JSON_PROJECT_TYPE);
        if (projectType == null) {
            throw new Exception("Project type is empty");
        }

        Map<String, Object>[] tasksPropertiesArray = null;

        TaskManager taskManager = new TaskManager(ids);
        if (tasksNode != null) {
            tasksPropertiesArray = new HashMap[tasksNode.size()];
            int i = 0;
            for (JsonNode node : tasksNode) {
                JsonNode taskPropertiesNode = JsonNodeUtil.getPropertiesJsonNode(node);
                Map<String, Object> taskProperties = JsonNodeUtil
                        .convertToPropertyNameValueMap(taskPropertiesNode);
                tasksPropertiesArray[i] = taskProperties;
                i++;
            }

            // append tasks properties to project properties
            taskManager.appendTaskPropertiesToProject(projectProperties, tasksPropertiesArray);
        }

        String projectFactType = getProjectFactType(projectType.toString());
        new ProjectManager(ids).createNewOrUpdateProjectInstance(projectFactType,
                projectProperties);

        // batch create tasks
        taskManager.batchCreateNewOrUpdateTaskInstances(projectFactType,
                tasksPropertiesArray);

    }


    private String getProjectFactType(String projectType) throws Exception {
        String projectFactType;
        if (projectType.equals(SampleDataSet.PROJECTTYPE_MAINTENANCE)) {
            projectFactType = SampleDataSet.FACTTYPE_MAINTENANCE_PROJECT;
        } else if (projectType.equals(SampleDataSet.PROJECTTYPE_NEW)) {
            projectFactType = SampleDataSet.FACTTYPE_CONSTRUCTION_PROJECT;
        } else if (projectType.equals(SampleDataSet.PROJECTTYPE_EXTENSION)) {
            projectFactType = SampleDataSet.FACTTYPE_CONSTRUCTION_PROJECT;
        } else if (projectType.equals(SampleDataSet.PROJECTTYPE_REBUILD)) {
            projectFactType = SampleDataSet.FACTTYPE_CONSTRUCTION_PROJECT;
        } else {
            throw new Exception("Project Type must be one of [" +
                    SampleDataSet.FACTTYPE_MAINTENANCE_PROJECT + ", "
                    + SampleDataSet.FACTTYPE_CONSTRUCTION_PROJECT);
        }

        return projectFactType;
    }

    public static void main(String[] args) {
        Importer importer = new Importer();
        InfoDiscoverSpace ids = DiscoverEngineComponentFactory.connectInfoDiscoverSpace
                (DatabaseConstants.DATABASE_SPACE);

        // create a project
        String projectFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com" +
                "/infoDiscover/solution/construction/supervision/manager/testdata/Project.json";
        String projectJson = FileUtil.getFileContent(projectFile);


        try {
            importer.importProject(ids, projectJson);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // create a project
        String taskFile = "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com" +
                "/infoDiscover/solution/construction/supervision/manager/testdata/Task.json";
        String taskJson = FileUtil.getFileContent(taskFile);


        try {
            importer.importTask(ids, taskJson);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // update the status
        try {
            importer.updateProjectStatus(ids,"projectId_1", "新建工程", "完成");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ids.closeSpace();

    }
}