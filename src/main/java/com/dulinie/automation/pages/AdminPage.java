package com.dulinie.automation.pages;

import com.dulinie.automation.driver.DriverManager;
import com.dulinie.automation.utils.WaitUtils;
import org.openqa.selenium.By;

public class AdminPage {

    private final By adminHeader = By.xpath("//h6[text() ='Admin']");

    private final By addUserButton = By.xpath("//button[@type='button' and contains(normalize-space(.), 'Add')]");
    private final By userNameSearchInput = By.xpath("//label[normalize-space()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By searchButton = By.xpath("//button[@type='submit' and normalize-space()='Search']");

     public String validateAdminPageTitle() {
        return DriverManager.getDriver().getTitle();


    }

    public boolean isAdminPageHeaderDisplayed() {
            try{
                return WaitUtils.waitForElementToBeVisible(DriverManager.getDriver(), adminHeader).isDisplayed();

            } catch (Exception e) {
                return false;
            }

        }


    public String getAdminHeaderName(){
        try{
            return (WaitUtils.waitForElementToBeVisible(DriverManager.getDriver(),adminHeader)).getText();




        } catch (Exception e) {
            return "";
        }

    }

    public void navigateAddUser(){
        WaitUtils.waitForElementToBeClickable(DriverManager.getDriver(),addUserButton).click();

    }

    public void searchUserByUsername(String username) {
        WaitUtils.waitForElementToBeVisible(DriverManager.getDriver(), userNameSearchInput).sendKeys(username);
        WaitUtils.waitForElementToBeClickable(DriverManager.getDriver(), searchButton).click();
    }
}