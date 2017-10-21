package com.businessExtension.arch.demo.prepare;

import com.infoDiscover.common.dimension.time.TimeDimensionGenerator;
import com.infoDiscover.infoDiscoverEngine.dataMart.FactType;
import com.infoDiscover.infoDiscoverEngine.dataMart.PropertyType;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.businessExtension.arch.database.DatabaseManager;
import com.businessExtension.arch.demo.prepare.progress.DemoArchProgressDemoDataGenerator;
import com.businessExtension.arch.progress.constants.ProgressConstants;
import com.businessExtension.arch.progress.manager.ProgressInitializer;
import com.businessExtension.arch.demo.UserRoleDataImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sun.
 */
public class PrepareData {
    private final static Logger logger = LoggerFactory.getLogger(PrepareData.class);

    public static final String prefix = "DEMO2_";

    public static void main(String[] args) {
        prepareData(DemoDataConfig.FILE_USER, DemoDataConfig.FILE_ROLE);

        DemoArchProgressDemoDataGenerator.generateMaintainProjectDemoData
                (DemoDataConfig.countOfMaintainProgressToGenerate, DemoDataConfig
                        .toGenerateRandomTasksNumber);

        DemoArchProgressDemoDataGenerator.generateNewProjectDemoData(DemoDataConfig
                .countOfNewProgressToGenerate, DemoDataConfig.toGenerateRandomTasksNumber);
    }

    public static void prepareData(String userFile, String roleFile) {
        logger.info("Start to prepare data");

        logger.info("Step 1: create demo database");
        if (DiscoverEngineComponentFactory.checkDiscoverSpaceExistence(DemoDataConfig
                .DATABASENAME)) {
            logger.error("Database: " + DemoDataConfig.DATABASENAME + " is already existed, " +
                    "please specify " +
                    "another " +
                    "one");
            System.exit(0);
        }
        boolean created = DiscoverEngineComponentFactory.createInfoDiscoverSpace(DemoDataConfig
                .DATABASENAME);
        logger.info("Step 1: end to create demo database: {}", created);

        if (!created) {
            logger.error("Failed to create demo database");
            System.exit(0);
        }

        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();

        logger.info("Step 2: initialize time dimension type");
        try {
            TimeDimensionGenerator.initTimeDimensionType(ids, prefix);
            logger.info("Step 2: end to initialize time dimension type with prefix: {}",
                    prefix);
        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.error("Failed to initialize time dimension type");
        }

        logger.info("Step 3: generate the specified years");
        TimeDimensionGenerator.generateYears(ids, prefix, DemoDataConfig
                .yearsToGenerate, DemoDataConfig.depth);
        logger.info("Step 3: end to generate the specified years: " + "{2010, 2011, 2012, 2013, " +
                "2014, 2015, 2016, 2017}");

        logger.info("Step 4: initialize the progress type");
        try {
            ProgressInitializer.initProgressFactType("");
            logger.debug("Step 4: end to initialize the progress type");
        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.error("Failed to initialize the progress type");
        }

        logger.info("Step 5: initialize the progress relation type");

        if (ids != null) {

            try {
                // progress fact type for maintain project
                FactType maintainProgressFactType = ids.addFactType(DemoDataConfig
                        .FACTTYPE_MAINTAIN_PROJECT);
                maintainProgressFactType.addTypeProperty("progressId", PropertyType.STRING);
                maintainProgressFactType.addTypeProperty("progressName", PropertyType.STRING);
                maintainProgressFactType.addTypeProperty("starter", PropertyType.STRING);
                maintainProgressFactType.addTypeProperty("startTime", PropertyType.DATE);
                maintainProgressFactType.addTypeProperty("endTime", PropertyType.DATE);
                maintainProgressFactType.addTypeProperty("status", PropertyType.STRING);

                // progress for new project
                FactType newProgressFactType = ids.addFactType(DemoDataConfig
                        .FACTTYPE_NEW_PROJECT);
                newProgressFactType.addTypeProperty("progressId", PropertyType.STRING);
                newProgressFactType.addTypeProperty("progressName", PropertyType.STRING);
                newProgressFactType.addTypeProperty("starter", PropertyType.STRING);
                newProgressFactType.addTypeProperty("startTime", PropertyType.DATE);
                newProgressFactType.addTypeProperty("endTime", PropertyType.DATE);
                newProgressFactType.addTypeProperty("status", PropertyType.STRING);

                // task fact type
                FactType taskFactType = ids.addFactType(ProgressConstants.FACT_TASK_WITHPREFIX);
                taskFactType.addTypeProperty("progressId", PropertyType.STRING);
                taskFactType.addTypeProperty("taskId", PropertyType.STRING);
                taskFactType.addTypeProperty("taskName", PropertyType.STRING);
                taskFactType.addTypeProperty("assignee", PropertyType.STRING);
                taskFactType.addTypeProperty("departmentId", PropertyType.STRING);
                taskFactType.addTypeProperty("startTime", PropertyType.DATE);
                taskFactType.addTypeProperty("endTime", PropertyType.DATE);


                // role dimension
                ids.addDimensionType(ProgressConstants.DIMENSION_ROLE_WITHPREFIX);

                // user dimension
                ids.addDimensionType(ProgressConstants.DIMENSION_USER_WITHPREFIX);
            } catch (InfoDiscoveryEngineDataMartException e) {
                logger.error(e.getMessage());
            } catch (InfoDiscoveryEngineRuntimeException e) {
                logger.error(e.getMessage());
            }
        }

        try {
            ProgressInitializer.initProgressRelationType("");
            logger.debug("Step 5: initialize the progress relation type");
        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.info("Failed to initialize the relation type");
        }

        logger.info("Step 6: import user and role demo data");

        try {
            UserRoleDataImporter.createUsers(ids, userFile, ProgressConstants.DIMENSION_USER_WITHPREFIX);
            UserRoleDataImporter.createRoles(ids, roleFile, ProgressConstants
                    .DIMENSION_ROLE_WITHPREFIX, ProgressConstants.DIMENSION_USER_WITHPREFIX,
                    ProgressConstants.RELATIONTYPE_ROLE_HASUSER_WITHPREFIX);
            logger.debug("Step 6: import user and role demo data");
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.error("Step 6: Failed to import user and role demo data");
        }

        ids.closeSpace();
        logger.info("End to prepare data");
    }
}
