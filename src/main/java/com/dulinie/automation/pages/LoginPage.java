package com.dulinie.automation.pages;

import com.dulinie.automation.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class LoginPage {

    private WebDriver driver;

    //Definning Object Repository for Login Page
    private By username = By.name("username");
    private By password = By.name("password");
    private By loginButton = By.xpath("//button[@type='submit']");
    private By orangeHrmLogo =By.xpath("//img[@alt='company-branding']");



    public LoginPage(){
        driver = DriverManager.getDriver();

    }

    public void login(String user, String pass) {
        driver.findElement(username).sendKeys(user);
        driver.findElement(password).sendKeys(pass);
        driver.findElement(loginButton).click();
    }

    public boolean isLogoDisplayed()
    {
        return driver.findElement(orangeHrmLogo).isDisplayed();
    }

    public String validateLoginPageTitle(){
        return driver.getTitle();
    }
    }




