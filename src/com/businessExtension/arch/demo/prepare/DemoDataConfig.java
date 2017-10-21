package com.businessExtension.arch.demo.prepare;

import com.businessExtension.arch.database.DatabaseConstants;
import com.businessExtension.arch.progress.constants.ProgressConstants;

/**
 * Created by sun.
 */
public class DemoDataConfig {

    public static final String DATABASENAME = DatabaseConstants.INFODISCOVER_SPACENAME;

    // json template path
    public static final String ROOT_PATH =
            "/Users/sun/InfoDiscovery/Code/DiscoverEngine_InfoDiscover/src/com/infoDiscover" +
                    "/solution/arch/demo/prepare/data/";
    public static final String FILE_ROLE =
            ROOT_PATH + "roles.csv";
    public static final String FILE_USER =
            ROOT_PATH + "users.csv";
    public static final String FILE_MAINTAIN_PROJECT =
            ROOT_PATH + "SampleAllData_MaintainProject.json";
    public static final String FILE_NEW_PROJECT =
            ROOT_PATH + "SampleAllData_NewProject.json";

    // 生成年份
    public static final int[] yearsToGenerate = new int[]{2010, 2011, 2012, 2013, 2014,
            2015, 2016, 2017, 2018};

    // 只生成年、月、日的时间维度
    public static final int depth = 3;

    //生成多少个maintain project
    public static final int countOfMaintainProgressToGenerate = 10;

    // 生成多少个new project
    public static final int countOfNewProgressToGenerate = 10;

    // 随机完成流程中的前几个任务, false表示完成全部任务
    public static final boolean toGenerateRandomTasksNumber = false;

    public static final String PROJECTTYPE_MAINTAIN = "maintain";
    public static final String PROJECTTYPE_NEW = "new";
    public static final String PROJECTNAME_MAINTAIN = "维修工程";
    public static final String PROJECTNAME_NEW = "新建工程";

    public static final String FACTTYPE_MAINTAIN_PROJECT = ProgressConstants.FACT_PROGRESS_WITHPREFIX +
            "_MAINTAIN";
    public static final String FACTTYPE_NEW_PROJECT = ProgressConstants.FACT_PROGRESS_WITHPREFIX +
            "_NEWPROJECT";

    public static final String[] MAINTAIN_TASKS = {"WeiXiuShenqing", "ProjectStart", "KanchaBaojia",
            "zaojiahetongshenpi", "zaojiazixunqianding", "yusuanzaojia", "yusuanzaojiashenpi",
            "shigongdanweiqianyue", "shigong", "jungongyanshou", "ProjectEnd"};

    public static final String[] NEWPROJECT_TASKS = {"LaunchMaintainProject", "ProjectStart",
            "projectDevelopmentCompany", "necessaryDataProcessing", "SurveyResults",
            "TenderAgentSigned", "InvestigationCompanySigned", "DesignCompanySigned",
            "DesignCompanyResults", "InvestmentEstimates", "BudgetApproval",
            "CostConsultingCompanySigned", "BudgetCost", "BudgetCostApproval",
            "BonstructionCompanySigned", "Construction", "MonthProgressReport",
            "CompletionAcceptance", "ProjectClosure"};

    public static final String[] DEPARTMENTS_MAINTAIN_PROJECT = {"Property_Department",
            "Property_Department",
            "Engineering_Department", "Area_Chief_Financial_Officer",
            "General_Manager", "Engineering_Department",
            "Engineering_Department", "Engineering_Department",
            "Engineering_Department", "Engineering_Department", "Engineering_Department"};

    public static final String[] DEPARTMENTS_NEWPROJECT = {"BuildingAndEnvironment_Bureau",
            "Property_Department",
            "General_Manager", "Engineering_Department",
            "Engineering_Department", "General_Manager",
            "General_Manager", "General_Manager",
            "Engineering_Department", "Engineering_Department", "BuildingAndEnvironment_Bureau",
            "General_Manager", "CostControl_Department", "Finance_Bureau", "General_Manager",
            "Engineering_Department", "Engineering_Department", "Engineering_Department",
            "General_Manager"};


    public static final String[] DEPARTMENTS = {"Proerty_Department",
            "Engineering_Department",
            "CostControl_Department", "Finance_Department",
            "Management_LeadingGroup", "Area_Chief_Financial_Officer",
            "Legal_Specialist", "Finance_Specialist",
            "PartyAndGovernment_Office", "OverallPlanAndDevelopment_Bureau",
            "BuildingAndEnvironment_Bureau", "Finance_Bureau", "LandAndResources_Bureau",
            "Planning_Bureau", "Area_SuperiorLeader", "General_Manager"};

//    public static final String[] ADDRESSES = {"北京海淀区中关村", "北京海淀区清华大学", "北京朝阳区日坛国际酒店", "北京朝阳区东直门",
//            "北京朝阳区西直门",
//            "北京鸟巢", "北京通州司空小区"};
//
//    public static final String[] LONGITUDE_LATITUDE = {"39.9910020000,116.3288960000",
//            "40.0092060000," +
//            "116.3332660000", "39.9260050000,116.4495730000", "39.9639990000,116.4600810000",
//            "39.8897660000,116.4803010000", "40.0001090000,116.4038930000", "39.9161950000," +
//            "116.6635560000"};

    public static final double LONGITUDE = 39.9910020000d;
    public static final double LATITUDE = 116.3288960000d;


    public static final String[] wentifenleiList = {"问题分类1", "问题分类2", "问题分类3", "问题分类4", "问题分类5"};
    public static final String[] didianfenleiList = {"维修地点分类1", "维修地点分类2", "维修地点分类3", "维修地点分类4",
            "维修地点分类5", "维修地点分类6"};
    public static final String[] gongzhongfenleiList = {"工种分类1", "工种分类2", "工种分类3", "工种分类4",
            "工种分类5"};
    public static final String[] buweifenleiList = {"维修部位分类1", "维修部位分类2", "维修部位分类3", "维修部位分类4",
            "维修部位分类5"};
    public static final String[] projectTypeList = {"项目分类1", "项目分类2", "项目分类3", "项目分类4",
            "项目分类5", "项目分类6", "项目分类7", "项目分类8"};

    public static final int[] weixiubaojiaList = {10000, 20000, 30000, 40000, 50000, 60000,
            70000, 80000, 90000};
}
