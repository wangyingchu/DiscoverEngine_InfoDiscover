package com.infoDiscover.solution.arch.progress.manager;

import com.infoDiscover.solution.arch.database.DatabaseConstants;
import com.infoDiscover.solution.arch.database.DatabaseManager;
import com.infoDiscover.solution.arch.progress.constants.ProgressConstants;
import com.infoDiscover.infoDiscoverEngine.dataMart.*;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by sun.
 */
public class ProgressInitializer {

    private final static Logger logger = LoggerFactory.getLogger
            (ProgressInitializer.class);

    public static void initProgressFactType(String prefix) throws
            InfoDiscoveryEngineDataMartException {
        logger.debug("Enter method initProgressFactType()");

        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();

        if (ids != null) {
            if (!ids.hasFactType(prefix + ProgressConstants.FACT_PROGRESS_WITHPREFIX)) {
                FactType progressType = ids.addFactType(prefix + ProgressConstants.FACT_PROGRESS_WITHPREFIX);
                logger.debug("Created progress fact type: " + progressType.getTypeName());
            }
            if (!ids.hasFactType(prefix + ProgressConstants.FACT_TASK_WITHPREFIX)) {
                FactType taskType = ids.addFactType(prefix + ProgressConstants.FACT_TASK_WITHPREFIX);
                logger.debug("Created task fact type: " + taskType.getTypeName());
            }
            if (!ids.hasFactType(prefix + ProgressConstants.DIMENSION_ROLE_WITHPREFIX)) {
                DimensionType roleType = ids.addDimensionType(prefix + ProgressConstants
                        .DIMENSION_ROLE_WITHPREFIX);
                logger.debug("Created role dimension type: " + roleType.getTypeName());
            }
            if (!ids.hasFactType(ProgressConstants.DIMENSION_USER_WITHPREFIX)) {
                DimensionType userType = ids.addDimensionType(prefix + ProgressConstants
                        .DIMENSION_USER_WITHPREFIX);
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
            if (!ids.hasRelationType(prefix + ProgressConstants.RELATIONTYPE_PROGRESS_HASTASK_WITHPREFIX)) {
                RelationType relationType = ids.addRelationType(prefix + ProgressConstants
                        .RELATIONTYPE_PROGRESS_HASTASK_WITHPREFIX);
                logger.debug("Created relationType: " + relationType.getTypeName());
            }

            if (!ids.hasRelationType(prefix + ProgressConstants.RELATIONTYPE_TASK_EXECUTEBYROLE_WITHPREFIX)) {
                RelationType relationType = ids.addRelationType(prefix + ProgressConstants
                        .RELATIONTYPE_TASK_EXECUTEBYROLE_WITHPREFIX);
                logger.debug("Created relationType: " + relationType.getTypeName());
            }

            if (!ids.hasRelationType(prefix + ProgressConstants.RELATIONTYPE_TASK_EXECUTEBYUSER_WITHPREFIX)) {
                RelationType relationType = ids.addRelationType(prefix + ProgressConstants
                        .RELATIONTYPE_TASK_EXECUTEBYUSER_WITHPREFIX);
                logger.debug("Created relationType: " + relationType.getTypeName());
            }

            if (!ids.hasRelationType(prefix + ProgressConstants.RELATIONTYPE_SUBTASK_WITHPREFIX)) {
                RelationType relationType = ids.addRelationType(prefix + ProgressConstants
                        .RELATIONTYPE_SUBTASK_WITHPREFIX);
                logger.debug("Created relationType: " + relationType.getTypeName());
            }

            if (!ids.hasRelationType(prefix + ProgressConstants.RELATIONTYPE_ROLE_HASUSER_WITHPREFIX)) {
                RelationType relationType = ids.addRelationType(prefix + ProgressConstants
                        .RELATIONTYPE_ROLE_HASUSER_WITHPREFIX);
                logger.debug("Created relationType: " + relationType.getTypeName());
            }

            if (!ids.hasRelationType(prefix + ProgressConstants.RELATIONTYPE_TRANSFER_WITHPREFIX)) {
                RelationType relationType = ids.addRelationType(prefix + ProgressConstants
                        .RELATIONTYPE_TRANSFER_WITHPREFIX);
                logger.debug("Created relationType: " + relationType.getTypeName());
            }

            if (!ids.hasRelationType(prefix + ProgressConstants.RELATIONTYPE_STARTAT_WITHPREFIX)) {
                RelationType relationType = ids.addRelationType(prefix + ProgressConstants
                        .RELATIONTYPE_STARTAT_WITHPREFIX);
                logger.debug("Created relationType: " + relationType.getTypeName());
            }

            if (!ids.hasRelationType(prefix + ProgressConstants.RELATIONTYPE_ENDAT_WITHPREFIX)) {
                RelationType relationType = ids.addRelationType(prefix + ProgressConstants
                        .RELATIONTYPE_ENDAT_WITHPREFIX);
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
