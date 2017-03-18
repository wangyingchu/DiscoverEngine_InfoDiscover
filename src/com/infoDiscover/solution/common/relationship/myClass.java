package com.infoDiscover.solution.common.relationship;

/**
 * Created by sun.
 */

import java.util.ArrayList;
import java.util.List;


import com.orientechnologies.orient.client.remote.OServerAdmin;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

public class myClass {

    private boolean stop = false;
    private List<Vertex> visitedNodesPreviousStep = new ArrayList<Vertex>();
    private List<Vertex> visitedNodeCurrently = new ArrayList<Vertex>();
    private List<List<Vertex>> path_vertex = new ArrayList<List<Vertex>>();
    private List<List<Edge>> path_edges = new ArrayList<List<Edge>>();
    private OrientGraph g;
    int step = 0;

    public myClass(OrientGraph g) {
        this.g = g;
    }

    protected List<Object> getDistance(String starting_rid, String ending_rid) {

        Vertex starting_node = g.getVertex(starting_rid);
        Vertex ending_node = g.getVertex(ending_rid);

        visitedNodesPreviousStep.add(starting_node);

        List<Vertex> p1 = new ArrayList<Vertex>();
        p1.add(starting_node);
        path_vertex.add(p1);

        step = 1;
        boolean found_node_to_be_added = false;
        do {
            stop = false;
            found_node_to_be_added = false;
            for (Vertex v : visitedNodesPreviousStep) {
                List<Edge> edges_to_be_added = new ArrayList<Edge>();
                List<Vertex> nodes_to_be_added = new ArrayList<Vertex>();
                Iterable<Edge> it_edge = (Iterable<Edge>) v.getEdges(Direction.OUT);
                for (Edge e1 : it_edge) {
                    Vertex v1 = e1.getVertex(Direction.IN);
                    edges_to_be_added.add(e1);
                    nodes_to_be_added.add(v1);
                    String rid = v1.getId().toString();
                    if (!rid.equals(ending_rid)) {  // checking the current @rid isn't the ending
                        visitedNodeCurrently.add(v1);
                    } else {   // ending node found
                        setPathFoundList(v, ending_node, step, e1);
                        stop = true;
                    }
                }
                if (nodes_to_be_added.size() != 0 && stop == false) {
                    found_node_to_be_added = true;
                    setpath_vertex(v, nodes_to_be_added, edges_to_be_added);
                }
            }
            if (found_node_to_be_added == false) {
                stop = true;
            }
            System.out.println("step =  " + step + "  " + path_vertex);
            change();

            step++;
        } while (stop == false);
        clean_vertex_path(ending_node);
        return getShortestPathList();
    }

    public void change() {
        visitedNodesPreviousStep.clear();
        for (Vertex v : visitedNodeCurrently)
            visitedNodesPreviousStep.add(v);
        visitedNodeCurrently.clear();
    }

    private void setPathFoundList(Vertex node, Vertex ending_node, int step, Edge edge) {
        for (int i = 0; i < path_vertex.size(); i++) {

            List<Vertex> path = path_vertex.get(i);
            Vertex last = path.get(path.size() - 1);
            if (last.getId().equals(node.getId()) && path.size() == step) {
                path.add(ending_node);
                List<Edge> edgesPath = path_edges.get(i);
                edgesPath.add(edge);
            }
        }
    }

    private void setpath_vertex(Vertex node, List<Vertex> nodes_to_be_added, List<Edge>
            edges_to_be_added) {
        for (int i = 0; i < path_vertex.size(); i++) {
            List<Vertex> path = path_vertex.get(i);
            Vertex last = path.get(path.size() - 1);
            if (last.getId().equals(node.getId())) {
                int j = 0;
                for (int h = 0; h < nodes_to_be_added.size(); h++) {
                    boolean name_present = false;
                    for (Vertex p : path) {
                        if (p.getId().equals(nodes_to_be_added.get(h).getId()))
                            name_present = true;
                    }
                    if (name_present == false) {
                        List<Vertex> p2 = new ArrayList<Vertex>();
                        for (Vertex p : path)
                            p2.add(p);
                        p2.add(nodes_to_be_added.get(h));
                        List<Edge> e2 = new ArrayList<Edge>();
                        if (step == 1) {
                            e2.add(edges_to_be_added.get(h));
                        } else {
                            List<Edge> edgesPath = path_edges.get(i);
                            for (Edge p1 : edgesPath)
                                e2.add(p1);
                            e2.add(edges_to_be_added.get(h));
                        }
                        if (j == 0) {
                            path_vertex.set(i, p2);
                            if (step == 1) {
                                path_edges.add(i, e2);
                            } else {
                                path_edges.set(i, e2);
                            }
                            j++;
                        } else {
                            path_vertex.add(p2);
                            path_edges.add(e2);
                        }
                    }
                }
            }
        }
    }

    public void clean_vertex_path(Vertex ending_node_name) {
        for (int i = 0; i < path_vertex.size(); i++) {
            List<Vertex> path = path_vertex.get(i);
            if (!path.get(path.size() - 1).getId().equals(ending_node_name.getId())) {
                path_vertex.remove(i);
                path_edges.remove(i);
                i--;
            }
        }
    }

    public List<Object> getShortestPathList() {
        List<Object> result = new ArrayList<Object>();
        if (path_vertex.size() == 0)
            return new ArrayList<Object>();
        else {
            List<Vertex> path = path_vertex.get(0);
            int min_size = path.size();
            for (int i = 0; i < path_vertex.size(); i++) {
                if (path_vertex.get(i).size() <= min_size) {
                    List<Vertex> path2 = path_vertex.get(i);
                    List<Edge> edges2 = path_edges.get(i);
                    for (int k = 0; k < path2.size(); k++) {
                        result.add(path2.get(k));
                        if (k != path2.size() - 1)
                            result.add(edges2.get(k));
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {

        String remote = "remote:localhost/";
        String DBname = "DemoArch";
        String currentPath = remote + DBname;

        OServerAdmin serverAdmin;
        try {
//            serverAdmin = new OServerAdmin(currentPath).connect("root", "root");
//            if(serverAdmin.existsDatabase()){

            OrientGraph graph = new OrientGraph("remote:localhost/DemoArch", "root", "wyc");
            myClass shortest2 = new myClass(graph);
            System.out.println("SHORTEST PATH " + shortest2.getDistance("#105:3", "#107:2"));

//            }
        } catch (Exception e) {
        }
    }
}
