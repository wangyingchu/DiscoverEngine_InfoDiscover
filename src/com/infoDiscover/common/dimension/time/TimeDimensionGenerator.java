package com.infoDiscover.common.dimension.time;

import com.infoDiscover.common.dimension.time.constants.TimeDimensionConstants;
import com.infoDiscover.common.dimension.time.dimension.*;
import com.infoDiscover.common.dimension.time.manager.TimeDimensionManager;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.construction.supervision.constants.DatabaseConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by sun.
 */
public class TimeDimensionGenerator {

    private final static Logger logger = LogManager.getLogger(TimeDimensionVO.class);

    public static void main(String[] args) throws InfoDiscoveryEngineRuntimeException,
            InfoDiscoveryEngineDataMartException {
        String dimensionPrefix = "DEMO2";
        int[] years = {2016};
        int depth = 3;

        InfoDiscoverSpace ids = DiscoverEngineComponentFactory.connectInfoDiscoverSpace
                (DatabaseConstants.DATABASE_SPACE);
        generateYears(ids, dimensionPrefix, years, depth);
        ids.closeSpace();
    }

    public static void generateYears(InfoDiscoverSpace ids, String dimensionPrefix, int[] years,
                                     int depth) {
        for (int year : years) {
            try {
                yearGenerator(ids, dimensionPrefix, year, depth);
            } catch (InfoDiscoveryEngineDataMartException e) {
                logger.error(e.getMessage());
            } catch (InfoDiscoveryEngineRuntimeException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /*
    * depth:
    * 1 - year
    * 2 - month
    * 3 - day
    * 4 - hour
    * 5 - minute
    * 6 - second
    *
    * */
    private static void yearGenerator(InfoDiscoverSpace ids, String dimensionPrefix, int year, int
            depth) throws
            InfoDiscoveryEngineDataMartException, InfoDiscoveryEngineRuntimeException {
        logger.info("Start to generate dimension with prefix: {}, and year: {} and depth: {}",
                dimensionPrefix, year, depth);

        if (year < 0 || depth < 0) {
            return;
        }

        if (depth > TimeDimensionConstants.MAX_DEPTH) {
            depth = TimeDimensionConstants.MAX_DEPTH;
        }

        long startTime = System.currentTimeMillis();
        initTimeDimensionType(ids, dimensionPrefix);
        long endTime = System.currentTimeMillis();
        logger.info("Elapsed time to create time dimension type: {}", (endTime - startTime));

        startTime = System.currentTimeMillis();
        createTimeDimension(ids, dimensionPrefix, year, depth);
        endTime = System.currentTimeMillis();
        logger.info("Elapsed time to create dimension: {}", (endTime - startTime));
        logger.info("End to generate time dimension");
    }

    public static void initTimeDimensionType(InfoDiscoverSpace ids, String dimensionPrefix) throws
            InfoDiscoveryEngineDataMartException {
        TimeDimensionManager manager = new TimeDimensionManager(ids);
        manager.createTimeDimensionType(dimensionPrefix);
    }

    public static void createTimeDimension(InfoDiscoverSpace ids, String dimensionPrefix, int year,
                                           int depth) throws
            InfoDiscoveryEngineRuntimeException {

        TimeDimensionManager manager = new TimeDimensionManager(ids);

        String prefix = "";
        if (dimensionPrefix != null || !dimensionPrefix.trim().equals("")) {
            prefix = normalizePrefix(dimensionPrefix);
        }

        String yearType = prefix + TimeDimensionConstants.YEAR;
        String monthType = prefix + TimeDimensionConstants.MONTH;
        String dayType = prefix + TimeDimensionConstants.DAY;
        String hourType = prefix + TimeDimensionConstants.HOUR;
        String minuteType = prefix + TimeDimensionConstants.MINUTE;

        // create year
        manager.createYearDimension(new YearDimensionVO(yearType, year));

        // create month
        if (depth > 1) {
            for (int i = 1; i < 13; i++) {
                manager.createMonthDimension(new MonthDimensionVO(monthType, year, i));
            }

            // create days
            if (depth > 2) {
                Map<Integer, Integer> daysOfMonth = getDaysOfMonth(year);
                Set<Integer> keySet = daysOfMonth.keySet();
                Iterator<Integer> it = keySet.iterator();
                while (it.hasNext()) {
                    int month = it.next().intValue();
                    int days = daysOfMonth.get(month);
                    for (int d = 1; d <= days; d++) {
                        manager.createDayDimension(new DayDimensionVO
                                (dayType, year, month, d));

                        // create hour
                        if (depth > 3) {
                            for (int h = 0; h <= 23; h++) {
                                manager.createHourDimension(new
                                        HourDimensionVO(hourType, year, month, d, h));

                                // create minute
                                if (depth > 4) {
                                    for (int m = 0; m <= 59; m++) {
                                        manager.createMinuteDimension
                                                (new MinuteDimensionVO
                                                        (minuteType, year, month, d, h, m));
                                    }
                                }
                            }

                        }
                    }

                }
            }
        }

    }

    private static int getDaysOfFeb(int year) {
        int daysOfFeb = 28;
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            daysOfFeb = 29;
        }
        return daysOfFeb;
    }

    public static Map<Integer, Integer> getDaysOfMonth(int year) {
        Map<Integer, Integer> daysOfMonthMap = new HashMap<>();
        daysOfMonthMap.put(1, 31);
        daysOfMonthMap.put(3, 31);
        daysOfMonthMap.put(5, 31);
        daysOfMonthMap.put(7, 31);
        daysOfMonthMap.put(8, 31);
        daysOfMonthMap.put(10, 31);
        daysOfMonthMap.put(12, 31);

        daysOfMonthMap.put(4, 30);
        daysOfMonthMap.put(6, 30);
        daysOfMonthMap.put(9, 30);
        daysOfMonthMap.put(11, 30);

        daysOfMonthMap.put(2, getDaysOfFeb(year));

        return daysOfMonthMap;
    }

    private static String normalizePrefix(String value) {
        return value.endsWith("_") ? value : value + "_";
    }
}
