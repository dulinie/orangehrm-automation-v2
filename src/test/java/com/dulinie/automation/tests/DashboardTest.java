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
        loginPage = new LoginPage();
        dashboardPage = new DashboardPage();
        loginPage.loginWithDefaultCredentials();

            }

    @Test(priority = 1,description = "Verify the Dashboard Heading is displayed in the Dashboard page")
    public void verifyDashboardPageHeaderDisplayed() {
        boolean isHeaderDisplayed = dashboardPage.isDashboardHeaderDisplayed();
        Assert.assertTrue(isHeaderDisplayed,"OrangeHRM Dashboard heading is not displayed on the Dashboard page.");
    }

    @Test(priority = 2, description = "Verify the Dashboard page title is correct")
    public void verifyDashboardPageTitle(){
        String pageTitle = dashboardPage.validateDashboardPageTitle();
        Assert.assertEquals(pageTitle,"OrangeHRM", "The dashboard page title does not match.");
    }

    @Test(priority = 3,description = "Verify the Dashboard Heading name is correct")
    public void verifyDashboardPageHeaderName() {
        String headerName = dashboardPage.isDashboardHeaderName();
        Assert.assertEquals(headerName,"Dashboard","OrangeHRM Dashboard heading is not displayed on the Dashboard page.");
    }


    }


