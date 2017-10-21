package com.businessExtension.constructionSupervision.manager;

import com.infoDiscover.common.dimension.time.TimeDimensionGenerator;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.common.database.DatabaseConnection;
import com.businessExtension.constructionSupervision.sample.PrepareSampleData;
import com.businessExtension.constructionSupervision.sample.SampleDimensionGenerator;
import com.businessExtension.constructionSupervision.sample.SampleFactGenerator;
import com.businessExtension.constructionSupervision.sample.SampleRelationshipGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sun.
 */
public class Initializer {
    private final static Logger logger = LoggerFactory.getLogger(PrepareSampleData.class);

    // solution prefix
    public static final String prefix = "ZHUHAI_";

    public static void initialize(String spaceName) throws Exception {
        logger.info("Start to initialize dimensions and relationTypes with spaceName: {}", spaceName);

        InfoDiscoverSpace ids = DatabaseConnection.connectToSpace(spaceName);

        logger.info("Step 1: initialize time dimension type");
        try {
            TimeDimensionGenerator.initTimeDimensionType(ids, prefix);
            logger.info("Step 1: end to initialize time dimension type with prefix: {}",
                    prefix);
        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.error("Failed to initialize time dimension type");
        }

        logger.info("Step 2: initialize the project fact type");
        SampleFactGenerator factGenerator = new SampleFactGenerator(ids);
        try {
            factGenerator.createFactType();
            logger.debug("Step 2: end to initialize the project type");
        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.error("Step 2: Failed to initialize the project type: {}", e.getMessage());
        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.error("Failed to initialize the project type: {}", e.getMessage());
        }

        logger.info("Step 3: create relation types");
        try {
            SampleRelationshipGenerator.createRelationType(ids, "");
            logger.debug("Step 3: create relation types");
        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.info("Step 3: Failed to create relation types");
        }

        logger.info("Step 4: create dimensions");
        SampleDimensionGenerator dimensionGenerator = new SampleDimensionGenerator(ids);
        try {
            dimensionGenerator.createDimensionType();
        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.error("Step 4: Failed to create dimensions: {}", e.getMessage());
        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.error("Step 4: Failed to create dimensions: {}", e.getMessage());
        }

        ids.closeSpace();
        logger.info("End method initialize()...");
    }

    public static void main(String[] args) {
        String spaceName = "Test";
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

        // initialize
        try {
            initialize(spaceName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
