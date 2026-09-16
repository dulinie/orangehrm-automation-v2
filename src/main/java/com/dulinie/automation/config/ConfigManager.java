package com.dulinie.automation.config;

import org.aeonbits.owner.ConfigFactory;

public class ConfigManager {

        private static FrameworkConfig config;

        private ConfigManager() {} // Prevent instantiation

        public static FrameworkConfig getConfig() {
            if (config == null) {

                config = ConfigFactory.create(FrameworkConfig.class);
            }
            return config;
        }
    }


