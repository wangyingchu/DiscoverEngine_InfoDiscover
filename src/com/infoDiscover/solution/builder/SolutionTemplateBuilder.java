package com.infoDiscover.solution.builder;

import com.infoDiscover.common.util.Util;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.common.fact.FactManager;
import com.infoDiscover.solution.common.util.JsonNodeUtil;
import org.apache.commons.collections.map.HashedMap;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun.
 */
public class SolutionTemplateBuilder {

    private final static Logger logger = LoggerFactory.getLogger(SolutionTemplateBuilder.class);

    private InfoDiscoverSpace ids;
    private String factName;

    public SolutionTemplateBuilder(InfoDiscoverSpace ids, String factName) {
        this.ids = ids;
        this.factName = factName;
    }

    public Fact createNewOrUpdateTemplate(String prefix, String templateJson) throws Exception {
        logger.info("Start to createNewOrUpdateTemplate with prefix: {} and templateJson: {}",
                prefix, templateJson);

        if (prefix == null || prefix.trim().isEmpty()) {
            String error = "Prefix should not be null or empty";
            logger.error(error);
            throw new Exception(error);
        }

        if (templateJson == null || templateJson.trim().isEmpty()) {
            String error = "Solution Template is empty";
            logger.error(error);
            throw new Exception(error);
        }

        // parse solution template
        Map<String, Object> properties = getPropertiesMapFromJson(templateJson);
        properties.put(SolutionConstants.PROPERTY_STD_PREFIX, prefix.toUpperCase());

        FactManager factManager = new FactManager(ids);
        Fact solutionTemplateFact = getSolutionTemplateByPrefix(prefix.toUpperCase());
        Date now = new Date();
        if (solutionTemplateFact == null) {
            properties.put(SolutionConstants.PROPERTY_STD_ID, Util.generateUUID());
            properties.put(SolutionConstants.PROPERTY_STD_CREATED_AT, now);
            properties.put(SolutionConstants.PROPERTY_STD_MODIFIED_AT, now);
            solutionTemplateFact = factManager.createFact(this.factName, properties);
        } else {
            properties.put(SolutionConstants.PROPERTY_STD_MODIFIED_AT, now);
            solutionTemplateFact = factManager.updateFact(solutionTemplateFact, properties);
        }

        logger.info("End method createNewOrUpdateTemplate()...");
        return solutionTemplateFact;
    }


    private Map<String, Object> getPropertiesMapFromJson(String templateJson) throws Exception {
        JsonNode dataNode = JsonNodeUtil.getDataNode(templateJson);

        JsonNode factsNode = dataNode.get(SolutionConstants.JSON_FACTS);
        JsonNode dimensionsNode = dataNode.get(SolutionConstants.JSON_DIMENSIONS);
        JsonNode relationsNode = dataNode.get(SolutionConstants.JSON_RELATIONS);

        logger.debug("Facts node: {}", factsNode);
        logger.debug("Dimensions node: {}", dimensionsNode);
        logger.debug("Relations node: {}", relationsNode);

        Map<String, Object> properties = new HashedMap();
        if (factsNode != null) {
            properties.put(SolutionConstants.PROPERTY_STD_FACT_DEFINITION,
                    appendJsonPrefix(SolutionConstants.JSON_FACTS, factsNode));
        }
        if (dimensionsNode != null) {
            properties.put(SolutionConstants.PROPERTY_STD_DIMENSION_DEFINITION,
                    appendJsonPrefix(SolutionConstants.JSON_DIMENSIONS, dimensionsNode));
        }
        if (relationsNode != null) {
            properties.put(SolutionConstants.PROPERTY_STD_RELATION_DEFINITION,
                    appendJsonPrefix(SolutionConstants.JSON_RELATIONS, relationsNode));
        }

        return properties;
    }

    public Fact getSolutionTemplateByPrefix(String prefix) {
        logger.info("getSolutionTemplateByPrefix() with prefix: {}", prefix);
        ExploreParameters ep = new ExploreParameters();
        ep.setType(this.factName);
        ep.setDefaultFilteringItem(new EqualFilteringItem(SolutionConstants.PROPERTY_STD_PREFIX,
                prefix.toUpperCase()));

        return QueryExecutor.executeFactQuery(this.ids.getInformationExplorer(), ep);
    }

    public Fact getSolutionTemplateById(String templateId) {
        logger.info("getSolutionTemplateById() with templateId: {}", templateId);
        ExploreParameters ep = new ExploreParameters();
        ep.setType(this.factName);
        ep.setDefaultFilteringItem(new EqualFilteringItem(SolutionConstants.PROPERTY_STD_ID,
                templateId));

        return QueryExecutor.executeFactQuery(this.ids.getInformationExplorer(), ep);
    }

    public boolean checkPrefixIsExisted(String prefix) {
        Fact fact = getSolutionTemplateByPrefix(prefix);
        return fact != null;
    }

    public void updatePrefix(String templateId, String prefix) throws Exception {
        logger.info("Enter updatePrefix(0 with templateId: {} and new prefix: {}", templateId,
                prefix);

        Fact templateFact = getSolutionTemplateById(templateId);
        if (templateFact == null) {
            String error = "Invalid templateId: " + templateId;
            logger.error(error);
            throw new Exception(error);
        }

        String existingPrefix = templateFact.getProperty(SolutionConstants.PROPERTY_STD_PREFIX)
                .toString();
        if (prefix.equalsIgnoreCase(existingPrefix)) {
            String error = prefix + " is already existed, please choose a new one";
            logger.error(error);
            throw new Exception(error);
        }

        updateSolutionTemplate(templateFact, SolutionConstants.PROPERTY_STD_PREFIX, prefix.toUpperCase());

    }

    public void updateFactDefinition(String templateId, String factDefinitionJson)
            throws Exception {
        updateSolutionTemplate(templateId, SolutionConstants.PROPERTY_STD_FACT_DEFINITION,
                factDefinitionJson);
    }

    public void updateDimensionDefinition(String templateId, String dimensionDefinitionJson)
            throws Exception {
        updateSolutionTemplate(templateId, SolutionConstants.PROPERTY_STD_DIMENSION_DEFINITION,
                dimensionDefinitionJson);
    }

    public void updateRelationDefinition(String templateId, String relationDefinitionJson)
            throws Exception {
        updateSolutionTemplate(templateId, SolutionConstants.PROPERTY_STD_RELATION_DEFINITION,
                relationDefinitionJson);
    }

    public boolean deleteSolutionTemplateById(String templateId) {
        logger.info("deleteSolutionTemplateById() with templateId: {}", templateId);
        Fact templateFact = getSolutionTemplateById(templateId);
        if(templateFact!= null) {
            try {
                return ids.removeFact(templateFact.getId());
            } catch (InfoDiscoveryEngineRuntimeException e) {
                logger.error("Failed to delete solution template");
            }
        }

        return false;
    }

    private void updateSolutionTemplate(String templateId, String key, String value)
            throws Exception {

        Fact templateFact = getSolutionTemplateById(templateId);
        if (templateFact == null) {
            String error = "Invalid templateId: " + templateId;
            logger.error(error);
            throw new Exception(error);
        }

        updateSolutionTemplate(templateFact, key, value);
    }

    private void updateSolutionTemplate(Fact templateFact, String key, String value)
            throws Exception {

        Map<String, Object> properties = new HashMap<>();
        properties.put(key, value);

        FactManager factManager = new FactManager(ids);
        factManager.updateFact(templateFact, properties);
    }

    private String appendJsonPrefix(String prefix, JsonNode node) {
        return "{\"" + prefix + "\": " + node.toString() + "}";
    }
}
