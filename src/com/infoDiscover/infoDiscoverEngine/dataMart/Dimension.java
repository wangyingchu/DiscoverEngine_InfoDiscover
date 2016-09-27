package com.infoDiscover.infoDiscoverEngine.dataMart;

public interface Dimension extends Relationable{
    //是否需要创建标准的常用维度，例如时间序列，地理，国家等
    public String getId();
    public String getType();
}
