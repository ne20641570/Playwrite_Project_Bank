package listeners;

import base.PlaywrightFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListeners implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        //ExtentTestManager.startTest(result.getMethod().getMethodName());
        //ExtentLogger.info("Test Started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // ExtentLogger.pass("Test Passed: " + result.getMethod().getMethodName());
        PlaywrightFactory.saveVideo(result.getTestName());
    }

    @Override
    public void onTestFailure(ITestResult result) {

        //ExtentLogger.fail("Test Failed: " + result.getMethod().getMethodName());

        try {
            String testName = result.getMethod().getMethodName();
            String videoPath = PlaywrightFactory.saveVideo(testName);

            if (videoPath != null) {
                // ExtentLogger.fail("Video Recorded: " + videoPath);
                // ExtentTestManager.getTest().addScreenCaptureFromPath(videoPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onTestSkipped(ITestResult result) {
        //ExtentLogger.info("Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {}

    @Override
    public void onFinish(ITestContext context) {
        //ExtentTestManager.endTest();
    }
}
