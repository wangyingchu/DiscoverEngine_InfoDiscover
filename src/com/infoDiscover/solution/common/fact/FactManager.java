package com.infoDiscover.solution.common.fact;

import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
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
}
