package com.infoDiscover.infoDiscoverEngine.dataMart;

import java.util.List;

public interface RelationType extends TypePropertyable{
    public String getTypeName();
    public RelationType getParentRelationType();
    public List<RelationType> getChildRelationTypes();
    public List<RelationType> getAncestorRelationTypes();
    public List<RelationType> getDescendantRelationTypes();
    public long countContainedRelations(boolean includeDescendant);
    public Object removeContainedRelations();
}
