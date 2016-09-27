package com.infoDiscover.infoDiscoverEngine.dataWarehouse;


import com.infoDiscover.infoDiscoverEngine.dataMart.*;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;

import java.util.List;

public interface InformationExplorer {

    public List<Fact> discoverFacts(ExploreParameters exploreParameters) throws InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException;
    public List<Dimension> discoverDimensions(ExploreParameters exploreParameters) throws InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException;
    public List<Relation> discoverRelations(ExploreParameters exploreParameters) throws InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException;
    public List<Relationable> discoverRelationables(String sql);
    public List<Relation> exploreRelationsByRelatedRelationables(RelationDirection relationDirection, List<String> relationableIds, ExploreParameters exploreParameters) throws InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException;
    public List<Fact> exploreFactsByRelatedRelationTypes(List<RelationTypeFilter> relationTypeFilters, ExploreParameters exploreParameters) throws InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException;
    public List<Fact> exploreFactsByRelatedDimensions(List<DimensionsFilter> dimensionsFilters,ExploreParameters exploreParameters) throws InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException;
    public List<Fact> exploreFactsByRelations(RelationDirection relationDirection, List<String> relationIds, ExploreParameters exploreParameter)throws InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException;
    public List<Dimension> exploreDimensionsByRelations(RelationDirection relationDirection, List<String> relationIds, ExploreParameters exploreParameters)throws InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException;
    public List<Dimension> exploreDimensionsByRelatedFacts(RelationDirection relationDirection, List<String> factIds, ExploreParameters exploreParameters) throws InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException;
    public List<Fact> exploreSimilarFactsByRelatedDimensions(RelationDirection relationDirection,String factId,String relationType,ExploreParameters exploreParameters)throws InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException;
    public List<Relationable> exploreRelatedRelationablesByRelationDepth(RelationDirection relationDirection,String sourceRelationableId,int depth,ExploreParameters exploreParameters) throws InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException;
}
