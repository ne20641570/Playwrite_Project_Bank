package extentReporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import config.ConfigReader;

import java.io.File;

import static utils.FileUtils.createFolderIfNotExists;

public class ReportConfigre {

    public static ExtentReports getExtentReports(String reportName) {

        String basePath = System.getProperty("user.dir")
                + File.separator + ConfigReader.getProperty("report.path");
        String reportFile = createFolderIfNotExists(basePath)
                + File.separator + reportName + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportFile);
        spark.config().setReportName("Playwright Report");
        spark.config().setDocumentTitle(reportName);

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(spark);

        extent.setSystemInfo("Framework", "TestNG");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Author", "Neelanjana K");

        return extent;
    }
}
