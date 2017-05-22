package com.infoDiscover.solution.arch.progress.manager;

import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.arch.progress.fact.TaskFact;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.common.fact.FactManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun.
 */
public class DemoTaskManager {
    private final static Logger logger = LoggerFactory.getLogger(DemoTaskManager.class);

    public Fact getTaskById(InformationExplorer ie, String taskId, String factType) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {

        ExploreParameters ep = new ExploreParameters();
        ep.setType(factType);
        ep.setDefaultFilteringItem(new EqualFilteringItem("taskId", taskId));

        return QueryExecutor.executeFactQuery(ie, ep);
    }


    public Fact createTaskFact(InfoDiscoverSpace ids, TaskFact task, String factType) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {
        logger.debug("Enter method createTaskFact() with taskId: " + task.getTaskId());

        // check if task is already exists
        Fact taskFact = getTaskById(ids.getInformationExplorer(), task.getTaskId(), factType);
        if (taskFact == null) {
            Map<String, Object> props = new HashMap<String, Object>();
            props.put("progressId", task.getProgressId());
            props.put("taskId", task.getTaskId());
            props.put("content", task.getContent());

            FactManager manager = new FactManager(ids);
            taskFact = manager.createFact(factType, props);
        }
        logger.debug("Exit method createTaskFact()...");
        return  taskFact;
    }
}
