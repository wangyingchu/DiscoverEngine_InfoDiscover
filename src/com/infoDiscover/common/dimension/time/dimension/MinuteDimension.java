package com.infoDiscover.common.dimension.time.dimension;

/**
 * Created by sun.
 */
public class MinuteDimension extends HourDimension {
    int minute;

    public MinuteDimension(String type, int year, int month, int day, int hour, int minute) {
        super(type, year, month, day, hour);
        this.minute = minute;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
