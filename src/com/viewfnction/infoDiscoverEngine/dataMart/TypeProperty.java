package com.viewfnction.infoDiscoverEngine.dataMart;

public interface TypeProperty {
    public String getPropertyName();
    public PropertyType getPropertyType();
    public boolean isMandatory();
    public boolean isReadOnly();
    public boolean isNullable();
    public void setMandatory(boolean value);
    public void setReadOnly(boolean value);
    public void setNullable(boolean value);
}
