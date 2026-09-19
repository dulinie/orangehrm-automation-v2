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
        loginPage.loginWithDefaultCredentials();

    }


    @Test
    public void testNavigationToAdmin() {
        dashboardPage = new DashboardPage();
        adminPage = new AdminPage();
        dashboardPage.clickAdminMenu();
        boolean isAdminHeaderVisible = adminPage.isAdminPageHeaderDisplayed();
        Assert.assertTrue(isAdminHeaderVisible, "Failed to navigate to the Admin page. Header not found.");
    }

    @Test(priority = 1,description = "Verify that the Admin Heading is displayed in the Admin page")
    public void verifyAdminPageHeaderDisplay() {
        dashboardPage = new DashboardPage();
        adminPage = new AdminPage();
        dashboardPage.clickAdminMenu();
        boolean isAdminHeaderDisplayed = adminPage.isAdminPageHeaderDisplayed();
        Assert.assertTrue(isAdminHeaderDisplayed,"OrangeHRM Admin header was not displayed on the Admin page.");
    }
}
