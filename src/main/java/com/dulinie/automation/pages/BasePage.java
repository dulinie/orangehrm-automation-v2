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

        protected void click(By locator) {
            waitClickable(locator).click();
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
            WebElement element = waitVisible(locator); // Evaluates and waits EXACTLY once
            element.clear();
            element.sendKeys(text);
    }

    }

