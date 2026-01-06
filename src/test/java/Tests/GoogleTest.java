package Tests;

import ExtentReporter.ReportManager;
import ExtentReporter.ReportTestLogger;
import Listeners.RetryAnalyzer;
import Pages.GooglePage;
import com.aventstack.extentreports.ExtentTest;
import config.ConfigReader;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class GoogleTest extends BaseTest {
    @BeforeClass
    public void setupBeforeClass() {
        ReportManager.startTest("Google TS_01 Google Home Page Valiation","GoogleTest");
    }

    @Test()
    public void homePageFunctionalities() throws IOException {
        ExtentTest testnode = ReportManager.getTest().createNode("TC01 About Link Verify");
        GooglePage googlePage = new GooglePage(page);
        try {
            googlePage.navigateTo(ConfigReader.getProperty("google.url"));

            ReportTestLogger.info(testnode, "Navigated to login page");

            RetryAnalyzer.retryStep(page,() -> googlePage.aboutTitleVerify(),testnode);
            ReportTestLogger.pass(testnode, "About link is clicked and verified the title");
            ReportTestLogger.info(testnode,page.title());
            ReportTestLogger.addScreenshotBase(testnode, googlePage.captureScreenshotBase("Google AboutLink"));

        }catch (Exception e  ){
            ReportTestLogger.fail(testnode,"Retrying the step");
//            ReportTestLogger.addScreenshotPath(testnode, googlePage.captureScreenshot("AboutFail"));
            ReportTestLogger.addScreenshotBase(testnode, googlePage.captureScreenshotBase("AboutFail"));
            throw e;
        }catch (AssertionError e  ){
            ReportTestLogger.fail(testnode,"Retrying the step");
//            ReportTestLogger.addScreenshotPath(testnode, googlePage.captureScreenshot("AboutLinkAssertionFail"));
            ReportTestLogger.addScreenshotBase(testnode, googlePage.captureScreenshotBase("AboutLinkAssertionFail"));

        }
    }
}
