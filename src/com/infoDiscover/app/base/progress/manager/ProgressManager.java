package com.infoDiscover.app.base.progress.manager;

import com.infoDiscover.app.base.database.DatabaseConstants;
import com.infoDiscover.app.base.database.DatabaseManager;
import com.infoDiscover.app.base.progress.constants.ProgressConstants;
import com.infoDiscover.infoDiscoverEngine.dataMart.*;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;

/**
 * Created by sun.
 */
public class ProgressManager {

    public static void initProgressFactType() throws InfoDiscoveryEngineDataMartException {
        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();

        if (ids != null) {
            if (!ids.hasFactType(ProgressConstants.FACT_PROGRESS)) {
                FactType progressType = ids.addFactType(ProgressConstants.FACT_PROGRESS);
            }
            if (!ids.hasFactType(ProgressConstants.FACT_TASK)) {
                FactType taskType = ids.addFactType(ProgressConstants.FACT_TASK);
            }
            if (!ids.hasFactType(ProgressConstants.FACT_ROLE)) {
                FactType roleType = ids.addFactType(ProgressConstants.FACT_ROLE);
            }
            if (!ids.hasFactType(ProgressConstants.FACT_USER)) {
                FactType userType = ids.addFactType(ProgressConstants.FACT_USER);
            }
        } else {
            println("Failed to connect to database: " + DatabaseConstants.INFODISCOVER_SPACENAME);
        }
        ids.closeSpace();
    }

    public static void initProgressRelationType() throws InfoDiscoveryEngineDataMartException {
        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();

        if (ids != null) {
            if (!ids.hasRelationType(ProgressConstants.RELATIONTYPE_PROGRESS)) {
                RelationType relationType = ids.addRelationType(ProgressConstants
                        .RELATIONTYPE_PROGRESS);
            }

            if (!ids.hasRelationType(ProgressConstants.RELATIONTYPE_TASK_BYROLE)) {
                RelationType relationType = ids.addRelationType(ProgressConstants
                        .RELATIONTYPE_TASK_BYROLE);
            }

            if (!ids.hasRelationType(ProgressConstants.RELATIONTYPE_TASK_BYUSER)) {
                RelationType relationType = ids.addRelationType(ProgressConstants
                        .RELATIONTYPE_TASK_BYUSER);
            }

            if (!ids.hasRelationType(ProgressConstants.RELATIONTYPE_SUBTASK)) {
                RelationType relationType = ids.addRelationType(ProgressConstants
                        .RELATIONTYPE_SUBTASK);
            }

            if (!ids.hasRelationType(ProgressConstants.RELATIONTYPE_ROLE)) {
                RelationType relationType = ids.addRelationType(ProgressConstants
                        .RELATIONTYPE_ROLE);
            }

            if (!ids.hasRelationType(ProgressConstants.RELATIONTYPE_TRANSFER_TASK)) {
                RelationType relationType = ids.addRelationType(ProgressConstants
                        .RELATIONTYPE_TRANSFER_TASK);
            }
        } else {
            println("Failed to connect to database: " + DatabaseConstants.INFODISCOVER_SPACENAME);
        }

        ids.closeSpace();
    }



    private static void println(String msg) {
        System.out.println(msg);
    }
}
