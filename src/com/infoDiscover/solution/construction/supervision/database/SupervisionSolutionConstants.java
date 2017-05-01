package com.infoDiscover.solution.construction.supervision.database;

/**
 * Created by sun.
 */
public class SupervisionSolutionConstants {
    // database space
    public final static String SOLUTION_PREFIX="ZHUHAI_";
    public final static String DATABASE_SPACE="ConstructionEngineeringSupervision";

    // Fact/Dimension/Relation Type
    public final static String FACT_PROGRESS = SOLUTION_PREFIX +  "PROGRESS";
    public final static String FACT_TASK = SOLUTION_PREFIX +  "TASK";
    public final static String DIMENSION_ROLE = SOLUTION_PREFIX +  "ROLE";
    public final static String DIMENSION_USER = SOLUTION_PREFIX +  "USER";
    public final static String DIMENSION_DEPARTMENT = SOLUTION_PREFIX +  "DEPARTMENT";

    public final static String RELATIONTYPE_PROGRESS_HASTASK = SOLUTION_PREFIX +  "HASTASK";
    public final static String RELATIONTYPE_SUBTASK = SOLUTION_PREFIX +  "SUBTASK";
    public final static String RELATIONTYPE_TASK_EXECUTEBYROLE = SOLUTION_PREFIX +  "EXECUTEBYROLE";
    public final static String RELATIONTYPE_TASK_EXECUTEBYDEPARTMENT = SOLUTION_PREFIX +
            "EXECUTEBYDEPARTMENT";
    public final static String RELATIONTYPE_TASK_EXECUTEBYUSER = SOLUTION_PREFIX +  "EXECUTEBYUSER";
    public final static String RELATIONTYPE_ROLE_HASUSER = SOLUTION_PREFIX +  "HASUSER";
    public final static String RELATIONTYPE_TRANSFER = SOLUTION_PREFIX +  "TRANSFERTASK";
    public final static String RELATIONTYPE_STARTAT = SOLUTION_PREFIX +  "STARTAT";
    public final static String RELATIONTYPE_ENDAT = SOLUTION_PREFIX +  "ENDAT";
}
