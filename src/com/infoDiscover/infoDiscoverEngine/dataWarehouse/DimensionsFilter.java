package com.infoDiscover.infoDiscoverEngine.dataWarehouse;

import com.infoDiscover.infoDiscoverEngine.dataMart.RelationDirection;

import java.util.ArrayList;
import java.util.List;

public class DimensionsFilter {

    public enum FilteringLogic{
        AND,OR
    }

    public enum RelationExistenceLogic {
        HAS,NOTHAS
    }

    private FilteringLogic filteringLogic;
    private RelationExistenceLogic relationExistenceLogic;
    private String relationType;
    private List<String> dimensionIdsList;
    private RelationDirection relationDirection;

    public DimensionsFilter(){
        this.dimensionIdsList=new ArrayList<>();
    }

    public DimensionsFilter(String dimensionId,RelationDirection relationDirection,
                            String relationType,FilteringLogic filteringLogic,RelationExistenceLogic relationExistenceLogic){
        this.dimensionIdsList=new ArrayList<>();
        getDimensionIdsList().add(dimensionId);
        this.relationDirection=relationDirection;
        this.relationType=relationType;
        this.filteringLogic=filteringLogic;
        this.relationExistenceLogic=relationExistenceLogic;
    }

    public FilteringLogic getFilteringLogic() {
        return filteringLogic;
    }

    public void setFilteringLogic(FilteringLogic filteringLogic) {
        this.filteringLogic = filteringLogic;
    }

    public RelationExistenceLogic getRelationExistenceLogic() {
        return relationExistenceLogic;
    }

    public void setRelationExistenceLogic(RelationExistenceLogic relationExistenceLogic) {
        this.relationExistenceLogic = relationExistenceLogic;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public RelationDirection getRelationDirection() {
        return relationDirection;
    }

    public void setRelationDirection(RelationDirection relationDirection) {
        this.relationDirection = relationDirection;
    }

    public void addDimensionId(String dimensionId){
        getDimensionIdsList().add(dimensionId);
    }

    public List<String> getDimensionIdsList() {
        return dimensionIdsList;
    }
}
