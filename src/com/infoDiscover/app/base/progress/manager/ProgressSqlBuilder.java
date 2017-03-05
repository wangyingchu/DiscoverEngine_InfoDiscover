package com.infoDiscover.app.base.progress.manager;

import com.infoDiscover.app.base.progress.constants.ProgressConstants;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationType;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.SQLBuilder;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;

/**
 * Created by sun.
 */
public class ProgressSqlBuilder {
    public static String buildSelectProgressSQL(String progressId) throws InfoDiscoveryEngineInfoExploreException {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(ProgressConstants.FACT_PROGRESS);
        ep.setDefaultFilteringItem(new EqualFilteringItem("progressId", progressId));

        String sql = "(" + SQLBuilder.buildQuerySQL(InformationType.FACT, ep) + ")";
        System.out.println("buildSelectProgressSQL: " + sql);

        return  sql;
    }

    public static String buildSelectTaskByIdSQL(String taskId) throws
            InfoDiscoveryEngineInfoExploreException {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(ProgressConstants.FACT_TASK);
        ep.setDefaultFilteringItem(new EqualFilteringItem("taskId", taskId));

        String sql = "(" + SQLBuilder.buildQuerySQL(InformationType.FACT, ep) + ")";
        System.out.println("buildSelectTaskByIdSQL: " + sql);

        return  sql;
    }

    public static String buildSelectTaskByUserIdSQL(String userId) throws
            InfoDiscoveryEngineInfoExploreException {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(ProgressConstants.FACT_USER);
        ep.setDefaultFilteringItem(new EqualFilteringItem("userId", userId));

        String sql = "(" + SQLBuilder.buildQuerySQL(InformationType.FACT, ep) + ")";
        System.out.println("buildSelectTaskByIdSQL: " + sql);

        return  sql;
    }
}
