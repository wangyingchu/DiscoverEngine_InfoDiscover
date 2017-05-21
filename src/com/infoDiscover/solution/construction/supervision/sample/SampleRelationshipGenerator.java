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

    public static void createRelationType(InfoDiscoverSpace ids, String prefix) throws
            InfoDiscoveryEngineDataMartException {
        logger.debug("Enter method createRelationType()");

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
                .RELATION_EXECUTIVE_DEPARTMENT_HAS_USER_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_EXECUTIVE_DEPARTMENT_HAS_USER_WITH_PREFIX);
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


        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_ASSET_FIRST_CLASSIFICATION_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_ASSET_FIRST_CLASSIFICATION_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_ASSET_SECOND_CLASSIFICATION_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_ASSET_SECOND_CLASSIFICATION_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_ASSIGN_MODEL_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_ASSIGN_MODEL_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }


        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_CONSTRUCTION_TYPE_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_CONSTRUCTION_TYPE_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_ISSUE_CLASSIFICATION_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_ISSUE_CLASSIFICATION_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_LAND_PROPERTY_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_LAND_PROPERTY_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_PROJECT_SCOPE_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_PROJECT_SCOPE_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_PROJECT_SITE_CLASSIFICATION_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_PROJECT_SITE_CLASSIFICATION_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_PROJECT_TYPE_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_PROJECT_TYPE_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_PROVIDER_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_PROVIDER_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_TASK_EXECUTEBYDEPARTMENT_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_TASK_EXECUTEBYDEPARTMENT_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_COMPANY_CLASSIFICATION_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_COMPANY_CLASSIFICATION_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_PROJECT_CLASSIFICATION_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_PROJECT_CLASSIFICATION_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_IS_MEMBER_OF_COMPANY_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_IS_MEMBER_OF_COMPANY_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants
                .RELATION_IS_COMPANY_CLASSIFICATION_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_IS_COMPANY_CLASSIFICATION_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_LOCATED_AT_ROAD_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_LOCATED_AT_ROAD_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_START_BY_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_START_BY_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_PROJECT_ADDRESS_AT_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_PROJECT_ADDRESS_AT_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_IN_CHARGE_OF_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_IN_CHARGE_OF_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + SupervisionSolutionConstants.RELATION_EXECUTE_BY_COMPANY_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + SupervisionSolutionConstants
                    .RELATION_EXECUTE_BY_COMPANY_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        logger.debug("Exit method createRelationType()...");
    }

}
