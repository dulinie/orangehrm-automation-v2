package com.dulinie.automation.pages;

import com.dulinie.automation.driver.DriverManager;
import com.dulinie.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import com.dulinie.automation.driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;


public class AddUser {

    private final WebDriver driver;

    //Definning Object Repository for Add User Page
    private final By userRole = By.xpath("//label[text()='User Role']/.." +
            "/following-sibling::div//div[@class='oxd-select-text-input']");
    private final By adminRole = By.xpath("//div[@role='listbox']//*[contains(text(), 'Admin')]");

    private final By employeeName = By.xpath("//label[text()" +
            "='Employee Name']/../following-sibling::div//input");
    private final By empNameOptions = By.xpath("//div[@role='listbox']//div[@role='option']");

    private final By status = By.xpath("//label[text()='Status']/.." +
            "/following-sibling::div//div[@class='oxd-select-text-input']");
    private final By statusOption = By.xpath("//div[@role='option' and normalize-space()='Enabled']");

    private final By username = By.xpath("//form//label[text()='Username']/.." +
            "/following-sibling::div//input");

    private final By password = By.xpath("//label[text()='Password']/.." +
            "/following-sibling::div//input[@type='password']");

    private final By confirmPassword = By.xpath("//label[text()='Confirm Password']/.." +
            "/following-sibling::div//input[@type='password']");
    private final By save = By.xpath("//button[@type='submit' and normalize-space()='Save']");


    public AddUser() {
        driver = DriverManager.getDriver();
    }

    public String validateAddUserPageTitle(){
        return driver.getTitle();
    }

    public void addNewUser(){
        WaitUtils.waitForElementToBeClickable(driver,userRole).click();
        WaitUtils.waitForElementToBeClickable(driver,adminRole).click();

        WaitUtils.waitForElementToBeClickable(driver,employeeName).sendKeys("John");
        WaitUtils.waitForElementWithPolling(driver,empNameOptions).click();

        WaitUtils.waitForElementToBeClickable(driver,status).click();
        WaitUtils.waitForElementToBeClickable(driver,statusOption).click();

        WaitUtils.waitForElementToBeClickable(driver,username).sendKeys("username");
        WaitUtils.waitForElementToBeClickable(driver,password).sendKeys("password1");
        WaitUtils.waitForElementToBeClickable(driver,confirmPassword).sendKeys("password1");

        WaitUtils.waitForElementToBeClickable(driver,save).click();

    }


}
