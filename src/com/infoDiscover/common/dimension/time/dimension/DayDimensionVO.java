package com.infoDiscover.common.dimension.time.dimension;

/**
 * Created by sun.
 */
public class DayDimensionVO extends MonthDimensionVO {
    int day;

    public DayDimensionVO(String type, int year, int month, int day) {
        super(type, year, month);
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String toString() {
        return year + "-" + month + "-" + day;
    }
}
