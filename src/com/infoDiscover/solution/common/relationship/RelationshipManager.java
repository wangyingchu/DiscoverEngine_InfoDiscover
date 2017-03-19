package com.infoDiscover.solution.common.relationship;

import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sun.
 */
public class RelationshipManager {
    private final static Logger logger = LogManager.getLogger(RelationshipManager.class);

    public List<OrientVertex> getVerticesFromShortestPath(OrientGraph graph, String
            fromVertexRID, String toVertexRID) {
        logger.info("Enter method getVerticesFromShortestPath() from rid: " + fromVertexRID + " " +
                "to rid: " +
                toVertexRID);

        String sql = "select expand(shortestpath) from (select shortestpath(" + fromVertexRID + ","
                + toVertexRID + ", 'BOTH'))";

        List<OrientVertex> list = new ArrayList<>();

        for (OrientVertex v : (Iterable<OrientVertex>) graph.command(
                new OCommandSQL(sql)).execute()) {
            list.add(v);
        }

        logger.info("Exit method getVerticesFromShortestPath()...");

        return list;
    }

    public List<Edge> getEdgesFromShortestPath(OrientGraph graph, String fromVertexRID, String
            toVertexRID) {
        logger.info("Enter method getEdgesFromShortestPath() from rid: " + fromVertexRID + " to " +
                "rid: " +
                toVertexRID);

        List<OrientVertex> orientVertexList = getVerticesFromShortestPath(graph, fromVertexRID,
                toVertexRID);

        if (orientVertexList == null || orientVertexList.size() == 0) {
            return null;
        }

        int i = 0;
        List<Edge> edgeList = new ArrayList<>();
        for (OrientVertex v : orientVertexList) {
            if (i < orientVertexList.size() - 1) {
                ++i;
            }

            Iterator<Edge> iterator = (Iterator<Edge>) v.getEdges(orientVertexList.get(i),
                    Direction.BOTH);

            if (iterator.hasNext()) {
                Edge e = iterator.next();
                edgeList.add(e);
            }
        }

        logger.info("Exit method getEdgesFromShortestPath()...");
        return edgeList;
    }

    public boolean checkRelationship(OrientGraph graph, String fromVertexRID, String toVertexRID) {
        List<OrientVertex> list = getVerticesFromShortestPath(graph, fromVertexRID, toVertexRID);
        return list.size() > 0;
    }


    public static void main(String[] args) throws InfoDiscoveryEngineRuntimeException,
            InfoDiscoveryEngineInfoExploreException {

        RelationshipManager manager = new RelationshipManager();

        OrientGraph graph = new OrientGraph("remote:localhost/InfoDiscover", "root", "wyc");
        List<OrientVertex> list = manager.getVerticesFromShortestPath(graph, "#549:0", "#545:2");
        for (OrientVertex v : list) {
            System.out.println("V: " + v.getId());
        }

        List<Edge> edgeList = manager.getEdgesFromShortestPath(graph, "#531:0", "#504:161");
        for (Edge e : edgeList) {
            logger.debug(" e: " + e.getId());
        }
    }
}
