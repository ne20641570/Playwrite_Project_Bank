package ExtentReporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ReportManager {

    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
    private static ExtentReports extent;

    // Initialize Extent once
    public static synchronized void initReport(String reportName) {
        if (extent == null) {
            extent = ReportConfigre.getExtentReports(reportName);
        }
    }

    // Start test per @Test method
    public static void startTest(String testName) {
        if (extent == null) {
            throw new RuntimeException("ExtentReports not initialized. Call initReport() first.");
        }
        ExtentTest test = extent.createTest(testName);
        testThread.set(test);
    }

    public static ExtentTest getTest() {

        return testThread.get();
    }

    public static void endTest() {
        testThread.remove();
    }

    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
