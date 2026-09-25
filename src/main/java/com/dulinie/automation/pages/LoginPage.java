package com.dulinie.automation.pages;

import com.dulinie.automation.config.ConfigManager;
import com.dulinie.automation.driver.DriverManager;
import com.dulinie.automation.utils.WaitUtils;
import org.openqa.selenium.By;


public class LoginPage {

    //Definning Object Repository for Login Page
    private final By username = By.name("username");
    private final By password = By.name("password");
    private final By loginButton = By.xpath("//button[@type='submit']");
    private final By orangeHrmLogo =By.xpath("//img[@alt='company-branding']");


    public void loginWithDefaultCredentials() {
        // Get Data from config factory
        String user = ConfigManager.getConfig().username();
        String pass = ConfigManager.getConfig().password();

        // Pass them into your main interaction method
        login(user, pass);
    }

    public void login(String user, String pass) {


        // Wait for fields to be visible before interacting
        WaitUtils.waitForElementToBeVisible(DriverManager.getDriver(), username).sendKeys(user);
        WaitUtils.waitForElementToBeVisible(DriverManager.getDriver(), password).sendKeys(pass);

        // Wait for the button to be clickable before clicking
        WaitUtils.waitForElementToBeClickable(DriverManager.getDriver(), loginButton).click();


    }

    public boolean isLogoDisplayed()
    {
        try{
            return WaitUtils.waitForElementToBeVisible(DriverManager.getDriver(), orangeHrmLogo).isDisplayed();


        } catch (Exception e) {
            return false;
        }

    }

    public String validateLoginPageTitle(){
        return DriverManager.getDriver().getTitle();
    }
    }




