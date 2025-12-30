package utils;

import com.microsoft.playwright.Page;

public class WaitUtils {
    public static void waitForElement(Page page, String selector, int timeoutInSeconds) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setTimeout(timeoutInSeconds * 1000));
        //.setState(WaitUntilState.VISIBLE));
    }
}
