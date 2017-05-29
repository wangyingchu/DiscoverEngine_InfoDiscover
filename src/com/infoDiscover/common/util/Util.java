package com.infoDiscover.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by sun.
 */
public class Util {

    public static String addDoubleQuotation(String value) {
        return "\"" + value + "\"";
    }

    public static int minInt(int range[]) {
        return range[0] < range[1] ? range[0] : range[1];
    }

    public static int maxInt(int range[]) {
        return range[0] < range[1] ? range[1] : range[0];
    }

    public static double minDouble(double range[]) {
        return range[0] < range[1] ? range[0] : range[1];
    }

    public static double maxDouble(double range[]) {
        return range[0] < range[1] ? range[1] : range[0];
    }

    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static boolean isNumbericType(String propertyType) {
        List<String> list = new ArrayList<>();
        list.add("INT");
        list.add("INTEGER");
        list.add("LONG");
        list.add("FLOAT");
        list.add("DOUBLE");

        return list.contains(propertyType.toUpperCase());
    }

    public static boolean isStringType(String propertyType) {
        return "String".equalsIgnoreCase(propertyType);
    }

    public static boolean isDateType(String propertyType) {
        return "Date".equalsIgnoreCase(propertyType) || "DateTime".equalsIgnoreCase(propertyType);
    }

    public static String removeFirstAndLastChar(String value) {
        return value.substring(1, value.length() -1);
    }

}
