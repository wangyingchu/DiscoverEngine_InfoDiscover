package com.infoDiscover.app.base.database;

import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;

/**
 * Created by sun.
 */
public class DatabaseManager {
    public static InfoDiscoverSpace getInfoDiscoverSpace() {
        return DiscoverEngineComponentFactory.connectInfoDiscoverSpace(DatabaseConstants
                .INFODISCOVER_SPACENAME);
    }
}
