package com.infoDiscover.solution.arch.progress.manager;

import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.arch.progress.fact.UserDimension;
import com.infoDiscover.solution.common.dimension.DimensionManager;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun.
 */
public class UserManager {

    private final static Logger logger = LoggerFactory.getLogger(UserManager.class);

    public Dimension getUserById(InformationExplorer ie, String userId, String dimensionType)
            throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {

        ExploreParameters ep = new ExploreParameters();
        ep.setType(dimensionType);
        ep.setDefaultFilteringItem(new EqualFilteringItem("userId", userId));

        return QueryExecutor.executeDimensionQuery(ie, ep);
    }

    public Dimension createUserDimension(InfoDiscoverSpace ids,
                                         UserDimension user,
                                         String dimensionType) throws
            InfoDiscoveryEngineRuntimeException,
            InfoDiscoveryEngineInfoExploreException {
        logger.debug("Enter method createUserDimension() with userId: " + user.getUserId());

        // first check if the user is already exists
        Dimension userDimension = getUserById(ids.getInformationExplorer(), user.getUserId(),dimensionType);
        if (userDimension == null) {
            Map<String, Object> props = new HashMap<String, Object>();
            props.put("userId", user.getUserId());
            props.put("userName", user.getUserName());

            DimensionManager manager = new DimensionManager(ids);
            userDimension = manager.createDimension(dimensionType, props);
        }

        logger.debug("Exit method createUserDimension()...");
        return userDimension;

    }
}
