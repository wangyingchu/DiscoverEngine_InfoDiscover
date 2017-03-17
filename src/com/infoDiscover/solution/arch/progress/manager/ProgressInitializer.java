package com.infoDiscover.solution.arch.progress.manager;

import com.infoDiscover.solution.arch.database.DatabaseConstants;
import com.infoDiscover.solution.arch.database.DatabaseManager;
import com.infoDiscover.solution.arch.progress.constants.ProgressConstants;
import com.infoDiscover.infoDiscoverEngine.dataMart.*;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Created by sun.
 */
public class ProgressInitializer {

    private final static Logger logger = LogManager.getLogger
            (ProgressInitializer.class);

    public static void initProgressFactType(String prefix) throws
            InfoDiscoveryEngineDataMartException {
        logger.debug("Enter method initTaskFactType()");

        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();

        if (ids != null) {
            if (!ids.hasFactType(prefix + ProgressConstants.FACT_PROGRESS)) {
                FactType progressType = ids.addFactType(prefix + ProgressConstants.FACT_PROGRESS);
                logger.debug("Created progress fact type: " + progressType.getTypeName());
            }
            if (!ids.hasFactType(prefix + ProgressConstants.FACT_TASK)) {
                FactType taskType = ids.addFactType(prefix + ProgressConstants.FACT_TASK);
                logger.debug("Created task fact type: " + taskType.getTypeName());
            }
            if (!ids.hasFactType(prefix + ProgressConstants.DIMENSION_ROLE)) {
                DimensionType roleType = ids.addDimensionType(prefix + ProgressConstants
                        .DIMENSION_ROLE);
                logger.debug("Created role dimension type: " + roleType.getTypeName());
            }
            if (!ids.hasFactType(ProgressConstants.DIMENSION_USER)) {
                DimensionType userType = ids.addDimensionType(prefix + ProgressConstants
                        .DIMENSION_USER);
                logger.debug("Created user dimension type: " + userType.getTypeName());
            }
        } else {
            logger.debug("Failed to connect to database: " + DatabaseConstants
                    .INFODISCOVER_SPACENAME);
        }
        ids.closeSpace();

        logger.debug("Exit method initTaskFactType()...");
    }

    public static void initProgressRelationType(String prefix) throws
            InfoDiscoveryEngineDataMartException {
        logger.debug("Enter method initProgressRelationType()");

        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        if (prefix == null) {
            prefix = "";
        }
        if (ids != null) {
            if (!ids.hasRelationType(prefix + ProgressConstants.RELATIONTYPE_PROGRESS_HASTASK)) {
                RelationType relationType = ids.addRelationType(prefix + ProgressConstants
                        .RELATIONTYPE_PROGRESS_HASTASK);
                logger.debug("Created relationType: " + relationType.getTypeName());
            }

            if (!ids.hasRelationType(prefix + ProgressConstants.RELATIONTYPE_TASK_EXECUTEBYROLE)) {
                RelationType relationType = ids.addRelationType(prefix + ProgressConstants
                        .RELATIONTYPE_TASK_EXECUTEBYROLE);
                logger.debug("Created relationType: " + relationType.getTypeName());
            }

            if (!ids.hasRelationType(prefix + ProgressConstants.RELATIONTYPE_TASK_EXECUTEBYUSER)) {
                RelationType relationType = ids.addRelationType(prefix + ProgressConstants
                        .RELATIONTYPE_TASK_EXECUTEBYUSER);
                logger.debug("Created relationType: " + relationType.getTypeName());
            }

            if (!ids.hasRelationType(prefix + ProgressConstants.RELATIONTYPE_SUBTASK)) {
                RelationType relationType = ids.addRelationType(prefix + ProgressConstants
                        .RELATIONTYPE_SUBTASK);
                logger.debug("Created relationType: " + relationType.getTypeName());
            }

            if (!ids.hasRelationType(prefix + ProgressConstants.RELATIONTYPE_ROLE_HASUSER)) {
                RelationType relationType = ids.addRelationType(prefix + ProgressConstants
                        .RELATIONTYPE_ROLE_HASUSER);
                logger.debug("Created relationType: " + relationType.getTypeName());
            }

            if (!ids.hasRelationType(prefix + ProgressConstants.RELATIONTYPE_TRANSFER)) {
                RelationType relationType = ids.addRelationType(prefix + ProgressConstants
                        .RELATIONTYPE_TRANSFER);
                logger.debug("Created relationType: " + relationType.getTypeName());
            }

            if (!ids.hasRelationType(prefix + ProgressConstants.RELATIONTYPE_STARTAT)) {
                RelationType relationType = ids.addRelationType(prefix + ProgressConstants
                        .RELATIONTYPE_STARTAT);
                logger.debug("Created relationType: " + relationType.getTypeName());
            }

            if (!ids.hasRelationType(prefix + ProgressConstants.RELATIONTYPE_ENDAT)) {
                RelationType relationType = ids.addRelationType(prefix + ProgressConstants
                        .RELATIONTYPE_ENDAT);
                logger.debug("Created relationType: " + relationType.getTypeName());
            }
        } else {
            logger.debug("Failed to connect to database: " + DatabaseConstants
                    .INFODISCOVER_SPACENAME);
        }

        ids.closeSpace();

        logger.debug("Exit method initProgressRelationType()...");
    }

    public static void main(String[] args) {
        try {
            initProgressFactType("");
            initProgressRelationType("");
        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.error(e.getMessage());
        }
    }
}
