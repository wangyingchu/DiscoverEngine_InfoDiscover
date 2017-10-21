package com.businessExtension.constructionSupervision.sample;

import com.infoDiscover.common.dimension.time.TimeDimensionGenerator;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.common.util.PrefixSetting;
import com.businessExtension.constructionSupervision.constants.DatabaseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sun.
 */
public class PrepareSampleData {
    private final static Logger logger = LoggerFactory.getLogger(PrepareSampleData.class);

    // ===============参数配置=====================//
    // 生成年份
    public static final int[] yearsRangeToGenerate = new int[]{2010, 2018};

    // 只生成年、月、日的时间维度
    public static final int depth = 3;

    //生成多少个维修工程
    public static final int[] countOfMaintainProgressToGenerate = {2, 1};

    // 生成多少个新建工程
    public static final int[] countOfNewProgressToGenerate = {2, 1};

    // 生成多少个扩建工程
    public static final int[] countOfExtensionProgressToGenerate = {2, 1};

    // 生成多少个改建工程
    public static final int[] countOfRebuildProgressToGenerate = {2, 1};

    // 随机完成流程中的前几个任务, false表示完成全部任务
//    public static final boolean toGenerateRandomTasksNumber = false;


    // solution prefix
    public static final String prefix = "ZHUHAI88_";

    // template root path
    public static final String ROOT_PATH =
            "/Users/sun/InfoDiscovery/code/DiscoverEngine_InfoDiscover/src/com/infoDiscover" +
                    "/solution/construction/supervision/sample/template";

    // ===============参数配置=====================//

    public static void main(String[] args) {

        validateInputProjectCount();

        // set prefix
        new PrefixSetting().setPrefixMap(prefix);

        prepareSampleData(DatabaseConstants.DATABASE_SPACE, SampleDataSet.FILE_USER,
                SampleDataSet.FILE_DEPARTMENT);

        InfoDiscoverSpace ids = DiscoverEngineComponentFactory.connectInfoDiscoverSpace
                (DatabaseConstants.DATABASE_SPACE);

        ProjectSampleDataGenerator.generateMaintenanceProjectSampleData(ids,
                countOfMaintainProgressToGenerate);

        ProjectSampleDataGenerator.generateNewProjectSampleData(ids,
                countOfNewProgressToGenerate);

        ProjectSampleDataGenerator.generateExtensionProjectSampleData(ids,
                countOfExtensionProgressToGenerate);

        ProjectSampleDataGenerator.generateRebuildProjectSampleData(ids,
                countOfRebuildProgressToGenerate);

        ids.closeSpace();
    }

    private static void validateInputProjectCount() {

        if (countOfMaintainProgressToGenerate == null || countOfMaintainProgressToGenerate
                .length != 2) {
            logger.error("You must specify how many completed maintenance projects and how many " +
                    "uncompleted " +
                    "projects to generated, the first value of array is complete, the second one " +
                    "is uncompleted");
            System.exit(0);
        }

        if (countOfNewProgressToGenerate == null || countOfNewProgressToGenerate
                .length != 2) {
            logger.error("You must specify how many completed new projects and how many " +
                    "uncompleted " +
                    "projects to generated, the first value of array is complete, the second one " +
                    "is uncompleted");
            System.exit(0);
        }

        if (countOfExtensionProgressToGenerate == null || countOfExtensionProgressToGenerate
                .length != 2) {
            logger.error("You must specify how many completed extension projects and how many " +
                    "uncompleted " +
                    "projects to generated, the first value of array is complete, the second one " +
                    "is uncompleted");
            System.exit(0);
        }

        if (countOfRebuildProgressToGenerate == null || countOfRebuildProgressToGenerate
                .length != 2) {
            logger.error("You must specify how many completed rebuild projects and how many " +
                    "uncompleted " +
                    "projects to generated, the first value of array is complete, the second one " +
                    "is uncompleted");
            System.exit(0);
        }
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
        TimeDimensionGenerator.generateYears(ids, prefix, yearsRangeToGenerate, depth);
        logger.info("Step 3: end to generate the specified years: {}", yearsRangeToGenerate);

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
            SampleRelationshipGenerator.createRelationType(ids, "");
            logger.debug("Step 5: create relation type");
        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.info("Step 5: Failed to create relation type");
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

        logger.info("Step 8: link users to executive departments");
        try {
            dimensionGenerator.linkUsersToExecutiveDepartment(ids, SampleDataSet
                    .FILE_USER_DEPARTMENT, DatabaseConstants
                    .DIMENSION_EXECUTIVE_DEPARTMENT_WITH_PREFIX, DatabaseConstants
                    .DIMENSION_USER_WITH_PREFIX, DatabaseConstants
                    .RELATION_EXECUTIVE_DEPARTMENT_HAS_USER_WITH_PREFIX);
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.error("Step 8: Failed to link users to role: {}", e.getMessage());
        }

        logger.info("Step9: link external user to company");
        dimensionGenerator.createCompanyAndUsers(ids);

        logger.info("Step10: link company to road");
        dimensionGenerator.createProjectAddressAndRoad(ids);

        ids.closeSpace();
        logger.info("End to prepare sample data");
    }
}
