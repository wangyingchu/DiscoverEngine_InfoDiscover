package com.infoDiscover.solution.arch.progress;

import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.solution.arch.database.DatabaseManager;
import com.infoDiscover.solution.arch.progress.analytics.ProgressAnalytics;
import com.infoDiscover.solution.arch.progress.constants.ProgressConstants;
import com.infoDiscover.solution.arch.progress.fact.ProgressFact;
import com.infoDiscover.solution.arch.progress.fact.RoleDimension;
import com.infoDiscover.solution.arch.progress.fact.TaskFact;
import com.infoDiscover.solution.arch.progress.fact.UserDimension;
import com.infoDiscover.solution.arch.progress.manager.*;
import com.infoDiscover.solution.common.relationship.RelationshipManager;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relationable;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;

import java.util.List;

/**
 * Created by sun.
 */
public class Test {
    public static void main(String[] args) throws InfoDiscoveryEngineDataMartException,
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {

        String progressId = "progressId1";
        String taskId1 = "taskId1";
        String taskId2 = "taskId2";
        String roleId = "roleId";
        String userId1 = "userId1";
        String userId2 = "userId2";
//        String progressId = "#145:0";
//        String taskId1 = "#153:0";
//        String taskId2 = "#154:0";
//        String roleId = "#161:0";
//        String userId1 = "#169:0";
//        String userId2 = "#170:0";

        // link facts
//        ProgressRelationManager relationManager = new ProgressRelationManager();
//        Relation r1 = relationManager.attachTaskToProgress(progressId,taskId1);
//
//        Relation r2 = relationManager.attachTaskToTask(taskId1, taskId2);
//
//        Relation r3 = relationManager.attachUserToTask(taskId1, userId1);
//
//        Relation r4 = relationManager.attachUserToTask(taskId2, userId1);
//        String sql = "select from (TRAVERSE out() FROM #145:0 WHILE $depth <= 3) where " +
//                "@class=\"ID_FACT_TASK_test\" and taskId=\"taskId2\"";

//        select();

    }

    private static void select(InfoDiscoverSpace ids) throws InfoDiscoveryEngineInfoExploreException {
        ProgressAnalytics analytics = new ProgressAnalytics();
        String progressId1 = "progressId1";
        String taskId1 = "taskId1";
        String userId1 = "userId1";
        List<Relationable> taskList = analytics.getAllTasksOfProgress(ids, progressId1);
        println("taskList.size " + taskList.size());
        for(Relationable fact : taskList) {
            println("id: " + fact.getId());
        }

        List<Relationable> progressUserList = analytics.getAllUsersOfProgress(ids, progressId1);
        println("progressUserList.size " + progressUserList.size());
        for(Relationable fact : progressUserList) {
            println("id: " + fact.getId());
        }

        List<Relationable> taskUserList = analytics.getAllUsersOfTask(ids, taskId1);
        println("taskUserList.size " + taskUserList.size());
        for(Relationable fact : taskUserList) {
            println("id: " + fact.getId());
        }

        List<Relationable> taskByUserIdList = analytics.getAllTasksOfUser(ids, userId1);
        println("taskByUserIdList.size" + taskByUserIdList.size());
        for(Relationable fact : taskByUserIdList) {
            println("id: " + fact.getId());
        }
    }



    private static void println(String message) {
        System.out.println(message);
    }


    private static void createFact(InformationExplorer ie, String prefix) throws
            InfoDiscoveryEngineRuntimeException,
            InfoDiscoveryEngineDataMartException, InfoDiscoveryEngineInfoExploreException {
        ProgressInitializer.initProgressFactType(prefix);
        ProgressInitializer.initProgressRelationType(prefix);

        // create progress 1
        ProgressManager progressManager = new ProgressManager();
        TaskManager taskManager = new TaskManager();
        Fact progress = progressManager.createProgressFact(ie, new ProgressFact(ProgressConstants
                .FACT_PROGRESS, "progressId1",
                "progressContent"));
        String progressId = progress.getId();
        println("progressId: " + progressId);

        // create task1
        Fact task1 = taskManager.createTaskFact(ie, new TaskFact("progressId1", "taskId1",
                "taskContent"));
        String taskId1 = task1.getId();
        println("taskId1: " + taskId1);

        // create task2
        Fact task2 = taskManager.createTaskFact(ie, new TaskFact("progressId1", "taskId2",
                "taskContent2"));
        String taskId2 = task2.getId();
        println("taskId2: " + taskId2);

        // create Role1
        Dimension role = new RoleManager().createRoleDimension(ie, new RoleDimension("roleId1",
                "role1"));
        String roleId = role.getId();
        println("roleId: " + roleId);

        // create User1
        UserManager userManager = new UserManager();
        Dimension user1 = userManager.createUserDimension(ie, new UserDimension("userId1",
                "user1"));
        String userId1 = user1.getId();
        println("userId1: " + userId1);

        Dimension user2 = userManager.createUserDimension(ie, new UserDimension("userId2",
                "user2"));
        String userId2 = user2.getId();
        println("userId2: " + userId2);
    }
}
