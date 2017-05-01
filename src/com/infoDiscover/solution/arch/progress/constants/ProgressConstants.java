package com.infoDiscover.solution.arch.progress.constants;

import com.infoDiscover.common.PrefixConstant;

/**
 * Created by sun.
 */
public class ProgressConstants {
    public final static String prefix = PrefixConstant.prefix;
    public final static String FACT_PROGRESS_WITHPREFIX = prefix +  "PROGRESS";
    public final static String FACT_TASK_WITHPREFIX = prefix +  "TASK";
    public final static String DIMENSION_ROLE_WITHPREFIX = prefix +  "ROLE";
    public final static String DIMENSION_USER_WITHPREFIX = prefix +  "USER";
    public final static String DIMENSION_DEPARTMENT_WITHPREFIX = prefix +  "DEPARTMENT";

    public final static String RELATIONTYPE_PROGRESS_HASTASK_WITHPREFIX = prefix +  "HASTASK";
    public final static String RELATIONTYPE_SUBTASK_WITHPREFIX = prefix +  "SUBTASK";
    public final static String RELATIONTYPE_TASK_EXECUTEBYROLE_WITHPREFIX = prefix +  "EXECUTEBYROLE";
    public final static String RELATIONTYPE_TASK_EXECUTEBYUSER_WITHPREFIX = prefix +  "EXECUTEBYUSER";
    public final static String RELATIONTYPE_ROLE_HASUSER_WITHPREFIX = prefix +  "HASUSER";
    public final static String RELATIONTYPE_TRANSFER_WITHPREFIX = prefix +  "TRANSFERTASK";
    public final static String RELATIONTYPE_STARTAT_WITHPREFIX = prefix +  "STARTAT";
    public final static String RELATIONTYPE_ENDAT_WITHPREFIX = prefix +  "ENDAT";
}
