package com.infoDiscover.solution.construction.supervision.sample;

import com.infoDiscover.infoDiscoverEngine.dataMart.RelationType;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.solution.construction.supervision.constants.DatabaseConstants;
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
        if (!ids.hasRelationType(prefix + DatabaseConstants
                .RELATION_PROJECT_HASTASK_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_PROJECT_HASTASK_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants
                .RELATION_TASK_EXECUTE_BY_ROLE_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_TASK_EXECUTE_BY_ROLE_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants
                .RELATION_TASK_EXECUTE_BY_USER_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_TASK_EXECUTE_BY_USER_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_SUBTASK_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_SUBTASK_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants
                .RELATION_EXECUTIVE_DEPARTMENT_HAS_USER_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_EXECUTIVE_DEPARTMENT_HAS_USER_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_TRANSFER_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_TRANSFER_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_STARTAT_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_STARTAT_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_ENDAT_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_ENDAT_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }


        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_ASSET_FIRST_CLASSIFICATION_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_ASSET_FIRST_CLASSIFICATION_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_ASSET_SECOND_CLASSIFICATION_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_ASSET_SECOND_CLASSIFICATION_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_ASSIGN_MODEL_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_ASSIGN_MODEL_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }


        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_CONSTRUCTION_TYPE_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_CONSTRUCTION_TYPE_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_ISSUE_CLASSIFICATION_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_ISSUE_CLASSIFICATION_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_LAND_PROPERTY_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_LAND_PROPERTY_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_PROJECT_SCOPE_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_PROJECT_SCOPE_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_PROJECT_SITE_CLASSIFICATION_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_PROJECT_SITE_CLASSIFICATION_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_PROJECT_TYPE_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_PROJECT_TYPE_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_PROVIDER_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_PROVIDER_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_TASK_EXECUTE_BY_DEPARTMENT_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_TASK_EXECUTE_BY_DEPARTMENT_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_COMPANY_CLASSIFICATION_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_COMPANY_CLASSIFICATION_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_PROJECT_CLASSIFICATION_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_PROJECT_CLASSIFICATION_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_IS_MEMBER_OF_COMPANY_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_IS_MEMBER_OF_COMPANY_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants
                .RELATION_IS_COMPANY_CLASSIFICATION_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_IS_COMPANY_CLASSIFICATION_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_LOCATED_AT_ROAD_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_LOCATED_AT_ROAD_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_START_BY_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_START_BY_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_PROJECT_ADDRESS_AT_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_PROJECT_ADDRESS_AT_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_IN_CHARGE_OF_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_IN_CHARGE_OF_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        if (!ids.hasRelationType(prefix + DatabaseConstants.RELATION_EXECUTE_BY_COMPANY_WITH_PREFIX)) {
            RelationType relationType = ids.addRelationType(prefix + DatabaseConstants
                    .RELATION_EXECUTE_BY_COMPANY_WITH_PREFIX);
            logger.debug("Created relationType: " + relationType.getTypeName());
        }

        logger.debug("Exit method createRelationType()...");
    }

}
