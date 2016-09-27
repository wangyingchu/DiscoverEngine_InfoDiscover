package com.infoDiscover.infoDiscoverEngine.dataMartImpl;

import com.infoDiscover.infoDiscoverEngine.dataMart.PropertyType;
import com.infoDiscover.infoDiscoverEngine.dataMart.TypeProperty;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.tinkerpop.blueprints.impls.orient.OrientElementType;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.infoDiscover.infoDiscoverEngine.dataMart.TypePropertyable;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;

import java.util.*;

public class OrientDBTypePropertyableImpl implements TypePropertyable {
    private OrientElementType orientElementType;
    private OrientGraph graph;

    public OrientDBTypePropertyableImpl(OrientElementType orientElementType,OrientGraph graph){
        this.orientElementType=orientElementType;
        this.graph=graph;
    }

    @Override
    public TypeProperty addTypeProperty(String propertyName, PropertyType propertyType) throws InfoDiscoveryEngineRuntimeException {
        if(hasTypeProperty(propertyName)){
            String exceptionMessage = "Type Property "+propertyName+" of type "+getCurrentTypeName()+" already exists";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }else{
            this.orientElementType.createProperty(propertyName,convertPropertyTypeToOType(propertyType));
            OrientDBTypePropertyImpl targetProperty=new OrientDBTypePropertyImpl(propertyName,this.orientElementType,this.graph);
            return targetProperty;
        }
    }

    @Override
    public boolean removeTypeProperty(String propertyName) throws InfoDiscoveryEngineRuntimeException {
        String exceptionMessage = "Remove type property from type is not support yet,p lease use InfoDiscoverAdminSpace" +
                " to execute this operation";
        throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
    }

    @Override
    public TypeProperty getTypePropertyType(String propertyName) throws InfoDiscoveryEngineRuntimeException {
        OProperty targetOProperty=this.orientElementType.getProperty(propertyName);
        if(targetOProperty==null){
            String exceptionMessage = "Type Property "+propertyName+" of type "+getCurrentTypeName()+" not exists";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }else{
            OrientDBTypePropertyImpl targetProperty=new OrientDBTypePropertyImpl(propertyName,this.orientElementType,this.graph);
            return targetProperty;
        }
    }

    @Override
    public List<String> getTypePropertyNames() {
        Map<String,OProperty> propertysMap=this.orientElementType.propertiesMap();
        List<String> propertyNameList=new ArrayList<String>();
        Iterator<String> propertyNames=propertysMap.keySet().iterator();
        while(propertyNames.hasNext()){
            propertyNameList.add(propertyNames.next());
        }
        return propertyNameList;
    }

    @Override
    public List<TypeProperty> getTypeProperties() {
        List<TypeProperty> propertyList=new ArrayList<TypeProperty>();
        Collection<OProperty> oPropertiesCollection=this.orientElementType.properties();
        Iterator<OProperty> oPropertyIterator= oPropertiesCollection.iterator();
        while(oPropertyIterator.hasNext()){
            OProperty currentOProperty= oPropertyIterator.next();
            OrientDBTypePropertyImpl targetProperty=new OrientDBTypePropertyImpl(currentOProperty.getName(),this.orientElementType,this.graph);
            propertyList.add(targetProperty);
        }
        return propertyList;
    }

    @Override
    public boolean hasTypeProperty(String propertyName) {
        OProperty targetProperty=this.orientElementType.getProperty(propertyName);
        if(targetProperty!=null){
            return true;
        }else{
            return false;
        }
    }

    private OType convertPropertyTypeToOType(PropertyType propertyType) {
        OType oType=null;
        switch(propertyType){
            case BOOLEAN:oType=OType.BOOLEAN;break;
            case INT:oType=OType.INTEGER;break;
            case SHORT:oType=OType.SHORT;break;
            case LONG:oType=OType.LONG;break;
            case FLOAT:oType=OType.FLOAT;break;
            case DOUBLE:oType=OType.DOUBLE;break;
            case DATE:oType=OType.DATETIME;break;
            case STRING:oType=OType.STRING;break;
            case BYTE:oType=OType.BYTE;break;
            case BINARY:oType=OType.BINARY;break;
        }
        return oType;
    }

    private String getCurrentTypeName(){
        String typeName=this.orientElementType.getName();
        if(typeName.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_FACT)){
            return typeName.replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_FACT,"");
        }
        if(typeName.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION)){
            return typeName.replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION,"");
        }
        if(typeName.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_RELATION)){
            return typeName.replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_RELATION,"");
        }
        return "UNKNOWN";
    }
}
