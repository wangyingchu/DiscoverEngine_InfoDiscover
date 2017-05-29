package com.infoDiscover.solution.common.util;

import com.infoDiscover.infoDiscoverEngine.util.config.PropertyHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun.
 */
public class PrefixSetting {

    public final static String PREFIX = "prefix";

    private static Map<String, String> prefixMap = new HashMap<>();

    public static Map<String, String> getPrefixMap() {

        if (prefixMap == null || prefixMap.size() == 0) {
            prefixMap.put(PREFIX, getDefaultPrefix());
        }

        return prefixMap;
    }

    public void setPrefixMap(String prefix) {
        prefixMap.put(PREFIX, normalizePrefix(prefix));
    }

    public static String getDefaultPrefix() {
        Object prefixObject = PropertyHandler.getPropertyValue(PropertyHandler
                .DISCOVER_DEFAULT_PREFIX);
        String prefixValue = prefixObject == null ? "" : prefixObject.toString();
        return normalizePrefix(prefixValue);
    }

    public static String normalizePrefix(String prefix) {
        if (prefix == null || prefix.trim().equals("")) {
            return prefix;
        }
        return prefix.endsWith("_") ? prefix.toUpperCase() : prefix.toUpperCase() + "_";
    }

    public static String getFactTypeWithPrefix(String prefix, String factType) {
        return PrefixSetting.normalizePrefix(prefix) + factType;
    }

    public static String removePrefix(String prefix, String factType) {
        String normalizePrefix = prefix;
        if (prefix.indexOf("_") == -1) {
            normalizePrefix += "_";
        }
        return factType.substring(normalizePrefix.length());
    }
}
