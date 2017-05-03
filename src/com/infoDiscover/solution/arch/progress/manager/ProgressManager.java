package com.infoDiscover.solution.arch.progress.manager;

import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.arch.progress.fact.ProgressFact;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.common.fact.FactManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun.
 */
public class ProgressManager {

    private final static Logger logger = LoggerFactory.getLogger(ProgressManager.class);

    public Fact getProgressById(InformationExplorer ie, String progressId, String factType) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {

        ExploreParameters ep = new ExploreParameters();
        ep.setType(factType);
        ep.setDefaultFilteringItem(new EqualFilteringItem("progressId", progressId));

        return QueryExecutor.executeFactQuery(ie, ep);
    }

    public Fact createProgressFact(InfoDiscoverSpace ids, ProgressFact progress) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {
        logger.debug("Enter method createProgressFact() with progressId: " + progress.getProgressId
                ());

        Fact progressFact = getProgressById(ids.getInformationExplorer(), progress.getProgressId(), progress.getFactType
                ());
        if (progressFact == null) {
            Map<String, Object> props = new HashMap<String, Object>();
            props.put("progressId", progress.getProgressId());
            props.put("content", progress.getContent());

            FactManager manager = new FactManager(ids);
            progressFact = manager.createFact(progress.getFactType(), props);
        }
        logger.debug("Exit method createProgressFact()...");
        return progressFact;
    }
}
