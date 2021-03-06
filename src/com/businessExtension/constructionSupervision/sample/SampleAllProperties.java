package com.businessExtension.constructionSupervision.sample;

import com.infoDiscover.infoDiscoverEngine.dataMart.PropertyType;
import com.infoDiscover.solution.common.util.JsonNodeUtil;
import com.infoDiscover.solution.common.util.PropertyTypeUtil;
import com.businessExtension.constructionSupervision.constants.JsonConstants;
import com.businessExtension.constructionSupervision.util.ProjectJsonParser;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun.
 */
public class SampleAllProperties {

    private static Logger logger = LoggerFactory.getLogger(SampleAllProperties.class);

    public static Map<String, PropertyType> maintenance_project_properties = new HashMap<>();
    public static Map<String, PropertyType> new_project_properties = new HashMap<>();

    public static Map<String, PropertyType> getMaintenanceProjectProperties() {
        if (maintenance_project_properties == null || maintenance_project_properties.size() == 0) {
            String projectJsonTemplate = SampleDataSet.FILE_MAINTENANCE_PROJECT;
            getProjectProperties(projectJsonTemplate, maintenance_project_properties);
        }
        return maintenance_project_properties;
    }

    public static Map<String, PropertyType> getNewProjectProperties() {
        if (new_project_properties == null || new_project_properties.size() == 0) {
            String projectJsonTemplate = SampleDataSet.FILE_NEW_PROJECT;
            getProjectProperties(projectJsonTemplate, new_project_properties);
        }
        return new_project_properties;
    }

    public static void getProjectProperties(String projectJsonTemplate,
                                            Map<String, PropertyType> allProperties) {

        logger.info("Start to getProjectProperties with template: {}", projectJsonTemplate);

        JsonNode json = JsonNodeUtil.loadJsonFile(projectJsonTemplate);
        JsonNode progressPropertiesJsonNode = ProjectJsonParser.getProjectNode(json.toString());

        JsonNode progressPropertiesNode = JsonNodeUtil.getPropertiesJsonNode
                (progressPropertiesJsonNode);

        setProperties(progressPropertiesNode, allProperties);

        JsonNode taskNodes = ProjectJsonParser.getTasksNode(json.toString());
        for (JsonNode taskNode : taskNodes) {
            JsonNode taskPropertiesJsonNode = taskNode.get(JsonConstants.JSON_TASK);
            JsonNode propertiesNode = JsonNodeUtil.getPropertiesJsonNode(taskPropertiesJsonNode);

            setProperties(propertiesNode, allProperties);
        }

        logger.info("Exit getProjectProperties");
    }

    public static void setProperties(JsonNode propertiesNode, Map<String, PropertyType>
            allProperties) {
        for (JsonNode propertyNode : propertiesNode) {
            String propertyName = propertyNode.get(JsonConstants.JSON_PROPERTY_NAME).asText();
            String propertyType = propertyNode.get(JsonConstants.JSON_PROPERTY_TYPE).asText();
            logger.info("propertyName: {}, propertyType: {}", propertyName, propertyType);
            PropertyType type = PropertyTypeUtil.getPropertyType(propertyType);
            if (type != null) {
                allProperties.put(propertyName, type);
            }
        }
    }




    public static void main(String[] args) {
        getMaintenanceProjectProperties();
        getNewProjectProperties();

        System.out.println(maintenance_project_properties);
        System.out.println(new_project_properties);
    }
}
