package com.businessExtension.constructionSupervision.sample;

import com.infoDiscover.infoDiscoverEngine.dataMart.FactType;
import com.infoDiscover.infoDiscoverEngine.dataMart.PropertyType;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.businessExtension.constructionSupervision.constants.DatabaseConstants;
import com.businessExtension.constructionSupervision.constants.JsonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sun.
 */
public class SampleFactGenerator {
    private final static Logger logger = LoggerFactory.getLogger(SampleFactGenerator.class);

    private InfoDiscoverSpace ids;

    public SampleFactGenerator(InfoDiscoverSpace ids) {
        this.ids = ids;
    }

    public void createFactType() throws InfoDiscoveryEngineDataMartException,
            InfoDiscoveryEngineRuntimeException {

        // maintenance project
        if (!ids.hasFactType(DatabaseConstants.FACTTYPE_MAINTENANCE_PROJECT)) {
            FactType maintenanceProjectFactType = ids.addFactType(DatabaseConstants.FACTTYPE_MAINTENANCE_PROJECT);
            maintenanceProjectFactType.addTypeProperty(JsonConstants.JSON_PROJECT_ID, PropertyType
                    .STRING);
            maintenanceProjectFactType.addTypeProperty(JsonConstants.JSON_PROJECT_NAME, PropertyType
                    .STRING);
            maintenanceProjectFactType.addTypeProperty(JsonConstants.JSON_PROJECT_STARTER_ID,
                    PropertyType.STRING);
            maintenanceProjectFactType.addTypeProperty(JsonConstants.JSON_PROJECT_STARTER, PropertyType
                    .STRING);
            maintenanceProjectFactType.addTypeProperty(JsonConstants.JSON_START_DATE, PropertyType.DATE);
            maintenanceProjectFactType.addTypeProperty(JsonConstants.JSON_END_DATE, PropertyType.DATE);
            maintenanceProjectFactType.addTypeProperty(JsonConstants.JSON_STATUS, PropertyType.STRING);
            logger.debug("Created maintenance project fact type: " + maintenanceProjectFactType
                    .getTypeName());
        }

        // construction project
        if (!ids.hasFactType(DatabaseConstants.FACTTYPE_CONSTRUCTION_PROJECT)) {
            FactType constructionProjectFactType = ids.addFactType(DatabaseConstants.FACTTYPE_CONSTRUCTION_PROJECT);
            constructionProjectFactType.addTypeProperty(JsonConstants.JSON_PROJECT_ID,
                    PropertyType.STRING);
            constructionProjectFactType.addTypeProperty(JsonConstants.JSON_PROJECT_NAME,
                    PropertyType.STRING);
            constructionProjectFactType.addTypeProperty(JsonConstants.JSON_PROJECT_STARTER_ID,
                    PropertyType.STRING);
            constructionProjectFactType.addTypeProperty(JsonConstants.JSON_PROJECT_STARTER,
                    PropertyType.STRING);
            constructionProjectFactType.addTypeProperty(JsonConstants.JSON_START_DATE, PropertyType.DATE);
            constructionProjectFactType.addTypeProperty(JsonConstants.JSON_END_DATE, PropertyType.DATE);
            constructionProjectFactType.addTypeProperty(JsonConstants.JSON_STATUS, PropertyType.STRING);
            logger.debug("Created construction project fact type: " + constructionProjectFactType
                    .getTypeName());
        }

        // progress for maintenance project
//        if (!ids.hasFactType(SampleDataSet.FACTTYPE_MAINTENANCE_PROJECT)) {
//            FactType maintainProgressFactType = ids.addFactType(SampleDataSet
//                    .FACTTYPE_MAINTENANCE_PROJECT);
//            maintainProgressFactType.addTypeProperty(JsonConstants.JSON_PROJECT_ID, PropertyType
//                    .STRING);
//            maintainProgressFactType.addTypeProperty(JsonConstants.JSON_PROJECT_NAME, PropertyType
//                    .STRING);
//            maintainProgressFactType.addTypeProperty(JsonConstants.JSON_PROJECT_STARTER, PropertyType
//                    .STRING);
//            maintainProgressFactType.addTypeProperty(JsonConstants.JSON_START_DATE, PropertyType.DATE);
//            maintainProgressFactType.addTypeProperty(JsonConstants.JSON_END_DATE, PropertyType.DATE);
//            maintainProgressFactType.addTypeProperty(JsonConstants.JSON_STATUS, PropertyType.STRING);
//            logger.debug("Created maintenance progress fact type: " + maintainProgressFactType
//                    .getTypeName());
//        }
//
//        // progress for new project
//        if (!ids.hasFactType(SampleDataSet.FACTTYPE_NEW_PROJECT)) {
//            FactType newProgressFactType = ids.addFactType(SampleDataSet
//                    .FACTTYPE_NEW_PROJECT);
//            newProgressFactType.addTypeProperty(JsonConstants.JSON_PROJECT_ID, PropertyType.STRING);
//            newProgressFactType.addTypeProperty(JsonConstants.JSON_PROJECT_NAME, PropertyType.STRING);
//            newProgressFactType.addTypeProperty(JsonConstants.JSON_PROJECT_STARTER, PropertyType
//                    .STRING);
//            newProgressFactType.addTypeProperty(JsonConstants.JSON_START_DATE, PropertyType.DATE);
//            newProgressFactType.addTypeProperty(JsonConstants.JSON_END_DATE, PropertyType.DATE);
//            newProgressFactType.addTypeProperty(JsonConstants.JSON_STATUS, PropertyType.STRING);
//            logger.debug("Created new progress fact type: " + newProgressFactType.getTypeName());
//        }
//
//        // progress for new project
//        if (!ids.hasFactType(SampleDataSet.FACTTYPE_EXTENSION_PROJECT)) {
//            FactType newProgressFactType = ids.addFactType(SampleDataSet
//                    .FACTTYPE_EXTENSION_PROJECT);
//            newProgressFactType.addTypeProperty(JsonConstants.JSON_PROJECT_ID, PropertyType.STRING);
//            newProgressFactType.addTypeProperty(JsonConstants.JSON_PROJECT_NAME, PropertyType.STRING);
//            newProgressFactType.addTypeProperty(JsonConstants.JSON_PROJECT_STARTER, PropertyType
//                    .STRING);
//            newProgressFactType.addTypeProperty(JsonConstants.JSON_START_DATE, PropertyType.DATE);
//            newProgressFactType.addTypeProperty(JsonConstants.JSON_END_DATE, PropertyType.DATE);
//            newProgressFactType.addTypeProperty(JsonConstants.JSON_STATUS, PropertyType.STRING);
//            logger.debug("Created new progress fact type: " + newProgressFactType.getTypeName());
//        }
//
//        // progress for new project
//        if (!ids.hasFactType(SampleDataSet.FACTTYPE_REBUILD_PROJECT)) {
//            FactType newProgressFactType = ids.addFactType(SampleDataSet
//                    .FACTTYPE_REBUILD_PROJECT);
//            newProgressFactType.addTypeProperty(JsonConstants.JSON_PROJECT_ID, PropertyType.STRING);
//            newProgressFactType.addTypeProperty(JsonConstants.JSON_PROJECT_NAME, PropertyType.STRING);
//            newProgressFactType.addTypeProperty(JsonConstants.JSON_PROJECT_STARTER, PropertyType
//                    .STRING);
//            newProgressFactType.addTypeProperty(JsonConstants.JSON_START_DATE, PropertyType.DATE);
//            newProgressFactType.addTypeProperty(JsonConstants.JSON_END_DATE, PropertyType.DATE);
//            newProgressFactType.addTypeProperty(JsonConstants.JSON_STATUS, PropertyType.STRING);
//            logger.debug("Created new progress fact type: " + newProgressFactType.getTypeName());
//        }

        // task fact type
        if (!ids.hasFactType(DatabaseConstants.FACT_TASK_WITH_PREFIX)) {
            FactType taskFactType = ids.addFactType(DatabaseConstants
                    .FACT_TASK_WITH_PREFIX);
            taskFactType.addTypeProperty(JsonConstants.JSON_PROJECT_ID, PropertyType.STRING);
            taskFactType.addTypeProperty(JsonConstants.JSON_TASK_ID, PropertyType.STRING);
            taskFactType.addTypeProperty(JsonConstants.JSON_TASK_NAME, PropertyType.STRING);
            taskFactType.addTypeProperty(JsonConstants.JSON_TASK_DISPLAY_NAME, PropertyType.STRING);
            taskFactType.addTypeProperty(JsonConstants.JSON_WORKER_ID, PropertyType.STRING);
            taskFactType.addTypeProperty(JsonConstants.JSON_WORKER, PropertyType.STRING);
            taskFactType.addTypeProperty(JsonConstants.JSON_EXECUTIVE_DEPARTMENT_ID, PropertyType
                    .STRING);
            taskFactType.addTypeProperty(JsonConstants.JSON_EXECUTIVE_DEPARTMENT, PropertyType.STRING);
            taskFactType.addTypeProperty(JsonConstants.JSON_START_DATE, PropertyType.DATE);
            taskFactType.addTypeProperty(JsonConstants.JSON_END_DATE, PropertyType.DATE);
            //taskFactType.addTypeProperty(JsonConstants.ATTACHMENT, PropertyType.STRING);

            logger.debug("Created task fact type: " + taskFactType.getTypeName());
        }

    }

}
