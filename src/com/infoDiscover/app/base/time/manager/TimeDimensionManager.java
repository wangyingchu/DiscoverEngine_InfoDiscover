package com.infoDiscover.app.base.time.manager;

import com.infoDiscover.app.base.database.DatabaseConstants;
import com.infoDiscover.app.base.database.DatabaseManager;
import com.infoDiscover.app.base.time.constants.TimeDimensionConstants;
import com.infoDiscover.app.base.time.dimension.*;
import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.DimensionType;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun.
 */
public class TimeDimensionManager {

    // relationshipType: TIME
    public final static String RelationType_TIME = "TIME";

    public void createTimeDimensionType() throws InfoDiscoveryEngineDataMartException {
        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        if (ids != null) {

            if (!ids.hasDimensionType(TimeDimensionConstants.YEAR)) {
                DimensionType yearType = ids.addDimensionType(TimeDimensionConstants.YEAR);
            }

            if (!ids.hasDimensionType(TimeDimensionConstants.MONTH)) {
                DimensionType monthType = ids.addChildDimensionType(TimeDimensionConstants
                        .MONTH, TimeDimensionConstants.YEAR);
            }

            if (!ids.hasDimensionType(TimeDimensionConstants.DAY)) {
                DimensionType dayType = ids.addChildDimensionType(TimeDimensionConstants
                        .DAY, TimeDimensionConstants.MONTH);
            }

            if (!ids.hasDimensionType(TimeDimensionConstants.HOUR)) {
                DimensionType hourType = ids.addChildDimensionType(TimeDimensionConstants
                        .HOUR, TimeDimensionConstants.DAY);
            }

            if (!ids.hasDimensionType(TimeDimensionConstants.MINUTE)) {
                DimensionType minuteType = ids.addChildDimensionType(TimeDimensionConstants
                        .MINUTE, TimeDimensionConstants.HOUR);
            }

        } else {
            println("Failed to connect to database: " + DatabaseConstants.INFODISCOVER_SPACENAME);
        }
        ids.closeSpace();
    }


    private Dimension createTimeDimension(InfoDiscoverSpace ids, String type, Map<String, Object>
            properties) throws InfoDiscoveryEngineRuntimeException {
        println("Start to create time dimension with type: " + type + " and properties: " +
                properties);

        Dimension dimension = DiscoverEngineComponentFactory.createDimension(type);
        dimension = ids.addDimension(dimension);
        dimension.addProperties(properties);

        println("End to crate time dimension");
        return dimension;
    }

    public Dimension createYearDimension(InfoDiscoverSpace ids, YearDimension year) throws
            InfoDiscoveryEngineRuntimeException {
        println("Start to create year dimension");
        Map<String, Object> yearProps = new HashMap<String, Object>();
        yearProps.put("year", year.getYear());

        Dimension yearDimension = createTimeDimension(ids, TimeDimensionConstants.YEAR, yearProps);

        println("End to create year dimension");

        return yearDimension;
    }

    public Dimension createMonthDimension(InfoDiscoverSpace ids, MonthDimension month) throws
            InfoDiscoveryEngineRuntimeException {
        println("Start to create month dimension");
        Map<String, Object> monthProps = new HashMap<String, Object>();
        monthProps.put("year", month.getYear());
        monthProps.put("month", month.getMonth());

        Dimension monthDimension = createTimeDimension(ids, TimeDimensionConstants.MONTH,
                monthProps);

        println("End to create month dimension");

        return monthDimension;
    }

    public Dimension createDayDimension(InfoDiscoverSpace ids, DayDimension day) throws
            InfoDiscoveryEngineRuntimeException {
        println("Start to create day dimension");
        Map<String, Object> dayProps = new HashMap<String, Object>();
        dayProps.put("year", day.getYear());
        dayProps.put("month", day.getMonth());
        dayProps.put("day", day.getDay());

        Dimension dayDimension = createTimeDimension(ids, TimeDimensionConstants.DAY, dayProps);

        println("End to create day dimension");

        return dayDimension;
    }

    public Dimension createHourDimension(InfoDiscoverSpace ids, HourDimension hour) throws
            InfoDiscoveryEngineRuntimeException {
        println("Start to create hour dimension");
        Map<String, Object> hourProps = new HashMap<String, Object>();
        hourProps.put("year", hour.getYear());
        hourProps.put("month", hour.getMonth());
        hourProps.put("day", hour.getDay());
        hourProps.put("hour", hour.getHour());

        Dimension hourDimension = createTimeDimension(ids, TimeDimensionConstants.HOUR, hourProps);

        println("End to create hour dimension");

        return hourDimension;
    }

    public Dimension createMinuteDimension(InfoDiscoverSpace ids, MinuteDimension minute) throws
            InfoDiscoveryEngineRuntimeException {
        println("Start to create minute dimension");
        Map<String, Object> minuteProps = new HashMap<String, Object>();
        minuteProps.put("year", minute.getYear());
        minuteProps.put("month", minute.getMonth());
        minuteProps.put("day", minute.getDay());
        minuteProps.put("hour", minute.getHour());
        minuteProps.put("minute", minute.getMinute());

        Dimension minuteDimension = createTimeDimension(ids, TimeDimensionConstants.MINUTE,
                minuteProps);

        println("End to create minute dimension");

        return minuteDimension;
    }

    public static void println(String message) {
        System.out.println(message);
    }

}
