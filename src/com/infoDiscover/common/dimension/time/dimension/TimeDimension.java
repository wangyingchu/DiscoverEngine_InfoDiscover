package com.infoDiscover.common.dimension.time.dimension;

import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;

/**
 * Created by sun.
 */
public class TimeDimension {
    private  String type;

    public TimeDimension(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
