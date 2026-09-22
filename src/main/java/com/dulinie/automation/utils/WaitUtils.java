package com.dulinie.automation.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import com.dulinie.automation.config.ConfigManager;



public class WaitUtils {

    // Thread-safe dynamic lookups from your config file
     private static long getTimeout() {
        // Setting up your wait timeouts without parsing Strings manually
        return ConfigManager.getConfig().timeout();

    }


        private static WebDriverWait getWait(WebDriver driver) {
            return new WebDriverWait(driver, Duration.ofSeconds(getTimeout()));
        }


        public static WebElement waitForElementToBeVisible(WebDriver driver, By locator) {
            return getWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
        }

        public static WebElement waitForElementToBeClickable(WebDriver driver, By locator) {
            return getWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
        }

        public static List<WebElement> waitForElementsToBeVisible(WebDriver driver, By locator) {
            return getWait(driver).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        }

        public static boolean waitForElementToBeInvisible(WebDriver driver, By locator) {
            return getWait(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));
        }

    public static WebElement waitForElementWithPolling(WebDriver driver, By locator) {
        // Configure a FluentWait that polls every 500ms and ignores standard flakiness exceptions
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(getTimeout()))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(new Function<WebDriver, WebElement>() {
                    @Override
                    public WebElement apply(WebDriver d) {
                        WebElement element = d.findElement(locator);
                        // Ensure it's fully interactable before returning it
                        if (element.isDisplayed() && element.isEnabled()) {
                            return element;
                        }
                        return null;
                    }
                });
    }

}

