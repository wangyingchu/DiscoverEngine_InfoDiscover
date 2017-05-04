package com.infoDiscover.solution.construction.supervision.sample;

import com.infoDiscover.infoDiscoverEngine.dataMart.RelationType;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.solution.construction.supervision.database.SupervisionSolutionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by sun.
 */
public class SampleRelationshipGenerator {

    private final static Logger logger = LoggerFactory.getLogger
            (SampleRelationshipGenerator.class);

    public static void initProgressRelationType(InfoDiscoverSpace ids, String prefix) throws
            InfoDiscoveryEngineDataMartException {
        logger.debug("Enter method initProgressRelationType()");

        if (prefix == null) {
            prefix = "";
        }
        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants
                .RELATION_PROGRESS_HASTASK_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_PROGRESS_HASTASK_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants
                .RELATION_TASK_EXECUTEBYROLE_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_TASK_EXECUTEBYROLE_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants
                .RELATION_TASK_EXECUTEBYUSER_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_TASK_EXECUTEBYUSER_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_SUBTASK_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_SUBTASK_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants
                .RELATION_ROLE_HASUSER_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_ROLE_HASUSER_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_TRANSFER_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_TRANSFER_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_STARTAT_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_STARTAT_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_ENDAT_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_ENDAT_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        logger.debug("Exit method initProgressRelationType()...");
    }

}
