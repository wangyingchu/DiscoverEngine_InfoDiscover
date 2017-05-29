package com.infoDiscover.solution.builder.vo;

/**
 * Created by sun.
 */
public class RelationMappingVO {
    private String fromType;
    private String fromProperty;
    private String toType;
    private String toProperty;
    private String relationTypeName;
    private String propertyType;

    public RelationMappingVO(String fromType, String fromProperty, String toType, String
            toProperty, String relationTypeName, String propertyType) {
        this.fromType = fromType;
        this.fromProperty = fromProperty;
        this.toType = toType;
        this.toProperty = toProperty;
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
}
