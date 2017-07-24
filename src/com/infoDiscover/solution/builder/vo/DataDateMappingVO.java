package com.infoDiscover.solution.builder.vo;

/**
 * Created by sun on 7/24/17.
 */
public class DataDateMappingVO {

    private String relationMappingType;

    private String sourceDataTypeKind;
    private String sourceDataTypeName;
    private String sourceDataPropertyName;
    private String relationTypeName;
    private String relationDirection;
    private String dateDimensionTypePrefix;


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

    public String getDateDimensionTypePrefix() {
        return dateDimensionTypePrefix;
    }

    public void setDateDimensionTypePrefix(String dateDimensionTypePrefix) {
        this.dateDimensionTypePrefix = dateDimensionTypePrefix;
    }
}
