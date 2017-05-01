package com.infoDiscover.solution.arch.progress.manager;

import com.infoDiscover.solution.arch.progress.constants.ProgressConstants;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationType;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.SQLBuilder;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sun.
 */
public class ProgressSqlBuilder {
    private final static Logger logger = LoggerFactory.getLogger(ProgressSqlBuilder.class);

    public static String buildSelectProgressSQL(String progressId) throws InfoDiscoveryEngineInfoExploreException {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(ProgressConstants.FACT_PROGRESS_WITHPREFIX);
        ep.setDefaultFilteringItem(new EqualFilteringItem("progressId", progressId));

        String sql = "(" + SQLBuilder.buildQuerySQL(InformationType.FACT, ep) + ")";
        System.out.println("buildSelectProgressSQL: " + sql);

        return  sql;
    }

    public static String buildSelectTaskByIdSQL(String taskId) throws
            InfoDiscoveryEngineInfoExploreException {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(ProgressConstants.FACT_TASK_WITHPREFIX);
        ep.setDefaultFilteringItem(new EqualFilteringItem("taskId", taskId));

        String sql = "(" + SQLBuilder.buildQuerySQL(InformationType.FACT, ep) + ")";
        System.out.println("buildSelectTaskByIdSQL: " + sql);

        return  sql;
    }

    public static String buildSelectTaskByUserIdSQL(String userId) throws
            InfoDiscoveryEngineInfoExploreException {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(ProgressConstants.DIMENSION_USER_WITHPREFIX);
        ep.setDefaultFilteringItem(new EqualFilteringItem("userId", userId));

        String sql = "(" + SQLBuilder.buildQuerySQL(InformationType.FACT, ep) + ")";
        System.out.println("buildSelectTaskByIdSQL: " + sql);

        return  sql;
    }
}
