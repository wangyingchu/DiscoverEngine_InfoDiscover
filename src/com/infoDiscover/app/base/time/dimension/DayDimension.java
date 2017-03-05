package com.infoDiscover.app.base.time.dimension;

/**
 * Created by sun.
 */
public class DayDimension extends MonthDimension {
    int day;

    public DayDimension(int year, int month, int day) {
        super(year, month);
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
