package Tests;

import ExtentReporter.ReportManager;
import base.PlaywrightFactory;
import com.microsoft.playwright.Page;
import config.ConfigReader;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ExcelUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import static utils.FileUtils.createFolderIfNotExists;
import static utils.FileUtils.deleteOldFolders;

public class BaseTest {
    protected Page page;

    @BeforeSuite
    public void beforeSuite() {
        String base = System.getProperty("user.dir");

        createFolderIfNotExists(base+File.separator+ConfigReader.getProperty("report.path"));
        createFolderIfNotExists(base+File.separator+ConfigReader.getProperty("screenshot.path"));
        deleteOldFolders(base+File.separator+ConfigReader.getProperty("screenshot.path"),ConfigReader.getProperty("folder.delete.afterdays"));
        deleteOldFolders(base+File.separator+ConfigReader.getProperty("report.path"),ConfigReader.getProperty("folder.delete.afterdays"));
    }
    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser, Method method) throws IOException {

        boolean headless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));
        String filePath =ExcelUtils.readExeclPath() + ConfigReader.getProperty("excel.file.demo");
        ExcelUtils.openExcel(filePath);
        PlaywrightFactory.initBrowser(browser, headless);
        page = PlaywrightFactory.getPage();

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException {
        page.close();
        ExcelUtils.closeExcel();   // Close Excel
        PlaywrightFactory.closeBrowser();
    }

    @AfterSuite
    public void endSuite() {
        ReportManager.endTest();

    }
}
