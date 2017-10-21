package com.infoDiscover.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun.
 */
public class ReflectHelper {

    private final static Logger logger = LogManager.getLogger(ReflectHelper.class);

    public static List<String> getFields(Class clazz) {
        List<String> list = new ArrayList<>();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            logger.info("fieldName: " + name);
            list.add(name);
        }

        return list;
    }
}
