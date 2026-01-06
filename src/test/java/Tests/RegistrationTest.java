package Tests;

import ExtentReporter.ReportManager;
import ExtentReporter.ReportTestLogger;
import Listeners.RetryAnalyzer;
import Pages.RegisterPage;
import Pages.TestDataGenerator;
import com.aventstack.extentreports.ExtentTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ExcelUtils;

import java.io.IOException;

public class RegistrationTest extends BaseTest {
    private RegisterPage registerPage;
    private String sheetName = "Register";
    String filePath;
    @BeforeClass
    public void setupBeforeClass() throws IOException {
        ReportManager.startTest("Login TS_01 Registration Page Functionality Validation","RegistrationTest");
        registerPage = new RegisterPage(page);
        filePath = ExcelUtils.readExeclPath() + ConfigReader.getProperty("excel.file.bank");
//
    }

    //    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Test(priority = 1)
    public void registrationPageValidation() throws IOException {

        ExtentTest testnode = ReportManager.getTest().createNode("TC01 Registration Page FieldName Verification");
//        testnode.assignCategory("TC01_verify_field_name_Error_Message");

        ExtentTest innerTestNode = testnode;
        try {
            innerTestNode = ReportTestLogger.createinnerNode(testnode,"Navigating to Register Page");

            registerPageNaviagation(innerTestNode);

            // FirstName Field and error

            innerTestNode = ReportTestLogger.createinnerNode(testnode, "verifying the Fields");
            registerPage.verifyFieldPresence("FirstName");
            registerPage.verifyFieldPresences("FirstName","Field");
            ReportTestLogger.info(innerTestNode, "First Name Field is Displayed");

            registerPage.verifyFieldPresence("LastName");
            ReportTestLogger.info(innerTestNode, "Last Name Field is Displayed");

            registerPage.verifyFieldPresence("Address");
            ReportTestLogger.info(innerTestNode, "Address Field is Displayed");

            registerPage.verifyFieldPresence("City");
            ReportTestLogger.info(innerTestNode, "City Field is Displayed");

            registerPage.verifyFieldPresence("State");
            ReportTestLogger.info(innerTestNode, "State Field is Displayed");

            registerPage.verifyFieldPresence("ZipCode");
            ReportTestLogger.info(innerTestNode, "Zip Code Field is Displayed");

            registerPage.verifyFieldPresence("PhoneNumber");
            ReportTestLogger.info(innerTestNode, "Phone number Field is Displayed");

            registerPage.verifyFieldPresence("SSN");
            ReportTestLogger.info(innerTestNode, "SSN Field is Displayed");

            registerPage.verifyFieldPresence("UserName");
            ReportTestLogger.info(innerTestNode, "User Name Field is Displayed");

            registerPage.verifyFieldPresence("Password");
            ReportTestLogger.info(innerTestNode, "Password Field is Displayed");

            registerPage.verifyFieldPresence("ConfirmPassword");
            ReportTestLogger.info(innerTestNode, "Confirm Password Field is Displayed");

            ReportTestLogger.pass(innerTestNode, "All Fields are Displayed");
            ReportTestLogger.addScreenshotBase(innerTestNode, registerPage.captureScreenshotBase("Registration_All_Field_Test"));

            ReportTestLogger.pass(testnode, "Field validation has been successfully completed.");
        } catch (Exception e) {
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
//            ReportTestLogger.addScreenshotPath(testnode, registerPage.captureScreenshot("LoginTestFail"));
            ReportTestLogger.addScreenshotBase(testnode, registerPage.captureScreenshotBase("Registration_Test_Fail"));

            throw e;
        }catch (AssertionError e){
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
//            ReportTestLogger.addScreenshotPath(testnode, registerPage.captureScreenshot("LoginTestFail"));
            ReportTestLogger.addScreenshotBase(testnode, registerPage.captureScreenshotBase("Registration_Test_Fail"));
        }
    }

    @Test(priority = 2)
    public void errorMessageValidation() throws Exception{
        ExtentTest testnode = ReportManager.getTest().createNode("TC02 Registration Page Error message Verification");
//        testnode.assignCategory("TC02_verify_Error_Message");
        ExtentTest innerTestNode = testnode;
        String errorMessage = "";

        try {
            innerTestNode = ReportTestLogger.createinnerNode(testnode,"Navigating to Register Page");
            registerPageNaviagation(innerTestNode);

            //Verifying All Error Message
            innerTestNode = ReportTestLogger.createinnerNode(testnode, "verifying the Error Message for Fields");

            errorMessage = registerPage.verifyErrorMessage("FirstName");
            ReportTestLogger.info(innerTestNode, "First name Error Message is Displayed ");
            ReportTestLogger.pass(innerTestNode, errorMessage);

            errorMessage = registerPage.verifyErrorMessage("LastName");
            ReportTestLogger.info(innerTestNode, "Last name Error Message is Displayed ");
            ReportTestLogger.pass(innerTestNode, errorMessage);

            errorMessage = registerPage.verifyErrorMessage("Address");
            ReportTestLogger.info(innerTestNode, "Address Error Message is Displayed ");
            ReportTestLogger.pass(innerTestNode, errorMessage);

            errorMessage = registerPage.verifyErrorMessage("City");
            ReportTestLogger.info(innerTestNode, "City Error Message is Displayed ");
            ReportTestLogger.pass(innerTestNode, errorMessage);

            errorMessage = registerPage.verifyErrorMessage("State");
            ReportTestLogger.info(innerTestNode, "State Error Message is Displayed ");
            ReportTestLogger.pass(innerTestNode, errorMessage);

            errorMessage = registerPage.verifyErrorMessage("ZipCode");
            ReportTestLogger.info(innerTestNode, "Zip Code Error Message is Displayed ");
            ReportTestLogger.pass(innerTestNode, errorMessage);

//            errorMessage = registerPage.verifyErrorMessage("PhoneNumber");
//            ReportTestLogger.info(innerTestNode, "Phone Number Error Message is Displayed ");
//            ReportTestLogger.pass(innerTestNode,errorMessage);

            errorMessage = registerPage.verifyErrorMessage("SSN");
            ReportTestLogger.info(innerTestNode, "SSN Error Message is Displayed ");
            ReportTestLogger.pass(innerTestNode, errorMessage);

            errorMessage = registerPage.verifyErrorMessage("UserName");
            ReportTestLogger.info(innerTestNode, "User name Error Message is Displayed ");
            ReportTestLogger.pass(innerTestNode, errorMessage);

            errorMessage = registerPage.verifyErrorMessage("Password");
            ReportTestLogger.info(innerTestNode, "Password Error Message is Displayed ");
            ReportTestLogger.pass(innerTestNode, errorMessage);

            errorMessage = registerPage.verifyErrorMessage("ConfirmPassword");
            ReportTestLogger.info(innerTestNode, "Confirm Password Error Message is Displayed ");
            ReportTestLogger.pass(innerTestNode, errorMessage);

            ReportTestLogger.pass(innerTestNode, "All Error Message is Displayed");
            ReportTestLogger.addScreenshotBase(innerTestNode, registerPage.captureScreenshotBase("Registration_All_Error_Message_Test"));

            ReportTestLogger.pass(testnode, "Error message validation has been successfully completed.");


        }catch (Exception e) {
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
//            ReportTestLogger.addScreenshotPath(testnode, registerPage.captureScreenshot("LoginTestFail"));
            ReportTestLogger.addScreenshotBase(testnode, registerPage.captureScreenshotBase("Registration_Test_Fail"));

            throw e;
        }catch (AssertionError e){
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
//            ReportTestLogger.addScreenshotPath(testnode, registerPage.captureScreenshot("LoginTestFail"));
            ReportTestLogger.addScreenshotBase(testnode, registerPage.captureScreenshotBase("Registration_Test_Fail"));
        }

    }

    @Test(priority = 3)
    public void passwordErrorMessageValidation() throws Exception {
        ExtentTest testnode = ReportManager.getTest().createNode("TC03 Registration Page Confirm Password Error message Verification");
//        testnode.assignCategory("TC02_verify_Error_Message");
        ExtentTest innerTestNode = testnode;
        String errorMessage = "";
        ExcelUtils.openExcel(filePath);
        try {

            innerTestNode = ReportTestLogger.createinnerNode(testnode,"Navigating to Register Page");
            registerPageNaviagation(innerTestNode);

            //writing data into form to register

            innerTestNode = ReportTestLogger.createinnerNode(testnode, "Writing input to form to register with two different password");

            String firstName = TestDataGenerator.randomFirstName();
            fillFieldAndLogForRegister("FirstName",firstName,innerTestNode);

            String lastName = TestDataGenerator.randomLastName();
            fillFieldAndLogForRegister("LastName",lastName,innerTestNode);

            String address = TestDataGenerator.randomStreet();
            fillFieldAndLogForRegister("Address",address,innerTestNode);

            String city = TestDataGenerator.randomCity();
            fillFieldAndLogForRegister("City",city,innerTestNode);

            String state = TestDataGenerator.randomState();
            fillFieldAndLogForRegister("State",state,innerTestNode);

            String zipCode = TestDataGenerator.randomZipCode();
            fillFieldAndLogForRegister("ZipCode",zipCode,innerTestNode);

            String phoneNumber = TestDataGenerator.randomPhoneNumber();
            fillFieldAndLogForRegister("PhoneNumber",phoneNumber,innerTestNode);

            String ssn = TestDataGenerator.randomSSN();
            fillFieldAndLogForRegister("SSN",ssn,innerTestNode);

            String userName = TestDataGenerator.randomUsername();
            fillFieldAndLogForRegister("UserName",userName,innerTestNode);

            String passwordIs = ConfigReader.getProperty("bank.password");
            String passwordExcel = "********";
            registerPage.fillForm("Password", passwordIs);
            ExcelUtils.writeResult(sheetName,"Password",passwordExcel);
            ReportTestLogger.info(innerTestNode, "Password: "+passwordExcel);

            String confirmPasswordIs = ConfigReader.getProperty("bank.password")+"asdf";
            registerPage.fillForm("ConfirmPassword", confirmPasswordIs);
            ExcelUtils.writeResult(sheetName,"ConfirmPassword",passwordExcel+"****");
            ReportTestLogger.info(innerTestNode, "ConfirmPassword: "+passwordExcel+"****");

            errorMessage = registerPage.verifyErrorMessage("ConfirmPassword");
//            errorMessage = registerPage.getErrorField("ConfirmPassword");
            ReportTestLogger.info(innerTestNode, "Confirm Password Error Message is Displayed ");
            ReportTestLogger.pass(innerTestNode, errorMessage);

            ReportTestLogger.addScreenshotBase(innerTestNode, registerPage.captureScreenshotBase("Registration_Confirm_Error_Message_Test"));
            ReportTestLogger.pass(testnode, "Password mismatch validation has been successfully completed.");

        }catch (Exception e) {
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
//            ReportTestLogger.addScreenshotPath(testnode, registerPage.captureScreenshot("LoginTestFail"));
            ReportTestLogger.addScreenshotBase(testnode, registerPage.captureScreenshotBase("Registration_Test_Fail"));

            throw e;
        }catch (AssertionError e){
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
//            ReportTestLogger.addScreenshotPath(testnode, registerPage.captureScreenshot("LoginTestFail"));
            ReportTestLogger.addScreenshotBase(testnode, registerPage.captureScreenshotBase("Registration_Test_Fail"));
        }
    }


    @Test(priority = 4)
    public void registrationWithValidData() throws IOException {

        ExtentTest testnode = ReportManager.getTest().createNode("TC04 Registration with Valid data");
//        testnode.assignCategory("TC03_registration_with_valid_data");
        ExtentTest innerTestNode = testnode;
        ExtentTest innerTestNodes ;
        ExcelUtils.openExcel(filePath);
        try {

            innerTestNode = ReportTestLogger.createinnerNode(testnode,"Navigating to Register Page");

            registerPageNaviagation(innerTestNode);

            //writing data into form to register

            innerTestNode = ReportTestLogger.createinnerNode(testnode, "Entered required details in the registration form and submitted for validation");

            String firstName = TestDataGenerator.randomFirstName();
            fillFieldAndLogForRegister("FirstName",firstName,innerTestNode);

            String lastName = TestDataGenerator.randomLastName();
            fillFieldAndLogForRegister("LastName",lastName,innerTestNode);

            String address = TestDataGenerator.randomStreet();
            fillFieldAndLogForRegister("Address",address,innerTestNode);

            String city = TestDataGenerator.randomCity();
            fillFieldAndLogForRegister("City",city,innerTestNode);

            String state = TestDataGenerator.randomState();
            fillFieldAndLogForRegister("State",state,innerTestNode);

            String zipCode = TestDataGenerator.randomZipCode();
            fillFieldAndLogForRegister("ZipCode",zipCode,innerTestNode);

            String phoneNumber = TestDataGenerator.randomPhoneNumber();
            fillFieldAndLogForRegister("PhoneNumber",phoneNumber,innerTestNode);

            String ssn = TestDataGenerator.randomSSN();
            fillFieldAndLogForRegister("SSN",ssn,innerTestNode);

            String userName = TestDataGenerator.randomUsernameWithFullName(firstName,lastName);
            fillFieldAndLogForRegister("UserName",userName,innerTestNode);

            String passwordIs = ConfigReader.getProperty("bank.password");
            String passwordExcel = "********";
            registerPage.fillForm("Password", passwordIs);
            ExcelUtils.writeResult(sheetName,"Password",passwordExcel);
            ReportTestLogger.info(innerTestNode, "Password: "+passwordExcel);

            String confirmPasswordIs = ConfigReader.getProperty("bank.password");
            registerPage.fillForm("ConfirmPassword", confirmPasswordIs);
            ExcelUtils.writeResult(sheetName,"ConfirmPassword",passwordExcel);
            ReportTestLogger.info(innerTestNode, "ConfirmPassword: "+passwordExcel);


            ReportTestLogger.addScreenshotBase(innerTestNode, registerPage.captureScreenshotBase("Registration_Test"));
            registerPage.clickFunctionOnField("RegisterButton");
//            registerPage.clickFunctionOnFields("RegisterButton");

            innerTestNodes = ReportTestLogger.createinnerNode(testnode,"Verifying Welcome Page");

            innerTestNode = ReportTestLogger.createinnerNode(innerTestNodes,"Welcome Page Validation");
            String expectedTitle = registerPage.verifyWelComePage("WelcomeTitle");
            Assert.assertTrue(expectedTitle.toLowerCase().contains(userName));

            ReportTestLogger.info(innerTestNode,"Title: "+expectedTitle);
            ReportTestLogger.info(innerTestNode,"Welcome Message: "+registerPage.verifyWelComePage("WelcomeSuccessMessage"));
            ReportTestLogger.pass(innerTestNode,"Title is matched");

            ReportTestLogger.pass(innerTestNodes,"Welcome Page validation is completed successfully");
            ReportTestLogger.addScreenshotBase(innerTestNodes, registerPage.captureScreenshotBase("Registration_Welcome_Page"));

            ReportTestLogger.pass(testnode, "User registration form completed successfully");

            ExcelUtils.fileOutPut();
        } catch (Exception e) {
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
//            ReportTestLogger.addScreenshotPath(testnode, registerPage.captureScreenshot("LoginTestFail"));
            ReportTestLogger.addScreenshotBase(testnode, registerPage.captureScreenshotBase("Registration_Test_Fail"));
            throw e;
        }catch (AssertionError e){
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
//            ReportTestLogger.addScreenshotPath(testnode, registerPage.captureScreenshot("LoginTestFail"));
            ReportTestLogger.addScreenshotBase(testnode, registerPage.captureScreenshotBase("Registration_Test_Assertion_Fail"));
            throw e;
        }
    }

    private void fillFieldAndLogForRegister(String fieldName, String value, ExtentTest innerTestNode) throws IOException {
        registerPage.fillForm(fieldName, value);                  // Fill the form
        ExcelUtils.writeResult(sheetName, fieldName, value);     // Write to Excel
        ReportTestLogger.info(innerTestNode, fieldName + ": " + value); // Log it
    }

    private void registerPageNaviagation(ExtentTest innerTestNode){
        registerPage.navigateTo(ConfigReader.getProperty("bank.url"));
        ReportTestLogger.info(innerTestNode, "Navigated to Url");

        RetryAnalyzer.retryStep(page,() -> registerPage.clickFunctionOnField("registerLink"),innerTestNode);
        ReportTestLogger.pass(innerTestNode, "Register link is clicked ");

        String registerTitle = registerPage.verifyWelComePage("RegisterTitle");
        String registerMessage = registerPage.verifyWelComePage("RegisterMessage");
        ReportTestLogger.info(innerTestNode, "Register Header: "+registerTitle);
        ReportTestLogger.info(innerTestNode, "Register Message: "+registerMessage);

        Assert.assertEquals(registerPage.getRegisterPageTitle(), page.title(),"Page title verification failed!" );


    }
}

