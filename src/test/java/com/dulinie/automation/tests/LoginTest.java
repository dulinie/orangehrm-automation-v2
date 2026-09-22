package com.dulinie.automation.tests;

import com.dulinie.automation.base.BaseTest;
import com.dulinie.automation.pages.DashboardPage;
import com.dulinie.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;


    @Test(priority = 1,description = "Verify that the OrangeHRM logo is displayed on the login page")
    public void verifyLoginPageLogo() {
        loginPage = new LoginPage();
        boolean isLogoDisplayed = loginPage.isLogoDisplayed();
        Assert.assertTrue(isLogoDisplayed,"OrangeHRM logo was not displayed on the login page.");
    }

    @Test(priority = 2, description = "Verify the login page title is correct")
    public void verifyLoginPageTitle(){
        loginPage = new LoginPage();
        String pageTitle = loginPage.validateLoginPageTitle();
        Assert.assertEquals(pageTitle,"OrangeHRM", "The login page title does not match.");
    }

    @Test(priority = 3, description = "Verify successful login to the application ")
    public void verifyLogin(){
        loginPage = new LoginPage();
        dashboardPage = new DashboardPage();

        loginPage.loginWithDefaultCredentials();
        boolean isDashboardHeaderVisible = dashboardPage.isDashboardHeaderDisplayed();
        Assert.assertTrue(isDashboardHeaderVisible, "Failed to navigate to the Dashboard page. Header not found.");

    }
}
