package com.infoDiscover.infoDiscoverEngine.dataMart;

public interface Relation extends Measurable{
    public String getId();
    public String getType();
    public Relationable getFromRelationable();
    public Relationable getToRelationable();
}
