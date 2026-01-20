package tests.db;

import com.aventstack.extentreports.ExtentTest;
import config.ConfigReader;
import db.client.DbClient;
import extentReporter.ReportManager;
import listeners.Scenario;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.File;

import static utils.FileUtils.createFolderIfNotExists;

public class BaseDbTest {

    protected static ThreadLocal<ExtentTest> testTL = new ThreadLocal<>();

    @BeforeTest
    public void setupDb(ITestContext context) {
        DbClient.getConnection();

        String suiteName = context.getSuite().getName();
        String base = System.getProperty("user.dir") + File.separator;
        createFolderIfNotExists(base + ConfigReader.getProperty("report.path"));

        ReportManager.initReport(suiteName + "_DB");
    }

    @BeforeClass
    public void setupClassLevel(ITestContext context) {
        Scenario scenario = this.getClass().getAnnotation(Scenario.class);
        if (scenario != null) {
            ReportManager.startTest(context.getName() + "-->" + scenario.value());
            testTL.set(ReportManager.getTest());
        }
    }

    @AfterClass
    public void cleanUp() {
        testTL.remove();
    }

    @AfterTest
    public void tearDownDb() {
        DbClient.close();
        ReportManager.flush();
    }
}
