package com.dulinie.automation.tests;

import com.dulinie.automation.base.BaseTest;
import com.dulinie.automation.models.SystemUser;
import com.dulinie.automation.pages.AddUserPage;
import com.dulinie.automation.pages.AdminPage;
import com.dulinie.automation.pages.DashboardPage;
import com.dulinie.automation.pages.LoginPage;
import com.dulinie.automation.utils.JsonDataReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;

public class AddUserPageTest extends BaseTest {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private AdminPage adminPage;
    private AddUserPage addUserPage;

    @BeforeMethod
    public void initializePage() {
        loginPage = new LoginPage();
        dashboardPage = new DashboardPage();
        adminPage = new AdminPage();
        addUserPage = new AddUserPage();

        loginPage.loginWithDefaultCredentials();
    }

    @DataProvider(name = "systemUserData")
    public Iterator<Object[]> adminUserData() {
        List<SystemUser> users = JsonDataReader.getSystemUsers("testdata/systemusers.json");

        return users.stream()
                .map(user -> new Object[]{user})
                .iterator();
    }

    @Test(priority = 1, description = "Verify the Add User Heading is displayed in the Add User page")
    public void verifyAddUserPageHeaderDisplayed(){
        dashboardPage.clickAdminMenu();
        adminPage.navigateAddUser();

        boolean addUserHeaderDisplayed = addUserPage.validateAddUserPageHeader();
        Assert.assertTrue(addUserHeaderDisplayed,"Add User heading is not displayed on the Add User page.");

    }

    @Test(priority = 2, description = "Verify the Add User page title is correct")
    public void verifyAddUserPageTitle(){
        dashboardPage.clickAdminMenu();
        adminPage.navigateAddUser();

        String addUserPageTitle = addUserPage.validateAddUserPageTitle();
        Assert.assertEquals(addUserPageTitle,"OrangeHRM", "Title is wrong");

    }

    @Test(priority = 3,description = "Verify header test is correct")
    public void verifyAddUserHeaderName(){
        dashboardPage.clickAdminMenu();
        adminPage.navigateAddUser();

        String addUserHeaderName = addUserPage.getAddUserHeaderName();
        Assert.assertEquals(addUserHeaderName, "Add User", "Add User heading is incorrect");

    }



    @Test(priority = 4, description = "Verify adding new system users", dataProvider = "systemUserData")
    public void verifyAddNewUser(SystemUser user) {
        dashboardPage.clickAdminMenu();
        adminPage.navigateAddUser();
        String actualRedirectUrl = addUserPage.addNewUser(user);
        Assert.assertTrue(actualRedirectUrl.contains("admin/viewSystemUsers"), "The user was not redirected to the expected URL after adding a new user.");

    }
}

