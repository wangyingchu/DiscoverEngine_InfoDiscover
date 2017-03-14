package com.infoDiscover.common.dimension.time.analytics;

import com.infoDiscover.common.dimension.time.dimension.*;
import com.infoDiscover.common.dimension.time.manager.TimeSqlBuilder;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relationable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by sun.
 */
public class TimeAnalytics {

    private final static Logger logger = LogManager.getLogger(TimeAnalytics.class);

    public List<Relationable> drillDownByYear(YearDimension year, String relationType) {
        String sql = TimeSqlBuilder.queryByYear(year, relationType);
        logger.debug("queryByYear: " + sql);
        return QueryExecutor.executeFactQuery(sql);
    }

    public List<Relationable> drillDownByMonth(MonthDimension month, String relationType) {
        String sql = TimeSqlBuilder.queryByMonth(month, relationType);
        logger.debug("queryByYear: " + sql);
        return QueryExecutor.executeFactQuery(sql);
    }

    public List<Relationable> drillDownByDay(DayDimension day, String relationType) {
        String sql = TimeSqlBuilder.queryByDay(day, relationType);
        logger.debug("queryByYear: " + sql);
        return QueryExecutor.executeFactQuery(sql);
    }

    public List<Relationable> drillDownByHour(HourDimension hour, String relationType) {
        String sql = TimeSqlBuilder.queryByHour(hour, relationType);
        logger.debug("queryByYear: " + sql);
        return QueryExecutor.executeFactQuery(sql);
    }

    public List<Relationable> drillDownByMinute(MinuteDimension minute, String relationType) {
        String sql = TimeSqlBuilder.queryByMinute(minute, relationType);
        logger.debug("queryByYear: " + sql);
        return QueryExecutor.executeFactQuery(sql);
    }

    public static void println(String message) {
        System.out.println(message);
    }

    public static void main(String[] args) {
        TimeAnalytics a = new TimeAnalytics();

        YearDimension y = new YearDimension(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION +
                "y", 2018 );

        List<Relationable> list = a.drillDownByYear(y, "created");
        println("list: " + list.size());
    }
}
