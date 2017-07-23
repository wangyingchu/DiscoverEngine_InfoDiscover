package com.infoDiscover.solution.template;

import com.infoDiscover.common.dimension.time.DayDimensionManager;
import com.infoDiscover.common.dimension.time.dimension.DayDimensionVO;
import com.infoDiscover.common.dimension.time.manager.TimeDimensionManager;
import com.infoDiscover.common.util.DataTypeChecker;
import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.Property;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relationable;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.infoDiscover.solution.builder.SolutionConstants;
import com.infoDiscover.solution.builder.vo.RelationMappingVO;
import com.infoDiscover.solution.common.dimension.DimensionManager;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.common.fact.FactManager;
import com.infoDiscover.solution.common.relationship.RelationshipManager;
import com.tinkerpop.blueprints.Direction;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by sun on 7/17/17.
 */
public class RelationMappingOperator {
    public static final Logger logger = LoggerFactory.getLogger(RelationMappingOperator.class);

    public void linkBetweenNodesFromFact(InfoDiscoverSpace ids, Fact fact) throws Exception {
        logger.info("Enter linkBetweenNodesFromFact with factRid: {}", fact.getId());

        String rid = fact.getId();
        String factType = fact.getType();

        link(ids, rid, SolutionConstants.JSON_FACT_TO_FACT_MAPPING, factType);
        link(ids, rid, SolutionConstants.JSON_FACT_TO_DIMENSION_MAPPING, factType);


        // TODO:
//        linkDimensionToDimension(ids,rid, factType);


        logger.info("Exit linkBetweenNodesFromFact()...");
    }

    public void linkBetweenNodesFromDimension(InfoDiscoverSpace ids, Dimension dimension) throws Exception {
        logger.info("Enter linkBetweenNodesFromDimension with dimensionRid: {}", dimension.getId());

        String rid = dimension.getId();
        String dimensionType = dimension.getType();

        link(ids, rid, SolutionConstants.JSON_DIMENSION_TO_FACT_MAPPING, dimensionType);
        link(ids, rid, SolutionConstants.JSON_DIMENSION_TO_DIMENSION_MAPPING, dimensionType);

        logger.info("Exit linkBetweenNodesFromDimension()...");
    }

    private void link(InfoDiscoverSpace ids, String rid,
                      String mappingType, String factType)
            throws Exception {

        Map<String, List<RelationMappingVO>> mappings = null;

        if (mappingType.equalsIgnoreCase(SolutionConstants.JSON_FACT_TO_FACT_MAPPING)) {
            mappings = RelationMapping.factToFactMap;
        } else if (mappingType.equalsIgnoreCase(SolutionConstants.JSON_FACT_TO_DIMENSION_MAPPING)) {
            mappings = RelationMapping.factToDimensionMap;
        } else if (mappingType.equalsIgnoreCase(SolutionConstants.JSON_DIMENSION_TO_FACT_MAPPING)) {
            mappings = RelationMapping.dimensionToFactMap;
        } else if (mappingType.equalsIgnoreCase(SolutionConstants.JSON_DIMENSION_TO_DIMENSION_MAPPING)) {
            mappings = RelationMapping.dimensionToDimensionMap;
        }

        if (MapUtils.isEmpty(mappings)) {
            return;
        }

        List<RelationMappingVO> factToFactList = mappings.get(mappingType);
        if (CollectionUtils.isEmpty(factToFactList)) {
            return;
        }

        List<RelationMappingVO> voList = new ArrayList<>();
        for (RelationMappingVO vo : factToFactList) {
            if (vo.getSourceDataTypeName().equalsIgnoreCase(factType)) {
                voList.add(vo);
            }
        }

        if (CollectionUtils.isNotEmpty(voList)) {
            // retrieve fact again as its version is updated
            Relationable latestFact = new FactManager(ids).getRelationableByRID(rid, factType);

            for (RelationMappingVO vo : voList) {
                linkRelation(ids, latestFact, vo);
            }
        }
    }

    private void linkRelation(InfoDiscoverSpace ids, Relationable fact, RelationMappingVO vo) throws Exception {

        String sourceDataTypeName = vo.getSourceDataTypeName();
        String sourceDataPropertyName = vo.getSourceDataPropertyName();
        String sourceDataPropertyType = vo.getSourceDataPropertyType();
        String targetDataTypeName = vo.getTargetDataTypeName();
        String targetDataPropertyName = vo.getTargetDataPropertyName();
        String targetDataPropertyType = vo.getTargetDataPropertyType();
        String targetDataTypeKind = vo.getTargetDataTypeKind();
        String relationTypeName = vo.getRelationTypeName();
        String relationDirection = vo.getRelationDirection();
        String mappingNotExistHandleMethod = vo.getMappingNotExistHandleMethod();

        Property sourceDataProperty = fact.getProperty(sourceDataPropertyName);
        if (sourceDataProperty == null) {
            logger.info("property of sourceDataPropertyName {} is null", sourceDataPropertyName);
            return;
        }

        FactManager factManager = new FactManager(ids);
        DimensionManager dimensionManager = new DimensionManager(ids);
        RelationshipManager relationshipManager = new RelationshipManager(ids);

        Object sourceDataPropertyValue = sourceDataProperty.getPropertyValue();

        // construct target sql to query
        String sql = constructSql(fact, vo);

        if (sql == null) {
            return;
        }

        // create the targetRelationable
        if (targetDataTypeKind.equalsIgnoreCase("FACT")) {
            if (!ids.hasFactType(targetDataTypeName)) {
                if (mappingNotExistHandleMethod.equalsIgnoreCase(MappingNotExistHandleMethod.CREATE)) {
                    ids.addFactType(targetDataTypeName);
                } else {
                    return;
                }
            }
        } else if (targetDataTypeKind.equalsIgnoreCase("DIMENSION")) {
            if (!ids.hasDimensionType(targetDataTypeName)) {
                if (mappingNotExistHandleMethod.equalsIgnoreCase(MappingNotExistHandleMethod.CREATE)) {
                    ids.addDimensionType(targetDataTypeName);
                } else {
                    return;
                }
            }
        }

        Relationable targetRelationable = QueryExecutor.getOneRelationable(ids.getInformationExplorer(), sql);

        // if targetRelationable is not existed, check if to create a new one or not
        if (targetRelationable == null && mappingNotExistHandleMethod.equalsIgnoreCase(MappingNotExistHandleMethod.CREATE)) {
            try {
                Map<String, Object> props = new HashMap<>();
                if (DataTypeChecker.isNumericType(targetDataPropertyType)) {
                    double targetDataPropertyValue = Double.parseDouble(vo.getTargetDataPropertyValue());
                    props.put(targetDataPropertyName, targetDataPropertyValue);
                } else if (DataTypeChecker.isStringType(targetDataPropertyType)) {
                    props.put(targetDataPropertyName, sourceDataPropertyValue);
                } else if (DataTypeChecker.isDateType(targetDataPropertyType)) {
                    props.put(targetDataPropertyName, sourceDataPropertyValue);
                }

                if (targetDataTypeKind.equalsIgnoreCase("FACT")) {
                    targetRelationable = factManager.createFact(targetDataTypeName, props);
                } else if (targetDataTypeKind.equalsIgnoreCase("DIMENSION") && !DataTypeChecker.isDateType(targetDataPropertyType)) {
                    targetRelationable = dimensionManager.createDimension(targetDataTypeName, props);
                } else if (targetDataTypeKind.equalsIgnoreCase("DIMENSION") && DataTypeChecker.isDateType(targetDataPropertyType)) {

                    DayDimensionVO dayDimensionVO = DayDimensionManager.getDayDimensionVO(targetDataTypeName, (Date)sourceDataPropertyValue);
                    targetRelationable =  new TimeDimensionManager(ids).createDayDimension(dayDimensionVO);
                }
            } catch (Exception e) {
                logger.error("targetDataPropertyValue {} is not a numeric", vo.getTargetDataPropertyValue());
            }
        }

        //TODO: to copy properties from target to source

        logger.info("start to link the source and target");
        if (targetRelationable != null && !sourceDataTypeName.equalsIgnoreCase(targetDataTypeName)) {
            // if relationType is not exist, create it
            if (!ids.hasRelationType(relationTypeName)) {
                ids.addRelationType(relationTypeName);
            }

            if (relationDirection.equalsIgnoreCase(RelationDirection.TO_TARGET)) {
                // check if the relation is already existed between two nodes
                if (!relationshipManager.isTwoRelationablesLinked(
                        fact,
                        targetRelationable,
                        Direction.OUT,
                        InfoDiscoverEngineConstant.CLASSPERFIX_RELATION + relationTypeName)) {
                    fact.addToRelation(targetRelationable, relationTypeName);
                }
            } else if (relationDirection.equalsIgnoreCase(RelationDirection.TO_SOURCE)) {
                if (!relationshipManager.isTwoRelationablesLinked(
                        fact,
                        targetRelationable,
                        Direction.IN,
                        InfoDiscoverEngineConstant.CLASSPERFIX_RELATION + relationTypeName)) {
                    fact.addFromRelation(targetRelationable, relationTypeName);
                }

            } else {
                if (!relationshipManager.isTwoRelationablesLinked(
                        fact,
                        targetRelationable,
                        Direction.IN,
                        InfoDiscoverEngineConstant.CLASSPERFIX_RELATION + relationTypeName)) {

                    fact.addFromRelation(targetRelationable, relationTypeName);
                }

                if (!relationshipManager.isTwoRelationablesLinked(
                        fact,
                        targetRelationable,
                        Direction.OUT,
                        InfoDiscoverEngineConstant.CLASSPERFIX_RELATION + relationTypeName)) {
                    fact.addToRelation(targetRelationable, relationTypeName);
                }
            }
        }

    }

    private String constructSql(Relationable fact, RelationMappingVO vo) {
        String sourceDataPropertyName = vo.getSourceDataPropertyName();
        String sourceDataPropertyType = vo.getSourceDataPropertyType();

        String sql = null;

        Object sourceDataPropertyValue = fact.getProperty(sourceDataPropertyName).getPropertyValue();
        if (DataTypeChecker.isStringType(sourceDataPropertyType)) {
            sql = constructEqualSql(vo, sourceDataPropertyValue);
        } else if (DataTypeChecker.isNumericType(sourceDataPropertyType)) {

            String targetDataPropertyValue = vo.getTargetDataPropertyValue();
            if (StringUtils.isEmpty(targetDataPropertyValue)) {
                return null;
            }

            try {
                // check the sourceDataPropertyValue in with minValue and maxValue range
                double sourceDataPropertyDoubleValue = (double) sourceDataPropertyValue;

                String minValue = vo.getMinValue();
                String maxValue = vo.getMaxValue();

                // 1. >= minValue, maxValue == null
                // 2. <= maxValue, minValue == null
                // 3. minValue <= sourceDataPropertyValue <= maxValue
                // 4. minValue == null, maxValue == null

                if (StringUtils.isEmpty(minValue) && StringUtils.isEmpty(maxValue)) {
                    return null;
                }

                if (StringUtils.isNotEmpty(minValue)) {
                    logger.info("minValue: {} is not null", minValue);
                    // minValue != null
                    double minDoubleValue = Double.parseDouble(minValue);

                    if (StringUtils.isEmpty(maxValue)) {
                        logger.info("maxValue is null");
                        // maxValue == null
                        if (sourceDataPropertyDoubleValue >= minDoubleValue) {
                            // check targetDatePropertyType is numeric or not
                            sql = constructEqualSqlOfNumericProperty(vo);
                        } else {
                            return null;
                        }
                    } else {
                        // maxValue != null
                        double maxDoubleValue = Double.parseDouble(maxValue);
                        if (minDoubleValue <= sourceDataPropertyDoubleValue && sourceDataPropertyDoubleValue <= maxDoubleValue) {
                            sql = constructEqualSqlOfNumericProperty(vo);
                        } else {
                            return null;
                        }
                    }

                } else {
                    // minValue == null
                    logger.info("minValue is null");
                    double maxDoubleValue = Double.parseDouble(maxValue);
                    if (sourceDataPropertyDoubleValue <= maxDoubleValue) {
                        sql = constructEqualSqlOfNumericProperty(vo);
                    } else {
                        return null;
                    }
                }

            } catch (Exception e) {
                logger.error("TargetDataPropertyValue {} is not a numeric", targetDataPropertyValue);
                return null;
            }

        } else if (DataTypeChecker.isDateType(sourceDataPropertyType)) {
            sql = constructEqualSql(vo, sourceDataPropertyValue);
        }

        return sql;
    }

    private String constructEqualSql(RelationMappingVO vo, Object propertyValue) {
        String targetTypeName = getTargetTypeName(vo);
        return "select * from " + targetTypeName + " where " + vo.getTargetDataPropertyName() + " = '" + propertyValue + "'";
    }

    private String constructEqualSqlOfNumericProperty(RelationMappingVO vo) {
        String targetDataPropertyValue = vo.getTargetDataPropertyValue();

        if (StringUtils.isEmpty(targetDataPropertyValue)) {
            return null;
        }

        try {
            if (DataTypeChecker.isNumericType(vo.getTargetDataPropertyType())) {
                double targetDataPropertyValueInDouble = Double.parseDouble(targetDataPropertyValue);
                return constructEqualSql(vo, targetDataPropertyValueInDouble);
            } else {
                return constructEqualSql(vo, targetDataPropertyValue);
            }
        } catch (Exception e) {
            logger.error("targetDataPropertyValue {} is not a numeric.", targetDataPropertyValue);
        }
        return null;
    }

    private String getTargetTypeName(RelationMappingVO vo) {
        return vo.getTargetDataTypeKind().equalsIgnoreCase("FACT")
                ? InfoDiscoverEngineConstant.CLASSPERFIX_FACT + vo.getTargetDataTypeName()
                : InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION + vo.getTargetDataTypeName();
    }

}
