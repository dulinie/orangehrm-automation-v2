package com.dulinie.automation.pages;

import com.dulinie.automation.driver.DriverManager;
import com.dulinie.automation.models.SystemUser;
import com.dulinie.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;



public class AddUser {

       private final WebDriver driver;

        // Object Repository
        private final By userRoleDropdown = By.xpath("//label[text()='User Role']/../following-sibling::div//div[@class='oxd-select-text-input']");
        private final By employeeNameInput = By.xpath("//label[text()='Employee Name']/../following-sibling::div//input");
        private final By empNameOptions = By.xpath("//div[@role='listbox']//div[@role='option']");
        private final By statusDropdown = By.xpath("//label[text()='Status']/../following-sibling::div//div[@class='oxd-select-text-input']");
        private final By usernameInput = By.xpath("//form//label[text()='Username']/../following-sibling::div//input");
        private final By passwordInput = By.xpath("//label[text()='Password']/../following-sibling::div//input[@type='password']");
        private final By confirmPasswordInput = By.xpath("//label[text()='Confirm Password']/../following-sibling::div//input[@type='password']");
        private final By saveButton = By.xpath("//button[@type='submit' and normalize-space()='Save']");

        public AddUser() {
            this.driver = DriverManager.getDriver();
        }

        public String validateAddUserPageTitle() {
            return driver.getTitle();
        }

        /**
         * Fills out and submits the Add User form using the encapsulated SystemUser data model.
         * @param user The SystemUser entity containing the target test data.
         */
        public void addNewUser(SystemUser user) {
            // Handle OrangeHRM custom dropdowns using helper method
            selectDropdownValue(userRoleDropdown, user.getRole());

            // Handle autocomplete employee name lookup field
            WaitUtils.waitForElementToBeClickable(driver, employeeNameInput).sendKeys(user.getEmployeeName());
            WaitUtils.waitForElementWithPolling(driver, empNameOptions).click();

            selectDropdownValue(statusDropdown, user.getStatus());

            // Fill out remaining text fields using Lombok getters
            WaitUtils.waitForElementToBeClickable(driver, usernameInput).sendKeys(user.getUsername());
            WaitUtils.waitForElementToBeClickable(driver, passwordInput).sendKeys(user.getPassword());
            WaitUtils.waitForElementToBeClickable(driver, confirmPasswordInput).sendKeys(user.getConfirmPassword());

            WaitUtils.waitForElementToBeClickable(driver, saveButton).click();
        }

        /**
         * Private helper method to handle custom stylized div/role options dropdowns.
         */
        private void selectDropdownValue(By dropdownLocator, String visibleText) {
            WaitUtils.waitForElementToBeClickable(driver, dropdownLocator).click();
            By optionLocator = By.xpath("//div[@role='option' and normalize-space()='" + visibleText + "']");
            WaitUtils.waitForElementToBeClickable(driver, optionLocator).click();
        }
    }

