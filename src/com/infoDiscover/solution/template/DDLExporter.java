package com.infoDiscover.solution.template;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.infoDiscover.common.util.JsonObjectUtil;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.solution.common.database.DatabaseConnection;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.construction.supervision.constants.JsonConstants;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by sun on 7/17/17.
 */
public class DDLExporter {

    public static final Logger logger = LoggerFactory.getLogger(DDLExporter.class);

    public static final String SOLUTION_NAME = "solutionName";

    private String solutionName;
    private String spaceName;

    public DDLExporter(String solutionName) {
        this.solutionName = solutionName;
    }

    public DDLExporter(String spaceName, String solutionName) {
        this.spaceName = spaceName;
        this.solutionName = solutionName;
    }

    public String generateSolutionDefinitionDDL() {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDefinitionFactType);
        ep.setDefaultFilteringItem(new EqualFilteringItem(this.SOLUTION_NAME, solutionName));

        return generateDDL(spaceName, ep);
    }

    public String generateFactTypeDefinitionDDL() {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SolutionTemplateConstants.BUSINESSSOLUTION_SolutionFactTypeFactType);
        ep.setDefaultFilteringItem(new EqualFilteringItem(this.SOLUTION_NAME, solutionName));

        return generateDDL(spaceName, ep);
    }

    public String generateDimensionTypeDefinitionDDL() {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDimensionTypeFactType);
        ep.setDefaultFilteringItem(new EqualFilteringItem(this.SOLUTION_NAME, solutionName));

        return generateDDL(spaceName, ep);
    }

    public String generateRelationTypeDefinitionDDL() {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SolutionTemplateConstants.BUSINESSSOLUTION_SolutionRelationTypeFactType);
        ep.setDefaultFilteringItem(new EqualFilteringItem(this.SOLUTION_NAME, solutionName));

        return generateDDL(spaceName, ep);
    }

    public String generateFactToFactDefinitionDDL() {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDataRelationMappingDefinitionFactType);
        ep.setDefaultFilteringItem(new EqualFilteringItem(this.SOLUTION_NAME, solutionName));
        ep.addFilteringItem(new EqualFilteringItem("sourceDataTypeKind", "FACT"), ExploreParameters.FilteringLogic.AND);
        ep.addFilteringItem(new EqualFilteringItem("targetDataTypeKind", "FACT"), ExploreParameters.FilteringLogic.AND);

        return generateDDL(spaceName, ep);
    }


    public String generateSolutionTypePropertyTypeDDL() {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SolutionTemplateConstants.BUSINESSSOLUTION_SolutionTypePropertyFactType);
        ep.setDefaultFilteringItem(new EqualFilteringItem(this.SOLUTION_NAME, solutionName));

        return generateDDL(spaceName, ep);
    }

    public String generateFactToDimensionDefinitionDDL() {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDataRelationMappingDefinitionFactType);
        ep.setDefaultFilteringItem(new EqualFilteringItem(this.SOLUTION_NAME, solutionName));
        ep.addFilteringItem(new EqualFilteringItem("sourceDataTypeKind", "FACT"), ExploreParameters.FilteringLogic.AND);
        ep.addFilteringItem(new EqualFilteringItem("targetDataTypeKind", "DIMENSION"), ExploreParameters.FilteringLogic.AND);

        return generateDDL(spaceName, ep);
    }

    public String generateDimensionToFactDefinitionDDL() {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDataRelationMappingDefinitionFactType);
        ep.setDefaultFilteringItem(new EqualFilteringItem(this.SOLUTION_NAME, solutionName));
        ep.addFilteringItem(new EqualFilteringItem("sourceDataTypeKind", "DIMENSION"), ExploreParameters.FilteringLogic.AND);
        ep.addFilteringItem(new EqualFilteringItem("targetDataTypeKind", "FACT"), ExploreParameters.FilteringLogic.AND);

        return generateDDL(spaceName, ep);
    }

    public String generateDimensionToDimensionDefinitionDDL() {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDataRelationMappingDefinitionFactType);
        ep.setDefaultFilteringItem(new EqualFilteringItem(this.SOLUTION_NAME, solutionName));
        ep.addFilteringItem(new EqualFilteringItem("sourceDataTypeKind", "DIMENSION"), ExploreParameters.FilteringLogic.AND);
        ep.addFilteringItem(new EqualFilteringItem("targetDataTypeKind", "DIMENSION"), ExploreParameters.FilteringLogic.AND);

        return generateDDL(spaceName, ep);
    }

    public String generateFactToDateDefinitionDDL() {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDataDateDimensionMappingDefinitionFactType);
        ep.setDefaultFilteringItem(new EqualFilteringItem(this.SOLUTION_NAME, solutionName));
        ep.addFilteringItem(new EqualFilteringItem("sourceDataTypeKind", "FACT"), ExploreParameters.FilteringLogic.AND);

        return generateDDL(spaceName, ep);
    }

    public String generateDimensionToDateDefinitionDDL() {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDataDateDimensionMappingDefinitionFactType);
        ep.setDefaultFilteringItem(new EqualFilteringItem(this.SOLUTION_NAME, solutionName));
        ep.addFilteringItem(new EqualFilteringItem("sourceDataTypeKind", "DIMENSION"), ExploreParameters.FilteringLogic.AND);

        return generateDDL(spaceName, ep);
    }

    public String generateCopyFactDuplicatePropertiesDDL() {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDataPropertiesDuplicateMappingDefinitionFactType);
        ep.setDefaultFilteringItem(new EqualFilteringItem(this.SOLUTION_NAME, solutionName));
        ep.addFilteringItem(new EqualFilteringItem("sourceDataTypeKind", "FACT"), ExploreParameters.FilteringLogic.AND);

        return generateDDL(spaceName, ep);
    }

    public String generateCopyDimensionDuplicatePropertiesDDL() {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDataPropertiesDuplicateMappingDefinitionFactType);
        ep.setDefaultFilteringItem(new EqualFilteringItem(this.SOLUTION_NAME, solutionName));
        ep.addFilteringItem(new EqualFilteringItem("sourceDataTypeKind", "DIMENSION"), ExploreParameters.FilteringLogic.AND);

        return generateDDL(spaceName, ep);
    }

    public String generateCustomPropertyAliasTypeDDL() {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SolutionTemplateConstants.BUSINESSSOLUTION_SolutionCustomPropertyAliasFactType);
        ep.setDefaultFilteringItem(new EqualFilteringItem(this.SOLUTION_NAME, solutionName));

        return generateDDL(spaceName, ep);
    }

    public String generateDDL(String spaceName, ExploreParameters ep) {
        logger.info("Enter generateDDL() with spaceName: {} and exploreParameters: {}", spaceName, ep);
        InfoDiscoverSpace ids = null;
        try {
            ids = DatabaseConnection.connectToSpace(spaceName);
            if (ids == null) {
                return null;
            }

            List<Fact> factTypesList = QueryExecutor.executeFactsQuery(ids.getInformationExplorer(), ep);

            return factListsToJson(factTypesList);
        } catch (Exception e) {
            logger.error("Failed to generateDDL: {}", e.getMessage());
        } finally {
            if (ids != null) {
                ids.closeSpace();
            }
        }

        logger.info("Exit method generateDDL()...");
        return null;
    }

    public String factListsToJson(List<Fact> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        JsonArray jsonArray = new JsonArray();
        for (Fact fact : list) {
            Map<String, Object> map = FactUtil.getPropertiesFromFact(fact);
            JsonObject jsonObject = JsonObjectUtil.mapToJsonObject(map);
            jsonArray.add(jsonObject);
        }

        JsonObject factTypesJson = new JsonObject();
        factTypesJson.add(JsonConstants.JSON_DATA, jsonArray);

        return factTypesJson.toString();
    }

//
//    public List<FactTypeDefinitionPOJO> getFactTypeDefinitionPOJOs(InfoDiscoverSpace ids, String factType) {
//        List<Fact> facts = getTypeDefinitions(ids, factType);
//
//        if (CollectionUtils.isNotEmpty(facts)) {
//            return convertFactToTypeDifinitionPOJO(facts);
//        }
//
//        return null;
//    }
//
//    public List<SolutionTypePropertyTypePOJO> getPropertyTypeDefinition(InfoDiscoverSpace ids, String factType) {
//        ExploreParameters ep = new ExploreParameters();
//        ep.setType(SolutionTemplateConstants.BUSINESSSOLUTION_SolutionTypePropertyFactType);
//        ep.setDefaultFilteringItem(new EqualFilteringItem("propertySourceOwner", factType));
//
//        List<Fact> facts = QueryExecutor.executeFactsQuery(ids.getInformationExplorer(), ep);
//        if (CollectionUtils.isNotEmpty(facts)) {
//            return convertFactToPropertyTypePOJO(facts);
//        }
//
//        return null;
//    }
//
//    private List<FactTypeDefinitionPOJO> convertFactToTypeDifinitionPOJO(List<Fact> facts) {
//        List<FactTypeDefinitionPOJO> list = new ArrayList<>();
//        for (Fact fact : facts) {
//            String factTypeName = fact.getProperty("factTypeName").getPropertyValue().toString();
//            String factTypeAliasName = fact.getProperty("factTypeAliasName").getPropertyValue().toString();
//            list.add(new FactTypeDefinitionPOJO(solutionName, factTypeName, factTypeAliasName));
//        }
//        return list;
//    }
//
//    private List<SolutionTypePropertyTypePOJO> convertFactToPropertyTypePOJO(List<Fact> facts) {
//        List<SolutionTypePropertyTypePOJO> list = new ArrayList<>();
//        for (Fact fact : facts) {
//            String propertySourceOwner = fact.getProperty("propertySourceOwner").getPropertyValue().toString();
//            String propertyTypeName = fact.getProperty("propertyTypeName").getPropertyValue().toString();
//            boolean isReadOnly = (boolean) fact.getProperty("isReadOnly").getPropertyValue();
//            boolean isMandatory = (boolean) fact.getProperty("isMandatory").getPropertyValue();
//            String propertyAliasName = fact.getProperty("propertyAliasName").getPropertyValue().toString();
//            String propertyName = fact.getProperty("propertyName").getPropertyValue().toString();
//            String propertyType = fact.getProperty("propertyType").getPropertyValue().toString();
//            boolean isNullable = (boolean) fact.getProperty("isNullable").getPropertyValue();
//            String solutionName = fact.getProperty("solutionName").getPropertyValue().toString();
//            String propertyTypeKind = fact.getProperty("propertyTypeKind").getPropertyValue().toString();
//
//            SolutionTypePropertyTypePOJO pojo = new SolutionTypePropertyTypePOJO();
//            pojo.setPropertySourceOwner(propertySourceOwner);
//            pojo.setPropertyTypeName(propertyTypeName);
//            pojo.setReadOnly(isReadOnly);
//            pojo.setMandatory(isMandatory);
//            pojo.setPropertyAliasName(propertyAliasName);
//            pojo.setPropertyName(propertyName);
//            pojo.setPropertyType(propertyType);
//            pojo.setNullable(isNullable);
//            pojo.setSolutionName(solutionName);
//            pojo.setPropertyTypeKind(propertyTypeKind);
//
//            list.add(pojo);
//        }
//        return list;
//    }

}
