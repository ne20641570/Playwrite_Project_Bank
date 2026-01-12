package utils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.MouseButton;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.microsoft.playwright.options.WaitUntilState;

import java.nio.file.Paths;
public class UiActions {
    private Page page;
    private int defaultTimeout = 5000;

    public void UIActions(Page page) {
        this.page = page;
    }

    /* ===================== PRIVATE HELPERS ===================== */

    private Locator getLocator(String selector) {
        return page.locator(selector);
    }

    private void ensureVisible(String selector) {
        getLocator(selector).waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(defaultTimeout)
        );
    }

    private void ensureEnabled(String selector) {
        getLocator(selector).waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.ATTACHED)
                        .setTimeout(defaultTimeout)
        );
        if (!page.isEnabled(selector)) {
            throw new RuntimeException("Element is disabled: " + selector);
        }
    }

    /* ===================== NAVIGATION ===================== */

    public void navigateTo(String url) {
        // ExtentLogger.info("Navigating to: " + url);
        page.navigate(url, new Page.NavigateOptions()
                .setWaitUntil(WaitUntilState.LOAD));
    }

    /* ===================== CLICK ACTIONS ===================== */

    public void click(String selector) {
        ensureVisible(selector);
        ensureEnabled(selector);
        //ExtentLogger.info("Clicking: " + selector);
        getLocator(selector).click();
    }

    public void forceClick(String selector) {
        ensureVisible(selector);
        getLocator(selector).click(new Locator.ClickOptions().setForce(true));
    }

    public void doubleClick(String selector) {
        ensureVisible(selector);
        getLocator(selector).dblclick();
    }

    public void rightClick(String selector) {
        ensureVisible(selector);
        getLocator(selector).click(
                new Locator.ClickOptions().setButton(MouseButton.RIGHT));
    }

    /* ===================== TEXT INPUT ===================== */

    public void type(String selector, String value) {
        ensureVisible(selector);
        ensureEnabled(selector);
        //  ExtentLogger.info("Typing text");
        getLocator(selector).fill(value);
    }

    public void clearAndType(String selector, String value) {
        ensureVisible(selector);
        getLocator(selector).fill("");
        getLocator(selector).type(value);
    }

    /* ===================== DROPDOWNS ===================== */

    public void selectByValue(String selector, String value) {
        ensureVisible(selector);
        page.selectOption(selector, value);
    }

    public void selectByLabel(String selector, String label) {
        ensureVisible(selector);
        page.selectOption(selector,
                new SelectOption().setLabel(label));
    }

    public void selectByIndex(String selector, int index) {
        ensureVisible(selector);
        page.selectOption(selector,
                new SelectOption().setIndex(index));
    }

    /* ===================== CHECKBOX / RADIO ===================== */

    public void check(String selector) {
        ensureVisible(selector);
        page.check(selector);
    }

    public void uncheck(String selector) {
        ensureVisible(selector);
        page.uncheck(selector);
    }

    /* ===================== GETTERS ===================== */

    public String getText(String selector) {
        ensureVisible(selector);
        return getLocator(selector).textContent().trim();
    }

    public String getAttribute(String selector, String attribute) {
        return page.getAttribute(selector, attribute);
    }

    public boolean isVisible(String selector) {
        return page.isVisible(selector);
    }

    public boolean isPresent(String selector) {
        return getLocator(selector).count() > 0;
    }

    /* ===================== WAIT UTILITIES ===================== */

    public void waitForHidden(String selector) {
        getLocator(selector).waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.HIDDEN)
                        .setTimeout(defaultTimeout));
    }

    public void waitForTimeout(int millis) {
        page.waitForTimeout(millis);
    }

    /* ===================== MOUSE ===================== */

    public void hover(String selector) {
        ensureVisible(selector);
        getLocator(selector).hover();
    }

    public void dragAndDrop(String source, String target) {
        ensureVisible(source);
        ensureVisible(target);
        page.dragAndDrop(source, target);
    }

    /* ===================== KEYBOARD ===================== */

    public void pressKey(String key) {
        page.keyboard().press(key);
    }

    public void pressKey(String selector, String key) {
        ensureVisible(selector);
        getLocator(selector).click();
        page.keyboard().press(key);
    }

    /* ===================== FILE UPLOAD ===================== */

    public void uploadFile(String selector, String filePath) {
        ensureVisible(selector);
        page.setInputFiles(selector, Paths.get(filePath));
    }

    /* ===================== SCREENSHOT ===================== */

    public void takeScreenshot(String name) {
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("screenshots/" + name + ".png"))
                .setFullPage(true));
    }

    /* ===================== SCROLL ===================== */

    public void scrollToElement(String selector) {
        ensureVisible(selector);
        getLocator(selector).scrollIntoViewIfNeeded();
    }

    public void scrollToBottom() {
        page.evaluate("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void scrollToTop() {
        page.evaluate("window.scrollTo(0, 0)");
    }

    /* ===================== JS ===================== */

    public Object executeJS(String script) {
        return page.evaluate(script);
    }
}
