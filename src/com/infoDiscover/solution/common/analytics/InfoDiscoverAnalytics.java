package com.infoDiscover.solution.common.analytics;

import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relationable;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun.
 */
public class InfoDiscoverAnalytics {

    private final static Logger logger = LoggerFactory.getLogger(InfoDiscoverAnalytics.class);

    public List<Relationable> getAllLinkedFacts(InfoDiscoverSpace ids, Fact fromFact, int depth) {
        return getAllLinkedFacts(ids, fromFact.getId(), depth);
    }

    public List<Relationable> getAllLinkedFacts(InfoDiscoverSpace ids, String fromFactRid, int
            depth) {
        logger.info("Enter getAllLinkedFacts() with fromFactRid: {} and depth: ", fromFactRid,
                depth);

        String sql = "select from (TRAVERSE both() FROM " + fromFactRid + " while $depth < "
                + (depth + 1) + ")";
        System.out.println("getRelatedFacts() sql: " + sql);

        List<Relationable> results = QueryExecutor.executeFactQuery(ids, sql);

        logger.info("Exit getAllLinkedFacts()...");
        return results;
    }

    public List<Vertex> getSimilarFactList(OrientGraph graph, List<String>
            dimensionRids, String targetFactType) {
        logger.info("Enter getSimilarFactList with graph: {} and dimensionIds: {} and " +
                "targetFactType: ", graph, dimensionRids, targetFactType);

        StringBuilder sb = new StringBuilder();
        for (String rid : dimensionRids) {
            sb.append(rid + ",");
        }
        String rids = sb.toString().substring(0, sb.toString().length() - 1);

        String innerQuery = "select from " + InfoDiscoverEngineConstant.DIMENSION_ROOTCLASSNAME
                + " where @rid in [" + rids + "]";
        logger.info("innerQuery: " + innerQuery);

        List<OrientVertex> verticesList = new ArrayList<>();

        for (OrientVertex v : (Iterable<OrientVertex>) graph.command(
                new OCommandSQL(innerQuery)).execute()) {
            verticesList.add(v);
        }

        List<Vertex> result = new ArrayList<>();
        if (verticesList != null && verticesList.size() > 0) {
            for (int i = 0; i < verticesList.size(); i++) {
                List<Vertex> list = new ArrayList<>();
                Iterable<Vertex> it = verticesList.get(i).getVertices(Direction.BOTH);
                for (Vertex v : it) {
                    String factType = ((OrientVertex) v).getRecord().getSchemaClass()
                            .toString();
                    if (factType.equalsIgnoreCase(targetFactType)) {
                        list.add(v);
                    }
                }

                if (i == 0) {
                    result = list;
                }

                result.retainAll(list);
            }

            logger.debug("similarFactList: {}", result);
        }

        logger.info("Exit getSimilarFactList()...");
        return result;
    }

    public static void main(String[] args) {
        List<String> rids = new ArrayList<>();
        rids.add("#106:3");
        rids.add("#50:60");
        InfoDiscoverAnalytics analytics = new InfoDiscoverAnalytics();

        OrientGraph graph = new OrientGraph("remote:localhost/DemoArch", "root", "wyc");
        List<Vertex> list = analytics.getSimilarFactList(graph, rids, "ID_FACT_DEMO_TASK");
        System.out.println("list: " + list);
        graph.commit();
    }
}
