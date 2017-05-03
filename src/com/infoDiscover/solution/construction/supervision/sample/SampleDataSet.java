package com.infoDiscover.solution.construction.supervision.sample;

import com.infoDiscover.solution.arch.progress.constants.ProgressConstants;

/**
 * Created by sun.
 */
public class SampleDataSet {

    public final static String PROJECTTYPE_MAINTENANCE = "maintain";
    public final static String PROJECTTYPE_NEW = "new";
    public final static String PROJECTNAME_MAINTANENCE = "维修工程";
    public final static String PROJECTNAME_NEW = "新建工程";
    public final static String PROJECTNAME_REBUILD = "改建工程";
    public final static String PROJECTTYPE_REBUILD = "改建工程";
    public final static String PROJECTNAME_EXTENSION = "扩建工程";
    public final static String PROJECTTYPE_EXTENSION = "扩建工程";

    public final static String FACTTYPE_MAINTENANCE_PROJECT = ProgressConstants
            .FACT_PROGRESS_WITHPREFIX +
            "_MAINTAIN";
    public final static String FACTTYPE_NEW_PROJECT = ProgressConstants.FACT_PROGRESS_WITHPREFIX +
            "_NEWPROJECT";

    public final static String[] TASKS_OF_MAINTENANCE = {
            "applyProjectMaintenance",
            "launchProject",
            "survey",
            "quote",
            "designContract",
            "design",
            "costConsultationContract",
            "costConsultation",
            "projectBudgetCostApproval",
            "constructionContract",
            "supervisionContract",
            "constructionResult",
            "projectFinalAcceptance",
            "qualityAssurance",
            "closeProject"
    };

    public final static String[] TASKS_OF_NEW = {
            "startProject",
            "launchProject",
            "biddingAgencyContract",
            "earlyStageConsultationContract",
            "feasibilityReport",
            "engineeringNecessaryInformation",
            "surveyCompanyContract",
            "surveyResult",
            "designContract",
            "design",
            "investmentBudgetaryEstimate",
            "costConsultationContract",
            "costConsultation",
            "projectBudgetCostApproval",
            "constructionContract",
            "supervisionContract",
            "construction",
            "monthlyProgressReport",
            "projectFinalAcceptance",
            "qualityAssurance",
            "closeProject"
    };


    public final static String[] DEPARTMENTS_OF_MAINTENANCE = {"Property_Department",
            "Property_Department",
            "Engineering_Department", "Area_Chief_Financial_Officer",
            "General_Manager", "Engineering_Department",
            "Engineering_Department", "Engineering_Department",
            "Engineering_Department", "Engineering_Department", "Engineering_Department"};

    public final static String[] DEPARTMENTS_OF_NEW = {"BuildingAndEnvironment_Bureau",
            "Property_Department",
            "General_Manager", "Engineering_Department",
            "Engineering_Department", "General_Manager",
            "General_Manager", "General_Manager",
            "Engineering_Department", "Engineering_Department", "BuildingAndEnvironment_Bureau",
            "General_Manager", "CostControl_Department", "Finance_Bureau", "General_Manager",
            "Engineering_Department", "Engineering_Department", "Engineering_Department",
            "General_Manager"};

    public final static String[] DEPARTMENTS = {"Property_Department",
            "Engineering_Department",
            "CostControl_Department", "Finance_Department",
            "Management_LeadingGroup", "Area_Chief_Financial_Officer",
            "Legal_Specialist", "Finance_Specialist",
            "PartyAndGovernment_Office", "OverallPlanAndDevelopment_Bureau",
            "BuildingAndEnvironment_Bureau", "Finance_Bureau", "LandAndResources_Bureau",
            "Planning_Bureau", "Area_SuperiorLeader", "General_Manager"};

    public final static double LONGITUDE = 39.9910020000d;
    public final static double LATITUDE = 116.3288960000d;


    // json template path
    public final static String ROOT_PATH =
            "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover" +
                    "/solution/construction/supervision/template/";
    public final static String FILE_ROLE = ROOT_PATH + "/data/roles.csv";
    public final static String FILE_USER = ROOT_PATH + "/data/users.csv";
    public final static String FILE_MAINTENANCE_PROJECT = ROOT_PATH + "allMaintenanceProject.json";
    public final static String FILE_NEW_PROJECT = ROOT_PATH + "allNewProject.json";

    public final static String FILE_DIMENSION_CONSTRUCTION_TYPE = ROOT_PATH +
            "/data/主要功能或建筑类型_constructionType.csv";
    public final static String FILE_DIMENSION_COMPANY_CLASSIFICATION = ROOT_PATH +
            "/dta/公司分类_companyClassification.csv";
    public final static String FILE_DIMENSION_ASSIGN_MODEL = ROOT_PATH +
            "/data/实施单位分派模式_assignModel.csv";
    public final static String FILE_DIMENSION_EXECUTIVE_DEPARTMENT = ROOT_PATH +
            "/data/执行部门_executiveDepartment.csv";
    public final static String FILE_DIMENSION_GOVERNMENT_APPROVAL_AUTHORITY = ROOT_PATH +
            "/data/政府审批主管部门_GovernmentApprovalAuthority.csv";
    public final static String FILE_DIMENSION_ISSUE_CLASSIFICATION = ROOT_PATH +
            "/data/故障问题分类_issueClassification.csv";
    public final static String FILE_DIMENSION_LAND_PROPERTY = ROOT_PATH +
            "/data/用地性质_landProperty.csv";
    public final static String FILE_DIMENSION_ASSET_FIRST_CLASSIFICATION = ROOT_PATH +
            "/data/资产一级分类_assetFirstClassification.csv";
    public final static String FILE_DIMENSION_ASSET_SECOND_CLASSIFICATION = ROOT_PATH +
            "/data/资产二级分类_assetSecondClassification.csv";
    public final static String FILE_DIMENSION_PROJECT_CLASSIFICATION = ROOT_PATH +
            "/data/项目专业分类_projectClassification.csv";
    public final static String FILE_DIMENSION_PROJECT_SITE_CLASSIFICATION = ROOT_PATH +
            "/data/项目实施地点分类_projectSiteClassification.csv";
    public final static String FILE_DIMENSION_PROJECT_SCOPE = ROOT_PATH +
            "/data/项目实施范围_projectScope.csv";
    public final static String FILE_DIMENSION_PROJECT_CONSTRUCTION_CLASSIFICATION = ROOT_PATH +
            "/data/项目建设分类_projectConstructionClassification.csv";
}
