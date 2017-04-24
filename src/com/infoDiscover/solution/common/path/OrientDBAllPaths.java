package com.infoDiscover.solution.common.path;

import com.infoDiscover.solution.common.path.helper.AllPaths;
import com.infoDiscover.solution.common.path.helper.Graph;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Created by sun.
 */
public class OrientDBAllPaths {

    private final static Logger logger = LoggerFactory.getLogger(OrientDBAllPaths.class);

    private OrientGraph graph;

    public OrientDBAllPaths(OrientGraph graph) {
        this.graph = graph;
    }

    public List<Stack<String>> getVerticesOfAllPaths(String fromRid, String toRid) {
        logger.info("Start to getAllPaths fromVertex: {} to toVertex: {}",
                fromRid, toRid);
        Graph g = OrientDBGraphHelper.generateGraphFromOrientDB(graph);

        List<Stack<String>> paths = new AllPaths(g,
                fromRid, toRid).getAllPaths();

        logger.info("End to getAllPaths()...");
        return paths;
    }

    public List<Stack<Edge>> getEdgesOfAllPaths(String fromRid, String toRid) {
        logger.info("Start to getEdgesOfAllPaths fromVertex: {} to toVertex: {}", fromRid, toRid);
        List<Stack<String>> paths = getVerticesOfAllPaths(fromRid, toRid);


        logger.info("End to getEdgesOfAllPaths()...");
        return getEdgesFromAllPaths(paths);
    }


    private List<Stack<Edge>> getEdgesFromAllPaths(List<Stack<String>> allPaths) {

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

    public static void main(String[] args) {

//        getAllPathsFromInfoDiscover(ResultType.V);
//        getAllPathsFromDemoArch(ResultType.V);
        getAllPathsFromDemoArch(ResultType.E);
    }

    private static void getAllPathsFromPathDB() {
        OrientGraph graph = new OrientGraph("remote:localhost/pathDB", "root", "wyc");

        OrientDBAllPaths allPaths = new OrientDBAllPaths(graph);

        List<Stack<String>> paths = allPaths.getVerticesOfAllPaths("#33:0",
                "#37:0");

        for (Stack<String> s : paths) {
            logger.info("path: ", s);
        }
    }

    private static void getAllPathsFromDemoArch(ResultType type) {
        OrientGraph graph = new OrientGraph("remote:localhost/DemoArch", "root", "wyc");
        OrientDBAllPaths allPaths = new OrientDBAllPaths(graph);
        if (type == ResultType.V) {
            List<Stack<String>> paths = allPaths.getVerticesOfAllPaths("#105:3", "#107:2");
            for (Stack<String> path : paths) {
                logger.info("path: {}", path);
            }
        } else if (type == ResultType.E) {
            List<Stack<Edge>> paths = allPaths.getEdgesOfAllPaths("#105:3", "#107:2");
            for (Stack<Edge> edge : paths) {
                logger.info("edge: {}", edge);
            }
        }
    }

    private static void getAllPathsFromInfoDiscover(ResultType type) {
        OrientGraph graph = new OrientGraph("remote:localhost/InfoDiscover", "root", "wyc");
        OrientDBAllPaths allPaths = new OrientDBAllPaths(graph);
        if (type == ResultType.V) {
            List<Stack<String>> paths = allPaths.getVerticesOfAllPaths("#549:0", "#545:2");
            for (Stack<String> path : paths) {
                logger.info("path: {}", path);
            }
        } else if (type == ResultType.E) {
            List<Stack<Edge>> paths = allPaths.getEdgesOfAllPaths("#549:0", "#545:2");
            for (Stack<Edge> edge : paths) {
                logger.info("edge: {}", edge);
            }
        }
    }
}
