package com.infoDiscover.app.base.time.analytics;

import com.infoDiscover.app.base.executor.QueryExecutor;
import com.infoDiscover.app.base.time.dimension.*;
import com.infoDiscover.app.base.time.manager.TimeSqlBuilder;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relationable;

import java.util.List;

/**
 * Created by sun.
 */
public class TimeAnalytics {

    public List<Relationable> drillDownByYear(YearDimension year, String relationType) {
        String sql = TimeSqlBuilder.queryByYear(year, relationType);
        println("queryByYear: " + sql);
        return QueryExecutor.executeQuery(sql);
    }

    public List<Relationable> drillDownByMonth(MonthDimension month, String relationType) {
        String sql = TimeSqlBuilder.queryByMonth(month, relationType);
        println("queryByYear: " + sql);
        return QueryExecutor.executeQuery(sql);
    }

    public List<Relationable> drillDownByDay(DayDimension day, String relationType) {
        String sql = TimeSqlBuilder.queryByDay(day, relationType);
        println("queryByYear: " + sql);
        return QueryExecutor.executeQuery(sql);
    }

    public List<Relationable> drillDownByHour(HourDimension hour, String relationType) {
        String sql = TimeSqlBuilder.queryByHour(hour, relationType);
        println("queryByYear: " + sql);
        return QueryExecutor.executeQuery(sql);
    }

    public List<Relationable> drillDownByMinute(MinuteDimension minute, String relationType) {
        String sql = TimeSqlBuilder.queryByMinute(minute, relationType);
        println("queryByYear: " + sql);
        return QueryExecutor.executeQuery(sql);
    }

    public static void println(String message) {
        System.out.println(message);
    }
}
