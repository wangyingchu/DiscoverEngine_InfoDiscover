package com.infoDiscover.solution.arch.progress.manager;

import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.arch.progress.constants.ProgressConstants;
import com.infoDiscover.solution.arch.progress.fact.RoleDimension;
import com.infoDiscover.solution.arch.progress.util.ProgressUtil;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun.
 */
public class RoleManager {
    private final static Logger logger = LoggerFactory.getLogger(RoleManager.class);

    public Dimension createRoleDimension(InfoDiscoverSpace ids, RoleDimension role, String
            dimensionType) throws
            InfoDiscoveryEngineRuntimeException,
            InfoDiscoveryEngineInfoExploreException {
        logger.debug("Enter method createRoleDimension() with roleId: " + role.getRoleId());

        // check if role is already existed
        Dimension roleDimension = getRoleById(ids.getInformationExplorer(), role.getRoleId(), dimensionType);
        if (roleDimension == null) {
            Map<String, Object> props = new HashMap<String, Object>();
            props.put("roleId", role.getRoleId());
            props.put("roleName", role.getRoleName());

            roleDimension = ProgressUtil.createDimension(ids, dimensionType, props);
        }
        logger.debug("Exit method createRoleDimension()...");
        return roleDimension;
    }

    public Dimension getRoleById(InformationExplorer ie, String roleId, String dimensionType) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {

        ExploreParameters ep = new ExploreParameters();
        ep.setType(dimensionType);
        ep.setDefaultFilteringItem(new EqualFilteringItem("roleId", roleId));

        return QueryExecutor.executeDimensionQuery(ie, ep);
    }

}
