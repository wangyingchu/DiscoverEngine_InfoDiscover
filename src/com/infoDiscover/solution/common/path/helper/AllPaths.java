package com.infoDiscover.solution.common.path.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AllPaths<Vertex> {

    private Stack<String> path = new Stack<String>();   // the current path
    private SET<String> onPath = new SET<String>();     // the set of vertices on the path

    private List<Stack<String>> allPaths = new ArrayList<>();

    public AllPaths(GenericGraph G, String s, String t) {
        enumerate(G, s, t);
    }

    // use DFS
    private void enumerate(GenericGraph G, String v, String t) {

        // add node v to current path from s
        path.push(v);
        onPath.add(v);

        // found path from s to t - currently prints in reverse order because of stack
        if (v.equals(t)) {
//            StdOut.println(path);
            Stack<String> clonedStack = (Stack<String>) path.clone();
            allPaths.add(clonedStack);
        } else {
            // consider all neighbors that would continue path with repeating a node
            for (String w : G.adjacentTo(v)) {
                if (!onPath.contains(w)) enumerate(G, w, t);
            }
        }

        // done exploring from v, so remove from path
        path.pop();
        onPath.delete(v);
    }

    public List<Stack<String>> getAllPaths() {
        return allPaths;
    }

    public static void main(String[] args) {
        GenericGraph G = new GenericGraph();
        G.addEdge("A", "B");
        G.addEdge("A", "C");
        G.addEdge("C", "D");
        G.addEdge("D", "E");
        G.addEdge("C", "F");
        G.addEdge("B", "F");
        G.addEdge("F", "D");
        G.addEdge("D", "G");
        G.addEdge("E", "G");
        System.out.println(G);
        new AllPaths(G, "A", "G");
        System.out.println();
        new AllPaths(G, "B", "F");

    }

}
