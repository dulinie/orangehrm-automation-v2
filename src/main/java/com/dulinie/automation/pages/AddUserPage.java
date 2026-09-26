package com.dulinie.automation.pages;

import com.dulinie.automation.driver.DriverManager;
import com.dulinie.automation.models.SystemUser;
import com.dulinie.automation.utils.WaitUtils;
import org.openqa.selenium.By;

public class AddUserPage extends BasePage {

    private final By addUserHeading = By.xpath("//h6[text()='Add User']");
    private final By userRoleDropdown = By.xpath("//label[text()='User Role']/../following-sibling::div//div[@class='oxd-select-text-input']");
    private final By employeeNameInput = By.xpath("//label[text()='Employee Name']/../following-sibling::div//input");
    private final By statusDropdown = By.xpath("//label[text()='Status']/../following-sibling::div//div[@class='oxd-select-text-input']");
    private final By usernameInput = By.xpath("//form//label[text()='Username']/../following-sibling::div//input");
    private final By passwordInput = By.xpath("//label[text()='Password']/../following-sibling::div//input[@type='password']");
    private final By confirmPasswordInput = By.xpath("//label[text()='Confirm Password']/../following-sibling::div//input[@type='password']");
    private final By saveButton = By.xpath("//button[@type='submit' and normalize-space()='Save']");

    public String getAddUserPageTitle() {
        return getPageTitle();
    }

    public String validateAddUserPageTitle() {
        return getAddUserPageTitle();
    }

    public boolean isAddUserPageHeaderDisplayed() {
        return isDisplayedSafely(addUserHeading);
    }

    public boolean validateAddUserPageHeader() {
        return isAddUserPageHeaderDisplayed();
    }

    public String getAddUserHeaderText() {
        return waitVisible(addUserHeading).getText();
    }

    public String getAddUserHeaderName() {
        return getAddUserHeaderText();
    }

    public String addNewUser(SystemUser user) {
        selectDropdownValue(userRoleDropdown, user.getRole());

        type(employeeNameInput, user.getEmployeeName());
        By searchingTextLocator = By.xpath("//div[@role='listbox' and contains(., 'Searching')]");
        WaitUtils.waitForElementToBeInvisible(DriverManager.getDriver(), searchingTextLocator, 3);

        By dynamicEmpOption = By.xpath("(//div[@role='listbox']//div[@role='option'])[1]");
        waitClickable(dynamicEmpOption).click();

        selectDropdownValue(statusDropdown, user.getStatus());

        String uniqueUsername = user.getUsername() + System.currentTimeMillis();
        type(usernameInput, uniqueUsername);
        type(passwordInput, user.getPassword());
        type(confirmPasswordInput, user.getConfirmPassword());

        waitClickable(saveButton).click();
        WaitUtils.waitForUrlToContain(DriverManager.getDriver(), "/admin/viewSystemUsers", 10);
        return DriverManager.getDriver().getCurrentUrl();
    }

    private void selectDropdownValue(By dropdownLocator, String visibleText) {
        waitClickable(dropdownLocator).click();
        By optionLocator = By.xpath("//div[@role='option' and normalize-space()='" + visibleText + "']");
        waitClickable(optionLocator).click();
    }
}
