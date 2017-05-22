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
public class ProgressManager {
    private final static Logger logger = LoggerFactory.getLogger(ProgressManager.class);

    public Fact getProgressById(InformationExplorer ie, String factType, String key, String
                                progressId)  {

        ExploreParameters ep = new ExploreParameters();
        ep.setType(factType);
        if(key == null) {
            key = JsonConstants.JSON_PROGRESS_ID;
        }
        ep.setDefaultFilteringItem(new EqualFilteringItem(key, progressId));

        return QueryExecutor.executeFactQuery(ie, ep);
    }
}
