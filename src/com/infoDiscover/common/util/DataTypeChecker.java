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
        list.add("SHORT");

        return list.contains(propertyType.toUpperCase());
    }

    public static boolean isStringType(String propertyType) {
        return "String".equalsIgnoreCase(propertyType);
    }

    public static boolean isBooleanType(String propertyType) {
        return "Boolean".equalsIgnoreCase(propertyType) || "Bool".equalsIgnoreCase(propertyType);
    }

    public static boolean isDateType(String propertyType) {
        return "Date".equalsIgnoreCase(propertyType) || "DateTime".equalsIgnoreCase(propertyType);
    }

    public static boolean isIntType(String propertyType) {
        return "Int".equalsIgnoreCase(propertyType) || "Integer".equalsIgnoreCase(propertyType);
    }

    public static boolean isLongType(String propertyType) {
        return "Long".equalsIgnoreCase(propertyType);
    }

    public static boolean isShortType(String propertyType) {
        return "Short".equalsIgnoreCase(propertyType);
    }

    public static boolean isFloatType(String propertyType) {
        return "Float".equalsIgnoreCase(propertyType);
    }

    public static boolean isDoubleType(String propertyType) {
        return "Double".equalsIgnoreCase(propertyType);
    }

}
