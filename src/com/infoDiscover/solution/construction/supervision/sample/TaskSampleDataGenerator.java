package com.infoDiscover.solution.construction.supervision.sample;

import com.infoDiscover.common.util.DateUtil;
import com.infoDiscover.common.util.JsonUtil;
import com.infoDiscover.common.util.RandomUtil;
import com.infoDiscover.solution.arch.demo.UserRoleDataImporter;
import com.infoDiscover.solution.common.util.RandomData;
import com.infoDiscover.solution.construction.supervision.database.SupervisionSolutionConstants;
import com.infoDiscover.solution.construction.supervision.util.SampleFileUtil;
import com.infoDiscover.solution.sample.util.JsonConstants;
import com.infoDiscover.solution.sample.util.ProgressJsonParser;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.codehaus.jackson.JsonNode;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by sun.
 */
public class TaskSampleDataGenerator {
    private final static Logger logger = LoggerFactory.getLogger(TaskSampleDataGenerator
            .class);

    public static int minValue = 1;
    public static int maxValue = 20;
    public static double minDoubleValue = 1000.0d;
    public static double maxDoubleValue = 100000.0d;
    public static int randomStringLength = 9;
    public static long longValue = 10000l;


    //
    public static Map<String, Double> longitudeMap = new HashMap<>();
    public static String projectAddress;
    public static String supervisionCompany;
    public static String supervisionCompanyContact;
    public static String supervisionContactPhone;

    public static Map<String, Object>[] generateTasksRandomData(
            String projectJsonTemplate,
            String projectType,
            String progressId,
            long progressStartDate,
            int firstTasks) {

        logger.info("Enter method generateTasksRandomData() with projectJsonTemplate: {} and " +
                "projectType: {} and progressId: {} and progressStartDate: {} and " +
                "firstTasks: {}", projectJsonTemplate, projectType, progressId, new Date
                (progressStartDate), firstTasks);

        JsonNode json = JsonUtil.loadJsonFile(projectJsonTemplate);
        JsonNode taskNodes = ProgressJsonParser.getTaskNodes(json.toString());

        // if json is empty
        if (JsonUtil.isEmptyJsonNode(taskNodes)) {
            logger.info("No progress and task data in the json template: {}", projectJsonTemplate);
            return null;
        }

        int tasksNumber = firstTasks <= taskNodes.size() ? firstTasks : taskNodes
                .size();

        Map<String, Object>[] tasksArray = new HashMap[tasksNumber];

        long startDate = progressStartDate;

        for (int i = 0; i < tasksNumber; i++) {
            JsonNode propertiesJsonNode = taskNodes.get(i).get(JsonConstants.JSON_TASK);

            Map<String, Object> properties = RandomData.propertiesJsonNodeToMapWithRandomValue
                    (propertiesJsonNode, randomStringLength,
                            minValue, maxValue, minDoubleValue, maxDoubleValue, longValue,
                            reservedStringPropertyNames(), null,
                            null);

            updateRequiredPropertiesRandomData(properties,
                    progressId, projectType, startDate, i);

            updateCustomizedTasksProperties(projectType,properties);

            // setup longitude and latitude
//            if (properties.containsKey("longitude")) {
//                setRandomLongitudeLatitude(properties);
//            }
//
//            if (properties.containsKey("latitude")) {
//                setRandomLongitudeLatitude(properties);
//            }

            tasksArray[i] = properties;

            startDate = ((Date) properties.get(JsonConstants.END_DATE)).getTime();
        }

        logger.info("Exit method generateMainProjectTasksRandomData()...");
        return tasksArray;
    }

    private static Map<String, Object> updateRequiredPropertiesRandomData(
            Map<String, Object> properties,
            String progressId,
            String projectType,
            long startDate,
            int taskSequence) {

//        properties.put(JsonConstants.JSON_TYPE, "Task");
        properties.put(JsonConstants.PROGRESS_ID, progressId);
        String taskName = getTasksList(projectType)[taskSequence];
        properties.put(JsonConstants.TASK_NAME, taskName);
        properties.put(JsonConstants.TASK_ID, progressId + "_" + taskName + "_" + (taskSequence +
                1));

        // TODO: update attachment
        //properties.put(JsonConstants.ATTACHMENT, "");

        String departmentId = getDepartmentIdList(projectType)[taskSequence];
        String departmentName = getDepartmentNameList(projectType)[taskSequence];

        if (taskName.equalsIgnoreCase(SampleDataSet.TASK1_OF_MAINTENANCE_PROJECT) || taskName
                .equalsIgnoreCase(SampleDataSet.TASK1_OF_NEW_PROJECT)) {
            int randomIndex = RandomUtil.generateRandomInRange(0, SampleDataSet
                    .DEPARTMENTS_TO_ASSIGN_TASK.length);
            departmentId = SampleDataSet.DEPARTMENTS_TO_ASSIGN_TASK[randomIndex];
            departmentName = SampleDataSet.DEPARTMENT_NAMES_TO_ASSIGN_TASK[randomIndex];
        }

        properties.put(JsonConstants.EXECUTIVE_DEPARTMENT_ID, departmentId);
        properties.put(JsonConstants.EXECUTIVE_DEPARTMENT, departmentName);
        String userId = UserRoleDataImporter.selectRandomUserFromRole
                (SampleDataSet.FILE_USER_DEPARTMENT, departmentId);
        properties.put(JsonConstants.WORKER_ID, userId);
        properties.put(JsonConstants.WORKER, UserRoleDataImporter.getUserName(SampleDataSet
                .FILE_USER, userId));

        // 1~9 中取随机数
        long taskStartDate = DateUtil.getLongDateValue(startDate, RandomUtil
                .generateRandomInRange(1, 9));
        properties.put(JsonConstants.START_DATE, DateUtil.getDateTime(taskStartDate).toDate());
        long taskEndDate = DateUtil.getLongDateValue(taskStartDate, RandomUtil
                .generateRandomInRange(10, 40));
        properties.put(JsonConstants.END_DATE, DateUtil.getDateTime(taskEndDate).toDate());

        logger.debug("properties: {}", properties);
        return properties;
    }

    // TODO: to refine with the new project template
    private static Map<String, Object> updateCustomizedTasksProperties(String projectType,
            Map<String, Object> taskProperties) {

        List<String> dimensionList = Arrays.asList(SampleDataSet.dimensionArray);

        Set<String> keySet = taskProperties.keySet();
        Iterator<String> it = keySet.iterator();

        while (it.hasNext()) {
            String key = it.next();
            if (dimensionList.contains(key)) {
                updateDimensionPropertyValue(key, taskProperties);
            }
        }

        if(projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_MAINTENANCE)){
            updateMaintenanceProjectTasksPropertyValue(taskProperties);
        }

        return taskProperties;
    }

    private static void updateMaintenanceProjectTasksPropertyValue(Map<String, Object>
                                                    taskProperties) {
        // task1 of maintenance project
        String taskName = taskProperties.get(JsonConstants.TASK_NAME).toString();
        if (taskName.equalsIgnoreCase(SampleDataSet.TASK1_OF_MAINTENANCE_PROJECT)) {
            Map<String, String> issueReporterMap = SampleFileUtil.readIssueReporterAndPhone
                    (SampleDataSet.FILE_ISSUE_REPORTER);

            Map<String, String> randomValue = SampleFileUtil.getRandomValue(issueReporterMap);

            String issueReporter = randomValue.keySet().iterator().next();
            String issueReporterPhone = issueReporterMap.get(issueReporter);
            String applyDepartment = "第三方物业管理公司";

            String issueClassification = taskProperties.get(ClassificationConstants
                    .ISSUE_CLASSIFICATION).toString();
            String issueDescription = SampleFileUtil.selectIssueDescription(SampleDataSet
                    .FILE_ISSUE_DESCRIPTION, issueClassification);

            Map<String, String> projectAddressMap = SampleFileUtil.readProjectAddressAndLongitude
                    (null);
            randomValue = SampleFileUtil.getRandomValue(projectAddressMap);
            projectAddress = randomValue.keySet().iterator().next();
            setLogitudeAndLatitude(randomValue.get(projectAddress));

            String projectName = projectAddress + "_" + issueClassification;

            taskProperties.put("issueReporter", issueReporter);
            taskProperties.put("issueReporterPhone", issueReporterPhone);
            taskProperties.put("issueDescription", issueDescription);
            taskProperties.put("applyDepartment", applyDepartment);
            taskProperties.put("projectAddress", projectAddress);
            taskProperties.put("projectName", projectName);
            taskProperties.put(ClassificationConstants.PROJECT_CONSTRUCTION_CLASSIFICATION,
                    SampleDataSet.PROJECTNAME_MAINTANENCE);

            taskProperties.put("estimatedDateOfInvestigation", getDateAfterStartDate
                    (taskProperties, 1));
        }

        // task2 of maintenance project
        if (taskName.equalsIgnoreCase(SampleDataSet.TASK2_OF_MAINTENANCE_PROJECT)) {
//            maintenanceProposal,equipment,specialRequirement,maintenanceCompany,projectLeader
            taskProperties.put("maintenanceProposal", "");
            taskProperties.put("equipment", "");
            taskProperties.put("specialRequirement", "");
            taskProperties.put("maintenanceDate", getDateAfterStartDate(taskProperties, 1));
            String maintenanceCompany = "";
            if (projectAddress != null && !projectAddress.isEmpty()) {
                if (projectAddress.indexOf("-") > -1) {
                    maintenanceCompany = projectAddress.substring(0, projectAddress.indexOf("-"));
                } else {
                    maintenanceCompany = projectAddress;
                }
            }
            Map<String, String> leaderMap = SampleFileUtil.readProjectLeader(null);
            Map<String, String> randomValue = SampleFileUtil.getRandomValue(leaderMap);
            String projectLeaderId = randomValue.keySet().iterator().next();
            String projectLeader = randomValue.get(projectLeaderId);

            taskProperties.put("maintenanceCompany", maintenanceCompany);
            taskProperties.put("projectLeader", projectLeader);
            taskProperties.put("latitude", longitudeMap.get("latitude"));
            taskProperties.put("longitude", longitudeMap.get("longitude"));
        }

        // task3 of maintenance project
        if (taskName.equalsIgnoreCase(SampleDataSet.TASK3_OF_MAINTENANCE_PROJECT)) {
//            录入现场勘察数据,taskName,String
//            现场勘察日期,dateOfSurvey,Date
//            现场勘察单位,companyOfSurvey,String
//            现场勘察单位负责人,surveyCompanyLeader,String
//            关联维修预警,relatedMaintenanceEarlyWarning,String
//            问题诊断,issueDiagnosis,String
//            经办人ID,workerId,String
//            经办人,worker,String
//            执行部门ID,executiveDepartmentId,String,Engineering_Department
//            执行部门,executiveDepartment,String,工程部
//            公司类别,companyClassification,String,勘察单位
//            地图信息_经度,longitude,Double
//            地图信息_纬度,latitude,Double
            Map<String, String> surveyMap = SampleFileUtil.readReconnaissanceCompany(null);
            Map<String, String> randomValue = SampleFileUtil.getRandomValue(surveyMap);
            String company = randomValue.keySet().iterator().next();
            String leader = randomValue.get(company);
            taskProperties.put("dateOfSurvey", getDateAfterStartDate(taskProperties, 2));
            taskProperties.put("companyOfSurvey", company);
            taskProperties.put("surveyCompanyLeader", leader);
            taskProperties.put("relatedMaintenanceEarlyWarning", "");
            taskProperties.put("issueDiagnosis", "");
            taskProperties.put("companyClassification", "勘察单位");
            taskProperties.put("latitude", longitudeMap.get("latitude"));
            taskProperties.put("longitude", longitudeMap.get("longitude"));

        }

        // task5 of maintenance project
        if (taskName.equalsIgnoreCase(SampleDataSet.TASK5_OF_MAINTENANCE_PROJECT)) {
//            录入设计单位合同数据,taskName,String
//            设计合同编号,designContractNumber,String
//            设计合同金额,designContractSum,Double
//            设计单位名称,designCompany,String
//            设计合同签订日期,designContractAssignDate,Date
//            设计合同开始日期,startDate,Date
//            设计合同结束日期,endDate,Date
//            设计单位负责人,designCompanyLeader,String
//            设计合同审批日期,designContractApproveDate,Date
//            经办人ID,workerId,String
//            经办人,worker,String
//            执行部门ID,executiveDepartmentId,String,Cost_Department
//            执行部门,executiveDepartment,String,成本部
//            公司类别,companyClassification,String,设计单位

            String designContractNumber = "设计合同编号_" + RandomUtil.generateRandomInRange(1, 1000);
            Map<String, String> designMap = SampleFileUtil.readDesignCompany(null);
            Map<String, String> randomValue = SampleFileUtil.getRandomValue(designMap);
            String company = randomValue.keySet().iterator().next();
            String leader = randomValue.get(company);
            Date designContractAssignDate = getDateAfterStartDate(taskProperties, -2);
            Date designContractApproveDate = getDateAfterStartDate(taskProperties, 2);
            taskProperties.put("designContractNumber", designContractNumber);
            taskProperties.put("designCompany", company);
            taskProperties.put("designCompanyLeader", leader);
            taskProperties.put("designContractAssignDate", designContractAssignDate);
            taskProperties.put("designContractApproveDate", designContractApproveDate);
            taskProperties.put("companyClassification", "设计单位");
        }

        // task6 of maintenance project
        if (taskName.equalsIgnoreCase(SampleDataSet.TASK6_OF_MAINTENANCE_PROJECT)) {
//            录入设计成果数据,taskName,String
//            建设用地面积,constructionArea,Double
//            用地性质,landProperty,String
//            主要功能或建筑类型,constructionType,String
//            地下建筑面积,undergroundBuildingArea,Double
//            绿化率,greeningRatio,Double
//            机动车停车数,motorVehicleParking,Int
//            占地率,floorAreaRatio,Double
//            容积率,plotRatio,Double
//            地上建筑层数,groundFloorLevel,Int
//            地下建筑层数,undergroundBuildingLayer,Int
//            建筑高度,buildingHeight,Double
//            设计单位技术负责人,designCompanyTechnicalLeader,String
//            经办人ID,workerId,String
//            经办人,worker,String
//            执行部门ID,executiveDepartmentId,String,Engineering_Department
//            执行部门,executiveDepartment,String,工程部

            Map<String, String> designMap = SampleFileUtil.readDesignCompanyTechnicalLeader(null);
            Map<String, String> randomValue = SampleFileUtil.getRandomValue(designMap);
            String company = randomValue.keySet().iterator().next();
            String technicalLeader = randomValue.get(company);
            taskProperties.put("designCompanyTechnicalLeader", technicalLeader);
        }

        // task7 of maintenance project
        if (taskName.equalsIgnoreCase(SampleDataSet.TASK7_OF_MAINTENANCE_PROJECT)) {
//        录入造价咨询合同数据,taskName,String
//        造价咨询合同编号,costConsultationContractNumber,String
//        造价咨询合同金额,costConsultationContractSum,Double
//        造价咨询单位名称,costConsultationCompany,String
//        造价咨询合同签订日期,costConsultationContractAssignDate,Date
//        造价咨询合同开始日期,startDate,Date
//        造价咨询合同结束日期,endDate,Date
//        造价咨询单位负责人,costConsultationCompanyLeader,String
//        造价咨询合同审批日期,costConsultationContractApprovalDate,Date
//        经办人ID,workerId,String
//        经办人,worker,String
//        执行部门ID,executiveDepartmentId,String,Cost_Department
//        执行部门,executiveDepartment,String,成本部
//        公司类别,companyClassification,String,造价咨询单位
            String costConsultationContractNumber = "造价咨询合同编号_" + RandomUtil.generateRandomInRange
                    (10000, 1000000);
            Map<String, String> costConsultingCompanyMap = SampleFileUtil
                    .readCostConsultingCompany(null);
            String costConsultationCompany = costConsultingCompanyMap.keySet().iterator()
                    .next();
            String costConsultationCompanyLeader = costConsultingCompanyMap.get
                    (costConsultationCompany);
            Date costConsultationContractAssignDate = getDateAfterStartDate(taskProperties, -5);
            Date costConsultationContractApprovalDate = getDateAfterStartDate(taskProperties, -2);
            taskProperties.put("costConsultationContractNumber", costConsultationContractNumber);
            taskProperties.put("costConsultationCompany", costConsultationCompany);
            taskProperties.put("costConsultationCompanyLeader", costConsultationCompanyLeader);
            taskProperties.put("costConsultationContractAssignDate",
                    costConsultationContractAssignDate);
            taskProperties.put("costConsultationContractApprovalDate",
                    costConsultationContractApprovalDate);
            taskProperties.put("companyClassification", "造价咨询单位");
        }

        // task8 of maintenance project
        if (taskName.equalsIgnoreCase(SampleDataSet.TASK8_OF_MAINTENANCE_PROJECT)) {
//            录入造价咨询单位成果数据,taskName,String
//            工程预算送审价,priceOfProjectBudgetForReview,Double
//            工程预算审核价,material_auditPriceOfProjectBudget,Double
//            预算核增（减）额,budgetOfIncreaseOrDecrease,Double
//            工程预算审核完成日期,projectBudgetAuditCompletionDate,Date
//            工程预算审核负责人,projectBudgetAuditLeader,String
//            经办人ID,workerId,String
//            经办人,worker,String
//            执行部门ID,executiveDepartmentId,String,Engineering_Department
//            执行部门,executiveDepartment,String,工程部
//            审批部门,approveDepartment,String,总经理
//            ....材料价格

            Date projectBudgetAuditCompletionDate = getDateAfterStartDate(taskProperties, 5);
            Map<String, String> map = SampleFileUtil.readBudgetAuditLeader(null);
            Map<String, String> randomValue = SampleFileUtil.getRandomValue(map);
            String id = randomValue.keySet().iterator().next();
            String leader = randomValue.get(id);
            taskProperties.put("projectBudgetAuditCompletionDate",
                    projectBudgetAuditCompletionDate);
            taskProperties.put("projectBudgetAuditLeader", leader);
            taskProperties.put("approveDepartment", "总经理");
        }

        // task9 of maintenance project
        if (taskName.equalsIgnoreCase(SampleDataSet.TASK9_OF_MAINTENANCE_PROJECT)) {
//            录入工程预算造价审批数据,taskName,String
//            工程预算审定价,approvalPriceOfProjectBudget,Double
//            工程预算审批完成日期,approvalDateOfProjectBudget,Date
//            工程预算审批负责人,approvalLeaderOfProjectBudget,String
//            经办人ID,workerId,String
//            经办人,worker,String
//            执行部门ID,executiveDepartmentId,String,GeneralManager
//            执行部门,executiveDepartment,String,总经理
            Date approvalDateOfProjectBudget = getDateAfterStartDate(taskProperties, -5);
            Map<String, String> map = SampleFileUtil.readBudgetApproveLeader(null);
            Map<String, String> randomValue = SampleFileUtil.getRandomValue(map);
            String id = randomValue.keySet().iterator().next();
            String leader = randomValue.get(id);
            taskProperties.put("approvalDateOfProjectBudget", approvalDateOfProjectBudget);
            taskProperties.put("approvalLeaderOfProjectBudget", leader);
        }

        // task10 of maintenance project
        if (taskName.equalsIgnoreCase(SampleDataSet.TASK10_OF_MAINTENANCE_PROJECT)) {
//            录入施工合同数据,taskName,String
//            施工合同编号,constructionContractNumber,String
//            施工合同金额,constructionContractSum,String
//            施工单位名称,constructionCompany,String
//            施工合同签订日期,constructionContractAssignDate,Date
//            施工合同开始日期,startDate,Date
//            施工合同结束日期,endDate,Date
//            施工合同审批完成日期,constructionContractApprovalDate,Date
//            施工单位负责人,constructionCompanyLeader,String
//            经办人ID,workerId,String
//            经办人,worker,String
//            执行部门ID,executiveDepartmentId,String,Cost_Department
//            执行部门,executiveDepartment,String,成本部
//            公司类别,companyClassification,String,施工单位

            String constructionContractNumber = "施工合同编号_" + RandomUtil.generateRandomInRange
                    (10000, 100000);
            Map<String, String> map = SampleFileUtil.readConstructionCompany(null);
            Map<String, String> randomValue = SampleFileUtil.getRandomValue(map);
            String constructionCompany = randomValue.keySet().iterator().next();
            String constructionCompanyLeader = randomValue.get(constructionCompany);
            Date constructionContractAssignDate = getDateAfterStartDate(taskProperties, -5);
            Date constructionContractApprovalDate = getDateAfterStartDate(taskProperties, -2);
            taskProperties.put("constructionContractNumber", constructionContractNumber);
            taskProperties.put("constructionCompany", constructionCompany);
            taskProperties.put("constructionCompanyLeader", constructionCompanyLeader);
            taskProperties.put("constructionContractAssignDate", constructionContractAssignDate);
            taskProperties.put("constructionContractApprovalDate",
                    constructionContractApprovalDate);
            taskProperties.put("companyClassification", "施工单位");
        }

        // task11 of maintenance project
        if (taskName.equalsIgnoreCase(SampleDataSet.TASK11_OF_MAINTENANCE_PROJECT)) {
//            录入监理单位合同数据,taskName,String
//            监理合同编号,supervisionContractNumber,String
//            监理合同金额,supervisionContractSum,Double
//            监理单位名称,supervisionCompany,String
//            监理合同签订日期,supervisionContractAssignDate,Date
//            监理合同开始日期,startDate,Date
//            监理合同结束日期,endDate,Date
//            监理单位技术负责人,supervisionCompanyTechnicalLeader,String
//            监理单位联系人,supervisionCompanyContact,String
//            监理单位联系人电话,supervisionContactPhone,String
//            合同审批人,supervisionContractAuditor,String
//            合同审批日期,supervisionContractAuditDate,Date
//            经办人ID,workerId,String
//            经办人,worker,String
//            执行部门ID,executiveDepartmentId,String,Cost_Department
//            执行部门,executiveDepartment,String,成本部
//            公司类别,companyClassification,String,监理单位
            String supervisionContractNumber = "监理合同编号_" + RandomUtil.generateRandomInRange
                    (10000, 100000);
            Map<String, String> supervisionCompanyMap = SampleFileUtil.readSupervisorCompany(null);
            Map<String, String> randomValue = SampleFileUtil.getRandomValue(supervisionCompanyMap);
            supervisionCompany = randomValue.keySet().iterator().next();
            supervisionCompanyContact = randomValue.get(supervisionCompany);
            Date supervisionContractAssignDate = getDateAfterStartDate(taskProperties, -5);
            Date supervisionContractAuditDate = getDateAfterStartDate(taskProperties, -2);

            Map<String, String> supervisionCompanyTechnicalLeaderMap = SampleFileUtil
                    .readSupervisionCompanyTechnicalLeader(null);
            Map<String, String> randomTechnicalLeaderMap = SampleFileUtil.getRandomValue
                    (supervisionCompanyTechnicalLeaderMap);
            String supervisionCompanyTechnicalLeaderId = randomTechnicalLeaderMap.keySet()
                    .iterator().next();
            String supervisionCompanyTechnicalLeader = randomTechnicalLeaderMap.get
                    (supervisionCompanyTechnicalLeaderId);


            Map<String, String> supervisionContractAuditorMap = SampleFileUtil
                    .readSupervisionContactAuditLeader(null);
            Map<String, String> randomSupervisionContractAuditorMap = SampleFileUtil.getRandomValue
                    (supervisionContractAuditorMap);
            String supervisionContractAuditorId = randomSupervisionContractAuditorMap.keySet()
                    .iterator()
                    .next();
            String supervisionContractAuditor = randomSupervisionContractAuditorMap.get
                    (supervisionContractAuditorId);

            List<String> supervisionContactPhoneList = SampleFileUtil.readSupervisionContactPhone
                    (null);
            supervisionContactPhone = supervisionContactPhoneList.get(RandomUtil
                    .generateRandomInRange(0, supervisionContactPhoneList.size()));

            taskProperties.put("supervisionContractNumber", supervisionContractNumber);
            taskProperties.put("supervisionCompany", supervisionCompany);
            taskProperties.put("supervisionCompanyContact", supervisionCompanyContact);
            taskProperties.put("supervisionContractAssignDate", supervisionContractAssignDate);
            taskProperties.put("supervisionContractAuditDate", supervisionContractAuditDate);
            taskProperties.put("supervisionCompanyTechnicalLeader",
                    supervisionCompanyTechnicalLeader);
            taskProperties.put("supervisionContactPhone", supervisionContactPhone);
            taskProperties.put("supervisionContractAuditor", supervisionContractAuditor);
            taskProperties.put("companyClassification", "监理单位");

        }

        // task12 of maintenance project
        if (taskName.equalsIgnoreCase(SampleDataSet.TASK12_OF_MAINTENANCE_PROJECT)) {
//            录入工程施工数据,taskName,String
//            开工日期,constructionStartDate,Date
//            施工工期,constructionPeriod,Int
//            监理单位,supervisionCompany,String
//            监理单位联系人,supervisionCompanyContact,String
//            监理单位联系人电话,supervisionContactPhone,String
//            现场巡查人员,siteInspector,String
//            现场巡查时间,siteInspectDate,Date
//            现场巡查地点,siteInspectPlace,String
//            经办人ID,workerId,String
//            经办人,worker,String
//            执行部门ID,executiveDepartmentId,String,Engineering_Department
//            执行部门,executiveDepartment,String,工程部
//            公司类别,companyClassification,String,监理单位
            Date constructionStartDate = getDateAfterStartDate(taskProperties, 5);
            List<String> siteInspectorList = SampleFileUtil.readSiteInspector(null);
            String siteInspector = siteInspectorList.get(RandomUtil.generateRandomInRange(0,
                    siteInspectorList.size()));
            List<String> siteInspectPlaceList = SampleFileUtil.readSiteInspectPlace(null);
            String siteInspectPlace = siteInspectPlaceList.get(RandomUtil.generateRandomInRange
                    (0,siteInspectPlaceList.size()));
            Date siteInspectDate = getDateAfterStartDate(taskProperties,10);
            taskProperties.put("constructionStartDate",constructionStartDate);
            taskProperties.put("supervisionCompany",supervisionCompany);
            taskProperties.put("supervisionCompanyContact",supervisionCompanyContact);
            taskProperties.put("supervisionContactPhone",supervisionContactPhone);
            taskProperties.put("siteInspector",siteInspector);
            taskProperties.put("siteInspectDate",siteInspectDate);
            taskProperties.put("siteInspectPlace",siteInspectPlace);
            taskProperties.put("companyClassification","监理单位");

        }

        // task13 of maintenance project
        if (taskName.equalsIgnoreCase(SampleDataSet.TASK13_OF_MAINTENANCE_PROJECT)) {
//            录入工程竣工验收数据,taskName,String
//            验收日期,finalAcceptanceDate,Date
//            验收人员名单,finalAcceptanceMembers,String
//            验收评价,finalAcceptanceComments,String
//            是否通过验收,passFinalAcceptance,Boolean
//            经办人ID,workerId,String
//            经办人,worker,String
//            执行部门ID,executiveDepartmentId,String,Engineering_Department
//            执行部门,executiveDepartment,String,工程部

            Date finalAcceptanceDate = getDateAfterStartDate(taskProperties,6);
            String finalAcceptanceMembers = SampleFileUtil.readFinalAcceptanceMembers(null);
            String finalAcceptanceComments = "通过";
            Boolean passFinalAcceptance = true;

            taskProperties.put("finalAcceptanceDate",finalAcceptanceDate);
            taskProperties.put("finalAcceptanceMembers",finalAcceptanceMembers);
            taskProperties.put("finalAcceptanceComments",finalAcceptanceComments);
            taskProperties.put("passFinalAcceptance", passFinalAcceptance);
        }

        // task14 of maintenance project
        if (taskName.equalsIgnoreCase(SampleDataSet.TASK14_OF_MAINTENANCE_PROJECT)) {

        }

        // task15 of maintenance project
        if (taskName.equalsIgnoreCase(SampleDataSet.TASK15_OF_MAINTENANCE_PROJECT)) {
//            项目关闭,taskName,String
//            物业部审批意见,commentOfPropertyDepartment,String
//            工程部审批意见,commentOfEngineeringDepartment,String
//            分管领导审批意见,commentOfLeaderInCharge,String
//            总经理审批意见,commentOfGeneralManager,String
//            区财务总监审批意见,commentOfFinanceDirector,String
//            任务下达局室审批意见,commentOfMissionOwner,String
//            经办人ID,workerId,String
//            经办人,worker,String
//            执行部门ID,executiveDepartmentId,String,GeneralManager
//            执行部门,executiveDepartment,String,总经理
            String commentOfPropertyDepartment= "通过";
            String commentOfEngineeringDepartment= "通过";
            String commentOfLeaderInCharge= "通过";
            String commentOfGeneralManager= "通过";
            String commentOfFinanceDirector= "通过";
            String commentOfMissionOwner= "通过";
            taskProperties.put("commentOfPropertyDepartment",commentOfPropertyDepartment);
            taskProperties.put("commentOfEngineeringDepartment",commentOfEngineeringDepartment);
            taskProperties.put("commentOfLeaderInCharge",commentOfLeaderInCharge);
            taskProperties.put("commentOfGeneralManager",commentOfGeneralManager);
            taskProperties.put("commentOfFinanceDirector",commentOfFinanceDirector);
            taskProperties.put("commentOfMissionOwner",commentOfMissionOwner);
        }
    }

    private static Date getDateAfterStartDate(Map<String, Object> taskProperties, int afterDays) {
        long startDateInLongValue = ((Date) taskProperties.get(JsonConstants.START_DATE))
                .getTime();
        long dateAfterStartDate = DateUtil.getLongDateValue(startDateInLongValue, afterDays);
        return DateUtil.getDateTime(dateAfterStartDate).toDate();

    }

    private static void setLogitudeAndLatitude(String longitudeAndLatitude) {
        String latitude = longitudeAndLatitude.split(",")[0];
        String longitude = longitudeAndLatitude.split(",")[1];
        longitudeMap.put("latitude", Double.valueOf(latitude));
        longitudeMap.put("longitude", Double.valueOf(longitude));
    }

    private static void updateDimensionPropertyValue(String key, Map<String, Object>
            taskProperties) {
        String dimensionTypeName = "";
        if (key.equalsIgnoreCase("constructionType")) {
            dimensionTypeName = SupervisionSolutionConstants
                    .DIMENSION_CONSTRUCTION_TYPE_WITH_PREFIX;
        } else if (key.equalsIgnoreCase("companyClassification")) {
            dimensionTypeName = SupervisionSolutionConstants
                    .DIMENSION_COMPANY_CLASSIFICATION_WITH_PREFIX;
        } else if (key.equalsIgnoreCase("assignModel")) {
            dimensionTypeName = SupervisionSolutionConstants
                    .DIMENSION_ASSIGN_MODEL_WITH_PREFIX;
        } else if (key.equalsIgnoreCase("issueClassification")) {
            dimensionTypeName = SupervisionSolutionConstants
                    .DIMENSION_ISSUE_CLASSIFICATION_WITH_PREFIX;
        } else if (key.equalsIgnoreCase("landProperty")) {
            dimensionTypeName = SupervisionSolutionConstants
                    .DIMENSION_LAND_PROPERTY_WITH_PREFIX;
        } else if (key.equalsIgnoreCase("assetFirstClassification")) {
            dimensionTypeName = SupervisionSolutionConstants
                    .DIMENSION_ASSET_FIRST_CLASSIFICATION_WITH_PREFIX;
        } else if (key.equalsIgnoreCase("assetSecondClassification")) {
            dimensionTypeName = SupervisionSolutionConstants
                    .DIMENSION_ASSET_SECOND_CLASSIFICATION_WITH_PREFIX;
        } else if (key.equalsIgnoreCase("projectClassification")) {
            dimensionTypeName = SupervisionSolutionConstants
                    .DIMENSION_PROJECT_CLASSIFICATION_WITH_PREFIX;
        } else if (key.equalsIgnoreCase("projectSiteClassification")) {
            dimensionTypeName = SupervisionSolutionConstants
                    .DIMENSION_PROJECT_SITE_CLASSIFICATION_WITH_PREFIX;
        } else if (key.equalsIgnoreCase("projectScope")) {
            dimensionTypeName = SupervisionSolutionConstants
                    .DIMENSION_PROJECT_SCOPE_WITH_PREFIX;
        } else if (key.equalsIgnoreCase("projectConstructionClassification")) {
            dimensionTypeName = SupervisionSolutionConstants
                    .DIMENSION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX;
        }

        List<String> values = SampleDimensionGenerator.dimensionCache.get
                (dimensionTypeName);
        if (values != null && values.size() > 0) {
            String propertyValue = values.get(RandomUtil.generateRandomInRange(0,
                    values.size()));
            taskProperties.put(key, propertyValue);
        }
    }


//    private static void setRandomLongitudeLatitude(Map<String, Object> properties) {
//        double longitude = SampleDataSet.LONGITUDE + RandomUtil
//                .generateRandomInRange(0, 999) / 100000d;
//        double latitude = SampleDataSet.LATITUDE + +RandomUtil
//                .generateRandomInRange(0, 999) / 100000d;
//
//        properties.put("longitude", longitude);
//        properties.put("latitude", latitude);
//    }

    public static String[] getTasksList(String projectType) {
        if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_MAINTENANCE)) {
            return SampleDataSet.TASKS_OF_MAINTENANCE_PROJECT;
        } else {
            return SampleDataSet.TASKS_OF_NEW_PROJECT;
        }
    }

    public static String[] getDepartmentIdList(String projectType) {

        if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_MAINTENANCE)) {
            return SampleDataSet.DEPARTMENTS_OF_MAINTENANCE_PROJECT;
        } else {
            return SampleDataSet.DEPARTMENTS_OF_NEW_PROJECT;
        }
    }

    public static String[] getDepartmentNameList(String projectType) {

        if (projectType.equalsIgnoreCase(SampleDataSet.PROJECTTYPE_MAINTENANCE)) {
            return SampleDataSet.DEPARTMENT_NAMES_OF_MAINTENANCE_PROJECT;
        } else {
            return SampleDataSet.DEPARTMENT_NAMES_OF_NEW_PROJECT;
        }
    }

    private static List<String> reservedStringPropertyNames() {
        List<String> reservedStringPropertyNames = new ArrayList<>();
        reservedStringPropertyNames.add(ClassificationConstants.CONSTRUCTION_TYPE);
        reservedStringPropertyNames.add(ClassificationConstants.COMPANY_CLASSIFICATION);
        reservedStringPropertyNames.add(ClassificationConstants.ASSIGN_MODEL);
        reservedStringPropertyNames.add(ClassificationConstants.EXECUTIVE_DEPARTMENT);
        reservedStringPropertyNames.add(ClassificationConstants.ISSUE_CLASSIFICATION);
        reservedStringPropertyNames.add(ClassificationConstants.LAND_PROPERTY);
        reservedStringPropertyNames.add(ClassificationConstants.ASSET_FIRST_CLASSIFICATION);
        reservedStringPropertyNames.add(ClassificationConstants.ASSET_SECOND_CLASSIFICATION);
        reservedStringPropertyNames.add(ClassificationConstants.PROJECT_CLASSIFICATION);
        reservedStringPropertyNames.add(ClassificationConstants.PROJECT_SITE_CLASSIFICATION);
        reservedStringPropertyNames.add(ClassificationConstants.PROJECT_SCOPE);
        reservedStringPropertyNames.add(ClassificationConstants
                .PROJECT_CONSTRUCTION_CLASSIFICATION);

        return reservedStringPropertyNames;
    }

    public static void main(String[] args) {
        String projectTemplate = SampleDataSet.FILE_MAINTENANCE_PROJECT;
        Map<String, Object>[] array = generateTasksRandomData(projectTemplate,
                SampleDataSet.PROJECTTYPE_MAINTENANCE,
                "maintenanceProject1",
                RandomData
                        .getRandomTime(2010, 2016, 0), 2);
//        logger.info("array.size: " + array.length);
        for (Map<String, Object> map : array) {
            System.out.println(map);
            logger.info("taskId: " + map.get("taskId"));
            logger.info("taskName: " + map.get("taskName"));
            logger.info("executiveDepartmentId: " + map.get(JsonConstants.EXECUTIVE_DEPARTMENT_ID));
            logger.info("EXECUTIVE_DEPARTMENT: " + map.get(JsonConstants.EXECUTIVE_DEPARTMENT));
            logger.info("workerId: " + map.get(JsonConstants.WORKER_ID));
            logger.info("worker: " + map.get(JsonConstants.WORKER));
            logger.info("startDate: " + map.get(JsonConstants.START_DATE));

            long date = ((Date) map.get(JsonConstants.START_DATE)).getTime();
            logger.info("startDate: " + date);
            DateTime time = DateUtil.getDateTime(date);
            logger.info("==: " + time.toString().substring(0, 10));
            logger.info("=================================================");
        }
    }
}
