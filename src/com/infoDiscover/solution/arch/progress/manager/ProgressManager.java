package com.infoDiscover.solution.arch.progress.manager;

import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.arch.progress.constants.ProgressConstants;
import com.infoDiscover.solution.arch.progress.fact.ProgressFact;
import com.infoDiscover.solution.arch.progress.util.ProgressUtil;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun.
 */
public class ProgressManager {

    private final static Logger logger = LogManager.getLogger(ProgressManager.class);

    public Fact getProgressById(InformationExplorer ie, String progressId) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {

        ExploreParameters ep = new ExploreParameters();
        ep.setType(ProgressConstants.FACT_PROGRESS);
        ep.setDefaultFilteringItem(new EqualFilteringItem("progressId", progressId));

        return QueryExecutor.executeFactQuery(ie, ep);
    }

    public Fact getProgressById(String progressId) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {

        ExploreParameters ep = new ExploreParameters();
        ep.setType(ProgressConstants.FACT_PROGRESS);
        ep.setDefaultFilteringItem(new EqualFilteringItem("progressId", progressId));

        return QueryExecutor.executeFactQuery(ep);
    }

    public Fact createProgressFact(ProgressFact progress) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {
        logger.debug("Enter method createProgressFact() with progressId: " + progress.getProgressId
                ());

        Fact progressFact = getProgressById(progress.getProgressId());
        if (progressFact == null) {
            Map<String, Object> props = new HashMap<String, Object>();
            props.put("progressId", progress.getProgressId());
            props.put("content", progress.getContent());

            progressFact = ProgressUtil.createFact(ProgressConstants.FACT_PROGRESS, props);
        }
        logger.debug("Exit method createProgressFact()...");
        return progressFact;
    }
}
