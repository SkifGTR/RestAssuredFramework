package com.spotify.oauth2.utils;

import java.util.Properties;

public class DataLoader {
    private final Properties properties;
    private static DataLoader dataLoader;

    private DataLoader() {
        properties = PropertiesFiles.propertiesLoader("src/test/resources/data.properties");
    }

    public static DataLoader getInstance() {
        if (dataLoader == null) {
            dataLoader = new DataLoader();
        }
        return dataLoader;
    }

    public String getPlayListId() {
        String property = properties.getProperty("get_playlist_id");
        if (property != null) {
            return property;
        } else {
            throw new RuntimeException("get_playlist_id property is not set in data.config file");
        }
    }

    public String updatePlayListId() {
        String property = properties.getProperty("update_playlist_id");
        if (property != null) {
            return property;
        } else {
            throw new RuntimeException("update_playlist_id property is not set in data.config file");
        }
    }
}
