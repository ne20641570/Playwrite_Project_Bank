package Pages;

import base.BasePage;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import config.ConfigReader;

import java.util.Collections;
import java.util.List;

public class LoginPage extends BasePage {



    private final String welcomePageTitle = "ParaBank | Accounts Overview";
    private final String errorPageTitle="ParaBank | Error";

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

    private void verifyInputField(String inputField) {
        switch (inputField.toLowerCase()) {
            case "username":
                verifyElement(userNameField);
                verifyElement(username);
                break;
            case "password":
                verifyElement(passwordField);
                verifyElement(password);
                break;
            case "forgot":
                verifyElement(forgotPasswordLink);
                break;
            case "register":
                verifyElement(registerUserLink);
                break;
            case "services":
                verifyElement(servicesTab);
                break;
            case "latestnews":
                verifyElement(latestNews);
                break;
            case "readmoreservice":
                verifyElement(readMoreService);
                break;
            case "readmorenews":
                verifyElement(readMoreNews);
                break;
            default:
                break;
        }
    }
    private List<String> verifyAndReadField(String inputField){
        List<String> fieldTexts;
        switch (inputField.toLowerCase()) {
            case "servicetabname":
                verifyElement(serviceTabName);
                fieldTexts=getMultipleElementText(serviceTabName);
                break;
            case "servicetablinks":
                verifyElement(serviceTabLinks);
                fieldTexts=getMultipleElementText(serviceTabLinks);
                break;
            case "latestnewstabnames":
                verifyElement(latestNewsTabName);
                fieldTexts=getMultipleElementText(latestNewsTabName);
                break;
            case "latestnewstablinks":
                verifyElement(latestNewsTabLinks);
                fieldTexts=getMultipleElementText(latestNewsTabLinks);
                break;
            default:
                fieldTexts= Collections.singletonList(" ");
                break;
        }

        return fieldTexts;
    }

    private String returnElements(String inputField){
        switch (inputField.toLowerCase()) {
            case "servicetabname":
                return serviceTabName;
            case "servicetablinks":
                return serviceTabLinks;
            case "latestnewstabnames":
                return latestNewsTabName;
            case "latestnewstablinks":
                return serviceTabLinks;
            case "services":
                return servicesTab;
            case "latestnews":
                return latestNews;
            default:
                return null;
        }
    }

    public void enterUsername(String usernameIs) {
        typeText(username, usernameIs);
    }

    public void enterPassword(String passwordIs) {
        typeText(password,passwordIs);
    }

    public void clickLogin() {
        clickElement(loginButton);
    }

    public int countTabs(String fieldToCount){
       return page.locator(returnElements(fieldToCount)).count();
    }

    public String verifyPageMessage(String field){
        switch (field.toLowerCase()){
            case "welcometitle":
                verifyElement(welcomeTitle);
                return getElementText(welcomeTitle);
//                break;
            case "accounttitle":
                verifyElement(accountTable);
                return getElementText(accountTable);
            case "errortitle":
                verifyElement(errorTitle);
                return getElementText(errorTitle);
            case "errormessage":
                verifyElement(errorMessage);
                return getElementText(errorMessage);
//                break;
            default:
                return "null";
        }
    }

    public void verifyFields(String field){
        verifyInputField(field);
    }
    public void verifyAndReadFieldText(ExtentTest test,String field){
        List<String> fieldTexts = verifyAndReadField(field);
        for(int i=0;i<fieldTexts.size();i++){
            test.info(fieldTexts.get(i));
        }
    }

    public void login(String userNameIs,String passwordIs) {
        enterUsername(userNameIs);
        //ExtentLogger.pass(testnode,"Username entered as: "+username);
        enterPassword(passwordIs);
        //ExtentLogger.pass("Password entered as: "+password);
        clickLogin();
    }



}
