package Tests;

import ExtentReporter.ReportManager;
import ExtentReporter.ReportTestLogger;
import Listeners.RetryAnalyzer;
import Pages.ForgotPage;
import Pages.RegisterPage;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.model.Report;
import config.ConfigReader;
import org.apache.xmlbeans.impl.xb.xmlconfig.ConfigDocument;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ExcelUtils;

import java.io.IOException;
import java.util.Random;

public class ForgotTest extends BaseTest{
    private ForgotPage forgotpage;
    private String sheetName = "Register";
    String filePath;
    ExtentTest innerTestNodes;
    @BeforeClass
    public void setupBeforeClass() throws IOException {
        ReportManager.startTest("Login TS_01 Registration Page Functionality Validation","ForgotTest");
        forgotpage = new ForgotPage(page);

        filePath = ExcelUtils.readExeclPath() + ConfigReader.getProperty("excel.file.bank");
//
    }

    //    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Test(priority = 1)
    public void forgotPageFieldValidation() throws IOException {

        ExtentTest testnode = ReportManager.getTest().createNode("TC01 Forgot Page FieldName and Field Verification");
//        testnode.assignCategory("TC01_verify_field_name_Error_Message");

        ExtentTest innerTestNode = testnode;
        try {
            innerTestNode = ReportTestLogger.createinnerNode(testnode,"Navigating to Forgot Page");

            forgotPageNavigation(innerTestNode);

            // FirstName Field and error

            innerTestNode = ReportTestLogger.createinnerNode(testnode, "verifying the Fields");
            forgotpage.verifyInputField("FirstName");
            forgotpage.verifyInputFieldName("FirstName");
            ReportTestLogger.info(innerTestNode, "First Name Field is Displayed");

            forgotpage.verifyInputField("LastName");
            forgotpage.verifyInputFieldName("LastName");
            ReportTestLogger.info(innerTestNode, "Last Name Field is Displayed");

            forgotpage.verifyInputField("Address");
            forgotpage.verifyInputFieldName("Address");
            ReportTestLogger.info(innerTestNode, "Address Field is Displayed");

            forgotpage.verifyInputField("City");
            forgotpage.verifyInputFieldName("City");
            ReportTestLogger.info(innerTestNode, "City Field is Displayed");

            forgotpage.verifyInputField("State");
            forgotpage.verifyInputFieldName("State");
            ReportTestLogger.info(innerTestNode, "State Field is Displayed");

            forgotpage.verifyInputField("ZipCode");
            forgotpage.verifyInputFieldName("ZipCode");
            ReportTestLogger.info(innerTestNode, "Zip Code Field is Displayed");

            forgotpage.verifyInputField("SSN");
            forgotpage.verifyInputFieldName("SSN");
            ReportTestLogger.info(innerTestNode, "SSN Field is Displayed");

            ReportTestLogger.pass(innerTestNode, "All Fields and their Names are Displayed");
            ReportTestLogger.addScreenshotBase(innerTestNode, forgotpage.captureScreenshotBase("Forgot_All_Field_Test"));
            ReportTestLogger.pass(testnode, "Field validation has been successfully completed.");

        } catch (Exception e) {
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
//            ReportTestLogger.addScreenshotPath(testnode, registerPage.captureScreenshot("LoginTestFail"));
            ReportTestLogger.addScreenshotBase(testnode, forgotpage.captureScreenshotBase("Forgot_Test_Fail"));
            throw e;
        }catch (AssertionError e){
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
//            ReportTestLogger.addScreenshotPath(testnode, registerPage.captureScreenshot("LoginTestFail"));
            ReportTestLogger.addScreenshotBase(testnode, forgotpage.captureScreenshotBase("Forgot_Assertion_Test_Fail"));
            throw e;
        }
    }
    @Test(priority = 2)
    public void forgotPageErrorFieldValidation() throws IOException {

        ExtentTest testnode = ReportManager.getTest().createNode("TC01 Forgot Page Error Message Verification");
//        testnode.assignCategory("TC01_verify_field_name_Error_Message");
        String errorMessage ="";
        ExtentTest innerTestNode = testnode;
        try {
            innerTestNode = ReportTestLogger.createinnerNode(testnode,"Navigating to Forgot Page");

            forgotPageNavigation(innerTestNode);

            // FirstName Field and error
            innerTestNode = ReportTestLogger.createinnerNode(testnode, "verifying the Error Message");

            errorMessage=forgotpage.getErrorMessageField("FirstName");
            ReportTestLogger.info(innerTestNode, "First Name Error Message is Displayed");


            errorMessage=forgotpage.getErrorMessageField("LastName");
            ReportTestLogger.info(innerTestNode, "Last Name Field is Displayed");
            ReportTestLogger.pass(innerTestNode,errorMessage);

            errorMessage=forgotpage.getErrorMessageField("Address");
            ReportTestLogger.info(innerTestNode, "Address Field is Displayed");
            ReportTestLogger.pass(innerTestNode,errorMessage);

            errorMessage=forgotpage.getErrorMessageField("City");
            ReportTestLogger.info(innerTestNode, "City Field is Displayed");
            ReportTestLogger.pass(innerTestNode,errorMessage);

            errorMessage=forgotpage.getErrorMessageField("State");
            ReportTestLogger.info(innerTestNode, "State Field is Displayed");
            ReportTestLogger.pass(innerTestNode,errorMessage);

            errorMessage=forgotpage.getErrorMessageField("ZipCode");
            ReportTestLogger.info(innerTestNode, "Zip Code Field is Displayed");
            ReportTestLogger.pass(innerTestNode,errorMessage);

            errorMessage=forgotpage.getErrorMessageField("SSN");
            ReportTestLogger.info(innerTestNode, "SSN Field is Displayed");
            ReportTestLogger.pass(innerTestNode,errorMessage);

            ReportTestLogger.pass(innerTestNode, "All Fields Error message are Displayed");
            ReportTestLogger.pass(testnode, "Field Error message validation has been successfully completed.");

            ReportTestLogger.addScreenshotBase(innerTestNode, forgotpage.captureScreenshotBase("Forgot_All_Error_Test"));
        } catch (Exception e) {
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
//            ReportTestLogger.addScreenshotPath(testnode, registerPage.captureScreenshot("LoginTestFail"));
            ReportTestLogger.addScreenshotBase(testnode, forgotpage.captureScreenshotBase("Forgot_Test_Fail"));
            throw e;
        }catch (AssertionError e){
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
//            ReportTestLogger.addScreenshotPath(testnode, registerPage.captureScreenshot("LoginTestFail"));
            ReportTestLogger.addScreenshotBase(testnode, forgotpage.captureScreenshotBase("Forgot_Assertion_Test_Fail"));
            throw e;
        }
    }

    @Test(priority = 2)
    public void forgotFunctionality() throws IOException {

        ExtentTest testnode = ReportManager.getTest().createNode("TC01 Forgot functionality Verification");
//        testnode.assignCategory("TC01_verify_field_name_Error_Message");
        String errorMessage ="";
        ExtentTest innerTestNode = testnode;
        ExcelUtils.openExcel(filePath);
        String successMessage="";
        try {
            int rowNumber = ExcelUtils.getTotalRowCount(sheetName);
            rowNumber=new Random().nextInt(rowNumber) + 1;
            innerTestNode = ReportTestLogger.createinnerNode(testnode,"Navigating to Forgot Page");

            forgotPageNavigation(innerTestNode);

            // FirstName Field and error
            innerTestNode = ReportTestLogger.createinnerNode(testnode, "Fetching the account with data");

            String firstName=ExcelUtils.getCellDataAtRow(sheetName,rowNumber,"FirstName");
            String lastName = ExcelUtils.getCellDataAtRow(sheetName,rowNumber,"LastName");
            String address = ExcelUtils.getCellDataAtRow(sheetName,rowNumber,"Address");
            String city = ExcelUtils.getCellDataAtRow(sheetName,rowNumber,"City");
            String state = ExcelUtils.getCellDataAtRow(sheetName,rowNumber,"State");
            String zipCode = ExcelUtils.getCellDataAtRow(sheetName,rowNumber,"ZipCode");
            String ssn = ExcelUtils.getCellDataAtRow(sheetName,rowNumber,"SSN");

            forgotpage.writeIntoField("FirstName",firstName);
            ReportTestLogger.info(innerTestNode,"First Name is : "+firstName);
            forgotpage.writeIntoField("LastName",lastName);
            ReportTestLogger.info(innerTestNode,"Last Name is : "+lastName);
            forgotpage.writeIntoField("Address",address);
            ReportTestLogger.info(innerTestNode,"Address is : "+address);
            forgotpage.writeIntoField("City",city);
            ReportTestLogger.info(innerTestNode,"State is : "+address);
            forgotpage.writeIntoField("State",city);
            ReportTestLogger.info(innerTestNode,"City is : "+city);
            forgotpage.writeIntoField("ZipCode",zipCode);
            ReportTestLogger.info(innerTestNode,"ZipCode is : "+zipCode);
            forgotpage.writeIntoField("SSN",ssn);
            ReportTestLogger.info(innerTestNode,"SSN is : "+ssn);

            forgotpage.clickFunctionField("FindMyInfoButton");

            ReportTestLogger.pass(innerTestNode,"Successfully fetched the account details.");

            innerTestNodes=ReportTestLogger.createinnerNode(innerTestNode,"verifying the forgot functionality open Page");

            innerTestNode = ReportTestLogger.createinnerNode(innerTestNodes,"Welcome Page Validation");

            successMessage=forgotpage.getMessageOnPage("SuccessTitle");

            ReportTestLogger.info(innerTestNode,successMessage);
            ReportTestLogger.info(innerTestNode,"Title: "+successMessage);
            successMessage=forgotpage.getMessageOnPage("SuccessMessage");
            ReportTestLogger.info(innerTestNode,"Message: "+successMessage);

            ReportTestLogger.info(innerTestNode,"Fetched account details.");

            successMessage=forgotpage.getMessageOnPage("successusername");
            ReportTestLogger.info(innerTestNode,successMessage);
            String userName = successMessage.substring(successMessage.indexOf(":") + 1).trim();
            String expectedUserName= ExcelUtils.getCellDataAtRow(sheetName,rowNumber,"UserName");
            Assert.assertTrue(expectedUserName.equalsIgnoreCase(userName));


            successMessage=forgotpage.getMessageOnPage("successpassword");
            ReportTestLogger.info(innerTestNode,successMessage);
            String password = successMessage.substring(successMessage.indexOf(":") + 1).trim();
            String expectedPassword= ConfigReader.getProperty("bank.password");
            Assert.assertTrue(expectedPassword.equalsIgnoreCase(password));

            ReportTestLogger.pass(innerTestNode,"SuccessPage verification is completed successfully");

            ReportTestLogger.addScreenshotBase(testnode, forgotpage.captureScreenshotBase("Forgot_Test"));
            ReportTestLogger.pass(testnode, "Forgot functionality is completed successfully");


        } catch (Exception e) {
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
//            ReportTestLogger.addScreenshotPath(testnode, registerPage.captureScreenshot("LoginTestFail"));
            ReportTestLogger.addScreenshotBase(testnode, forgotpage.captureScreenshotBase("Forgot_Test_Fail"));
            throw e;
        }catch (AssertionError e){
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
//            ReportTestLogger.addScreenshotPath(testnode, registerPage.captureScreenshot("LoginTestFail"));
            ReportTestLogger.addScreenshotBase(testnode, forgotpage.captureScreenshotBase("Forgot_Assertion_Test_Fail"));
            throw e;
        }
    }
    private void forgotPageNavigation(ExtentTest innerTestNode){
        forgotpage.navigateTo(ConfigReader.getProperty("bank.url"));
        ReportTestLogger.info(innerTestNode, "Navigated to Url");

        RetryAnalyzer.retryStep(page,() -> forgotpage.clickFunctionField("ForgotLink"),innerTestNode);
        ReportTestLogger.pass(innerTestNode, "Forgot link is clicked ");

        String forgotTitle = forgotpage.getMessageOnPage("ForgotTitle");
        String forgotMessage = forgotpage.getMessageOnPage("ForgotMessage");
        ReportTestLogger.info(innerTestNode, "Forgot Header: "+forgotTitle);
        ReportTestLogger.info(innerTestNode, "Forgot Message: "+forgotMessage);

        Assert.assertEquals(forgotpage.getForgotPageTitle(), page.title(),"Page title verification failed!" );


    }
}
