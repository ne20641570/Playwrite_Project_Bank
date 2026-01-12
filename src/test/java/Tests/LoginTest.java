package Tests;

import ExtentReporter.ReportManager;
import ExtentReporter.ReportTestLogger;
import Listeners.RetryAnalyzer;
import Listeners.Scenario;
import Pages.LoginPage;
import com.aventstack.extentreports.ExtentTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ExcelUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Scenario("TS_02 Login Page Functionality Validation")
public class LoginTest extends BaseTest {
    LoginPage loginPage;
    private String sheetName = "Login";
    private String readDataSheetName = "Register";
    String filePath;
    ExtentTest testnode;
    ExtentTest innerTestNode;
    ExtentTest innerTestNodes;
    private static ThreadLocal<ExtentTest> testTL = new ThreadLocal<>();
    protected String[] dependsOn;
    protected String projectName;
//    // ------------------- Class-level setup -------------------
    @BeforeClass
    public void setupBeforeClass(ITestContext context) {
        Scenario scenario = this.getClass().getAnnotation(Scenario.class);
        if (scenario != null) {
//            testName = context.getCurrentXmlTest().getName();
            ReportManager.startTest(context.getName()+"-->"+scenario.value());
            testTL.set(ReportManager.getTest());
        }
    }

    // ------------------- Method-level setup -------------------
    @BeforeMethod
    public void setupBeforeMethod(Method method, ITestContext context) {

        filePath = ExcelUtils.readExeclPath() + ConfigReader.getProperty("excel.file.bank");
        loginPage = new LoginPage(page);
        String testDescription = method.isAnnotationPresent(Test.class)
                ? method.getAnnotation(Test.class).description()
                : method.getName();

        if (testTL.get() == null) {
            throw new RuntimeException("ReportManager.getTest() is null in @BeforeMethod");
        }
        testnode = testTL.get().createNode(testDescription);
//        innerTestNode = ReportTestLogger.createNode(testnode,testDescription);
        innerTestNode = ReportTestLogger.createinnerNode(testnode, "Navigating to Register Page");

        loginPage.navigateTo(ConfigReader.getProperty("bank.url"));
        ReportTestLogger.info(innerTestNode, "Navigated to URL");
        Assert.assertEquals(loginPage.getLoginPageTitle(), page.title(), "Page title mismatch!");
    }
    // ------------------- Test 1: Field Presence -------------------
    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "TC01 Login page validation")
    public void loginPageVerify() throws IOException {
            innerTestNode=ReportTestLogger.createinnerNode(testnode,"verifying the Fields");

        try{
            verifyLoginField("UserName");
            verifyLoginField("UserNameField");
            verifyLoginField("Password");
            verifyLoginField("PasswordField");
            verifyLoginField("Forgot");
            verifyLoginField("Register");
            innerTestNode=ReportTestLogger.createinnerNode(testnode,"verifying the Tab Fields");


            innerTestNodes=ReportTestLogger.createinnerNode(innerTestNode,"verifying the Services Tab Fields");
//            loginPage.verifyAndReadFieldText(innerTestNodes,"Services");
            RetryAnalyzer.retryStep(() -> loginPage.verifyAndReadFieldText(innerTestNodes,"Services"), innerTestNode);
            ReportTestLogger.info(innerTestNodes, "Services Tab is Displayed and we have "+loginPage.countTabs("Services")+ " Tabs");
            ReportTestLogger.info(innerTestNodes, "Services Tab Name is Displayed and we have "+loginPage.countTabs("ServiceTabName")+ " Names");
            loginPage.verifyAndReadFieldText(innerTestNodes,"ServiceTabName");

            ReportTestLogger.info(innerTestNodes, "Services Links are Displayed and we have "+loginPage.countTabs("ServiceTabLinks")+ " Links");
            loginPage.verifyAndReadFieldText(innerTestNodes,"ServiceTabLinks");

            loginPage.verifyField("ReadMoreService");
            ReportTestLogger.info(innerTestNodes, "Read More Link for Services is Displayed");
            ReportTestLogger.pass(innerTestNodes,"Reading Services Tabs and their Names with links completed successfully.");

            innerTestNodes=ReportTestLogger.createinnerNode(innerTestNode,"verifying the Latest news Tab Fields");
            loginPage.verifyAndReadFieldText(innerTestNodes,"LatestNews");
            ReportTestLogger.info(innerTestNodes, "Latest News Tab is Displayed and we have "+loginPage.countTabs("LatestNews")+ " Tabs");
            ReportTestLogger.info(innerTestNodes, "Latest news Tab Name is Displayed and we have "+loginPage.countTabs("LatestNewsTabNames")+ " Names");
            loginPage.verifyAndReadFieldText(innerTestNodes,"LatestNewsTabNames");

            ReportTestLogger.info(innerTestNodes, "Latest news Tab Links are Displayed and we have "+loginPage.countTabs("LatestNewsTabLinks")+ " Links");
            loginPage.verifyAndReadFieldText(innerTestNodes,"LatestNewsTabLinks");

            loginPage.verifyField("ReadMoreNews");
            ReportTestLogger.info(innerTestNodes, "Read More Link for Latest News is Displayed");
            ReportTestLogger.pass(innerTestNodes,"Reading Latest news Tabs and their Names with links completed successfully.");

            ReportTestLogger.pass(innerTestNode, "All Fields are Displayed");
            ReportTestLogger.addScreenshotBase(testnode, loginPage.captureScreenshotBase("Login_All_Field_Test"));
            ReportTestLogger.pass(testnode, "Field validation has been successfully completed.");

        }catch (Exception e){

            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
//            ReportTestLogger.addScreenshotPath(testnode, registerPage.captureScreenshot("LoginTestFail"));
            ReportTestLogger.addScreenshotBase(innerTestNode, loginPage.captureScreenshotBase("Login_Page_Fail"));
            throw e;
        }
    }

    // ------------------- Test 2: Invalid Login -------------------
    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "TC02 Login with invalid credentials")
    public void loginWithInvalidCredentialsTest() throws IOException {
        filePath = ExcelUtils.readExeclPath() + ConfigReader.getProperty("excel.file.bank");
        innerTestNode = ReportTestLogger.createinnerNode(testnode,"Login without writing credentials");
        ExcelUtils.openExcel(filePath);
        String userName="";
        String password = "";
        String excelPassword = ExcelUtils.getCellData(readDataSheetName,"Password");
        try {
            // -------without credentials--------
            loginErrorFlow(innerTestNode,"", "", "Login without credentials");

            // -------Invalid User name credentials--------
            userName = ExcelUtils.getCellData(readDataSheetName, "UserName") + "123";
            password = ConfigReader.getProperty("bank.password");
            loginErrorFlow(innerTestNode,userName, password, "Login with invalid username");


            // -------Invalid Password credentials--------
            userName = ExcelUtils.getCellData(readDataSheetName, "UserName");
            password = ConfigReader.getProperty("bank.password")+"123";
            loginErrorFlow(innerTestNode,userName, password, "Login with invalid password");

            ReportTestLogger.addScreenshotBase(innerTestNode, loginPage.captureScreenshotBase("Login_invalid_Cred_Test"));
            ReportTestLogger.pass(testnode, "Login with invalid credentials has been successfully completed.");

        }catch (Exception e){
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode,"Retrying the step");
//            ReportTestLogger.addScreenshotPath(testnode, loginPage.captureScreenshot("LoginTestFail"));
            ReportTestLogger.addScreenshotBase(innerTestNode, loginPage.captureScreenshotBase("LoginTestFail"));
            throw e;
        }
    }
    // ------------------- Test 3: Valid Login -------------------
    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "TC03 Login with valid credentials")
    public void loginWithValidCredentialsTest() throws IOException {
        filePath = ExcelUtils.readExeclPath() + ConfigReader.getProperty("excel.file.bank");
        innerTestNode = ReportTestLogger.createinnerNode(testnode, "Login with Valid Credentials");
        ExcelUtils.openExcel(filePath);
        try {
//            innerTestNode = ReportTestLogger.createinnerNode(testnode,"Reading username from excel and trying to login");

            String userName=ExcelUtils.getCellData(readDataSheetName,"UserName");
            String actualPassword = ConfigReader.getProperty("bank.password");

            RetryAnalyzer.retryStep(() -> loginPage.login(userName,actualPassword),testnode);
            ReportTestLogger.info(innerTestNode, "Username entered is: " + userName);
            ReportTestLogger.info(innerTestNode, "Password entered is: " +actualPassword);
            ReportTestLogger.pass(innerTestNode, "Log in with valid credentials completed Successfully");

//            ReportTestLogger.addScreenshotPath(testnode, loginPage.captureScreenshot("LoginTest"));
            ReportTestLogger.addScreenshotBase(testnode, loginPage.captureScreenshotBase("Login_Test"));
        }catch (Exception e){
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode,"Retrying the step");
//            ReportTestLogger.addScreenshotPath(testnode, loginPage.captureScreenshot("LoginTestFail"));
            ReportTestLogger.addScreenshotBase(innerTestNode, loginPage.captureScreenshotBase("LoginTestFail"));

            throw e;
        }
    }
    // ------------------- Utility Methods -------------------
    private void verifyLoginField(String fieldName) throws IOException {
        loginPage.verifyField(fieldName);
        ReportTestLogger.info(innerTestNode, fieldName + " field is displayed");
    }
    private void loginErrorFlow(ExtentTest innerTestNode,String userName, String password, String scenario) throws IOException {
        loginPage.login(userName, password);

        ReportTestLogger.info(innerTestNode, "Username entered: " + userName);
        ReportTestLogger.info(innerTestNode, "Password entered: " + password);

        innerTestNodes = ReportTestLogger.createinnerNode(innerTestNode, "Verifying error message for: " + scenario);
        String errorTitle = loginPage.verifyPageMessage("ErrorTitle");
        String errorMessage = loginPage.verifyPageMessage("ErrorMessage");

        ReportTestLogger.info(innerTestNodes, "Error Title: " + errorTitle);
        ReportTestLogger.info(innerTestNodes, "Error Message: " + errorMessage);
        ReportTestLogger.pass(innerTestNodes, scenario + " completed successfully");
        ReportTestLogger.addScreenshotBase(innerTestNode, loginPage.captureScreenshotBase(scenario.replace(" ", "_")));
    }
    @AfterClass
    public void tearDown() {
        testTL.remove();
        ReportManager.endTest();
    }
}
