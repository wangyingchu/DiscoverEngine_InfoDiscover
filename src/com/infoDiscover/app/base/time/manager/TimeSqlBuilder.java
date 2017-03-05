package com.infoDiscover.app.base.time.manager;

import com.infoDiscover.app.base.time.constants.TimeDimensionConstants;
import com.infoDiscover.app.base.time.dimension.*;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;

/**
 * Created by sun.
 */
public class TimeSqlBuilder {

    public static String queryByYear(YearDimension year, String relationType) {
        String sql = "select expand(both('" + InfoDiscoverEngineConstant.CLASSPERFIX_RELATION + relationType + "')) from " +
                InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION + TimeDimensionConstants
                .YEAR + " WHERE year=" + year.getYear();

        return sql;
    }

    public static String queryByMonth(MonthDimension month, String relationType) {

        StringBuilder sb = new StringBuilder();
        sb.append("select expand(both('" + InfoDiscoverEngineConstant.CLASSPERFIX_RELATION + relationType + "')) from " +
                InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION + TimeDimensionConstants
                .MONTH + " WHERE");

        if (month.getYear() > 0) {
            sb.append(" year=" + month.getYear() + " and ");
        }

        sb.append(" month=" + month.getMonth());

        return sb.toString();
    }

    public static String queryByDay(DayDimension day, String relationType) {

        StringBuilder sb = new StringBuilder();
        sb.append("select expand(both('" + InfoDiscoverEngineConstant.CLASSPERFIX_RELATION + relationType + "')) from " +
                InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION + TimeDimensionConstants
                .DAY + " WHERE");

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
                InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION + TimeDimensionConstants
                .HOUR + " WHERE");

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
                InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION + TimeDimensionConstants
                .MINUTE + " WHERE");

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
