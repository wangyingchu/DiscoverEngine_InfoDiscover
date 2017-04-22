package com.infoDiscover.solution.common.path;

import com.infoDiscover.solution.common.path.helper.AllPaths;
import com.infoDiscover.solution.common.path.helper.Graph;
import com.infoDiscover.solution.common.path.helper.StdOut;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by sun.
 */
public class OrientDBAllPaths {

    private final static Logger logger = LoggerFactory.getLogger(OrientDBAllPaths.class);

    public enum ResultType {
        V, E, BOTH;
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

    public static List<Stack<String>> getAllPaths(OrientGraph graph, String fromRid, String
            toRid, ResultType type) {
        logger.info("Start to getAllPaths fromVertex: {} to toVertex: {} with returnType: {}",
                fromRid, toRid, type);
        Graph g = generateGraphFromOrientDB(graph);

        List<Stack<String>> paths = new AllPaths(g,
                fromRid, toRid).getAllPaths();

        if (paths == null || paths.size() == 0) {
            return null;
        }

        logger.info("End to getAllPaths()...");
        switch (type) {
            case E:
                return getEdgesFromAllPaths(graph, paths);
            default:
                return paths;
        }

    }

    private static List<Stack<String>> getEdgesFromAllPaths(OrientGraph graph, List<Stack<String>>
            allPaths) {
        logger.info("Start to getEdgesFromAllPaths");
        List<Stack<String>> edgesOfAllPath = new ArrayList<>();

        for (Stack<String> path : allPaths) {
            int i = 0;
            Stack<String> edge = new Stack<>();
            for (String vertexId : path) {
                if (i < path.size() - 1) {
                    ++i;
                }

                OrientVertex fromVertex = graph.getVertex(vertexId);
                OrientVertex toVertex = graph.getVertex(path.get(i));
                Iterator<Edge> iterator = (Iterator<Edge>) fromVertex.getEdges(toVertex,
                        Direction.BOTH);
                if (iterator.hasNext()) {
                    String edgeId = iterator.next().getId().toString();
                    edge.add(edgeId);
                }
            }
            edgesOfAllPath.add(edge);
        }

        logger.info("End to getEdgesFromAllPaths()...");
        return edgesOfAllPath;
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

    public static void main(String[] args) {

        getAllPathsFromInfoDiscover(ResultType.V);
//        getAllPathsFromDemoArch(ResultType.V);
//        getAllPathsFromDemoArch(ResultType.E);
    }

    private static void getAllPathsFromPathDB() {
        OrientGraph graph = new OrientGraph("remote:localhost/pathDB", "root", "wyc");

        List<Stack<String>> paths = getAllPaths(graph, "#33:0",
                "#37:0", ResultType.V);

        for (Stack<String> s : paths) {
            logger.info("path: ", s);
        }
    }

    private static void getAllPathsFromDemoArch(ResultType type) {
        OrientGraph graph = new OrientGraph("remote:localhost/DemoArch", "root", "wyc");

        List<Stack<String>> paths = getAllPaths(graph, "#105:3", "#107:2", type);
        for (Stack<String> path : paths) {
            logger.info("path: {}", path);
        }
    }

    private static void getAllPathsFromInfoDiscover(ResultType type) {
        OrientGraph graph = new OrientGraph("remote:localhost/InfoDiscover", "root", "wyc");

        List<Stack<String>> paths = getAllPaths(graph,"#549:0", "#545:2", type);
        for (Stack<String> path : paths) {
            logger.info("path: {}", path);
        }
    }
}
