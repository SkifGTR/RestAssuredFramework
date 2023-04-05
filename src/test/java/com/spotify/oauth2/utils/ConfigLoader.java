package com.spotify.oauth2.utils;

import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader() {
        properties = PropertiesFiles.propertiesLoader("src/test/resources/config.properties");
    }

    public static ConfigLoader getInstance() {
        if (configLoader == null) {
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public String getClientId() {
        String property = properties.getProperty("client_id");
        if (property != null) {
            return property;
        } else {
            throw new RuntimeException("client_id property is not set in config file");
        }
    }

    public String getClientSecret() {
        String property = properties.getProperty("client_secret");
        if (property != null) {
            return property;
        } else {
            throw new RuntimeException("client_secret property is not set in config file");
        }
    }

    public String getClientRefreshToken() {
        String property = properties.getProperty("refresh_token");
        if (property != null) {
            return property;
        } else {
            throw new RuntimeException("refresh_token property is not set in config file");
        }
    }

    public String getClientGrantType() {
        String property = properties.getProperty("grant_type");
        if (property != null) {
            return property;
        } else {
            throw new RuntimeException("grant_type property is not set in config file");
        }
    }

    public String getClientUserId() {
        String property = properties.getProperty("user_id");
        if (property != null) {
            return property;
        } else {
            throw new RuntimeException("user_id property is not set in config file");
        }
    }
}
