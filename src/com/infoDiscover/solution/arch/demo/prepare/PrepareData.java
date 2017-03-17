package com.infoDiscover.solution.arch.demo.prepare;

import com.infoDiscover.common.PrefixConstant;
import com.infoDiscover.common.dimension.time.TimeDimensionGenerator;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.arch.demo.prepare.progress.ProgressDemoDataGenerator;
import com.infoDiscover.solution.arch.progress.manager.ProgressInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by sun.
 */
public class PrepareData {
    private final static Logger logger = LogManager.getLogger(PrepareData.class);

    public static void main(String[] args) {
        prepareData(DemoDataConfig.FILE_USER, DemoDataConfig.FILE_ROLE);

        ProgressDemoDataGenerator.generateMaintainProjectDemoData
                (DemoDataConfig.countOfMaintainProgressToGenerate, DemoDataConfig
                        .toGenerateRandomTasksNumber);

        ProgressDemoDataGenerator.generateNewProjectDemoData(DemoDataConfig
                .countOfNewProgressToGenerate, DemoDataConfig.toGenerateRandomTasksNumber);
    }

    public static void prepareData(String userFile, String roleFile) {
        logger.debug("Start to prepare data");

        logger.debug("Step 1: create demo database");
        if (DiscoverEngineComponentFactory.checkDiscoverSpaceExistence(DemoDataConfig
                .DATABASENAME)) {
            logger.debug("Database: " + DemoDataConfig.DATABASENAME + " is already existed, " +
                    "please specify " +
                    "another " +
                    "one");
            System.exit(0);
        }
        boolean created = DiscoverEngineComponentFactory.createInfoDiscoverSpace(DemoDataConfig.DATABASENAME);
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
        TimeDimensionGenerator.generateYears(PrefixConstant.prefixWithout, DemoDataConfig
                .yearsToGenerate, DemoDataConfig.depth);
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
