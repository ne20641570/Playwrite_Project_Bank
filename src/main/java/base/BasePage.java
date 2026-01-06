package base;

import com.microsoft.playwright.Page;
import utils.AttachmentUtils;
import utils.BrowserUtils;
import utils.WaitUtils;

import java.io.IOException;
import java.util.List;

public class BasePage {
    protected Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    public void clickElement(String selector) {
        WaitUtils.waitForElement(page, selector, 30);
        BrowserUtils.click(page, selector);
    }

    public void typeText(String selector, String text) {
        WaitUtils.waitForElement(page, selector, 30);
        BrowserUtils.type(page, selector, text);
    }

    public String getElementText(String selector) {
        WaitUtils.waitForElement(page, selector, 30);
        return BrowserUtils.getText(page, selector);
    }
    public List<String> getMultipleElementText(String selector) {
        WaitUtils.waitForElement(page, selector, 30);
        return BrowserUtils.multipleText(page, selector);
    }

    public void navigateTo(String url) {
        BrowserUtils.navigate(page, url);
    }

    public String captureScreenshot(String name) {
        return AttachmentUtils.screenshotAttach(page, name);
    }
    public String captureScreenshotBase(String name) throws IOException {
        return AttachmentUtils.screenshotTake(page, name);
    }

    public void selectElement(String selector, String text) {
        WaitUtils.waitForElement(page, selector, 30);
        page.locator(selector).selectOption(text);
    }
    public void verifyElement(String selector) {
        WaitUtils.waitForElement(page, selector, 30);
        BrowserUtils.isDisplayed(page, selector);
    }

}
