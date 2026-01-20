package extentReporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.util.concurrent.ConcurrentHashMap;

public class ReportManager {

    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
    private static ExtentReports extent;
    private static ConcurrentHashMap<String, ExtentTest> parentTestMap = new ConcurrentHashMap<>();

    // Initialize Extent once
    public static synchronized void initReport(String reportName) {
        if (extent == null) {
            extent = ReportConfigre.getExtentReports(reportName);
        }
    }

    // Start test per @Test method
    public static void startTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        testThread.set(test);
        parentTestMap.putIfAbsent(testName, test);
    }
    public static ExtentTest getParentTest(String testName) {
        return parentTestMap.get(testName);
    }
    public static ExtentTest getTest() {
//        System.out.println(testThread.get());
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
