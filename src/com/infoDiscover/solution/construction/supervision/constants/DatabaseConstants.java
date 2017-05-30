package com.infoDiscover.solution.construction.supervision.constants;

import com.infoDiscover.solution.common.util.PrefixSetting;

/**
 * Created by sun.
 */
public class DatabaseConstants {
    // database space
    public static final String SOLUTION_PREFIX =
            PrefixSetting.getPrefixMap().get(PrefixSetting.PREFIX).toString();

    public static final String DATABASE_SPACE = SOLUTION_PREFIX +
            "ConstructionEngineeringSupervision";

    // Fact type
    //public static final String FACT_PROJECT_WITH_PREFIX = SOLUTION_PREFIX + "PROJECT";
    public static final String FACT_PROJECT_WITH_PREFIX = SOLUTION_PREFIX;
    public static final String FACTTYPE_MAINTENANCE_PROJECT = FACT_PROJECT_WITH_PREFIX +
            "MAINTENANCE_PROJECT";
    public static final String FACTTYPE_CONSTRUCTION_PROJECT = FACT_PROJECT_WITH_PREFIX +
            "CONSTRUCTION_PROJECT";
    public static final String FACT_TASK_WITH_PREFIX = SOLUTION_PREFIX + "TASK";

    // Dimension type
    public static final String DIMENSION_ROLE_WITH_PREFIX = SOLUTION_PREFIX + "ROLE";
    public static final String DIMENSION_USER_WITH_PREFIX = SOLUTION_PREFIX + "USER";

    public static final String DIMENSION_CONSTRUCTION_TYPE_WITH_PREFIX = SOLUTION_PREFIX +
            "CONSTRUCTION_TYPE";
    public static final String DIMENSION_COMPANY_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "COMPANY_CLASSIFICATION";
    public static final String DIMENSION_ASSIGN_MODEL_WITH_PREFIX = SOLUTION_PREFIX +
            "ASSIGN_MODEL";
    public static final String DIMENSION_EXECUTIVE_DEPARTMENT_WITH_PREFIX = SOLUTION_PREFIX +
            "EXECUTIVE_DEPARTMENT";
    public static final String DIMENSION_GOVERNMENT_APPROVAL_AUTHORITY_WITH_PREFIX =
            SOLUTION_PREFIX + "GOVERNMENT_APPROVAL_AUTHORITY";
    public static final String DIMENSION_ISSUE_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "ISSUE_CLASSIFICATION";
    public static final String DIMENSION_LAND_PROPERTY_WITH_PREFIX = SOLUTION_PREFIX +
            "LAND_PROPERTY";
    public static final String DIMENSION_ASSET_FIRST_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "ASSERT_FIRST_CLASSIFICATION";
    public static final String DIMENSION_ASSET_SECOND_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX
            + "ASSET_SECOND_CLASSIFICATION";
    public static final String DIMENSION_PROJECT_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "PROJECT_CLASSIFICATION";
    public static final String DIMENSION_PROJECT_SITE_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX
            + "PROJECT_SITE_CLASSIFICATION";
    public static final String DIMENSION_PROJECT_SCOPE_WITH_PREFIX = SOLUTION_PREFIX +
            "PROJECT_SCOPE";
    public static final String DIMENSION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX =
            SOLUTION_PREFIX + "PROJECT_CONSTRUCTION_CLASSIFICATION";

    public static final String DIMENSION_ROAD_WITH_PREFIX = SOLUTION_PREFIX + "ROAD";
    public static final String DIMENSION_COMPANY_WITH_PREFIX = SOLUTION_PREFIX + "COMPANY";


    public static final String DIMENSION_EXTERNAL_USER_WITH_PREFIX = SOLUTION_PREFIX +
            "EXTERNAL_USER";

    public static final String DIMENSION_PROJECT_ADDRESS_WITH_PREFIX = SOLUTION_PREFIX +
            "PROJECT_ADDRESS";

    // Relation type
    public static final String RELATION_PROJECT_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "PROJECT_CLASSIFICATION_IS";
    public static final String RELATION_ASSET_FIRST_CLASSIFICATION_WITH_PREFIX =
            SOLUTION_PREFIX + "ASSET_FIRST_CLASSIFICATION_IS";
    public static final String RELATION_ASSET_SECOND_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "ASSET_SECOND_CLASSIFICATION_IS";
    public static final String RELATION_ASSIGN_MODEL_WITH_PREFIX = SOLUTION_PREFIX +
            "ASSIGN_MODEL_IS";
    public static final String RELATION_CONSTRUCTION_TYPE_WITH_PREFIX = SOLUTION_PREFIX +
            "CONSTRUCTION_TYPE_IS";
    public static final String RELATION_COMPANY_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "COMPANY_CLASSIFICATION_IS";
    public static final String RELATION_ISSUE_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "ISSUE_CLASSIFICATION_IS";
    public static final String RELATION_LAND_PROPERTY_WITH_PREFIX = SOLUTION_PREFIX +
            "LAND_PROPERTY_IS";
    public static final String RELATION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX =
            SOLUTION_PREFIX + "PROJECT_CONSTRUCTION_CLASSIFICATION_IS";
    public static final String RELATION_PROJECT_SCOPE_WITH_PREFIX = SOLUTION_PREFIX +
            "PROJECT_SCOPE_IS";
    public static final String RELATION_PROJECT_SITE_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "PROJECT_SITE_CLASSIFICATION_IS";
    public static final String RELATION_PROJECT_TYPE_WITH_PREFIX = SOLUTION_PREFIX +
            "PROJECT_TYPE_IS";
//    public static final String RELATION_PROVIDER_WITH_PREFIX = SOLUTION_PREFIX + "PROVIDER_IS";

    public static final String RELATION_PROJECT_HASTASK_WITH_PREFIX = SOLUTION_PREFIX +
            "HAS_TASK";
//    public static final String RELATION_SUBTASK_WITH_PREFIX = SOLUTION_PREFIX + "SUBTASK";
//    public static final String RELATION_TASK_EXECUTE_BY_ROLE_WITH_PREFIX = SOLUTION_PREFIX +
//            "EXECUTE_BY_ROLE";
    public static final String RELATION_TASK_EXECUTE_BY_DEPARTMENT_WITH_PREFIX = SOLUTION_PREFIX +
            "EXECUTE_BY_DEPARTMENT";
    public static final String RELATION_TASK_EXECUTE_BY_USER_WITH_PREFIX = SOLUTION_PREFIX +
            "EXECUTE_BY_USER";
    public static final String RELATION_EXECUTIVE_DEPARTMENT_HAS_USER_WITH_PREFIX =
            SOLUTION_PREFIX + "HAS_USER";
//    public static final String RELATION_TRANSFER_WITH_PREFIX = SOLUTION_PREFIX +
//            "TRANSFER_TASK";
    public static final String RELATION_STARTAT_WITH_PREFIX = SOLUTION_PREFIX + "START_AT";
    public static final String RELATION_ENDAT_WITH_PREFIX = SOLUTION_PREFIX + "END_AT";

    public static final String RELATION_IS_MEMBER_OF_COMPANY_WITH_PREFIX = SOLUTION_PREFIX +
            "IS_MEMBER_OF";

    public static final String RELATION_IS_COMPANY_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "IS_COMPANY_OF";

    public static final String RELATION_LOCATED_AT_ROAD_WITH_PREFIX = SOLUTION_PREFIX +
            "LOCATED_AT_ROAD";

    public static final String RELATION_START_BY_WITH_PREFIX = SOLUTION_PREFIX +
            "START_BY";

    public static final String RELATION_PROJECT_ADDRESS_AT_WITH_PREFIX = SOLUTION_PREFIX +
            "PROJECT_AT";

    public static final String RELATION_IN_CHARGE_OF_WITH_PREFIX = SOLUTION_PREFIX +
            "IS_IN_CHARGE_OF";
    public static final String RELATION_EXECUTE_BY_COMPANY_WITH_PREFIX = SOLUTION_PREFIX +
            "EXECUTE_BY_COMPANY";


    public static final String[] factPropertiesAsDimensionArray = {
            JsonConstants.JSON_CONSTRUCTION_TYPE,
            JsonConstants.JSON_COMPANY_CLASSIFICATION,
            JsonConstants.JSON_ASSIGN_MODEL,
            JsonConstants.JSON_ISSUE_CLASSIFICATION,
            JsonConstants.JSON_LAND_PROPERTY,
            JsonConstants.JSON_ASSET_FIRST_CLASSIFICATION,
            JsonConstants.JSON_ASSET_SECOND_CLASSIFICATION,
            JsonConstants.JSON_PROJECT_CLASSIFICATION,
            JsonConstants.JSON_PROJECT_SITE_CLASSIFICATION,
            JsonConstants.JSON_PROJECT_SCOPE,
            JsonConstants.JSON_PROJECT_CONSTRUCTION_CLASSIFICATION};


    public static final String[][] factPropertiesToDimensionsMapping = {
            {JsonConstants.JSON_CONSTRUCTION_TYPE, DIMENSION_CONSTRUCTION_TYPE_WITH_PREFIX},
            {JsonConstants.JSON_COMPANY_CLASSIFICATION,
                    DIMENSION_COMPANY_CLASSIFICATION_WITH_PREFIX},
            {JsonConstants.JSON_ASSIGN_MODEL, DIMENSION_ASSIGN_MODEL_WITH_PREFIX},
            {JsonConstants.JSON_ISSUE_CLASSIFICATION, DIMENSION_ISSUE_CLASSIFICATION_WITH_PREFIX},
            {JsonConstants.JSON_LAND_PROPERTY, DIMENSION_LAND_PROPERTY_WITH_PREFIX},
            {JsonConstants.JSON_ASSET_FIRST_CLASSIFICATION,
                    DIMENSION_ASSET_FIRST_CLASSIFICATION_WITH_PREFIX},
            {JsonConstants.JSON_ASSET_SECOND_CLASSIFICATION,
                    DIMENSION_ASSET_SECOND_CLASSIFICATION_WITH_PREFIX},
            {JsonConstants.JSON_PROJECT_CLASSIFICATION,
                    DIMENSION_PROJECT_CLASSIFICATION_WITH_PREFIX},
            {JsonConstants.JSON_PROJECT_SITE_CLASSIFICATION,
                    DIMENSION_PROJECT_SITE_CLASSIFICATION_WITH_PREFIX},
            {JsonConstants.JSON_PROJECT_SCOPE, DIMENSION_PROJECT_SCOPE_WITH_PREFIX},
            {JsonConstants.JSON_PROJECT_CONSTRUCTION_CLASSIFICATION,
                    DIMENSION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX},
    };

    //
    public static final String PROPERTY_USER_ID = "userId";
    public static final String PROPERTY_USER_NAME = "userName";

    //
    public static final String PROPERTY_COMPANY_NAME = "companyName";

}
