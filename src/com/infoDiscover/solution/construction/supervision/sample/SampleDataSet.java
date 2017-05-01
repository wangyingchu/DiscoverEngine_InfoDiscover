package com.infoDiscover.solution.construction.supervision.sample;

/**
 * Created by sun.
 */
public class SampleDataSet {

    public final static String PROJECTTYPE_MAINTENANCE = "maintain";
    public final static String PROJECTTYPE_NEW = "new";
    public final static String PROJECTNAME_MAINTANENCE = "维修工程";
    public final static String PROJECTNAME_NEW = "新建工程";

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
            "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover/solution/construction/supervision/template/";
    public final static String FILE_ROLE =
            ROOT_PATH + "roles.csv";
    public final static String FILE_USER =
            ROOT_PATH + "users.csv";
    public final static String FILE_MAINTENANCE_PROJECT =
            ROOT_PATH + "allMaintenanceProject.json";
    public final static String FILE_NEW_PROJECT =
            ROOT_PATH + "allNewProject.json";
}
