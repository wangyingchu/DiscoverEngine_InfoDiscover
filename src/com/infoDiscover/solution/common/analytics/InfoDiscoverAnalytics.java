package com.infoDiscover.solution.common.analytics;

import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relationable;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.arch.database.DatabaseConstants;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.common.util.Helper;
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

    public List<Relationable> getSimilarFacts(InfoDiscoverSpace ids, List<Dimension> dimensions,
                                              String targetFactType) {

        List<String> ridList = new ArrayList<>();

        for (Dimension dimension : dimensions) {
            ridList.add(dimension.getId());
        }

        return getSimilarFactList(ids, ridList, targetFactType);
    }

    public List<Relationable> getSimilarFactList(InfoDiscoverSpace ids, List<String> dimensionIds,
                                                 String targetFactType) {
        logger.info("Enter getSimilarFactList() with dimensionIs: {} and targetFactType: {}",
                dimensionIds, targetFactType);
        StringBuilder dimensionRids = new StringBuilder();
        for (String rid : dimensionIds) {
            dimensionRids.append(rid + ",");
        }
        String rids = dimensionRids.toString().substring(0, dimensionRids.toString().length() - 1);

        String innerQuery = "(select from " + InfoDiscoverEngineConstant.DIMENSION_ROOTCLASSNAME
                + " where " +
                "@rid in [" + rids + "])";

        String sql = "select from (select from (TRAVERSE both() FROM " + innerQuery + " while " +
                "$depth < 2)) where" +
                " @class=" + Helper.addDoubleQuotation(targetFactType);
        logger.debug("sql: " + sql);

        List<Relationable> results = QueryExecutor.executeFactQuery(ids, sql);

        logger.info("Exit getSimilarFactList()...");
        return results;
    }

    public static void main(String[] args) {
        List<String> rids = new ArrayList<>();
        rids.add("#106:3");

        InfoDiscoverSpace ids  = DiscoverEngineComponentFactory.connectInfoDiscoverSpace
                ("DemoArch");

        InfoDiscoverAnalytics a = new InfoDiscoverAnalytics();
        List<Relationable> results = a.getSimilarFactList(ids,rids, "ID_FACT_DEMO_TASK");
        for (Relationable r : results) {
            System.out.println("similarFacts: " + r.getId());
        }

        results = a.getAllLinkedFacts(ids, "#108:3", 1);
        for (Relationable r : results) {
            System.out.println("allLinkedFacts: " + r.getId());
        }

        ids.closeSpace();
    }
}
