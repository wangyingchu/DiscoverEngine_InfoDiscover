package com.infoDiscover.app.base.time.manager;

import com.infoDiscover.app.base.database.DatabaseConstants;
import com.infoDiscover.app.base.database.DatabaseManager;
import com.infoDiscover.app.base.time.dimension.*;
import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;

/**
 * Created by sun.
 */
public class TimeDimensionHelper {
    public static void main(String[] args) throws InfoDiscoveryEngineRuntimeException,
            InfoDiscoveryEngineDataMartException {

        initTimeDimensionType();

        int year = 2017;
        createTimeDimention(year);
    }

    public static void println(String message) {
        System.out.println(message);
    }

    public static void initTimeDimensionType() throws InfoDiscoveryEngineDataMartException {
        TimeDimensionManager manager = new TimeDimensionManager();
        manager.createTimeDimensionType();
    }

    public static void createTimeDimention(int y) throws InfoDiscoveryEngineRuntimeException {
        TimeDimensionManager manager = new TimeDimensionManager();

        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();

        if (ids != null) {
            // create year
            Dimension year = manager.createYearDimension(ids, new YearDimension(y));

            // create month
            for (int i = 1; i < 13; i++) {
                Dimension month = manager.createMonthDimension(ids, new MonthDimension(y, i));
            }

            // create day


            for (int d = 1; d <= 31; d++) {
                Dimension day = manager.createDayDimension(ids, new DayDimension(y, 1, d));
                day = manager.createDayDimension(ids, new DayDimension(y, 3, d));
                day = manager.createDayDimension(ids, new DayDimension(y, 5, d));
                day = manager.createDayDimension(ids, new DayDimension(y, 7, d));
                day = manager.createDayDimension(ids, new DayDimension(y, 8, d));
                day = manager.createDayDimension(ids, new DayDimension(y, 10, d));
                day = manager.createDayDimension(ids, new DayDimension(y, 12, d));

                for (int h = 0; h <= 23; h++) {
                    Dimension hour = manager.createHourDimension(ids, new HourDimension(y, 1, d,
                            h));
                    hour = manager.createHourDimension(ids, new HourDimension(y, 3, d,
                            h));
                    hour = manager.createHourDimension(ids, new HourDimension(y, 5, d,
                            h));
                    hour = manager.createHourDimension(ids, new HourDimension(y, 7, d,
                            h));
                    hour = manager.createHourDimension(ids, new HourDimension(y, 8, d,
                            h));
                    hour = manager.createHourDimension(ids, new HourDimension(y, 10, d,
                            h));
                    hour = manager.createHourDimension(ids, new HourDimension(y, 12, d,
                            h));

                    for (int m = 0; m <= 59; m++) {
                        Dimension minute = manager.createMinuteDimension(ids, new MinuteDimension
                                (y, 1, d, h, m));
                        minute = manager.createMinuteDimension(ids, new MinuteDimension
                                (y, 3, d, h, m));
                        minute = manager.createMinuteDimension(ids, new MinuteDimension
                                (y, 5, d, h, m));
                        minute = manager.createMinuteDimension(ids, new MinuteDimension
                                (y, 7, d, h, m));
                        minute = manager.createMinuteDimension(ids, new MinuteDimension
                                (y, 8, d, h, m));
                        minute = manager.createMinuteDimension(ids, new MinuteDimension
                                (y, 10, d, h, m));
                        minute = manager.createMinuteDimension(ids, new MinuteDimension
                                (y, 12, d, h, m));
                    }
                }
            }

            for (int d = 1; d <= 30; d++) {
                Dimension day = manager.createDayDimension(ids, new DayDimension(y, 4, d));
                day = manager.createDayDimension(ids, new DayDimension(y, 6, d));
                day = manager.createDayDimension(ids, new DayDimension(y, 9, d));
                day = manager.createDayDimension(ids, new DayDimension(y, 11, d));

                for (int h = 0; h <= 23; h++) {
                    Dimension hour = manager.createHourDimension(ids, new HourDimension(y, 4, d,
                            h));
                    hour = manager.createHourDimension(ids, new HourDimension(y, 6, d,
                            h));
                    hour = manager.createHourDimension(ids, new HourDimension(y, 9, d,
                            h));
                    hour = manager.createHourDimension(ids, new HourDimension(y, 11, d,
                            h));

                    for (int m = 0; m <= 59; m++) {
                        Dimension minute = manager.createMinuteDimension(ids, new MinuteDimension
                                (y, 4, d, h, m));
                        minute = manager.createMinuteDimension(ids, new MinuteDimension
                                (y, 6, d, h, m));
                        minute = manager.createMinuteDimension(ids, new MinuteDimension
                                (y, 9, d, h, m));
                        minute = manager.createMinuteDimension(ids, new MinuteDimension
                                (y, 11, d, h, m));
                    }
                }

            }

            int daysOfFeb = 28;
            if (y % 4 == 0 && y % 100 != 0 || y % 400 == 0) {
                daysOfFeb = 29;
            }

            for (int d = 1; d <= daysOfFeb; d++) {
                Dimension day = manager.createDayDimension(ids, new DayDimension(y, 2, d));
                for (int h = 0; h <= 23; h++) {
                    Dimension hour = manager.createHourDimension(ids, new HourDimension(y, 2, d,
                            h));
                    for (int m = 0; m <= 59; m++) {
                        Dimension minute = manager.createMinuteDimension(ids, new MinuteDimension
                                (y, 2, d, h, m));
                    }
                }
            }

        } else {
            println("Failed to connect to database: " + DatabaseConstants.INFODISCOVER_SPACENAME);
        }

        ids.closeSpace();

    }
}
