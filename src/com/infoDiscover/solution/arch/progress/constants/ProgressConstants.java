package com.infoDiscover.solution.arch.progress.constants;

import com.infoDiscover.common.PrefixConstant;

/**
 * Created by sun.
 */
public class ProgressConstants {
    public static final String prefix = PrefixConstant.prefix;
    public static final String FACT_PROGRESS_WITHPREFIX = prefix +  "PROGRESS";
    public static final String FACT_TASK_WITHPREFIX = prefix +  "TASK";
    public static final String DIMENSION_ROLE_WITHPREFIX = prefix +  "ROLE";
    public static final String DIMENSION_USER_WITHPREFIX = prefix +  "USER";
    public static final String DIMENSION_DEPARTMENT_WITHPREFIX = prefix +  "DEPARTMENT";

    public static final String RELATIONTYPE_PROGRESS_HASTASK_WITHPREFIX = prefix +  "HASTASK";
    public static final String RELATIONTYPE_SUBTASK_WITHPREFIX = prefix +  "SUBTASK";
    public static final String RELATIONTYPE_TASK_EXECUTEBYROLE_WITHPREFIX = prefix +  "EXECUTEBYROLE";
    public static final String RELATIONTYPE_TASK_EXECUTEBYUSER_WITHPREFIX = prefix +  "EXECUTEBYUSER";
    public static final String RELATIONTYPE_ROLE_HASUSER_WITHPREFIX = prefix +  "HASUSER";
    public static final String RELATIONTYPE_TRANSFER_WITHPREFIX = prefix +  "TRANSFERTASK";
    public static final String RELATIONTYPE_STARTAT_WITHPREFIX = prefix +  "STARTAT";
    public static final String RELATIONTYPE_ENDAT_WITHPREFIX = prefix +  "ENDAT";
}
