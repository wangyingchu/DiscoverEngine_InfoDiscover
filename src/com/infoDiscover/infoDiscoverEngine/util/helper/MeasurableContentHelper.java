package com.infoDiscover.infoDiscoverEngine.util.helper;

import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.dataMart.Measurable;
import com.infoDiscover.infoDiscoverEngine.dataMart.Property;

public interface MeasurableContentHelper {
    public void retrievePropertyBinaryContent(Property binaryProperty, String contentLocation,String contentFileName) throws InfoDiscoveryEngineRuntimeException;
    public void persistBinaryContentToProperty(Measurable targetMeasurable,String propertyName,String contentFullLocation)throws InfoDiscoveryEngineRuntimeException;
    public void updateBinaryContentOfProperty(Measurable targetMeasurable,String propertyName,String contentFullLocation)throws InfoDiscoveryEngineRuntimeException;
    public byte[] retrieveBinaryPropertyContent(Property binaryProperty)throws InfoDiscoveryEngineRuntimeException;
}
