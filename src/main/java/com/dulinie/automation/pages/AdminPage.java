package com.dulinie.automation.pages;

import com.dulinie.automation.driver.DriverManager;
import com.dulinie.automation.utils.WaitUtils;
import org.openqa.selenium.By;

public class AdminPage {

    private final By adminHeader = By.xpath("//h6[text() ='Admin']");
    private final By addEmployeeButton = By.xpath("//button[@type='button' and text()=' Add ']");

     public String validateAdminPageTitle() {
        return DriverManager.getDriver().getTitle();


    }

    public boolean isAdminPageHeaderDisplayed() {
        {
            try{
                return WaitUtils.waitForElementToBeVisible(DriverManager.getDriver(), adminHeader).isDisplayed();

            } catch (Exception e) {
                return false;
            }

        }
    }

    public String validateHeader(){
        return (WaitUtils.waitForElementToBeVisible(DriverManager.getDriver(),adminHeader)).getText();
    }

    public void verifyNavigateAddUser(){
        WaitUtils.waitForElementToBeClickable(DriverManager.getDriver(),addEmployeeButton).click();

    }
}