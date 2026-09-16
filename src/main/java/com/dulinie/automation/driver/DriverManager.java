package com.dulinie.automation.driver;


import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.dulinie.automation.config.ConfigManager;


public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverManager(){
        // prevent instantiation
    }


    public static WebDriver getDriver(){
        return driver.get();

    }


    public static void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    public static void initializeDriver() {
        String browser = ConfigManager.getConfig().browser();
        String url = ConfigManager.getConfig().url();


        if(browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            setDriver(new ChromeDriver());
        }else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            setDriver(new FirefoxDriver());
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            setDriver(new EdgeDriver());
        }
        else
            throw new RuntimeException("Unsupported browser specified in config: " + browser);

        getDriver().manage().window().maximize();
        getDriver().get(url);

    }


        public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit(); // Closes the browser
            driver.remove();     // Clears the ThreadLocal memory
        }
    }

    }

