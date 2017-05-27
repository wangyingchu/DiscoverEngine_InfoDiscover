package com.infoDiscover.common.util;

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
}
