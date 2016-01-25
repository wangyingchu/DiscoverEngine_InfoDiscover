package com.viewfnction.infoDiscoverEngine.util.helper;

import com.viewfnction.infoDiscoverEngine.dataMart.Measurable;
import com.viewfnction.infoDiscoverEngine.dataMart.Property;
import com.viewfnction.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;

public interface MeasurableContentHelper {
    public void retrievePropertyBinaryContent(Property binaryProperty, String contentLocation,String contentFileName) throws InfoDiscoveryEngineRuntimeException;
    public void persistBinaryContentToProperty(Measurable targetMeasurable,String propertyName,String contentFullLocation)throws InfoDiscoveryEngineRuntimeException;
    public void updateBinaryContentOfProperty(Measurable targetMeasurable,String propertyName,String contentFullLocation)throws InfoDiscoveryEngineRuntimeException;
    public byte[] retrieveBinaryPropertyContent(Property binaryProperty)throws InfoDiscoveryEngineRuntimeException;
}
