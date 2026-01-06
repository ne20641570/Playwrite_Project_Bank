package Tests;

import ExtentReporter.ReportManager;
import ExtentReporter.ReportTestLogger;
import Listeners.RetryAnalyzer;
import Pages.LoginPage;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.options.LoadState;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ExcelUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class LoginTest extends BaseTest {
    LoginPage loginPage;
    private String sheetName = "Login";
    private String readDataSheetName = "Register";
    String filePath;
    ExtentTest testnode;
    ExtentTest innerTestNode;
    ExtentTest innerTestNodes;
    @BeforeClass
    public void setupBeforeClass() {
        ReportManager.startTest("Login TS_01 Login Functionality Validation","LoginTest");
        loginPage = new LoginPage(page);
        filePath = ExcelUtils.readExeclPath() + ConfigReader.getProperty("excel.file.bank");
    }

    @Test(priority = 1)
    public void loginPageVerify() throws IOException {

         testnode = ReportManager.getTest().createNode("TC01 Login page validation");
//        testnode.assignCategory("TC01_login_with_Valid_Credentials");
        loginPage.navigateTo(ConfigReader.getProperty("bank.url"));
        ReportTestLogger.info(testnode, "Navigated to login page");
        ExcelUtils.openExcel(filePath);
        try{
            innerTestNode=ReportTestLogger.createinnerNode(testnode,"verifying the Fields");
            loginPage.verifyFields("UserName");
            ReportTestLogger.info(innerTestNode, "User Name Field is Displayed");
            loginPage.verifyFields("Password");
            ReportTestLogger.info(innerTestNode, "Password Field is Displayed");
            loginPage.verifyFields("Forgot");
            ReportTestLogger.info(innerTestNode, "Forgot Password Link is Displayed");
            loginPage.verifyFields("Register");
            ReportTestLogger.info(innerTestNode, "Register Link is Displayed");


            innerTestNode=ReportTestLogger.createinnerNode(testnode,"verifying the Tab Fields");


            innerTestNodes=ReportTestLogger.createinnerNode(innerTestNode,"verifying the Services Tab Fields");
            loginPage.verifyFields("Services");
            ReportTestLogger.info(innerTestNodes, "Services Tab is Displayed and we have "+loginPage.countTabs("Services")+ " Tabs");

            ReportTestLogger.info(innerTestNodes, "Services Tab Name is Displayed and we have "+loginPage.countTabs("ServiceTabName")+ " Names");
            loginPage.verifyAndReadFieldText(innerTestNodes,"ServiceTabName");
            ReportTestLogger.info(innerTestNodes, "Services Links are Displayed and we have "+loginPage.countTabs("ServiceTabLinks")+ " Links");
            loginPage.verifyAndReadFieldText(innerTestNodes,"ServiceTabLinks");
            loginPage.verifyFields("ReadMoreService");
            ReportTestLogger.info(innerTestNodes, "Read More Link for Services is Displayed");
            ReportTestLogger.pass(innerTestNodes,"Reading Services Tabs and their Names with links completed successfully.");


            innerTestNodes=ReportTestLogger.createinnerNode(innerTestNode,"verifying the Latest news Tab Fields");
            loginPage.verifyFields("LatestNews");
            ReportTestLogger.info(innerTestNodes, "Latest News Tab is Displayed and we have "+loginPage.countTabs("LatestNews")+ " Tabs");
            ReportTestLogger.info(innerTestNodes, "Latest news Tab Name is Displayed and we have "+loginPage.countTabs("LatestNewsTabNames")+ " Names");
            loginPage.verifyAndReadFieldText(innerTestNodes,"LatestNewsTabNames");
            ReportTestLogger.info(innerTestNodes, "Latest news Tab Links are Displayed and we have "+loginPage.countTabs("LatestNewsTabLinks")+ " Links");
            loginPage.verifyAndReadFieldText(innerTestNodes,"LatestNewsTabLinks");
            loginPage.verifyFields("ReadMoreNews");
            ReportTestLogger.info(innerTestNodes, "Read More Link for Latest News is Displayed");
            ReportTestLogger.pass(innerTestNodes,"Reading Latest news Tabs and their Names with links completed successfully.");

            ReportTestLogger.pass(innerTestNode, "All Fields are Displayed");
            ReportTestLogger.addScreenshotBase(innerTestNode, loginPage.captureScreenshotBase("Login_All_Field_Test"));

            ReportTestLogger.pass(testnode, "Field validation has been successfully completed.");

        }catch (Exception e){
            ReportTestLogger.info(testnode, e.getMessage());
            ReportTestLogger.fail(testnode, "Failed");
//            ReportTestLogger.addScreenshotPath(testnode, registerPage.captureScreenshot("LoginTestFail"));
            ReportTestLogger.addScreenshotBase(testnode, loginPage.captureScreenshotBase("Login_Page_Fail"));
            throw e;
        }
    }
    @Test(priority = 2)
    public void loginWithInvalidCredentialsTest() throws IOException {

        testnode = ReportManager.getTest().createNode("TC02 Login with invalid credentials");
//        testnode.assignCategory("TC01_login_with_Valid_Credentials");
        loginPage.navigateTo(ConfigReader.getProperty("bank.url"));
        ReportTestLogger.info(testnode, "Navigated to login page");

        ExcelUtils.openExcel(filePath);
        String userName;
        String password;
        String excelPassword = ExcelUtils.getCellData(readDataSheetName,"Password");
        try {
            innerTestNode = ReportTestLogger.createinnerNode(testnode,"Login without writing credentials");
            userName="";
            password = "";
            loginErrorFunctionality(innerTestNode,userName,password,"Login without credentials");

            innerTestNode = ReportTestLogger.createinnerNode(testnode,"Login with invalid User Name");
            userName=ExcelUtils.getCellData(readDataSheetName,"UserName")+123;
            password = ConfigReader.getProperty("bank.password");
            loginErrorFunctionality(innerTestNode,userName,password,"Invalid User Name");

            innerTestNode = ReportTestLogger.createinnerNode(testnode,"Login with invalid Password");
            userName=ExcelUtils.getCellData(readDataSheetName,"UserName");
            password = ConfigReader.getProperty("bank.password")+123;;
            loginErrorFunctionality(innerTestNode,userName,password,"Invalid Password");


        }catch (Exception e){
            ReportTestLogger.fail(testnode,"Retrying the step");
//            ReportTestLogger.addScreenshotPath(testnode, loginPage.captureScreenshot("LoginTestFail"));
            ReportTestLogger.addScreenshotBase(testnode, loginPage.captureScreenshotBase("LoginTestFail"));
            throw e;
        }
    }
//    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Test(priority = 3)
    public void loginWithValidCredentialsTest() throws IOException {

        testnode = ReportManager.getTest().createNode("TC03 Login with valid credentials");
//        testnode.assignCategory("TC01_login_with_Valid_Credentials");
        loginPage.navigateTo(ConfigReader.getProperty("bank.url"));
        ReportTestLogger.info(testnode, "Navigated to login page");
        ExtentTest innerTestNode;
        ExcelUtils.openExcel(filePath);
        try {
            innerTestNode = ReportTestLogger.createinnerNode(testnode,"Reading username from excel and trying to login");

            String userName=ExcelUtils.getCellData(readDataSheetName,"UserName");
            String actualPassword = ConfigReader.getProperty("bank.password");

            RetryAnalyzer.retryStep(page,() -> loginPage.login(userName,actualPassword),testnode);
            ReportTestLogger.info(innerTestNode, "Username entered is: " + userName);
            ReportTestLogger.info(innerTestNode, "Password entered is: " +actualPassword);
            ReportTestLogger.pass(innerTestNode, "Log in with valid credentials completed Successfully");

//            ReportTestLogger.addScreenshotPath(testnode, loginPage.captureScreenshot("LoginTest"));
            ReportTestLogger.addScreenshotBase(testnode, loginPage.captureScreenshotBase("Login_Test"));
        }catch (Exception e){
            ReportTestLogger.fail(testnode,"Retrying the step");
//            ReportTestLogger.addScreenshotPath(testnode, loginPage.captureScreenshot("LoginTestFail"));
            ReportTestLogger.addScreenshotBase(testnode, loginPage.captureScreenshotBase("LoginTestFail"));
            throw e;
        }
    }

    private void loginErrorFunctionality(ExtentTest innerTestNode,String userName,String password,String input) throws IOException {
        loginPage.login(userName, password);
        ReportTestLogger.info(innerTestNode, "Username entered is: " + userName);
        ReportTestLogger.info(innerTestNode, "Password entered is: " +password);

        innerTestNodes = ReportTestLogger.createinnerNode(innerTestNode,"Verifying error message");

        ReportTestLogger.info(innerTestNodes, "Error title is: " + loginPage.verifyPageMessage("ErrorTitle"));
        ReportTestLogger.info(innerTestNodes, "Error Message is: " +loginPage.verifyPageMessage("ErrorMessage"));
        ReportTestLogger.pass(innerTestNodes,input+" function completed successfully");
        ReportTestLogger.addScreenshotBase(innerTestNode, loginPage.captureScreenshotBase(input.replace(" ","_")));


    }
}
