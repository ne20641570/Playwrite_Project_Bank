package Pages;

import base.BasePage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

public class LoginPage extends BasePage {
    private final String usernameInput = "//input[@name=\"username\"]";
    private final String usernameIs = "Admin";
    private final String passwordInput = "//input[@name=\"password\"]";
    private final String passwordIs = "admin123";
    private final String loginButton = "//button[@type=\"submit\"]";

    public LoginPage(Page page) {
        super(page);
    }

    public String userName(){
        return usernameIs;
    }
    public String password(){
        return passwordIs;
    }

    public void enterUsername(String username) {
        typeText(usernameInput, username);
    }

    public void enterPassword(String password) {
        typeText(passwordInput, password);
    }

    public void clickLogin() {
        clickElement(loginButton);
    }

    public void login() {
        page.waitForLoadState(LoadState.LOAD);
        enterUsername(usernameIs);
        //ExtentLogger.pass(testnode,"Username entered as: "+username);
        enterPassword(passwordIs);
        //ExtentLogger.pass("Password entered as: "+password);
        clickLogin();
        page.waitForLoadState(LoadState.LOAD);
    }
}
