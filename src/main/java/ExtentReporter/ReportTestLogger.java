package ExtentReporter;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;

import java.io.IOException;

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
    public static ExtentTest createinnerNode(ExtentTest extentTest, String message){
       return extentTest.createNode(message);
    }


    public static void addScreenshotPath(ExtentTest extentTest,String path) {
        extentTest.addScreenCaptureFromPath(path);
    }
    public static void addScreenshotBase(ExtentTest extentTest,String name) {
        extentTest.addScreenCaptureFromBase64String(name);
    }

    public static ExtentTest createNode(ExtentTest parentTest, String nodeName) {
        return parentTest.createNode(nodeName);
    }

    public static void info(ExtentTest testNode, String message) {
        testNode.info(message);
    }

    public static void addScreenshot(ExtentTest testNode, String base64Image) {
        testNode.addScreenCaptureFromBase64String(base64Image);
    }


}
