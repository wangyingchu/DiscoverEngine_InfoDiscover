package com.infoDiscover.infoDiscoverEngine.dataMart;

public interface FactType extends TypePropertyable{
    public String getTypeName();

    public long countContainedFacts();
    public Object removeContainedFaces();
}
