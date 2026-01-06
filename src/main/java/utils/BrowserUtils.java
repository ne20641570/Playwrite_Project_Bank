package utils;

import com.microsoft.playwright.Page;

import java.util.List;

public class BrowserUtils {

    public static void click(Page page, String selector) {
        page.click(selector);
    }

    public static void type(Page page, String selector, String text) {
        page.fill(selector, text);
    }

    public static String getText(Page page, String selector) {
        return page.textContent(selector);
    }
    public static List<String> multipleText(Page page,String selector){
        return page.locator(selector).allInnerTexts();
    }
    public static void navigate(Page page, String url) {
        page.navigate(url);
    }
    public static void isDisplayed(Page page,String selector){
       page.isVisible(selector);
    }
}
