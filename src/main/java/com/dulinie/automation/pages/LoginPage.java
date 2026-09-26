package com.dulinie.automation.pages;

import com.dulinie.automation.config.ConfigManager;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By loginButton = By.xpath("//button[@type='submit']");
    private final By orangeHrmLogo = By.xpath("//img[@alt='company-branding']");

    public void loginWithDefaultCredentials() {
        String username = ConfigManager.getConfig().username();
        String password = ConfigManager.getConfig().password();
        login(username, password);
    }

    public void login(String username, String password) {
        waitVisible(usernameInput).sendKeys(username);
        waitVisible(passwordInput).sendKeys(password);
        waitClickable(loginButton).click();
    }

    public boolean isLogoDisplayed() {
        return isDisplayedSafely(orangeHrmLogo);
    }

    public String getLoginPageTitle() {
        return getPageTitle();
    }

    public String validateLoginPageTitle() {
        return getLoginPageTitle();
    }
}



