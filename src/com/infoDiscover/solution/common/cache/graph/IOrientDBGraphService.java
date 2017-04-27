package com.infoDiscover.solution.common.cache.graph;

import com.infoDiscover.solution.common.path.helper.GenericGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

/**
 * Created by sun.
 */
public interface IOrientDBGraphService {
    public GenericGraph getGraphFromOrientGraph(OrientGraph graph);
}
