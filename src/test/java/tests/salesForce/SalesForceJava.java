package tests.salesForce;


import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.regex.Pattern;

public class SalesForceJava {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("file:///Users/ne20641570/Library/CloudStorage/OneDrive-Wipro/Framework" +
                    "/Playwrite_Project_Bank/src/test/java/Resources/htmlPractice.html");
//            Locator firstName=page.getByRole(AriaRole.TEXTBOX,new Page.GetByRoleOptions().setName("First Name"));
            Locator firstName=page.locator("first-name-input >> input[id=\"first_name_input_field\"]");
            firstName.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            firstName.fill("First Name");

            Locator submitBtn = page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName(Pattern
                    .compile("Sign me")));
            submitBtn.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            page.locator("submit-button >> button").evaluate("el => el.removeAttribute('disabled')");
            Thread.sleep(3000);
            submitBtn.click();

            page.locator("submit-button >> button").evaluate("el => el.setAttribute('disabled','')");
            Thread.sleep(5000);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
