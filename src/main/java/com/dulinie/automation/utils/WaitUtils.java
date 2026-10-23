package com.dulinie.automation.utils;

import com.dulinie.automation.config.ConfigManager;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {

    private static long getTimeout() {
        return ConfigManager.getConfig().timeout();
    }

    private static WebDriverWait getWait(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(getTimeout()));
        wait.pollingEvery(Duration.ofMillis(500));
        wait.ignoring(NoSuchElementException.class);
        wait.ignoring(StaleElementReferenceException.class);

        return wait; // Return the cleanly configured WebDriverWait object
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

    public static boolean waitForElementToBeInvisible(WebDriver driver, By locator, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.pollingEvery(Duration.ofMillis(250))
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static void waitForUrlToContain(WebDriver driver, String urlFraction, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.urlContains(urlFraction));
    }
}

