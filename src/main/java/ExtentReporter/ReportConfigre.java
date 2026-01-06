package ExtentReporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import config.ConfigReader;

import java.io.File;
import java.nio.file.Paths;

import static utils.FileUtils.createFolderIfNotExists;

public class ReportConfigre {

    private static ExtentReports extent;

    public synchronized static ExtentReports getExtentReports(String reportIs) {
        if (extent == null) {
            String baseIs = System.getProperty("user.dir")+File.separator+ConfigReader.getProperty("report.path")+File.separator;
            ExtentSparkReporter spark = new ExtentSparkReporter( createFolderIfNotExists(baseIs)+File.separator+reportIs+"_ExtentReport.html");
            spark.config().setReportName("PlayWright Demo Report");
            spark.config().setDocumentTitle("Demo Playwright Automation");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            String applicationURL = "<a href=\"" + ConfigReader.getProperty("base.url")
                    + "\" target=\"_blank\">PlayWright_Demo_Application</a>";
            extent.setSystemInfo("Application", applicationURL);

            extent.setSystemInfo("OS", System.getProperties().getProperty("os.name"));
            extent.setSystemInfo("Browser", ConfigReader.getProperty("browser"));
            extent.setSystemInfo("Framework", "Playwright + TestNG ");
            extent.setSystemInfo("Author", "Neelanjana K");
            String filePath = Paths.get(System.getProperty("user.dir")+"/reports/videos").toAbsolutePath()
                    .toString();
//            String recordedVideoFilePath = "<a href=\"" + filePath
//                    + "\" target=\"_blank\">Video</a>";
//            extent.setSystemInfo("Execution Recorded Video", recordedVideoFilePath);
        }
        return extent;
    }}
