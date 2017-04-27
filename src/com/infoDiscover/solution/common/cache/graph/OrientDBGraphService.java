package com.infoDiscover.solution.common.cache.graph;

import com.infoDiscover.solution.common.cache.ILocalCache;
import com.infoDiscover.solution.common.path.helper.GenericGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

/**
 * Created by sun.
 */
public class OrientDBGraphService implements IOrientDBGraphService {
    private static ILocalCache<OrientGraph, GenericGraph> localCache = new OrientDBGraphCache();

    private static OrientDBGraphService newInstance;

    public static synchronized OrientDBGraphService getInstance() {
        if (newInstance == null) {
            newInstance = new OrientDBGraphService();
        }
        return newInstance;
    }

    @Override
    public GenericGraph getGraphFromOrientGraph(OrientGraph graph) {
        return localCache.get(graph);
    }

    public static void main(String[] args) {
        OrientGraph graph = new OrientGraph("remote:localhost/InfoDiscover", "root", "wyc");

        long start = System.currentTimeMillis();
        OrientDBGraphService service = OrientDBGraphService.getInstance();
        GenericGraph g = service.getGraphFromOrientGraph(graph);
        System.out.println("graph: " + g);
        long end = System.currentTimeMillis();
        System.out.println("time1: " + (end - start));
//
//        OrientGraph og2 = new OrientGraph("remote:localhost/DemoArch", "root", "wyc");
//        Graph g2 = service.getGraphFromOrientGraph(og2);
//        System.out.println("g2: " + g2);

        long start1 = System.currentTimeMillis();
        GenericGraph g3 = service.getGraphFromOrientGraph(graph);
        System.out.println("g3: " + g3);
        long end1 = System.currentTimeMillis();
        System.out.println("time2: " + (end1 - start1));

        System.out.println("cache hit: {}");
    }
}
