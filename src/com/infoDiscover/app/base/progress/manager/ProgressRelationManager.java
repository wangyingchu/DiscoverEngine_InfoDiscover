package com.infoDiscover.app.base.progress.manager;

import com.infoDiscover.app.base.helper.Helper;
import com.infoDiscover.app.base.database.DatabaseManager;
import com.infoDiscover.app.base.executor.QueryExecutor;
import com.infoDiscover.app.base.progress.constants.ProgressConstants;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relation;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relationable;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;

import java.util.List;

/**
 * Created by sun.
 */
public class ProgressRelationManager {

    public Relation attachTaskToProgress(String progressId, String taskId) {
        ProgressQueryManager queryManager = new ProgressQueryManager();
        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        InformationExplorer ie = ids.getInformationExplorer();
        try {
            Fact progress = queryManager.getProgressById(ie, progressId);
            Fact task = queryManager.getTaskById(ie, taskId);

            return linkFactsByRelationType(progress, task, ProgressConstants.RELATIONTYPE_PROGRESS);

        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            e.printStackTrace();
        } finally {
            ids.closeSpace();
        }

        return null;
    }

    public Relation attachTaskToTask(String fromTaskId, String toTaskId) {
        ProgressQueryManager queryManager = new ProgressQueryManager();
        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        InformationExplorer ie = ids.getInformationExplorer();

        try {
            Fact fromTask = queryManager.getTaskById(ie, fromTaskId);
            Fact toTask = queryManager.getTaskById(ie, toTaskId);

            return linkFactsByRelationType(fromTask, toTask, ProgressConstants
                    .RELATIONTYPE_SUBTASK);
        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Relation attachRoleToTask(String taskId, String roleId) {
        ProgressQueryManager queryManager = new ProgressQueryManager();
        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        InformationExplorer ie = ids.getInformationExplorer();

        try {
            Fact task = queryManager.getTaskById(ie, taskId);
            Fact role = queryManager.getRoleById(ie, roleId);

            linkFactsByRelationType(task, role, ProgressConstants.RELATIONTYPE_TASK_BYROLE);

        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            e.printStackTrace();
        } finally {
            ids.closeSpace();
        }

        return null;
    }

    public Relation attachUserToTask(String taskId, String userId) {
        ProgressQueryManager queryManager = new ProgressQueryManager();
        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        InformationExplorer ie = ids.getInformationExplorer();

        try {
            Fact task = queryManager.getTaskById(ie, taskId);
            Fact user = queryManager.getUserById(ie, userId);

            linkFactsByRelationType(task, user, ProgressConstants.RELATIONTYPE_TASK_BYUSER);

        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            e.printStackTrace();
        } finally {
            ids.closeSpace();
        }

        return null;
    }

    public Relation attachUserToRole(String roleId, String userId) {
        ProgressQueryManager queryManager = new ProgressQueryManager();
        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        InformationExplorer ie = ids.getInformationExplorer();

        try {
            Fact role = queryManager.getRoleById(ie, roleId);
            Fact user = queryManager.getUserById(ie, userId);

            linkFactsByRelationType(role, user, ProgressConstants.RELATIONTYPE_ROLE);

        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            e.printStackTrace();
        } finally {
            ids.closeSpace();
        }

        return null;
    }

    public Relation attachUserToUser(String fromUserId, String toUserId) {
        ProgressQueryManager queryManager = new ProgressQueryManager();
        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        InformationExplorer ie = ids.getInformationExplorer();

        try {
            Fact fromUser = queryManager.getUserById(ie, fromUserId);
            Fact toUser = queryManager.getUserById(ie, toUserId);

            linkFactsByRelationType(fromUser, toUser, ProgressConstants.RELATIONTYPE_TRANSFER_TASK);
        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            e.printStackTrace();
        } finally {
            ids.closeSpace();
        }

        return null;
    }

    private Relation linkFactsByRelationType(Fact fromFact, Fact toFact, String relationType) {
        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        InformationExplorer ie = ids.getInformationExplorer();
        try {

            if (!ids.hasRelationType(relationType)) {
                ids.addRelationType(relationType);
            }

            Relation r = ids.addDirectionalFactRelation(fromFact, toFact, relationType, false);
            return r;
        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineDataMartException e) {
            e.printStackTrace();
        } finally {
            ids.closeSpace();
        }

        return null;
    }
}
