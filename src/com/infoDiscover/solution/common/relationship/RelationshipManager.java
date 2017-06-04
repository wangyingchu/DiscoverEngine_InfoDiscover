package com.infoDiscover.solution.common.relationship;

import com.infoDiscover.common.dimension.time.TimeDimensionGenerator;
import com.infoDiscover.common.dimension.time.dimension.DayDimensionVO;
import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relation;
import com.infoDiscover.infoDiscoverEngine.dataMartImpl.OrientDBDimensionImpl;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.common.dimension.DimensionManager;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.common.path.OrientDBShortestPath;
import com.tinkerpop.blueprints.Direction;
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

    private InfoDiscoverSpace ids;

    public RelationshipManager(InfoDiscoverSpace ids) {
        this.ids = ids;
    }

    public boolean checkRelationship(OrientGraph graph, String fromVertexRID, String toVertexRID) {
        List<OrientVertex> list = OrientDBShortestPath.getVerticesFromShortestPath(graph,
                fromVertexRID,
                toVertexRID);
        return list.size() > 0;
    }

    public Relation attachFactToDimension(String factId, ExploreParameters
            dimensionEp, String relationType) throws InfoDiscoveryEngineRuntimeException,
            InfoDiscoveryEngineInfoExploreException {
        Dimension dimension = QueryExecutor.executeDimensionQuery(ids.getInformationExplorer(),
                dimensionEp);
        if (dimension == null) {
            return null;
        }

        return ids.attachFactToDimension(factId, dimension.getId(), relationType);
    }

    public boolean isDirectlyLinked(Dimension fromDimension, Dimension
            toDimension, String relationType) {

        String relationTypeClassName = InfoDiscoverEngineConstant.CLASSPERFIX_RELATION +
                relationType;

        OrientVertex fromVertex = ((OrientDBDimensionImpl) fromDimension).getDimensionVertex();
        OrientVertex toVertex = ((OrientDBDimensionImpl) toDimension).getDimensionVertex();

        Iterable<Edge> edgeIterator = fromVertex.getEdges(toVertex, Direction.OUT,
                relationTypeClassName);
        return edgeIterator.iterator().hasNext();
    }

    public Relation linkFactToDimension(Fact fromFact,
                                        Dimension toDimension,
                                        String relationType) {
        logger.info("Enter method linkFactToDimensionByRelationType() with fromFactId: {} and " +
                "toDimensionId: {} and relationType: {}", fromFact.getId(), toDimension
                .getId(), relationType);

        if (fromFact == null) {
            logger.error("fromFact should not be null");
            return null;
        }

        if (toDimension == null) {
            logger.error("toDimension should not be null");
            return null;
        }

        logger.debug("Exit method linkFactToDimensionByRelationType()...");

        return attachFactToDimension(fromFact.getId(), toDimension.getId(),
                relationType, true);
    }

    public Relation linkFactsByRelationType(Fact fromFact, Fact toFact,
                                            String relationType) {
        logger.debug("Enter method linkFactsByRelationType() with fromFactId: " + fromFact.getId() +
                " " + "and " + "toFactId: " + toFact.getId() + " and relationType: " +
                relationType);

        if (fromFact == null) {
            logger.error("fromFact should not be null");
            return null;
        }

        if (toFact == null) {
            logger.error("toFact should not be null");
            return null;
        }

        try {
            if (!ids.hasRelationType(relationType)) {
                ids.addRelationType(relationType);
            }
            Relation r = ids.addDirectionalFactRelation(fromFact, toFact, relationType, false);
            return r;
        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineDataMartException e) {
            e.printStackTrace();
        }

        logger.debug("Exit method linkFactsByRelationType()...");
        return null;
    }

    public Relation linkDimensionsByRelationType(Dimension fromDimension,
                                                 Dimension toDimension,
                                                 String relationType) {
        logger.debug("Enter method linkDimensionsByRelationType() with fromDimensionId: " +
                fromDimension.getId() + " and toDimensionId: " + toDimension.getId() + " and " +
                "relationType: " + relationType);

        if (fromDimension == null) {
            logger.error("fromDimension should not be null");
            return null;
        }

        if (toDimension == null) {
            logger.error("toDimension should not be null");
            return null;
        }

        try {

            if (!ids.hasRelationType(relationType)) {
                ids.addRelationType(relationType);
            }

            Relation r = ids.addDirectionalDimensionRelation(fromDimension, toDimension,
                    relationType, false);
            return r;
        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineDataMartException e) {
            e.printStackTrace();
        }

        logger.debug("Exit method linkDimensionsByRelationType()...");
        return null;
    }

    public Relation attachFactToDimension(String factId,
                                          String dimensionType,
                                          String key, String value,
                                          String relationType,
                                          boolean toCreateRelationTypeIfNotExisted) {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(dimensionType);
        ep.setDefaultFilteringItem(new EqualFilteringItem(key, value));
        Dimension dimension = QueryExecutor.executeDimensionQuery(ids
                .getInformationExplorer(), ep);

        if (dimension == null) {
            return null;
        }

        return attachFactToDimension(factId, dimension.getId(), relationType,
                toCreateRelationTypeIfNotExisted);
    }

    public Relation attachFactToDimension(String fromFactId, String toDimensionId,
                                          String relationType,
                                          boolean toCreateRelationTypeIfNotExisted) {

        if (!toCreateRelationTypeIfNotExisted && !ids.hasRelationType(relationType)) {
            return null;
        }

        try {

            if (toCreateRelationTypeIfNotExisted) {
                if (!ids.hasRelationType(relationType)) {
                    ids.addRelationType(relationType);
                }
            }

            return ids.attachFactToDimension(fromFactId, toDimensionId, relationType);
        } catch (InfoDiscoveryEngineDataMartException e) {
            logger.error("Failed to attachFact: {} to dimension: {}", fromFactId, toDimensionId);
        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.error("Failed to attachFact: {} to dimension: {}", fromFactId, toDimensionId);
        }

        return null;
    }

    public Relation linkFactToDateDimension(
            String prefix,
            Fact fact,
            DayDimensionVO dayDimension,
            String relationType) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException,
            InfoDiscoveryEngineDataMartException {

        logger.debug("Enter method linkFactToDateDimension() with factId: {} ");

        Dimension day = new DimensionManager(ids).getDayDimension(prefix, dayDimension);

        if (!ids.hasRelationType(relationType)) {
            ids.addRelationType(relationType);
        }

        linkFactToDimension(fact, day, relationType);

        logger.debug("Exit method attachTimeToProgress()");
        return null;
    }



}
