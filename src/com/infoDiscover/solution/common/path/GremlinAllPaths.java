package com.infoDiscover.solution.common.path;

import com.infoDiscover.common.util.DateUtil;
import com.infoDiscover.solution.common.path.helper.AllPaths;
import com.infoDiscover.solution.common.path.helper.GenericGraph;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by sun.
 */
public class GremlinAllPaths {
    private final static Logger logger = LoggerFactory.getLogger(GremlinAllPaths.class);

    private OrientGraph graph;

    private int maxLoops = 5;

    public GremlinAllPaths(OrientGraph graph) {
        this.graph = graph;
    }

    public void setMaxLoops(int maxLoops) {
        this.maxLoops = maxLoops;
    }

    private List<Stack<String>> getVerticesOfAllPaths(String fromRid, String toRid) {

        Vertex fromVertex = graph.getVertex(fromRid);
        Vertex toVertex = graph.getVertex(toRid);

        if (fromVertex == null || toVertex == null) {
            return null;
        }

        List<Stack<String>> result = new ArrayList<>();

        long start = System.currentTimeMillis();
        logger.debug("******Start to find allPaths at: {}", DateUtil.getDateTime(start));

        final Set<Vertex> x = new HashSet<>(Collections.singleton(fromVertex));
        final GremlinPipeline<Object, List> pipeline = new GremlinPipeline<>(fromVertex).as("x")
                .both()
                .loop("x", vertexLoopBundle -> vertexLoopBundle.getLoops() <= maxLoops,
                        vertexLoopBundle -> vertexLoopBundle.getObject() != fromVertex &&
                                vertexLoopBundle.getObject() != toVertex).filter(vertex -> vertex
                        .getId().toString()
                        .equals(toVertex.getId().toString())).path();

        for (final List path : pipeline) {
            Stack<String> stack = new Stack<>();
            for (Object v : path) {
                stack.add(((Vertex) v).getId().toString());
            }
            result.add(stack);
        }
        long end = System.currentTimeMillis();
        logger.debug("******End at: " + DateUtil.getDateTime(end));
        logger.debug("******Elapsed time: {}", (end - start));
        logger.debug("Result size: {}", result.size());

        return result;
    }

    public List<Stack<Edge>> getEdgesOfAllPaths(String fromRid, String toRid) {
        List<Stack<String>> result = getVerticesOfAllPaths(fromRid, toRid);

        List<Stack<String>> paths = getAllPaths(graph, result, fromRid, toRid);

        OrientDBAllPaths orientDBAllPaths = new OrientDBAllPaths(graph);
        return orientDBAllPaths.getEdgesFromAllPaths(paths);
    }




    public static void main(String[] args) {

        OrientGraph graph = new OrientGraph("remote:localhost/DemoArch2", "root", "wyc");
        String fromRid = "#115:0";
        String toRid = "#116:0";

        GremlinAllPaths allPaths = new GremlinAllPaths(graph);
//        List<Stack<String>> result = allPaths.getVerticesOfAllPaths(fromRid, toRid);
//        for (Stack<String> stack : result) {
//            logger.debug("path: {}", stack);
//        }
//
//        System.out.println("result: " + result.size());

        List<Stack<Edge>> edges = allPaths.getEdgesOfAllPaths(fromRid, toRid);
        for(Stack<Edge> stack: edges) {
            logger.debug("edges: {}", stack);
        }

        System.out.println("edges: " + edges.size());


//        List<Stack<String>> paths = getAllPaths(graph, result, "#115:0", "#116:0");
//
//        for(Stack<String> stack : paths) {
//            System.out.println("path: " + stack);
//        }
//
//        System.out.println("paths: " + paths.size());
    }

    private static List<Stack<String>> getAllPaths(OrientGraph graph, List<Stack<String>> result,
                                                   String fromRid, String toRid) {

        Set<String> set = removeDuplicateVertex(result);
        GenericGraph g = constructGraph(graph, set);

        List<Stack<String>> paths = new AllPaths(g, "#115:0", "#116:0").getAllPaths();
        return paths;
    }

    private static Set<String> removeDuplicateVertex(List<Stack<String>> results) {

        Set<String> set = new LinkedHashSet<>();

        Stack<String> all = new Stack<>();
        for (Stack<String> s : results) {
            all.addAll(s);
        }

        for (String v : all) {
            set.add(v);
        }

        return set;
    }

    private static GenericGraph G = new GenericGraph();

    private static GenericGraph constructGraph(OrientGraph graph, Set<String> ridSet) {

        List<String> list = new ArrayList<>();
        list.addAll(ridSet);

        for (int i = 0; i < list.size(); i++) {
            String startRid = list.get(i);

            for (int j = i + 1; j < list.size(); j++) {
                String endRid = list.get(j);

                OrientVertex startV = graph.getVertex(startRid);
                OrientVertex endV = graph.getVertex(endRid);

                Iterable<Edge> e = startV.getEdges(endV, Direction.BOTH);
                if (e.iterator().hasNext()) {
                    G.addEdge(startRid, endRid);
                }
            }


//            }

        }

        System.out.println("g: " + G);

        return G;
    }
}
