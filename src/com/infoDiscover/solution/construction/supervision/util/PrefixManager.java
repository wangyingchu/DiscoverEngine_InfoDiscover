package com.infoDiscover.solution.construction.supervision.util;

/**
 * Created by sun.
 */
public class PrefixManager {
    private String prefix;

    public PrefixManager(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String normalizePrefix(String prefix) {
        if(prefix == null || prefix.trim().equals("")) {
            return  prefix;
        }
        return prefix.endsWith("_") ? prefix : prefix + "_";
    }
}
