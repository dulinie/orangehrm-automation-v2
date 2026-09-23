
# OrangeHRM Automation V2

[![OrangeHRM Parallel Automation Suite](https://github.com/dulinie/orangehrm-automation-v2/actions/workflows/regression.yml/badge.svg)](https://github.com/dulinie/orangehrm-automation-v2/actions/workflows/regression.yml)

A Selenium-based Java test automation framework for the OrangeHRM demo application, built with Maven and TestNG. The project follows the Page Object Model (POM) design pattern to keep tests readable, maintainable, and reusable.

## Overview

This framework automates core user journeys in OrangeHRM including:

- Login validation
- Dashboard verification
- Admin page validation
- Adding users through the Admin module
- Data-driven test input using JSON files

## Tech Stack

- Java
- Selenium WebDriver
- TestNG
- Maven
- Log4j2
- Jackson
- Owner Config library
- Extent Reports

## ⚙️ Continuous Integration
The project includes CI/CD automation with GitHub Actions, running the Maven test suite in headless browser mode on pushes and pull requests and publishing execution reports as build artifacts.
The framework also supports parallel test execution when needed, helping reduce overall test runtime.

## Project Structure

```text
orangehrm-automation-v2/
├── .github/
├── .idea/
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
│   │   │       │   ├── LoginPage.java
│   │   │       │   ├── DashboardPage.java
│   │   │       │   ├── AdminPage.java
│   │   │       │   └── AddUser.java
│   │   │       └── utils/
│   │   │           ├── JsonDataReader.java
│   │   │           └── WaitUtils.java
│   │   └── resources/
│   │       └── config/
│   │           ├── qa.properties
│   │           ├── stage.properties
│   │           └── loginData.json
│   └── test/
│       ├── java/com/dulinie/automation/
│       │   ├── base/
│       │   │   └── BaseTest.java
│       │   ├── listeners/
│       │   │   └── TestNGListener.java
│       │   └── tests/
│       │       ├── LoginTest.java
│       │       ├── LoginDataDrivenTest.java
│       │       ├── DashboardTest.java
│       │       ├── AdminTest.java
│       │       └── AddUserTest.java
│       └── resources/
│           ├── log4j2.xml
│           ├── runner/
│           │   └── testng.xml
│           └── testdata/
│               └── systemusers.json
├── pom.xml
├── .gitignore
├── README.md
└── target/
```

## Main Components

### Config

The `config` package is responsible for reading browser and environment configuration from the properties files.

- `FrameworkConfig.java` defines required config properties like:
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

# Execution Target
browser=chrome
url=https://opensource-demo.orangehrmlive.com/web/index.php/auth/login

# Test Credentials (Use sandbox credentials only)
username=Admin
password=admin123

# Framework Timeouts (Seconds)
explicit.wait.timeout=12

### Driver Manager

`DriverManager.java` handles browser setup and teardown using Selenium. It supports:

- Chrome
- Firefox
- Edge
- Headless mode for CI/CD execution

The driver is stored in a `ThreadLocal` object, which helps keep test execution isolated when parallel tests run.

### Page Objects

The `pages` package contains reusable page classes that represent different screens in OrangeHRM:

- `LoginPage.java`
- `DashboardPage.java`
- `AdminPage.java`
- `AddUser.java`

These classes store locators and actions for each page, reducing duplication and keeping tests easier to read.

### Test Layer

The `src/test/java/com/dulinie/automation/tests` package contains the actual validation tests.

Included tests:

- `LoginTest` - validates login page behavior and successful login
- `DashboardTest` - checks dashboard elements
- `AdminTest` - checks admin page navigation and headings
- `AddUserTest` - verifies add-user screen and user creation flow
- `LoginDataDrivenTest` - available for data-driven login scenarios

### Data Handling

The project reads user data from JSON using `JsonDataReader.java` and maps it to `SystemUser.java`.

- Test data is located in:
  - `src/test/resources/testdata/systemusers.json`

## Test Execution

### Prerequisites

Make sure the following are installed:

- JDK 25 (configured in `pom.xml`)
- Maven 3.8+
- Chrome / Firefox / Edge browser depending on your selected config

### Run the full suite

```bash
mvn test
```

### Run with a specific suite XML

The default suite is configured in `pom.xml` and points to:

```text
src/test/resources/runner/testng.xml
```

You can also run the suite directly from IDE or via Maven if needed.

## Reporting and Logs

- TestNG suite configuration: `src/test/resources/runner/testng.xml`
- Logging configuration: `src/test/resources/log4j2.xml`
- Surefire reports: `target/surefire-reports/`
- Execution logs: `target/automation-logs/`
- Automation reports: `target/automation-reports/`

## Notes

- The project is structured around maintainability and scalability.
- Browser and app environment values are centralized in config files instead of hard-coded across tests.
- Parallel execution is enabled in the TestNG suite configuration.

## Author

Dulini Egodawatta
