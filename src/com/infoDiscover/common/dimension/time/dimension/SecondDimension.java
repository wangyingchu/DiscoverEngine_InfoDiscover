package com.infoDiscover.common.dimension.time.dimension;

/**
 * Created by sun.
 */
public class SecondDimension extends MinuteDimension {
    int second;

    public SecondDimension(String type, int year, int month, int day, int hour, int minute, int
            second) {
        super(type, year, month, day, hour, minute);
        this.second = second;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}
