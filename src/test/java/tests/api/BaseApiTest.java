package tests.api;

import api.client.ApiClient;
import com.aventstack.extentreports.ExtentTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.ConfigReader;
import extentReporter.ReportManager;
import io.restassured.response.Response;
import listeners.Scenario;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import static utils.FileUtils.createFolderIfNotExists;

public class BaseApiTest {

    protected String baseUrl;

    public static ThreadLocal<ExtentTest> testTL = new ThreadLocal<>();
    String testDescription;
    int priority;
    ExtentTest testnode;
    Gson gson;
    @BeforeTest
    public void setupApi(ITestContext context) {
        baseUrl = ConfigReader.getProperty("api.base.url");
        ApiClient.getRequestContext(baseUrl);
        String suiteName = context.getSuite().getName();
        String base = System.getProperty("user.dir")+ File.separator ;
        createFolderIfNotExists(base +  ConfigReader.getProperty("report.path"));
        ReportManager.initReport(suiteName);
    }

    @BeforeClass
    public void setupClassLevel(ITestContext context){
        Scenario scenario = this.getClass().getAnnotation(Scenario.class);
        if (scenario != null) {
            //            testName = context.getCurrentXmlTest().getName();
            ReportManager.startTest(context.getName()+"-->"+scenario.value());
            testTL.set(ReportManager.getTest());
//            testTL.set(ReportManager.getParentTest());
        }
    }
    @BeforeMethod
    public void setupBeforeMethod(Method method, ITestContext context) throws IOException {
        Scenario scenario = this.getClass().getAnnotation(Scenario.class);
        baseUrl = ConfigReader.getProperty("api.base.url");

        testDescription = method.isAnnotationPresent(Test.class)
                ? method.getAnnotation(Test.class).description()
                : method.getName();
        priority=method.getAnnotation(Test.class).priority();
        gson = new GsonBuilder().setPrettyPrinting().create();
        ExtentTest parentTest = ReportManager.getParentTest(context.getName()+"-->"+scenario.value());
        if (parentTest == null) {
//            throw new RuntimeException("ReportManager.getTest() is null in @BeforeMethod");
            ReportManager.startTest(context.getName() + " --> " + testDescription);
            parentTest = ReportManager.getTest();
        }
        testnode =parentTest.createNode("TC" + priority + "_" + testDescription);
    }

    @AfterClass
    public void classTearDown(){
        testTL.remove();
        ReportManager.endTest();
    }

    @AfterTest
    public void tearDownApi() {
        ApiClient.close();
        ReportManager.flush();
    }

    public String intoJsonForReport(Response response){
        try {
            // Convert to pretty JSON
            ObjectMapper mapper = new ObjectMapper();
            Object json = mapper.readValue(response.asString(), Object.class);
            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
            String prettyJson = writer.writeValueAsString(json);
            return "<pre>" + prettyJson + "</pre>";
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
