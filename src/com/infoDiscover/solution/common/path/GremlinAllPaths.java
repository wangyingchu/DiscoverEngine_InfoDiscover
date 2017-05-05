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

    private List<Stack<String>> getVerticesOfAllPaths(String fromRid, String toRid, List<String>
            filterVertex) {

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

        result = filterResult(result, filterVertex);
        logger.debug("Result size: {}", result.size());

        return result;
    }

    private List<Stack<String>> filterResult(List<Stack<String>> allPaths, List<String> filter) {
        if (allPaths == null || allPaths.size() == 0 || filter == null || filter.size() == 0) {
            return allPaths;
        }

        List<Stack<String>> result = new ArrayList<>();

        for (Stack<String> stack : allPaths) {
            Stack<String> cloned = (Stack<String>) stack.clone();
            stack.retainAll(filter);

            if (stack.size() == filter.size()) {
                result.add(cloned);
            }
        }
        return result;
    }

    public List<Stack<Edge>> getEdgesOfAllPaths(String fromRid, String toRid) {
        List<Stack<String>> result = getVerticesOfAllPaths(fromRid, toRid, null);

        // get all paths from GenericGraph
//        List<Stack<String>> paths = getAllPaths(graph, result, fromRid, toRid);

        OrientDBAllPaths orientDBAllPaths = new OrientDBAllPaths(graph);
        return orientDBAllPaths.getEdgesFromAllPaths(result);
    }

    public List<Stack<Edge>> getEdgesOfAllPaths(String fromRid, String toRid, List<String>
            filterVertex) {
        List<Stack<String>> allPaths = getVerticesOfAllPaths(fromRid, toRid, filterVertex);

        return getEdgesOfAllPaths(allPaths);
    }

    public List<Stack<Edge>> getEdgesOfAllPaths(List<Stack<String>> verticesOfAllPaths) {
        OrientDBAllPaths orientDBAllPaths = new OrientDBAllPaths(graph);
        return orientDBAllPaths.getEdgesFromAllPaths(verticesOfAllPaths);
    }


    public List<Stack<Edge>> getFirstShortestPath(String fromRid, String toRid, int
            firstNumberOfShortestPath, List<String> filterVertex) {
        List<Stack<String>> allPaths = getVerticesOfFirstShortestPath(fromRid, toRid,
                firstNumberOfShortestPath,
                filterVertex);

        return getEdgesOfAllPaths(allPaths);
    }

    public List<Stack<Edge>> getFirstLongestPath(String fromRid, String toRid, int
            firstNumberOfLongestPath, List<String> filterVertex) {
        List<Stack<String>> allPaths = getVerticesOfFirstLongestPath(fromRid, toRid,
                firstNumberOfLongestPath, filterVertex);

        return getEdgesOfAllPaths(allPaths);
    }

    private List<Stack<String>> getVerticesOfPath(String fromRid, String toRid, int
            firstNumberOfPath, List<String> filterVertex, boolean findFirstShortestPaths) {
        List<Stack<String>> allPaths = getVerticesOfAllPaths(fromRid, toRid, filterVertex);

        if (allPaths == null || allPaths.size() == 0) {
            return null;
        }

        List<Stack<String>> result = new ArrayList<>();


        if (findFirstShortestPaths) {
            // first shortest paths
            int sizeOfPath = (firstNumberOfPath < allPaths.size()) ? firstNumberOfPath
                    : allPaths.size();
            for (int i = 0; i < sizeOfPath; i++) {
                result.add(allPaths.get(i));
            }
        } else {
            // first longest paths
            int sizeOfPath = (firstNumberOfPath > allPaths.size()) ? 0 : (allPaths.size() -
                    firstNumberOfPath);
            for (int i = allPaths.size() - 1; i >= sizeOfPath; i--) {
                result.add(allPaths.get(i));
            }
        }

        return result;
    }

    public List<Stack<String>> getVerticesOfFirstShortestPath(String fromRid, String toRid, int
            firstNumberOfShortestPath, List<String> filterVertex) {
        return getVerticesOfPath(fromRid, toRid, firstNumberOfShortestPath, filterVertex, true);
    }

    public List<Stack<String>> getVerticesOfFirstLongestPath(String fromRid, String toRid, int
            firstNumberOfLongestPath, List<String> filterVertex) {
        return getVerticesOfPath(fromRid, toRid, firstNumberOfLongestPath, filterVertex, false);
    }

    private static List<Stack<String>> getAllPaths(OrientGraph graph, List<Stack<String>> result,
                                                   String fromRid, String toRid) {
        logger.info("Start getAllPaths from Generic Graph");
        Set<String> set = removeDuplicateVertex(result);
        GenericGraph g = constructGraph(graph, set);

        List<Stack<String>> paths = new AllPaths(g, fromRid, toRid).getAllPaths();
        logger.info("Start getAllPaths from Generic Graph");
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

    private static GenericGraph constructGraph(OrientGraph graph, Set<String> ridSet) {
        GenericGraph G = new GenericGraph();

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

        }

        logger.debug("g: " + G);
        return G;
    }

    public static void main(String[] args) {

        OrientGraph graph = new OrientGraph("remote:localhost/DemoArch2", "root", "wyc");
        String fromRid = "#115:0";
        String toRid = "#116:0";

        List<String> filter = new ArrayList<>();
        filter.add("#89:4");
        filter.add("#98:0");
//        filter.add("#113:0");


        GremlinAllPaths allPaths = new GremlinAllPaths(graph);
        List<Stack<String>> result = allPaths.getVerticesOfAllPaths(fromRid, toRid, filter);


        for (Stack<String> r : result) {
            System.out.println("result: " + r);
        }

//        System.out.println("result: " + result.size());


//        List<Stack<Edge>> edges = allPaths.getEdgesOfAllPaths(fromRid, toRid);
//        for (Stack<Edge> stack : edges) {
//            logger.debug("edges: {}", stack);
//        }
//
//        System.out.println("edges: " + edges.size());


//        List<Stack<String>> paths = getAllPaths(graph, result, "#115:0", "#116:0");
//
//        for(Stack<String> stack : paths) {
//            System.out.println("path: " + stack);
//        }
//
//        System.out.println("paths: " + paths.size());

        List<Stack<String>> list = allPaths.getVerticesOfFirstShortestPath(fromRid, toRid, 5,
                filter);

        for (Stack<String> stack : list) {
            System.out.println("first shortest: " + stack);
        }

        list = allPaths.getVerticesOfFirstLongestPath(fromRid, toRid, 50,
                filter);

        for (Stack<String> stack : list) {
            System.out.println("longest shortest: " + stack);
        }
    }


}
