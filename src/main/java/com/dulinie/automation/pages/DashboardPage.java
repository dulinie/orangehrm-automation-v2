package com.dulinie.automation.pages;

import org.openqa.selenium.By;

public class DashboardPage extends BasePage {

    private final By dashboardHeader = By.xpath("//h6[text()='Dashboard']");
    private final By adminMenu = By.xpath("//span[text()='Admin']");
    private final By pimMenu = By.xpath("//span[text()='PIM']");
    private final By leaveMenu = By.xpath("//span[text()='Leave']");

    public String getDashboardPageTitle() {
        return getPageTitle();
    }

    public String validateDashboardPageTitle() {
        return getDashboardPageTitle();
    }

    public boolean isDashboardHeaderDisplayed() {
        return isDisplayedSafely(dashboardHeader);
    }

    public String getDashboardHeaderText() {
        return waitVisible(dashboardHeader).getText();
    }

    public String getDashboardHeaderName() {
        return getDashboardHeaderText();
    }

    public void clickAdminMenu() {
        waitVisible(adminMenu).click();
    }

    public void openAdminPage() {
        clickAdminMenu();
    }
}
