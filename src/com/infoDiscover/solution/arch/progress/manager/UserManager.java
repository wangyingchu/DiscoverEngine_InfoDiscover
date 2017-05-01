package com.infoDiscover.solution.arch.progress.manager;

import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.arch.progress.constants.ProgressConstants;
import com.infoDiscover.solution.arch.progress.fact.UserDimension;
import com.infoDiscover.solution.arch.progress.util.ProgressUtil;
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

    public Dimension getUserById(InformationExplorer ie, String userId) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {

        ExploreParameters ep = new ExploreParameters();
        ep.setType(ProgressConstants.DIMENSION_USER);
        ep.setDefaultFilteringItem(new EqualFilteringItem("userId", userId));

        return QueryExecutor.executeDimensionQuery(ie, ep);
    }

    public Dimension createUserDimension(InformationExplorer ie, UserDimension user) throws
            InfoDiscoveryEngineRuntimeException,
            InfoDiscoveryEngineInfoExploreException {
        logger.debug("Enter method createUserDimension() with userId: " + user.getUserId());

        // first check if the user is already exists
        Dimension userDimension = getUserById(ie, user.getUserId());

        if (userDimension == null) {
            Map<String, Object> props = new HashMap<String, Object>();
            props.put("userId", user.getUserId());
            props.put("userName", user.getUserName());

            userDimension = ProgressUtil.createDimension(ProgressConstants.DIMENSION_USER, props);
        }

        logger.debug("Exit method createUserDimension()...");
        return userDimension;

    }
}
