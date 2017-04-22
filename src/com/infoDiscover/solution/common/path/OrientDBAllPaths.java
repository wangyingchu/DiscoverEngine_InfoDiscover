package com.infoDiscover.solution.common.path;

import com.infoDiscover.solution.common.path.helper.AllPaths;
import com.infoDiscover.solution.common.path.helper.Graph;
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

    public static List<Stack<String>> getVerticesOfAllPaths(OrientGraph graph, String fromRid,
                                                            String
                                                                    toRid) {
        logger.info("Start to getAllPaths fromVertex: {} to toVertex: {}",
                fromRid, toRid);
        Graph g = generateGraphFromOrientDB(graph);

        List<Stack<String>> paths = new AllPaths(g,
                fromRid, toRid).getAllPaths();

        logger.info("End to getAllPaths()...");
        return paths;
    }

    public static List<Stack<Edge>> getEdgesOfAllPaths(OrientGraph graph, String fromRid, String
            toRid) {
        logger.info("Start to getEdgesOfAllPaths fromVertex: {} to toVertex: {}", fromRid, toRid);
        List<Stack<String>> paths = getVerticesOfAllPaths(graph, fromRid, toRid);


        logger.info("End to getEdgesOfAllPaths()...");
        return getEdgesFromAllPaths(graph, paths);
    }


    private static List<Stack<Edge>> getEdgesFromAllPaths(OrientGraph graph, List<Stack<String>>
            allPaths) {

        if (allPaths == null || allPaths.size() == 0) {
            return null;
        }

        logger.info("Start to getEdgesFromAllPaths");
        List<Stack<Edge>> edgesOfAllPath = new ArrayList<>();

        for (Stack<String> path : allPaths) {
            int i = 0;
            Stack<Edge> edge = new Stack<>();
            for (String vertexId : path) {
                if (i < path.size() - 1) {
                    ++i;
                }

                OrientVertex fromVertex = graph.getVertex(vertexId);
                OrientVertex toVertex = graph.getVertex(path.get(i));
                Iterator<Edge> iterator = (Iterator<Edge>) fromVertex.getEdges(toVertex,
                        Direction.BOTH);
                if (iterator.hasNext()) {
                    edge.add(iterator.next());
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

//        getAllPathsFromInfoDiscover(ResultType.V);
//        getAllPathsFromDemoArch(ResultType.V);
        getAllPathsFromDemoArch(ResultType.E);
    }

    private static void getAllPathsFromPathDB() {
        OrientGraph graph = new OrientGraph("remote:localhost/pathDB", "root", "wyc");

        List<Stack<String>> paths = getVerticesOfAllPaths(graph, "#33:0",
                "#37:0");

        for (Stack<String> s : paths) {
            logger.info("path: ", s);
        }
    }

    private static void getAllPathsFromDemoArch(ResultType type) {
        OrientGraph graph = new OrientGraph("remote:localhost/DemoArch", "root", "wyc");

        if (type == ResultType.V) {
            List<Stack<String>> paths = getVerticesOfAllPaths(graph, "#105:3", "#107:2");
            for (Stack<String> path : paths) {
                logger.info("path: {}", path);
            }
        } else if (type == ResultType.E) {
            List<Stack<Edge>> paths = getEdgesOfAllPaths(graph,"#105:3", "#107:2");
            for(Stack<Edge> edge: paths) {
                logger.info("edge: {}", edge);
            }
        }
    }

    private static void getAllPathsFromInfoDiscover(ResultType type) {
        OrientGraph graph = new OrientGraph("remote:localhost/InfoDiscover", "root", "wyc");

        if (type == ResultType.V) {
            List<Stack<String>> paths = getVerticesOfAllPaths(graph, "#549:0", "#545:2");
            for (Stack<String> path : paths) {
                logger.info("path: {}", path);
            }
        } else if (type == ResultType.E) {
            List<Stack<Edge>> paths = getEdgesOfAllPaths(graph,"#549:0", "#545:2");
            for(Stack<Edge> edge: paths) {
                logger.info("edge: {}", edge);
            }
        }
    }
}
