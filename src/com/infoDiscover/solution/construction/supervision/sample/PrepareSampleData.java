package com.infoDiscover.solution.construction.supervision.sample;

import com.infoDiscover.common.dimension.time.TimeDimensionGenerator;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.construction.supervision.database.SupervisionSolutionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sun.
 */
public class PrepareSampleData {
    private final static Logger logger = LoggerFactory.getLogger(PrepareSampleData.class);

    // ===============参数配置=====================//
    // 生成年份
    public final static int[] yearsToGenerate = new int[]{2010, 2011, 2012, 2013, 2014,
            2015, 2016, 2017, 2018, 2019, 2020};

    // 只生成年、月、日的时间维度
    public final static int depth = 3;

    //生成多少个维修工程
    public final static int countOfMaintainProgressToGenerate = 1;

    // 生成多少个新建工程
    public final static int countOfNewProgressToGenerate = 1;

    // 生成多少个扩建工程
    public final static int countOfExtensionProgressToGenerate = 1;

    // 生成多少个改建工程
    public final static int countOfRebuildProgressToGenerate = 1;

    // 随机完成流程中的前几个任务, false表示完成全部任务
    public final static boolean toGenerateRandomTasksNumber = false;

    // solution prefix
    public final static String prefix = "ZHUHAI_";

    // template root path
    public final static String ROOT_PATH =
            "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover" +
                    "/solution/construction/supervision/template/";

    // ===============参数配置=====================//

    public static void main(String[] args) {

        prepareSampleData(SupervisionSolutionConstants.DATABASE_SPACE, SampleDataSet.FILE_USER,
                SampleDataSet.FILE_ROLE);

        InfoDiscoverSpace ids = DiscoverEngineComponentFactory.connectInfoDiscoverSpace
                (SupervisionSolutionConstants.DATABASE_SPACE);

        ProgressSampleDataGenerator.generateMaintenanceProjectSampleData(ids,
                countOfMaintainProgressToGenerate, toGenerateRandomTasksNumber);

        ProgressSampleDataGenerator.generateNewProjectSampleData(ids,
                countOfNewProgressToGenerate, toGenerateRandomTasksNumber);

        ProgressSampleDataGenerator.generateExtensionProjectSampleData(ids,
                countOfExtensionProgressToGenerate, toGenerateRandomTasksNumber);

        ProgressSampleDataGenerator.generateRebuildProjectSampleData(ids,
                countOfRebuildProgressToGenerate, toGenerateRandomTasksNumber);

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

        logger.info("Step 4: initialize the progress fact type");
        SampleFactGenerator factGenerator = new SampleFactGenerator(ids);
        try {
            factGenerator.createFactType();
            logger.debug("Step 4: end to initialize the progress type");
        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.error("Step4: Failed to initialize the progress type: {}", e.getMessage());
        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.error("Failed to initialize the progress type: {}", e.getMessage());
        }

        logger.info("Step 5: initialize the progress relation type");
        try {
            SampleRelationshipGenerator.initProgressRelationType(ids, "");
            logger.debug("Step 5: initialize the progress relation type");
        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.info("Step 5: Failed to initialize the relation type");
        }

        logger.info("Step 6: create dimensions");
        SampleDimensionGenerator dimensionGenerator = new SampleDimensionGenerator(ids);
        try {
            dimensionGenerator.createDimensionType();
        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.error("Step 6: Failed to create dimensions: {}", e.getMessage());
        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.error("Step 6: Failed to create dimensions: {}", e.getMessage());
        }

        logger.info("Step 7: create dimension sample data");
        try {
            dimensionGenerator.createDimensionSampleData(ids);
        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.error("Step 7: Failed to create sample dimension data: {}", e.getMessage());
        }

        logger.info("Step 8: link user and role");
        try {
            dimensionGenerator.linkUsersToRole(ids, SampleDataSet.FILE_USER_ROLE,
                    SupervisionSolutionConstants
                    .DIMENSION_ROLE_WITH_PREFIX, SupervisionSolutionConstants
                    .DIMENSION_USER_WITH_PREFIX, SupervisionSolutionConstants
                            .RELATION_ROLE_HASUSER_WITH_PREFIX);
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.error("Step 8: Failed to link users to role: {}", e.getMessage());
        }
        ids.closeSpace();
        logger.info("End to prepare sample data");
    }
}
