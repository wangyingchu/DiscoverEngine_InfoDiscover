package com.infoDiscover.common.util;

import java.util.List;
import java.util.UUID;

/**
 * Created by sun.
 */
public class StringUtil {

    public static String addDoubleQuotation(String value) {
        return "\"" + value + "\"";
    }

    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String removeFirstAndLastChar(String value) {
        return value.substring(1, value.length() -1);
    }

    public static String[] listToArray(List<String> strings) {
        return strings.toArray(new String[strings.size()]);
    }
}
