package com.infoDiscover.solution.common.fact;

import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.FactType;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relationable;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.builder.SolutionConstants;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.common.util.PrefixSetting;
import com.infoDiscover.solution.common.util.PropertyTypeUtil;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
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

            FactType factType = ids.addFactType(factTypeName);

            if (factTypeProperties != null) {
                for (String[] property : factTypeProperties) {
                    String propertyName = property[0];
                    String propertyType = property[1];
                    factType.addTypeProperty(
                            propertyName,
                            PropertyTypeUtil.getPropertyType(propertyType));
                }
            }

            logger.debug("End to create fact type: " + factType.getTypeName());
        }
    }


    public void createFactType(String factTypeName, JsonNode propertiesNode) throws
            InfoDiscoveryEngineDataMartException, InfoDiscoveryEngineRuntimeException {
        logger.info("Start to createFactType with factTypeName: {} and propertiesNode: {}",
                factTypeName, propertiesNode);

        if (!ids.hasFactType(factTypeName)) {
            FactType factType = ids.addFactType(factTypeName);
            if (propertiesNode != null) {
                for (JsonNode property : propertiesNode) {
                    String propertyName = property.get(SolutionConstants.JSON_PROPERTY_NAME)
                            .asText();
                    String propertyType = property.get(SolutionConstants.JSON_PROPERTY_TYPE)
                            .asText();
                    factType.addTypeProperty(propertyName, PropertyTypeUtil.getPropertyType
                            (propertyType));
                }
            }
        }

        logger.info("End to createFactType...");
    }

    public Fact getFactByRID(String rid, String factType) {
        String sql = "select from " + PrefixSetting.getFactTypeWithPrefix
                (InfoDiscoverEngineConstant.CLASSPERFIX_FACT , factType) + " WHERE @rid = " + rid;

        List<Relationable> list = QueryExecutor.executeFactQuery(ids, sql);
        if (list!= null && list.size() > 0) {
            return (Fact) list.get(0);
        }

        return null;
    }
}
