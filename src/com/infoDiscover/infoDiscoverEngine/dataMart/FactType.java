package com.infoDiscover.infoDiscoverEngine.dataMart;

public interface FactType extends TypePropertyable{
    public String getTypeName();
    public long countContainedFaces();
    public Object removeContainedFaces();
}
