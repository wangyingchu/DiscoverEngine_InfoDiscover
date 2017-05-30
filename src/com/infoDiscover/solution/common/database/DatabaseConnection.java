package com.infoDiscover.solution.common.database;

import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;

import java.rmi.server.ExportException;

/**
 * Created by sun.
 */
public class DatabaseConnection {
    public static InfoDiscoverSpace connectToSpace(String spaceName) throws Exception {
        InfoDiscoverSpace ids = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);

        if (ids == null) {
            throw new ExportException("Could not connect to space: " + spaceName);
        }

        return ids;
    }
}
