package com.infoDiscover.solution.common.relationship;

import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relation;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.common.path.OrientDBShortestPath;
import com.infoDiscover.solution.common.util.Constants;
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
        List<OrientVertex> list = OrientDBShortestPath.getVerticesFromShortestPath(graph,
                fromVertexRID,
                toVertexRID);
        return list.size() > 0;
    }

    public Relation attachFactToDimension(InfoDiscoverSpace ids, String factId, String dimensionId,
                                          String dimensionType, String relationType) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(dimensionType);
        ep.setDefaultFilteringItem(new EqualFilteringItem(Constants.DIMENSION_ID, dimensionId));
        Dimension projectTypeDimension = QueryExecutor.executeDimensionQuery(ids
                .getInformationExplorer(), ep);

        return ids.attachFactToDimension(factId, projectTypeDimension.getId(), relationType);
    }
}
