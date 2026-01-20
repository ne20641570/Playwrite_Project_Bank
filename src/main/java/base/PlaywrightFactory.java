package base;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.microsoft.playwright.*;
import config.ConfigReader;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static utils.FileUtils.createFolderIfNotExists;

public class PlaywrightFactory {
    private static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static ThreadLocal<Page> page = new ThreadLocal<>();
    private static final ThreadLocal<Video> videoThread = new ThreadLocal<>();

    public static void initBrowser(String browserName,boolean headless) {

        playwright.set(Playwright.create());
        Browser.NewContextOptions options = new Browser.NewContextOptions();

        switch (browserName.toLowerCase()) {
            case "firefox":
                //firefox browser launch and set with headed and added into browser
                browser.set(playwright.get().firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless)));
                break;
            case "webkit":
                //safari browser launch and set with headed and added into browser
                browser.set(playwright.get().webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless)));
                break;
            default:
                //chromium browser launch and set with headed and added into browser
                browser.set(playwright.get().chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless))); //
        }

        // Video Recording enabled
//        boolean recordVideo = Boolean.parseBoolean(ConfigReader.getProperty("video.record"));
//        String videoDir = ConfigReader.getProperty("video.dir");
//        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();
//        if (recordVideo&retryCount == 1) {
//            contextOptions.setRecordVideoDir(Paths.get(videoDir));
//            contextOptions.setRecordVideoSize(1280, 720);
//        }
        context.set(browser.get().newContext());
        page.set(context.get().newPage());

//        if (recordVideo&retryCount == 2) {
//            videoThread.set(page.get().video());
//        }


    }

    public static Page getPage() {
        // set default timeout for page
//        page.get().setDefaultTimeout(15_000);
//        page.get().setDefaultNavigationTimeout(20_000);
        return page.get(); //returning value of opened tab
    }

    public static void closeBrowser() {
        context.get().close();
        browser.get().close();
        playwright.get().close();
    }

    public static String saveVideo(String testName) {
        try {
            Video video = videoThread.get();
            if (video == null) return null;

            Path tempPath = video.path();
            String videoDir = System.getProperty("user.dir")+File.separator+ConfigReader.getProperty("video.path")+File.separator;
            Path finalPath = Paths.get(videoDir + createFolderIfNotExists(videoDir)+File.separator+ ".webm");

            // Ensure video exists before moving
            video.saveAs(finalPath);
            return finalPath.toString();

        } catch (Exception e) {
            return null;
        }
    }
}
