package com.dulinie.automation.tests;

import com.dulinie.automation.base.BaseTest;
import com.dulinie.automation.pages.AdminPage;
import com.dulinie.automation.pages.DashboardPage;
import com.dulinie.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DashboardTest extends BaseTest {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private AdminPage adminPage;


    @BeforeMethod
    public void initializePage() {
        // Initialize the page object once before every test method execution
        loginPage = new LoginPage();
        loginPage.loginWithDefaultCredentials();

            }

    @Test(priority = 1,description = "Verify that the Dashboard Heading is displayed in the Dashboard page")
    public void verifyDashboardHeaderDisplay() {
        dashboardPage = new DashboardPage();
        boolean isHeaderDispayed = dashboardPage.isDashboardHeaderDisplayed();
        Assert.assertTrue(isHeaderDispayed,"OrangeHRM Dashboard header was not displayed on the dashboard page.");
    }

    @Test(priority = 2, description = "Verify that the login page title is correct")
    public void verifyDashboardPageTitle(){
        dashboardPage = new DashboardPage();
        String pageTitle = dashboardPage.validateDashboardPageTitle();
        Assert.assertEquals(pageTitle,"OrangeHRM", "The dashboard page title does not match.");
    }


    }


