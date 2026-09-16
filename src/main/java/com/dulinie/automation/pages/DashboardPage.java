package com.dulinie.automation.pages;

import com.dulinie.automation.driver.DriverManager;
import com.dulinie.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage {

    private final WebDriver driver;

    private final By dashboardHeader = By.xpath("//h6[text()='Dashboard']");
    private final By adminMenu = By.xpath("//span[text()='Admin']");
    private final By pimMenu = By.xpath("//span[text()='PIM']");
    private final By leaveMenu =By.xpath("//span[text()='Leave']");

    public DashboardPage(){
        driver = DriverManager.getDriver();

    }

    public String validateDashboardPageTitle() {
        return driver.getTitle();


    }

    public boolean isDashboardHeaderDisplayed() {
        {
            try{
                return WaitUtils.waitForElementToBeVisible(driver, dashboardHeader).isDisplayed();

            } catch (Exception e) {
                return false;
            }

        }
    }

    public AdminPage clickAdminMenu() {
        WaitUtils.waitForElementToBeClickable(driver, adminMenu).click();
        return new AdminPage();

    }



}
