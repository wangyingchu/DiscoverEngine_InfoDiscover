package com.infoDiscover.solution.construction.supervision.sample;

import com.infoDiscover.solution.construction.supervision.database.SupervisionSolutionConstants;

/**
 * Created by sun.
 */
public class SampleDataSet {

    public final static String PROJECTTYPE_MAINTENANCE = "维修工程";
    public final static String PROJECTNAME_MAINTANENCE = "维修工程";
    public final static String PROJECTTYPE_NEW = "新建工程";
    public final static String PROJECTNAME_NEW = "新建工程";
    public final static String PROJECTTYPE_REBUILD = "改建工程";
    public final static String PROJECTNAME_REBUILD = "改建工程";
    public final static String PROJECTTYPE_EXTENSION = "扩建工程";
    public final static String PROJECTNAME_EXTENSION = "扩建工程";

    public final static String FACTTYPE_MAINTENANCE_PROJECT = SupervisionSolutionConstants
            .FACT_PROGRESS_WITH_PREFIX + "_MAINTAIN_PROJECT";
    public final static String FACTTYPE_NEW_PROJECT = SupervisionSolutionConstants
            .FACT_PROGRESS_WITH_PREFIX + "_NEW_PROJECT";
    public final static String FACTTYPE_REBUILD_PROJECT = SupervisionSolutionConstants
            .FACT_PROGRESS_WITH_PREFIX + "_REBUILD_PROJECT";
    public final static String FACTTYPE_EXTENSION_PROJECT = SupervisionSolutionConstants
            .FACT_PROGRESS_WITH_PREFIX + "_EXTENSION_PROJECT";


    public final static String TASK1_OF_MAINTENANCE_PROJECT = "录入项目维修申请数据";
    public final static String TASK2_OF_MAINTENANCE_PROJECT = "录入项目启动及分派数据";
    public final static String TASK3_OF_MAINTENANCE_PROJECT = "录入现场勘察数据";
    public final static String TASK4_OF_MAINTENANCE_PROJECT = "录入报价数据";
    public final static String TASK5_OF_MAINTENANCE_PROJECT = "录入设计单位合同数据";
    public final static String TASK6_OF_MAINTENANCE_PROJECT = "录入设计成果数据";
    public final static String TASK7_OF_MAINTENANCE_PROJECT = "录入造价咨询合同数据";
    public final static String TASK8_OF_MAINTENANCE_PROJECT = "录入造价咨询单位成果数据";
    public final static String TASK9_OF_MAINTENANCE_PROJECT = "录入工程预算造价审批数据";
    public final static String TASK10_OF_MAINTENANCE_PROJECT = "录入施工合同数据";
    public final static String TASK11_OF_MAINTENANCE_PROJECT = "录入监理单位合同数据";
    public final static String TASK12_OF_MAINTENANCE_PROJECT = "录入工程施工数据";
    public final static String TASK13_OF_MAINTENANCE_PROJECT = "录入工程竣工验收数据";
    public final static String TASK14_OF_MAINTENANCE_PROJECT = "录入项目质保数据";
    public final static String TASK15_OF_MAINTENANCE_PROJECT = "项目关闭";

    public final static String[] TASKS_OF_MAINTENANCE_PROJECT = {
            TASK1_OF_MAINTENANCE_PROJECT, TASK2_OF_MAINTENANCE_PROJECT,
            TASK3_OF_MAINTENANCE_PROJECT, TASK4_OF_MAINTENANCE_PROJECT,
            TASK5_OF_MAINTENANCE_PROJECT, TASK6_OF_MAINTENANCE_PROJECT,
            TASK7_OF_MAINTENANCE_PROJECT, TASK8_OF_MAINTENANCE_PROJECT,
            TASK9_OF_MAINTENANCE_PROJECT, TASK10_OF_MAINTENANCE_PROJECT,
            TASK11_OF_MAINTENANCE_PROJECT, TASK12_OF_MAINTENANCE_PROJECT,
            TASK13_OF_MAINTENANCE_PROJECT, TASK14_OF_MAINTENANCE_PROJECT,
            TASK15_OF_MAINTENANCE_PROJECT
    };

    public final static String TASK1_OF_NEW_PROJECT = "录入项目发起数据";
    public final static String TASK2_OF_NEW_PROJECT = "录入项目启动及分派数据";
    public final static String TASK3_OF_NEW_PROJECT = "录入招标代理单位合同数据";
    public final static String TASK4_OF_NEW_PROJECT = "录入项目前期咨询单位合同数据";
    public final static String TASK5_OF_NEW_PROJECT = "录入可研数据";
    public final static String TASK6_OF_NEW_PROJECT = "录入工程必备资料数据";
    public final static String TASK7_OF_NEW_PROJECT = "录入勘察单位合同数据";
    public final static String TASK8_OF_NEW_PROJECT = "录入勘察单位成果数据";
    public final static String TASK9_OF_NEW_PROJECT = "录入设计单位合同数据";
    public final static String TASK10_OF_NEW_PROJECT = "录入设计成果数据";
    public final static String TASK11_OF_NEW_PROJECT = "录入投资概算数据";
    public final static String TASK12_OF_NEW_PROJECT = "录入造价咨询合同数据";
    public final static String TASK13_OF_NEW_PROJECT = "录入造价咨询单位成果数据";
    public final static String TASK14_OF_NEW_PROJECT = "录入工程预算造价审批数据";
    public final static String TASK15_OF_NEW_PROJECT = "录入施工合同数据";
    public final static String TASK16_OF_NEW_PROJECT = "录入监理单位合同数据";
    public final static String TASK17_OF_NEW_PROJECT = "录入工程施工数据";
    public final static String TASK18_OF_NEW_PROJECT = "录入月进度报告数据";
    public final static String TASK19_OF_NEW_PROJECT = "录入工程竣工验收数据";
    public final static String TASK20_OF_NEW_PROJECT = "录入项目质保数据";
    public final static String TASK21_OF_NEW_PROJECT = "项目关闭";

    public final static String[] TASKS_OF_NEW_PROJECT = {
            TASK1_OF_NEW_PROJECT, TASK2_OF_NEW_PROJECT, TASK3_OF_NEW_PROJECT,
            TASK4_OF_NEW_PROJECT, TASK5_OF_NEW_PROJECT, TASK6_OF_NEW_PROJECT,
            TASK7_OF_NEW_PROJECT, TASK8_OF_NEW_PROJECT, TASK9_OF_NEW_PROJECT,
            TASK10_OF_NEW_PROJECT, TASK11_OF_NEW_PROJECT, TASK12_OF_NEW_PROJECT,
            TASK13_OF_NEW_PROJECT, TASK14_OF_NEW_PROJECT, TASK15_OF_NEW_PROJECT,
            TASK16_OF_NEW_PROJECT, TASK17_OF_NEW_PROJECT, TASK18_OF_NEW_PROJECT,
            TASK19_OF_NEW_PROJECT, TASK20_OF_NEW_PROJECT, TASK21_OF_NEW_PROJECT
    };

    public final static String[] DEPARTMENTS_TO_ASSIGN_TASK = {
            "PartyAndGovernment_Office",
            "BuildingAndEnvironment_Bureau",
            "OverallPlanAndDevelopment_Bureau"};

    public final static String[] DEPARTMENT_NAMES_TO_ASSIGN_TASK = {
            "党政办",
            "建环局",
            "统发局"};


    public final static String DEPARTMENT_OF_TASK1_OF_MAINTENANCE_PROJECT =
            "DEPARTMENT_TO_ASSIGN_TASK";
    public final static String DEPARTMENT_OF_TASK2_OF_MAINTENANCE_PROJECT = "Property_Department";
    public final static String DEPARTMENT_OF_TASK3_OF_MAINTENANCE_PROJECT =
            "Engineering_Department";
    public final static String DEPARTMENT_OF_TASK4_OF_MAINTENANCE_PROJECT = "Property_Department";
    public final static String DEPARTMENT_OF_TASK5_OF_MAINTENANCE_PROJECT = "Cost_Department";
    public final static String DEPARTMENT_OF_TASK6_OF_MAINTENANCE_PROJECT =
            "Engineering_Department";
    public final static String DEPARTMENT_OF_TASK7_OF_MAINTENANCE_PROJECT = "Cost_Department";
    public final static String DEPARTMENT_OF_TASK8_OF_MAINTENANCE_PROJECT =
            "Engineering_Department";
    public final static String DEPARTMENT_OF_TASK9_OF_MAINTENANCE_PROJECT = "GeneralManager";
    public final static String DEPARTMENT_OF_TASK10_OF_MAINTENANCE_PROJECT = "Cost_Department";
    public final static String DEPARTMENT_OF_TASK11_OF_MAINTENANCE_PROJECT = "Cost_Department";
    public final static String DEPARTMENT_OF_TASK12_OF_MAINTENANCE_PROJECT =
            "Engineering_Department";
    public final static String DEPARTMENT_OF_TASK13_OF_MAINTENANCE_PROJECT =
            "Engineering_Department";
    public final static String DEPARTMENT_OF_TASK14_OF_MAINTENANCE_PROJECT =
            "Engineering_Department";
    public final static String DEPARTMENT_OF_TASK15_OF_MAINTENANCE_PROJECT = "GeneralManager";

    public final static String[] DEPARTMENTS_OF_MAINTENANCE_PROJECT = {
            DEPARTMENT_OF_TASK1_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_OF_TASK2_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_OF_TASK3_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_OF_TASK4_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_OF_TASK5_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_OF_TASK6_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_OF_TASK7_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_OF_TASK8_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_OF_TASK9_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_OF_TASK10_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_OF_TASK11_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_OF_TASK12_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_OF_TASK13_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_OF_TASK14_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_OF_TASK15_OF_MAINTENANCE_PROJECT};

    public final static String DEPARTMENT_NAME_OF_TASK1_OF_MAINTENANCE_PROJECT = "任务下达局室";
    public final static String DEPARTMENT_NAME_OF_TASK2_OF_MAINTENANCE_PROJECT = "物业部";
    public final static String DEPARTMENT_NAME_OF_TASK3_OF_MAINTENANCE_PROJECT = "工程部";
    public final static String DEPARTMENT_NAME_OF_TASK4_OF_MAINTENANCE_PROJECT = "物业部";
    public final static String DEPARTMENT_NAME_OF_TASK5_OF_MAINTENANCE_PROJECT = "成本部";
    public final static String DEPARTMENT_NAME_OF_TASK6_OF_MAINTENANCE_PROJECT = "工程部";
    public final static String DEPARTMENT_NAME_OF_TASK7_OF_MAINTENANCE_PROJECT = "成本部";
    public final static String DEPARTMENT_NAME_OF_TASK8_OF_MAINTENANCE_PROJECT = "工程部";
    public final static String DEPARTMENT_NAME_OF_TASK9_OF_MAINTENANCE_PROJECT = "总经理";
    public final static String DEPARTMENT_NAME_OF_TASK10_OF_MAINTENANCE_PROJECT = "成本部";
    public final static String DEPARTMENT_NAME_OF_TASK11_OF_MAINTENANCE_PROJECT = "成本部";
    public final static String DEPARTMENT_NAME_OF_TASK12_OF_MAINTENANCE_PROJECT = "工程部";
    public final static String DEPARTMENT_NAME_OF_TASK13_OF_MAINTENANCE_PROJECT = "工程部";
    public final static String DEPARTMENT_NAME_OF_TASK14_OF_MAINTENANCE_PROJECT = "工程部";
    public final static String DEPARTMENT_NAME_OF_TASK15_OF_MAINTENANCE_PROJECT = "总经理";

    public final static String[] DEPARTMENT_NAMES_OF_MAINTENANCE_PROJECT = {
            DEPARTMENT_NAME_OF_TASK1_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_NAME_OF_TASK2_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_NAME_OF_TASK3_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_NAME_OF_TASK4_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_NAME_OF_TASK5_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_NAME_OF_TASK6_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_NAME_OF_TASK7_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_NAME_OF_TASK8_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_NAME_OF_TASK9_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_NAME_OF_TASK10_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_NAME_OF_TASK11_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_NAME_OF_TASK12_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_NAME_OF_TASK13_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_NAME_OF_TASK14_OF_MAINTENANCE_PROJECT,
            DEPARTMENT_NAME_OF_TASK15_OF_MAINTENANCE_PROJECT};


    public final static String DEPARTMENT_OF_TASK1_OF_NEW_PROJECT =
            "DEPARTMENT_TO_ASSIGN_TASK";
    public final static String DEPARTMENT_OF_TASK2_OF_NEW_PROJECT =
            "ComprehensiveManagementDepartment";
    public final static String DEPARTMENT_OF_TASK3_OF_NEW_PROJECT =
            "Cost_Department";
    public final static String DEPARTMENT_OF_TASK4_OF_NEW_PROJECT = "Cost_Department";
    public final static String DEPARTMENT_OF_TASK5_OF_NEW_PROJECT = "Cost_Department";
    public final static String DEPARTMENT_OF_TASK6_OF_NEW_PROJECT =
            "Engineering_Department";
    public final static String DEPARTMENT_OF_TASK7_OF_NEW_PROJECT = "Cost_Department";
    public final static String DEPARTMENT_OF_TASK8_OF_NEW_PROJECT =
            "Engineering_Department";
    public final static String DEPARTMENT_OF_TASK9_OF_NEW_PROJECT = "Engineering_Department";
    public final static String DEPARTMENT_OF_TASK10_OF_NEW_PROJECT = "Engineering_Department";
    public final static String DEPARTMENT_OF_TASK11_OF_NEW_PROJECT = "Cost_Department";
    public final static String DEPARTMENT_OF_TASK12_OF_NEW_PROJECT =
            "Cost_Department";
    public final static String DEPARTMENT_OF_TASK13_OF_NEW_PROJECT =
            "Cost_Department";
    public final static String DEPARTMENT_OF_TASK14_OF_NEW_PROJECT =
            "Finance_Department";
    public final static String DEPARTMENT_OF_TASK15_OF_NEW_PROJECT = "Cost_Department";
    public final static String DEPARTMENT_OF_TASK16_OF_NEW_PROJECT = "Engineering_Department";
    public final static String DEPARTMENT_OF_TASK17_OF_NEW_PROJECT = "Engineering_Department";
    public final static String DEPARTMENT_OF_TASK18_OF_NEW_PROJECT =
            "Engineering_Department";
    public final static String DEPARTMENT_OF_TASK19_OF_NEW_PROJECT =
            "Engineering_Department";
    public final static String DEPARTMENT_OF_TASK20_OF_NEW_PROJECT =
            "Engineering_Department";
    public final static String DEPARTMENT_OF_TASK21_OF_NEW_PROJECT = "GeneralManager";


    public final static String DEPARTMENT_NAME_OF_TASK1_OF_NEW_PROJECT = "任务下达局室";
    public final static String DEPARTMENT_NAME_OF_TASK2_OF_NEW_PROJECT = "综合管理部";
    public final static String DEPARTMENT_NAME_OF_TASK3_OF_NEW_PROJECT = "成本部";
    public final static String DEPARTMENT_NAME_OF_TASK4_OF_NEW_PROJECT = "成本部";
    public final static String DEPARTMENT_NAME_OF_TASK5_OF_NEW_PROJECT = "成本部";
    public final static String DEPARTMENT_NAME_OF_TASK6_OF_NEW_PROJECT = "工程部";
    public final static String DEPARTMENT_NAME_OF_TASK7_OF_NEW_PROJECT = "成本部";
    public final static String DEPARTMENT_NAME_OF_TASK8_OF_NEW_PROJECT = "工程部";
    public final static String DEPARTMENT_NAME_OF_TASK9_OF_NEW_PROJECT = "工程部";
    public final static String DEPARTMENT_NAME_OF_TASK10_OF_NEW_PROJECT = "工程部";
    public final static String DEPARTMENT_NAME_OF_TASK11_OF_NEW_PROJECT = "成本部";
    public final static String DEPARTMENT_NAME_OF_TASK12_OF_NEW_PROJECT = "成本部";
    public final static String DEPARTMENT_NAME_OF_TASK13_OF_NEW_PROJECT = "成本部";
    public final static String DEPARTMENT_NAME_OF_TASK14_OF_NEW_PROJECT = "财政局";
    public final static String DEPARTMENT_NAME_OF_TASK15_OF_NEW_PROJECT = "成本部";
    public final static String DEPARTMENT_NAME_OF_TASK16_OF_NEW_PROJECT = "工程部";
    public final static String DEPARTMENT_NAME_OF_TASK17_OF_NEW_PROJECT = "工程部";
    public final static String DEPARTMENT_NAME_OF_TASK18_OF_NEW_PROJECT = "工程部";
    public final static String DEPARTMENT_NAME_OF_TASK19_OF_NEW_PROJECT = "工程部";
    public final static String DEPARTMENT_NAME_OF_TASK20_OF_NEW_PROJECT = "工程部";
    public final static String DEPARTMENT_NAME_OF_TASK21_OF_NEW_PROJECT = "总经理";

    public final static String[] DEPARTMENTS_OF_NEW_PROJECT = {
            DEPARTMENT_OF_TASK1_OF_NEW_PROJECT, DEPARTMENT_OF_TASK2_OF_NEW_PROJECT,
            DEPARTMENT_OF_TASK3_OF_NEW_PROJECT, DEPARTMENT_OF_TASK4_OF_NEW_PROJECT,
            DEPARTMENT_OF_TASK5_OF_NEW_PROJECT, DEPARTMENT_OF_TASK6_OF_NEW_PROJECT,
            DEPARTMENT_OF_TASK7_OF_NEW_PROJECT, DEPARTMENT_OF_TASK8_OF_NEW_PROJECT,
            DEPARTMENT_OF_TASK9_OF_NEW_PROJECT, DEPARTMENT_OF_TASK10_OF_NEW_PROJECT,
            DEPARTMENT_OF_TASK11_OF_NEW_PROJECT, DEPARTMENT_OF_TASK12_OF_NEW_PROJECT,
            DEPARTMENT_OF_TASK13_OF_NEW_PROJECT, DEPARTMENT_OF_TASK14_OF_NEW_PROJECT,
            DEPARTMENT_OF_TASK15_OF_NEW_PROJECT, DEPARTMENT_OF_TASK16_OF_NEW_PROJECT,
            DEPARTMENT_OF_TASK17_OF_NEW_PROJECT, DEPARTMENT_OF_TASK18_OF_NEW_PROJECT,
            DEPARTMENT_OF_TASK19_OF_NEW_PROJECT,DEPARTMENT_OF_TASK20_OF_NEW_PROJECT,
            DEPARTMENT_OF_TASK21_OF_NEW_PROJECT};

    public final static String[] DEPARTMENT_NAMES_OF_NEW_PROJECT = {
            DEPARTMENT_NAME_OF_TASK1_OF_NEW_PROJECT, DEPARTMENT_NAME_OF_TASK2_OF_NEW_PROJECT,
            DEPARTMENT_NAME_OF_TASK3_OF_NEW_PROJECT, DEPARTMENT_NAME_OF_TASK4_OF_NEW_PROJECT,
            DEPARTMENT_NAME_OF_TASK5_OF_NEW_PROJECT, DEPARTMENT_NAME_OF_TASK6_OF_NEW_PROJECT,
            DEPARTMENT_NAME_OF_TASK7_OF_NEW_PROJECT, DEPARTMENT_NAME_OF_TASK8_OF_NEW_PROJECT,
            DEPARTMENT_NAME_OF_TASK9_OF_NEW_PROJECT, DEPARTMENT_NAME_OF_TASK10_OF_NEW_PROJECT,
            DEPARTMENT_NAME_OF_TASK11_OF_NEW_PROJECT, DEPARTMENT_NAME_OF_TASK12_OF_NEW_PROJECT,
            DEPARTMENT_NAME_OF_TASK13_OF_NEW_PROJECT, DEPARTMENT_NAME_OF_TASK14_OF_NEW_PROJECT,
            DEPARTMENT_NAME_OF_TASK15_OF_NEW_PROJECT, DEPARTMENT_NAME_OF_TASK16_OF_NEW_PROJECT,
            DEPARTMENT_NAME_OF_TASK17_OF_NEW_PROJECT, DEPARTMENT_NAME_OF_TASK18_OF_NEW_PROJECT,
            DEPARTMENT_NAME_OF_TASK19_OF_NEW_PROJECT,DEPARTMENT_NAME_OF_TASK20_OF_NEW_PROJECT,
            DEPARTMENT_NAME_OF_TASK21_OF_NEW_PROJECT};

    public final static String[] DEPARTMENTS = {
            "OverallPlanAndDevelopment_Bureau",
            "Planning_Bureau",
            "BuildingAndEnvironment_Bureau",
            "Finance_Department",
            "PartyAndGovernment_Office",
            "StateOwnedOffice",
            "ConstructionDevelopmentCompany",
            "PreProjectCompany",
            "ReconnaissanceCompany",
            "DesignCompany",
            "AuditCompany",
            "CostConsultingCompany",
            "BiddingAgency",
            "SupervisionCompany",
            "ConstructionCompany"};

    public final static double LONGITUDE = 39.9910020000d;
    public final static double LATITUDE = 116.3288960000d;


    // json template path
    public final static String ROOT_PATH = PrepareSampleData.ROOT_PATH;
    public final static String FILE_DEPARTMENT = ROOT_PATH + "/data/departments.csv";
    public final static String FILE_USER = ROOT_PATH + "/data/users.csv";
    public final static String FILE_USER_DEPARTMENT = ROOT_PATH + "/data/user_department.csv";
    public final static String FILE_MAINTENANCE_PROJECT = ROOT_PATH + "/allMaintenanceProject.json";
    public final static String FILE_NEW_PROJECT = ROOT_PATH + "/allNewProject.json";
    public final static String FILE_EXTENSION_PROJECT = ROOT_PATH + "/allExtensionProject.json";
    public final static String FILE_REBUILD_PROJECT = ROOT_PATH + "/allRebuildProject.json";

    public final static String FILE_DIMENSION_CONSTRUCTION_TYPE = ROOT_PATH +
            "/data/dimension/主要功能或建筑类型_constructionType.csv";
    public final static String FILE_DIMENSION_COMPANY_CLASSIFICATION = ROOT_PATH +
            "/data/dimension/公司类别_companyClassification.csv";
    public final static String FILE_DIMENSION_ASSIGN_MODEL = ROOT_PATH +
            "/data/dimension/实施单位分派模式_assignModel.csv";
    public final static String FILE_DIMENSION_EXECUTIVE_DEPARTMENT = ROOT_PATH +
            "/data/dimension/执行部门_executiveDepartment.csv";
    public final static String FILE_DIMENSION_GOVERNMENT_APPROVAL_AUTHORITY = ROOT_PATH +
            "/data/dimension/政府审批主管部门_governmentApprovalAuthority.csv";
    public final static String FILE_DIMENSION_ISSUE_CLASSIFICATION = ROOT_PATH +
            "/data/dimension/故障问题分类_issueClassification.csv";
    public final static String FILE_DIMENSION_LAND_PROPERTY = ROOT_PATH +
            "/data/dimension/用地性质_landProperty.csv";
    public final static String FILE_DIMENSION_ASSET_FIRST_CLASSIFICATION = ROOT_PATH +
            "/data/dimension/资产一级分类_assetFirstClassification.csv";
    public final static String FILE_DIMENSION_ASSET_SECOND_CLASSIFICATION = ROOT_PATH +
            "/data/dimension/资产二级分类_assetSecondClassification.csv";
    public final static String FILE_DIMENSION_PROJECT_CLASSIFICATION = ROOT_PATH +
            "/data/dimension/项目专业分类_projectClassification.csv";
    public final static String FILE_DIMENSION_PROJECT_SITE_CLASSIFICATION = ROOT_PATH +
            "/data/dimension/项目实施地点分类_projectSiteClassification.csv";
    public final static String FILE_DIMENSION_PROJECT_SCOPE = ROOT_PATH +
            "/data/dimension/项目实施范围_projectScope.csv";
    public final static String FILE_DIMENSION_PROJECT_CONSTRUCTION_CLASSIFICATION = ROOT_PATH +
            "/data/dimension/项目建设分类_projectConstructionClassification.csv";
    public final static String FILE_DIMENSION_ROAD = ROOT_PATH +
            "/data/dimension/道路.txt";

    // other file
    public final static String FILE_STARTER = ROOT_PATH + "/data/starter.txt";
    public final static String FILE_ISSUE_REPORTER = ROOT_PATH + "/data/issueReporter.txt";
    public final static String FILE_ISSUE_DESCRIPTION = ROOT_PATH + "/data/issueDescription.txt";
    public final static String FILE_PROJECT_ADDRESS = ROOT_PATH + "/data/建筑-道路-坐标映射.txt";

    // 单位和负责人
    public final static String FILE_Reconnaissance_COMPANY = ROOT_PATH + "/data/勘察单位-user.txt";
    public final static String FILE_Consulting_COMPANY = ROOT_PATH + "/data/咨询单位-user.txt";
    public final static String FILE_BiddingAgent_COMPANY = ROOT_PATH + "/data/招标代理-user.txt";
    public final static String FILE_Construction_COMPANY = ROOT_PATH + "/data/施工单位-user.txt";
    public final static String FILE_Supervisor_COMPANY = ROOT_PATH + "/data/监理单位-user.txt";
    public final static String FILE_Design_COMPANY = ROOT_PATH + "/data/设计单位-user.txt";
    public final static String FILE_CostConsulting_COMPANY = ROOT_PATH + "/data/造价咨询单位-user.txt";
    public final static String FILE_BudgetEstimate_COMPANY = ROOT_PATH + "/data/概算审核单位-user.txt";


    public final static String FILE_PROJECT_LEADER = ROOT_PATH + "/data/项目负责人.txt";
    public final static String FILE_BUDGET_AUDIT_LEADER = ROOT_PATH + "/data/预算审核负责人.txt";
    public final static String FILE_BUDGET_APPROVE_LEADER = ROOT_PATH + "/data/预算审批负责人.txt";
    public final static String FILE_DESIGN_COMPANY_TECHNICAL_LEADER = ROOT_PATH +
            "/data/设计公司技术负责人.txt";
    public final static String FILE_SUPERVISION_TECHINICAL_LEADER = ROOT_PATH + "/data/监理公司技术负责人" +
            ".txt";
    public final static String FILE_SUPERVISION_CONTACT_AUDIT_LEADER = ROOT_PATH + "/data/监理合同审批人" +
            ".txt";
    public final static String FILE_SUPERVISION_CONTRACT_PHONE = ROOT_PATH + "/data/监理公司联系人电话.txt";

    public final static String FILE_SITE_INSPECTOR = ROOT_PATH + "/data/现场巡查人员.txt";
    public final static String FILE_SITE_INSPECT_PLACE = ROOT_PATH + "/data/现场巡查地点.txt";
    public final static String FILE_FINAL_ACCEPTANCE_MEMBERS = ROOT_PATH +
            "/data/验收人员名单.txt";

    public final static String FILE_BIDDING_AGENCY_CONTRACT_APPROVER = ROOT_PATH +
            "/data/招标代理合同审批人.txt";
    public final static String FILE_CONSULTING_CONTRACT_APPROVER = ROOT_PATH +
            "/data/咨询合同审批人.txt";
    public final static String FILE_FEASIBILITY_REPORT_APPROVER = ROOT_PATH +
            "/data/可研报告批复人.txt";
    public final static String FILE_SURVEY_CONTRACT_APPROVER = ROOT_PATH +
            "/data/勘察合同审批人.txt";
    public final static String FILE_DESIGN_CONTRACT_APPROVER = ROOT_PATH +
            "/data/设计合同审批人.txt";
    public final static String FILE_COST_CONSULTING_CONTRACT_APPROVER = ROOT_PATH +
            "/data/造价咨询合同审批人.txt";

    public final static String FILE_SURVEY_DESIGN_MEMBER = ROOT_PATH +
            "/data/勘察设计人员-user.txt";
    public final static String FILE_SURVEY_TECHNICAL_MEMBER = ROOT_PATH +
            "/data/勘察技术人员-user.txt";

    public final static String FILE_PROJECT_BUDGET_AUDIT_LEADER = ROOT_PATH +
            "/data/工程预算审核负责人.txt";
    public final static String FILE_PROJECT_BUDGET_APPROVER_LEADER = ROOT_PATH +
            "/data/工程预算审批负责人.txt";

    public final static String FILE_CONSTRUCTION_CONTACT = ROOT_PATH +
            "/data/施工单位联系人.txt";
    public final static String FILE_CONSTRUCTION_CONTRACT_APPROVER = ROOT_PATH +
            "/data/施工合同审批人.txt";
    public final static String FILE_FINAL_ACCEPTANCE_COMPANY = ROOT_PATH +
            "/data/验收单位.txt";



    public final static String FILE_MAINTENACE_PROJECT_TASKS = ROOT_PATH + "/data/维修工程-任务.txt";
    public final static String FILE_NEW_PROJECT_TASKS = ROOT_PATH + "/data/新建工程-任务.txt";


    public final static String[] dimensionArray = {"constructionType", "companyClassification",
            "assignModel", "executiveDepartment", "governmentApprovalAuthority",
            "issueClassification",
            "landProperty", "assetFirstClassification", "assetSecondClassification",
            "projectClassification", "projectSiteClassification", "projectScope",
            "projectConstructionClassification"};
}
