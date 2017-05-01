package com.infoDiscover.solution.construction.supervision.sample;

import com.infoDiscover.infoDiscoverEngine.dataMart.DimensionType;
import com.infoDiscover.infoDiscoverEngine.dataMart.FactType;
import com.infoDiscover.infoDiscoverEngine.dataMart.RelationType;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.construction.supervision.database.SupervisionSolutionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by sun.
 */
public class SampleProgressInitializer {

    private final static Logger logger = LoggerFactory.getLogger
            (SampleProgressInitializer.class);

    public static void initProgressFactType(InfoDiscoverSpace ids, String prefix) throws
            InfoDiscoveryEngineDataMartException {
        logger.debug("Enter method initProgressFactType()");

        if (!ids.hasFactType(prefix + SupervisionSolutionConstants.FACT_PROGRESS_WITH_PREFIX)) {
            FactType progressType = ids.addFactType(prefix + SupervisionSolutionConstants
                    .FACT_PROGRESS_WITH_PREFIX);
            logger.debug("Created progress fact type: " + progressType.getTypeName());
        }
        if (!ids.hasFactType(prefix + SupervisionSolutionConstants.FACT_TASK_WITH_PREFIX)) {
            FactType taskType = ids.addFactType(prefix + SupervisionSolutionConstants.FACT_TASK_WITH_PREFIX);
            logger.debug("Created task fact type: " + taskType.getTypeName());
        }
        if (!ids.hasFactType(prefix + SupervisionSolutionConstants
                .DIMENSION_ROLE_WITH_PREFIX)) {
            DimensionType roleType = ids.addDimensionType(prefix + SupervisionSolutionConstants
                    .DIMENSION_ROLE_WITH_PREFIX);
            logger.debug("Created role dimension type: " + roleType.getTypeName());
        }
        if (!ids.hasFactType(SupervisionSolutionConstants.DIMENSION_USER_WITH_PREFIX)) {
            DimensionType userType = ids.addDimensionType(prefix + SupervisionSolutionConstants
                    .DIMENSION_USER_WITH_PREFIX);
            logger.debug("Created user dimension type: " + userType.getTypeName());
        }

        logger.debug("Exit method initTaskFactType()...");
    }

    public static void initProgressRelationType(InfoDiscoverSpace ids, String prefix) throws
            InfoDiscoveryEngineDataMartException {
        logger.debug("Enter method initProgressRelationType()");

        if (prefix == null) {
            prefix = "";
        }
        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants
                .RELATIONTYPE_PROGRESS_HASTASK_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATIONTYPE_PROGRESS_HASTASK_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants
                .RELATIONTYPE_TASK_EXECUTEBYROLE_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATIONTYPE_TASK_EXECUTEBYROLE_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants
                .RELATIONTYPE_TASK_EXECUTEBYUSER_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATIONTYPE_TASK_EXECUTEBYUSER_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATIONTYPE_SUBTASK_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATIONTYPE_SUBTASK_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants
                .RELATIONTYPE_ROLE_HASUSER_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATIONTYPE_ROLE_HASUSER_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATIONTYPE_TRANSFER_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATIONTYPE_TRANSFER_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATIONTYPE_STARTAT_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATIONTYPE_STARTAT_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATIONTYPE_ENDAT_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATIONTYPE_ENDAT_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        logger.debug("Exit method initProgressRelationType()...");
    }

    public static void main(String[] args) {
        try {
            InfoDiscoverSpace ids = DiscoverEngineComponentFactory.connectInfoDiscoverSpace
                    (SupervisionSolutionConstants.DATABASE_SPACE);
            initProgressFactType(ids, "");
            initProgressRelationType(ids, "");

            ids.closeSpace();
        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.error(e.getMessage());
        }
    }
}
