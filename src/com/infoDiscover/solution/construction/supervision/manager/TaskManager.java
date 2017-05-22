package com.infoDiscover.solution.construction.supervision.manager;

import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.construction.supervision.constants.JsonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sun.
 */
public class TaskManager {
    private final static Logger logger = LoggerFactory.getLogger(TaskManager.class);

    public Fact getTaskById(InformationExplorer ie, String factType, String key, String taskId) {

        ExploreParameters ep = new ExploreParameters();
        ep.setType(factType);
        if (key == null) {
            key = JsonConstants.JSON_TASK_ID;
        }
        ep.setDefaultFilteringItem(new EqualFilteringItem(key, taskId));

        return QueryExecutor.executeFactQuery(ie, ep);
    }
}
