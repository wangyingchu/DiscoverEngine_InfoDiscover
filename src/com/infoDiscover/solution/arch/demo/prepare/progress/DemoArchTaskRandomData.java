package com.infoDiscover.solution.arch.demo.prepare.progress;

import com.infoDiscover.common.util.DateUtil;
import com.infoDiscover.common.util.JsonUtil;
import com.infoDiscover.common.util.RandomUtil;
import com.infoDiscover.solution.demo.util.JsonConstants;
import com.infoDiscover.solution.demo.util.ProgressJsonParser;
import com.infoDiscover.solution.arch.demo.prepare.DemoDataConfig;
import com.infoDiscover.solution.arch.demo.prepare.DemoArchRandomData;
import com.infoDiscover.solution.arch.demo.prepare.UserRoleDataImporter;
import com.infoDiscover.solution.common.util.RandomData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun.
 */
public class DemoArchTaskRandomData {
    private final static Logger logger = LogManager.getLogger(DemoArchTaskRandomData
            .class);

    static String projectTemplate = DemoDataConfig.FILE_MAINTAIN_PROJECT;

    public static Map<String, Object>[] generateTasksRandomData
            (String projectTemplate, String projectType,
             String progressId,
             long progressStartTime,
             int firstNumberTasks) {
        logger.info("Enter method generateTasksRandomData() with projectTemplate: {} and " +
                "projectType: {} and progressId: {} and progressStartTime: {} and " +
                "firstNumberTasks: {}", projectTemplate, projectType, progressId, new Date
                (progressStartTime), firstNumberTasks);

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
            Map<String, Object> properties = DemoArchRandomData.jsonNodeToMapWithRandomValue(taskJsonNode);
            properties.put("type", "Task");
            properties.put("progressId", progressId);
            properties.put("taskName", getTasksList(projectType)[i]);

            String departmentId = getDepartmentsList(projectType)[i];
            properties.put("departmentId", departmentId);
            properties.put("assignee", UserRoleDataImporter.selectRandomUserFromRole
                    (DemoDataConfig.FILE_ROLE, departmentId));
            // 1~9 中取随机数
            long taskStartTime = DateUtil.getLongDateValue(startTime, RandomUtil
                    .generateRandomInRange(1, 9));
            properties.put("startTime", DateUtil.getDateTime(taskStartTime).toDate());
            long taskEndTime = DateUtil.getLongDateValue(taskStartTime, RandomUtil
                    .generateRandomInRange(1, 30));
            properties.put("endTime", DateUtil.getDateTime(taskEndTime).toDate());
            logger.info("properties: " + properties);

            // setup longitude and latitude
            if (projectType.equalsIgnoreCase(DemoDataConfig.PROJECTTYPE_MAINTAIN)) {
                if (DemoDataConfig.MAINTAIN_TASKS[i].equalsIgnoreCase("ProjectStart")) {

                    double longitude = DemoDataConfig.LONGITUDE + RandomUtil
                            .generateRandomInRange(0, 999) / 100000d;
                    double latitude = DemoDataConfig.LATITUDE + +RandomUtil
                            .generateRandomInRange(0, 999) / 100000d;
                    properties.put("dituxinxi_jingdu", longitude);
                    properties.put("dituxinxi_weidu", latitude);
                }

                if (DemoDataConfig.MAINTAIN_TASKS[i].equalsIgnoreCase("WeiXiuShenqing")) {
                    properties.put("wentifenlei", DemoDataConfig.wentifenleiList[RandomUtil
                            .generateRandomInRange(0, DemoDataConfig.wentifenleiList.length - 1)]);
                    properties.put("didianfenlei", DemoDataConfig.didianfenleiList[RandomUtil
                            .generateRandomInRange(0, DemoDataConfig.didianfenleiList.length - 1)]);
                    properties.put("gongzhongfenlei", DemoDataConfig.gongzhongfenleiList[RandomUtil
                            .generateRandomInRange(0, DemoDataConfig.gongzhongfenleiList.length -
                                    1)]);
                    properties.put("buweifenlei", DemoDataConfig.buweifenleiList[RandomUtil
                            .generateRandomInRange(0, DemoDataConfig.buweifenleiList.length - 1)]);
                    properties.put("projectType", DemoDataConfig.projectTypeList[RandomUtil
                            .generateRandomInRange(0, DemoDataConfig.projectTypeList.length - 1)]);
                }

                if (DemoDataConfig.MAINTAIN_TASKS[i].equalsIgnoreCase("KanchaBaojia")) {
                    double gaisuanzongji = 0;
                    for (int j = 1; j < 11; j++) {
                        String key = "gaisuanfenxiang_" + j;
                        DecimalFormat df = new DecimalFormat("######0.00");
                        double value = Double.valueOf(df.format(RandomUtil.generateRandomDouble(j
                                + 10000.0, j + 20000.0)));
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

    public static String[] getDepartmentsList(String projectType) {
        if (projectType.equalsIgnoreCase(DemoDataConfig.PROJECTTYPE_MAINTAIN)) {
            return DemoDataConfig.DEPARTMENTS_MAINTAIN_PROJECT;
        } else {
            return DemoDataConfig.DEPARTMENTS_NEWPROJECT;
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
