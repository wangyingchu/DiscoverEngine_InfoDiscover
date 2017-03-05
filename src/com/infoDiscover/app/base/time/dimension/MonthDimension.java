package com.infoDiscover.app.base.time.dimension;

/**
 * Created by sun.
 */
public class MonthDimension extends YearDimension {
    int month;

    public MonthDimension(int year, int month) {
        super(year);
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
