package com.infoDiscover.solution.template;

import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.config.PropertyHandler;
import com.infoDiscover.solution.builder.SolutionConstants;
import com.infoDiscover.solution.builder.vo.RelationMappingVO;
import com.infoDiscover.solution.common.database.DatabaseConnection;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sun on 7/17/17.
 */
public class RelationMapping {
    public static final Logger logger = LoggerFactory.getLogger(RelationMapping.class);

    private String solutionName;
    private String spaceName = PropertyHandler.getPropertyValue(PropertyHandler.META_CONFIG_DISCOVERSPACE);

    public RelationMapping(String solutionName) {
        this.solutionName = solutionName;
    }

    public static Map<String, List<RelationMappingVO>> factToDimensionMap = new HashMap<>();
    public static Map<String, List<RelationMappingVO>> dimensionToFactMap = new HashMap<>();
    public static Map<String, List<RelationMappingVO>> factToFactMap = new HashMap<>();
    public static Map<String, List<RelationMappingVO>> dimensionToDimensionMap = new HashMap<>();

    public void getRelationMappings() {
        logger.info("Enter getRelationMappings()");
        InfoDiscoverSpace ids = null;
        try {

            ids = DatabaseConnection.connectToSpace(spaceName);
            setFactToFactMappings(ids);
            setFactToDimensionMappings(ids);
            setDimensionToFactMappings(ids);
            setDimensionToDimensionMappings(ids);


        } catch (Exception e) {
            logger.error("Failed to connect to {}", spaceName);
        } finally {
            ids.closeSpace();
        }

        logger.info("Exit getRelationMappings()");
    }

    private void setFactToFactMappings(InfoDiscoverSpace ids) {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDataRelationMappingDefinitionFactType);
        ep.setDefaultFilteringItem(new EqualFilteringItem(DDLExporter.SOLUTION_NAME, solutionName));
        ep.addFilteringItem(new EqualFilteringItem("sourceDataTypeKind", "FACT"), ExploreParameters.FilteringLogic.AND);
        ep.addFilteringItem(new EqualFilteringItem("targetDataTypeKind", "FACT"), ExploreParameters.FilteringLogic.AND);
//        ep.addFilteringItem(new EqualFilteringItem("sourceDataTypeName", factType), ExploreParameters.FilteringLogic.AND);

        List<Fact> factToFactMappingList = QueryExecutor.executeFactsQuery(ids.getInformationExplorer(), ep);
        if (CollectionUtils.isEmpty(factToFactMappingList)) {
            this.setFactToFactMap(null);
        }

        List<RelationMappingVO> list = convertFactToRelationMappingPOJO(factToFactMappingList, SolutionConstants.JSON_FACT_TO_FACT_MAPPING);
        Map<String, List<RelationMappingVO>> map = new HashMap<>();
        map.put(SolutionConstants.JSON_FACT_TO_FACT_MAPPING, list);
        this.setFactToFactMap(map);
    }

    private void setFactToDimensionMappings(InfoDiscoverSpace ids) {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDataRelationMappingDefinitionFactType);
        ep.setDefaultFilteringItem(new EqualFilteringItem(DDLExporter.SOLUTION_NAME, solutionName));
        ep.addFilteringItem(new EqualFilteringItem("sourceDataTypeKind", "FACT"), ExploreParameters.FilteringLogic.AND);
        ep.addFilteringItem(new EqualFilteringItem("targetDataTypeKind", "DIMENSION"), ExploreParameters.FilteringLogic.AND);
//        ep.addFilteringItem(new EqualFilteringItem("sourceDataTypeName", factType), ExploreParameters.FilteringLogic.AND);

        List<Fact> factToDimensionMappingList = QueryExecutor.executeFactsQuery(ids.getInformationExplorer(), ep);
        if (CollectionUtils.isEmpty(factToDimensionMappingList)) {
            this.setFactToDimensionMap(null);
        }

        List<RelationMappingVO> list = convertFactToRelationMappingPOJO(factToDimensionMappingList, SolutionConstants.JSON_FACT_TO_DIMENSION_MAPPING);
        Map<String, List<RelationMappingVO>> map = new HashMap<>();
        map.put(SolutionConstants.JSON_FACT_TO_DIMENSION_MAPPING, list);
        this.setFactToDimensionMap(map);
    }

    private void setDimensionToDimensionMappings(InfoDiscoverSpace ids) {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDataRelationMappingDefinitionFactType);
        ep.setDefaultFilteringItem(new EqualFilteringItem(DDLExporter.SOLUTION_NAME, solutionName));
        ep.addFilteringItem(new EqualFilteringItem("sourceDataTypeKind", "DIMENSION"), ExploreParameters.FilteringLogic.AND);
        ep.addFilteringItem(new EqualFilteringItem("targetDataTypeKind", "DIMENSION"), ExploreParameters.FilteringLogic.AND);
//        ep.addFilteringItem(new EqualFilteringItem("sourceDataTypeName", dimensionType), ExploreParameters.FilteringLogic.AND);

        List<Fact> factToDimensionMappingList = QueryExecutor.executeFactsQuery(ids.getInformationExplorer(), ep);
        if (CollectionUtils.isEmpty(factToDimensionMappingList)) {
            this.setDimensionToDimensionMap(null);
        }

        List<RelationMappingVO> list = convertFactToRelationMappingPOJO(factToDimensionMappingList, SolutionConstants.JSON_DIMENSION_TO_DIMENSION_MAPPING);
        Map<String, List<RelationMappingVO>> map = new HashMap<>();
        map.put(SolutionConstants.JSON_DIMENSION_TO_DIMENSION_MAPPING, list);
        this.setDimensionToDimensionMap(map);
    }

    private void setDimensionToFactMappings(InfoDiscoverSpace ids) {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SolutionTemplateConstants.BUSINESSSOLUTION_SolutionDataRelationMappingDefinitionFactType);
        ep.setDefaultFilteringItem(new EqualFilteringItem(DDLExporter.SOLUTION_NAME, solutionName));
        ep.addFilteringItem(new EqualFilteringItem("sourceDataTypeKind", "DIMENSION"), ExploreParameters.FilteringLogic.AND);
        ep.addFilteringItem(new EqualFilteringItem("targetDataTypeKind", "FACT"), ExploreParameters.FilteringLogic.AND);
//        ep.addFilteringItem(new EqualFilteringItem("sourceDataTypeName", dimensionType), ExploreParameters.FilteringLogic.AND);

        List<Fact> factToDimensionMappingList = QueryExecutor.executeFactsQuery(ids.getInformationExplorer(), ep);
        if (CollectionUtils.isEmpty(factToDimensionMappingList)) {
            this.setDimensionToFactMap(null);
        }

        List<RelationMappingVO> list = convertFactToRelationMappingPOJO(factToDimensionMappingList, SolutionConstants.JSON_DIMENSION_TO_FACT_MAPPING);
        Map<String, List<RelationMappingVO>> map = new HashMap<>();
        map.put(SolutionConstants.JSON_DIMENSION_TO_FACT_MAPPING, list);
        this.setDimensionToFactMap(map);
    }

    private List<RelationMappingVO> convertFactToRelationMappingPOJO(List<Fact> facts, String relationMappingType) {
        List<RelationMappingVO> list = new ArrayList<>();
        for (Fact fact : facts) {
            Object sourceDataTypeKind = fact.getProperty("sourceDataTypeKind").getPropertyValue();
            Object sourceDataTypeName = fact.getProperty("sourceDataTypeName").getPropertyValue();
            Object sourceDataPropertyName = fact.getProperty("sourceDataPropertyName").getPropertyValue();
            Object sourceDataPropertyType = fact.getProperty("sourceDataPropertyType").getPropertyValue();
            Object targetDataTypeKind = fact.getProperty("targetDataTypeKind").getPropertyValue();
            Object targetDataTypeName = fact.getProperty("targetDataTypeName").getPropertyValue();
            Object targetDataPropertyName = fact.getProperty("targetDataTypeName").getPropertyValue();
            Object targetDataPropertyType = fact.getProperty("targetDataPropertyType").getPropertyValue();
            Object relationTypeName = fact.getProperty("relationTypeName").getPropertyValue();
            Object relationDirection = fact.getProperty("relationDirection").getPropertyValue();
            Object minValue = fact.getProperty("minValue") == null ? null : fact.getProperty("minValue").getPropertyValue();
            Object maxValue = fact.getProperty("maxValue") == null ? null : fact.getProperty("maxValue").getPropertyValue();
            Object mappingNotExistHandleMethod = fact.getProperty("mappingNotExistHandleMethod").getPropertyValue();

            RelationMappingVO pojo = new RelationMappingVO();
            pojo.setRelationMappingType(relationMappingType);
            pojo.setSourceDataTypeKind(sourceDataTypeKind == null ? null : sourceDataTypeKind.toString());
            pojo.setSourceDataTypeName(sourceDataTypeName == null ? null : sourceDataTypeName.toString());
            pojo.setSourceDataPropertyName(sourceDataPropertyName == null ? null : sourceDataPropertyName.toString());
            pojo.setSourceDataPropertyType(sourceDataPropertyType == null ? null : sourceDataPropertyType.toString());
            pojo.setTargetDataTypeKind(targetDataTypeKind == null ? null : targetDataTypeKind.toString());
            pojo.setTargetDataTypeName(targetDataTypeName == null ? null : targetDataTypeName.toString());
            pojo.setTargetDataPropertyName(targetDataPropertyName == null ? null : targetDataPropertyName.toString());
            pojo.setTargetDataPropertyType(targetDataPropertyType == null ? null : targetDataPropertyType.toString());
            pojo.setRelationTypeName(relationTypeName == null ? null : relationTypeName.toString());
            pojo.setRelationDirection(relationDirection.toString());
            pojo.setMinValue(minValue == null ? null : minValue.toString());
            pojo.setMaxValue(maxValue == null ? null : maxValue.toString());
            pojo.setMappingNotExistHandleMethod(mappingNotExistHandleMethod == null ? null : mappingNotExistHandleMethod.toString());

            list.add(pojo);
        }
        return list;
    }

    public Map<String, List<RelationMappingVO>> getFactToDimensionMap() {
        return factToDimensionMap;
    }

    public void setFactToDimensionMap(Map<String, List<RelationMappingVO>> factToDimensionMap) {
        this.factToDimensionMap = factToDimensionMap;
    }

    public Map<String, List<RelationMappingVO>> getDimensionToFactMap() {
        return dimensionToFactMap;
    }

    public void setDimensionToFactMap(Map<String, List<RelationMappingVO>> dimensionToFactMap) {
        this.dimensionToFactMap = dimensionToFactMap;
    }

    public Map<String, List<RelationMappingVO>> getFactToFactMap() {
        return factToFactMap;
    }

    public void setFactToFactMap(Map<String, List<RelationMappingVO>> factToFactMap) {
        this.factToFactMap = factToFactMap;
    }

    public Map<String, List<RelationMappingVO>> getDimensionToDimensionMap() {
        return dimensionToDimensionMap;
    }

    public void setDimensionToDimensionMap(Map<String, List<RelationMappingVO>> dimensionToDimensionMap) {
        this.dimensionToDimensionMap = dimensionToDimensionMap;
    }

}
