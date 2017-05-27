package com.infoDiscover.solution.construction.supervision.constants;

import com.infoDiscover.solution.construction.supervision.sample.PrepareSampleData;

/**
 * Created by sun.
 */
public class DatabaseConstants {
    // database space
    public final static String SOLUTION_PREFIX = PrepareSampleData.prefix;
    public final static String DATABASE_SPACE = SOLUTION_PREFIX +
            "ConstructionEngineeringSupervision";

    // Fact type
    //public final static String FACT_PROJECT_WITH_PREFIX = SOLUTION_PREFIX + "PROJECT";
    public final static String FACT_PROJECT_WITH_PREFIX = SOLUTION_PREFIX;
    public final static String FACT_TASK_WITH_PREFIX = SOLUTION_PREFIX + "TASK";

    // Dimension type
    public final static String DIMENSION_ROLE_WITH_PREFIX = SOLUTION_PREFIX + "ROLE";
    public final static String DIMENSION_USER_WITH_PREFIX = SOLUTION_PREFIX + "USER";

    public final static String DIMENSION_CONSTRUCTION_TYPE_WITH_PREFIX = SOLUTION_PREFIX +
            "CONSTRUCTION_TYPE";
    public final static String DIMENSION_COMPANY_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "COMPANY_CLASSIFICATION";
    public final static String DIMENSION_ASSIGN_MODEL_WITH_PREFIX = SOLUTION_PREFIX +
            "ASSIGN_MODEL";
    public final static String DIMENSION_EXECUTIVE_DEPARTMENT_WITH_PREFIX = SOLUTION_PREFIX +
            "EXECUTIVE_DEPARTMENT";
    public final static String DIMENSION_GOVERNMENT_APPROVAL_AUTHORITY_WITH_PREFIX =
            SOLUTION_PREFIX + "GOVERNMENT_APPROVAL_AUTHORITY";
    public final static String DIMENSION_ISSUE_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "ISSUE_CLASSIFICATION";
    public final static String DIMENSION_LAND_PROPERTY_WITH_PREFIX = SOLUTION_PREFIX +
            "LAND_PROPERTY";
    public final static String DIMENSION_ASSET_FIRST_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "ASSERT_FIRST_CLASSIFICATION";
    public final static String DIMENSION_ASSET_SECOND_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX
            + "ASSET_SECOND_CLASSIFICATION";
    public final static String DIMENSION_PROJECT_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "PROJECT_CLASSIFICATION";
    public final static String DIMENSION_PROJECT_SITE_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX
            + "PROJECT_SITE_CLASSIFICATION";
    public final static String DIMENSION_PROJECT_SCOPE_WITH_PREFIX = SOLUTION_PREFIX +
            "PROJECT_SCOPE";
    public final static String DIMENSION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX =
            SOLUTION_PREFIX + "PROJECT_CONSTRUCTION_CLASSIFICATION";

    public final static String DIMENSION_ROAD_WITH_PREFIX = SOLUTION_PREFIX + "ROAD";
    public final static String DIMENSION_COMPANY_WITH_PREFIX = SOLUTION_PREFIX + "COMPANY";


    public final static String DIMENSION_EXTERNAL_USER_WITH_PREFIX = SOLUTION_PREFIX +
            "EXTERNAL_USER";

    public final static String DIMENSION_PROJECT_ADDRESS_WITH_PREFIX = SOLUTION_PREFIX +
            "PROJECT_ADDRESS";

    // Relation type
    public final static String RELATION_PROJECT_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "PROJECT_CLASSIFICATION_IS";
    public final static String RELATION_ASSET_FIRST_CLASSIFICATION_WITH_PREFIX =
            SOLUTION_PREFIX + "ASSET_FIRST_CLASSIFICATION_IS";
    public final static String RELATION_ASSET_SECOND_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "ASSET_SECOND_CLASSIFICATION_IS";
    public final static String RELATION_ASSIGN_MODEL_WITH_PREFIX = SOLUTION_PREFIX +
            "ASSIGN_MODEL_IS";
    public final static String RELATION_CONSTRUCTION_TYPE_WITH_PREFIX = SOLUTION_PREFIX +
            "CONSTRUCTION_TYPE_IS";
    public final static String RELATION_COMPANY_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "COMPANY_CLASSIFICATION_IS";
    public final static String RELATION_ISSUE_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "ISSUE_CLASSIFICATION_IS";
    public final static String RELATION_LAND_PROPERTY_WITH_PREFIX = SOLUTION_PREFIX +
            "LAND_PROPERTY_IS";
    public final static String RELATION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX =
            SOLUTION_PREFIX + "PROJECT_CONSTRUCTION_CLASSIFICATION_IS";
    public final static String RELATION_PROJECT_SCOPE_WITH_PREFIX = SOLUTION_PREFIX +
            "PROJECT_SCOPE_IS";
    public final static String RELATION_PROJECT_SITE_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "PROJECT_SITE_CLASSIFICATION_IS";
    public final static String RELATION_PROJECT_TYPE_WITH_PREFIX = SOLUTION_PREFIX +
            "PROJECT_TYPE_IS";
    public final static String RELATION_PROVIDER_WITH_PREFIX = SOLUTION_PREFIX + "PROVIDER_IS";

    public final static String RELATION_PROJECT_HASTASK_WITH_PREFIX = SOLUTION_PREFIX +
            "HAS_TASK";
    public final static String RELATION_SUBTASK_WITH_PREFIX = SOLUTION_PREFIX + "SUBTASK";
    public final static String RELATION_TASK_EXECUTE_BY_ROLE_WITH_PREFIX = SOLUTION_PREFIX +
            "EXECUTE_BY_ROLE";
    public final static String RELATION_TASK_EXECUTE_BY_DEPARTMENT_WITH_PREFIX = SOLUTION_PREFIX +
            "EXECUTE_BY_DEPARTMENT";
    public final static String RELATION_TASK_EXECUTE_BY_USER_WITH_PREFIX = SOLUTION_PREFIX +
            "EXECUTE_BY_USER";
    public final static String RELATION_EXECUTIVE_DEPARTMENT_HAS_USER_WITH_PREFIX =
            SOLUTION_PREFIX + "HAS_USER";
    public final static String RELATION_TRANSFER_WITH_PREFIX = SOLUTION_PREFIX +
            "TRANSFER_TASK";
    public final static String RELATION_STARTAT_WITH_PREFIX = SOLUTION_PREFIX + "START_AT";
    public final static String RELATION_ENDAT_WITH_PREFIX = SOLUTION_PREFIX + "END_AT";

    public final static String RELATION_IS_MEMBER_OF_COMPANY_WITH_PREFIX = SOLUTION_PREFIX +
            "IS_MEMBER_OF";

    public final static String RELATION_IS_COMPANY_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "IS_COMPANY_OF";

    public final static String RELATION_LOCATED_AT_ROAD_WITH_PREFIX = SOLUTION_PREFIX +
            "LOCATED_AT_ROAD";

    public final static String RELATION_START_BY_WITH_PREFIX = SOLUTION_PREFIX +
            "START_BY";

    public final static String RELATION_PROJECT_ADDRESS_AT_WITH_PREFIX = SOLUTION_PREFIX +
            "PROJECT_AT";

    public final static String RELATION_IN_CHARGE_OF_WITH_PREFIX = SOLUTION_PREFIX +
            "IS_IN_CHARGE_OF";
    public final static String RELATION_EXECUTE_BY_COMPANY_WITH_PREFIX = SOLUTION_PREFIX +
            "EXECUTE_BY_COMPANY";


    //
    public final static String PROPERTY_USER_ID = "userId";
    public final static String PROPERTY_USER_NAME = "userName";

    //
    public final static String PROPERTY_COMPANY_NAME = "companyName";
    
}
