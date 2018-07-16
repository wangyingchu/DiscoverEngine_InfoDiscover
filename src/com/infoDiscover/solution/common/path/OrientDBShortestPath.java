package com.infoDiscover.solution.common.path;

import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sun.
 */
public class OrientDBShortestPath {
    private final static Logger logger = LoggerFactory.getLogger(OrientDBShortestPath.class);

    public static List<OrientVertex> getVerticesFromShortestPath(OrientGraph graph, String
            fromVertexRID, String toVertexRID) {
        logger.info("Enter method getVerticesFromShortestPath() from rid: {} to rid: {}",
                fromVertexRID, toVertexRID);

        String sql = "select expand(shortestpath) from (select shortestpath(" + fromVertexRID + ","
                + toVertexRID + "))";

        List<OrientVertex> list = new ArrayList<>();

        for (OrientVertex v : (Iterable<OrientVertex>) graph.command(
                new OCommandSQL(sql)).execute()) {
            list.add(v);
        }

        logger.info("Exit method getVerticesFromShortestPath()...");

        return list;
    }

    public static List<Edge> getEdgesFromShortestPath(OrientGraph graph, String fromVertexRID, String
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

    public static void main(String[] args) throws InfoDiscoveryEngineRuntimeException,
            InfoDiscoveryEngineInfoExploreException {

        OrientGraph graph = new OrientGraph("remote:localhost/InfoDiscover", "root", "wyc");
        List<OrientVertex> list = getVerticesFromShortestPath(graph, "#549:0", "#545:2");
        for (OrientVertex v : list) {
            System.out.println("V: " + v.getId());
        }

        List<Edge> edgeList = getEdgesFromShortestPath(graph, "#531:0", "#504:161");
        for (Edge e : edgeList) {
            logger.debug(" e: {}", e.getId());
        }
    }
}
