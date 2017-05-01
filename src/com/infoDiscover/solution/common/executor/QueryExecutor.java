package com.infoDiscover.solution.common.executor;

import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.solution.arch.database.DatabaseManager;
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

//    public static List<Relationable> executeFactQuery(String sql) {
//        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
//        InformationExplorer ie = ids.getInformationExplorer();
//        List<Relationable> result = ie.discoverRelationables(sql);
//        ids.closeSpace();
//        return result;
//    }

    public static List<Relationable> executeFactQuery(InfoDiscoverSpace ids, String sql) {
        InformationExplorer ie = ids.getInformationExplorer();
        List<Relationable> result = ie.discoverRelationables(sql);
        return result;
    }

    public static Fact executeFactQuery(InformationExplorer ie, ExploreParameters ep) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {

        List<Fact> facts = ie.discoverFacts(ep);
        if (facts != null && facts.size() > 0) {
            return facts.get(0);
        }
        return null;
    }

//    public static Fact executeFactQuery(ExploreParameters ep) throws
//            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {
//        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
//        InformationExplorer ie = ids.getInformationExplorer();
//
//        List<Fact> facts = ie.discoverFacts(ep);
//        ids.closeSpace();
//        if (facts != null && facts.size() > 0) {
//            return facts.get(0);
//        }
//        return null;
//    }

    public static Dimension executeDimensionQuery(InformationExplorer ie, ExploreParameters ep)
            throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {

        List<Dimension> dimensionList = ie.discoverDimensions(ep);

        if (dimensionList != null && dimensionList.size() > 0) {
            return dimensionList.get(0);
        }
        return null;
    }

//    public static Dimension executeDimensionQuery(ExploreParameters ep)
//            throws
//            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {
//
//        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
//        InformationExplorer ie = ids.getInformationExplorer();
//        List<Dimension> dimensionList = ie.discoverDimensions(ep);
//
//        ids.closeSpace();
//
//        if (dimensionList != null && dimensionList.size() > 0) {
//            return dimensionList.get(0);
//        }
//        return null;
//    }

}
