package com.infoDiscover.solution.template;

import com.info.discover.ruleengine.solution.pojo.DataDateMappingVO;
import com.info.discover.ruleengine.solution.pojo.RelationMappingVO;
import com.infoDiscover.common.dimension.time.DayDimensionManager;
import com.infoDiscover.common.dimension.time.dimension.DayDimensionVO;
import com.infoDiscover.common.util.DataTypeChecker;
import com.infoDiscover.infoDiscoverEngine.dataMart.*;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.builder.SolutionConstants;
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

    public long linkBetweenNodesFromFact(InfoDiscoverSpace ids, Fact fact,
                                         Map<String, List<RelationMappingVO>> relationMappingsMap,
                                         Map<String, List<DataDateMappingVO>> dateMappingsMap) throws Exception {

        logger.info("Enter linkBetweenNodesFromFact with factRid: {}", fact.getId());

        long changed = 0l;

        String rid = fact.getId();
        String factType = fact.getType();

        if (MapUtils.isNotEmpty(relationMappingsMap)) {
            changed += link(ids, rid, relationMappingsMap.get(SolutionConstants.JSON_FACT_TO_FACT_MAPPING),
                    factType, "FACT");
            changed += link(ids, rid, relationMappingsMap.get(SolutionConstants.JSON_FACT_TO_DIMENSION_MAPPING),
                    factType, "FACT");
        }

        // link to DATE dimension
        if (MapUtils.isNotEmpty(dateMappingsMap)) {
            changed += linkToDateDimension(ids, rid, dateMappingsMap.get(SolutionConstants.JSON_FACT_TO_DATE_DIMENSION_MAPPING),
                    factType, "FACT");
        }

        logger.info("Exit linkBetweenNodesFromFact()...");

        return changed;
    }

    public long linkBetweenNodesFromDimension(InfoDiscoverSpace ids, Dimension dimension,
                                              Map<String, List<RelationMappingVO>> relationMappingsMap,
                                              Map<String, List<DataDateMappingVO>> dateMappingsMap) throws Exception {

        logger.info("Enter linkBetweenNodesFromDimension with dimensionRid: {}", dimension.getId());

        long changed = 0l;

        String rid = dimension.getId();
        String dimensionType = dimension.getType();

        if (MapUtils.isNotEmpty(relationMappingsMap)) {
            changed += link(ids, rid, relationMappingsMap.get(SolutionConstants.JSON_DIMENSION_TO_FACT_MAPPING),
                    dimensionType, "DIMENSION");
            changed += link(ids, rid, relationMappingsMap.get(SolutionConstants.JSON_DIMENSION_TO_DIMENSION_MAPPING),
                    dimensionType, "DIMENSION");
        }

        // link to DATE dimension
        if (MapUtils.isNotEmpty(dateMappingsMap)) {
            changed += linkToDateDimension(ids, rid, dateMappingsMap.get(SolutionConstants.JSON_DIMENSION_TO_DATE_DIMENSION_MAPPING),
                    dimensionType, "DIMENSION");
        }
        logger.info("Exit linkBetweenNodesFromDimension()...");

        return changed;
    }

    private long link(InfoDiscoverSpace ids,
                      String rid,
                      List<RelationMappingVO> dataToDataMappingList,
                      String factType,
                      String relationableType)
            throws Exception {

        long changed = 0l;

        if (CollectionUtils.isEmpty(dataToDataMappingList)) {
            return changed;
        }

        // link fact/dimension
        List<RelationMappingVO> voList = new ArrayList<>();
        for (RelationMappingVO vo : dataToDataMappingList) {
            if (vo.getSourceDataTypeName().equalsIgnoreCase(factType)) {
                voList.add(vo);
            }
        }

        if (CollectionUtils.isNotEmpty(voList)) {
            // retrieve fact again as its version is updated
            Relationable latestFact = new FactManager(ids).getRelationableByRID(rid, factType, relationableType);

            for (RelationMappingVO vo : voList) {
                changed += linkRelation(ids, latestFact, vo);
            }
        }

        return changed;
    }

    public long linkToDateDimension(InfoDiscoverSpace ids,
                                    String rid,
                                    List<DataDateMappingVO> dataToDateMappingList,
                                    String factType,
                                    String relationableType)
            throws Exception {

        long changed = 0l;

        if (CollectionUtils.isEmpty(dataToDateMappingList)) {
            return changed;
        }

        // link fact/dimension to DATE dimension
        List<DataDateMappingVO> dateMappingList = new ArrayList<>();
        for (DataDateMappingVO vo : dataToDateMappingList) {
            if (vo.getSourceDataTypeName().equalsIgnoreCase(factType)) {
                dateMappingList.add(vo);
            }
        }

        if (CollectionUtils.isNotEmpty(dateMappingList)) {
            // retrieve fact again as its version is updated
            Relationable latestFact = new FactManager(ids).getRelationableByRID(rid, factType, relationableType);
            for (DataDateMappingVO vo : dateMappingList) {
                changed += linkToDateRelation(ids, latestFact, vo);
            }
        }

        return changed;
    }

    private long linkToDateRelation(InfoDiscoverSpace ids, Relationable fact, DataDateMappingVO vo) {

        long changed = 0l;

        String sourceDataTypeKind = vo.getSourceDataTypeKind();
        String sourcePropertyName = vo.getSourceDataPropertyName();
        String prefix = vo.getDateDimensionTypePrefix();
        String relationType = vo.getRelationTypeName();
        String relationDirection = vo.getRelationDirection();

        Property sourceDataProperty = fact.getProperty(sourcePropertyName);
        logger.debug("sourceDatProperty: {}",sourceDataProperty);
        if (sourceDataProperty == null) {
            logger.info("property of sourceDataPropertyName {} is null", sourcePropertyName);
            return changed;
        }

        Date sourceDataPropertyValue = (Date) sourceDataProperty.getPropertyValue();

        RelationshipManager relationshipManager = new RelationshipManager(ids);

        try {
            DayDimensionVO dayDimensionVO = DayDimensionManager.getDayDimensionVOWithPrefix
                    (prefix, sourceDataPropertyValue);
            Dimension day = new DimensionManager(ids).getDayDimension(prefix, dayDimensionVO);

            if (sourceDataTypeKind.equalsIgnoreCase("FACT")) {
                Relation relation = relationshipManager.linkFactToDateDimension(prefix, (Fact) fact, dayDimensionVO, relationType, relationDirection);
                if (relation != null) {
                    changed += 1;
                }
            } else if (sourceDataTypeKind.equalsIgnoreCase("DIMENSION")) {
                if (relationDirection.equalsIgnoreCase(RelationDirection.TO_TARGET)) {
                    Relation relation = relationshipManager.linkDimensionsByRelationType((Dimension) fact, day, relationType);
                    if (relation != null) {
                        changed += 1;
                    }
                } else if (relationDirection.equalsIgnoreCase(RelationDirection.TO_SOURCE)) {
                    Relation relation = relationshipManager.linkDimensionsByRelationType(day, (Dimension) fact, relationType);
                    if (relation != null) {
                        changed += 1;
                    }
                }
            }
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineRuntimeException e) {
            e.printStackTrace();
        } catch (InfoDiscoveryEngineDataMartException e) {
            e.printStackTrace();
        }


        return changed;
    }

    private long linkRelation(InfoDiscoverSpace ids, Relationable fact, RelationMappingVO vo) throws Exception {

        long changed = 0l;

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
            return changed;
        }

        FactManager factManager = new FactManager(ids);
        DimensionManager dimensionManager = new DimensionManager(ids);
        RelationshipManager relationshipManager = new RelationshipManager(ids);

        // construct target sql to query
        String sql = constructSql(fact, vo);

        if (sql == null) {
            return changed;
        }

        // create the targetRelationable
        if (targetDataTypeKind.equalsIgnoreCase("FACT")) {
            if (!ids.hasFactType(targetDataTypeName)) {
                if (mappingNotExistHandleMethod.equalsIgnoreCase(MappingNotExistHandleMethod.CREATE)) {
                    ids.addFactType(targetDataTypeName);
                } else {
                    return changed;
                }
            }
        } else if (targetDataTypeKind.equalsIgnoreCase("DIMENSION")) {
            if (!ids.hasDimensionType(targetDataTypeName)) {
                if (mappingNotExistHandleMethod.equalsIgnoreCase(MappingNotExistHandleMethod.CREATE)) {
                    ids.addDimensionType(targetDataTypeName);
                } else {
                    return changed;
                }
            }
        }

        List<Relationable> targetRelationablesList = QueryExecutor.getManyRelationables(ids.getInformationExplorer(), sql);

        // if targetRelationable is not existed, check if to create a new one or not
        if (targetRelationablesList.isEmpty() && mappingNotExistHandleMethod.equalsIgnoreCase(MappingNotExistHandleMethod.CREATE)) {
            try {
                Map<String, Object> props = new HashMap<>();

                Object targetDataPropertyValue = getNumericTargetPropertyValue(fact, vo);
                props.put(targetDataPropertyName, targetDataPropertyValue);

                if (targetDataTypeKind.equalsIgnoreCase("FACT")) {
                    Fact r = factManager.createFact(targetDataTypeName, props);
                    if (r != null) {
                        targetRelationablesList.add(r);
                    }
                } else if (targetDataTypeKind.equalsIgnoreCase("DIMENSION")) {
                    Dimension d = dimensionManager.createDimension(targetDataTypeName, props);
                    if (d != null) {
                        targetRelationablesList.add(d);
                    }
                }
            } catch (Exception e) {
                logger.error("targetDataPropertyValue {} is not a numeric", vo.getTargetDataPropertyValue());
            }
        }

        //TODO: to copy properties from target to source

        logger.info("start to link the source and target");
        if (!targetRelationablesList.isEmpty() && !sourceDataTypeName.equalsIgnoreCase(targetDataTypeName)) {
            for (Relationable targetRelationable : targetRelationablesList) {
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
                        Relation relation = fact.addToRelation(targetRelationable, relationTypeName);
                        if (relation != null) {
                            changed += 1;
                        }
                    }
                } else if (relationDirection.equalsIgnoreCase(RelationDirection.TO_SOURCE)) {
                    if (!relationshipManager.isTwoRelationablesLinked(
                            fact,
                            targetRelationable,
                            Direction.IN,
                            InfoDiscoverEngineConstant.CLASSPERFIX_RELATION + relationTypeName)) {
                        Relation relation = fact.addFromRelation(targetRelationable, relationTypeName);
                        if (relation != null) {
                            changed += 1;
                        }
                    }

                } else {
                    if (!relationshipManager.isTwoRelationablesLinked(
                            fact,
                            targetRelationable,
                            Direction.IN,
                            InfoDiscoverEngineConstant.CLASSPERFIX_RELATION + relationTypeName)) {

                        Relation relation = fact.addFromRelation(targetRelationable, relationTypeName);
                        if (relation != null) {
                            changed += 1;
                        }
                    }

                    if (!relationshipManager.isTwoRelationablesLinked(
                            fact,
                            targetRelationable,
                            Direction.OUT,
                            InfoDiscoverEngineConstant.CLASSPERFIX_RELATION + relationTypeName)) {
                        Relation relation = fact.addToRelation(targetRelationable, relationTypeName);
                        if (relation != null) {
                            changed += 1;
                        }
                    }
                }
            }
        }

        return changed;
    }

    private Object getNumericTargetPropertyValue(Relationable fact, RelationMappingVO vo) {
        String minValue = vo.getMinValue();
        String maxValue = vo.getMaxValue();

        // if minValue and maxValue is not set, use sourcePropertyValue as targetPropertyValue
        if (StringUtils.isEmpty(minValue) && StringUtils.isEmpty(maxValue)) {
            return fact.getProperty(vo.getSourceDataPropertyName()).getPropertyValue();
        }

        // if minValue and maxValue is set,
        return vo.getTargetDataPropertyValue();
    }

    private String constructSql(Relationable fact, RelationMappingVO vo) {
        String sourceDataPropertyName = vo.getSourceDataPropertyName();
        String sourceDataPropertyType = vo.getSourceDataPropertyType();

        String sql = null;

        Object sourceDataPropertyValue = fact.getProperty(sourceDataPropertyName).getPropertyValue();
        if (DataTypeChecker.isStringType(sourceDataPropertyType)) {
            String targetTypeName = getTargetTypeName(vo);
            String targetDataPropertyName = vo.getTargetDataPropertyName();
            sql = constructStringEqualSql(targetTypeName, targetDataPropertyName, sourceDataPropertyValue.toString());
        } else if (DataTypeChecker.isNumericType(sourceDataPropertyType)) {

            String minValue = vo.getMinValue();
            String maxValue = vo.getMaxValue();

            // if minValue == null, maxValue == null, construct equal sql
            if (StringUtils.isEmpty(minValue) && StringUtils.isEmpty(maxValue)) {
                return constructNumericEqualSql(vo, sourceDataPropertyValue);
            }

            String targetDataPropertyValue = vo.getTargetDataPropertyValue();
            if (StringUtils.isEmpty(targetDataPropertyValue)) {
                return null;
            }

            try {
                // check the sourceDataPropertyValue in with minValue and maxValue range


                double sourceDataPropertyDoubleValue = Double.valueOf(sourceDataPropertyValue.toString());

                // 1. >= minValue, maxValue == null
                // 2. <= maxValue, minValue == null
                // 3. minValue <= sourceDataPropertyValue <= maxValue

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

        } else if (DataTypeChecker.isBooleanType(sourceDataPropertyType)) {
            sql = constructNumericEqualSql(vo, sourceDataPropertyValue);
        } else if (DataTypeChecker.isDateType(sourceDataPropertyType)) {
            sql = constructDateEqualSql(vo, sourceDataPropertyValue);
        }
        return sql;
    }

    private String constructNumericEqualSql(RelationMappingVO vo, Object propertyValue) {
        String targetTypeName = getTargetTypeName(vo);
        return "select * from " + targetTypeName + " where " + vo.getTargetDataPropertyName() + " = " + propertyValue;
    }

    String constructStringEqualSql(String targetTypeName, String targetDataPropertyName, String propertyValue) {

        String[] values = propertyValue.split(",");
        StringBuilder sb = new StringBuilder();
        sb.append("select * from " + targetTypeName);

        StringBuilder where = new StringBuilder();
        int i = 0;
        for (String value : values) {
            if (StringUtils.isNotBlank(value)) {
                where.append(targetDataPropertyName + ".toUpperCase() = '" + value.trim().toUpperCase() + "'");
            }

            if (i < values.length - 1) {
                where.append(" OR ");
            }

            ++i;
        }

        String whereAfterStrip = StringUtils.stripEnd(where.toString(), "OR ");
        if (StringUtils.isNotBlank(whereAfterStrip)) {
            sb.append(" where (");
            sb.append(whereAfterStrip);
            sb.append(")");
        }

        return sb.toString();
    }

    private String constructDateEqualSql(RelationMappingVO vo, Object propertyValue) {
        String targetTypeName = getTargetTypeName(vo);
        return "select * from " + targetTypeName + " where " + vo.getTargetDataPropertyName() + " = '" + propertyValue + "'";
    }

    private String constructEqualSqlOfNumericProperty(RelationMappingVO vo) {
        String targetDataPropertyValue = vo.getTargetDataPropertyValue();

        String targetTypeName = getTargetTypeName(vo);
        String targetDataPropertyName = vo.getTargetDataPropertyName();

        if (StringUtils.isEmpty(targetDataPropertyValue)) {
            return null;
        }

        try {
            if (DataTypeChecker.isNumericType(vo.getTargetDataPropertyType())) {
                double targetDataPropertyValueInDouble = Double.parseDouble(targetDataPropertyValue);
                return constructNumericEqualSql(vo, targetDataPropertyValueInDouble);
            } else {
                return constructStringEqualSql(targetTypeName, targetDataPropertyName, targetDataPropertyValue);
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
