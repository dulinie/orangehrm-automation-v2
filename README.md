# OrangeHRM Automation V2

[![OrangeHRM Parallel Automation Suite](https://github.com/dulinie/orangehrm-automation-v2/actions/workflows/regression.yml/badge.svg)](https://github.com/dulinie/orangehrm-automation-v2/actions/workflows/regression.yml)
[![Build Status](https://dev.azure.com/dulinie/OrangeHRM-Automation/_apis/build/status%2Fdulinie.orangehrm-automation-v2?branchName=main)](https://dev.azure.com/dulinie/OrangeHRM-Automation/_build/latest?definitionId=1&branchName=main)

A Java-based Selenium and TestNG automation framework for the OrangeHRM demo application, designed around the Page Object Model with thread-safe parallel execution, centralized environment configuration, automatic retry on failure, and CI/CD-integrated reporting with failure screenshots.

## Project Purpose

This repository showcases a Java-based UI automation framework engineered for maintainability and scale, featuring thread-safe parallel execution, centralized environment configuration, JSON-driven test data, automatic retry handling, and CI/CD-integrated reporting with failure screenshots. It is built to reflect enterprise test automation practices.

## Overview

This framework automates core user journeys in OrangeHRM, including:

- Login validation
- Dashboard verification
- Admin page validation
- User creation through the Admin module
- Data-driven test input using JSON files

## Key Features

- **Page Object Model** with a shared `BasePage` for common wait, click, type, and display-check helpers
- **Thread-safe parallel execution** using a `ThreadLocal` WebDriver
- **Centralized configuration** (browser, URL, credentials, timeouts) through the Owner library, with separate `qa` and `stage` property files
- **Data-driven testing** with JSON test data parsed by Jackson and supplied through TestNG `@DataProvider`
- **Automatic retry** of failed tests (up to 2 retries) applied suite-wide, with no per-test annotation needed
- **Extent HTML reporting** with Base64 failure screenshots, including failures in setup and teardown methods
- **Structured logging** with Log4j2
- **CI/CD** with GitHub Actions running the suite headless on every push and pull request

## Tech Stack

- Java
- Selenium WebDriver
- TestNG
- Maven
- Log4j2
- Jackson
- Owner Config library
- Extent Reports
- GitHub Actions

## Continuous Integration

The project includes CI/CD automation with GitHub Actions. The workflow runs the Maven test suite in headless browser mode on pushes and pull requests and publishes execution reports as build artifacts, so results from any run can be downloaded and reviewed.

## Project Structure

```text
orangehrm-automation-v2/
├── .github/
│   └── workflows/
│       └── regression.yml
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/dulinie/automation/
│   │   │       ├── config/
│   │   │       │   ├── ConfigManager.java
│   │   │       │   └── FrameworkConfig.java
│   │   │       ├── driver/
│   │   │       │   └── DriverManager.java
│   │   │       ├── models/
│   │   │       │   └── SystemUser.java
│   │   │       ├── pages/
│   │   │       │   ├── BasePage.java
│   │   │       │   ├── LoginPage.java
│   │   │       │   ├── DashboardPage.java
│   │   │       │   ├── AdminPage.java
│   │   │       │   └── AddUserPage.java
│   │   │       └── utils/
│   │   │           ├── JsonDataReader.java
│   │   │           └── WaitUtils.java
│   │   └── resources/
│   │       └── config/
│   │           ├── qa.properties
│   │           ├── stage.properties
│   │           └── loginData.json
│   └── test/
│       ├── java/
│       │   └── com/dulinie/automation/
│       │       ├── base/
│       │       │   └── BaseTest.java
│       │       ├── listeners/
│       │       │   ├── TestNGListener.java
│       │       │   ├── RetryAnalyzer.java
│       │       │   └── RetryTransformer.java
│       │       └── tests/
│       │           ├── LoginTest.java
│       │           ├── LoginDataDrivenTest.java
│       │           ├── DashboardTest.java
│       │           ├── AdminTest.java
│       │           └── AddUserPageTest.java
│       └── resources/
│           ├── log4j2.xml
│           ├── runner/
│           │   └── testng.xml
│           └── testdata/
│               └── systemusers.json
├── pom.xml
├── .gitignore
└── README.md
```

## Main Components

### Config

The `config` package reads browser and environment configuration from the properties files.

- `FrameworkConfig.java` defines the required config properties:
  - browser
  - url
  - username
  - password
  - explicit.wait.timeout
- `ConfigManager.java` creates and exposes the config object through Owner.

Default environment configuration is in:

```properties
src/main/resources/config/qa.properties
```

Example values:

- Execution target: `browser=chrome`, `url=https://opensource-demo.orangehrmlive.com/web/index.php/auth/login`
- Test credentials (sandbox credentials only): `username=Admin`, `password=admin123`
- Framework timeouts (seconds): `explicit.wait.timeout=12`

### Driver Manager

`DriverManager.java` handles browser setup and teardown using Selenium. It supports:

- Chrome
- Firefox
- Edge
- Headless mode for CI/CD execution

The driver is stored in a `ThreadLocal` object, so each test thread gets its own browser instance and parallel tests stay isolated.

### Page Objects

The `pages` package contains reusable page classes that represent the screens in OrangeHRM:

- `BasePage.java` - shared explicit-wait, click, type, and safe display-check helpers that every page class extends
- `LoginPage.java`
- `DashboardPage.java`
- `AdminPage.java`
- `AddUserPage.java`

Page classes hold the locators and actions for their screen and use void-return action methods paired with explicit boolean and string check methods. Assertions live in the test layer, not in the page objects.

### Test Layer

The `src/test/java/com/dulinie/automation/tests` package contains the validation tests.

- `LoginTest` - validates login page behavior and successful login
- `DashboardTest` - checks dashboard elements and title/header validation
- `AdminTest` - checks admin page navigation and heading validation
- `AddUserPageTest` - verifies the Add User screen and the user creation flow, including a post-save redirect check
- `LoginDataDrivenTest` - data-driven login scenarios

### Listeners, Retry, and Reporting

The `listeners` package wires TestNG events into reporting and retry behavior.

- `TestNGListener.java` implements `ITestListener` and `IConfigurationListener`. It builds the Extent report, keeps a `ThreadLocal<ExtentTest>` per thread, and attaches a Base64 screenshot when a test fails. Because it also handles configuration failures, errors in `@BeforeMethod` and `@AfterMethod` are captured in the report instead of being lost.
- `RetryAnalyzer.java` re-runs a failed test up to 2 times before reporting it as failed. Each failed test gets its own counter.
- `RetryTransformer.java` implements `IAnnotationTransformer` and applies `RetryAnalyzer` to every `@Test` automatically, so new tests get retry handling without extra annotations.

Both listeners are registered in `testng.xml`. Retried attempts appear in the report, so intermittent failures stay visible rather than being hidden.

### Data Handling

The project reads user data from JSON using `JsonDataReader.java` and maps it to `SystemUser.java`.

- Test data is located in `src/test/resources/testdata/systemusers.json`

## Test Execution

### Prerequisites

Make sure the following are installed:

- JDK 25 (configured in `pom.xml`)
- Maven 3.8+
- Chrome / Firefox / Edge browser, depending on your selected config

### Run the full suite

```bash
mvn test
```

### Run with a specific suite XML

The default suite is configured in `pom.xml` and points to:

```text
src/test/resources/runner/testng.xml
```

You can also run the suite directly from your IDE or via Maven.

## Reporting and Logs

- TestNG suite configuration: `src/test/resources/runner/testng.xml`
- Logging configuration: `src/test/resources/log4j2.xml`
- Surefire reports: `target/surefire-reports/`
- Execution logs: `target/automation-logs/`
- Extent reports: `target/automation-reports/`
  - `latest-run/` holds the most recent report (the one CI publishes)
  - `run-history_<timestamp>/` keeps a timestamped copy of each run

## Design Decisions and Known Limitations

This project targets the public OrangeHRM demo site, which shapes a few deliberate choices:

- **Credentials in properties files.** The demo login is public, so it lives in `qa.properties` for simplicity. In a real project, credentials would come from environment variables or GitHub Actions secrets and would never be committed.
- **Shared, changing test data.** The demo instance is shared and its data changes or gets deleted. The Add User flow selects the first employee from the autocomplete list instead of a specific name, and it appends a timestamp to each username to avoid duplicates. Created users are not cleaned up afterward.
- **Regression-only suite.** The suite is scoped as a regression run, with no smoke or sanity grouping.
- **UI-only, local browsers.** There is no API-layer setup or verification, and no Selenium Grid or cloud grid execution.

## Notes

- The project is structured around maintainability and scalability.
- Browser and app environment values are centralized in config files instead of being hard-coded across tests.
- Parallel execution is configured in the TestNG suite file.

## Author

**Dulini Egodawatta**
