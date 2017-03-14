package com.infoDiscover.infoDiscoverEngine.util.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyHandler {

    private final static Logger logger = LogManager.getLogger(PropertyHandler.class);

    private static Properties _properties;
    public static String DISCOVER_ENGINE_SERVICE_LOCATION = "DISCOVER_ENGINE_SERVICE_LOCATION";
    public static String DISCOVER_ENGINE_ADMIN_ACCOUNT = "DISCOVER_ENGINE_ADMIN_ACCOUNT";
    public static String DISCOVER_ENGINE_ADMIN_PWD = "DISCOVER_ENGINE_ADMIN_PWD";
    public static String DISCOVER_SPACE_DATABASE_TYPE = "DISCOVER_SPACE_DATABASE_TYPE";
    public static String DISCOVER_SPACE_STORAGE_MODE = "DISCOVER_SPACE_STORAGE_MODE";

    public static String getPropertyValue(String propertyName) {
        String InfoDiscoverEngine_Home = System.getenv("INFODISCOVER_HOME");
        String configPath = "";
        if (InfoDiscoverEngine_Home == null) {
            logger.error("INFODISCOVER_HOME is null, please configure INFODISCOVER_HOME in" +
                    " " +
                    "the System environment");
            configPath = RuntimeEnvironmentHandler.getApplicationRootPath() +
                    "InfoDiscoverEngineCfg.properties";
        } else {
            configPath = InfoDiscoverEngine_Home + "/conf/InfoDiscoverEngineCfg.properties";
        }

        logger.info("configPath: " + configPath);
        _properties = new Properties();
        try {
            _properties.load(new FileInputStream(configPath));
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
        }
        return _properties.getProperty(propertyName);
    }
}
