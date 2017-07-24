package com.infoDiscover.solution.builder.vo;

/**
 * Created by sun on 7/24/17.
 */
public class DataDuplicateCopyMappingVO {

    private String relationMappingType;

    private String sourceDataTypeKind;
    private String sourceDataTypeName;
    private String sourceDataPropertyName;
    private String sourceDataPropertyType;
    private String targetDataTypeName;
    private String targetDataPropertyName;
    private String targetDataPropertyType;
    private String existingPropertyHandleMethod;

    public String getRelationMappingType() {
        return relationMappingType;
    }

    public void setRelationMappingType(String relationMappingType) {
        this.relationMappingType = relationMappingType;
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

    public String getExistingPropertyHandleMethod() {
        return existingPropertyHandleMethod;
    }

    public void setExistingPropertyHandleMethod(String existingPropertyHandleMethod) {
        this.existingPropertyHandleMethod = existingPropertyHandleMethod;
    }
}
