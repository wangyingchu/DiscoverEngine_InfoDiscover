package com.infoDiscover.solution.construction.supervision.database;

/**
 * Created by sun.
 */
public class SupervisionSolutionConstants {
    // database space
    public final static String SOLUTION_PREFIX="ZHUHAI_";
    public final static String DATABASE_SPACE="ConstructionEngineeringSupervision";

    // Fact/Dimension/Relation Type
    public final static String FACT_PROGRESS_WITH_PREFIX = SOLUTION_PREFIX +  "PROGRESS";
    public final static String FACT_TASK_WITH_PREFIX = SOLUTION_PREFIX +  "TASK";
    public final static String DIMENSION_ROLE_WITH_PREFIX = SOLUTION_PREFIX +  "ROLE";
    public final static String DIMENSION_USER_WITH_PREFIX = SOLUTION_PREFIX +  "USER";
    public final static String DIMENSION_DEPARTMENT_WITH_PREFIX = SOLUTION_PREFIX +  "DEPARTMENT";

    public final static String RELATIONTYPE_PROGRESS_HASTASK_WITH_PREFIX = SOLUTION_PREFIX +  "HASTASK";
    public final static String RELATIONTYPE_SUBTASK_WITH_PREFIX = SOLUTION_PREFIX +  "SUBTASK";
    public final static String RELATIONTYPE_TASK_EXECUTEBYROLE_WITH_PREFIX = SOLUTION_PREFIX +  "EXECUTEBYROLE";
    public final static String RELATIONTYPE_TASK_EXECUTEBYDEPARTMENT_WITH_PREFIX = SOLUTION_PREFIX +
            "EXECUTEBYDEPARTMENT";
    public final static String RELATIONTYPE_TASK_EXECUTEBYUSER_WITH_PREFIX = SOLUTION_PREFIX +  "EXECUTEBYUSER";
    public final static String RELATIONTYPE_ROLE_HASUSER_WITH_PREFIX = SOLUTION_PREFIX +  "HASUSER";
    public final static String RELATIONTYPE_TRANSFER_WITH_PREFIX = SOLUTION_PREFIX +  "TRANSFERTASK";
    public final static String RELATIONTYPE_STARTAT_WITH_PREFIX = SOLUTION_PREFIX +  "STARTAT";
    public final static String RELATIONTYPE_ENDAT_WITH_PREFIX = SOLUTION_PREFIX +  "ENDAT";
}
