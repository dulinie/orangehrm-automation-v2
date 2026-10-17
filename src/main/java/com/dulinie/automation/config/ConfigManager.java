package com.dulinie.automation.config;

import org.aeonbits.owner.ConfigFactory;

public final class ConfigManager {

        private static FrameworkConfig config;

        private ConfigManager() {} // Prevent instantiation

        public static synchronized FrameworkConfig getConfig() {
            if (config == null) {

                FrameworkConfig loaded = ConfigFactory.create(FrameworkConfig.class);
                if (loaded.url() == null || loaded.url().isBlank()) {
                    throw new IllegalStateException("Missing required config key: url");
                }
                if (loaded.username() == null || loaded.username().isBlank()) {
                    throw new IllegalStateException("Missing required config key: username");
                }
                if (loaded.password() == null || loaded.password().isBlank()) {
                    throw new IllegalStateException("Missing required config key: password");
                }
                config = loaded;
            }
            return config;

        }
    }


