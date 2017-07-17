package com.infoDiscover.solution.template.POJO;

/**
 * Created by sun on 7/17/17.
 */
public class SolutionTypePropertyTypePOJO {
    private String propertySourceOwner;
    private String propertyTypeName;
    private boolean isReadOnly;
    private boolean isMandatory;
    private String propertyAliasName;
    private String propertyName;
    private String propertyType;
    private boolean isNullable;
    private String solutionName;
    private String propertyTypeKind;

    public String getPropertySourceOwner() {
        return propertySourceOwner;
    }

    public void setPropertySourceOwner(String propertySourceOwner) {
        this.propertySourceOwner = propertySourceOwner;
    }

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public void setPropertyTypeName(String propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public String getPropertyAliasName() {
        return propertyAliasName;
    }

    public void setPropertyAliasName(String propertyAliasName) {
        this.propertyAliasName = propertyAliasName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public void setNullable(boolean nullable) {
        isNullable = nullable;
    }

    public String getSolutionName() {
        return solutionName;
    }

    public void setSolutionName(String solutionName) {
        this.solutionName = solutionName;
    }

    public String getPropertyTypeKind() {
        return propertyTypeKind;
    }

    public void setPropertyTypeKind(String propertyTypeKind) {
        this.propertyTypeKind = propertyTypeKind;
    }
}
