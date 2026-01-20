package listeners;

import base.PlaywrightFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

public class TestListeners implements ITestListener {


    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = getTestDescription(result);
        System.out.println("[START] Test: " + testName + " | Description: " + description);

        // ExtentTestManager.startTest(testName); // Example for ExtentReports
        // ExtentLogger.info("Test Started: " + testName);
    }

    /**
     * Invoked each time a test succeeds
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("[PASS] Test: " + testName);

        PlaywrightFactory.saveVideo(testName);
        // ExtentLogger.pass("Test Passed: " + testName);
    }

    /**
     * Invoked each time a test fails
     */
    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("[FAIL] Test: " + testName);

        String description = getTestDescription(result);
        System.out.println("Description: " + description);

        try {
            String videoPath = PlaywrightFactory.saveVideo(testName);
            if (videoPath != null) {
                System.out.println("Video saved at: " + videoPath);
                // Optionally attach to reports
                // ExtentTestManager.getTest().addScreenCaptureFromPath(videoPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Optionally log exception
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            System.out.println("Exception: " + throwable.getMessage());
        }
    }

    /**
     * Invoked each time a test is skipped
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("[SKIPPED] Test: " + testName);

        String description = getTestDescription(result);
        System.out.println("Description: " + description);
        // ExtentLogger.info("Test Skipped: " + testName);
    }

    /**
     * Invoked if a test fails but is within success percentage
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("[FAIL WITHIN SUCCESS %] Test: " + testName);
        // ExtentLogger.warning("Test failed but within success percentage: " + testName);
    }

    /**
     * Invoked before any test methods are invoked in the <test> tag of testng-ui.xml
     */
    @Override
    public void onStart(ITestContext context) {
        System.out.println("[START TEST CONTEXT] " + context.getName());
        // context.getSuite() can be used to get suite info
    }

    /**
     * Invoked after all the test methods in the <test> tag of testng-ui.xml have run
     */
    @Override
    public void onFinish(ITestContext context) {
        System.out.println("[FINISH TEST CONTEXT] " + context.getName());
        // ExtentTestManager.endTest(); // Example for ExtentReports
    }

    /**
     * Utility method to get @Test description safely
     */
    private String getTestDescription(ITestResult result) {
        Test annotation = result.getMethod()
                .getConstructorOrMethod()
                .getMethod()
                .getAnnotation(Test.class);
        if (annotation != null) {
            return annotation.description();
        } else {
            return "";
        }
    }
}
