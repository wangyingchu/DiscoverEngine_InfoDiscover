package com.infoDiscover.common.dimension.time.manager;

import com.infoDiscover.common.dimension.time.dimension.*;
import com.infoDiscover.common.dimension.time.constants.TimeDimensionConstants;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;

/**
 * Created by sun.
 */
public class TimeSqlBuilder {

    public static String queryByYear(YearDimension year, String relationType) {
        String sql = "select expand(both('" + InfoDiscoverEngineConstant.CLASSPERFIX_RELATION + relationType + "')) from " +
                year.getType() + " WHERE year=" + year.getYear();

        return sql;
    }

    public static String queryByMonth(MonthDimension month, String relationType) {

        StringBuilder sb = new StringBuilder();
        sb.append("select expand(both('" + InfoDiscoverEngineConstant.CLASSPERFIX_RELATION + relationType + "')) from " +
                month.getType() + " WHERE");

        if (month.getYear() > 0) {
            sb.append(" year=" + month.getYear() + " and ");
        }

        sb.append(" month=" + month.getMonth());

        return sb.toString();
    }

    public static String queryByDay(DayDimension day, String relationType) {

        StringBuilder sb = new StringBuilder();
        sb.append("select expand(both('" + InfoDiscoverEngineConstant.CLASSPERFIX_RELATION + relationType + "')) from " +
                day.getType() + " WHERE");

        if (day.getYear() > 0) {
            sb.append(" year=" + day.getYear() + " and ");
        }

        if (day.getMonth() > 0) {
            sb.append(" month=" + day.getMonth() + " and ");
        }

        sb.append("day=" + day.getDay());

        return sb.toString();
    }

    public static String queryByHour(HourDimension hour, String relationType) {

        StringBuilder sb = new StringBuilder();
        sb.append("select expand(both('" + InfoDiscoverEngineConstant.CLASSPERFIX_RELATION + relationType + "')) from " +
                hour.getType() + " WHERE");

        if (hour.getYear() > 0) {
            sb.append(" year=" + hour.getYear() + " and ");
        }

        if (hour.getMonth() > 0) {
            sb.append(" month=" + hour.getMonth() + " and ");
        }

        if (hour.getDay() > 0) {
            sb.append(" day=" + hour.getDay() + " and ");
        }

        sb.append(" hour=" + hour.getHour());

        return sb.toString();
    }

    public static String queryByMinute(MinuteDimension minute, String relationType) {

        StringBuilder sb = new StringBuilder();
        sb.append("select expand(both('" + InfoDiscoverEngineConstant.CLASSPERFIX_RELATION + relationType + "')) from " +
                minute.getType() + " WHERE");

        if (minute.getYear() > 0) {
            sb.append(" year=" + minute.getYear() + " and ");
        }

        if (minute.getMonth() > 0) {
            sb.append(" month=" + minute.getMonth() + " and ");
        }

        if (minute.getDay() > 0) {
            sb.append(" day=" + minute.getDay() + " and ");
        }

        if (minute.getHour() > 0) {
            sb.append(" hour=" + minute.getHour() + " and ");
        }

        sb.append(" minute=" + minute.getMinute());

        return sb.toString();
    }
}
