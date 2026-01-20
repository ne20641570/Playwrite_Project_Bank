package tests.ui;

import extentReporter.ReportManager;
import extentReporter.ReportTestLogger;
import listeners.RetryAnalyzer;
import pages.ForgotPage;
import pages.InputField;
import com.aventstack.extentreports.ExtentTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;
import utils.DBUtils;
import utils.ExcelUtils;
import listeners.Scenario;
import utils.TestDataGenerator;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
//@Listeners(TestListeners.class)
@Scenario("TS_03 Forgot Page Functionality Validation")
@Test(groups = "Forgot")
public class ForgotTest extends BaseTest {

    private ForgotPage forgotPage;
    private String sheetName = "Register";
    private String filePath;
    private static ThreadLocal<ExtentTest> testTL = new ThreadLocal<>();
    String attachmentIs;
    ExtentTest testnode;
    ExtentTest innerTestNode;
    ExtentTest innerTestNodes;

    // ------------------- Class-level setup for report -------------------
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
    @BeforeMethod(alwaysRun = true)
    public void setupBeforeMethod(Method method,ITestContext context) {
        Scenario scenario = this.getClass().getAnnotation(Scenario.class);
        forgotPage = new ForgotPage(page);
        // Read the @Test description dynamically
        String testDescription = method.isAnnotationPresent(Test.class)
                ? method.getAnnotation(Test.class).description()
                : method.getName();
        attachmentIs=context.getCurrentXmlTest().getName()+"_"+method.getName();

        ExtentTest parentTest = ReportManager.getParentTest(context.getName()+"-->"+scenario.value());
        if (parentTest == null) {
//            throw new RuntimeException("ReportManager.getTest() is null in @BeforeMethod");
            ReportManager.startTest(context.getName() + " --> " + testDescription);
            parentTest = ReportManager.getTest();
        }
        testnode =parentTest.createNode(testDescription);

//        testnode = ReportManager.getTest().createNode(testDescription);
//        innerTestNode = ReportTestLogger.createNode(testnode,testDescription);
        innerTestNode = ReportTestLogger.createinnerNode(testnode, "Navigating to Main Page");

        // Navigate and log
        forgotPage.navigateTo(ConfigReader.getProperty("bank.url"));
        ReportTestLogger.info(innerTestNode, "Navigated to URL");

        RetryAnalyzer.retryStep(() -> forgotPage.clickFunctionField("ForgotLink"), innerTestNode);
        ReportTestLogger.pass(innerTestNode, "Forgot link is clicked");

        // Log page messages
        ReportTestLogger.info(innerTestNode, "Forgot Header: " + forgotPage.getMessageOnPage("ForgotTitle"));
        ReportTestLogger.info(innerTestNode, "Forgot Message: " + forgotPage.getMessageOnPage("ForgotMessage"));

        // Assert page title
        Assert.assertEquals(forgotPage.getForgotPageTitle(), page.title(), "Page title verification failed!");
    }

    // ------------------- Test 1: Field Presence -------------------
    @Test(priority = 1,retryAnalyzer = RetryAnalyzer.class, description = "TC01 Forgot Page FieldName Verification")
    public void forgotPageFieldValidation() throws IOException {
        innerTestNode = ReportTestLogger.createinnerNode(testnode, "Verifying the Fields");
        try {
            for (InputField field : InputField.values()) {
                if(field!=InputField.PhoneNumber&&field!=InputField.UserName&&field!=InputField.Password&&field!=InputField.ConfirmPassword) {
                    forgotPage.verifyInputField(field.name());
                    forgotPage.verifyInputFieldName(field.name());
                    ReportTestLogger.info(innerTestNode, field.name() + " Field is Displayed");
                }
            }

            ReportTestLogger.pass(innerTestNode, "All Fields are Displayed");
            ReportTestLogger.addScreenshotBase(innerTestNode, forgotPage.captureScreenshotBase(attachmentIs+"_Pass"));
            ReportTestLogger.pass(testnode, "Field validation has been successfully completed.");
        } catch (Exception | AssertionError e) {
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
            ReportTestLogger.addScreenshotBase(innerTestNode, forgotPage.captureScreenshotBase(attachmentIs+"_Fail"));
            throw e;
        }
    }

    // ------------------- Test 2: Error Messages -------------------
    @Test(priority = 2,retryAnalyzer = RetryAnalyzer.class, description = "TC02 Forgot Page Error message Verification")
    public void forgotPageErrorFieldValidation() throws IOException {
        innerTestNode = ReportTestLogger.createinnerNode(testnode, "Verifying the Error Message for Fields");

        try {
            for (InputField field : InputField.values()) {
                if(field!=InputField.PhoneNumber&&field!=InputField.UserName&&field!=InputField.Password&&field!=InputField.ConfirmPassword) {
                    String errorMessage = forgotPage.getErrorMessageField(field.name());
                    ReportTestLogger.info(innerTestNode, field.name() + " Error Message is Displayed");
                    ReportTestLogger.pass(innerTestNode, errorMessage);
                }
            }
            ReportTestLogger.pass(innerTestNode, "All Error Messages are Displayed");
            ReportTestLogger.addScreenshotBase(innerTestNode, forgotPage.captureScreenshotBase(attachmentIs+"_Test"));
            ReportTestLogger.pass(testnode, "Error message validation completed successfully.");
        } catch (Exception | AssertionError e) {
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
            ReportTestLogger.addScreenshotBase(innerTestNode, forgotPage.captureScreenshotBase(attachmentIs+"_Fail"));
            throw e;
        }
    }

    // ------------------- Test 3: Invalid Data -------------------
    @Test(priority = 3,alwaysRun=true, retryAnalyzer = RetryAnalyzer.class,description = "TC03 Forgot functionality Verification with Invalid Data")
    public void forgotInvalidFunctionality() throws IOException, SQLException {
        innerTestNode = ReportTestLogger.createinnerNode(testnode, "Fetching account with invalid data");
//        filePath = ExcelUtils.readExeclPath() + ConfigReader.getProperty("excel.file.bank");
//        ExcelUtils.openExcel(filePath);
        try {
//            int rowNumber = new Random().nextInt(ExcelUtils.getTotalRowCount(sheetName)) + 1;
//            Map<InputField, String> formData = buildFormData(rowNumber, invalidData);

            Map<InputField, String> dbData = DBUtils.getUserByUserName(String.valueOf(InputField.UserName),DBUtils.getRandomUserName());
            for (Map.Entry<InputField, String> entry : dbData.entrySet()) {
                if(entry.getKey()!=InputField.PhoneNumber&&entry.getKey()!=InputField.UserName&&entry.getKey()!=InputField.Password&&entry.getKey()!=InputField.ConfirmPassword) {
                    String value = entry.getValue();
                    if(entry.getKey()==InputField.SSN ) {
                      value = entry.getValue()+"8908";
                    }else if(entry.getKey()==InputField.State){
                        value=entry.getValue()+"NY";
                    }
                    fillFieldsAndLog(entry.getKey(), value, innerTestNode);
                }
            }
            ReportTestLogger.addScreenshotBase(innerTestNode, forgotPage.captureScreenshotBase("Forgot_Form_Filled"));

            RetryAnalyzer.retryStep(() -> forgotPage.clickFunctionField("FindMyInfoButton"), innerTestNode);

            innerTestNodes = ReportTestLogger.createinnerNode(testnode, "Verifying Error Page for invalid data");
            innerTestNode = ReportTestLogger.createinnerNode(innerTestNodes, "Error Page Validation");

            String errorTitle = forgotPage.getMessageOnPage("ErrorTitle");
            String errorMessage = forgotPage.getMessageOnPage("ErrorMessage");

            ReportTestLogger.info(innerTestNode, "Error Title: " + errorTitle);
            ReportTestLogger.info(innerTestNode, "Error Message: " + errorMessage);

            Assert.assertTrue(page.title().equals(forgotPage.getForgotErrorPageTitle()));

            ReportTestLogger.pass(innerTestNode, "Error Page validation completed successfully");
            ReportTestLogger.addScreenshotBase(innerTestNode, forgotPage.captureScreenshotBase(attachmentIs+"_Pass"));
            ReportTestLogger.pass(testnode, "Invalid data functionality completed successfully.");
        } catch (Exception | AssertionError e) {
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
            ReportTestLogger.addScreenshotBase(innerTestNode, forgotPage.captureScreenshotBase(attachmentIs+"_Fail"));
            throw e;
        }
    }

    // ------------------- Test 4: Valid Data -------------------
    @Test(priority = 4,alwaysRun=true, retryAnalyzer = RetryAnalyzer.class,description = "TC04 Forgot functionality Verification with Valid Data")
    public void forgotFunctionality() throws IOException, SQLException {
        innerTestNode = ReportTestLogger.createinnerNode(testnode, "Fetching account with valid data");
        boolean invalidData=false;
//        filePath = ExcelUtils.readExeclPath() + ConfigReader.getProperty("excel.file.bank");
//        ExcelUtils.openExcel(filePath);

        try {
//            System.out.println(ExcelUtils.getTotalRowCount(sheetName));
//            int rowNumber = new Random().nextInt(ExcelUtils.getTotalRowCount(sheetName)) + 1;
//            if(rowNumber<1) rowNumber=1;

//            Map<InputField, String> formData = buildFormData(rowNumber, invalidData);
            Map<InputField, String> dbData = DBUtils.getUserByUserName(String.valueOf(InputField.UserName),DBUtils.getRandomUserName());
            String expectedUserName = "";

            for (Map.Entry<InputField, String> entry : dbData.entrySet()) {
                if(entry.getKey()!=InputField.PhoneNumber&&entry.getKey()!=InputField.UserName&&entry.getKey()!=InputField.Password&&entry.getKey()!=InputField.ConfirmPassword) {
                    fillFieldsAndLog(entry.getKey(), entry.getValue(), innerTestNode);
                }
                if(entry.getKey()==InputField.UserName){
                    expectedUserName= entry.getValue();
                }
            }
            ReportTestLogger.addScreenshotBase(innerTestNode, forgotPage.captureScreenshotBase("Forgot_Form_Filled"));

            RetryAnalyzer.retryStep(() -> forgotPage.clickFunctionField("FindMyInfoButton"), innerTestNode);

            innerTestNodes = ReportTestLogger.createinnerNode(testnode, "Verifying Success Page for valid data");
            innerTestNode = ReportTestLogger.createinnerNode(innerTestNodes, "Success Page Validation");

            String successMessage = forgotPage.getMessageOnPage("SuccessTitle");
            ReportTestLogger.info(innerTestNode, "Title: " + successMessage);

            successMessage = forgotPage.getMessageOnPage("SuccessMessage");
            ReportTestLogger.info(innerTestNode, "Message: " + successMessage);

            String credentials = forgotPage.getMessageOnPage("successPageCredentials");
//            String expectedUserName = ExcelUtils.getCellDataAtRow(sheetName, rowNumber, "UserName");
//            String expectedUserName = DBUtils.getRandomUserName();
            String expectedPassword = ConfigReader.getProperty("bank.password");
            System.out.println("exp: "+credentials+"\nact: "+expectedUserName);
            Assert.assertTrue(credentials.contains(expectedUserName));
            Assert.assertTrue(credentials.contains(expectedPassword));

            ReportTestLogger.info(innerTestNode, "Credentials displayed: " + credentials);
            ReportTestLogger.pass(innerTestNode, "Success Page validation completed successfully");
            ReportTestLogger.addScreenshotBase(innerTestNode, forgotPage.captureScreenshotBase(attachmentIs+"_Pass"));

            forgotPage.clickFunctionField("LogoutLink");
            ReportTestLogger.pass(testnode, "Forgot functionality with valid data completed successfully");

        } catch (Exception | AssertionError e) {
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
            ReportTestLogger.addScreenshotBase(innerTestNode, forgotPage.captureScreenshotBase(attachmentIs+"_Fail"));
            throw e;
        }
    }

//    private Map<InputField, String> buildFormData(int row, boolean invalidData) {
//        Map<InputField, String> data = new LinkedHashMap<>();
//        data.put(InputField.FirstName, ExcelUtils.getCellDataAtRow(sheetName, row, InputField.FirstName.name()));
//        data.put(InputField.LastName, ExcelUtils.getCellDataAtRow(sheetName, row, InputField.LastName.name()));
//        data.put(InputField.Address, ExcelUtils.getCellDataAtRow(sheetName, row, InputField.Address.name()));
//        data.put(InputField.City, ExcelUtils.getCellDataAtRow(sheetName, row, InputField.City.name()) +
//                (invalidData ? TestDataGenerator.randomCity() : ""));
//        data.put(InputField.State, ExcelUtils.getCellDataAtRow(sheetName, row, InputField.State.name()));
//        data.put(InputField.ZipCode, ExcelUtils.getCellDataAtRow(sheetName, row, InputField.ZipCode.name()) +
//                (invalidData ? TestDataGenerator.randomZipCode().substring(0, 3) : ""));
//        data.put(InputField.SSN, ExcelUtils.getCellDataAtRow(sheetName, row, InputField.SSN.name()) +
//                (invalidData ? TestDataGenerator.randomSSN().substring(0, 3) : ""));
//        return data;
//    }

    private void fillFieldsAndLog(InputField field,String value, ExtentTest innerTestNode) throws IOException {
        if (shouldValidateField(field)) {
            forgotPage.writeIntoField(field.name(), value);
            ReportTestLogger.info(innerTestNode, field.name() + ": " + value);
        }
    }
    private boolean shouldValidateField(InputField field) {
        return field != InputField.PhoneNumber &&
                field != InputField.UserName &&
                field != InputField.Password &&
                field != InputField.ConfirmPassword;
    }
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        testTL.remove();
        ReportManager.endTest();
    }
}
