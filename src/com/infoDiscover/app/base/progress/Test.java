package com.infoDiscover.app.base.progress;

import com.infoDiscover.app.base.database.DatabaseManager;
import com.infoDiscover.app.base.progress.analytics.ProgressAnalytics;
import com.infoDiscover.app.base.progress.fact.ProgressFact;
import com.infoDiscover.app.base.progress.fact.RoleFact;
import com.infoDiscover.app.base.progress.fact.TaskFact;
import com.infoDiscover.app.base.progress.fact.UserFact;
import com.infoDiscover.app.base.progress.manager.ProgressManager;
import com.infoDiscover.app.base.progress.manager.ProgressQueryManager;
import com.infoDiscover.app.base.relationship.RelationshipManager;
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

        checkRelationship();
    }

    private static void select() throws InfoDiscoveryEngineInfoExploreException {
        ProgressAnalytics analytics = new ProgressAnalytics();
        String progressId1 = "progressId1";
        String taskId1 = "taskId1";
        String userId1 = "userId1";
        List<Relationable> taskList = analytics.getAllTasksOfProgress(progressId1);
        println("taskList.size " + taskList.size());
        for(Relationable fact : taskList) {
            println("id: " + fact.getId());
        }

        List<Relationable> progressUserList = analytics.getAllUsersOfProgress(progressId1);
        println("progressUserList.size " + progressUserList.size());
        for(Relationable fact : progressUserList) {
            println("id: " + fact.getId());
        }

        List<Relationable> taskUserList = analytics.getAllUsersOfTask(taskId1);
        println("taskUserList.size " + taskUserList.size());
        for(Relationable fact : taskUserList) {
            println("id: " + fact.getId());
        }

        List<Relationable> taskByUserIdList = analytics.getAllTasksOfUser(userId1);
        println("taskByUserIdList.size" + taskByUserIdList.size());
        for(Relationable fact : taskByUserIdList) {
            println("id: " + fact.getId());
        }
    }

    private static  void checkRelationship() throws InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {
        ProgressQueryManager queryManager = new ProgressQueryManager();
        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        InformationExplorer ie = ids.getInformationExplorer();
        Fact fromFact = queryManager.getProgressById(ie, "progressId1");
        Fact toFact = queryManager.getTaskById(ie, "taskId2");

        RelationshipManager manager = new RelationshipManager();
        boolean flag = manager.checkRelationship(fromFact, toFact);
        println("flag: " + flag);
    }

    private static void println(String message) {
        System.out.println(message);
    }


    private static void createFact() throws InfoDiscoveryEngineRuntimeException,
            InfoDiscoveryEngineDataMartException {
        ProgressManager.initProgressFactType();
        ProgressManager.initProgressRelationType();

        // create progress 1
        ProgressQueryManager queryManager = new ProgressQueryManager();
        Fact progress = queryManager.createProgressFact(new ProgressFact("progressId1",
                "progressContent"));
        String progressId = progress.getId();
        println("progressId: " + progressId);

        // create task1
        Fact task1 = queryManager.createTaskFact(new TaskFact("progressId1", "taskId1",
                "taskContent"));
        String taskId1 = task1.getId();
        println("taskId1: " + taskId1);

        // create task2
        Fact task2 = queryManager.createTaskFact(new TaskFact("progressId1", "taskId2",
                "taskContent2"));
        String taskId2 = task2.getId();
        println("taskId2: " + taskId2);

        // create Role1
        Fact role = queryManager.createRoleFact(new RoleFact("roleId1", "role1"));
        String roleId = role.getId();
        println("roleId: " + roleId);

        // create User1
        Fact user1 = queryManager.createUserFact(new UserFact("userId1", "user1"));
        String userId1 = user1.getId();
        println("userId1: " + userId1);

        Fact user2 = queryManager.createUserFact(new UserFact("userId2", "user2"));
        String userId2 = user2.getId();
        println("userId2: " + userId2);
    }
}
