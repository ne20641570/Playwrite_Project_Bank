package base;

import com.microsoft.playwright.Page;
import utils.BrowserUtils;
import utils.WaitUtils;
import utils.AttachmentUtils;

import java.io.IOException;
import java.util.List;

public class BasePage {

    protected Page page;
    private static final int TIMEOUT = 30;

    public BasePage(Page page) {
        this.page = page;
    }

    // ================= BASIC ACTIONS =================

    public void clickElement(String selector) {
        WaitUtils.waitForElement(page, selector, TIMEOUT);
        BrowserUtils.click(page, selector);
    }

    public void typeText(String selector, String text) {
        WaitUtils.waitForElement(page, selector, TIMEOUT);
        BrowserUtils.type(page, selector, text);
    }

    public String getElementText(String selector) {
        WaitUtils.waitForElement(page, selector, TIMEOUT);
        return BrowserUtils.getText(page, selector);
    }

    public List<String> getMultipleElementText(String selector) {
        WaitUtils.waitForElement(page, selector, TIMEOUT);
        return BrowserUtils.getMultipleText(page, selector);
    }

    public boolean verifyElementDisplayed(String selector) {
        WaitUtils.waitForElement(page, selector, TIMEOUT);
        return BrowserUtils.isDisplayed(page, selector);
    }

    // ================= NAVIGATION =================

    public void navigateTo(String url) {
        BrowserUtils.navigate(page, url);
    }

    public String getPageTitle() {
        return BrowserUtils.getTitle(page);
    }

    public String getCurrentUrl() {
        return BrowserUtils.getCurrentUrl(page);
    }

    // ================= DROPDOWN =================

    public void selectElement(String selector, String value) {
        WaitUtils.waitForElement(page, selector, TIMEOUT);
        BrowserUtils.selectByValue(page, selector, value);
    }

    // ================= MOUSE ACTIONS =================

    public void hoverOnElement(String selector) {
        WaitUtils.waitForElement(page, selector, TIMEOUT);
        BrowserUtils.hover(page, selector);
    }

    public void doubleClickElement(String selector) {
        WaitUtils.waitForElement(page, selector, TIMEOUT);
        BrowserUtils.doubleClick(page, selector);
    }

    public void rightClickElement(String selector) {
        WaitUtils.waitForElement(page, selector, TIMEOUT);
        BrowserUtils.rightClick(page, selector);
    }

    // ================= ALERTS =================

    public void acceptAlert() {
        BrowserUtils.acceptDialog(page);
    }

    public void dismissAlert() {
        BrowserUtils.dismissDialog(page);
    }

    // ================= FRAMES =================

    public void switchToFrame(String frameSelector) {
        BrowserUtils.switchToFrame(page, frameSelector);
    }

    public void switchToMainFrame() {
        BrowserUtils.switchToMainFrame(page);
    }

    // ================= SCREENSHOTS =================

    public String captureScreenshot(String name) {
        return AttachmentUtils.screenshotAttach(page, name);
    }

    public String captureScreenshotBase(String name) throws IOException {
        return AttachmentUtils.screenshotTake(page, name);
    }
}
