package com.infoDiscover.solution.common.fact;

import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.FactType;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.common.util.PropertyTypeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by sun.
 */
public class FactManager {

    private final static Logger logger = LoggerFactory.getLogger(FactManager.class);

    private InfoDiscoverSpace ids;

    public FactManager(InfoDiscoverSpace ids) {
        this.ids = ids;
    }

    public Fact createFact(String type, Map<String, Object> properties) {
        logger.debug("Enter method createFact() with type: {} and properties: {}", type,
                properties);
        try {
            Fact fact = DiscoverEngineComponentFactory.createFact(type);
            fact = ids.addFact(fact);
            fact.addProperties(properties);
            return fact;
        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        }

        logger.debug("Exit createFact()...");
        return null;
    }

    public Fact updateFact(Fact fact, Map<String, Object> properties) throws
            InfoDiscoveryEngineRuntimeException {
        logger.debug("Enter method updateFact() with factId: " + fact.getId() + " and properties:" +
                " " +
                properties);
        fact.addNewOrUpdateProperties(properties);
        logger.debug("End method updateFact()...");

        return fact;
    }

    public void createFactType(String factTypeName, String[][] factTypeProperties)
            throws InfoDiscoveryEngineDataMartException, InfoDiscoveryEngineRuntimeException {

        logger.info("Start to create fact type: {} with properties: {}",
                factTypeName, factTypeProperties);

        if (!ids.hasFactType(factTypeName)) {

            FactType solutionTemplate = ids.addFactType(factTypeName);

            if (factTypeProperties != null) {
                for (String[] property : factTypeProperties) {
                    String propertyName = property[0];
                    String propertyType = property[1];
                    solutionTemplate.addTypeProperty(
                            propertyName,
                            PropertyTypeUtil.getPropertyType(propertyType));
                }
            }

            logger.debug("End to create fact type: " + solutionTemplate.getTypeName());
        }
    }

}
