package com.infoDiscover.common.dimension.time.dimension;

/**
 * Created by sun.
 */
public class YearDimensionVO extends TimeDimensionVO {
    int year;

    public YearDimensionVO(String type, int year) {
        super(type);
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
