package com.dulinie.automation.pages;

import com.dulinie.automation.driver.DriverManager;
import com.dulinie.automation.utils.WaitUtils;
import org.openqa.selenium.By;

public class DashboardPage {

    private final By dashboardHeader = By.xpath("//h6[text()='Dashboard']");
    private final By adminMenu = By.xpath("//span[text()='Admin']");
    private final By pimMenu = By.xpath("//span[text()='PIM']");
    private final By leaveMenu =By.xpath("//span[text()='Leave']");


    public String validateDashboardPageTitle() {
        return DriverManager.getDriver().getTitle();


    }

    public boolean isDashboardHeaderDisplayed() {

        try{
            return (WaitUtils.waitForElementToBeVisible(DriverManager.getDriver(),dashboardHeader).isDisplayed());

        } catch (Exception e) {
            return false;
        }

    }

    public String getDashboardHeaderName() {

        try{
            return (WaitUtils.waitForElementToBeVisible(DriverManager.getDriver(),dashboardHeader)).getText();

        } catch (Exception e) {
            return "";
        }

    }

    public void clickAdminMenu() {
        WaitUtils.waitForElementToBeClickable(DriverManager.getDriver(), adminMenu).click();

    }



}
