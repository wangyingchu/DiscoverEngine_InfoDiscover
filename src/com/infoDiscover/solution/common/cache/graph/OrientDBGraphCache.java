package com.infoDiscover.solution.common.cache.graph;

import com.infoDiscover.solution.common.cache.BaseLoadingCache;
import com.infoDiscover.solution.common.cache.ILocalCache;
import com.infoDiscover.solution.common.path.OrientDBGraphHelper;
import com.infoDiscover.solution.common.path.helper.Graph;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

import java.util.concurrent.ExecutionException;

/**
 * Created by sun.
 */
public class OrientDBGraphCache extends BaseLoadingCache<OrientGraph, Graph> implements
        ILocalCache<OrientGraph, Graph> {

    @Override
    public Graph get(OrientGraph key) {
        try {
            return getValue(key);
        } catch (ExecutionException e) {
            logger.error("Could not find graph with spaceName: {}", key, e);
            return null;
        }
    }

    @Override
    protected Graph loadData(OrientGraph key) {
        logger.debug("Generate graph: {} from orientGraph", key);
        return OrientDBGraphHelper.generateGraphFromOrientDB(key);
    }
}
