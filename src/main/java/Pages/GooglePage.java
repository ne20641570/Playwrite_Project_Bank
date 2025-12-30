package Pages;

import base.BasePage;
import com.microsoft.playwright.Page;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class GooglePage extends BasePage {
    private final String aboutLink = "//a[contains(@href,'about') and contains(text(),'About')]";
    private final String aboutPageTitleIs = "About Google: Our products, technology and company information - About Google";
    private final String storeLink = "";
    private final String gmailLink = "";
    private final String ImagesLink = "";
    private final String appsIcon = "";
    private final String signInButton = "";
    private final String searchField = "";
    private final String uploadIcon = "";

    public GooglePage(Page page) {
        super(page);
    }

    public void clickAbout() {
        clickElement(aboutLink);
    }
    public String verifyTitle(){
        assertThat(page).hasTitle(Pattern.compile(aboutPageTitleIs));
        return page.title();
    }
    public void aboutTitleVerify(){
        clickAbout();
        verifyTitle();
    }
}
