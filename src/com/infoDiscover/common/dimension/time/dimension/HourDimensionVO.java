package com.infoDiscover.common.dimension.time.dimension;

/**
 * Created by sun.
 */
public class HourDimensionVO extends DayDimensionVO {
    int hour;

    public HourDimensionVO(String type, int year, int month, int day, int hour) {
        super(type, year, month, day);
        this.hour = hour;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
