package com.infoDiscover.infoDiscoverEngine.dataMart;


import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface Measurable {
    public boolean removeProperty(String propertyName) throws InfoDiscoveryEngineRuntimeException;
    public List<Property> getProperties();
    public boolean hasProperty(String propertyName);
    public List<String> getPropertyNames();
    public Property getProperty(String propertyName);
    public Property addProperty(String propertyName,boolean propertyValue) throws InfoDiscoveryEngineRuntimeException;
    public Property addProperty(String propertyName,int propertyValue) throws InfoDiscoveryEngineRuntimeException;
    public Property addProperty(String propertyName,short propertyValue) throws InfoDiscoveryEngineRuntimeException;
    public Property addProperty(String propertyName,long propertyValue) throws InfoDiscoveryEngineRuntimeException;
    public Property addProperty(String propertyName,float propertyValue) throws InfoDiscoveryEngineRuntimeException;
    public Property addProperty(String propertyName,double propertyValue) throws InfoDiscoveryEngineRuntimeException;
    public Property addProperty(String propertyName,Date propertyValue) throws InfoDiscoveryEngineRuntimeException;
    public Property addProperty(String propertyName,String propertyValue) throws InfoDiscoveryEngineRuntimeException;
    public Property addProperty(String propertyName,byte[] propertyValue) throws InfoDiscoveryEngineRuntimeException;
    public Property addProperty(String propertyName,byte propertyValue) throws InfoDiscoveryEngineRuntimeException;

    public Property updateProperty(String propertyName,boolean propertyValue) throws InfoDiscoveryEngineRuntimeException;
    public Property updateProperty(String propertyName,int propertyValue) throws InfoDiscoveryEngineRuntimeException;
    public Property updateProperty(String propertyName,short propertyValue) throws InfoDiscoveryEngineRuntimeException;
    public Property updateProperty(String propertyName,long propertyValue) throws InfoDiscoveryEngineRuntimeException;
    public Property updateProperty(String propertyName,float propertyValue) throws InfoDiscoveryEngineRuntimeException;
    public Property updateProperty(String propertyName,double propertyValue) throws InfoDiscoveryEngineRuntimeException;
    public Property updateProperty(String propertyName,Date propertyValue) throws InfoDiscoveryEngineRuntimeException;
    public Property updateProperty(String propertyName,String propertyValue) throws InfoDiscoveryEngineRuntimeException;
    public Property updateProperty(String propertyName,byte[] propertyValue) throws InfoDiscoveryEngineRuntimeException;
    public Property updateProperty(String propertyName,byte propertyValue) throws InfoDiscoveryEngineRuntimeException;

    public List<String> addProperties(Map<String,Object> properties);
    public List<String> updateProperties(Map<String,Object> properties);

    public boolean setInitProperty(String propertyName,boolean propertyValue);
    public boolean setInitProperty(String propertyName,int propertyValue);
    public boolean setInitProperty(String propertyName,short propertyValue);
    public boolean setInitProperty(String propertyName,long propertyValue);
    public boolean setInitProperty(String propertyName,float propertyValue);
    public boolean setInitProperty(String propertyName,double propertyValue);
    public boolean setInitProperty(String propertyName,Date propertyValue);
    public boolean setInitProperty(String propertyName,String propertyValue);
    public boolean setInitProperty(String propertyName,byte[] propertyValue);
    public boolean setInitProperty(String propertyName,byte propertyValue);
}
