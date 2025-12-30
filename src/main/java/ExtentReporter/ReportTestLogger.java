package ExtentReporter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.microsoft.playwright.Page;
import utils.AttachmentUtils;

public class ReportTestLogger {
    protected static Page page;
    public ReportTestLogger(Page page) {
        this.page=page;
    }
    public static void pass(ExtentTest extentTest, String message) {
        extentTest.pass(message);
    }

    public static void fail(ExtentTest extentTest,String message) {
        extentTest.fail(message);
    }
    public static void createNode(ExtentTest extentTest, String message){
        extentTest.createNode(message);
    }

    public static void info(ExtentTest extentTest,String message) {
        extentTest.info(message);
    }

    public static void addScreenshotPath(ExtentTest extentTest,String path) {
        extentTest.addScreenCaptureFromPath(path);
    }
    public static void addScreenshotBase(ExtentTest extentTest,String name) {
        extentTest.addScreenCaptureFromBase64String(name);
    }
}
