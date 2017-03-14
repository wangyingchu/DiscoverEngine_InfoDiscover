package com.infoDiscover.solution.arch.demo.prepare;

import com.infoDiscover.common.util.DateUtil;
import com.infoDiscover.common.util.JsonUtil;
import com.infoDiscover.common.util.RandomUtil;
import com.infoDiscover.solution.arch.demo.JsonConstants;
import com.infoDiscover.solution.arch.demo.ProgressJsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun.
 */
public class MaintainTaskRandomData {
    private final static Logger logger = LogManager.getLogger(MaintainProgressRandomData
            .class);

    static String roleFile = "/Users/sun/InfoDiscovery/Demodata/roles.csv";

    static String maintainProjectTemplate =
            "/Users/sun/InfoDiscovery/Demodata/jsonData/MaintainProject/SampleAllData.json";

    static String[] taskNames = {"WeiXiuShenqing", "ProjectStart", "KanchaBaojia",
            "zaojiahetongshenpi", "zaojiazixunqianding", "yusuanzaojia", "yusuanzaojiashenpi",
            "shigongdanweiqianyue", "shigong", "jungongyanshou", "ProjectEnd"};

    static String[] departmentIds = {"Proerty_Department", "Proerty_Department",
            "Engineering_Department", "Area_Chief_Financial_Officer",
            "General_Manager", "Engineering_Department",
            "Engineering_Department", "Engineering_Department",
            "Engineering_Department", "Engineering_Department", "Engineering_Department"};

    static String[] addresses = {"北京海淀区中关村", "北京海淀区清华大学", "北京朝阳区日坛国际酒店", "北京朝阳区东直门", "北京朝阳区西直门",
            "北京鸟巢", "北京通州司空小区"};
    static String[] longitude_latitude = {"39.9910020000,116.3288960000", "40.0092060000," +
            "116.3332660000", "39.9260050000,116.4495730000", "39.9639990000,116.4600810000",
            "39.8897660000,116.4803010000", "40.0001090000,116.4038930000", "39.9161950000," +
            "116.6635560000"};


    public static Map<String, Object>[] generateMainProjectTasksRandomData
            (String maintainProjectTemplate,
             String roleFile,
             String progressId,
             long progressStartTime,
             int firstNumberTasks) {
        logger.debug("Enter method generateMainProjectTasksRandomData() with template: " +
                maintainProjectTemplate);

        JsonNode json = JsonUtil.loadJsonFile(maintainProjectTemplate);
        JsonNode taskNodes = ProgressJsonParser.getTaskNodes(json.toString());

        // if json is empty
        if (JsonUtil.isEmptyJsonNode(taskNodes)) {
            logger.info("No progress and task data in the json");
            return null;
        }

        // in template, only one progress
        int tasksNumber = firstNumberTasks <= taskNodes.size() ? firstNumberTasks : taskNodes
                .size();

        Map<String, Object>[] tasksArray = new HashMap[tasksNumber];

        long startTime = progressStartTime;

        for (int i = 0; i < tasksNumber; i++) {
            JsonNode taskJsonNode = taskNodes.get(i).get(JsonConstants.JSON_TASK);
            Map<String, Object> properties = RandomData.jsonNodeToMapWithRandomValue(taskJsonNode);
            properties.put("type", "Task");
            properties.put("progressId", progressId);
            properties.put("taskName", taskNames[i]);
            properties.put("departmentId", departmentIds[i]);
            properties.put("assignee", UserRoleDataImporter.selectRandomUserFromRole(roleFile,
                    departmentIds[i]));
            // 1~9 中取随机数
            long taskStartTime = DateUtil.getLongDateValue(startTime, RandomUtil
                    .generateRandomInRange(1, 9));
            properties.put("startTime", taskStartTime);
            long taskEndTime = DateUtil.getLongDateValue(taskStartTime, RandomUtil
                    .generateRandomInRange(1, 30));
            properties.put("endTime", taskEndTime);
            logger.debug("properties: " + properties);

            // setup longitude and latitude
            if (taskNames[i].equalsIgnoreCase("ProjectStart")) {
                int randomInt = RandomUtil.generateRandomInRange(0, addresses.length - 1);
                String address = addresses[randomInt];
                double longitude = Double.valueOf(longitude_latitude[randomInt].split(",")[1]);
                double latitude = Double.valueOf(longitude_latitude[randomInt].split(",")[0]);
                properties.put("dizhifanghao", address);
                properties.put("dituxinxi_jingdu",longitude);
                properties.put("dituxinxi_weidu", latitude);
            }


            tasksArray[i] = properties;

            startTime = taskEndTime;
        }

        logger.debug("Exit method generateMainProjectTasksRandomData()...");
        return tasksArray;
    }

    public static void main(String[] args) {
       Map<String, Object>[] array = generateMainProjectTasksRandomData(maintainProjectTemplate,
               roleFile,
                "maintain1",
                RandomData
                .getRandomTime(2010,2016, 0), 11);
       logger.debug("array.size: " + array.length);
       for (Map<String, Object> map: array) {
           logger.debug("map's startTime: " + map.get("startTime"));
           logger.debug("map's endTime: " + map.get("endTime"));
       }
    }
}
