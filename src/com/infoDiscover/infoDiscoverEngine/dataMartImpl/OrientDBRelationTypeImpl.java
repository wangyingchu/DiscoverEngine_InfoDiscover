package com.infoDiscover.infoDiscoverEngine.dataMartImpl;

import com.infoDiscover.infoDiscoverEngine.dataMart.RelationType;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.impls.orient.OrientEdgeType;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class OrientDBRelationTypeImpl extends OrientDBTypePropertyableImpl implements RelationType {
    private OrientEdgeType orientEdgeType;
    private OrientGraph graph;

    public OrientDBRelationTypeImpl(OrientEdgeType orientEdgeType,OrientGraph graph){
        super(orientEdgeType,graph);
        this.orientEdgeType=orientEdgeType;
        this.graph=graph;
    }

    @Override
    public String getTypeName() {
        if(this.orientEdgeType!=null){
            String orientDBEdgeTypeName=this.orientEdgeType.getName();
            String typeName=orientDBEdgeTypeName.replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_RELATION,"");
            return typeName;
        }else{
            return null;
        }
    }

    @Override
    public RelationType getParentRelationType() {
        if(this.orientEdgeType!=null){
            OrientEdgeType parentOrientEdgeType=this.orientEdgeType.getSuperClass();
            if(parentOrientEdgeType!=null&&
                    !parentOrientEdgeType.getName().equals(InfoDiscoverEngineConstant.RELATION_ROOTCLASSNAME)&&
                    !parentOrientEdgeType.getName().equals(InfoDiscoverEngineConstant.ROOTCLASS_NAME_E)){
                RelationType parentRelationType=new OrientDBRelationTypeImpl(parentOrientEdgeType,this.graph);
                return parentRelationType;
            }

        }else{
            return null;
        }
        return null;
    }

    @Override
    public List<RelationType> getChildRelationTypes() {
        if(this.orientEdgeType!=null){
            Collection<OClass> childClassCollection = this.orientEdgeType.getSubclasses();
            return getRelationTypeList(childClassCollection);
        }else{
            return null;
        }
    }

    @Override
    public List<RelationType> getAncestorRelationTypes() {
        if(this.orientEdgeType!=null){
            Collection<OClass> childClassCollection = this.orientEdgeType.getAllSuperClasses();
            return getRelationTypeList(childClassCollection);
        }else{
            return null;
        }
    }

    @Override
    public List<RelationType> getDescendantRelationTypes() {
        if(this.orientEdgeType!=null){
            Collection<OClass> childClassCollection = this.orientEdgeType.getAllSubclasses();
            return getRelationTypeList(childClassCollection);
        }else{
            return null;
        }
    }

    @Override
    public long countContainedRelations(boolean includeDescendant) {
        if(this.orientEdgeType!=null){
            return this.orientEdgeType.count(includeDescendant);
        }else{
            return 0;
        }
    }

    @Override
    public Object removeContainedRelations() {
        String sql= "DELETE EDGE "+this.orientEdgeType.getName();
        Object deleteResult=graph.command(new OCommandSQL(sql)).execute();
        return deleteResult;
    }

    private List<RelationType> getRelationTypeList(Collection<OClass> orientDBClassCollection){
        if(orientDBClassCollection!=null){
            List<RelationType> relationTypeList=new ArrayList<RelationType>();
            Iterator<OClass> itr=orientDBClassCollection.iterator();
            while(itr.hasNext()){
                OClass currentClass= itr.next();
                String className=currentClass.getName();
                if(!className.equals(InfoDiscoverEngineConstant.RELATION_ROOTCLASSNAME)&&
                        !className.equals(InfoDiscoverEngineConstant.ROOTCLASS_NAME_E)){

                    OrientEdgeType currentOrientEdgeType=this.graph.getEdgeType(className);
                    RelationType currentType=new OrientDBRelationTypeImpl(currentOrientEdgeType,this.graph);
                    relationTypeList.add(currentType);
                }
            }
            return relationTypeList;
        }else{
            return null;
        }
    }
}
