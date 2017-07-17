package com.infoDiscover.solution.builder.vo;

/**
 * Created by sun.
 */
public class RelationMappingVO {

    private String sourceDataTypeKind;
    private String sourceDataTypeName;
    private String sourceDataPropertyName;
    private String sourceDataPropertyType;
    private String sourcePrimaryKey;

    private String targetDataTypeKind;
    private String targetDataTypeName;
    private String targetDataPropertyName;
    private String targetDataPropertyType;
    private String targetPrimaryKey;

    private String relationTypeName;
    private String relationDirection;

    private String minValue;
    private String maxValue;

    private String mappingNotExistHandleMethod;

    private String propertyType;

    public RelationMappingVO(String sourceDataTypeName, String sourceDataPropertyName, String sourcePrimaryKey, String
            targetDataPropertyType, String targetDataPropertyName, String targetPrimaryKey, String relationTypeName, String
            propertyType) {
        this.sourceDataTypeName = sourceDataTypeName;
        this.sourceDataPropertyName = sourceDataPropertyName;
        this.sourcePrimaryKey = sourcePrimaryKey;
        this.targetDataPropertyType = targetDataPropertyType;
        this.targetDataPropertyName = targetDataPropertyName;
        this.targetPrimaryKey = targetPrimaryKey;
        this.relationTypeName = relationTypeName;
        this.propertyType = propertyType;
    }

    public String getSourceDataTypeName() {
        return sourceDataTypeName;
    }

    public String getSourceDataPropertyName() {
        return sourceDataPropertyName;
    }

    public String getTargetDataPropertyType() {
        return targetDataPropertyType;
    }

    public String getTargetDataPropertyName() {
        return targetDataPropertyName;
    }

    public String getRelationTypeName() {
        return relationTypeName;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public String getSourcePrimaryKey() {
        return sourcePrimaryKey;
    }

    public String getTargetPrimaryKey() {
        return targetPrimaryKey;
    }
}
