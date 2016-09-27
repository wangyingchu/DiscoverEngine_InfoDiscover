package com.infoDiscover.infoDiscoverEngine.dataMartImpl;

import com.infoDiscover.infoDiscoverEngine.dataMart.DimensionType;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class OrientDBDimensionTypeImpl extends OrientDBTypePropertyableImpl implements DimensionType {
    private OrientVertexType orientVertexType;
    private OrientGraph graph;

    public OrientDBDimensionTypeImpl(OrientVertexType orientVertexType,OrientGraph graph){
        super(orientVertexType,graph);
        this.orientVertexType=orientVertexType;
        this.graph=graph;
    }

    @Override
    public List<DimensionType> getAncestorDimensionTypes() {
        if(this.orientVertexType!=null){
            Collection<OClass> ancestorClassCollection = this.orientVertexType.getAllSuperClasses();
            return getDimensionTypeList(ancestorClassCollection);
        }else {
            return null;
        }
    }

    @Override
    public List<DimensionType> getDescendantDimensionTypes() {
        if(this.orientVertexType!=null){
            Collection<OClass> descendantClassCollection = this.orientVertexType.getAllSubclasses();
            return getDimensionTypeList(descendantClassCollection);
        }else {
            return null;
        }
    }

    @Override
    public List<DimensionType> getChildDimensionTypes() {
        if(this.orientVertexType!=null){
            Collection<OClass> childClassCollection = this.orientVertexType.getSubclasses();
            return getDimensionTypeList(childClassCollection);
        }else {
            return null;
        }
    }

    @Override
    public DimensionType getParentDimensionType() {
        if(this.orientVertexType!=null){
            OrientVertexType parentOrientVertexType=this.orientVertexType.getSuperClass();
            if(parentOrientVertexType!=null&&
                    !parentOrientVertexType.getName().equals(InfoDiscoverEngineConstant.DIMENSION_ROOTCLASSNAME)&&
                    !parentOrientVertexType.getName().equals(InfoDiscoverEngineConstant.ROOTCLASS_NAME_V)){
                DimensionType parentType=new OrientDBDimensionTypeImpl(parentOrientVertexType,this.graph);
                return parentType;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    @Override
    public String getTypeName() {
        if(this.orientVertexType!=null){
            String orientDBVertexTypeName=this.orientVertexType.getName();
            String typeName=orientDBVertexTypeName.replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION,"");
            return typeName;
        }else{
            return null;
        }
    }

    private List<DimensionType> getDimensionTypeList(Collection<OClass> orientDBClassCollection){
        if(orientDBClassCollection!=null){
            List<DimensionType> dimensionTypeList=new ArrayList<DimensionType>();
            Iterator<OClass> itr=orientDBClassCollection.iterator();
            while(itr.hasNext()){
                OClass currentClass= itr.next();
                String className=currentClass.getName();
                if(!className.equals(InfoDiscoverEngineConstant.DIMENSION_ROOTCLASSNAME)&&
                        !className.equals(InfoDiscoverEngineConstant.ROOTCLASS_NAME_V)){
                    OrientVertexType currentOrientVertexType=this.graph.getVertexType(className);
                    DimensionType currentType=new OrientDBDimensionTypeImpl(currentOrientVertexType,this.graph);
                    dimensionTypeList.add(currentType);
                }
            }
            return dimensionTypeList;
        }else{
            return null;
        }
    }
}
