package com.infoDiscover.solution.common.relationship.bb;

/**
 * Created by sun.
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AllPaths {

    private final static Logger logger = LoggerFactory.getLogger(AllPaths.class);
    
    private boolean stop = false;
    private Vertex vertexFrom = null;
    private List<Vertex> vertexPreviousStep = new ArrayList<Vertex>();
    private List<Vertex> vertexCurrently = new ArrayList<Vertex>();
    private List<List<Vertex>> paths = new ArrayList<List<Vertex>>();
    private OrientGraph g;

    public AllPaths(OrientGraph g) {
        this.g = g;
    }

    protected List<List<Vertex>> getPaths(String ridFrom, String ridTo) {
        logger.debug("start to get paths: " + ridFrom  + " and " + ridTo);
        // check if these two rid are existed
        if (!check(ridFrom, ridTo))
            return paths;

        vertexPreviousStep.add(vertexFrom);
        List<Vertex> p = new ArrayList<>();
        p.add(vertexFrom);
        paths.add(p);
        int step = 1;
        do {
            stop = false;
            for (Vertex v : vertexPreviousStep) {
                logger.debug("Vertex at previousStep: " + v.getId());
                List<Vertex> toAdd = new ArrayList<>();
                Iterable<Vertex> nodes = v.getVertices(Direction.BOTH);
                logger.debug("Vertex connected to previousStep vertex: " + nodes);
                for (Vertex v1 : nodes) {

                    if(!v1.getId().toString().equals(v.getId().toString())) {
                        logger.debug("Vertex connected to previousStep vertex: " + v1.getId());
                        toAdd.add(v1);
                    }
                    if (!v1.getId().toString().equals(ridTo) || !v1.getId().toString().equals
                            (v.getId().toString())) {
                        vertexCurrently.add(v1);
                        logger.debug("v1: " + v1.getId() + " is added to currentlyVertex");
                    }
                }
                if (toAdd.size() != 0)
                    setPaths(v, toAdd, step);
            }
            change();
            step++;
        } while (stop == true);

        logger.debug("end to get paths");
        return cleanPaths(ridTo);
    }

    private boolean check(String ridFrom, String ridTo) {
        boolean findFrom = false, findTo = false;
        for (Vertex v : g.getVertices()) {
            if (v.getId().toString().equals(ridFrom)) {
                findFrom = true;
                vertexFrom = v;
            } else if (v.getId().toString().equals(ridTo))
                findTo = true;
        }
        if (findFrom == false || findTo == false)
            return false;
//        logger.debug("start to check if the vertex is found");
//        String sql = "select from ID_DIMENSION_DEMO_USER where @rid = '" +ridFrom + "'";
//        List<Vertex> list = new ArrayList<>();
//
//        OrientGraph graph = new OrientGraph("remote:localhost/DemoArch", "root", "wyc");
//        for (Vertex v : (Iterable<Vertex>) graph.command(
//                new OCommandSQL(sql)).execute()) {
//
//            list.add(v);
//
//        }
//        vertexFrom = list.get(0);
//        logger.debug("Vertex from's rid: " + vertexFrom.getId());
//        logger.debug("end to check if the vertex is found");
        return true;
    }

    public void change() {
        vertexPreviousStep.clear();
        for (Vertex v : vertexCurrently)
            vertexPreviousStep.add(v);
        vertexCurrently.clear();
    }

    private void setPaths(Vertex previousVertex, List<Vertex> toAdd, int step) {
        for (int i = 0; i < paths.size(); i++) {
            List<Vertex> list = paths.get(i);
            Vertex last = list.get(list.size() - 1);
            if (last.getId().toString().equals(previousVertex.getId().toString()) && list.size()
                    == step) {
                int j = 0;
                for (Vertex v1 : toAdd) {
                    boolean vertexFound = false;
                    for (Vertex v2 : list) {
                        if (v2.getId().toString().equals(v1.getId().toString()))
                            vertexFound = true;
                    }
                    if (vertexFound == false) {
                        List<Vertex> listVertex = new ArrayList<Vertex>();
                        for (Vertex p : list)
                            listVertex.add(p);
                        listVertex.add(v1);
                        if (j == 0) {
                            stop = true;
                            paths.set(i, listVertex);
                            j++;
                        } else
                            paths.add(listVertex);
                    }
                }
            }
        }
    }

    public List<List<Vertex>> cleanPaths(String ridTo) {
        logger.debug("start to clean paths");
        for (int i = 0; i < paths.size(); i++) {
            List<Vertex> list = paths.get(i);
            if (!list.get(list.size() - 1).getId().toString().equals(ridTo)) {
                paths.remove(i);
                i--;
            }
        }
        logger.debug("end to clean paths");
        return paths;
    }

    public static void main(String[] args) {
//        OrientGraph graph = new OrientGraph("remote:localhost/DemoArch", "root", "wyc");
        OrientGraph graph = new OrientGraph("remote:localhost/pathDB", "root", "wyc");
        AllPaths allPath = new AllPaths(graph);
        long start = System.currentTimeMillis();
        logger.debug("start at time: " + new Date(start));
        List<List<Vertex>> paths = allPath.getPaths("#33:0", "#37:0");
//        List<List<Vertex>> paths = allPath.getPaths("#105:3", "#107:2");
        long end = System.currentTimeMillis();
        logger.debug("end at time: " + new Date(end));
        logger.debug("time: " + (end - start)/1000/60);

//        logger.debug("paths: " + paths.size());
        for(List<Vertex> list: paths) {
            for(Vertex vertex : list) {
                logger.debug("path: " + vertex.getId().toString());
            }
//            logger.debug(list.size());
        }

//        Graph g = new("remote:localhost/InfoDiscover", "root", "wyc");
//        GremlinPipeline pipeline = new GremlinPipeline<>();
//        pipeline.start(g.getVertices());
//        logger.debug(pipeline);
//        pipeline.start(g.getVertex(1)).out("knows").property("name");
    }
}
