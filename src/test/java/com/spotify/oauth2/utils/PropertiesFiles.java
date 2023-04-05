package com.spotify.oauth2.utils;

import java.io.*;
import java.util.Properties;

public class PropertiesFiles {

    public static Properties propertiesLoader(String filePath) {
        Properties properties = new Properties();
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            try {
                properties.load(bufferedReader);
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to load file from path " + filePath);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("File not find in path " + filePath);
        }
        return properties;
    }
}
