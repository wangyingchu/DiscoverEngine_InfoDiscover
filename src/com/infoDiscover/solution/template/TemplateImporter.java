package com.infoDiscover.solution.template;

import com.infoDiscover.common.util.CompressionUtil;
import com.infoDiscover.common.util.JsonObjectUtil;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.builder.SolutionConstants;
import com.infoDiscover.solution.common.database.DatabaseConnection;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.common.util.JsonNodeUtil;
import com.infoDiscover.solution.template.exceptions.DuplicateSolutionException;
import com.infoDiscover.solution.template.exceptions.InvalidSolutionDefinitionFileException;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by sun on 7/17/17.
 */
public class TemplateImporter {
    public static final Logger logger = LoggerFactory.getLogger(TemplateImporter.class);

    private String spaceName;

    public TemplateImporter(String spaceName) {
        this.spaceName = spaceName;
    }

    public void importSolution(String zippedFile, boolean overwrite) throws
            Exception {
        logger.info("Start to importSolution to space: {} with solutionName: {} and " +
                "zippedFile: {} and overwrite is: {}", spaceName, zippedFile, overwrite);

        Map<String, String> files = CompressionUtil.unzip(zippedFile);

        String solutionDefinitions = files.get(SolutionConstants.SOLUTION_TEMPLATE_SOLUTION_DEFINITION);
        logger.debug("solutionDefinitions: {}", solutionDefinitions);

        if (StringUtils.isEmpty(solutionDefinitions)) {
            throw new InvalidSolutionDefinitionFileException();
        }

        InfoDiscoverSpace ids = DatabaseConnection.connectToSpace(spaceName);

        if (checkSolutionExisted(ids, solutionDefinitions)) {
            ids.closeSpace();
            throw new DuplicateSolutionException();
        }

        insertNewFact(ids,SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDefinitionFactType, solutionDefinitions);
        
        
        // import solution templates
        String solutionTypePropertyTypeDefinitions = files.get(SolutionConstants.SOLUTION_TEMPLATE_SOLUTION_TYPE_PROPERTY_TYPE_FILE_NAME);
        logger.debug("solutionTypePropertyTypeDefinitions: {}", solutionTypePropertyTypeDefinitions);
        insertNewFact(ids,SolutionTemplateConstants.BUSINESSSOLUTION_SolutionTypePropertyFactType, solutionTypePropertyTypeDefinitions);

        String factTypeDefinitions = files.get(SolutionConstants.SOLUTION_TEMPLATE_FACT_TYPE_FILE_NAME);
        logger.debug("factTypeDefinitions: {}", factTypeDefinitions);
        insertNewFact(ids,SolutionTemplateConstants.BUSINESSSOLUTION_SolutionFactTypeFactType, factTypeDefinitions);

        String dimensionTypeDefinitions = files.get(SolutionConstants.SOLUTION_TEMPLATE_DIMENSION_TYPE_FILE_NAME);
        logger.debug("dimensionTypeDefinitions: {}", dimensionTypeDefinitions);
        insertNewFact(ids,SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDimensionTypeFactType, dimensionTypeDefinitions);

        String relationTypeDefinitions = files.get(SolutionConstants.SOLUTION_TEMPLATE_RELATION_TYPE_FILE_NAME);
        logger.debug("relationTypeDefinitions: {}", relationTypeDefinitions);
        insertNewFact(ids,SolutionTemplateConstants.BUSINESSSOLUTION_SolutionRelationTypeFactType, relationTypeDefinitions);

        String factToFactMappings = files.get(SolutionConstants.SOLUTION_TEMPLATE_FACT_TO_FACT_FILE_NAME);
        logger.debug("factToFactMappings: {}", factToFactMappings);
        insertNewFact(ids,SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDataRelationMappingDefinitionFactType, factToFactMappings);

        String factToDimensionMappings = files.get(SolutionConstants.SOLUTION_TEMPLATE_FACT_TO_DIMENSION_FILE_NAME);
        logger.debug("factToDimensionMappings: {}", factToDimensionMappings);
        insertNewFact(ids,SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDataRelationMappingDefinitionFactType, factToDimensionMappings);

        String dimensionToFactMappings = files.get(SolutionConstants.SOLUTION_TEMPLATE_DIMENSION_TO_FACT_FILE_NAME);
        logger.debug("dimensionToFactMappings: {}", dimensionToFactMappings);
        insertNewFact(ids,SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDataRelationMappingDefinitionFactType, dimensionToFactMappings);

        String dimensionToDimensionMappings = files.get(SolutionConstants.SOLUTION_TEMPLATE_DIMENSION_TO_DIMENSION_FILE_NAME);
        logger.debug("dimensionToDimensionMappings: {}", dimensionToDimensionMappings);
        insertNewFact(ids,SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDataRelationMappingDefinitionFactType, dimensionToDimensionMappings);

        String factToDateMappings = files.get(SolutionConstants.SOLUTION_TEMPLATE_FACT_TO_DATE_FILE_NAME);
        logger.debug("factToDateMappings: {}", factToDateMappings);
        insertNewFact(ids,SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDataDateDimensionMappingDefinitionFactType, factToDateMappings);

        String dimensionToDateMappings = files.get(SolutionConstants.SOLUTION_TEMPLATE_DIMENSION_TO_DATE_FILE_NAME);
        logger.debug("dimensionToDateMappings: {}", dimensionToDateMappings);
        insertNewFact(ids,SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDataDateDimensionMappingDefinitionFactType, dimensionToDateMappings);

        String duplicateFactPropertiesCopyMapping = files.get(SolutionConstants.SOLUTION_TEMPLATE_FACT_DUPLICATE_COPY_FILE_NAME);
        logger.debug("duplicateFactPropertiesCopyMapping: {}", duplicateFactPropertiesCopyMapping);
        insertNewFact(ids,SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDataPropertiesDuplicateMappingDefinitionFactType, duplicateFactPropertiesCopyMapping);

        String duplicateDimensionPropertiesCopyMapping = files.get(SolutionConstants.SOLUTION_TEMPLATE_DIMENSION_DUPLICATE_COPY_FILE_NAME);
        logger.debug("duplicateDimensionPropertiesCopyMapping: {}", duplicateDimensionPropertiesCopyMapping);
        insertNewFact(ids,SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDataPropertiesDuplicateMappingDefinitionFactType, duplicateDimensionPropertiesCopyMapping);

        String customPropertyAliasType = files.get(SolutionConstants.SOLUTION_TEMPLATE_CUSTOM_PROPERTY_ALIAS_TYPE_FILE_NAME);
        logger.debug("customPropertyAliasType: {}", customPropertyAliasType);
        insertNewFact(ids,SolutionTemplateConstants.BUSINESSSOLUTION_SolutionCustomPropertyAliasFactType, customPropertyAliasType);

        ids.closeSpace();

        logger.info("Exit importSolution()...");
    }

    private boolean checkSolutionExisted(InfoDiscoverSpace ids, String solutionDefinitionJson) throws Exception {

        String solutionName = getSolutionName(solutionDefinitionJson);

        if ( solutionName == null) {
            throw new InvalidSolutionDefinitionFileException();
        }

        ExploreParameters ep = new ExploreParameters();
        ep.setType(SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDefinitionFactType);
        ep.setDefaultFilteringItem(new EqualFilteringItem("solutionName", solutionName));

        Fact solution = QueryExecutor.executeFactQuery(ids.getInformationExplorer(),ep);

        return solution != null ? true : false;
    }

    private String getSolutionName(String solutionDefinitionJson) {
        JsonNode jsonNode = JsonNodeUtil.getDataNode(solutionDefinitionJson).get(0);
        JsonNode solutionNameJsonNode = jsonNode.get("solutionName");
        return solutionNameJsonNode == null ? null : solutionNameJsonNode.asText();
    }

    private void insertNewFact(InfoDiscoverSpace ids, String factType, String json) throws InfoDiscoveryEngineDataMartException, InfoDiscoveryEngineRuntimeException {
        JsonNode dataNode = JsonNodeUtil.getDataNode(json);

        if (dataNode == null) {
            return;
        }

        if (!ids.hasFactType(factType)) {
            ids.addFactType(factType);
        }

        for(JsonNode jsonNode: dataNode) {
            Map<String, Object> properties = JsonObjectUtil.jsonToMap(jsonNode.toString());

            Fact fact = DiscoverEngineComponentFactory.createFact(factType);
            Set<String> keySet = properties.keySet();
            Iterator<String> it = keySet.iterator();
            while (it.hasNext()) {
                String key = it.next();
                Object value = properties.get(key);
                if (ArrayUtils.contains(booleanProperties, key)) {
                    fact.setInitProperty(key,(Boolean) value);
                } else {
                    fact.setInitProperty(key, value.toString());
                }
            }
            ids.addFact(fact);
        }
    }

    private String[] booleanProperties = {"isMandatory", "isNullable", "isReadOnly"};
}
