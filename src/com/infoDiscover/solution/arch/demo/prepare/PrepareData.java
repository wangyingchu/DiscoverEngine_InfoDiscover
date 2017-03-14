package com.infoDiscover.solution.arch.demo.prepare;

import com.infoDiscover.common.PrefixConstant;
import com.infoDiscover.common.dimension.time.TimeDimensionGenerator;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.arch.database.DatabaseConstants;
import com.infoDiscover.solution.arch.demo.prepare.maintainproject
        .MaintainProgressDemoDataGenerator;
import com.infoDiscover.solution.arch.progress.manager.ProgressInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by sun.
 */
public class PrepareData {
    private final static Logger logger = LogManager.getLogger(PrepareData.class);

    //===========================可以修改一下参数，进行配置 ==============//
    static String userFile = "users.csv";
    static String roleFile = "roles.csv";
    static String maintainProjectTemplateFile = "SampleAllData.json";


    static String database = DatabaseConstants.INFODISCOVER_SPACENAME;

    // 生成年份
    static int[] yearsToGenerate = new int[]{2010, 2011, 2012, 2013, 2014,
            2015, 2016, 2017, 2018, 2019, 2020};

    // 只生成年、月、日的时间维度
    static int depth = 3;

    //生成多少个maintain project
    static int countOfProgressToGenerate = 100;

    // 随机完成流程中的前几个任务, false表示完成全部任务
    static boolean toGenerateRandomTasksNumber = true;

    public static void main(String[] args) {
        prepareData(userFile, roleFile);

        MaintainProgressDemoDataGenerator.generateMainProjectDemoData
                (maintainProjectTemplateFile, roleFile, countOfProgressToGenerate,
                        toGenerateRandomTasksNumber);
    }

    public static void prepareData(String userFile, String roleFile) {
        logger.debug("Start to prepare data");

        logger.debug("Step 1: create demo database");
        if (DiscoverEngineComponentFactory.checkDiscoverSpaceExistence(database)) {
            logger.debug("Database: " + database + " is already existed, please specify another " +
                    "one");
            System.exit(0);
        }
        boolean created = DiscoverEngineComponentFactory.createInfoDiscoverSpace(DatabaseConstants
                .INFODISCOVER_SPACENAME);
        logger.debug("Step 1: end to create demo database: " + created);

        if (!created) {
            logger.error("Failed to create demo database");
            System.exit(0);
        }

        logger.debug("Step 2: initialize time dimension type");
        try {
            TimeDimensionGenerator.initTimeDimensionType(PrefixConstant.prefixWithout);
            logger.debug("Step 2: end to initialize time dimension type with prefix: " +
                    PrefixConstant.prefixWithout);
        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.error("Failed to initialize time dimension type");
        }

        logger.debug("Step 3: generate the specified years");
        TimeDimensionGenerator.generateYears(PrefixConstant.prefixWithout, yearsToGenerate, depth);
        logger.debug("Step 3: end to generate the specified years: " + "{2010, 2011, 2012, 2013, " +
                "2014, 2015, 2016, 2017}");

        logger.debug("Step 4: initialize the progress type");
        try {
            ProgressInitializer.initProgressFactType("");
            logger.debug("Step 4: end to initialize the progress type");
        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.error("Failed to initialize the progress type");
        }

        logger.debug("Step 5: initialize the progress relation type");
        try {
            ProgressInitializer.initProgressRelationType("");
            logger.debug("Step 5: initialize the progress relation type");
        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.debug("Failed to initialize the relation type");
        }

        logger.debug("Step 6: import user and role demo data");

        try {
            UserRoleDataImporter.createUsers(userFile);
            UserRoleDataImporter.createRoles(roleFile);
            logger.debug("Step 6: import user and role demo data");
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.error("Step 6: Failed to import user and role demo data");
        }

        logger.debug("End to prepare data");
    }
}
