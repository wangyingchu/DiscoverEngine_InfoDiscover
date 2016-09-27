package com.infoDiscover.infoDiscoverEngine.dataMart;

import java.util.List;

public interface DimensionType extends TypePropertyable{
    public String getTypeName();
    public DimensionType getParentDimensionType();
    public List<DimensionType> getChildDimensionTypes();
    public List<DimensionType> getAncestorDimensionTypes();
    public List<DimensionType> getDescendantDimensionTypes();
}
