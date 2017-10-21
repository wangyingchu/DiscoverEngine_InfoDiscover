package com.businessExtension.arch.demo;

/**
 * Created by sun.
 */
public class RelationMapping {

    private String propertyName;
    private String propertyValue;
    private String classType;
    private DemoFactEnum type;

    public RelationMapping(String propertyName, String propertyValue, String classType, DemoFactEnum
            type) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
        this.classType = classType;
        this.type = type;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public String getClassType() {
        return classType;
    }

    public DemoFactEnum getType() {
        return type;
    }

}
