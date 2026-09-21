package com.dulinie.automation.tests;

import com.dulinie.automation.base.BaseTest;
import com.dulinie.automation.models.SystemUser;
import com.dulinie.automation.pages.AddUser;
import com.dulinie.automation.pages.AdminPage;
import com.dulinie.automation.pages.DashboardPage;
import com.dulinie.automation.pages.LoginPage;
import com.dulinie.automation.utils.JsonDataReader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;

public class AddUserTest extends BaseTest {

    @BeforeMethod
    public void initializePage() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithDefaultCredentials();
    }

    @DataProvider(name = "systemUserData")
    public Iterator<Object[]> adminUserData() {
        List<SystemUser> users = JsonDataReader.getSystemUsers("testdata/systemusers.json");

        // Clean, loop-free conversion using Java Streams
        return users.stream()
                .map(user -> new Object[]{user})
                .iterator();
    }

    @Test(dataProvider = "systemUserData")
    public void verifyAddUser(SystemUser user) {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.clickAdminMenu();

        AdminPage adminPage = new AdminPage();
        adminPage.verifyNavigateAddUser();

        AddUser addUser = new AddUser();
        addUser.addNewUser(user);
    }
}

