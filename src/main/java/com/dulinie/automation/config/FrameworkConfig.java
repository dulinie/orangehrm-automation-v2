package com.dulinie.automation.config;
import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "classpath:config/${env}.properties",
        "classpath:config/qa.properties"      // Baseline fallback if ${env} parameter is missing
})


public interface FrameworkConfig extends Config{

        @Key("browser")
        @DefaultValue("chrome")
        String browser();

        @Key("url")
        String url();

        @Key("username")
        String username();

        @Key("password")
        String password();

        @Key("explicitWaitTimeout")
        @DefaultValue("10")
        int timeout();  // Owner library automatically converts this string to an int!

        @Key("headless")
        @DefaultValue("false")
        boolean headless();

    }

