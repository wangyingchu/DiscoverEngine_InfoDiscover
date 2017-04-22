package com.infoDiscover.solution.arch.progress.manager;

import com.infoDiscover.common.PrefixConstant;
import com.infoDiscover.common.dimension.time.TimeDimensionGenerator;
import com.infoDiscover.common.dimension.time.dimension.DayDimensionVO;
import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relation;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.arch.database.DatabaseManager;
import com.infoDiscover.solution.arch.progress.constants.ProgressConstants;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sun.
 */
public class ProgressRelationManager {

    private final static Logger logger = LoggerFactory.getLogger(ProgressRelationManager.class);

    public void batchAttachTasksToProgress(InfoDiscoverSpace ids, String progressId, String
            progressFactType, String taskFactType,
                                           String[]
            taskIds) {
        logger.debug("Enter method batchAttachTasksToProgress() with progressId: " + progressId +
                " " +
                "and taskIds: " + taskIds);
        InformationExplorer ie = ids.getInformationExplorer();

        for (String taskId : taskIds) {
            try {
                Fact progress = new ProgressManager().getProgressById(ie, progressId, progressFactType);
                Fact task = new TaskManager().getTaskById(ie, taskId);

                linkFactsByRelationType(progress, task, ProgressConstants.RELATIONTYPE_PROGRESS_HASTASK);
            } catch (InfoDiscoveryEngineRuntimeException e) {
                logger.error(e.getMessage());
            } catch (InfoDiscoveryEngineInfoExploreException e) {
                logger.error(e.getMessage());
            }
        }

        logger.debug("Exit method batchAttachTasksToProgress()...");
    }

    public Relation attachTaskToProgress(String progressId, String progressFactType, String
            taskId) {
        logger.debug("Enter method attachTaskToProgress() with progressId: " + progressId + " " +
                "and taskId: " + taskId);

        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        InformationExplorer ie = ids.getInformationExplorer();
        try {
            Fact progress = new ProgressManager().getProgressById(ie, progressId, progressFactType);
            Fact task = new TaskManager().getTaskById(ie, taskId);

            return linkFactsByRelationType(progress, task, ProgressConstants.RELATIONTYPE_PROGRESS_HASTASK);

        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            e.printStackTrace();
        } finally {
            ids.closeSpace();
        }

        logger.debug("Exit method attachTaskToProgress()...");

        return null;
    }

    public Relation attachTaskToTask(String fromTaskId, String toTaskId) {
        logger.debug("Enter method attachTaskToTask() with fromTaskId: " + fromTaskId + " and " +
                "toTaskId: " + toTaskId);

        TaskManager taskManager = new TaskManager();
        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        InformationExplorer ie = ids.getInformationExplorer();

        try {
            Fact fromTask = taskManager.getTaskById(ie, fromTaskId);
            Fact toTask = taskManager.getTaskById(ie, toTaskId);

            return linkFactsByRelationType(fromTask, toTask, ProgressConstants
                    .RELATIONTYPE_SUBTASK);
        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            e.printStackTrace();
        }

        logger.debug("Exit method attachTaskToTask()...");
        return null;
    }

    public Relation attachRoleToTask(String taskId, String roleId) {
        logger.debug("Enter method attachRoleToTask() with taskId: " + taskId + " and " +
                "roleId: " + roleId);

        TaskManager taskManager = new TaskManager();
        RoleManager roleManager = new RoleManager();
        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        InformationExplorer ie = ids.getInformationExplorer();

        try {
            Fact task = taskManager.getTaskById(ie, taskId);
            Dimension role = roleManager.getRoleById(ie, roleId);

            linkFactToDimensionByRelationType(task, role, ProgressConstants.RELATIONTYPE_TASK_EXECUTEBYROLE);

        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            e.printStackTrace();
        } finally {
            ids.closeSpace();
        }

        logger.debug("Exit method attachRoleToTask()...");
        return null;
    }

    public Relation attachUserToTask(String taskId, String userId) {
        logger.debug("Enter method attachUserToTask() with taskId: " + taskId + " and " +
                "userId: " + userId);

        TaskManager taskManager = new TaskManager();
        UserManager userManager = new UserManager();
        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        InformationExplorer ie = ids.getInformationExplorer();

        try {
            Fact task = taskManager.getTaskById(ie, taskId);
            Dimension user = userManager.getUserById(ie, userId);

            linkFactToDimensionByRelationType(task, user, ProgressConstants.RELATIONTYPE_TASK_EXECUTEBYUSER);

        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            e.printStackTrace();
        } finally {
            ids.closeSpace();
        }

        logger.debug("Exit method attachUserToTask()");
        return null;
    }

    public Relation attachTimeToProgress(InfoDiscoverSpace ids, String progressId,
                                         String progressFactType, DayDimensionVO
            dayDimension, String relationType) {
        logger.debug("Enter method attachTimeToProgress() with progressId: " + progressId + " and " +
                "dayDimension: " + dayDimension.toString());

        ProgressManager progressManager = new ProgressManager();

        InformationExplorer ie = ids.getInformationExplorer();

        try {
            Fact progress = progressManager.getProgressById(ie, progressId, progressFactType);

            Dimension day = getDayDimension(ie, dayDimension);

            if (!ids.hasRelationType(relationType)) {
                ids.addRelationType(relationType);
            }

            linkFactToDimensionByRelationType(progress, day, relationType);

        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineDataMartException e) {
            e.printStackTrace();
        }

        logger.debug("Exit method attachTimeToProgress()");
        return null;
    }

    public Relation attachTimeToTask(InfoDiscoverSpace ids, String taskId, DayDimensionVO
            dayDimension, String relationType) {
        logger.debug("Enter method attachTimeToTask() with taskId: " + taskId + " and " +
                "dayDimension: " + dayDimension.toString());

        TaskManager taskManager = new TaskManager();
        InformationExplorer ie = ids.getInformationExplorer();

        try {
            Fact task = taskManager.getTaskById(ie, taskId);

            Dimension day = getDayDimension(ie, dayDimension);

            if (!ids.hasRelationType(relationType)) {
                ids.addRelationType(relationType);
            }

            linkFactToDimensionByRelationType(task, day, relationType);

        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineDataMartException e) {
            e.printStackTrace();
        }

        logger.debug("Exit method attachTimeToTask()");
        return null;
    }

    // TODO: refine this part
    private Dimension getDayDimension(InformationExplorer ie, DayDimensionVO dayDimension) throws
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(dayDimension.getType());
        ep.setDefaultFilteringItem(new EqualFilteringItem("year", dayDimension.getYear()));
        ep.addFilteringItem(new EqualFilteringItem("month", dayDimension.getMonth()), ExploreParameters
                .FilteringLogic.AND);
        ep.addFilteringItem(new EqualFilteringItem("day", dayDimension.getDay()), ExploreParameters
                .FilteringLogic.AND);

        Dimension day = QueryExecutor.executeDimensionQuery(ie, ep);
        if (day == null) {
            TimeDimensionGenerator.generateYears(PrefixConstant.prefixWithout, new int[]{dayDimension
                    .getYear
                    ()}, 3);
            day = QueryExecutor.executeDimensionQuery(ie, ep);
        }
        return day;
    }


    public Relation attachUserToRole(String roleId, String userId) {
        logger.debug("Enter method attachUserToRole() with roleId: " + roleId + " and " +
                "userId: " + userId);

        RoleManager roleManager = new RoleManager();
        UserManager userManager = new UserManager();

        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        InformationExplorer ie = ids.getInformationExplorer();

        try {
            Dimension role = roleManager.getRoleById(ie, roleId);
            Dimension user = userManager.getUserById(ie, userId);

            linkDimensionsByRelationType(role, user, ProgressConstants.RELATIONTYPE_ROLE_HASUSER);

        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            e.printStackTrace();
        } finally {
            ids.closeSpace();
        }

        logger.debug("Exit method attachUserToRole()...");
        return null;
    }

    public Relation attachUserToUser(String fromUserId, String toUserId) {
        logger.debug("Enter method attachUserToUser() with fromUserId: " + fromUserId + " and " +
                "toUserId: " + toUserId);

        UserManager userManager = new UserManager();

        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        InformationExplorer ie = ids.getInformationExplorer();

        try {
            Dimension fromUser = userManager.getUserById(ie, fromUserId);
            Dimension toUser = userManager.getUserById(ie, toUserId);

            linkDimensionsByRelationType(fromUser, toUser, ProgressConstants
                    .RELATIONTYPE_TRANSFER);
        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            e.printStackTrace();
        } finally {
            ids.closeSpace();
        }

        logger.debug("Exit method attachUserToUser()...");
        return null;
    }

    private Relation linkFactToDimensionByRelationType(Fact fromFact, Dimension toDimension, String
            relationType) {
        logger.debug("Enter method linkFactToDimensionByRelationType() with fromFactId: " +
                fromFact.getId() +
                " " + "and " + "toDimensionId: " + toDimension.getId() + " and relationType: " +
                relationType);

        if (fromFact == null) {
            logger.error("fromFact should not be null");
            return null;
        }

        if (toDimension == null) {
            logger.error("toDimension should not be null");
            return null;
        }

        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        try {

            if (!ids.hasRelationType(relationType)) {
                ids.addRelationType(relationType);
            }

            Relation r = ids.attachFactToDimension(fromFact.getId(), toDimension.getId(),
                    relationType);
            return r;
        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineDataMartException e) {
            e.printStackTrace();
        } finally {
            ids.closeSpace();
        }

        logger.debug("Exit method linkFactToDimensionByRelationType()...");
        return null;
    }

    private Relation linkFactsByRelationType(Fact fromFact, Fact toFact, String relationType) {
        logger.debug("Enter method linkFactsByRelationType() with fromFactId: " + fromFact.getId() +
                " " + "and " + "toFactId: " + toFact.getId() + " and relationType: " +
                relationType);

        if (fromFact == null) {
            logger.error("fromFact should not be null");
            return null;
        }

        if (toFact == null) {
            logger.error("toFact should not be null");
            return null;
        }

        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
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

        logger.debug("Exit method linkFactsByRelationType()...");
        return null;
    }

    private Relation linkDimensionsByRelationType(Dimension fromDimension, Dimension toDimension,
                                                  String
                                                          relationType) {
        logger.debug("Enter method linkDimensionsByRelationType() with fromDimensionId: " +
                fromDimension.getId() +
                " " + "and " + "toDimensionId: " + toDimension.getId() + " and relationType: " +
                relationType);

        if (fromDimension == null) {
            logger.error("fromDimension should not be null");
            return null;
        }

        if (toDimension == null) {
            logger.error("toDimension should not be null");
            return null;
        }

        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        try {

            if (!ids.hasRelationType(relationType)) {
                ids.addRelationType(relationType);
            }

            Relation r = ids.addDirectionalDimensionRelation(fromDimension, toDimension,
                    relationType, false);
            return r;
        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineDataMartException e) {
            e.printStackTrace();
        } finally {
            ids.closeSpace();
        }

        logger.debug("Exit method linkDimensionsByRelationType()...");
        return null;
    }
}
