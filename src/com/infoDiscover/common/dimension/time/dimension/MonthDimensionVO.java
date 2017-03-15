package com.infoDiscover.common.dimension.time.dimension;

/**
 * Created by sun.
 */
public class MonthDimensionVO extends YearDimensionVO {
    int month;

    public MonthDimensionVO(String type, int year, int month) {
        super(type, year);
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
