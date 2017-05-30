package com.infoDiscover.common.util;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sun.
 */
public class DateUtil {

    public static final String format1 = "yyyy-MM-dd";

    public static DateTime getDateTime(long dateMillionSeconds) {
        return new DateTime(dateMillionSeconds);
    }

    public static long getLongDateValue(int year, int month, int day) {
        return getDateTimeFromLong(year, month, day).getMillis();
    }

    public static DateTime getDateTimeFromLong(int year, int month, int day) {
        DateTime dateTime = new DateTime(String.valueOf(year) + "-" + String.valueOf(month) +
                "-" + String.valueOf(day));
        return dateTime;
    }

    public static long getLongDateValue(int year, int month, int day, int plusDays) {
        return plusDays > 0 ? getDateTimeFromLong(year, month, day).plusDays(plusDays).getMillis() :
                getLongDateValue(year, month, day);
    }

    public static long getLongDateValue(long date, int plusDays) {
        if (plusDays <= 0) {
            new DateTime(date).minus(plusDays).getMillis();
        }
        return new DateTime(date).plusDays(plusDays).getMillis();
    }

    private static void dateConverter() {
        String dateString = "2016-07-01";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateString);
            System.out.println("date: " + date);

            long dateLongValue = date.getTime();
            System.out.println("dateLong: " + dateLongValue);

            date = new Date(1451577600000L);
            System.out.println("dateFromLong: " + date);


        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        long dateMillionSeconds = System.currentTimeMillis();

        DateTime date = getDateTime(dateMillionSeconds);
        System.out.println("date: " + date);

        int year = date.getYear();
        int month = date.getMonthOfYear();
        int day = date.getDayOfMonth();
        int hour = date.getHourOfDay();
        int minute = date.getMinuteOfHour();


        dateConverter();
        System.out.println(getLongDateValue(2016, 6, 30, 1));
    }
}
