package com.infoDiscover.solution.construction.supervision.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.common.util.JsonUtil;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by sun.
 */
public class JsonTemplateGenerator {

    private final static Logger logger = LoggerFactory.getLogger(JsonTemplateGenerator.class);


    private static String taskRequiredPropertiesFile =
            "/Users/sun/Desktop/珠海/TaskRequiredProperties" +
                    ".txt";
    private static String userRequiredPropertiesFile =
            "/Users/sun/Desktop/珠海/userRequiredProperties" +
                    ".txt";

    private static String departmentRequiredPropertiesFile =
            "/Users/sun/Desktop/珠海/departmentRequiredProperties" +
                    ".txt";

    private static String LINE_SPLITTER = ",";

    public static void main(String[] args) {

        generateMaintenanceProjectJsonTemplate();
        generateNewProjectJsonTemplate();
        generateExtensionProjectJsonTemplate();
        generateRebuildProjectJsonTemplate();

//        generateUserJsonTemplate("/Users/sun/Desktop/珠海/userJsonTemplate.json");
    }

    public static void generateMaintenanceProjectJsonTemplate() {
        String rootDir = "/Users/sun/Desktop/珠海";
        String maintenanceProjectRootDir = rootDir + "/maintain_project";
        String maintenanceProjectPropertiesFileDir = maintenanceProjectRootDir + "/source";
        String progressOfMaintenanceProjectTemplateJsonFile = maintenanceProjectRootDir +
                "/maintenanceProgress.json";

        generateProgressAndTasksJsonTemplate(maintenanceProjectPropertiesFileDir, "Task", "" +
                        ".txt", true, progressOfMaintenanceProjectTemplateJsonFile,
                maintenanceProjectRootDir + "/allMaintenanceProject.json");
    }

    public static void generateNewProjectJsonTemplate() {
        String rootDir = "/Users/sun/Desktop/珠海";
        String maintenanceProjectRootDir = rootDir + "/new_project";
        String maintenanceProjectPropertiesFileDir = maintenanceProjectRootDir + "/source";
        String progressOfMaintenanceProjectTemplateJsonFile = maintenanceProjectRootDir +
                "/newProgress.json";

        generateProgressAndTasksJsonTemplate(maintenanceProjectPropertiesFileDir, "task", "" +
                        ".txt", true, progressOfMaintenanceProjectTemplateJsonFile,
                maintenanceProjectRootDir + "/allNewProject.json");
    }

    public static void generateExtensionProjectJsonTemplate() {
        String rootDir = "/Users/sun/Desktop/珠海";
        String maintenanceProjectRootDir = rootDir + "/new_project";
        String maintenanceProjectPropertiesFileDir = maintenanceProjectRootDir + "/source";
        String progressOfMaintenanceProjectTemplateJsonFile = maintenanceProjectRootDir +
                "/extensionProgress.json";

        generateProgressAndTasksJsonTemplate(maintenanceProjectPropertiesFileDir, "task", "" +
                        ".txt", true, progressOfMaintenanceProjectTemplateJsonFile,
                maintenanceProjectRootDir + "/allExtensionProgress.json");
    }

    public static void generateRebuildProjectJsonTemplate() {
        String rootDir = "/Users/sun/Desktop/珠海";
        String maintenanceProjectRootDir = rootDir + "/new_project";
        String maintenanceProjectPropertiesFileDir = maintenanceProjectRootDir + "/source";
        String progressOfMaintenanceProjectTemplateJsonFile = maintenanceProjectRootDir +
                "/rebuildProgress.json";

        generateProgressAndTasksJsonTemplate(maintenanceProjectPropertiesFileDir, "task", "" +
                        ".txt", true, progressOfMaintenanceProjectTemplateJsonFile,
                maintenanceProjectRootDir + "/allRebuildProgress.json");
    }

    public static void generateProgressAndTasksJsonTemplate(String rootPath,
                                                            String filterFilePrefix,
                                                            String filterFileSuffix,
                                                            boolean
                                                                    setFirstValueAsDefaultStringValue,
                                                            String progressJsonTemplateFile,
                                                            String targetJsonFile) {

        String jsonRootPath = rootPath + "/json";
        if (setFirstValueAsDefaultStringValue) {
            jsonRootPath += "/withDefaultValue";
        } else {
            jsonRootPath += "/withoutDefaultValue";
        }

        // create tasks json template from properties files
        generateTaskJsonTemplateFromPropertyFile(rootPath, filterFilePrefix, filterFileSuffix,
                true);

        // create progressAndTasks json template
        generateProgressAndTasksJsonTemplate(jsonRootPath, progressJsonTemplateFile,
                filterFilePrefix,
                targetJsonFile);
    }

    public static void generateTaskJsonTemplateFromPropertyFile(
            String sourcePath,
            String filePrefix,
            String fileSuffix,
            boolean setFirstValueAsDefaultStringValue) {

        String targetPath = sourcePath + "/json/withoutDefaultValue";
        if (setFirstValueAsDefaultStringValue) {
            targetPath = sourcePath + "/json/withDefaultValue";
        }

        List<String> fileList = FileUtil.getFileList(sourcePath, filePrefix, false);
        for (String file : fileList) {
            String sourceFile = sourcePath + "/" + file + normalizeFileExtension(fileSuffix);
            String targetFile = targetPath + "/" + file + ".json";
            generateTaskJsonTemplate(sourceFile, targetFile, setFirstValueAsDefaultStringValue);
        }
    }

    public static String normalizeFileExtension(String extension) {
        return extension.startsWith(".") ? extension : "." + extension;
    }


    public static void generateProgressAndTasksJsonTemplate(
            String jsonRootPath,
            String progressTemplateJsonFile,
            String filePrefix,
            String targetJsonFile) {

        JsonObject dataJsonObject = new JsonObject();
        JsonObject taskJsonObject = new JsonObject();
        JsonArray tasksJsonArray = new JsonArray();

        JsonObject progressJsonObject = getProgressJsonObject(progressTemplateJsonFile);

        List<String> taskJsonFilesList = FileUtil.getFileList(jsonRootPath, filePrefix, true);
        for (int i = 0; i < taskJsonFilesList.size(); i++) {
            String file = jsonRootPath + "/" + filePrefix + (i + 1) + ".json";
            String jsonStr = JsonUtil.loadJsonFile(file).toString();
            JsonObject jsonObject = JsonUtil.string2JsonObject(jsonStr);
            tasksJsonArray.add(jsonObject);
        }

        taskJsonObject.add("tasks", tasksJsonArray);

        String allJsonStr = "{" + removeBracket(progressJsonObject.toString()) + "," + removeBracket
                (taskJsonObject.toString()) + "}";
        JsonObject all = JsonUtil.string2JsonObject(allJsonStr);

        dataJsonObject.add("data", all);

        FileUtil.saveToFile(targetJsonFile, dataJsonObject.toString());

    }

    public static String removeBracket(String value) {
        return value.substring(1, value.length() - 1);
    }

    public static JsonObject getProgressJsonObject(String file) {
        String jsonStr = JsonUtil.loadJsonFile(file).toString();
        return JsonUtil.string2JsonObject(jsonStr);
    }


    public static void generateDepartmentJsonTemplate(String targetFile) {
        List<Map<String, Object>> requiredProperties = generatePropertiesFromFile
                (departmentRequiredPropertiesFile, false, false);
        JsonObject jsonObject = generateJsonFromPropertiesMap(requiredProperties, "department");
        logger.debug("properties: {}", jsonObject);

        // write to file
        FileUtil.saveToFile(targetFile, jsonObject.toString());
    }

    public static void generateUserJsonTemplate(String targetFile) {
        List<Map<String, Object>> requiredProperties = generatePropertiesFromFile
                (userRequiredPropertiesFile, false, false);
        JsonObject jsonObject = generateJsonFromPropertiesMap(requiredProperties, "user");
        logger.debug("properties: {}", jsonObject);

        // write to file
        FileUtil.saveToFile(targetFile, jsonObject.toString());
    }

    public static void generateTaskJsonTemplate(String sourceFile, String targetFile, boolean
            setFirstValueAsDefaultStringValue) {
        List<Map<String, Object>> requiredProperties = generatePropertiesFromFile
                (taskRequiredPropertiesFile, false, false);
        List<Map<String, Object>> customizedProperties = generatePropertiesFromFile(sourceFile,
                setFirstValueAsDefaultStringValue, true);

        List<Map<String, Object>> result = new ArrayList<>();
        result.addAll(requiredProperties);
        result.addAll(customizedProperties);

        List<Map<String, Object>> list = removeDuplicateWithOrder(result);

        JsonObject taskJsonObject = generateJsonFromPropertiesMap(list, "task");
        // write to file
        FileUtil.saveToFile(targetFile, taskJsonObject.toString());
    }

    public static JsonObject generateJsonFromPropertiesMap(List<Map<String, Object>> list, String
            jsonPrefix) {
        JsonArray jsonArray = new JsonArray();
        for (Map<String, Object> map : list) {
            JsonObject jsonObject = JsonUtil.mapToJsonObject(map);
            jsonArray.add(jsonObject);
        }

        JsonObject propertiesJsonObject = new JsonObject();
        propertiesJsonObject.add("properties", jsonArray);

        JsonObject jsonObject = new JsonObject();
        jsonObject.add(jsonPrefix, propertiesJsonObject);
        logger.debug("properties: {}", jsonObject);
        return jsonObject;
    }


    public static List<Map<String, Object>> removeDuplicateWithOrder(List<Map<String, Object>>
                                                                             list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Object element = it.next();
            if (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
        return list;
    }

    public static List<Map<String, Object>> generatePropertiesFromFile(String file, boolean
            setFirstValueAsDefaultStringValue, boolean excludeRequiredProperties) {

        List<Map<String, Object>> list = new ArrayList<>();

        List<String> linesList = FileUtil.read(file);
        for (String line : linesList) {
            // remove the commented lines
            if (!line.startsWith("//")) {
                Map<String, Object> map = convertLineToMap(line,
                        setFirstValueAsDefaultStringValue, excludeRequiredProperties);
                if (map != null && map.size() > 0) {
                    list.add(map);
                }
            }
        }
        //logger.debug("properties {} of file: {}", list, file);

        return list;
    }

    // line format is as below:
    // 1. the second value is propertyName
    // 2. the last one is propertyType
    // Sample: 录入设计单位合同数据,designContract,String
    // Return: map("propertyType","propertyName","propertyType")
    public static Map<String, Object> convertLineToMap(String line, boolean
            setFirstValueAsDefaultStringValue, boolean excludeRequiredProperties) {
        Map<String, Object> map = new HashedMap();
        String[] values = line.split(LINE_SPLITTER);
        String propertyName = values[1];
        String propertyType = values[2];
        Object propertyValue;

        if (setFirstValueAsDefaultStringValue) {
            if (propertyType.equalsIgnoreCase("String")) {
                if (values.length == 4) {
                    propertyValue = values[3];
                } else {
                    propertyValue = values[0];
                }
            } else {
                propertyValue = getPropertyValue(propertyType);
            }
        } else {
            propertyValue = (values.length == 4) ? values[3] : getPropertyValue(propertyType);
        }

//        logger.debug("propertyType: {}", propertyType);
//        logger.debug("propertyName: {}", propertyName);
//        logger.debug("propertyValue: {}", propertyValue);

        //TODO: update
        List<String> reservedProperties = new ArrayList<>();
        reservedProperties.add("worker");
        reservedProperties.add("workerId");
//        reservedProperties.add("attachment");
        reservedProperties.add("executiveDepartment");
        reservedProperties.add("executiveDepartmentId");

        if (!excludeRequiredProperties) {
            map.put("propertyType", propertyType);
            map.put("propertyName", propertyName);
            map.put("propertyValue", propertyValue);
        } else {
            if (!reservedProperties.contains(propertyName)) {
                map.put("propertyType", propertyType);
                map.put("propertyName", propertyName);
                map.put("propertyValue", propertyValue);
            }
        }

        return map;
    }

    public static Object getPropertyValue(String propertyType) {
        Object propertyValue = null;
        if (propertyType.equalsIgnoreCase("String")) {
            propertyValue = "";
        } else if (propertyType.equalsIgnoreCase("Int") || propertyType.equalsIgnoreCase
                ("Integer")) {
            propertyValue = 0;
        } else if (propertyType.equalsIgnoreCase("Long")) {
            propertyValue = 0;
        } else if (propertyType.equalsIgnoreCase("Float") || propertyType.equalsIgnoreCase
                ("Double")) {
            propertyValue = 0;
        } else if (propertyType.equalsIgnoreCase("Boolean") || propertyType.equalsIgnoreCase
                ("bool")) {
            propertyValue = false;
        } else if (propertyType.equalsIgnoreCase("Date") || propertyType.equalsIgnoreCase
                ("DateTime")) {
            propertyValue = null;
        }

        return propertyValue;
    }

}
