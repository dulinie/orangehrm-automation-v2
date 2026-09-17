package com.dulinie.automation.tests;

import com.dulinie.automation.base.BaseTest;
import com.dulinie.automation.pages.AdminPage;
import com.dulinie.automation.pages.DashboardPage;
import com.dulinie.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    @BeforeMethod
    public void initializePage() {
        // Initialize the page object once before every test method execution
        //loginPage = new LoginPage();
       // dashboardPage= new DashboardPage();

    }

    @Test(priority = 1,description = "Verify that the OrangeHRM logo is displayed on the login page")
    public void verifyLogoDisplay() {
        loginPage = new LoginPage();
        boolean isLogoDispayed = loginPage.isLogoDisplayed();
        Assert.assertTrue(isLogoDispayed,"OrangeHRM logo was not displayed on the login page.");
    }

    @Test(priority = 2, description = "Verify that the login page title is correct")
    public void verifyLoginPageTitle(){
        loginPage = new LoginPage();
        String pageTitle = loginPage.validateLoginPageTitle();
        Assert.assertEquals(pageTitle,"OrangeHRM", "The login page title does not match.");
    }

    @Test(priority = 3, description = "Verify successful login to the application ")
    public void verifyLogin(){
        loginPage = new LoginPage();
        dashboardPage= new DashboardPage();

        loginPage.loginWithDefaultCredentials();
        boolean isDashboardHeaderVisible = dashboardPage.isDashboardHeaderDisplayed();
        Assert.assertTrue(isDashboardHeaderVisible, "Failed to navigate to the Dashboard page. Header not found.");

    }
}
