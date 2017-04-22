package com.infoDiscover.solution.common.analytics;

import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.common.util.Helper;
import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relationable;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by sun.
 */
public class InfoDiscoverAnalytics {

    private final static Logger logger = LoggerFactory.getLogger(InfoDiscoverAnalytics.class);

    public List<Relationable> getAllLinkedFacts(Fact fromFact) {
        String sql = "select from (TRAVERSE both() FROM " + fromFact.getId() + ")";
        System.out.println("getRelatedFacts() sql: " + sql);

        List<Relationable> results = QueryExecutor.executeFactQuery(sql);
        return results;
    }

    public List<Relationable> getSimilarFacts(List<Dimension> dimensions, String targetFactType) {
        StringBuilder dimensionRids = new StringBuilder();
        for (Dimension dimension: dimensions) {
            dimensionRids.append(dimension.getId() + ",");
        }
        String rids = dimensionRids.toString().substring(0, dimensionRids.toString().length() - 1);

        String innerQuery = "(select from " + InfoDiscoverEngineConstant.DIMENSION_ROOTCLASSNAME
                + " where " +
                "@rid in [" + rids + "])";

        String sql = "select from (TRAVERSE both() FROM " + innerQuery +" ) where $depth >= 1 and" +
                " @class=" + Helper.addDoubleQuotation(targetFactType);
        System.out.println("sql: " + sql);

        List<Relationable> results = QueryExecutor.executeFactQuery(sql);

        return results;
    }
}
