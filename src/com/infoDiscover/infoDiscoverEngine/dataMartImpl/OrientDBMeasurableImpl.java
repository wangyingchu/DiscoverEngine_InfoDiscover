package com.infoDiscover.infoDiscoverEngine.dataMartImpl;

import com.infoDiscover.infoDiscoverEngine.dataMart.PropertyType;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineDataOperationUtil;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ORecordBytes;
import com.tinkerpop.blueprints.impls.orient.OrientElement;
import com.infoDiscover.infoDiscoverEngine.dataMart.Measurable;
import com.infoDiscover.infoDiscoverEngine.dataMart.Property;

import java.util.*;

public class OrientDBMeasurableImpl implements Measurable {

    private OrientElement measureElement;

    private Map<String, Object> initProperties;

    public OrientDBMeasurableImpl() {
        this.initProperties = new HashMap<String, Object>();
    }

    @Override
    public boolean removeProperty(String propertyName) throws InfoDiscoveryEngineRuntimeException {
        InfoDiscoverEngineDataOperationUtil.checkIfReservedProperty(propertyName);
        Object removedObjectId = this.measureElement.removeProperty(propertyName);
        if (removedObjectId != null) {
            this.measureElement.getGraph().commit();
            return true;
        } else {
            String exceptionMessage = "Property " + propertyName + " not exist";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }
    }

    @Override
    public List<Property> getProperties() {
        List<Property> propertiesList = new ArrayList<Property>();
        Map<String, Object> propertiesMap = this.measureElement.getProperties();
        Set<String> propertyNameSet = propertiesMap.keySet();
        Iterator<String> iterator = propertyNameSet.iterator();
        while (iterator.hasNext()) {
            String propertyName = iterator.next();
            if (InfoDiscoverEngineDataOperationUtil.checkNotReservedProperty(propertyName)) {
                Object propertyValue = propertiesMap.get(propertyName);
                propertiesList.add(InfoDiscoverEngineDataOperationUtil.generateProperty
                        (propertyName, propertyValue));
            }
        }
        return propertiesList;
    }

    @Override
    public boolean hasProperty(String propertyName) {
        Set<String> propertySet = this.measureElement.getPropertyKeys();
        return propertySet.contains(propertyName);
    }

    @Override
    public List<String> getPropertyNames() {
        Set<String> propertySet = this.measureElement.getPropertyKeys();
        List<String> propertyList = new ArrayList<String>();
        propertyList.addAll(propertySet);
        return propertyList;
    }

    @Override
    public Property getProperty(String propertyName) {
        Object propertyValue = this.measureElement.getProperty(propertyName);
        if (propertyValue == null) {
            return null;
        } else {
            return InfoDiscoverEngineDataOperationUtil.generateProperty(propertyName,
                    propertyValue);
        }
    }

    @Override
    public Property addProperty(String propertyName, boolean propertyValue) throws
            InfoDiscoveryEngineRuntimeException {
        return setOrientVertexProperty(propertyName, propertyValue, PropertyType.BOOLEAN, true);
    }

    @Override
    public Property addProperty(String propertyName, int propertyValue) throws
            InfoDiscoveryEngineRuntimeException {
        return setOrientVertexProperty(propertyName, propertyValue, PropertyType.INT, true);
    }

    @Override
    public Property addProperty(String propertyName, short propertyValue) throws
            InfoDiscoveryEngineRuntimeException {
        return setOrientVertexProperty(propertyName, propertyValue, PropertyType.SHORT, true);
    }

    @Override
    public Property addProperty(String propertyName, long propertyValue) throws
            InfoDiscoveryEngineRuntimeException {
        return setOrientVertexProperty(propertyName, propertyValue, PropertyType.LONG, true);
    }

    @Override
    public Property addProperty(String propertyName, float propertyValue) throws
            InfoDiscoveryEngineRuntimeException {
        return setOrientVertexProperty(propertyName, propertyValue, PropertyType.FLOAT, true);
    }

    @Override
    public Property addProperty(String propertyName, double propertyValue) throws
            InfoDiscoveryEngineRuntimeException {
        return setOrientVertexProperty(propertyName, propertyValue, PropertyType.DOUBLE, true);
    }

    @Override
    public Property addProperty(String propertyName, Date propertyValue) throws
            InfoDiscoveryEngineRuntimeException {
        return setOrientVertexProperty(propertyName, propertyValue, PropertyType.DATE, true);
    }

    @Override
    public Property addProperty(String propertyName, String propertyValue) throws
            InfoDiscoveryEngineRuntimeException {
        return setOrientVertexProperty(propertyName, propertyValue, PropertyType.STRING, true);
    }

    @Override
    public Property addProperty(String propertyName, byte[] propertyValue) throws
            InfoDiscoveryEngineRuntimeException {
        return setOrientVertexProperty(propertyName, propertyValue, PropertyType.BINARY, true);
    }

    @Override
    public Property addProperty(String propertyName, byte propertyValue) throws
            InfoDiscoveryEngineRuntimeException {
        return setOrientVertexProperty(propertyName, propertyValue, PropertyType.BYTE, true);
    }

    @Override
    public Property updateProperty(String propertyName, boolean propertyValue) throws
            InfoDiscoveryEngineRuntimeException {
        validatePropertyInfo(propertyName, PropertyType.BOOLEAN);
        return setOrientVertexProperty(propertyName, propertyValue, PropertyType.BOOLEAN, false);
    }

    @Override
    public Property updateProperty(String propertyName, int propertyValue) throws
            InfoDiscoveryEngineRuntimeException {
        validatePropertyInfo(propertyName, PropertyType.INT);
        return setOrientVertexProperty(propertyName, propertyValue, PropertyType.INT, false);
    }

    @Override
    public Property updateProperty(String propertyName, short propertyValue) throws
            InfoDiscoveryEngineRuntimeException {
        validatePropertyInfo(propertyName, PropertyType.SHORT);
        return setOrientVertexProperty(propertyName, propertyValue, PropertyType.SHORT, false);
    }

    @Override
    public Property updateProperty(String propertyName, long propertyValue) throws
            InfoDiscoveryEngineRuntimeException {
        validatePropertyInfo(propertyName, PropertyType.LONG);
        return setOrientVertexProperty(propertyName, propertyValue, PropertyType.LONG, false);
    }

    @Override
    public Property updateProperty(String propertyName, float propertyValue) throws
            InfoDiscoveryEngineRuntimeException {
        validatePropertyInfo(propertyName, PropertyType.FLOAT);
        return setOrientVertexProperty(propertyName, propertyValue, PropertyType.FLOAT, false);
    }

    @Override
    public Property updateProperty(String propertyName, double propertyValue) throws
            InfoDiscoveryEngineRuntimeException {
        validatePropertyInfo(propertyName, PropertyType.DOUBLE);
        return setOrientVertexProperty(propertyName, propertyValue, PropertyType.DOUBLE, false);
    }

    @Override
    public Property updateProperty(String propertyName, Date propertyValue) throws
            InfoDiscoveryEngineRuntimeException {
        validatePropertyInfo(propertyName, PropertyType.DATE);
        return setOrientVertexProperty(propertyName, propertyValue, PropertyType.DATE, false);
    }

    @Override
    public Property updateProperty(String propertyName, String propertyValue) throws
            InfoDiscoveryEngineRuntimeException {
        validatePropertyInfo(propertyName, PropertyType.STRING);
        return setOrientVertexProperty(propertyName, propertyValue, PropertyType.STRING, false);
    }

    @Override
    public Property updateProperty(String propertyName, byte[] propertyValue) throws
            InfoDiscoveryEngineRuntimeException {
        validatePropertyInfo(propertyName, PropertyType.BINARY);
        return setOrientVertexProperty(propertyName, propertyValue, PropertyType.BINARY, false);
    }

    @Override
    public Property updateProperty(String propertyName, byte propertyValue) throws
            InfoDiscoveryEngineRuntimeException {
        validatePropertyInfo(propertyName, PropertyType.BYTE);
        return setOrientVertexProperty(propertyName, propertyValue, PropertyType.BYTE, false);
    }

    @Override
    public List<String> addProperties(Map<String, Object> properties) {
        if (properties == null) {
            return null;
        }
        List<String> addSuccessPropertyNameList = new ArrayList<String>();
        Set<String> propertyNameSet = properties.keySet();
        Iterator<String> iterator = propertyNameSet.iterator();
        while (iterator.hasNext()) {
            String propertyName = iterator.next();
            if (InfoDiscoverEngineDataOperationUtil.checkNotReservedProperty(propertyName)) {
                if (!hasProperty(propertyName)) {
                    Object propertyValue = properties.get(propertyName);
                    saveOrientPropertyWithoutCommit(propertyName, propertyValue);
                    addSuccessPropertyNameList.add(propertyName);
                }
            }
        }
        this.measureElement.getGraph().commit();
        return addSuccessPropertyNameList;
    }

    @Override
    public List<String> updateProperties(Map<String, Object> properties) {
        if (properties == null) {
            return null;
        }
        List<String> updateSuccessPropertyNameList = new ArrayList<String>();
        Set<String> propertyNameSet = properties.keySet();
        Iterator<String> iterator = propertyNameSet.iterator();
        while (iterator.hasNext()) {
            String propertyName = iterator.next();
            if (InfoDiscoverEngineDataOperationUtil.checkNotReservedProperty(propertyName)) {
                if (hasProperty(propertyName)) {
                    Object propertyValue = properties.get(propertyName);
                    if (checkIfDataTypeMatch(propertyName, propertyValue)) {
                        saveOrientPropertyWithoutCommit(propertyName, propertyValue);
                        updateSuccessPropertyNameList.add(propertyName);
                    }
                }
            }
        }
        this.measureElement.getGraph().commit();
        return updateSuccessPropertyNameList;
    }

    @Override
    public List<String> addNewOrUpdateProperties(Map<String, Object> properties) {
        if (properties == null) {
            return null;
        }

        List<String> successPropertyNameList = new ArrayList<String>();
        Set<String> propertyNameSet = properties.keySet();
        Iterator<String> iterator = propertyNameSet.iterator();
        while(iterator.hasNext()) {
            String propertyName = iterator.next();
            if (InfoDiscoverEngineDataOperationUtil.checkNotReservedProperty(propertyName)) {
                Object propertyValue = properties.get(propertyName);
                if (!hasProperty(propertyName)) {
                    saveOrientPropertyWithoutCommit(propertyName, propertyValue);
                    successPropertyNameList.add(propertyName);
                } else {
                    if (checkIfDataTypeMatch(propertyName, propertyValue)) {
                        saveOrientPropertyWithoutCommit(propertyName, propertyValue);
                        successPropertyNameList.add(propertyName);
                    }
                }
            }

        }

        this.measureElement.getGraph().commit();
        return successPropertyNameList;
    }

    @Override
    public boolean setInitProperty(String propertyName, boolean propertyValue) {
        return recordInitProperty(propertyName, propertyValue);
    }

    @Override
    public boolean setInitProperty(String propertyName, int propertyValue) {
        return recordInitProperty(propertyName, propertyValue);
    }

    @Override
    public boolean setInitProperty(String propertyName, short propertyValue) {
        return recordInitProperty(propertyName, propertyValue);
    }

    @Override
    public boolean setInitProperty(String propertyName, long propertyValue) {
        return recordInitProperty(propertyName, propertyValue);
    }

    @Override
    public boolean setInitProperty(String propertyName, float propertyValue) {
        return recordInitProperty(propertyName, propertyValue);
    }

    @Override
    public boolean setInitProperty(String propertyName, double propertyValue) {
        return recordInitProperty(propertyName, propertyValue);
    }

    @Override
    public boolean setInitProperty(String propertyName, Date propertyValue) {
        return recordInitProperty(propertyName, propertyValue);
    }

    @Override
    public boolean setInitProperty(String propertyName, String propertyValue) {
        return recordInitProperty(propertyName, propertyValue);
    }

    @Override
    public boolean setInitProperty(String propertyName, byte[] propertyValue) {
        return recordInitProperty(propertyName, propertyValue);
    }

    @Override
    public boolean setInitProperty(String propertyName, byte propertyValue) {
        return recordInitProperty(propertyName, propertyValue);
    }

    private boolean recordInitProperty(String propertyName, Object propertyValue) {
        if (this.initProperties.get(propertyName) != null) {
            return false;
        }
        this.initProperties.put(propertyName, propertyValue);
        return true;
    }

    public Map<String, Object> getInitProperties() {
        if (this.initProperties.isEmpty()) {
            return null;
        } else {
            return this.initProperties;
        }
    }

    private void validatePropertyInfo(String propertyName, PropertyType propertyType) throws
            InfoDiscoveryEngineRuntimeException {
        InfoDiscoverEngineDataOperationUtil.checkIfReservedProperty(propertyName);
        if (!hasProperty(propertyName)) {
            String exceptionMessage = "Property " + propertyName + " not exist";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        } else {
            Property currentProperty = getProperty(propertyName);
            if (!currentProperty.getPropertyType().equals(propertyType)) {
                String exceptionMessage = "Property " + propertyName + "'s type isn't " +
                        propertyType.toString();
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }
        }
    }

    private Property setOrientVertexProperty(String propertyName, Object propertyValue,
                                             PropertyType propertyType, boolean checkExistence)
            throws InfoDiscoveryEngineRuntimeException {
        InfoDiscoverEngineDataOperationUtil.checkIfReservedProperty(propertyName);
        if (checkExistence) {
            if (this.measureElement.getProperty(propertyName) != null) {
                String exceptionMessage = "Property " + propertyName + " already exists";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }
        }
        OrientDBPropertyImpl targetProperty = new OrientDBPropertyImpl();
        targetProperty.setPropertyName(propertyName);
        targetProperty.setPropertyType(propertyType);
        switch (propertyType) {
            case BOOLEAN:
                this.measureElement.setProperty(propertyName, propertyValue, OType.BOOLEAN);
                break;
            case INT:
                this.measureElement.setProperty(propertyName, propertyValue, OType.INTEGER);
                break;
            case SHORT:
                this.measureElement.setProperty(propertyName, propertyValue, OType.SHORT);
                break;
            case LONG:
                this.measureElement.setProperty(propertyName, propertyValue, OType.LONG);
                break;
            case FLOAT:
                this.measureElement.setProperty(propertyName, propertyValue, OType.FLOAT);
                break;
            case DOUBLE:
                this.measureElement.setProperty(propertyName, propertyValue, OType.DOUBLE);
                break;
            case DATE:
                this.measureElement.setProperty(propertyName, propertyValue, OType.DATETIME);
                break;
            case STRING:
                this.measureElement.setProperty(propertyName, propertyValue, OType.STRING);
                break;
            case BINARY:
                byte[] buffer = (byte[]) propertyValue;
                ORecordBytes binaryValue = new ORecordBytes(buffer);
                this.measureElement.setProperty(propertyName, binaryValue);
                break;
            case BYTE:
                this.measureElement.setProperty(propertyName, propertyValue, OType.BYTE);
                break;
        }
        this.measureElement.getGraph().commit();
        targetProperty.setPropertyValue(this.measureElement.getProperty(propertyName));
        return targetProperty;
    }

    private void saveOrientPropertyWithoutCommit(String propertyName, Object propertyValue) {
        if (propertyValue instanceof Boolean) {
            this.measureElement.setProperty(propertyName, propertyValue, OType.BOOLEAN);
        }
        if (propertyValue instanceof Integer) {
            this.measureElement.setProperty(propertyName, propertyValue, OType.INTEGER);
        }
        if (propertyValue instanceof Short) {
            this.measureElement.setProperty(propertyName, propertyValue, OType.SHORT);
        }
        if (propertyValue instanceof Long) {
            this.measureElement.setProperty(propertyName, propertyValue, OType.LONG);
        }
        if (propertyValue instanceof Float) {
            this.measureElement.setProperty(propertyName, propertyValue, OType.FLOAT);
        }
        if (propertyValue instanceof Double) {
            this.measureElement.setProperty(propertyName, propertyValue, OType.DOUBLE);
        }
        if (propertyValue instanceof Date) {
            this.measureElement.setProperty(propertyName, propertyValue, OType.DATETIME);
        }
        if (propertyValue instanceof String) {
            this.measureElement.setProperty(propertyName, propertyValue, OType.STRING);
        }
        if (propertyValue instanceof Byte[]) {
            byte[] buffer = (byte[]) propertyValue;
            ORecordBytes binaryValue = new ORecordBytes(buffer);
            this.measureElement.setProperty(propertyName, binaryValue);
        }
        if (propertyValue instanceof byte[]) {
            byte[] buffer = (byte[]) propertyValue;
            ORecordBytes binaryValue = new ORecordBytes(buffer);
            this.measureElement.setProperty(propertyName, binaryValue);
        }
        if (propertyValue instanceof Byte) {
            this.measureElement.setProperty(propertyName, propertyValue, OType.BYTE);
        }
    }

    private boolean checkIfDataTypeMatch(String propertyName, Object newDataValue) {
        Object propertyValue = this.measureElement.getProperty(propertyName);
        if (propertyValue instanceof Boolean) {
            return (newDataValue instanceof Boolean) ? true : false;
        } else if (propertyValue instanceof Integer) {
            return (newDataValue instanceof Integer) ? true : false;
        } else if (propertyValue instanceof Short) {
            return (newDataValue instanceof Short) ? true : false;
        } else if (propertyValue instanceof Long) {
            return (newDataValue instanceof Long) ? true : false;
        } else if (propertyValue instanceof Float) {
            return (newDataValue instanceof Float) ? true : false;
        } else if (propertyValue instanceof Double) {
            return (newDataValue instanceof Double) ? true : false;
        } else if (propertyValue instanceof Date) {
            return (newDataValue instanceof Date) ? true : false;
        } else if (propertyValue instanceof String) {
            return (newDataValue instanceof String) ? true : false;
        } else if (propertyValue instanceof ORecordBytes) {
            return (newDataValue instanceof byte[]) ? true : false;
        } else if (propertyValue instanceof Byte) {
            return (newDataValue instanceof Byte) ? true : false;
        } else {
            return false;
        }
    }

    protected void setMeasureElement(OrientElement measureElement) {
        this.measureElement = measureElement;
    }
}
