package com.infoDiscover.infoDiscoverEngine.dataWarehouse;

import com.infoDiscover.infoDiscoverEngine.dataMart.RelationDirection;

public class RelationTypeFilter {

    public enum FilteringLogic{
        AND,OR
    }

    public enum TypeExistenceLogic {
        HAS,NOTHAS
    }

    private String typeName;
    private RelationDirection relationDirection;
    private long relationCount;
    private FilteringLogic filteringLogic;
    private TypeExistenceLogic typeExistenceLogic;

    public TypeExistenceLogic getTypeExistenceLogic() {
        return typeExistenceLogic;
    }

    public void setTypeExistenceLogic(TypeExistenceLogic typeExistenceLogic) {
        this.typeExistenceLogic = typeExistenceLogic;
    }

    public RelationTypeFilter(){}

    public RelationTypeFilter(String typeName, RelationDirection relationDirection, long relationCount,
                              FilteringLogic filteringLogic, TypeExistenceLogic typeExistenceLogic){
        this.typeName=typeName;
        this.relationDirection=relationDirection;
        this.relationCount=relationCount;
        this.filteringLogic=filteringLogic;
        this.typeExistenceLogic = typeExistenceLogic;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public RelationDirection getRelationDirection() {
        return relationDirection;
    }

    public void setRelationDirection(RelationDirection relationDirection) {
        this.relationDirection = relationDirection;
    }

    public long getRelationCount() {
        return relationCount;
    }

    public void setRelationCount(long relationCount) {
        this.relationCount = relationCount;
    }

    public FilteringLogic getFilteringLogic() {
        return filteringLogic;
    }

    public void setFilteringLogic(FilteringLogic filteringLogic) {
        this.filteringLogic = filteringLogic;
    }
}
