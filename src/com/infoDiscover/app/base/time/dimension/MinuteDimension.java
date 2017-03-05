package com.infoDiscover.app.base.time.dimension;

/**
 * Created by sun.
 */
public class MinuteDimension extends HourDimension {
    int minute;

    public MinuteDimension(int year, int month, int day, int hour, int minute) {
        super(year, month, day, hour);
        this.minute = minute;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
