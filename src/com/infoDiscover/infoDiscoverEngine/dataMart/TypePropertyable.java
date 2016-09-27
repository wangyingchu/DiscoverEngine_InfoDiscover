package com.infoDiscover.infoDiscoverEngine.dataMart;

import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;

import java.util.List;

public interface TypePropertyable {
    public TypeProperty addTypeProperty(String propertyName,PropertyType propertyType) throws InfoDiscoveryEngineRuntimeException;
    public boolean removeTypeProperty(String propertyName) throws InfoDiscoveryEngineRuntimeException;
    public TypeProperty getTypePropertyType(String propertyName) throws InfoDiscoveryEngineRuntimeException;
    public List<String> getTypePropertyNames();
    public List<TypeProperty> getTypeProperties();
    public boolean hasTypeProperty(String propertyName);
}
