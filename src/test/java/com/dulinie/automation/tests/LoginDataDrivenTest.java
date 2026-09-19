package com.dulinie.automation.tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dulinie.automation.base.BaseTest;
import com.dulinie.automation.pages.LoginPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class LoginDataDrivenTest extends BaseTest {

         // Setting parallel = true launches a fresh thread/browser for EACH JSON data row simultaneously!
        @DataProvider(name = "getLoginData", parallel = true)
        public Object[][] getData() throws IOException {
            // Locate the JSON file inside your resources folder
            String filePath = System.getProperty("user.dir") + "/src/main/resources/config/loginData.json";

            // Read JSON data using Jackson ObjectMapper
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, String>> data = mapper.readValue(
                    new File(filePath),
                    new TypeReference<List<Map<String, String>>>() {}
            );

            // Transform the List of Maps into a TestNG compatible 2D Object Array
            Object[][] dataProviderMatrix = new Object[data.size()][1]; // Defined columns as 1
            for (int i = 0; i < data.size(); i++) {
                // FIX: Wrap the Map object inside an Object array shell
                dataProviderMatrix[i][0] = data.get(i);
            }


            return dataProviderMatrix;
        }

        @Test(dataProvider = "getLoginData", description = "Execute invalid login scenarios using external JSON data sets")
        public void verifyInvalidLoginScenarios(Map<String, String> testData) {
            // Isolate instantiation strictly to this thread slot for parallel safety
            LoginPage loginPage = new LoginPage();

            // Dynamic console tracking for your active thread logs
            System.out.println("Starting Data-Driven Scenario: " + testData.get("testCaseName"));

            // Execute the user interaction using your type-safe LoginPage parameters model
            loginPage.login(testData.get("username"), testData.get("password"));

            System.out.println("Data-Driven Scenario finished for user: " + testData.get("username"));
        }
    }
