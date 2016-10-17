package com.infoDiscover.infoDiscoverEngine.dataMartImpl;

import com.infoDiscover.infoDiscoverEngine.dataMart.PropertyType;
import com.infoDiscover.infoDiscoverEngine.dataMart.TypeProperty;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.tinkerpop.blueprints.impls.orient.*;

public class OrientDBTypePropertyImpl implements TypeProperty {

    private String propertyName;
    private OrientElementType orientElementType;
    private  OrientGraph graph;

    public OrientDBTypePropertyImpl(String propertyName,OrientElementType orientElementType,OrientGraph graph){
        this.orientElementType=orientElementType;
        this.propertyName=propertyName;
        this.graph=graph;
    }

    @Override
    public String getPropertyName() {
        return this.propertyName;
    }

    @Override
    public PropertyType getPropertyType() {
        OProperty currentOProperty=this.orientElementType.getProperty(this.propertyName);
        OType propertyOrientType=currentOProperty.getType();
        PropertyType propertyType=null;
        switch(propertyOrientType){
            case BOOLEAN:propertyType=PropertyType.BOOLEAN;break;
            case INTEGER:propertyType=PropertyType.INT;break;
            case SHORT:propertyType=PropertyType.SHORT;break;
            case LONG:propertyType=PropertyType.LONG;break;
            case FLOAT:propertyType=PropertyType.FLOAT;break;
            case DOUBLE:propertyType=PropertyType.DOUBLE;break;
            case DATETIME:propertyType=PropertyType.DATE;break;
            case STRING:propertyType=PropertyType.STRING;break;
            case BYTE:propertyType=PropertyType.BYTE;break;
            case BINARY:propertyType=PropertyType.BINARY;break;
        }
        return propertyType;
    }

    @Override
    public boolean isMandatory() {
        return this.orientElementType.getProperty(getPropertyName()).isMandatory();
    }

    @Override
    public boolean isReadOnly() {
        return this.orientElementType.getProperty(getPropertyName()).isReadonly();
    }

    @Override
    public boolean isNullable() {
        return !this.orientElementType.getProperty(getPropertyName()).isNotNull();
    }

    @Override
    public void setMandatory(boolean value) {
        this.orientElementType.getProperty(getPropertyName()).setMandatory(value);
        this.graph.commit();
    }

    @Override
    public void setReadOnly(boolean value) {
        this.orientElementType.getProperty(getPropertyName()).setReadonly(value);
        this.graph.commit();
    }

    @Override
    public void setNullable(boolean value) {
        this.orientElementType.getProperty(getPropertyName()).setNotNull(!value);
        this.graph.commit();
    }

    @Override
    public String getPropertySourceOwner() {
        String sourceOwnerClassname=this.orientElementType.getProperty(getPropertyName()).getOwnerClass().getName();
        String sourceOwner=sourceOwnerClassname;
        if(sourceOwnerClassname.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_FACT)){
            sourceOwner=sourceOwnerClassname.replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_FACT,"");
        }
        if(sourceOwnerClassname.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION)){
            sourceOwner=sourceOwnerClassname.replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION,"");
        }
        if(sourceOwnerClassname.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_RELATION)){
            sourceOwner=sourceOwnerClassname.replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_RELATION,"");
        }
        return sourceOwner;
    }
}
