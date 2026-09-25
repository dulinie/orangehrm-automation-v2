package com.dulinie.automation.pages;

import com.dulinie.automation.driver.DriverManager;
import com.dulinie.automation.models.SystemUser;
import com.dulinie.automation.utils.WaitUtils;
import org.openqa.selenium.By;


public class AddUserPage {

        // Object Repository
        private final By addUserHeading = By.xpath("//h6[text()='Add User']");
        private final By userRoleDropdown = By.xpath("//label[text()='User Role']/../following-sibling::div//div[@class='oxd-select-text-input']");
        private final By employeeNameInput = By.xpath("//label[text()='Employee Name']/../following-sibling::div//input");
        private final By statusDropdown = By.xpath("//label[text()='Status']/../following-sibling::div//div[@class='oxd-select-text-input']");
        private final By usernameInput = By.xpath("//form//label[text()='Username']/../following-sibling::div//input");
        private final By passwordInput = By.xpath("//label[text()='Password']/../following-sibling::div//input[@type='password']");
        private final By confirmPasswordInput = By.xpath("//label[text()='Confirm Password']/../following-sibling::div//input[@type='password']");
        private final By saveButton = By.xpath("//button[@type='submit' and normalize-space()='Save']");

      public String validateAddUserPageTitle() {
            return DriverManager.getDriver().getTitle();
        }

        public boolean validateAddUserPageHeader(){
            try {
                return WaitUtils.waitForElementToBeVisible(DriverManager.getDriver(), addUserHeading).isDisplayed();
            }
            catch (Exception e){
                return false;

            }


    }

    public String getAddUserHeaderName(){
            try{
                return (WaitUtils.waitForElementToBeVisible(DriverManager.getDriver(),addUserHeading)).getText();

            }
            catch (Exception e) {
                return "";
            }


    }

        /**
         * Fills out and submits the Add User form using the encapsulated SystemUser data model.
         * @param user The SystemUser entity containing the target test data.
         */
        public String addNewUser(SystemUser user) {
            // Handle OrangeHRM custom dropdowns using helper method
            selectDropdownValue(userRoleDropdown, user.getRole());

            // Handle autocomplete employee name lookup field
            WaitUtils.waitForElementToBeClickable(DriverManager.getDriver(), employeeNameInput).sendKeys(user.getEmployeeName());

            // STABILIZATION STEP: Wait for OrangeHRM's dynamic 'Searching...' state to disappear
            By searchingTextLocator = By.xpath("//div[@role='listbox' and contains(., 'Searching')]");
            WaitUtils.waitForElementToBeInvisible(DriverManager.getDriver(), searchingTextLocator, 3);


            // Build a targeted dynamic locator using the employee's exact name
            By dynamicEmpOption = By.xpath("(//div[@role='listbox']//div[@role='option'])[1]");
            WaitUtils.waitForElementToBeClickable(DriverManager.getDriver(), dynamicEmpOption).click();


            selectDropdownValue(statusDropdown, user.getStatus());

            String uniqueUsername = user.getUsername() + System.currentTimeMillis();
           // user.setUsername(uniqueUsername);

            // Fill out remaining text fields using Lombok getters
            WaitUtils.waitForElementToBeClickable(DriverManager.getDriver(), usernameInput).sendKeys(user.getUsername());
            WaitUtils.waitForElementToBeClickable(DriverManager.getDriver(), passwordInput).sendKeys(user.getPassword());
            WaitUtils.waitForElementToBeClickable(DriverManager.getDriver(), confirmPasswordInput).sendKeys(user.getConfirmPassword());

            WaitUtils.waitForElementToBeClickable(DriverManager.getDriver(), saveButton).click();

            // Wait until the URL transitions to 'viewSystemUser' form
            WaitUtils.waitForUrlToContain(DriverManager.getDriver(), "/admin/viewSystemUsers", 10);
            return DriverManager.getDriver().getCurrentUrl();
            //return uniqueUsername;
        }

        /**
         * Private helper method to handle custom stylized div/role options dropdowns.
         */
        private void selectDropdownValue(By dropdownLocator, String visibleText) {
            WaitUtils.waitForElementToBeClickable(DriverManager.getDriver(), dropdownLocator).click();
            By optionLocator = By.xpath("//div[@role='option' and normalize-space()='" + visibleText + "']");
            WaitUtils.waitForElementToBeClickable(DriverManager.getDriver(), optionLocator).click();
        }
    }

