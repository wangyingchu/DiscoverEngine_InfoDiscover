package com.infoDiscover.common.dimension.time.manager;

import com.infoDiscover.common.dimension.time.dimension.*;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.solution.arch.database.DatabaseConstants;
import com.infoDiscover.solution.arch.database.DatabaseManager;
import com.infoDiscover.common.dimension.time.constants.TimeDimensionConstants;
import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.DimensionType;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun.
 */
public class TimeDimensionManager {

    private final static Logger logger = LogManager.getLogger
            (TimeDimensionManager.class);

    public void createTimeDimensionType(String dimensionPrefix) throws
            InfoDiscoveryEngineDataMartException {
        logger.info("Enter method createTimeDimensionType() with dimensionPrefix: " +
                dimensionPrefix);

        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        if (ids != null) {

            String prefix = "";
            if (dimensionPrefix != null || !dimensionPrefix.trim().equals("")) {
                prefix = dimensionPrefix + "_";
            }

            String year = prefix + TimeDimensionConstants.YEAR;
            if (!ids.hasDimensionType(year)) {
                DimensionType yearType = ids.addDimensionType(year);
            }

            String month = prefix + TimeDimensionConstants.MONTH;
            if (!ids.hasDimensionType(month)) {
                DimensionType monthType = ids.addChildDimensionType(month, year);
            }

            String day = prefix + TimeDimensionConstants.DAY;
            if (!ids.hasDimensionType(day)) {
                DimensionType dayType = ids.addChildDimensionType(day, month);
            }

            String hour = prefix + TimeDimensionConstants.HOUR;
            if (!ids.hasDimensionType(hour)) {
                DimensionType hourType = ids.addChildDimensionType(hour, day);
            }

            String minute = prefix + TimeDimensionConstants.MINUTE;
            if (!ids.hasDimensionType(minute)) {
                DimensionType minuteType = ids.addChildDimensionType(minute, hour);
            }

        } else {
            logger.error("Failed to connect to database: " + DatabaseConstants
                    .INFODISCOVER_SPACENAME);
        }
        ids.closeSpace();

        logger.info("Exit method createTimeDimensionType()...");
    }


    private Dimension createTimeDimension(InfoDiscoverSpace ids, String type, Map<String, Object>
            properties) throws InfoDiscoveryEngineRuntimeException {

        Dimension dimension = DiscoverEngineComponentFactory.createDimension(type);
        dimension = ids.addDimension(dimension);
        dimension.addProperties(properties);

        return dimension;
    }

    public Dimension createYearDimension(InfoDiscoverSpace ids, YearDimensionVO year) throws
            InfoDiscoveryEngineRuntimeException {
        logger.info("Start to create year dimension");
        Map<String, Object> yearProps = new HashMap<String, Object>();
        yearProps.put("year", year.getYear());

        // check if year is already existed
        ExploreParameters ep = new ExploreParameters();
        ep.setType(year.getType());
        ep.setDefaultFilteringItem(new EqualFilteringItem("year", year.getYear()));
        try {
            Dimension existingYearDimension = QueryExecutor.executeDimensionQuery(ids.getInformationExplorer(), ep);
            if (existingYearDimension == null) {
                return createTimeDimension(ids, year.getType(), yearProps);
            }
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.error("Failed to create year dimension");
        }

        logger.info("End to create year dimension");

        return null;
    }


    public Dimension createMonthDimension(InfoDiscoverSpace ids, MonthDimensionVO month) throws
            InfoDiscoveryEngineRuntimeException {
        logger.info("Start to create month dimension");

        Map<String, Object> monthProps = new HashMap<String, Object>();
        monthProps.put("year", month.getYear());
        monthProps.put("month", month.getMonth());

        ExploreParameters ep = new ExploreParameters();
        ep.setType(month.getType());
        ep.setDefaultFilteringItem(new EqualFilteringItem("year", month.getYear()));
        ep.addFilteringItem(new EqualFilteringItem("month", month.getMonth()), ExploreParameters
                .FilteringLogic.AND);
        try {
            if (QueryExecutor.executeDimensionQuery(ids.getInformationExplorer(), ep) == null) {
                return createTimeDimension(ids, month.getType(),
                        monthProps);
            }

        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.error("Failed to create month dimension");
        }


        logger.info("End to create month dimension");

        return null;
    }

    public Dimension createDayDimension(InfoDiscoverSpace ids, DayDimensionVO day) throws
            InfoDiscoveryEngineRuntimeException {
        logger.info("Start to create day dimension with day: " + day.toString());

        Map<String, Object> dayProps = new HashMap<String, Object>();
        dayProps.put("year", day.getYear());
        dayProps.put("month", day.getMonth());
        dayProps.put("day", day.getDay());

        ExploreParameters ep = new ExploreParameters();
        ep.setType(day.getType());
        ep.setDefaultFilteringItem(new EqualFilteringItem("year", day.getYear()));
        ep.addFilteringItem(new EqualFilteringItem("month", day.getMonth()), ExploreParameters
                .FilteringLogic.AND);
        ep.addFilteringItem(new EqualFilteringItem("day", day.getDay()), ExploreParameters
                .FilteringLogic.AND);
        try {
            if (QueryExecutor.executeDimensionQuery(ids.getInformationExplorer(), ep) == null) {
                return createTimeDimension(ids, day.getType(), dayProps);
            }
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.error("Failed to create day dimension");
        }

        logger.debug("End to create day dimension");

        return null;
    }

    public Dimension createHourDimension(InfoDiscoverSpace ids, HourDimensionVO hour) throws
            InfoDiscoveryEngineRuntimeException {
        logger.info("Start to create hour dimension");

        Map<String, Object> hourProps = new HashMap<String, Object>();
        hourProps.put("year", hour.getYear());
        hourProps.put("month", hour.getMonth());
        hourProps.put("day", hour.getDay());
        hourProps.put("hour", hour.getHour());

        ExploreParameters ep = new ExploreParameters();
        ep.setType(hour.getType());
        ep.setDefaultFilteringItem(new EqualFilteringItem("year", hour.getYear()));
        ep.addFilteringItem(new EqualFilteringItem("month", hour.getMonth()), ExploreParameters
                .FilteringLogic.AND);
        ep.addFilteringItem(new EqualFilteringItem("day", hour.getDay()), ExploreParameters
                .FilteringLogic.AND);
        ep.addFilteringItem(new EqualFilteringItem("hour", hour.getHour()), ExploreParameters
                .FilteringLogic.AND);
        try {
            if (QueryExecutor.executeDimensionQuery(ids.getInformationExplorer(), ep) == null) {
                return createTimeDimension(ids, hour.getType(), hourProps);
            }
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.error("Failed to create hour dimension");
        }

        logger.info("End to create hour dimension");

        return null;
    }

    public Dimension createMinuteDimension(InfoDiscoverSpace ids, MinuteDimensionVO minute) throws
            InfoDiscoveryEngineRuntimeException {
        logger.info("Start to create minute dimension");
        Map<String, Object> minuteProps = new HashMap<String, Object>();
        minuteProps.put("year", minute.getYear());
        minuteProps.put("month", minute.getMonth());
        minuteProps.put("day", minute.getDay());
        minuteProps.put("hour", minute.getHour());
        minuteProps.put("minute", minute.getMinute());

        ExploreParameters ep = new ExploreParameters();
        ep.setType(minute.getType());
        ep.setDefaultFilteringItem(new EqualFilteringItem("year", minute.getYear()));
        ep.addFilteringItem(new EqualFilteringItem("month", minute.getMonth()), ExploreParameters
                .FilteringLogic.AND);
        ep.addFilteringItem(new EqualFilteringItem("day", minute.getDay()), ExploreParameters
                .FilteringLogic.AND);
        ep.addFilteringItem(new EqualFilteringItem("hour", minute.getHour()), ExploreParameters
                .FilteringLogic.AND);
        ep.addFilteringItem(new EqualFilteringItem("minute", minute.getMinute()), ExploreParameters
                .FilteringLogic.AND);
        try {
            if (QueryExecutor.executeDimensionQuery(ids.getInformationExplorer(), ep) == null) {
                return createTimeDimension(ids, minute.getType(),
                        minuteProps);
            }
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.error("Failed to create minute dimension");
        }

        logger.info("End to create minute dimension");

        return null;
    }

}
