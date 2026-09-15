package com.dulinie.automation.tests;

import com.dulinie.automation.base.BaseTest;
import com.dulinie.automation.pages.LoginPage;
import com.dulinie.automation.utils.PropertyReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {


    @Test(priority = 1)
    public void verifyLogoDisplay() {
        LoginPage loginpage = new LoginPage();
        boolean isLogoDispayed = loginpage.isLogoDisplayed();
        Assert.assertTrue(isLogoDispayed);
    }

    @Test(priority = 2)
    public void verifyLoginPageTitle(){
        LoginPage loginpage = new LoginPage();
        String pageTitle = loginpage.validateLoginPageTitle();
        Assert.assertEquals(pageTitle,"OrangeHRM");
    }

    @Test(priority = 3)
    public void verifyLogin(){
        LoginPage loginpage = new LoginPage();
        loginpage.loginWithDefaultCredentials();
    }
}
