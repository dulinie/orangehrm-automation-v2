package com.dulinie.automation.pages;

import org.openqa.selenium.By;

public class AdminPage extends BasePage {

    private final By adminHeader = By.xpath("//h6[text() ='Admin']");
    private final By addUserButton = By.xpath("//button[@type='button' and contains(normalize-space(.), 'Add')]");
    private final By usernameSearchInput = By.xpath("//label[normalize-space()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By searchButton = By.xpath("//button[@type='submit' and normalize-space()='Search']");

    public String getAdminPageTitle() {
        return getPageTitle();
    }

    public String validateAdminPageTitle() {
        return getAdminPageTitle();
    }

    public boolean isAdminPageHeaderDisplayed() {
        return isDisplayedSafely(adminHeader);
    }

    public String getAdminHeaderText() {
        return waitVisible(adminHeader).getText();
    }

    public String getAdminHeaderName() {
        return getAdminHeaderText();
    }

    public void openAddUserPage() {
        waitClickable(addUserButton).click();
    }

    public void navigateAddUser() {
        openAddUserPage();
    }

    public void searchUserByUsername(String username) {
        waitVisible(usernameSearchInput).clear();
        waitVisible(usernameSearchInput).sendKeys(username);
        waitClickable(searchButton).click();
    }
}