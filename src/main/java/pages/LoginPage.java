package pages;

import base.BasePage;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;

import java.util.List;

public class LoginPage extends BasePage {



    private final String welcomePageTitle = "ParaBank | Accounts Overview";
    private final String errorPageTitle="ParaBank | Error";
    private final String loginPageTitle="ParaBank | Welcome | Online Banking";

    public String getErrorPageTitle() {
        return errorPageTitle;
    }

    public String getWelcomePageTitle() {
        return welcomePageTitle;
    }


    private final String loginTitle = "//h2";

    private final String username = "//input[@name='username']";
    private final String password = "//input[@name='password']";
    private final String loginButton = "//input[@type='submit']";
    private final String forgotPasswordLink = "//a[contains(text(),'Forgot')]";
    private final String registerUserLink = "//a[contains(text(),'Register')]";

    //Field Name
    private final String userNameField = "//input[@name='username']/ancestor::form/p[1]/b";
    private final String passwordField = "//input[@name='password']/ancestor::form/p[2]/b";

    //Error Side
    private final String errorTitle="//h1[@class='title']";
    private final String errorMessage="//p[@class='error']";


    //welcome Page
    private final String welcomeTitle = "//h1[@class='title' and contains(text(),'Acc')]";
    private final String accountTable = "//table[@id='accountTable']";

    //other field
    private final String linksFollowedTabName="/child::li[contains(@class,'caption')]";
    private final String linksFollowedTabLinks="/child::li/a";

    private final String servicesTab = "//ul[contains(@class,'service')]";
    private final String serviceTabLinks = servicesTab+linksFollowedTabLinks;
    private final String serviceTabName = servicesTab+linksFollowedTabName;

    private final String latestNews = "//ul[contains(@class,'events')]";
    private final String latestNewsTabLinks = latestNews+linksFollowedTabLinks;
    private final String latestNewsTabName = latestNews+linksFollowedTabName;

    private final String readMoreService = "//a[contains(@href,'service') and contains(text(),'Read')]";
    private final String readMoreNews = "//a[contains(@href,'news') and contains(text(),'Read')]";

    public LoginPage(Page page) {
        super(page);
    }
    private String getFieldSelector(String input){
        switch (input.toLowerCase()) {
            case "username":
                return username;
            case "password":
                return password;
            case "usernamefield":
                return userNameField;
            case "passwordfield":
                return passwordField;
            case "forgot":
                return forgotPasswordLink;
            case "register":
                return registerUserLink;
            case "servicetabname":
                return serviceTabName;
            case "servicetablinks":
                return serviceTabLinks;
            case "latestnewstabnames":
                return latestNewsTabName;
            case "latestnewstablinks":
                return latestNewsTabName;
            case "loginbutton":
                return loginButton;
            case "services":
                return servicesTab;
            case "latestnews":
                return latestNews;
            case "readmoreservice":
                return readMoreService;
            case "readmorenews":
                return readMoreNews;
                case "errortitle":
                return errorTitle;
            case "errormessage":
                return errorMessage;
            default:
                throw new IllegalArgumentException("Unknown field: " + input);
        }
    }
    private void enterUsername(String usernameIs) {
        typeText(username, usernameIs);
    }
    private void enterPassword(String passwordIs) {
        typeText(password,passwordIs);
    }

    public String verifyPageMessage(String inputField){
        return getElementText(getFieldSelector(inputField));

    }
    public void verifyField(String inputField){
        verifyElementDisplayed(getFieldSelector(inputField));
    }
    public void clickFunctionField(String inputField) {
        clickElement(getFieldSelector(inputField));
    }
    public int countTabs(String fieldToCount){
       return page.locator(getFieldSelector(fieldToCount)).count();
    }

    public void verifyAndReadFieldText(ExtentTest test,String field){
        List<String> fieldTexts = getMultipleElementText(getFieldSelector(field));
        for(int i=0;i<fieldTexts.size();i++){
            test.info(fieldTexts.get(i));
        }
    }
    public void login(String userNameIs,String passwordIs) {
        enterUsername(userNameIs);
        //ExtentLogger.pass(testnode,"Username entered as: "+username);
        enterPassword(passwordIs);
        //ExtentLogger.pass("Password entered as: "+password);
        clickFunctionField("loginbutton");
    }


    public String getLoginPageTitle() {
        return loginPageTitle;
    }
}
