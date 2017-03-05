package com.infoDiscover.app.base.time.dimension;

/**
 * Created by sun.
 */
public class SecondDimension extends MinuteDimension {
    int second;

    public SecondDimension(int year, int month, int day, int hour, int minute, int second) {
        super(year, month, day, hour, minute);
        this.second = second;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}
