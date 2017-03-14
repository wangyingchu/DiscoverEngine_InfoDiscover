package com.infoDiscover.solution.arch.progress.constants;

import com.infoDiscover.common.PrefixConstant;

/**
 * Created by sun.
 */
public class ProgressConstants {
    public final static String prefix = PrefixConstant.prefix;
    public final static String FACT_PROGRESS = prefix +  "PROGRESS";
    public final static String FACT_TASK = prefix +  "TASK";
    public final static String DIMENSION_ROLE = prefix +  "ROLE";
    public final static String DIMENSION_USER = prefix +  "USER";
    public final static String DIMENSION_DEPARTMENT = prefix +  "DEPARTMENT";

    public final static String RELATIONTYPE_PROGRESS_HASTASK = prefix +  "HASTASK";
    public final static String RELATIONTYPE_SUBTASK = prefix +  "SUBTASK";
    public final static String RELATIONTYPE_TASK_EXECUTEBYROLE = prefix +  "EXECUTEBYROLE";
    public final static String RELATIONTYPE_TASK_EXECUTEBYUSER = prefix +  "EXECUTEBYUSER";
    public final static String RELATIONTYPE_ROLE_HASUSER = prefix +  "HASUSER";
    public final static String RELATIONTYPE_TRANSFER = prefix +  "TRANSFERTASK";
    public final static String RELATIONTYPE_STARTAT = prefix +  "STARTAT";
    public final static String RELATIONTYPE_ENDAT = prefix +  "ENDAT";
}
