package com.infoDiscover.solution.common.cache.graph;

import com.infoDiscover.solution.common.path.helper.Graph;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

/**
 * Created by sun.
 */
public interface IOrientDBGraphService {
    public Graph getGraphFromOrientGraph(OrientGraph graph);
}
