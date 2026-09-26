package com.dulinie.automation.pages;

import com.dulinie.automation.driver.DriverManager;
import com.dulinie.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class BasePage {

        protected WebElement waitVisible(By locator) {
            return WaitUtils.waitForElementToBeVisible(DriverManager.getDriver(), locator);
        }

        protected WebElement waitClickable(By locator) {
            return WaitUtils.waitForElementToBeClickable(DriverManager.getDriver(), locator);
        }

        protected boolean isDisplayedSafely(By locator) {
            try {
                return waitVisible(locator).isDisplayed();
            } catch (Exception e) {
                return false;
            }
        }

        protected String getPageTitle() {
            return DriverManager.getDriver().getTitle();
        }

        protected void type(By locator, String text) {
            waitClickable(locator).clear();
            waitClickable(locator).sendKeys(text);
    }

    }

