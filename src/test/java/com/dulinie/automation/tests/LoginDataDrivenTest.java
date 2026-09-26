package com.dulinie.automation.tests;

import com.dulinie.automation.base.BaseTest;
import com.dulinie.automation.pages.LoginPage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginDataDrivenTest extends BaseTest {

    @DataProvider(name = "getLoginData", parallel = true)
    public Object[][] getData() throws IOException {
        String filePath = System.getProperty("user.dir") + "/src/main/resources/config/loginData.json";

        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, String>> data = mapper.readValue(
                new File(filePath),
                new TypeReference<List<Map<String, String>>>() {}
        );

        Object[][] dataProviderMatrix = new Object[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            dataProviderMatrix[i][0] = data.get(i);
        }

        return dataProviderMatrix;
    }

    @Test(dataProvider = "getLoginData", description = "Execute invalid login scenarios using external JSON data sets")
    public void verifyInvalidLoginScenarios(Map<String, String> testData) {
        LoginPage loginPage = new LoginPage();
        System.out.println("Starting Data-Driven Scenario: " + testData.get("testCaseName"));
        loginPage.login(testData.get("username"), testData.get("password"));
        System.out.println("Data-Driven Scenario finished for user: " + testData.get("username"));
    }
}
