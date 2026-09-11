package com.dulinie.automation.tests;

import com.dulinie.automation.base.BaseTest;
import com.dulinie.automation.pages.LoginPage;
import com.dulinie.automation.utils.PropertyReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    LoginPage loginpage ;

    @BeforeMethod
    public void loginSetUp(){
        loginpage = new LoginPage();
   }

    @Test(priority = 1)
    public void verifyLogoDisplay() {
        boolean isLogoDispayed = loginpage.isLogoDisplayed();
        Assert.assertTrue(isLogoDispayed);
    }

    @Test(priority = 2)
    public void verifyLoginPageTitle(){
        String pageTitle = loginpage.validateLoginPageTitle();
        Assert.assertEquals(pageTitle,"OrangeHRM");
    }

    @Test(priority = 3)
    public void verifyLogin(){
        String uname = PropertyReader.getProperty("username");
        String password = PropertyReader.getProperty("password");
        loginpage.login(uname,password);
    }
}
