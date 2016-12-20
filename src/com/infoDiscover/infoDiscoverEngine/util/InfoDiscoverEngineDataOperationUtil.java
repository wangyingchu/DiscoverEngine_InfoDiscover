package com.infoDiscover.infoDiscoverEngine.util;

import com.infoDiscover.infoDiscoverEngine.dataMart.Property;
import com.infoDiscover.infoDiscoverEngine.dataMart.PropertyType;
import com.infoDiscover.infoDiscoverEngine.dataMartImpl.OrientDBPropertyImpl;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ORecordBytes;
import com.tinkerpop.blueprints.impls.orient.OrientEdge;

import java.util.Date;

/**
 * Created by wangychu on 12/20/16.
 */
public class InfoDiscoverEngineDataOperationUtil {

    public static boolean checkNotReservedProperty(String propertyName){
        if(!propertyName.equals(InfoDiscoverEngineConstant.PROPRETY_RESERVENAME_RID)&&
                !propertyName.equals(InfoDiscoverEngineConstant.PROPRETY_RESERVENAME_CLASS)&&
                !propertyName.equals(InfoDiscoverEngineConstant.PROPRETY_RESERVENAME_IN)&&
                !propertyName.equals(InfoDiscoverEngineConstant.PROPRETY_RESERVENAME_OUT)&&
                !propertyName.startsWith(InfoDiscoverEngineConstant.PROPRETY_RESERVENAME_IN+"_")&&
                !propertyName.startsWith(InfoDiscoverEngineConstant.PROPRETY_RESERVENAME_OUT+"_")){
            return true;
        }else{
            return false;
        }
    }

    public static void saveOrientPropertyWithoutCommit(OrientEdge targetEdge, String propertyName, Object propertyValue){
        if(propertyValue instanceof Boolean){
            targetEdge.setProperty(propertyName, propertyValue, OType.BOOLEAN);
        }
        if(propertyValue instanceof Integer){
            targetEdge.setProperty(propertyName, propertyValue, OType.INTEGER);
        }
        if(propertyValue instanceof Short){
            targetEdge.setProperty(propertyName, propertyValue, OType.SHORT);
        }
        if(propertyValue instanceof Long){
            targetEdge.setProperty(propertyName, propertyValue, OType.LONG);
        }
        if(propertyValue instanceof Float){
            targetEdge.setProperty(propertyName, propertyValue, OType.FLOAT);
        }
        if(propertyValue instanceof Double){
            targetEdge.setProperty(propertyName, propertyValue, OType.DOUBLE);
        }
        if(propertyValue instanceof Date){
            targetEdge.setProperty(propertyName, propertyValue, OType.DATETIME);
        }
        if(propertyValue instanceof String){
            targetEdge.setProperty(propertyName, propertyValue, OType.STRING);
        }
        if(propertyValue instanceof Byte[]){
            byte[] buffer =(byte[])propertyValue;
            ORecordBytes binaryValue = new ORecordBytes(buffer);
            targetEdge.setProperty(propertyName, binaryValue);
        }
        if(propertyValue instanceof byte[]){
            byte[] buffer =(byte[])propertyValue;
            ORecordBytes binaryValue = new ORecordBytes(buffer);
            targetEdge.setProperty(propertyName, binaryValue);
        }
        if(propertyValue instanceof Byte){
            targetEdge.setProperty(propertyName, propertyValue, OType.BYTE);
        }
    }

    public static void checkIfReservedProperty(String propertyName)throws InfoDiscoveryEngineRuntimeException {
        if(propertyName.equals(InfoDiscoverEngineConstant.PROPRETY_RESERVENAME_RID)||
                propertyName.equals(InfoDiscoverEngineConstant.PROPRETY_RESERVENAME_CLASS)||
                propertyName.equals(InfoDiscoverEngineConstant.PROPRETY_RESERVENAME_IN)||
                propertyName.equals(InfoDiscoverEngineConstant.PROPRETY_RESERVENAME_OUT)){
            String exceptionMessage = "Name "+propertyName+" is a system reserved Property Name";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }
        if(propertyName.startsWith(InfoDiscoverEngineConstant.PROPRETY_RESERVENAME_IN + "_")){
            String exceptionMessage = "Property Name can not start with "+InfoDiscoverEngineConstant.PROPRETY_RESERVENAME_IN+"_";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }
        if(propertyName.startsWith(InfoDiscoverEngineConstant.PROPRETY_RESERVENAME_OUT+"_")){
            String exceptionMessage = "Property Name can not start with "+InfoDiscoverEngineConstant.PROPRETY_RESERVENAME_OUT+"_";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }
    }

    public static Property generateProperty(String propertyName, Object propertyValue){
        OrientDBPropertyImpl targetProperty=new OrientDBPropertyImpl();
        targetProperty.setPropertyName(propertyName);
        targetProperty.setPropertyValue(propertyValue);
        if(propertyValue instanceof Boolean){
            targetProperty.setPropertyType(PropertyType.BOOLEAN);
        }
        if(propertyValue instanceof Integer){
            targetProperty.setPropertyType(PropertyType.INT);
        }
        if(propertyValue instanceof Short){
            targetProperty.setPropertyType(PropertyType.SHORT);
        }
        if(propertyValue instanceof Long){
            targetProperty.setPropertyType(PropertyType.LONG);
        }
        if(propertyValue instanceof Float){
            targetProperty.setPropertyType(PropertyType.FLOAT);
        }
        if(propertyValue instanceof Double){
            targetProperty.setPropertyType(PropertyType.DOUBLE);
        }
        if(propertyValue instanceof Date){
            targetProperty.setPropertyType(PropertyType.DATE);
        }
        if(propertyValue instanceof String){
            targetProperty.setPropertyType(PropertyType.STRING);
        }
        if(propertyValue instanceof ORecordBytes){
            targetProperty.setPropertyType(PropertyType.BINARY);
        }
        if(propertyValue instanceof Byte){
            targetProperty.setPropertyType(PropertyType.BYTE);
        }
        if(propertyValue instanceof byte[]){
            targetProperty.setPropertyType(PropertyType.BINARY);
        }
        return targetProperty;
    }
}
