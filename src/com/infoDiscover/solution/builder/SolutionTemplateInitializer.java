package com.infoDiscover.solution.builder;

import com.infoDiscover.common.dimension.time.TimeDimensionGenerator;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.common.database.DatabaseConnection;
import com.infoDiscover.solution.common.fact.FactManager;
import com.infoDiscover.solution.common.util.PrefixSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sun.
 */
public class SolutionTemplateInitializer {

    private final static Logger logger = LoggerFactory.getLogger(SolutionTemplateInitializer.class);

    public static void initializeSolutionTemplate(String spaceName, String prefix) throws
            Exception {

        logger.info("Start to create space: {}", spaceName);
        if (!DiscoverEngineComponentFactory.checkDiscoverSpaceExistence
                (spaceName)) {
            boolean created = DiscoverEngineComponentFactory.createInfoDiscoverSpace
                    (spaceName);
            logger.info("End to create space...");

            if (!created) {
                logger.error("Failed to create space: {}", spaceName);
                throw new Exception("Failed to create space " + spaceName);
            }
        }

        // connect to database
        InfoDiscoverSpace ids = DatabaseConnection.connectToSpace(spaceName);

        // initialize solution template fact type
        if (ids != null) {
            FactManager factManager = new FactManager(ids);
            factManager.createFactType(PrefixSetting.normalizePrefix(prefix) + SolutionConstants
                            .FACT_TYPE_SOLUTION_TEMPLATE,
                    SolutionConstants.FACT_TYPE_PROPERTIES);
        }

        ids.closeSpace();
    }


}
