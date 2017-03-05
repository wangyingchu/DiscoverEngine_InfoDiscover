package com.infoDiscover.app.base.executor;

import com.infoDiscover.app.base.database.DatabaseManager;
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

    public static List<Relationable> executeQuery(String sql) {
        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        InformationExplorer ie = ids.getInformationExplorer();
        List<Relationable> result = ie.discoverRelationables(sql);
        ids.closeSpace();
        return result;
    }

    public static Fact executeQuery(InformationExplorer ie, ExploreParameters ep) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {
        List<Fact> facts = ie.discoverFacts(ep);
        if (facts != null && facts.size() > 0) {
            return facts.get(0);
        }
        return null;
    }
}
