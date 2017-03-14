package com.infoDiscover.solution.arch.demo;

import com.infoDiscover.common.util.JsonUtil2;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by sun.
 */
public class DemoPropertyMapping {

    private final static Logger logger = LogManager.getLogger(DemoPropertyMapping.class);

    public static String propertyFactMappingFile = "PropertyFactMapping.json";
    public static String propertyRelationshipMappingFile = "PropertyRelationMapping.json";

    public static String config_home = System.getenv("INFODISCOVER_HOME") + "/conf";

    private static HashMap<String, Map<String, String>> propertyFactMappingMap = new HashMap<>();
    private static HashMap<String, Map<String, Object>> propertyRelationshipMappingMap = new
            HashMap<>();

    public final static String KEY_PROPERTY_FACT_MAPPING = "propertyFactMapping";
    public final static String KEY_PROPERTY_RELATIONSHIP_MAPPING = "propertyRelationshipMapping";


    public static HashMap<String, Map<String, String>> loadPropertyFactMapping() {
        logger.debug("Enter method loadPropertyFactMapping()");
        if(propertyFactMappingMap.containsKey(KEY_PROPERTY_FACT_MAPPING)) {
            logger.debug("hit cache in propertyFactMappingMap");
            logger.debug("Exit method loadPropertyFactMapping()...");
            return propertyFactMappingMap;
        }

        HashMap<String, String> properties = new HashMap<>();
        String mappingFile = config_home + "/" + propertyFactMappingFile;

        JsonNode dataNode = getDataNode(mappingFile);
        if (dataNode != null) {
            JsonNode prefix = dataNode.get(JsonConstants.JSON_PREFIX);
            if (prefix != null) {
                properties.put(prefix.get(JsonConstants.JSON_PROPERTY_NAME).asText(), prefix.get
                        (JsonConstants.JSON_PROPERTY_VALUE).asText());
            }

            JsonNode types = dataNode.get("types");
            for (JsonNode type : types) {
                String propertyName = type.get(JsonConstants.JSON_PROPERTY_NAME).asText();
                JsonNode propertyValue = type.get(JsonConstants.JSON_PROPERTY_VALUE);
                String factTypeName = type.get("factTypeName").asText();
                if (propertyName.equalsIgnoreCase("type")) {
                    properties.put(propertyValue.asText(), factTypeName);
                } else {
                    properties.put(propertyName, factTypeName);
                }
            }
            propertyFactMappingMap.put(KEY_PROPERTY_FACT_MAPPING, properties);

        }

        logger.debug("Exit method loadPropertyFactMapping()...");
        return propertyFactMappingMap;

    }

    public static HashMap<String, Map<String, Object>> loadPropertyRelationshipMapping() {
        logger.debug("Enter method loadPropertyRelationshipMapping()");
        if(propertyRelationshipMappingMap.containsKey(KEY_PROPERTY_RELATIONSHIP_MAPPING)) {
            logger.debug("hit cache in propertyFactMappingMap");
            logger.debug("Exit method loadPropertyRelationshipMapping()...");
            return propertyRelationshipMappingMap;
        }

        HashMap<String, Object> properties = new HashMap<>();
        String mappingFile = config_home + "/" + propertyRelationshipMappingFile;

        JsonNode dataNode = getDataNode(mappingFile);
        if (dataNode != null) {
            JsonNode prefix = dataNode.get(JsonConstants.JSON_PREFIX);
            if (prefix != null) {
                properties.put(prefix.get(JsonConstants.JSON_PROPERTY_NAME).asText(), prefix.get
                        (JsonConstants.JSON_RELATION_TYPE_NAME).asText());
            }

            JsonNode timesNode = dataNode.get(JsonConstants.JSON_TIMES);
            for (JsonNode time : timesNode) {
                String propertyName = time.get(JsonConstants.JSON_PROPERTY_NAME).asText();
                String propertyValue = time.get(JsonConstants.JSON_RELATION_TYPE_NAME).asText();
                String classType = InfoDiscoverEngineConstant.DIMENSION_ROOTCLASSNAME;

                RelationMapping mapping = new RelationMapping(propertyName,propertyValue,
                        classType, DemoFactEnum.Time);
                properties.put(propertyName, mapping);
            }

            JsonNode participantsNode = dataNode.get(JsonConstants.JSON_PARTICIPANTS);
            for(JsonNode user: participantsNode) {
                String propertyName = user.get(JsonConstants.JSON_PROPERTY_NAME).asText();
                String propertyValue = user.get(JsonConstants.JSON_RELATION_TYPE_NAME).asText();
                String classType = InfoDiscoverEngineConstant.FACT_ROOTCLASSNAME;

                RelationMapping mapping = new RelationMapping(propertyName,propertyValue,
                        classType, DemoFactEnum.User);
                properties.put(propertyName, mapping);
            }

            JsonNode rolesNode = dataNode.get(JsonConstants.JSON_ROLES);
            for(JsonNode role: rolesNode) {
                String propertyName = role.get(JsonConstants.JSON_PROPERTY_NAME).asText();
                String propertyValue = role.get(JsonConstants.JSON_RELATION_TYPE_NAME).asText();
                String classType = InfoDiscoverEngineConstant.FACT_ROOTCLASSNAME;

                RelationMapping mapping = new RelationMapping(propertyName,propertyValue,
                        classType, DemoFactEnum.Role);
                properties.put(propertyName, mapping);
            }

            propertyRelationshipMappingMap.put(KEY_PROPERTY_RELATIONSHIP_MAPPING, properties);
        }

        return  propertyRelationshipMappingMap;
    }

    private static JsonNode getDataNode(String mappingFile) {
        JsonNode jsonNode = JsonUtil2.loadJsonFile(mappingFile);
        return jsonNode == null ? null : jsonNode.get(JsonConstants.JSON_DATA);
    }


    public static void main(String[] args) {
        HashMap<String, Map<String, String>> factMapping = new HashMap<>();
        factMapping = loadPropertyFactMapping();
        logger.debug("factMapping: " + factMapping);


        HashMap<String, Map<String, Object>> relationshipMapping = new HashMap<>();
        relationshipMapping = loadPropertyRelationshipMapping();
        logger.debug("relationshipMapping: " + relationshipMapping);

        Map<String, Object> map = relationshipMapping.get(KEY_PROPERTY_RELATIONSHIP_MAPPING);
        String prefix = map.get("prefix").toString();
        RelationMapping relation = (RelationMapping) map.get("createdBy");
        logger.debug("prefix: " + prefix);
        logger.debug("relation's name: " + relation.getPropertyName());
        logger.debug("relation's value: " + relation.getPropertyValue());
        logger.debug("relation's class type: " + relation.getClassType());
        logger.debug("relation's type: " + relation.getType());
    }
}
