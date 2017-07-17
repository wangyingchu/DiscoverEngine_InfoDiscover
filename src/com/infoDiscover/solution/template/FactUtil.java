package com.infoDiscover.solution.template;

import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.Property;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sun on 7/17/17.
 */
public class FactUtil {

    public static Map<String, Object> getPropertiesFromFact(Fact fact) {

        Map<String, Object> map = new HashMap<>();

        List<Property> propertiesList = fact.getProperties();
        for(Property property: propertiesList) {
            String propertyName = property.getPropertyName();
            Object propertyValue = property.getPropertyValue();
            map.put(propertyName, propertyValue);
        }

        return map;
    }
}
