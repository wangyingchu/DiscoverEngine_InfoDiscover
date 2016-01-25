package com.viewfnction.infoDiscoverEngine.dataMart;

import com.viewfnction.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;

import java.util.List;

public interface Relationable extends Measurable{
    public String getId();

    public List<Relation> getAllRelations();
    public List<Relation> getSpecifiedRelations(String relationType,RelationDirection relationDirection) throws InfoDiscoveryEngineRuntimeException;
    public long getRelationCount(String relationType,RelationDirection relationDirection) throws InfoDiscoveryEngineRuntimeException;

    public Relation addFromRelation(Relationable targetRelationable,String relationType) throws InfoDiscoveryEngineRuntimeException;
    public Relation addToRelation(Relationable targetRelationable,String relationType) throws InfoDiscoveryEngineRuntimeException;

    public boolean removeRelation(String relationId) throws InfoDiscoveryEngineRuntimeException;
    public List<String> removeAllRelations();
    public List<String> removeSpecifiedRelations(String relationType,RelationDirection relationDirection) throws InfoDiscoveryEngineRuntimeException;
}
