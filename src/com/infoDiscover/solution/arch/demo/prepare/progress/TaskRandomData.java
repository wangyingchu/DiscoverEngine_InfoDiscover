package com.infoDiscover.solution.arch.demo.prepare.progress;

import com.infoDiscover.common.util.DateUtil;
import com.infoDiscover.common.util.JsonUtil;
import com.infoDiscover.common.util.RandomUtil;
import com.infoDiscover.solution.arch.demo.JsonConstants;
import com.infoDiscover.solution.arch.demo.ProgressJsonParser;
import com.infoDiscover.solution.arch.demo.prepare.DemoDataConfig;
import com.infoDiscover.solution.arch.demo.prepare.RandomData;
import com.infoDiscover.solution.arch.demo.prepare.UserRoleDataImporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun.
 */
public class TaskRandomData {
    private final static Logger logger = LogManager.getLogger(TaskRandomData
            .class);

    static String projectTemplate = DemoDataConfig.FILE_MAINTAIN_PROJECT;

    public static Map<String, Object>[] generateTasksRandomData
            (String projectTemplate, String projectType,
             String progressId,
             long progressStartTime,
             int firstNumberTasks) {
        logger.info("Enter method generateTasksRandomData() with projectTemplate: " +
                projectTemplate + " and projectType: " + projectType + " and progressId: " +
                progressId + " and progressStartTime: " + new
                DateTime(progressStartTime) + " and firstNumberTasks: " + firstNumberTasks);

        JsonNode json = JsonUtil.loadJsonFile(projectTemplate);
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
            properties.put("taskName", getTasksList(projectType)[i]);
            properties.put("departmentId", DemoDataConfig.DEPARTMENTS[i]);
            properties.put("assignee", UserRoleDataImporter.selectRandomUserFromRole
                    (DemoDataConfig.FILE_ROLE,
                            DemoDataConfig.DEPARTMENTS[i]));
            // 1~9 中取随机数
            long taskStartTime = DateUtil.getLongDateValue(startTime, RandomUtil
                    .generateRandomInRange(1, 9));
            properties.put("startTime", DateUtil.getDateTime(taskStartTime));
            long taskEndTime = DateUtil.getLongDateValue(taskStartTime, RandomUtil
                    .generateRandomInRange(1, 30));
            properties.put("endTime", DateUtil.getDateTime(taskEndTime));
            logger.info("properties: " + properties);

            // setup longitude and latitude
            if (projectType.equalsIgnoreCase(DemoDataConfig.PROJECTTYPE_MAINTAIN)) {
                if (DemoDataConfig.MAINTAIN_TASKS[i].equalsIgnoreCase("ProjectStart")) {
                    int randomInt = RandomUtil.generateRandomInRange(0, DemoDataConfig.ADDRESSES
                            .length - 1);
                    String address = DemoDataConfig.ADDRESSES[randomInt];
                    double longitude = Double.valueOf(DemoDataConfig
                            .LONGITUDE_LATITUDE[randomInt].split(",")[1]);
                    double latitude = Double.valueOf(DemoDataConfig.LONGITUDE_LATITUDE[randomInt]
                            .split(",")[0]);
                    properties.put("dizhifanghao", address);
                    properties.put("dituxinxi_jingdu", longitude);
                    properties.put("dituxinxi_weidu", latitude);
                }

                if (DemoDataConfig.MAINTAIN_TASKS[i].equalsIgnoreCase("KanchaBaojia")) {
                    double gaisuanzongji = 0;
                    for (int j = 1; j < 11; j++) {
                        String key = "gaisuanfenxiang_" + j;
                        double value = RandomUtil.generateRandomDouble(j + 10000, j + 20000);
                        properties.put(key, value);
                        gaisuanzongji += value;
                    }

                    properties.put("gaisuanzongji", gaisuanzongji);
                }
            }


            tasksArray[i] = properties;

            startTime = taskEndTime;
        }

        logger.info("Exit method generateMainProjectTasksRandomData()...");
        return tasksArray;
    }

    public static String[] getTasksList(String projectType) {
        if (projectType.equalsIgnoreCase(DemoDataConfig.PROJECTTYPE_MAINTAIN)) {
            return DemoDataConfig.MAINTAIN_TASKS;
        } else {
            return DemoDataConfig.NEWPROJECT_TASKS;
        }
    }


    public static void main(String[] args) {
        Map<String, Object>[] array = generateTasksRandomData(projectTemplate, DemoDataConfig
                        .PROJECTTYPE_MAINTAIN,
                "maintain1",
                RandomData
                        .getRandomTime(2010, 2016, 0), 11);
        logger.info("array.size: " + array.length);
        for (Map<String, Object> map : array) {
            logger.info("map's startTime: " + map.get("startTime"));
            logger.info("map's endTime: " + map.get("endTime"));
        }
    }
}
