package com.infoDiscover.solution.construction.supervision.sample;

import com.infoDiscover.infoDiscoverEngine.dataMart.FactType;
import com.infoDiscover.infoDiscoverEngine.dataMart.PropertyType;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.construction.supervision.database.SupervisionSolutionConstants;
import com.infoDiscover.solution.sample.util.JsonConstants;
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

        // progress for maintenance project
        if (!ids.hasFactType(SampleDataSet.FACTTYPE_MAINTENANCE_PROJECT)) {
            FactType maintainProgressFactType = ids.addFactType(SampleDataSet
                    .FACTTYPE_MAINTENANCE_PROJECT);
            maintainProgressFactType.addTypeProperty(JsonConstants.PROGRESS_ID, PropertyType
                    .STRING);
            maintainProgressFactType.addTypeProperty(JsonConstants.PROGRESS_NAME, PropertyType
                    .STRING);
            maintainProgressFactType.addTypeProperty(JsonConstants.PROGRESS_STARTER, PropertyType
                    .STRING);
            maintainProgressFactType.addTypeProperty(JsonConstants.START_DATE, PropertyType.DATE);
            maintainProgressFactType.addTypeProperty(JsonConstants.END_DATE, PropertyType.DATE);
            maintainProgressFactType.addTypeProperty(JsonConstants.STATUS, PropertyType.STRING);
            logger.debug("Created maintenance progress fact type: " + maintainProgressFactType
                    .getTypeName());
        }

        // progress for new project
        if (!ids.hasFactType(SampleDataSet.FACTTYPE_NEW_PROJECT)) {
            FactType newProgressFactType = ids.addFactType(SampleDataSet
                    .FACTTYPE_NEW_PROJECT);
            newProgressFactType.addTypeProperty(JsonConstants.PROGRESS_ID, PropertyType.STRING);
            newProgressFactType.addTypeProperty(JsonConstants.PROGRESS_NAME, PropertyType.STRING);
            newProgressFactType.addTypeProperty(JsonConstants.PROGRESS_STARTER, PropertyType
                    .STRING);
            newProgressFactType.addTypeProperty(JsonConstants.START_DATE, PropertyType.DATE);
            newProgressFactType.addTypeProperty(JsonConstants.END_DATE, PropertyType.DATE);
            newProgressFactType.addTypeProperty(JsonConstants.STATUS, PropertyType.STRING);
            logger.debug("Created new progress fact type: " + newProgressFactType.getTypeName());
        }

        // progress for new project
        if (!ids.hasFactType(SampleDataSet.FACTTYPE_EXTENSION_PROJECT)) {
            FactType newProgressFactType = ids.addFactType(SampleDataSet
                    .FACTTYPE_EXTENSION_PROJECT);
            newProgressFactType.addTypeProperty(JsonConstants.PROGRESS_ID, PropertyType.STRING);
            newProgressFactType.addTypeProperty(JsonConstants.PROGRESS_NAME, PropertyType.STRING);
            newProgressFactType.addTypeProperty(JsonConstants.PROGRESS_STARTER, PropertyType
                    .STRING);
            newProgressFactType.addTypeProperty(JsonConstants.START_DATE, PropertyType.DATE);
            newProgressFactType.addTypeProperty(JsonConstants.END_DATE, PropertyType.DATE);
            newProgressFactType.addTypeProperty(JsonConstants.STATUS, PropertyType.STRING);
            logger.debug("Created new progress fact type: " + newProgressFactType.getTypeName());
        }

        // progress for new project
        if (!ids.hasFactType(SampleDataSet.FACTTYPE_REBUILD_PROJECT)) {
            FactType newProgressFactType = ids.addFactType(SampleDataSet
                    .FACTTYPE_REBUILD_PROJECT);
            newProgressFactType.addTypeProperty(JsonConstants.PROGRESS_ID, PropertyType.STRING);
            newProgressFactType.addTypeProperty(JsonConstants.PROGRESS_NAME, PropertyType.STRING);
            newProgressFactType.addTypeProperty(JsonConstants.PROGRESS_STARTER, PropertyType
                    .STRING);
            newProgressFactType.addTypeProperty(JsonConstants.START_DATE, PropertyType.DATE);
            newProgressFactType.addTypeProperty(JsonConstants.END_DATE, PropertyType.DATE);
            newProgressFactType.addTypeProperty(JsonConstants.STATUS, PropertyType.STRING);
            logger.debug("Created new progress fact type: " + newProgressFactType.getTypeName());
        }

        // task fact type
        if (!ids.hasFactType(SupervisionSolutionConstants.FACT_TASK_WITH_PREFIX)) {
            FactType taskFactType = ids.addFactType(SupervisionSolutionConstants
                    .FACT_TASK_WITH_PREFIX);
            taskFactType.addTypeProperty(JsonConstants.PROGRESS_ID, PropertyType.STRING);
            taskFactType.addTypeProperty(JsonConstants.TASK_ID, PropertyType.STRING);
            taskFactType.addTypeProperty(JsonConstants.TASK_NAME, PropertyType.STRING);
            taskFactType.addTypeProperty(JsonConstants.WORKER, PropertyType.STRING);
            taskFactType.addTypeProperty(JsonConstants.EXECUTIVEDEPARTMENT, PropertyType.STRING);
            taskFactType.addTypeProperty(JsonConstants.START_DATE, PropertyType.DATE);
            taskFactType.addTypeProperty(JsonConstants.END_DATE, PropertyType.DATE);
            taskFactType.addTypeProperty(JsonConstants.ATTACHMENT, PropertyType.STRING);

            logger.debug("Created task fact type: " + taskFactType.getTypeName());
        }

    }
}
