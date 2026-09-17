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
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestNGListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(com.dulinie.automation.listeners.TestNGListener.class);
    private static ExtentReports extent;

    // Safely isolates parallel execution report instances per thread
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override

    public void onStart(ITestContext context) {
        log.info("▶️ STARTING TEST SUITE: " + context.getName());

        // Generate a unique timestamp matching your Log4j2 pattern (yyyyMMdd_HHmmss)
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        /* Point path to the persistent "automation-reports" folder outside of target
         Define path A: Your historical timestamped path for local execution runs*/

        String historyReportPath  = "automation-reports/Run_" + timestamp + "/Automation Execution Report.html";

        //Define path B: A flat, predictable path specifically for GitHub Actions/Jenkins
        String staticReportPath  = "automation-reports/latest-run/Automation Execution Report.html";

        // Create the historical tracker reporter
        ExtentSparkReporter sparkHistory  = new ExtentSparkReporter(historyReportPath);
        sparkHistory.config().setReportName("Parallel Automation Dashboard(History)");
        sparkHistory.config().setDocumentTitle("Test Execution Report");
        sparkHistory.config().setTheme(Theme.DARK);



        // Create the CI/CD predictable reporter
        ExtentSparkReporter sparkStatic = new ExtentSparkReporter(staticReportPath);
        sparkStatic .config().setReportName("Parallel Automation Dashboard(CI/CD)");
        sparkStatic .config().setDocumentTitle("Test Execution Report");
        sparkStatic .config().setTheme(Theme.DARK);


        // Initialize and attach BOTH reporters seamlessly
        extent = new ExtentReports();
        extent.attachReporter(sparkHistory, sparkStatic); // The engine outputs to both paths at once!


        // Dynamic dashboard info from your properties file though config manager
        extent.setSystemInfo("Target URL", ConfigManager.getConfig().url());
        extent.setSystemInfo("Test User Profile", ConfigManager.getConfig().username());

        // Make the report environment dynamic! Reads your runtime flag (e.g., QA, STAGE)
        String activeEnv = System.getProperty("env", "QA").toUpperCase();
        extent.setSystemInfo("Environment", activeEnv);

        extent.setSystemInfo("Framework", "Selenium Java (Owner API)");
        extent.setSystemInfo("User", System.getProperty("user.name"));

    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        log.info("🚀 Starting Test: " + testName);

        // Routes Log4j2 logs into separate thread-named files
        ThreadContext.put("logFileName", testName);

        // Creates an isolated report node for this test
        ExtentTest test = extent.createTest(testName);
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("✅ PASSED: " + result.getMethod().getMethodName());
        extentTest.get().pass("Test executed and passed successfully.");
        ThreadContext.remove("logFileName");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        log.error("❌ FAILED: " + testName, result.getThrowable());

        try {
            // Fetch the thread-safe driver container instance
            WebDriver driver = DriverManager.getDriver();

            if (driver != null) {
                // Take screenshot directly into a Base64 string format
                String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);

                // Attach exception stack trace and the embedded screenshot directly to the test step
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
    public void onTestSkipped(ITestResult result) {
        log.warn("⚠️ SKIPPED: " + result.getMethod().getMethodName());
        extentTest.get().skip("Test skipped.");
        ThreadContext.remove("logFileName");
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("⏹️ FINISHED TEST SUITE: " + context.getName());
        if (extent != null) {
            extent.flush(); // Consolidates and renders the physical HTML file
        }
    }
}



