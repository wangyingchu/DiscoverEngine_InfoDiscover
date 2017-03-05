package com.infoDiscover.app.base.time.dimension;

/**
 * Created by sun.
 */
public class HourDimension extends DayDimension {
    int hour;

    public HourDimension(int year, int month, int day, int hour) {
        super(year, month, day);
        this.hour = hour;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
