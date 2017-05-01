package com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau;

import com.infoDiscover.infoDiscoverEngine.dataMart.*;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;

import java.util.List;
import java.util.Map;

public interface InfoDiscoverSpace {
    public Fact addFact(Fact fact) throws InfoDiscoveryEngineRuntimeException;
    public boolean removeFact(String factId)throws InfoDiscoveryEngineRuntimeException;
    public Fact getFactById(String factId) throws InfoDiscoveryEngineRuntimeException;

    public Dimension addDimension(Dimension dimension) throws InfoDiscoveryEngineRuntimeException;
    public boolean removeDimension(String dimensionId) throws InfoDiscoveryEngineRuntimeException;
    public Dimension getDimensionById(String dimensionId)throws InfoDiscoveryEngineRuntimeException;

    public boolean hasFactType(String factType);
    public FactType getFactType(String typeName);
    public FactType addFactType(String factType) throws InfoDiscoveryEngineDataMartException;
    public boolean removeFactType(String factType)throws InfoDiscoveryEngineDataMartException;
    public List<String> getFactTypesList();

    public boolean hasDimensionType(String typeName);
    public DimensionType getDimensionType(String typeName);
    public DimensionType addDimensionType(String typeName) throws InfoDiscoveryEngineDataMartException;
    public boolean removeDimensionType(String typeName)throws InfoDiscoveryEngineDataMartException;
    public DimensionType addChildDimensionType(String childTypeName,String parentTypeName) throws InfoDiscoveryEngineDataMartException;
    public List<String> getRootDimensionTypesList();

    public boolean hasRelationType(String typeName);
    public RelationType getRelationType(String typeName);
    public RelationType addRelationType(String typeName) throws InfoDiscoveryEngineDataMartException;
    public boolean removeRelationType(String typeName)throws InfoDiscoveryEngineDataMartException;
    public RelationType addChildRelationType(String childTypeName,String parentTypeName) throws InfoDiscoveryEngineDataMartException;
    public List<String> getRootRelationTypesList();

    public Relation addDirectionalFactRelation(Fact fromFact,Fact toFact,String relationType,boolean repeatable)throws InfoDiscoveryEngineRuntimeException;
    public Relation addDirectionalFactRelation(Fact fromFact,Fact toFact,String relationType,boolean repeatable,Map<String,Object> initRelationProperties)throws InfoDiscoveryEngineRuntimeException;
    public Relation getRelationById(String relationId);
    public boolean removeRelation(String relationId) throws InfoDiscoveryEngineRuntimeException;

    public Relation addDirectionalDimensionRelation(Dimension fromDimension,Dimension toDimension,String relationType, boolean repeatable)throws InfoDiscoveryEngineRuntimeException;
    public Relation attachFactToDimension(String factId,String dimensionId,String relationType)throws InfoDiscoveryEngineRuntimeException;
    public Relation attachFactToDimension(String factId,String dimensionId,String relationType,Map<String,Object> initRelationProperties)throws InfoDiscoveryEngineRuntimeException;
    public Relation connectDimensionWithFact(String dimensionId,String factId,String relationType)throws InfoDiscoveryEngineRuntimeException;
    public Relation connectDimensionWithFact(String dimensionId,String factId,String relationType,Map<String,Object> initRelationProperties)throws InfoDiscoveryEngineRuntimeException;

    public String getSpaceName();
    public void closeSpace();
    public InformationExplorer getInformationExplorer();

    public Measurable getMeasurableById(String measurableId);
}
