package com.viewfnction.infoDiscoverEngine.infoDiscoverBureauImpl;

import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.tinkerpop.blueprints.impls.orient.OrientEdgeType;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;
import com.viewfnction.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverAdminSpace;
import com.viewfnction.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.viewfnction.infoDiscoverEngine.util.exception.InfoDiscoveryEngineException;
import com.viewfnction.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;

public class OrientDBInfoDiscoverAdminSpaceImpl implements InfoDiscoverAdminSpace {

    private OrientGraphNoTx graph;
    private String spaceName;

    public OrientDBInfoDiscoverAdminSpaceImpl(String spaceName,OrientGraphNoTx graph){
        this.spaceName=spaceName;
        this.graph=graph;
    }

    @Override
    public String getSpaceName() {
        return this.spaceName;
    }

    @Override
    public void closeSpace() {
        this.graph.shutdown();
    }

    @Override
    public boolean removeFactTypeProperty(String typeName,String propertyName) throws InfoDiscoveryEngineRuntimeException {
        String orientDBClassName= InfoDiscoverEngineConstant.CLASSPERFIX_FACT+typeName;
        OrientVertexType ovt=this.graph.getVertexType(orientDBClassName);
        if(ovt==null){
            String exceptionMessage = "Fact Type "+typeName+" not exists";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }else{
            OProperty targetProperty=ovt.getProperty(propertyName);
            if(targetProperty==null){
                String exceptionMessage = "Type Property "+propertyName+" of fact type "+typeName+" not exists";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }else{
                ovt.dropProperty(propertyName);
                return true;
            }
        }
    }

    @Override
    public boolean removeDimensionTypeProperty(String typeName,String propertyName) throws InfoDiscoveryEngineRuntimeException {
        String orientDBClassName= InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION+typeName;
        OrientVertexType ovt=this.graph.getVertexType(orientDBClassName);
        if(ovt==null){
            String exceptionMessage = "Dimension Type "+typeName+" not exists";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }else{
            OProperty targetProperty=ovt.getProperty(propertyName);
            if(targetProperty==null){
                String exceptionMessage = "Type Property "+propertyName+" of dimension type "+typeName+" not exists";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }else{
                ovt.dropProperty(propertyName);
                return true;
            }
        }
    }

    @Override
    public boolean removeRelationTypeProperty(String typeName,String propertyName) throws InfoDiscoveryEngineRuntimeException {
        String orientDBClassName= InfoDiscoverEngineConstant.CLASSPERFIX_RELATION+typeName;
        OrientEdgeType oet=this.graph.getEdgeType(orientDBClassName);
        if(oet==null){
            String exceptionMessage = "Relation Type "+typeName+" not exists";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }else{
            OProperty targetProperty=oet.getProperty(propertyName);
            if(targetProperty==null){
                String exceptionMessage = "Type Property "+propertyName+" of relation type "+typeName+" not exists";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }else{
                oet.dropProperty(propertyName);
                return true;
            }
        }
    }
}
