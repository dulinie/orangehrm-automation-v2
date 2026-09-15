package com.dulinie.automation.pages;

import com.dulinie.automation.driver.DriverManager;
import com.dulinie.automation.utils.PropertyReader;
import com.dulinie.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class LoginPage {

    private final WebDriver driver;

    //Definning Object Repository for Login Page
    private final By username = By.name("username");
    private final By password = By.name("password");
    private final By loginButton = By.xpath("//button[@type='submit']");
    private final By orangeHrmLogo =By.xpath("//img[@alt='company-branding']");



    public LoginPage(){
        driver = DriverManager.getDriver();

    }

    public void loginWithDefaultCredentials() {
        // Use getEnvProperty so it automatically adapts to whatever environment is active
        String user = PropertyReader.getProperty("username");
        String pass = PropertyReader.getProperty("password");

        // Pass them into your main interaction method
        login(user, pass);
    }

    public void login(String user, String pass) {


        // Wait for fields to be visible before interacting
        WaitUtils.waitForElementToBeVisible(driver, username).sendKeys(user);
        WaitUtils.waitForElementToBeVisible(driver, password).sendKeys(pass);

        // Wait for the button to be clickable before clicking
        WaitUtils.waitForElementToBeClickable(driver, loginButton).click();
    }

    public boolean isLogoDisplayed()
    {
        try{
            return WaitUtils.waitForElementToBeInvisible(driver, orangeHrmLogo);

        } catch (Exception e) {
            return false;
        }

    }

    public String validateLoginPageTitle(){
        return driver.getTitle();
    }
    }




