package utils;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.MouseButton;

import java.util.List;

public class BrowserUtils {

    // ================= ELEMENT ACTIONS =================

    public static void click(Page page, String selector) {
        page.locator(selector).click();
    }

    public static void type(Page page, String selector, String text) {
        page.locator(selector).fill(text);
    }

    public static String getText(Page page, String selector) {
        return page.locator(selector).textContent();
    }

    public static List<String> getMultipleText(Page page, String selector) {
        return page.locator(selector).allInnerTexts();
    }

    public static boolean isDisplayed(Page page, String selector) {
        return page.locator(selector).isVisible();
    }

    // ================= NAVIGATION =================

    public static void navigate(Page page, String url) {
        page.navigate(url, new Page.NavigateOptions().setTimeout(30000));
    }

    public static String getTitle(Page page) {
        return page.title();
    }

    public static String getCurrentUrl(Page page) {
        return page.url();
    }

    // ================= DROPDOWNS =================

    public static void selectByValue(Page page, String selector, String value) {
        page.locator(selector).selectOption(value);
    }

    // ================= MOUSE ACTIONS =================

    public static void hover(Page page, String selector) {
        page.locator(selector).hover();
    }

    public static void doubleClick(Page page, String selector) {
        page.locator(selector).dblclick();
    }

    public static void rightClick(Page page, String selector) {
        page.locator(selector).click(
                new Locator.ClickOptions().setButton(MouseButton.RIGHT)
        );
    }

    // ================= ALERT / DIALOG =================

    public static void acceptDialog(Page page) {
        page.onceDialog(Dialog::accept);
    }

    public static void dismissDialog(Page page) {
        page.onceDialog(Dialog::dismiss);
    }

    // ================= FRAMES =================

    public static void switchToFrame(Page page, String frameSelector) {
        page.frameLocator(frameSelector);
    }

    public static void switchToMainFrame(Page page) {
        page.mainFrame();
    }
}
