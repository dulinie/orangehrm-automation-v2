package com.dulinie.automation.config;

import org.aeonbits.owner.ConfigFactory;

public class ConfigManager {

        private static FrameworkConfig config;

        private ConfigManager() {} // Prevent instantiation

        public static FrameworkConfig getConfig() {
            if (config == null) {

                // Debug print to track which environment variable Java is reading
                System.out.println("======> INITIALIZING CONFIG FOR ENV: " + System.getProperty("env", "qa"));


                config = ConfigFactory.create(FrameworkConfig.class);
            }
            return config;
        }
    }


