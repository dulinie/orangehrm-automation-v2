package com.dulinie.automation.driver;


import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.dulinie.automation.config.ConfigManager;
import org.openqa.selenium.firefox.FirefoxOptions;


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

        // Hide verbose Selenium standard logger outputs from crowding your pipeline logs
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.SEVERE);

        String browser = ConfigManager.getConfig().browser();
        String url = ConfigManager.getConfig().url();

        // Check if headless mode is requested via command line parameter (-Dheadless=true)
        boolean isHeadless = System.getProperty("headless", "false").equalsIgnoreCase("true");

        if(browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            if (isHeadless) {
                options.addArguments("--headless=new");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            }
            setDriver(new ChromeDriver(options));

        }else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            if (isHeadless) {
                options.addArguments("--headless");
                options.addArguments("--window-size=1920,1080");
            }
            setDriver(new FirefoxDriver(options));

        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            if (isHeadless) {
                options.addArguments("--headless=new");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            }
            setDriver(new EdgeDriver(options));

        }
        else
            throw new RuntimeException("Unsupported browser specified in config: " + browser);

        // Window maximizing only takes functional effect if the session is visual
        if (!isHeadless) {
            getDriver().manage().window().maximize();
        }

              getDriver().get(url);

    }


        public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit(); // Closes the browser
            driver.remove();     // Clears the ThreadLocal memory
        }
    }

    }

