package tests.salesForce;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public class LoginSalesForce {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            page.navigate("file:///Users/ne20641570/Library/CloudStorage/OneDrive-Wipro/Framework/Playwrite_Project_Bank" +
                    "/src/test/java/Resources/loginSalesforce.html");
            Locator userName = page.locator("username-input >> input[id='username_input_field']");
            Locator passWord = page.getByRole(AriaRole.TEXTBOX,new Page.GetByRoleOptions().setName("Password"));
            Locator submitBtn = page.locator("login-button >> button[id='login_button']");

            userName.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            userName.fill("admin");

            passWord.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            passWord.fill("1234");

            submitBtn.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            submitBtn.click();
            Thread.sleep(5000);

            //verifying error message
            Locator loginError = page.locator("//div[@id='global_error']");
            loginError.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            System.out.println("Error Message: "+loginError.innerText());

            // 2
            page.reload();
            userName.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            userName.fill("admin");

            submitBtn.evaluate("el => el.removeAttribute('disabled')");
            Thread.sleep(2000);
            submitBtn.click();

            Locator loginError2 = page.locator("div[id=\"password_input_field_error\"]");
            loginError2.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            System.out.println("Field Error Message: "+loginError2.textContent());
            Thread.sleep(2000);
            submitBtn.evaluate("el => el.setAttribute('disabled','')");

//            Locator passwordError = page.locator("password-input >> div[id='password_input_field_error']");
//            passwordError.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
//            System.out.println("pass error: "+passwordError.innerText());
            page.reload();
            userName.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            userName.fill("admin");

            passWord.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            passWord.fill("123456");
            submitBtn.evaluate("el => el.removeAttribute('disabled')");
            Thread.sleep(2000);
            submitBtn.click();
            submitBtn.evaluate("el => el.setAttribute('disabled','')");

            FrameLocator scsMes = page.frameLocator("iframe[id='success_frame']");
            page.waitForTimeout(2000);

            String message = scsMes
                    .locator("#success_root")
                    .evaluate("el => el.shadowRoot.querySelector('div').textContent")
                    .toString();

            System.out.println("Message: " + message);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
