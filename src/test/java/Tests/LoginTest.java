package Tests;

import ExtentReporter.ReportManager;
import ExtentReporter.ReportTestLogger;
import Listeners.RetryAnalyzer;
import Pages.LoginPage;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.options.LoadState;
import config.ConfigReader;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class LoginTest extends BaseTest {
    @BeforeClass
    public void setupBeforeClass() {
        ReportManager.startTest("Login TS_01 Login Functionality Validation");
    }

//    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Test
    public void loginwithValidCredentialsTest() throws IOException {

        ExtentTest testnode = ReportManager.getTest().createNode("TC01 Login with valid credentials");
        testnode.assignCategory("TC01_login_with_Valid_Credentials");


            LoginPage loginPage = new LoginPage(page);
        try {
            loginPage.navigateTo(ConfigReader.getProperty("demo.url"));

            ReportTestLogger.info(testnode, "Navigated to login page");

            RetryAnalyzer.retryStep(page,() -> loginPage.login(),testnode);

//            loginPage.login();
            ReportTestLogger.pass(testnode, "Username entered as: " + loginPage.userName());
            ReportTestLogger.pass(testnode, "Password entered as: " + loginPage.password());

            ReportTestLogger.pass(testnode, "Login performed with user: " + loginPage.userName());
//            ReportTestLogger.addScreenshotPath(testnode, loginPage.captureScreenshot("LoginTest"));
            ReportTestLogger.addScreenshotBase(testnode, loginPage.captureScreenshotBase("LoginTest"));

        }catch (Exception e){
            ReportTestLogger.fail(testnode,"Retrying the step");
//            ReportTestLogger.addScreenshotPath(testnode, loginPage.captureScreenshot("LoginTestFail"));
            ReportTestLogger.addScreenshotBase(testnode, loginPage.captureScreenshotBase("LoginTestFail"));


            throw e;
        }
    }
}
