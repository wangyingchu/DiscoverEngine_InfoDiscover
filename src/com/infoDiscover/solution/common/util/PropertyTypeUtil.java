package com.infoDiscover.solution.common.util;

import com.infoDiscover.infoDiscoverEngine.dataMart.PropertyType;

/**
 * Created by sun.
 */
public class PropertyTypeUtil {

    public static PropertyType getPropertyType(String propertyType) {
        if (propertyType.equalsIgnoreCase("String")) {
            return PropertyType.STRING;
        } else if (propertyType.equalsIgnoreCase("Int") || propertyType.equalsIgnoreCase
                ("Integer")) {
            return PropertyType.INT;
        } else if (propertyType.equalsIgnoreCase("Short")) {
            return PropertyType.SHORT;
        } else if (propertyType.equalsIgnoreCase("Long")) {
            return PropertyType.LONG;
        } else if (propertyType.equalsIgnoreCase("Float")) {
            return PropertyType.FLOAT;
        } else if (propertyType.equalsIgnoreCase("Double")) {
            return PropertyType.DOUBLE;
        } else if (propertyType.equalsIgnoreCase("Boolean") || propertyType.equalsIgnoreCase
                ("bool")) {
            return PropertyType.BOOLEAN;
        } else if (propertyType.equalsIgnoreCase("Date") || propertyType.equalsIgnoreCase
                ("DateTime")) {
            return PropertyType.DATE;
        }

        return null;
    }
}
