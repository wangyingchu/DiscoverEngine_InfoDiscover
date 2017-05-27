package com.infoDiscover.solution.arch.progress.analytics;

import com.infoDiscover.common.util.Util;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relationable;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.solution.arch.progress.constants.ProgressConstants;
import com.infoDiscover.solution.arch.progress.manager.ProgressSqlBuilder;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by sun.
 */
public class ProgressAnalytics {

    private final static Logger logger = LoggerFactory.getLogger(ProgressAnalytics.class);


    public List<Relationable> getAllTasksOfProgress(InfoDiscoverSpace ids, String progressId)
            throws
            InfoDiscoveryEngineInfoExploreException {

        String selectProgressSql = ProgressSqlBuilder.buildSelectProgressSQL(progressId);

        String sql = "select from (TRAVERSE out() FROM " + selectProgressSql + ") where @class=" +
                Util.addDoubleQuotation(InfoDiscoverEngineConstant.CLASSPERFIX_FACT +
                        ProgressConstants.FACT_TASK_WITHPREFIX);

        logger.info("getAllTasksOfProgress sql: {}", sql);
        List<Relationable> results = QueryExecutor.executeFactQuery(ids, sql);
        return results;
    }

    public List<Relationable> getAllUsersOfTask(InfoDiscoverSpace ids, String taskId) throws
            InfoDiscoveryEngineInfoExploreException {
        String selectProgressSql = ProgressSqlBuilder.buildSelectTaskByIdSQL(taskId);

        String sql = "select from (TRAVERSE out() FROM " + selectProgressSql + ") where @class=" +
                Util.addDoubleQuotation(InfoDiscoverEngineConstant.CLASSPERFIX_FACT +
                        ProgressConstants.DIMENSION_USER_WITHPREFIX);

        System.out.println("getAllUsersOfTask sql: " + sql);

        List<Relationable> results = QueryExecutor.executeFactQuery(ids, sql);
        return results;
    }

    public List<Relationable> getAllUsersOfProgress(InfoDiscoverSpace ids, String progressId) throws
            InfoDiscoveryEngineInfoExploreException {
        String selectProgressSql = ProgressSqlBuilder.buildSelectProgressSQL(progressId);

        String sql = "select from (TRAVERSE out() FROM " + selectProgressSql + ") where @class=" +
                Util.addDoubleQuotation(InfoDiscoverEngineConstant.CLASSPERFIX_FACT +
                        ProgressConstants.DIMENSION_USER_WITHPREFIX);

        logger.info("getAllUsersOfProgress sql: {}", sql);

        List<Relationable> results = QueryExecutor.executeFactQuery(ids, sql);
        return results;
    }

    public List<Relationable> getAllTasksOfUser(InfoDiscoverSpace ids, String userId) throws
            InfoDiscoveryEngineInfoExploreException {
        String selectProgressSql = ProgressSqlBuilder.buildSelectTaskByUserIdSQL(userId);

        String sql = "select from (TRAVERSE in() FROM " + selectProgressSql + ") where @class=" +
                Util.addDoubleQuotation(InfoDiscoverEngineConstant.CLASSPERFIX_FACT +
                        ProgressConstants.FACT_TASK_WITHPREFIX);

        System.out.println("getAllTasksOfUser sql: " + sql);

        List<Relationable> results = QueryExecutor.executeFactQuery(ids, sql);
        return results;
    }
}
