# Playwright Java Framework - Neelanjana K (Enterprise Edition)

This is a complete **UI Automation Framework** using:

## 🚀 Tech Stack
- **Java 17+**
- **Playwright Java** (UI Automation)
- **TestNG** (Test Runner)
- **Rest-Assured** (API Automation)
- **Extent Reports** (Reporting)
- **Apache POI** (Excel Data-Driven)
- **POM (Page Object Model)**
- **GitHub Actions**
- **Parallel Execution**
- **Retry Logic**
- **Soft Assertions**

---

## 📁 Project Architecture (Enterprise)

Playwright_Project_Bank
│
├── reports
│   ├── extentReports
│   │   └── yyyy-mm-dd
│   ├── screenshots
│   │   └── yyyy-mm-dd
│   └── videos
│   	└── yyyy-mm-dd
│
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── base
│   │   │   │   ├── BasePage.java
│   │   │   │   └── PlaywrightFactory.java
│   │   │   ├── config
│   │   │   │   ├── ConfigReader.java
│   │   │   │   ├── ExcelConfig.java
│   │   │   │   └── PropertyUtils.java
│   │   │   ├── extentreporter
│   │   │   │   ├── ReportConfigre.java
│   │   │   │   ├── ReportManager.java
│   │   │   │   └── ReportTestLogger.java
│   │   │   ├── pages
│   │   │   │   ├── LoginPage.java
│   │   │   │   ├── RegisterPage.java
│   │   │   │   └── ForgotPage.java
│   │   │   └── utils
│   │   │       ├── BrowserUtils.java
│   │   │       ├── ExcelUtils.java
│   │   │       ├── FileUtils.java
│   │   │       ├── TestDataGenerator.java
│   │   │       ├── UiActions.java
│   │   │       └── WaitUtils.java
│   │   └── resources
│   │       ├── config.properties
│   │       └── TestData.xlsx
│   └── test
│       ├── java
│       │   ├── listeners
│       │   │   ├── RetryAnalyzer.java
│       │   │   ├── RetryListener.java
│       │   │   └── TestListeners.java
│       │   └── tests
│       │       ├── BaseTest.java
│       │       ├── LoginTest.java
│       │       ├── RegistrationTest.java
│       │       └── ForgotTest.java
│
├── pom.xml
├── testng.xml
└── README.md
---

## ✨ Features

### ✓ UI Automation (Playwright)
- Browser Factory
- Full Playwright wrapper
- Automatic screenshots
- Page Object Model

### ✓ Reporting
- Extent Report HTML
- Screenshots
- Parallel-safe

### ✓ Data-Driven
- Excel Reader

### ✓ Robust Utilities
- Assertion Util
- Wait Util
- Browser Utils
- Environment manager

---

## ▶️ Run tests

### Run all tests with TestNG with parallel testing:
mvn clean test

## 📦 Reports
After execution:
Date--> storing datewise for tracking and analysing purpose
/reports/extentsReports/Date/
/reports/screenshots/Date/
/reports/videos/Date/

---

## 👨‍💻 Author
**Neel**

---

