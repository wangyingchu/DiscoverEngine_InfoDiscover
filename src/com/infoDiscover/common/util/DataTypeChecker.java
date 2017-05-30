package com.infoDiscover.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun.
 */
public class DataTypeChecker {

    public static boolean isNumericType(String propertyType) {
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
}
