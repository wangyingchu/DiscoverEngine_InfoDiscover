package com.infoDiscover.solution.arch.progress.analytics;

import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.common.util.Helper;
import com.infoDiscover.solution.arch.progress.constants.ProgressConstants;
import com.infoDiscover.solution.arch.progress.manager.ProgressSqlBuilder;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relationable;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;

import java.util.List;

/**
 * Created by sun.
 */
public class ProgressAnalytics {
    public List<Relationable> getAllTasksOfProgress(String progressId) throws
            InfoDiscoveryEngineInfoExploreException {

        String selectProgressSql = ProgressSqlBuilder.buildSelectProgressSQL(progressId);

        String sql = "select from (TRAVERSE out() FROM " + selectProgressSql + ") where @class=" +
                Helper.addDoubleQuotation(InfoDiscoverEngineConstant.CLASSPERFIX_FACT +
                        ProgressConstants.FACT_TASK);

        System.out.println("getAllTasksOfProgress sql: " + sql);

        List<Relationable> results = QueryExecutor.executeFactQuery(sql);
        return results;
    }

    public List<Relationable> getAllUsersOfProgress(String progressId) throws
            InfoDiscoveryEngineInfoExploreException {
        String selectProgressSql = ProgressSqlBuilder.buildSelectProgressSQL(progressId);

        String sql = "select from (TRAVERSE out() FROM " + selectProgressSql + ") where @class=" +
                Helper.addDoubleQuotation(InfoDiscoverEngineConstant.CLASSPERFIX_FACT +
                        ProgressConstants.DIMENSION_USER);

        System.out.println("getAllUsersOfProgress sql: " + sql);

        List<Relationable> results = QueryExecutor.executeFactQuery(sql);
        return results;
    }

    public List<Relationable> getAllUsersOfTask(String taskId) throws
            InfoDiscoveryEngineInfoExploreException {
        String selectProgressSql = ProgressSqlBuilder.buildSelectTaskByIdSQL(taskId);

        String sql = "select from (TRAVERSE out() FROM " + selectProgressSql + ") where @class=" +
                Helper.addDoubleQuotation(InfoDiscoverEngineConstant.CLASSPERFIX_FACT +
                        ProgressConstants.DIMENSION_USER);

        System.out.println("getAllUsersOfTask sql: " + sql);

        List<Relationable> results = QueryExecutor.executeFactQuery(sql);
        return results;
    }

    public List<Relationable> getAllTasksOfUser(String userId) throws
            InfoDiscoveryEngineInfoExploreException {
        String selectProgressSql = ProgressSqlBuilder.buildSelectTaskByUserIdSQL(userId);

        String sql = "select from (TRAVERSE in() FROM " + selectProgressSql + ") where @class=" +
                Helper.addDoubleQuotation(InfoDiscoverEngineConstant.CLASSPERFIX_FACT +
                        ProgressConstants.FACT_TASK);

        System.out.println("getAllTasksOfUser sql: " + sql);

        List<Relationable> results = QueryExecutor.executeFactQuery(sql);
        return results;
    }
}
