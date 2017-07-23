package com.infoDiscover.solution.builder.vo;

/**
 * Created by sun.
 */
public class RelationMappingVO {

    private String relationMappingType;

    private String sourceDataTypeKind;
    private String sourceDataTypeName;
    private String sourceDataPropertyName;
    private String sourceDataPropertyType;
    private String sourcePrimaryKey;

    private String targetDataTypeKind;
    private String targetDataTypeName;
    private String targetDataPropertyName;
    private String targetDataPropertyType;
    private String targetDataPropertyValue;
    private String targetPrimaryKey;

    private String relationTypeName;
    private String relationDirection;

    private String minValue;
    private String maxValue;

    private String mappingNotExistHandleMethod;

    private String propertyType;

    public RelationMappingVO() {}

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

    public String getSourceDataTypeKind() {
        return sourceDataTypeKind;
    }

    public void setSourceDataTypeKind(String sourceDataTypeKind) {
        this.sourceDataTypeKind = sourceDataTypeKind;
    }

    public String getSourceDataTypeName() {
        return sourceDataTypeName;
    }

    public void setSourceDataTypeName(String sourceDataTypeName) {
        this.sourceDataTypeName = sourceDataTypeName;
    }

    public String getSourceDataPropertyName() {
        return sourceDataPropertyName;
    }

    public void setSourceDataPropertyName(String sourceDataPropertyName) {
        this.sourceDataPropertyName = sourceDataPropertyName;
    }

    public String getSourceDataPropertyType() {
        return sourceDataPropertyType;
    }

    public void setSourceDataPropertyType(String sourceDataPropertyType) {
        this.sourceDataPropertyType = sourceDataPropertyType;
    }

    public String getSourcePrimaryKey() {
        return sourcePrimaryKey;
    }

    public void setSourcePrimaryKey(String sourcePrimaryKey) {
        this.sourcePrimaryKey = sourcePrimaryKey;
    }

    public String getTargetDataTypeKind() {
        return targetDataTypeKind;
    }

    public void setTargetDataTypeKind(String targetDataTypeKind) {
        this.targetDataTypeKind = targetDataTypeKind;
    }

    public String getTargetDataTypeName() {
        return targetDataTypeName;
    }

    public void setTargetDataTypeName(String targetDataTypeName) {
        this.targetDataTypeName = targetDataTypeName;
    }

    public String getTargetDataPropertyName() {
        return targetDataPropertyName;
    }

    public void setTargetDataPropertyName(String targetDataPropertyName) {
        this.targetDataPropertyName = targetDataPropertyName;
    }

    public String getTargetDataPropertyType() {
        return targetDataPropertyType;
    }

    public void setTargetDataPropertyType(String targetDataPropertyType) {
        this.targetDataPropertyType = targetDataPropertyType;
    }

    public String getTargetDataPropertyValue() {
        return targetDataPropertyValue;
    }

    public void setTargetDataPropertyValue(String targetDataPropertyValue) {
        this.targetDataPropertyValue = targetDataPropertyValue;
    }

    public String getTargetPrimaryKey() {
        return targetPrimaryKey;
    }

    public void setTargetPrimaryKey(String targetPrimaryKey) {
        this.targetPrimaryKey = targetPrimaryKey;
    }

    public String getRelationTypeName() {
        return relationTypeName;
    }

    public void setRelationTypeName(String relationTypeName) {
        this.relationTypeName = relationTypeName;
    }

    public String getRelationDirection() {
        return relationDirection;
    }

    public void setRelationDirection(String relationDirection) {
        this.relationDirection = relationDirection;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getMappingNotExistHandleMethod() {
        return mappingNotExistHandleMethod;
    }

    public void setMappingNotExistHandleMethod(String mappingNotExistHandleMethod) {
        this.mappingNotExistHandleMethod = mappingNotExistHandleMethod;
    }

    public String getRelationMappingType() {
        return relationMappingType;
    }

    public void setRelationMappingType(String relationMappingType) {
        this.relationMappingType = relationMappingType;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }
}
