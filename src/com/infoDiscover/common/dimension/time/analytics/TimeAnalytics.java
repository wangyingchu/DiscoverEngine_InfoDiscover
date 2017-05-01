package com.infoDiscover.common.dimension.time.analytics;

import com.infoDiscover.common.dimension.time.dimension.*;
import com.infoDiscover.common.dimension.time.manager.TimeSqlBuilder;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relationable;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by sun.
 */
public class TimeAnalytics {

    private final static Logger logger = LoggerFactory.getLogger(TimeAnalytics.class);

    public List<Relationable> drillDownByYear(InfoDiscoverSpace ids, YearDimensionVO year, String
            relationType) {
        String sql = TimeSqlBuilder.queryByYear(year, relationType);
        logger.info("queryByYear: {}", sql);
        return QueryExecutor.executeFactQuery(ids, sql);
    }

    public List<Relationable> drillDownByMonth(InfoDiscoverSpace ids, MonthDimensionVO month,
                                               String relationType) {
        String sql = TimeSqlBuilder.queryByMonth(month, relationType);
        logger.info("queryByYear: {}", sql);
        return QueryExecutor.executeFactQuery(ids, sql);
    }

    public List<Relationable> drillDownByDay(InfoDiscoverSpace ids, DayDimensionVO day, String
            relationType) {
        String sql = TimeSqlBuilder.queryByDay(day, relationType);
        logger.info("queryByYear: {}", sql);
        return QueryExecutor.executeFactQuery(ids, sql);
    }

    public List<Relationable> drillDownByHour(InfoDiscoverSpace ids, HourDimensionVO hour, String
            relationType) {
        String sql = TimeSqlBuilder.queryByHour(hour, relationType);
        logger.info("queryByYear: {}", sql);
        return QueryExecutor.executeFactQuery(ids, sql);
    }

    public List<Relationable> drillDownByMinute(InfoDiscoverSpace ids, MinuteDimensionVO minute,
                                                String relationType) {
        String sql = TimeSqlBuilder.queryByMinute(minute, relationType);
        logger.info("queryByYear: {}", sql);
        return QueryExecutor.executeFactQuery(ids, sql);
    }

    public static void println(String message) {
        System.out.println(message);
    }

    public static void main(String[] args) {
        TimeAnalytics a = new TimeAnalytics();

        YearDimensionVO y = new YearDimensionVO(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION +
                "y", 2018);

        InfoDiscoverSpace ids = DiscoverEngineComponentFactory.connectInfoDiscoverSpace("DemoArch");
        List<Relationable> list = a.drillDownByYear(ids, y, "created");
        println("list: " + list.size());
        ids.closeSpace();
    }
}
