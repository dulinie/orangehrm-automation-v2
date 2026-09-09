package com.dulinie.automation.tests;


import com.dulinie.automation.driver.DriverManager;
import org.testng.annotations.Test;

public class DriverManagerSanityTest {

        @Test
        public void verifyDriverInitializes() {
            DriverManager.initializeDriver();
            System.out.println("Title: " + DriverManager.getDriver().getTitle());
            DriverManager.quitDriver();
        }
    }

