package com.infoDiscover.solution.builder;

import com.infoDiscover.common.dimension.time.dimension.DayDimensionVO;
import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.Property;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.builder.vo.RelationMappingVO;
import com.infoDiscover.solution.common.dimension.DimensionManager;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.common.fact.FactManager;
import com.infoDiscover.solution.common.relationship.RelationshipManager;
import com.infoDiscover.solution.common.util.JsonNodeUtil;
import com.infoDiscover.solution.common.util.PrefixSetting;
import com.infoDiscover.solution.construction.supervision.constants.JsonConstants;
import com.infoDiscover.solution.construction.supervision.manager.DayDimensionManager;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by sun.
 */
public class ImportData {
    private final static Logger logger = LoggerFactory.getLogger(ImportData.class);

    private InfoDiscoverSpace ids;
    private String prefix;

    public ImportData(InfoDiscoverSpace ids, String prefix) {
        this.ids = ids;
        this.prefix = prefix;
    }

    public void importData(String dataJson, boolean overwrite) throws Exception {

        logger.info("Start to importData with data: {}", dataJson);

        JsonNode dataJsonNode = JsonNodeUtil.getDataNode(dataJson);
        if (dataJson != null) {
            for (JsonNode jsonNode : dataJsonNode) {
                // create or update fact
                Fact fact = createMeasurable(jsonNode, overwrite);

                // link relations
                if (fact != null) {
                    linkRelation(fact);
                }
            }
        }

        logger.info("Exit to importData()...");
    }

    public Fact createMeasurable(JsonNode jsonNode, boolean override) throws Exception {
        String type = jsonNode.get(SolutionConstants.JSON_TYPE).asText();

        if (SolutionConstants.FACT_TYPE.equalsIgnoreCase(type)) {
            return createNewOrUpdateFact(jsonNode, override);
        } else {
            throw new Exception("Wrong type, it should be: " + SolutionConstants.FACT_TYPE);
        }
    }

    private Fact createNewOrUpdateFact(JsonNode jsonNode, boolean overwrite) throws
            InfoDiscoveryEngineRuntimeException {
        logger.info("Start to createNewOrUpdateFact with jsonNode: {} and overwrite is: {}",
                jsonNode, overwrite);

        if (jsonNode == null) {
            logger.warn("Exit createNewOrUpdateFact() that jsonNode is null");
            return null;
        }

        Map<String, Object> uniqueKey = new HashMap<>();
        Map<String, Object> properties = new HashMap<>();

        JsonNode propertiesJsonNode = JsonNodeUtil.getPropertiesJsonNode(jsonNode);
        if (propertiesJsonNode == null) {
            logger.warn("Exit createNewOrUpdateFact() that propertiesJsonNode is null");
            return null;
        }

        // convert jsonNode to properties map
        convertJsonNodeToPropertiesMap(propertiesJsonNode, properties, uniqueKey);

        // create or update fact
        String typeName = getTypeName(jsonNode);
        FactManager manager = new FactManager(ids);
        Fact fact;

        if (overwrite && uniqueKey.size() > 0) {
            fact = getFactWithUniqueKeys(typeName, uniqueKey);
            if (fact != null) {
                return manager.updateFact(fact, properties);
            }
        }

        fact = manager.createFact(typeName, properties);

        logger.info("Exit createNewOrUpdateFact()...");

        return fact;
    }

    private void linkFactToDimension(SolutionTemplateParser parser, Fact fact) throws
            InfoDiscoveryEngineInfoExploreException, InfoDiscoveryEngineRuntimeException,
            InfoDiscoveryEngineDataMartException {

        Map<String, List<RelationMappingVO>> factToDimensionMap = parser.getFactToDimensionMap();
        List<RelationMappingVO> voList = parser.getRelationMappingVOList(PrefixSetting
                .removePrefix(prefix, fact.getType()), factToDimensionMap);

        if (voList != null) {
            for (RelationMappingVO vo : voList) {
                linkRelation(fact,vo, SolutionConstants.JSON_FACT_TO_DIMENSION_MAPPING);
            }
        }
    }

    private void linkFactToFact(SolutionTemplateParser parser, String rid, String factType) throws
            InfoDiscoveryEngineInfoExploreException, InfoDiscoveryEngineRuntimeException,
            InfoDiscoveryEngineDataMartException {

        Map<String, List<RelationMappingVO>> factToFactMap = parser.getFactToFactMap();
        List<RelationMappingVO> voList = new ArrayList<>();

        Set<String> keySet = factToFactMap.keySet();
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            voList.addAll(factToFactMap.get(it.next()));
        }

        if (voList != null) {

            // retrieve fact again as its version is updated
            Fact latestFact = new FactManager(ids).getFactByRID(rid, factType);

            for (RelationMappingVO vo : voList) {
                linkRelation(latestFact,vo, SolutionConstants.JSON_FACT_TO_FACT_MAPPING);
            }
        }
    }

    private void linkFactToDateDimension(SolutionTemplateParser parser, String rid, String factType) throws
            InfoDiscoveryEngineInfoExploreException, InfoDiscoveryEngineRuntimeException,
            InfoDiscoveryEngineDataMartException {
        Map<String, List<RelationMappingVO>> factToDateDimensionMap = parser
                .getFactToDateDimension();

        List<RelationMappingVO> voList = parser.getRelationMappingVOList(
                PrefixSetting.removePrefix(prefix, factType), factToDateDimensionMap);

        if (voList != null) {
            // retrieve fact again as its version is updated
            Fact latestFact = new FactManager(ids).getFactByRID(rid, factType);

            for (RelationMappingVO vo : voList) {
                linkRelation(latestFact,vo, SolutionConstants.JSON_FACT_TO_DATE_DIMENSION_MAPPING);
            }
        }
    }

    public void linkRelation(Fact fact) throws InfoDiscoveryEngineInfoExploreException,
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineDataMartException {
        logger.info("Enter addRelation with factType: {}", fact.getType());

        String rid = fact.getId();
        String factType = fact.getType();
        SolutionTemplateParser parser = new SolutionTemplateParser(ids, prefix);

        // fact to dimension mapping
        linkFactToDimension(parser,fact);

        // fact to fact mapping
        linkFactToFact(parser, rid, factType);

        // fact to date dimension
        linkFactToDateDimension(parser, rid, factType);

        logger.info("Exit addRelation()...");
    }

    private void linkRelation(Fact fact, RelationMappingVO vo, String
            mappingType) throws InfoDiscoveryEngineInfoExploreException,
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineDataMartException {

        String fromType = PrefixSetting.getFactTypeWithPrefix(prefix, vo.getFromType());
        String fromProperty = vo.getFromProperty();
        String toType = PrefixSetting.getFactTypeWithPrefix(prefix, vo.getToType());
        String toProperty = vo.getToProperty();
        String relationType = PrefixSetting.getFactTypeWithPrefix(prefix, vo
                .getRelationTypeName());
        String propertyType = vo.getPropertyType();

        Property property = fact.getProperty(fromProperty);
        if (property != null) {
            Object propertyValue = property.getPropertyValue();

            ExploreParameters ep = new ExploreParameters();
            ep.setType(toType);
            ep.setDefaultFilteringItem(new EqualFilteringItem(toProperty, propertyValue));

            if (mappingType.equalsIgnoreCase(SolutionConstants.JSON_FACT_TO_DIMENSION_MAPPING)) {

                Dimension dimension = QueryExecutor.executeDimensionQuery(ids
                        .getInformationExplorer(), ep);

                if (dimension == null) {
                    Map<String, Object> props = new HashMap<>();
                    props.put(toProperty, propertyValue);
                    dimension = new DimensionManager(ids).createDimension(toType, props);
                }

                if (dimension != null) {
                    RelationshipManager manager = new RelationshipManager(ids);
                    manager.linkFactToDimension(fact, dimension, relationType);
                }
            } else if (mappingType.equalsIgnoreCase(SolutionConstants.JSON_FACT_TO_FACT_MAPPING)) {
                // check which is the fromFact, which is the toFact
                if (fact.getType().equalsIgnoreCase(toType)) {
                    // the passing fact is a toFact, to get the fromFact
                    ep.setType(fromType);
                    ep.setDefaultFilteringItem(new EqualFilteringItem(fromProperty, propertyValue));
                }

                Fact targetFact = QueryExecutor.executeFactQuery(ids.getInformationExplorer(), ep);

                if (targetFact != null) {
                    RelationshipManager manager = new RelationshipManager(ids);
                    if (fact.getType().equalsIgnoreCase(toType)) {
                        manager.linkFactsByRelationType(targetFact, fact, relationType);
                    } else {
                        manager.linkFactsByRelationType(fact, targetFact, relationType);
                    }
                }

            } else if (mappingType.equalsIgnoreCase(SolutionConstants
                    .JSON_FACT_TO_DATE_DIMENSION_MAPPING)) {
                DayDimensionVO dayDimensionVO = DayDimensionManager.getDayDimensionVO
                        (prefix, (Date) propertyValue);
                RelationshipManager manager = new RelationshipManager(ids);
                manager.linkFactToDateDimension(prefix, fact, dayDimensionVO, relationType);
            }

        }
    }

    public Fact getFactWithUniqueKeys(String factType, Map<String, Object> uniqueKeys) {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(factType);

        Set<String> keySet = uniqueKeys.keySet();
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            String key = it.next();
            Object value = uniqueKeys.get(key);
            ep.addFilteringItem(new EqualFilteringItem(key, value), ExploreParameters
                    .FilteringLogic.AND);
        }

        return QueryExecutor.executeFactQuery(ids.getInformationExplorer(), ep);
    }

    public void convertJsonNodeToPropertiesMap(JsonNode propertiesJsonNode, Map<String, Object>
            properties, Map<String, Object> uniqueKey) {
        for (JsonNode propertyNode : propertiesJsonNode) {
            String propertyName = propertyNode.get(JsonConstants.JSON_PROPERTY_NAME).asText();
            String propertyType = propertyNode.get(JsonConstants.JSON_PROPERTY_TYPE).asText();

            Object propertyValue = JsonNodeUtil.getPropertyValue(propertyType, propertyNode);
            if (propertyValue != null) {
                properties.put(propertyName, propertyValue);
            }

            JsonNode key = propertyNode.get(SolutionConstants.JSON_IS_UNIQUE_KEY);
            if (key != null) {
                uniqueKey.put(propertyName, propertyValue);
            }
        }
    }

    private String getTypeName(JsonNode jsonNode) {
        return PrefixSetting.normalizePrefix(prefix) + jsonNode.get(SolutionConstants
                .JSON_TYPE_NAME).asText();
    }
}