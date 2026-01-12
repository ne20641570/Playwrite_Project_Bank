# Playwright Java Framework - Venkat (Enterprise Edition)

This is a complete **Enterprise-Grade Hybrid UI + API Automation Framework** using:

## 🚀 Tech Stack
- **Java 17+**
- **Playwright Java** (UI Automation)
- **TestNG** (Test Runner)
- **Rest-Assured** (API Automation)
- **Extent Reports** (Reporting)
- **Apache POI** (Excel Data-Driven)
- **POM (Page Object Model)**
- **Log4j2** (Logging)
- **GitHub Actions + Jenkins Pipeline**
- **Parallel Execution**
- **Retry Logic**
- **Soft Assertions**
- **Environment-Based Execution**

---

## 📁 Project Architecture (Enterprise)

src/main/java
├── base
├── config
├── pages
├── api
├── reporting
├── utils
src/test/java
├── ui
├── api
└── listeners



---

## ✨ Features

### ✓ UI Automation (Playwright)
- Browser Factory
- Full Playwright wrapper
- Automatic screenshots
- Page Object Model

### ✓ API Automation (Rest-Assured)
- Endpoints
- Payload Builders
- API Base
- Response validators

### ✓ Reporting
- Extent Report HTML
- Screenshots
- Logs
- Parallel-safe

### ✓ Data-Driven
- Excel Reader
- JSON TestData manager

### ✓ Robust Utilities
- Assertion Util
- Wait Util
- Browser Utils
- Environment manager

### ✓ CI/CD
- GitHub Actions workflow
- Jenkinsfile

---

## ▶️ Run Tests

### Run all tests with TestNG:
mvn clean test



### Run parallel:
mvn clean test -Dsuite=parallel


---

## 📦 Reports
After execution:
Date--> storing datewise for tracking and analysing purpose
/reports/Date/ExtentReport.html
/reports/Date/screenshots/



---

## 👨‍💻 Author
**Neel**

---

