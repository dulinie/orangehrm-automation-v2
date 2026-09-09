package com.dulinie.automation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    private static final Properties prop = new Properties();
    static {


        try (InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream("config/config.properties")) {
            if (input == null) {
                throw new RuntimeException("config/config.properties not found on classpath");
            }
            prop.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Could not load config.properties file!", e);
        }

    }

public static String getProperty(String key) {
    // First check if a system property (command line) overrides it, otherwise use properties file
    return System.getProperty(key, prop.getProperty(key));
}

}
