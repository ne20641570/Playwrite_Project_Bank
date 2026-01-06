package ExtentReporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ReportManager {
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
    private static ExtentReports extent; ;

    public static synchronized void startTest(String testName,String reportIs) {
        extent=ReportConfigre.getExtentReports(reportIs);
        ExtentTest test = extent.createTest(testName);
        testThread.set(test);
    }

    public static synchronized ExtentTest getTest() {
        return testThread.get();
    }

    public static synchronized void endTest() {
        extent.flush();
        testThread.remove();
    }
}
