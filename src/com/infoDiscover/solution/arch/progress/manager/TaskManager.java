package com.infoDiscover.solution.arch.progress.manager;

import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.arch.progress.constants.ProgressConstants;
import com.infoDiscover.solution.arch.progress.fact.TaskFact;
import com.infoDiscover.solution.arch.progress.util.ProgressUtil;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun.
 */
public class TaskManager {
    private final static Logger logger = LoggerFactory.getLogger(TaskManager.class);

    public Fact getTaskById(InformationExplorer ie, String taskId) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {

        ExploreParameters ep = new ExploreParameters();
        ep.setType(ProgressConstants.FACT_TASK);
        ep.setDefaultFilteringItem(new EqualFilteringItem("taskId", taskId));

        return QueryExecutor.executeFactQuery(ie, ep);
    }

    public Fact getTaskById(String taskId) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {

        ExploreParameters ep = new ExploreParameters();
        ep.setType(ProgressConstants.FACT_TASK);
        ep.setDefaultFilteringItem(new EqualFilteringItem("taskId", taskId));

        return QueryExecutor.executeFactQuery(ep);
    }

    public Fact createTaskFact(TaskFact task) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {
        logger.debug("Enter method createTaskFact() with taskId: " + task.getTaskId());

        // check if task is already exists
        Fact taskFact = getTaskById(task.getTaskId());
        if (taskFact == null) {
            Map<String, Object> props = new HashMap<String, Object>();
            props.put("progressId", task.getProgressId());
            props.put("taskId", task.getTaskId());
            props.put("content", task.getContent());

            taskFact = ProgressUtil.createFact(ProgressConstants.FACT_TASK, props);
        }
        logger.debug("Exit method createTaskFact()...");
        return  taskFact;
    }
}
