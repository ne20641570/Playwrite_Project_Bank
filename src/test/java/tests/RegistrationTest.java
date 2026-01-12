package tests;

import extentReporter.ReportManager;
import extentReporter.ReportTestLogger;
import listeners.RetryAnalyzer;
import pages.RegisterPage;
import pages.RegistrationFormData;
import pages.InputField;
import com.aventstack.extentreports.ExtentTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;
import utils.ExcelUtils;
import listeners.Scenario;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

@Scenario("TS_01 Registration Page Functionality Validation")
public class RegistrationTest extends BaseTest {
    private static ThreadLocal<ExtentTest> testTL = new ThreadLocal<>();
    private RegisterPage registerPage;
    private String sheetName = "Register";
    private String filePath;
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
    @BeforeMethod
    public void setupBeforeMethod(Method method,ITestContext context) throws IOException {
        registerPage = new RegisterPage(page);

        // Read the @Test description dynamically
        String testDescription = method.isAnnotationPresent(Test.class)
                ? method.getAnnotation(Test.class).description()
                : method.getName();

        // Create report nodes
        if (testTL.get() == null) {
            throw new RuntimeException("ReportManager.getTest() is null in @BeforeMethod");
        }
        testnode = testTL.get().createNode(testDescription);
//        testnode = ReportManager.getTest().createNode(testDescription);
        innerTestNode = ReportTestLogger.createinnerNode(testnode, "Navigating to Register Page");

        // Navigate and log
        registerPage.navigateTo(ConfigReader.getProperty("bank.url"));
        ReportTestLogger.info(innerTestNode, "Navigated to URL");

        RetryAnalyzer.retryStep(() -> registerPage.clickFunctionField("registerLink"), innerTestNode);
        ReportTestLogger.pass(innerTestNode, "Register link is clicked");

        // Log page messages
        ReportTestLogger.info(innerTestNode, "Register Header: " + registerPage.getMessageOnPage("RegisterTitle"));
        ReportTestLogger.info(innerTestNode, "Register Message: " + registerPage.getMessageOnPage("RegisterMessage"));

        // Assert page title
        Assert.assertEquals(registerPage.getRegisterPageTitle(), page.title(), "Page title verification failed!");
    }

    // ------------------- Test 1: Field Presence -------------------
    @Test(priority = 1, description = "TC01 Registration Page FieldName Verification")
    public void registrationPageValidation(Method method) throws IOException {
        String testDescription = method.getAnnotation(Test.class).description(); // Dynamic description
        innerTestNode = ReportTestLogger.createinnerNode(testnode, "Verifying the Fields");

        try {
            for (InputField field : InputField.values()) {
                registerPage.verifyFieldPresence(field.name());
                registerPage.verifyInputFieldName(field.name());
                ReportTestLogger.info(innerTestNode, field.name() + " Field is Displayed");
            }

            ReportTestLogger.pass(innerTestNode, "All Fields are Displayed");
            ReportTestLogger.addScreenshotBase(innerTestNode, registerPage.captureScreenshotBase("Registration_All_Field_Test"));
            ReportTestLogger.pass(testnode, "Field validation has been successfully completed.");

        } catch (Exception | AssertionError e) {
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
            ReportTestLogger.addScreenshotBase(testnode, registerPage.captureScreenshotBase("Registration_Test_Fail"));
            throw e;
        }
    }

    // ------------------- Test 2: Error Messages -------------------
    @Test(priority = 2, description = "TC02 Registration Page Error message Verification")
    public void errorMessageValidation(Method method) throws Exception {
        String testDescription = method.getAnnotation(Test.class).description();
        innerTestNode = ReportTestLogger.createinnerNode(testnode, "Verifying the Error Message for Fields");

        try {
            for (InputField field : InputField.values()) {
                if(field!=InputField.PhoneNumber) {
                    String errorMessage = registerPage.verifyErrorMessage(field.name());
                    ReportTestLogger.info(innerTestNode, field.name() + " Error Message is Displayed");
                    ReportTestLogger.pass(innerTestNode, errorMessage);
                }
            }

            ReportTestLogger.pass(innerTestNode, "All Error Messages are Displayed");
            ReportTestLogger.addScreenshotBase(innerTestNode, registerPage.captureScreenshotBase("Registration_All_Error_Message_Test"));
            ReportTestLogger.pass(testnode, "Error message validation completed successfully.");

        } catch (Exception | AssertionError e) {
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
            ReportTestLogger.addScreenshotBase(testnode, registerPage.captureScreenshotBase("Registration_Test_Fail"));
            throw e;
        }
    }

    // ------------------- Test 3: Confirm Password Error -------------------
    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "TC03 Registration Page Confirm Password Error message Verification")
    public void passwordErrorMessageValidation(Method method) throws Exception {
        innerTestNode = ReportTestLogger.createinnerNode(testnode, "Writing input to form.");
        filePath = ExcelUtils.readExeclPath() + ConfigReader.getProperty("excel.file.bank");
        ExcelUtils.openExcel(filePath);

        try {
            // Get test data
            Map<InputField, String> formData = RegistrationFormData.getFormData("", "");

            // Fill all fields except ConfirmPassword
            for (Map.Entry<InputField, String> entry : formData.entrySet()) {
                if (entry.getKey() != InputField.ConfirmPassword) {
                    fillFieldAndLogForRegister(entry.getKey(), entry.getValue(), innerTestNode);
                }
            }

            // Enter mismatched ConfirmPassword
            String passwordIs = formData.get(InputField.Password);
            registerPage.writeIntoField(InputField.Password.name(), passwordIs);
            ReportTestLogger.info(innerTestNode, "Password: ********");

            String confirmPasswordIs = passwordIs + "123";
            registerPage.writeIntoField(InputField.ConfirmPassword.name(), confirmPasswordIs);
            ReportTestLogger.info(innerTestNode, "ConfirmPassword: ********123");

            // Validate error
            String errorMessage = registerPage.verifyErrorMessage(InputField.ConfirmPassword.name());
            ReportTestLogger.info(innerTestNode, "Confirm Password Error Message is Displayed ");
            ReportTestLogger.pass(innerTestNode, errorMessage);

            ReportTestLogger.addScreenshotBase(innerTestNode, registerPage.captureScreenshotBase("Registration_Confirm_Error_Message_Test"));
            ReportTestLogger.pass(testnode, "Password mismatch validation completed successfully.");
            ExcelUtils.closeExcel();
        } catch (Exception | AssertionError e) {
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
            ReportTestLogger.addScreenshotBase(testnode, registerPage.captureScreenshotBase("Registration_Test_Fail"));
            throw e;
        }
    }

    // ------------------- Test 4: Successful Registration -------------------
    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "TC04 Registration with Valid data")
    public void registrationWithValidData(Method method) throws IOException {
        String testDescription = method.getAnnotation(Test.class).description();
        innerTestNode = ReportTestLogger.createinnerNode(testnode, "Entered required details in the registration form");
        filePath = ExcelUtils.readExeclPath() + ConfigReader.getProperty("excel.file.bank");
        ExcelUtils.openExcel(filePath);

        try {
            // Generate test data
            Map<InputField, String> fieldsToFill = RegistrationFormData.getFormData("", "");

            // Fill all fields
            for (Map.Entry<InputField, String> entry : fieldsToFill.entrySet()) {
                fillFieldAndLogForRegister(entry.getKey(), entry.getValue(), innerTestNode);
            }

            ReportTestLogger.addScreenshotBase(innerTestNode, registerPage.captureScreenshotBase("Registration_Test"));
            RetryAnalyzer.retryStep(() -> registerPage.clickFunctionField("RegisterButton"), testnode);

            // Verify Welcome Page
            innerTestNodes = ReportTestLogger.createinnerNode(testnode, "Verifying Welcome Page | Test: " + testDescription);
            innerTestNode = ReportTestLogger.createinnerNode(innerTestNodes, "Welcome Page Validation");

            String expectedTitle = registerPage.getMessageOnPage("WelcomeTitle");
            String userName = fieldsToFill.get(InputField.UserName);
            Assert.assertTrue(expectedTitle.toLowerCase().contains(userName.toLowerCase()));

            ReportTestLogger.info(innerTestNode, "Title: " + expectedTitle);
            ReportTestLogger.info(innerTestNode, "Welcome Message: " + registerPage.getMessageOnPage("WelcomeSuccessMessage"));
            ReportTestLogger.pass(innerTestNode, "Title is matched");

            ReportTestLogger.pass(innerTestNodes, "Welcome Page validation completed successfully");
            ReportTestLogger.addScreenshotBase(innerTestNodes, registerPage.captureScreenshotBase("Registration_Welcome_Page"));

            ReportTestLogger.pass(testnode, "User registration form completed successfully");
            ExcelUtils.fileOutPut();

        } catch (Exception | AssertionError e) {
            ReportTestLogger.info(innerTestNode, e.getMessage());
            ReportTestLogger.fail(innerTestNode, "Failed");
            ReportTestLogger.addScreenshotBase(testnode, registerPage.captureScreenshotBase("Registration_Test_Fail"));
            throw e;
        }
    }

    // ------------------- Utility Method -------------------
    private void fillFieldAndLogForRegister(InputField field, String value, ExtentTest innerTestNode) throws IOException {
        registerPage.writeIntoField(field.name(), value);
        // Fill the form
        if(field==InputField.Password || field == InputField.ConfirmPassword) {
           value = "**********";
        }
        ExcelUtils.writeResult(sheetName, field.name(), value); // Write to Excel
        ReportTestLogger.info(innerTestNode, field.name() + ": " + value); // Log it
    }

    @AfterClass
    public void tearDown() {
        testTL.remove();
        ReportManager.endTest();
    }
}
