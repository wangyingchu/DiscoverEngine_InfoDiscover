package com.infoDiscover.solution.common.relationship;

import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.common.path.OrientDBShortestPath;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by sun.
 */
public class RelationshipManager {
    private final static Logger logger = LoggerFactory.getLogger(RelationshipManager.class);

    public boolean checkRelationship(OrientGraph graph, String fromVertexRID, String toVertexRID) {
        List<OrientVertex> list = OrientDBShortestPath.getVerticesFromShortestPath(graph, fromVertexRID,
                toVertexRID);
        return list.size() > 0;
    }

}
