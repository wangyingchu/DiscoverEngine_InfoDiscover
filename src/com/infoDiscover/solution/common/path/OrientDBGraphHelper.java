package com.infoDiscover.solution.common.path;

import com.infoDiscover.solution.common.path.helper.Graph;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sun.
 */
public class OrientDBGraphHelper {

    private final static Logger logger = LoggerFactory.getLogger(OrientDBGraphHelper.class);

    public static Graph generateGraphFromOrientDB(OrientGraph graph) {

        long start = System.currentTimeMillis();
        logger.info("Start to generateGraphFromOrientDB at time: {}", new Date(start));
        Graph g = new Graph();
        List<String> allVertex = getAllVertices(graph);
        if (!isEmptyList(allVertex)) {
            for (String rid : allVertex) {
                List<String> adjacentVertexList = getAdjacentVertices(graph, rid, Direction.BOTH);
                if (!isEmptyList(adjacentVertexList)) {
                    for (String v : adjacentVertexList) {
                        g.addEdge(rid, v);
                    }
                }
            }
        }
        long end = System.currentTimeMillis();
        logger.info("End to generateGraphFromOrientDB() at time: {}", new Date(end));
        logger.info("Elapsed time to generateGraphFromOrientDB: {}", (end - start));
        logger.debug("Graph: {}", g);
        return g;
    }

    public static List<String> getAllVertices(OrientGraph graph) {
        logger.info("Start to getAllVertexList()");
        Iterable<Vertex> vertices = graph.getVertices();
        List<String> list = getRidListFromVertexList(vertices);
        logger.info("There are {} vertices in graph", list.size());
        return list;
    }

    public static List<String> getAdjacentVertices(OrientGraph graph,
                                                   String fromVertexId,
                                                   Direction direction) {
        logger.debug("Start to getAdjacentVertices() of vertex: {}", fromVertexId);
        Vertex v = graph.getVertex(fromVertexId);
        Iterable<Vertex> vertices = v.getVertices(direction);
        List<String> list = getRidListFromVertexList(vertices);
        logger.debug("There are {} adjacent vertices of vertex: {}", list.size(), fromVertexId);
        return list;
    }

    private static List<String> getRidListFromVertexList(Iterable<Vertex> vertices) {
        List<String> list = new ArrayList<>();
        for (Vertex v : vertices) {
            list.add(v.getId().toString());
        }
        return list;
    }

    private static boolean isEmptyList(List<String> list) {
        return list == null || list.size() == 0;
    }
}
