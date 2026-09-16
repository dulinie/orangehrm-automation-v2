package com.dulinie.automation.config;
import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "classpath:config/${env}.properties", // Points to src/main/resources/config/qa.properties
        "classpath:config/qa.properties"      // Baseline fallback if ${env} parameter is missing
})


public interface FrameworkConfig extends Config{

        @Key("browser")
        @DefaultValue("chrome") // Fallback default if not explicitly provided
        String browser();

        @Key("url")
        String url();

        @Key("username")
        String username();

        @Key("password")
        String password();

        @Key("explicit.wait.timeout")
        @DefaultValue("10")    // Owner library automatically converts this string to an int!
        int timeout();

    }

