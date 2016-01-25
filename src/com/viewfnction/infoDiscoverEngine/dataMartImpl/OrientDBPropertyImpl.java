package com.viewfnction.infoDiscoverEngine.dataMartImpl;

import com.viewfnction.infoDiscoverEngine.dataMart.Property;
import com.viewfnction.infoDiscoverEngine.dataMart.PropertyType;

public class OrientDBPropertyImpl implements Property {

    private String propertyName;
    private PropertyType propertyType;
    private Object propertyValue;

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    @Override
    public Object getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(Object propertyValue) {
        this.propertyValue = propertyValue;
    }
}
