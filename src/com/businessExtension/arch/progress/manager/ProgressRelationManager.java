package com.businessExtension.arch.progress.manager;

import com.businessExtension.constructionSupervision.constants.PrefixConstant;
import com.infoDiscover.common.dimension.time.dimension.DayDimensionVO;
import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relation;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.common.dimension.DimensionManager;
import com.infoDiscover.solution.common.relationship.RelationshipManager;
import com.businessExtension.constructionSupervision.constants.DatabaseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sun.
 */
public class ProgressRelationManager {

    private final static Logger logger = LoggerFactory.getLogger(ProgressRelationManager.class);

    private InfoDiscoverSpace ids;

    public ProgressRelationManager(InfoDiscoverSpace ids) {
        this.ids = ids;
    }

    //TODO:
    public static final String prefix = PrefixConstant.prefix;


    public void batchAttachTasksToProgress(String progressId,
                                           String progressFactType, String taskFactType,
                                           String[] taskIds, String relationType) {
        logger.debug("Enter method batchAttachTasksToProgress() with progressId: " + progressId +
                " " +
                "and taskIds: " + taskIds);
        InformationExplorer ie = ids.getInformationExplorer();

        for (String taskId : taskIds) {
            try {
                Fact progress = new DemoProgressManager().getProgressById(ie, progressId,
                        progressFactType);
                Fact task = new DemoTaskManager().getTaskById(ie, taskId, taskFactType);

                new RelationshipManager(ids).linkFactsByRelationType(progress, task,
                        relationType);
            } catch (InfoDiscoveryEngineRuntimeException e) {
                logger.error(e.getMessage());
            } catch (InfoDiscoveryEngineInfoExploreException e) {
                logger.error(e.getMessage());
            }
        }

        logger.debug("Exit method batchAttachTasksToProgress()...");
    }

    public Relation attachTaskToProgress(Fact progress, Fact task, String relationType) {
        return new RelationshipManager(ids).linkFactsByRelationType(progress, task, relationType);
    }

    public Relation attachTaskToTask(Fact fromTask, Fact toTask,
                                     String relationType) {
        return new RelationshipManager(ids).linkFactsByRelationType(fromTask, toTask, relationType);
    }

    public Relation attachRoleToTask(Fact task, Dimension role, String relationType) {
        return new RelationshipManager(ids).linkFactToDimension(task, role, relationType);
    }

    public Relation attachUserToTask(Fact task, Dimension user, String relationType) {
        return new RelationshipManager(ids).linkFactToDimension(task, user, relationType);
    }

    public Relation attachTimeToProgress(String progressId,
                                         String progressFactType,
                                         DayDimensionVO dayDimension,
                                         String relationType) {
        logger.debug("Enter method attachTimeToProgress() with progressId: " + progressId +
                " and dayDimension: " + dayDimension.toString());

        DemoProgressManager demoProgressManager = new DemoProgressManager();

        InformationExplorer ie = ids.getInformationExplorer();

        try {
            Fact progress = demoProgressManager.getProgressById(ie, progressId, progressFactType);

            Dimension day = new DimensionManager(ids).getDayDimension(DatabaseConstants
                    .SOLUTION_PREFIX, dayDimension);

            if (!ids.hasRelationType(relationType)) {
                ids.addRelationType(relationType);
            }

            new RelationshipManager(ids).linkFactToDimension(progress, day, relationType);

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

    public Relation attachTimeToTask(Fact task, DayDimensionVO
            dayDimension, String relationType) {
        logger.debug("Enter method attachTimeToTask() with taskId: " + task.getId() + " and " +
                "dayDimension: " + dayDimension.toString());

        try {
            Dimension day = new DimensionManager(ids).getDayDimension(DatabaseConstants
                    .SOLUTION_PREFIX, dayDimension);
            if (!ids.hasRelationType(relationType)) {
                ids.addRelationType(relationType);
            }
            return new RelationshipManager(ids).linkFactToDimension(task, day,
                    relationType);
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

    public Relation attachUserToRole(Dimension role, Dimension user, String relationType) {
        return new RelationshipManager(ids).linkDimensionsByRelationType(role, user, relationType);
    }

    public Relation attachUserToExecuteDepartment(Dimension department, Dimension user, String
            relationType) {
        return new RelationshipManager(ids).linkDimensionsByRelationType(department, user,
                relationType);
    }

    public Relation attachUserToUser(Dimension fromUser, Dimension toUser, String relationType) {
        return new RelationshipManager(ids).linkDimensionsByRelationType(fromUser, toUser,
                relationType);
    }

    public Relation addExternalUserToCompany(Dimension fromUser, Dimension toCompany, String
            relationType) {
        return new RelationshipManager(ids).linkDimensionsByRelationType(fromUser, toCompany,
                relationType);
    }

    public Relation addCompanyToClassification(Dimension fromCompany, Dimension toClassificaiton,
                                               String relationType) {
        return new RelationshipManager(ids).linkDimensionsByRelationType(fromCompany,
                toClassificaiton, relationType);
    }

    public Relation addProjectAddressToRoad(Dimension fromAddress, Dimension
            toRoad, String relationType) {
        return new RelationshipManager(ids).linkDimensionsByRelationType(fromAddress, toRoad,
                relationType);
    }


}
