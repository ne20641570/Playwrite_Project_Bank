package tests.ui;

import extentReporter.ReportManager;
import com.microsoft.playwright.*;
import config.ConfigReader;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.InputField;
import utils.DBUtils;
import utils.ExcelUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;

import static utils.FileUtils.createFolderIfNotExists;
import static utils.FileUtils.deleteOldFolders;

public class BaseTest {

    // Thread-safe Playwright objects
    private static ThreadLocal<Playwright> playwrightTL = new ThreadLocal<>();
    private static ThreadLocal<Browser> browserTL = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> contextTL = new ThreadLocal<>();
    private static ThreadLocal<Page> pageTL = new ThreadLocal<>();
    private int invocation;
    private int resultStatus;
    private String methodName;
    private String reportPath;
    private String screenshotPath;
    private String videoPath;

    protected Page page;

    // ------------------- Class-level setup -------------------
    @Parameters({"browser"})
    @BeforeClass(alwaysRun = true)
    public void setUp(@Optional("chromium") String browserName, ITestContext context,Method method,ITestResult result) throws IOException, SQLException {

        // ✅ Create necessary folders once
        String base = System.getProperty("user.dir")+File.separator ;
        createFolderIfNotExists(base +  ConfigReader.getProperty("report.path"));
        createFolderIfNotExists(base + ConfigReader.getProperty("screenshot.path"));
        createFolderIfNotExists(base +  ConfigReader.getProperty("video.path"));
        deleteOldFolders(base + ConfigReader.getProperty("screenshot.path"), ConfigReader.getProperty("folder.delete.afterdays"));
        deleteOldFolders(base + ConfigReader.getProperty("report.path"), ConfigReader.getProperty("folder.delete.afterdays"));
        deleteOldFolders(base + ConfigReader.getProperty("video.path"), ConfigReader.getProperty("folder.delete.afterdays"));
        DBUtils.deleteOldUsers();
        // ✅ Initialize ExtentReports per suite
        String suiteName = context.getSuite().getName();
//        String testName = context.getName();
        ReportManager.initReport(suiteName);

        boolean headless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));



        Playwright playwright = Playwright.create();
        playwrightTL.set(playwright);

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(headless);

        Browser browser;
        switch (browserName.toLowerCase()) {
            case "firefox":
                browser = playwright.firefox().launch(options);
                break;
            case "webkit":
                browser = playwright.webkit().launch(options);
                break;
            default:
                browser = playwright.chromium().launch(options);
        }Browser.NewContextOptions contextOptions = null;
        browserTL.set(browser);
        if (videoPath != null){
         contextOptions=
                new Browser.NewContextOptions().setRecordVideoDir(Paths.get(videoPath))
                        .setRecordVideoSize(1280, 720);;
        }
        BrowserContext contextPW = browser.newContext(contextOptions);
        contextTL.set(contextPW);

        Page p = contextPW.newPage();
        pageTL.set(p);
        this.page = p;

    }

    // ------------------- Method-level teardown -------------------
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            methodName = result.getMethod().getMethodName();
            invocation  = result.getMethod().getCurrentInvocationCount();
            resultStatus = result.getStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @AfterClass
    public void tearDown(){
        try{
            Page page = pageTL.get();
            Path originalVideo = page.video().path();
            // Save video ONLY on 2nd retry FAILURE
            if ( resultStatus== ITestResult.FAILURE && invocation == Integer.parseInt(ConfigReader.getProperty("video.atRetry"))) {
                Path renamedVideo = Paths.get(originalVideo.getParent().toString(),methodName + ".webm");

                Files.move(originalVideo, renamedVideo,
                        StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Video saved: " + renamedVideo);

            } else {
                // ❌ Delete video for all other cases
                Files.deleteIfExists(originalVideo);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (pageTL.get() != null) pageTL.get().close();
            if (contextTL.get() != null) contextTL.get().close();
            if (browserTL.get() != null) browserTL.get().close();
            if (playwrightTL.get() != null) playwrightTL.get().close();

            pageTL.remove();
            contextTL.remove();
            browserTL.remove();
            playwrightTL.remove();
        }

    }

    // ------------------- Suite-level cleanup -------------------
    @AfterSuite(alwaysRun = true)
    public void endSuite() throws Exception {
//        ExcelUtils.fileOutPut();
        ExcelUtils.closeExcel();
        ReportManager.flush();
    }

}
