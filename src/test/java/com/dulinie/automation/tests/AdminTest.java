package com.dulinie.automation.tests;

import com.dulinie.automation.base.BaseTest;
import com.dulinie.automation.pages.AdminPage;
import com.dulinie.automation.pages.DashboardPage;
import com.dulinie.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AdminTest extends BaseTest {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private AdminPage adminPage;


    @BeforeMethod
    public void initializePage() {
        // Initialize the page object once before every test method execution
        loginPage = new LoginPage();
        dashboardPage = new DashboardPage();
        adminPage = new AdminPage();

        loginPage.loginWithDefaultCredentials();

    }

    @Test(priority = 1,description = "Verify the Admin heading is displayed in the Admin page")
    public void verifyAdminPageHeaderDisplayed() {
        dashboardPage.clickAdminMenu();
        boolean isAdminHeaderDisplayed = adminPage.isAdminPageHeaderDisplayed();
        Assert.assertTrue(isAdminHeaderDisplayed,"OrangeHRM Admin header was not displayed on the Admin page.");
    }

    @Test(priority = 2, description = "Verify Admin page title is correct")
    public void verifyAdminPageTitle() {
        dashboardPage.clickAdminMenu();

        String isAdminTitleVisible = adminPage.validateAdminPageTitle();
        Assert.assertEquals(isAdminTitleVisible,"OrangeHRM");

    }

    @Test(priority = 3, description = "Verify Admin Header is correct")
    public void verifyAdminPageHeaderName(){
        dashboardPage.clickAdminMenu();
        String headerName = adminPage.getAdminHeaderName();
        Assert.assertEquals(headerName,"Admin", "Admin header is incorrect");
    }
}
