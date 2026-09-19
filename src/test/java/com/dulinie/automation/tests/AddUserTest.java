package com.dulinie.automation.tests;

import com.dulinie.automation.base.BaseTest;
import com.dulinie.automation.pages.AddUser;
import com.dulinie.automation.pages.AdminPage;
import com.dulinie.automation.pages.DashboardPage;
import com.dulinie.automation.pages.LoginPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddUserTest extends BaseTest {

    AddUser addUser;
    DashboardPage dashboardPage;
    AdminPage adminPage;
    LoginPage loginPage;

    @BeforeMethod
    public void initializePage() {
        // Initialize the page object once before every test method execution
        loginPage = new LoginPage();
        loginPage.loginWithDefaultCredentials();

    }

    @Test
    public void verifyAddUser() {
        dashboardPage = new DashboardPage();
        dashboardPage.clickAdminMenu();

        adminPage = new AdminPage();
        adminPage.verifyNavigateAddUser();

        addUser = new AddUser();
        addUser.addNewUser();


    }
}
