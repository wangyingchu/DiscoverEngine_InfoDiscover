package com.infoDiscover.solution.builder.vo;

/**
 * Created by sun.
 */
public class RelationMappingVO {
    private String fromType;
    private String fromProperty;
    private String fromPrimaryKey;
    private String toType;
    private String toProperty;
    private String toPrimaryKey;
    private String relationTypeName;
    private String propertyType;

    public RelationMappingVO(String fromType, String fromProperty, String fromPrimaryKey, String
            toType, String toProperty, String toPrimaryKey, String relationTypeName, String
            propertyType) {
        this.fromType = fromType;
        this.fromProperty = fromProperty;
        this.fromPrimaryKey = fromPrimaryKey;
        this.toType = toType;
        this.toProperty = toProperty;
        this.toPrimaryKey = toPrimaryKey;
        this.relationTypeName = relationTypeName;
        this.propertyType = propertyType;
    }

    public String getFromType() {
        return fromType;
    }

    public String getFromProperty() {
        return fromProperty;
    }

    public String getToType() {
        return toType;
    }

    public String getToProperty() {
        return toProperty;
    }

    public String getRelationTypeName() {
        return relationTypeName;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public String getFromPrimaryKey() {
        return fromPrimaryKey;
    }

    public String getToPrimaryKey() {
        return toPrimaryKey;
    }
}
