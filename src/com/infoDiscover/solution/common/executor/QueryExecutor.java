package com.infoDiscover.solution.common.executor;

import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relationable;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;

import java.util.List;

/**
 * Created by sun.
 */
public class QueryExecutor {

    public static List<Relationable> executeFactQuery(InfoDiscoverSpace ids, String sql) {
        InformationExplorer ie = ids.getInformationExplorer();
        List<Relationable> result = ie.discoverRelationables(sql);
        return result;
    }

    public static List<Fact> executeFactsQuery(InformationExplorer ie, ExploreParameters ep) {
        try {
            List<Fact> facts = ie.discoverFacts(ep);
            return  facts;
        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Fact executeFactQuery(InformationExplorer ie, ExploreParameters ep) {

        List<Fact> facts = executeFactsQuery(ie, ep);
        if (facts != null && facts.size() > 0) {
            return facts.get(0);
        }

        return null;
    }

    public static Dimension executeDimensionQuery(InformationExplorer ie, ExploreParameters ep) {
        try {
            List<Dimension> dimensionList = ie.discoverDimensions(ep);
            if (dimensionList != null && dimensionList.size() > 0) {
                return dimensionList.get(0);
            }
            return null;
        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            e.printStackTrace();
        }
        return null;
    }

}
