package com.infoDiscover.solution.construction.supervision.sample;

import com.infoDiscover.common.PrefixConstant;
import com.infoDiscover.common.dimension.time.TimeDimensionGenerator;
import com.infoDiscover.infoDiscoverEngine.dataMart.FactType;
import com.infoDiscover.infoDiscoverEngine.dataMart.PropertyType;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.arch.progress.constants.ProgressConstants;
import com.infoDiscover.solution.construction.supervision.database.SupervisionSolutionConstants;
import com.infoDiscover.solution.sample.util.JsonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sun.
 */
public class PrepareSampleData {
    private final static Logger logger = LoggerFactory.getLogger(PrepareSampleData.class);

    // 生成年份
    public final static int[] yearsToGenerate = new int[]{2010, 2011, 2012, 2013, 2014,
            2015, 2016, 2017};

    // 只生成年、月、日的时间维度
    public final static int depth = 3;

    //生成多少个maintain project
    public final static int countOfMaintainProgressToGenerate = 1;

    // 生成多少个new project
    public final static int countOfNewProgressToGenerate = 1;

    // 随机完成流程中的前几个任务, false表示完成全部任务
    public final static boolean toGenerateRandomTasksNumber = false;

    // solution prefix
    public final static String prefix = PrefixConstant.prefix;

    public static void main(String[] args) {

        prepareSampleData(SupervisionSolutionConstants.DATABASE_SPACE, SampleDataSet.FILE_USER,
                SampleDataSet.FILE_ROLE);

        InfoDiscoverSpace ids = DiscoverEngineComponentFactory.connectInfoDiscoverSpace
                (SupervisionSolutionConstants.DATABASE_SPACE);

        ProgressSampleDataGenerator.generateMaintenanceProjectSampleData(ids,
                countOfMaintainProgressToGenerate, toGenerateRandomTasksNumber);

        ProgressSampleDataGenerator.generateNewProjectSampleData(ids,
                countOfNewProgressToGenerate,toGenerateRandomTasksNumber);

        ids.closeSpace();
    }

    public static void prepareSampleData(String spaceName, String userFile, String roleFile) {
        logger.info("Start to prepare sample data");

        logger.info("Step 1: create sample ConstructionEngineeringSupervision database");
        if (DiscoverEngineComponentFactory.checkDiscoverSpaceExistence
                (spaceName)) {
            logger.error("Database: " + spaceName + " is already existed, " +
                    "please specify another one");
            System.exit(0);
        }
        boolean created = DiscoverEngineComponentFactory.createInfoDiscoverSpace
                (spaceName);
        logger.info("Step 1: end to create {} database: {}", spaceName, created);

        if (!created) {
            logger.error("Failed to create {} database", spaceName);
            System.exit(0);
        }

        // connect to database
        InfoDiscoverSpace ids = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);

        if (ids == null) {
            logger.error("Failed to connect to database: {} ", spaceName);
            System.exit(0);
        }

        logger.info("Step 2: initialize time dimension type");
        try {
            TimeDimensionGenerator.initTimeDimensionType(ids, prefix);
            logger.info("Step 2: end to initialize time dimension type with prefix: {}",
                    prefix);
        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.error("Failed to initialize time dimension type");
        }

        logger.info("Step 3: generate the specified years");
        TimeDimensionGenerator.generateYears(ids, prefix, yearsToGenerate,
                depth);
        logger.info("Step 3: end to generate the specified years: " + "{2010, 2011, 2012, 2013, " +
                "2014, 2015, 2016, 2017}");

        logger.info("Step 4: initialize the progress type");

        try {
            SampleProgressInitializer.initProgressFactType(ids, "");
            logger.debug("Step 4: end to initialize the progress type");
        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.error("Failed to initialize the progress type");
        }

        logger.info("Step 5: initialize the progress relation type");

        try {
            // progress fact type for maintain project
            FactType maintainProgressFactType = ids.addFactType(SampleDataSet
                    .FACTTYPE_MAINTENANCE_PROJECT);
            maintainProgressFactType.addTypeProperty(JsonConstants.PROGRESS_ID, PropertyType
                    .STRING);
            maintainProgressFactType.addTypeProperty(JsonConstants.PROGRESS_NAME, PropertyType
                    .STRING);
            maintainProgressFactType.addTypeProperty(JsonConstants.PROGRESS_STARTER, PropertyType
                    .STRING);
            maintainProgressFactType.addTypeProperty(JsonConstants.START_DATE, PropertyType.DATE);
            maintainProgressFactType.addTypeProperty(JsonConstants.END_DATE, PropertyType.DATE);
            maintainProgressFactType.addTypeProperty(JsonConstants.STATUS, PropertyType.STRING);

            // progress for new project
            FactType newProgressFactType = ids.addFactType(SampleDataSet
                    .FACTTYPE_NEW_PROJECT);
            newProgressFactType.addTypeProperty(JsonConstants.PROGRESS_ID, PropertyType.STRING);
            newProgressFactType.addTypeProperty(JsonConstants.PROGRESS_NAME, PropertyType.STRING);
            newProgressFactType.addTypeProperty(JsonConstants.PROGRESS_STARTER, PropertyType
                    .STRING);
            newProgressFactType.addTypeProperty(JsonConstants.START_DATE, PropertyType.DATE);
            newProgressFactType.addTypeProperty(JsonConstants.END_DATE, PropertyType.DATE);
            newProgressFactType.addTypeProperty(JsonConstants.STATUS, PropertyType.STRING);

            // task fact type
            FactType taskFactType = ids.addFactType(ProgressConstants.FACT_TASK_WITHPREFIX);
            taskFactType.addTypeProperty(JsonConstants.PROGRESS_ID, PropertyType.STRING);
            taskFactType.addTypeProperty(JsonConstants.TASK_ID, PropertyType.STRING);
            taskFactType.addTypeProperty(JsonConstants.TASK_NAME, PropertyType.STRING);
            taskFactType.addTypeProperty(JsonConstants.WORKER, PropertyType.STRING);
            taskFactType.addTypeProperty(JsonConstants.EXECUTIVEDEPARTMENT, PropertyType.STRING);
            taskFactType.addTypeProperty(JsonConstants.START_DATE, PropertyType.DATE);
            taskFactType.addTypeProperty(JsonConstants.END_DATE, PropertyType.DATE);

            // role dimension
            ids.addDimensionType(SupervisionSolutionConstants.DIMENSION_ROLE_WITH_PREFIX);

            // user dimension
            ids.addDimensionType(SupervisionSolutionConstants.DIMENSION_USER_WITH_PREFIX);
        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.error(e.getMessage());
        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.error(e.getMessage());
        }

        try {
            SampleProgressInitializer.initProgressRelationType(ids, "");
            logger.debug("Step 5: initialize the progress relation type");
        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.info("Failed to initialize the relation type");
        }

        logger.info("Step 6: import user and role sample data");

        try {
            UserRoleDataImporter.createUsers(ids, userFile, SupervisionSolutionConstants
                    .DIMENSION_USER_WITH_PREFIX);
            UserRoleDataImporter.createRoles(ids, roleFile, SupervisionSolutionConstants
                    .DIMENSION_ROLE_WITH_PREFIX, SupervisionSolutionConstants
                    .DIMENSION_USER_WITH_PREFIX, SupervisionSolutionConstants
                    .RELATION_ROLE_HASUSER_WITH_PREFIX);
            logger.debug("Step 6: import user and role sample data");
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.error("Step 6: Failed to import user and role sample data");
        }

        logger.info("Step 7: create dimensions");
        SampleDimensionGenerator generator = new SampleDimensionGenerator(ids);
        try {
            generator.createDimensionType();
        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.error("Step 7: Failed to create dimensions: {}", e.getMessage());
        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.error("Step 7: Failed to create dimensions: {}", e.getMessage());
        }

        logger.info("Step 8: create dimension sample data");
        try {
            generator.createDimensionSampleData(ids);
        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.error("Step 8: Failed to create sample dimension data: {}", e.getMessage());
        }

        ids.closeSpace();
        logger.info("End to prepare sample data");
    }
}
