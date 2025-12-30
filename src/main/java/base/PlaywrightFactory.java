package base;

import com.microsoft.playwright.*;
import config.ConfigReader;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PlaywrightFactory {
    private static final ThreadLocal<Browser> browserThread = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> contextThread = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageThread = new ThreadLocal<>();
    private static final ThreadLocal<String> videoPathThread = new ThreadLocal<>();
    private static final ThreadLocal<Video> videoThread = new ThreadLocal<>();
    private static Playwright playwright;

    public static void initBrowser(String browserName, boolean headless) {
        if (playwright == null) {
            playwright = Playwright.create();
        }

        Browser browser;
        switch (browserName.toLowerCase()) {
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless));
                break;
            case "webkit":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless));
                break;
            default:
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
                break;
        }
        browserThread.set(browser);

        boolean recordVideo = Boolean.parseBoolean(ConfigReader.getProperty("video.record"));
        String videoDir = ConfigReader.getProperty("video.dir");

        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();

        if (recordVideo) {
            contextOptions.setRecordVideoDir(Paths.get(videoDir));
            contextOptions.setRecordVideoSize(1280, 720);
        }

        BrowserContext context = browser.newContext(contextOptions);
        contextThread.set(context);
        // Default timeout for all actions (click, waitForSelector, etc.)
        context.setDefaultTimeout(15_000);

        // Default timeout for page navigation (page load)
        context.setDefaultNavigationTimeout(15_000);
        Page page = context.newPage();
        pageThread.set(page);

        if (recordVideo) {
            videoThread.set(page.video());
        }
    }

    public static String saveVideo(String testName) {
        try {
            Video video = videoThread.get();
            if (video == null) return null;

            Path tempPath = video.path();
            String videoDir = ConfigReader.getProperty("video.dir");
            Path finalPath = Paths.get(videoDir + testName + ".webm");

            // Ensure video exists before moving
            video.saveAs(finalPath);
            return finalPath.toString();

        } catch (Exception e) {
            return null;
        }
    }


    public static Page getPage() {
        return pageThread.get();
    }

    public static String getVideoPath() {
        return videoPathThread.get();
    }

    public static BrowserContext getContext() {
        return contextThread.get();
    }

    public static Browser getBrowser() {
        return browserThread.get();
    }

    public static void closeBrowser() {
        try {
            if (pageThread.get() != null) pageThread.get().close();
            if (contextThread.get() != null) contextThread.get().close();
            if (browserThread.get() != null) browserThread.get().close();
            if (playwright != null) playwright.close();
        } catch (Exception ignored) {}

        pageThread.remove();
        contextThread.remove();
        browserThread.remove();
        videoPathThread.remove();
    }

}
