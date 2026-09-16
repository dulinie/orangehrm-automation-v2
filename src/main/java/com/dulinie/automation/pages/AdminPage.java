package com.dulinie.automation.pages;

import com.dulinie.automation.driver.DriverManager;
import com.dulinie.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminPage {
    private final WebDriver driver;


    private final By adminHeader = By.xpath("//h6[text()='Admin']");
    private final By addEmployeeButton = By.xpath("//button[@type='button' and text()=' Add ']");

    public AdminPage() {
        driver = DriverManager.getDriver();

    }

    public String validateAdminPageTitle() {
        return driver.getTitle();


    }

    public boolean isAdminPageHeaderDisplayed() {
        {
            try{
                return WaitUtils.waitForElementToBeVisible(driver, adminHeader).isDisplayed();

            } catch (Exception e) {
                return false;
            }

        }
    }
}