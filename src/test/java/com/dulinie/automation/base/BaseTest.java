package com.dulinie.automation.base;

import com.dulinie.automation.driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

        @BeforeMethod
        public void setUp() {
            DriverManager.initializeDriver();

        }

        @AfterMethod
        public void tearDown() {
            DriverManager.quitDriver();
        }
    }



