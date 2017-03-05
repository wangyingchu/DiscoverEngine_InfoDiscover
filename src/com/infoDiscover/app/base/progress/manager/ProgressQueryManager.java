package com.infoDiscover.app.base.progress.manager;

import com.infoDiscover.app.base.database.DatabaseManager;
import com.infoDiscover.app.base.executor.QueryExecutor;
import com.infoDiscover.app.base.progress.constants.ProgressConstants;
import com.infoDiscover.app.base.progress.fact.ProgressFact;
import com.infoDiscover.app.base.progress.fact.RoleFact;
import com.infoDiscover.app.base.progress.fact.TaskFact;
import com.infoDiscover.app.base.progress.fact.UserFact;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun.
 */
public class ProgressQueryManager {
    public Fact getProgressById(InformationExplorer ie, String progressId) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {

        ExploreParameters ep = new ExploreParameters();
        ep.setType(ProgressConstants.FACT_PROGRESS);
        ep.setDefaultFilteringItem(new EqualFilteringItem("progressId", progressId));

        return QueryExecutor.executeQuery(ie, ep);
    }

    public Fact getTaskById(InformationExplorer ie, String taskId) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {

        ExploreParameters ep = new ExploreParameters();
        ep.setType(ProgressConstants.FACT_TASK);
        ep.setDefaultFilteringItem(new EqualFilteringItem("taskId", taskId));

        return QueryExecutor.executeQuery(ie, ep);
    }

    public Fact getRoleById(InformationExplorer ie, String roleId) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {

        ExploreParameters ep = new ExploreParameters();
        ep.setType(ProgressConstants.FACT_ROLE);
        ep.setDefaultFilteringItem(new EqualFilteringItem("roleId", roleId));

        return QueryExecutor.executeQuery(ie, ep);
    }

    public Fact getUserById(InformationExplorer ie, String userId) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {

        ExploreParameters ep = new ExploreParameters();
        ep.setType(ProgressConstants.FACT_USER);
        ep.setDefaultFilteringItem(new EqualFilteringItem("userId", userId));

        return QueryExecutor.executeQuery(ie, ep);
    }



    public Fact createProgressFact(ProgressFact progress) throws
            InfoDiscoveryEngineRuntimeException {
        println("Start to create progress fact: " + progress.getProgressId());
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("progressId", progress.getProgressId());
        props.put("content", progress.getContent());

        Fact fact = createFact(ProgressConstants.FACT_PROGRESS, props);

        println("End to create progress fact");
        return fact;
    }

    public Fact createTaskFact(TaskFact task) throws
            InfoDiscoveryEngineRuntimeException {
        println("Start to create task fact: " + task.getTaskId());
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("progressId", task.getProgressId());
        props.put("taskId", task.getTaskId());
        props.put("content", task.getContent());

        Fact fact = createFact(ProgressConstants.FACT_TASK, props);

        println("End to create task fact");
        return fact;
    }

    public Fact createRoleFact(RoleFact role) throws InfoDiscoveryEngineRuntimeException {
        println("Start to create role fact: " + role.getRoleId());
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("roleId", role.getRoleId());
        props.put("roleName", role.getRoleName());

        Fact fact = createFact(ProgressConstants.FACT_ROLE, props);

        println("Start to create role fact");
        return fact;
    }

    public Fact createUserFact(UserFact user) throws InfoDiscoveryEngineRuntimeException {
        println("Start to create user fact: " + user.getUserId());
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("userId", user.getUserId());
        props.put("userName", user.getUserName());

        Fact fact = createFact(ProgressConstants.FACT_USER, props);

        println("Start to create role fact");
        return fact;
    }

    private Fact createFact(String type, Map<String, Object>
            properties) throws InfoDiscoveryEngineRuntimeException {
        println("Start to create fact with type: " + type + " and properties: " +
                properties);

        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        Fact fact = DiscoverEngineComponentFactory.createFact(type);
        fact = ids.addFact(fact);
        fact.addProperties(properties);
        ids.closeSpace();
        println("End to create fact");
        return fact;
    }

    private void println(String msg) {
        System.out.println(msg);
    }
}
