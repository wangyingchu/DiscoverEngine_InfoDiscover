package com.infoDiscover.infoDiscoverEngine.dataWarehouse;


import com.infoDiscover.infoDiscoverEngine.dataMart.*;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

import java.util.List;
import java.util.Stack;

public interface InformationExplorer {

    public enum FilteringPattern{
        AND,OR
    }

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
    public List<Measurable> discoverMeasurablesByQuerySQL(InformationType informationType, String typeName, String querySQL) throws InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException;
    public Stack<Relation> discoverRelationablesShortestPath(String firstRelationableId,String secondRelationableId) throws InfoDiscoveryEngineRuntimeException;
    public List<Stack<Relation>> discoverRelationablesShortestPaths(String firstRelationableId,String secondRelationableId,int pathNumber) throws InfoDiscoveryEngineRuntimeException;
    public List<Stack<Relation>> discoverRelationablesLongestPaths(String firstRelationableId,String secondRelationableId,int pathNumber) throws InfoDiscoveryEngineRuntimeException;
    public List<Stack<Relation>> discoverRelationablesAllPaths(String firstRelationableId,String secondRelationableId) throws InfoDiscoveryEngineRuntimeException;
    public List<Stack<Relation>> discoverPathsConnectedWithSpecifiedRelationables(String startRelationableId,String endRelationableId,List<String> passedRelationablesId) throws InfoDiscoveryEngineRuntimeException;
    public List<Relationable> discoverSimilarRelationablesRelatedToSameDimensions(String sourceRelationableId,List<String> dimensionIds,FilteringPattern filteringPattern) throws InfoDiscoveryEngineRuntimeException;
    public List<OrientVertex> discoverOrientVertexList(String sql);
    public void update(String sql);
}
