package com.dulinie.automation.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.dulinie.automation.config.ConfigManager;
import com.dulinie.automation.driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IConfigurationListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestNGListener implements ITestListener, IConfigurationListener {


        private static final Logger log = LogManager.getLogger(TestNGListener.class);
        private static ExtentReports extent;

        // Safely isolates parallel execution report instances per thread
        private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

        /**
         * Centralized initialization block to safely launch the reporting configurations
         * whether running via an XML suite run or executing a single class file.
         */
        private synchronized static void initializeExtentReport(String suiteName) {
            if (extent == null) {
                log.info("🔧 Initializing Extent Reports engine for: " + suiteName);

                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm"));
                System.setProperty("sharedRunTimestamp", timestamp);


                String historyReportPath = "target/automation-reports/run-history_" + timestamp + "/Automation Execution Report.html";
                String staticReportPath  = "target/automation-reports/latest-run/Automation Execution Report.html";

                ExtentSparkReporter sparkHistory = new ExtentSparkReporter(historyReportPath);
                sparkHistory.config().setReportName("Parallel Automation Dashboard(History)");
                sparkHistory.config().setDocumentTitle("Test Execution Report");
                sparkHistory.config().setTheme(Theme.DARK);

                ExtentSparkReporter sparkStatic = new ExtentSparkReporter(staticReportPath);
                sparkStatic.config().setReportName("Parallel Automation Dashboard(CI/CD)");
                sparkStatic.config().setDocumentTitle("Test Execution Report");
                sparkStatic.config().setTheme(Theme.DARK);

                extent = new ExtentReports();
                extent.attachReporter(sparkHistory, sparkStatic);

                // Dynamic system diagnostics with safe fallbacks if configuration engine hasn't fully booted
                try {
                    extent.setSystemInfo("Target URL", ConfigManager.getConfig().url());
                    extent.setSystemInfo("Test User Profile", ConfigManager.getConfig().username());
                } catch (Exception e) {
                    log.warn("ConfigManager properties skipped or unavailable during independent runtime initialization.");
                }

                String activeEnv = System.getProperty("env", "QA").toUpperCase();
                extent.setSystemInfo("Environment", activeEnv);
                extent.setSystemInfo("Framework", "Selenium Java (Owner API)");
                extent.setSystemInfo("User", System.getProperty("user.name"));
            }
        }

        @Override
        public void onStart(ITestContext context) {
            log.info("▶️ STARTING TEST SUITE: " + context.getName());
            initializeExtentReport(context.getName());
        }

        @Override
        public void onTestStart(ITestResult result) {
            String testName = result.getMethod().getMethodName();
            log.info("Starting Test: " + testName);

            ThreadContext.put("logFileName", testName);

            // Fallback Initialization: Instantiates the reporting engine if running outside of testng.xml context
            if (extent == null) {
                initializeExtentReport("Standalone Class Run Context");
            }

            ExtentTest test = extent.createTest(testName);
            extentTest.set(test);
        }

        @Override
        public void onTestSuccess(ITestResult result) {
            log.info("PASSED: " + result.getMethod().getMethodName());

            // Null-safe defensive check protecting thread execution steps
            if (extentTest.get() != null) {
                extentTest.get().pass("Test executed and passed successfully.");
            }
            ThreadContext.remove("logFileName");
        }

        @Override
        public void onTestFailure(ITestResult result) {
            String testName = result.getMethod().getMethodName();
            log.error("FAILED: " + testName, result.getThrowable());

            if (extentTest.get() == null) {
                log.warn("Execution failure reporting skipped: ThreadLocal ExtentTest node structure context is null.");
                ThreadContext.remove("logFileName");
                return;
            }

            try {
                WebDriver driver = DriverManager.getDriver();

                if (driver != null) {
                    String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
                    extentTest.get().fail(result.getThrowable(),
                            MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
                } else {
                    extentTest.get().fail("Driver was null. Could not capture screenshot. Exception: " + result.getThrowable());
                }
            } catch (Exception e) {
                log.error("Failed to capture screenshot on test failure: ", e);
                extentTest.get().fail("Screenshot capture failed. Exception: " + result.getThrowable());
            }

            ThreadContext.remove("logFileName");
        }

    @Override
    public void onConfigurationFailure(ITestResult result) {
        String configName = result.getMethod().getMethodName();
        log.error("CONFIG FAILURE: " + configName, result.getThrowable());

        // Configuration failures don't go through onTestStart, so there may be no
        // ExtentTest node yet for this thread — create one on the fly if needed.
        if (extent == null) {
            initializeExtentReport("Configuration Failure Context");
        }

        ExtentTest test = extentTest.get();
        if (test == null) {
            test = extent.createTest("CONFIG FAILURE: " + configName);
            extentTest.set(test);
        }

        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver != null) {
                String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
                test.fail(result.getThrowable(),
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
            } else {
                test.fail("Driver was null during configuration failure. Exception: " + result.getThrowable());
            }
        } catch (Exception e) {
            log.error("Failed to capture screenshot on configuration failure: ", e);
            test.fail("Screenshot capture failed. Exception: " + result.getThrowable());
        }
    }


        @Override
        public void onTestSkipped(ITestResult result) {
            log.warn("SKIPPED: " + result.getMethod().getMethodName());
            if (extentTest.get() != null) {
                extentTest.get().skip("Test skipped.");
            }
            ThreadContext.remove("logFileName");
        }

        @Override
        public void onFinish(ITestContext context) {
            log.info("FINISHED TEST SUITE: " + context.getName());
            if (extent != null) {
                extent.flush();
            }
        }
    }
