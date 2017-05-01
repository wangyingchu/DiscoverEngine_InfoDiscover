package com.infoDiscover.solution.construction.supervision.database;

/**
 * Created by sun.
 */
public class SupervisionSolutionConstants {
    // database space
    public final static String SOLUTION_PREFIX = "ZHUHAI_";
    public final static String DATABASE_SPACE = "ConstructionEngineeringSupervision";

    // Fact type
    public final static String FACT_PROGRESS_WITH_PREFIX = SOLUTION_PREFIX + "PROGRESS";
    public final static String FACT_TASK_WITH_PREFIX = SOLUTION_PREFIX + "TASK";

    // Dimension type
    public final static String DIMENSION_ROLE_WITH_PREFIX = SOLUTION_PREFIX + "ROLE";
    public final static String DIMENSION_USER_WITH_PREFIX = SOLUTION_PREFIX + "USER";
    public final static String DIMENSION_DEPARTMENT_WITH_PREFIX = SOLUTION_PREFIX + "DEPARTMENT";

    public final static String DIMENSION_ASSET_FIRST_CLASSIFICATION_WITH_PREFIX =
            SOLUTION_PREFIX + "ASSET_FIRST_CLASSIFICATION";
    public final static String DIMENSION_ASSET_SECOND_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "ASSET_SECOND_CLASSIFICATION";
    public final static String DIMENSION_ASSIGN_MODEL_WITH_PREFIX = SOLUTION_PREFIX +
            "ASSIGN_MODEL";
    public final static String DIMENSION_Classification_WITH_PREFIX = SOLUTION_PREFIX +
            "CLASSIFICATION";
    public final static String DIMENSION_CONSTRUCTION_TYPE_WITH_PREFIX = SOLUTION_PREFIX +
            "CONSTRUCTION_TYPE";
    public final static String DIMENSION_ISSUE_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "ISSUE_CLASSIFICATION";
    public final static String DIMENSION_LAND_PROPERTY_WITH_PREFIX = SOLUTION_PREFIX +
            "LAND_PROPERTY";
    public final static String DIMENSION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX =
            SOLUTION_PREFIX + "PROJECT_CONSTRUCTION_CLASSIFICATION";
    public final static String DIMENSION_PROJECT_SCOPE_WITH_PREFIX = SOLUTION_PREFIX +
            "PROJECT_SCOPE";
    public final static String DIMENSION_PROJECT_SITE_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "PROJECT_SITE_CLASSIFICATION";
    public final static String DIMENSION_PROJECT_TYPE_WITH_PREFIX = SOLUTION_PREFIX +
            "PROJECT_TYPE";
    public final static String DIMENSION_PROVIDER_WITH_PREFIX = SOLUTION_PREFIX + "PROVIDER";

    // Relation type
    public final static String RELATION_ASSET_FIRST_CLASSIFICATION_WITH_PREFIX =
            SOLUTION_PREFIX + "ASSET_FIRST_CLASSIFICATION_IS";
    public final static String RELATION_ASSET_SECOND_CLASSIFICATION_WITH_PREFIX = SOLUTION_PREFIX +
            "ASSET_SECOND_CLASSIFICATION_IS";
    public final static String RELATION_ASSIGN_MODEL_WITH_PREFIX = SOLUTION_PREFIX +
            "ASSIGN_MODEL_IS";
    public final static String RELATION_CONSTRUCTION_TYPE_WITH_PREFIX = SOLUTION_PREFIX +
            "CONSTRUCTION_TYPE_IS";
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

    public final static String RELATION_PROGRESS_HASTASK_WITH_PREFIX = SOLUTION_PREFIX +
            "HAS_TASK";
    public final static String RELATION_SUBTASK_WITH_PREFIX = SOLUTION_PREFIX + "SUBTASK";
    public final static String RELATION_TASK_EXECUTEBYROLE_WITH_PREFIX = SOLUTION_PREFIX +
            "EXECUTE_BY_ROLE";
    public final static String RELATION_TASK_EXECUTEBYDEPARTMENT_WITH_PREFIX = SOLUTION_PREFIX +
            "EXECUTE_BY_DEPARTMENT";
    public final static String RELATION_TASK_EXECUTEBYUSER_WITH_PREFIX = SOLUTION_PREFIX +
            "EXECUTE_BY_USER";
    public final static String RELATION_ROLE_HASUSER_WITH_PREFIX = SOLUTION_PREFIX + "HAS_USER";
    public final static String RELATION_TRANSFER_WITH_PREFIX = SOLUTION_PREFIX +
            "TRANSFER_TASK";
    public final static String RELATION_STARTAT_WITH_PREFIX = SOLUTION_PREFIX + "START_AT";
    public final static String RELATION_ENDAT_WITH_PREFIX = SOLUTION_PREFIX + "END_AT";
}
