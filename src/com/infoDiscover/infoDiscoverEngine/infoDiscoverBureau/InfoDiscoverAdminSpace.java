package com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau;

import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;

public interface InfoDiscoverAdminSpace {
    public String getSpaceName();
    public void closeSpace();
    public boolean removeFactTypeProperty(String typeName,String propertyName) throws InfoDiscoveryEngineRuntimeException;
    public boolean removeDimensionTypeProperty(String typeName,String propertyName) throws InfoDiscoveryEngineRuntimeException;
    public boolean removeRelationTypeProperty(String typeName,String propertyName) throws InfoDiscoveryEngineRuntimeException;
}
