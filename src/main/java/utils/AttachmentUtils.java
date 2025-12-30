package utils;

import com.microsoft.playwright.Page;
import config.ConfigReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import static utils.FileUtils.createFolderIfNotExists;

public class AttachmentUtils {
    //takescreenshot

    private static String screenshotFolder(){
        String baseIs = System.getProperty("user.dir")+File.separator+ConfigReader.getProperty("screenshot.path")+File.separator;
        return createFolderIfNotExists(baseIs)+File.separator;
    }

    public static String screenshotAttach(Page page, String fileName){
        String screenshotPath = screenshotFolder()+ fileName + ".png";
        // Take screenshot with playwright command
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(false));
        System.out.println("Screenshot saved at: " + screenshotPath);
        return screenshotPath;
    }

    public static String screenshotTake(Page page, String fileName) throws IOException {
        byte[] screenshotBytes = page.screenshot();
        String base64Screenshot = Base64.getEncoder().encodeToString(screenshotBytes);// NOT full page String base64Screenshot = Base64.getEncoder().encodeToString(screenshotBytes);
        byte[] decodedBytes = Base64.getDecoder().decode(base64Screenshot);
        Path filePath = Paths.get( screenshotFolder()+ fileName + ".png");
        Files.write(filePath, decodedBytes);
        return base64Screenshot;
    }

}
