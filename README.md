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
## Key Features

- **UI Automation** using Playwright + Java + POM
- **API Automation** using RestAssured
- **Database Validation** using JDBC
- **Retry Mechanism** with configurable retry count
- **Video Recording** on retries
- **Excel-based Test Data Management**
- **Parallel Execution** with unified Extent Reports
- **Multi-browser support**: Chromium, WebKit 


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
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── api
│   │   │   │   ├── base
│   │   │   │   │    └── aseApi.java
│   │   │   │   ├── clients
│   │   │   │   │    └── BaseApi.java
│   │   │   │   ├── data
│   │   │   │   │    ├──factory
│   │   │   │   │    │    ├──OrderDataFactory.java
│   │   │   │   │    │    ├──PetDataFactory.java
│   │   │   │   │    │    └── UserDataFactory.java
│   │   │   │   │    └── generators
│   │   │   │   │           └── RandomDataGenerator.java
│   │   │   │   ├── endPoints
│   │   │   │   │    ├──PetEndPoints.java
│   │   │   │   │    ├── StoreEndPoints.java
│   │   │   │   │    └──UserEndPoints.java
│   │   │   │   ├── models
│   │   │   │   │    ├──pet
│   │   │   │   │    │    ├──Category.java
│   │   │   │   │    │    ├──Pet.java
│   │   │   │   │    │    └── Tag.java
│   │   │   │   │    ├──store
│   │   │   │   │    │    └── Order.java
│   │   │   │   │    └── user
│   │   │   │   │           └── User.java
│   │   │   │   └── services
│   │   │   │         ├── PetService.java
│   │   │   │         ├── StoreService.java
│   │   │   │         └──UserService.java
│   │   │   ├── base
│   │   │   │   ├── BasePage.java
│   │   │   │   └── PlaywrightFactory.java
│   │   │   ├── config
│   │   │   │   ├── ConfigReader.java
│   │   │   │   ├── DBConfig.java
│   │   │   │   └── PropertyUtils.java
│   │   │   ├── db
│   │   │   │   ├── client
│   │   │   │   │    └── DBClient.java
│   │   │   │   ├── dao
│   │   │   │   │    └── UserDao.java
│   │   │   │   ├── model
│   │   │   │   │    └── User.java
│   │   │   │   └── queries
│   │   │   │          └── UserQueries.java
│   │   │   ├── extentreporter
│   │   │   │   ├── ReportConfigre.java
│   │   │   │   ├── ReportManager.java
│   │   │   │   └── ReportTestLogger.java
│   │   │   ├── pages
│   │   │   │   ├── InputField.java
│   │   │   │   ├── RegisterFormData.java
│   │   │   │   ├── LoginPage.java
│   │   │   │   ├── RegisterPage.java
│   │   │   │   └── ForgotPage.java
│   │   │   └── utils
│   │   │       ├── AttachmentUtils.java
│   │   │       ├── BrowserUtils.java
│   │   │       ├── DBUtils.java
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
│       │   ├── Resources
│       │   │   ├── automationdb.sql
│       │   │   └── testdb.sql
│       │   └── tests
│       │       ├── api
│       │       │   ├── BaseApiTest.java
│       │       │   ├── PetApiTest.java
│       │       │   ├── StoreApiTest.java
│       │       │   └── UserApiTest.java
│       │       ├── db
│       │       │   ├── BasedbTest.java
│       │       │   └── UserdbTest.java
│       │       ├── ui
│       │       │   ├── BaseTest.java
│       │       │   ├── ForgotTest.java
│       │       │   ├── LoginTest.java
│       │            └── RegistrationTest.java
│
├── pom.xml
├── testng-master.xml
├── testng-api.xml
├── testng-db.xml
├── testng-ui.xml
└── README.md
---


## ✨ Features

## 5. Framework Design Highlights

### 5.1 UI Layer
- **Page Object Model (POM)**: Each page has a dedicated class for locators and actions
- **BasePage**: Centralizes common interactions (click, type, mouse, keyboard)
- **BaseTest**: Handles browser setup, page initialization, teardown, and listeners

### 5.2 API Layer
- Base API classes for client setup
- Service classes implement business logic for API endpoints
- Supports positive and negative API test scenarios

### 5.3 Database Layer
- JDBC client handles DB connections
- DAO classes implement CRUD operations
- Validate database state after UI/API actions

### 5.4 Retry Mechanism & Video Recording
- TestNG RetryAnalyzer with configurable retry count
- Video recording enabled only on the **second retry**
- Reduces storage usage while capturing failures

---

## 6. Reporting & Artifacts

- **Extent Reports**: `reports/extentReports/yyyy-mm-dd`
- **Screenshots**: Automatically captured for failed tests
- **Execution Videos**: Captured during retries
- Unified reporting for **parallel executions**

---

## 7. Test Data Management

## 7.1 Excel Data Management
- Test data for **User Registration** is stored in Excel
- Excel utilities support:
    - Read/write operations
    - Dynamic data updates
- Registered user details are reused for **Login** and **Forgot Password** tests


## 7.2 Data Base Test Data Management

- Test data for **User Registration** is stored in Database
- Database utilities support:
    - Read/write operations
    - Dynamic data updates
- Registered user details are reused for **Login** and **Forgot Password** tests
---

## 8. Known Application Limitation

- Application supports only a **5-minute user session**
- Excel test data must be cleared every 5 minutes to avoid conflicts
- Handled manually in current test strategy

---

## Test Coverage

### 1 UI Tests
- **Registration Page**: Field validation, error messages, successful registration (update to Redistration table of automationdb Database)
- **Login Page**: Valid/invalid credentials, page validation
- **Forgot Password Page**: Field validation, recovery scenarios

### 2 API Tests
- **User API**, **Pet API**, **Store API**
- CRUD operations, positive & negative scenarios

### 3 Database Tests
- Validate User records
- CRUD Operations on User table


### ✓ Robust Utilities
- Assertion Util
- Wait Util
- DB Util
- Browser Utils
- Environment manager
---

## ▶️ Run tests

### Run all tests with TestNG with parallel testing:
mvn clean test -Dsuite=master.xml

### Run specific Category tests:
mvn clean test -Dsuite=testng-ui.xml
mvn clean test -Dsuite=testng-api.xml
mvn clean test -Dsuite=testng-db.xml

### Run specific Scenario-UI tests with mutli browser:
mvn clean test -Dsuite=testng-ui.xml -Dgroups=Registration
mvn clean test -Dsuite=testng-ui.xml -Dgroups=Login
mvn clean test -Dsuite=testng-ui.xml -Dgroups=Forgot
mvn clean test -Dsuite=testng-api.xml -Dgroups=PetAPITest
mvn clean test -Dsuite=testng-api.xml -Dgroups=StoreAPITest
mvn clean test -Dsuite=testng-api.xml -Dgroups=UserAPITest
mvn clean test -Dsuite=testng-db.xml -Dgroups=UserDBTest

### Run specific Scenario-UI tests with single browser:
mvn clean test -Dsuite=testng-ui.xml -Dtest=RegistrationTest -Dbrowser=chromium
mvn clean test -Dsuite=testng-ui.xml -Dtest=LoginTest -Dbrowser=webkit
mvn clean test -Dsuite=testng-ui.xml -Dtest=ForgotTest -Dbrowser=chromium

### Run specific Scenario-UI tests particular method:
mvn clean test -Dsuite=testng-ui.xml -Dtest=RegistrationTest#registrationWithValidData

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

