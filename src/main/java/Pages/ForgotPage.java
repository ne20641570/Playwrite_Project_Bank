package Pages;

import base.BasePage;
import com.microsoft.playwright.Page;
import utils.InputField;

public class ForgotPage extends BasePage {

    private final String forgotLink = "//a[contains(@href,'look') and contains(text(),'Forgot')]";

    private final String fieldInput="/ancestor::tr/child::td/b";
    private final String errorMessage ="/ancestor::tr/child::td/child::span";

    //page Titles
    private final String forgotPageTitle = "ParaBank | Customer Lookup";
    private final String forgotSuccessPageTitle = "ParaBank | Customer Lookup";
    private final String forgotErrorPageTitle = "ParaBank | Error";

    //forgot Page
    private final String forgotTitle="//h1[@class='title']";
    private final String forgotMessage = "//p[contains(text(),'validate')]";

    //input field
    private final String firstName = "//input[@id='firstName']";
    private final String lastName = "//input[@id='lastName']";
    private final String address = "//input[@id='address.street']";
    private final String city = "//input[@id='address.city']";
    private final String state = "//input[@id='address.state']";
    private final String zipCode = "//input[@id='address.zipCode']";
    private final String ssnNumber = "//input[@id='ssn']";

    //submit button
    private final String findMyInfoButton="//input[@type='submit' and contains(@value,'Find')]";

    //input Field Names
    private final String firstNameField = firstName+fieldInput;
    private final String lastNameField = lastName+fieldInput;
    private final String addressField = address+fieldInput;
    private final String cityField = city+fieldInput;
    private final String stateField = state+fieldInput;
    private final String zipCodeField = zipCode+fieldInput;
    private final String ssnNumberField = ssnNumber+fieldInput;

    //input Field Error
    private final String firstNameError = firstName+errorMessage;
    private final String lastNameError = lastName+errorMessage;
    private final String addressError = address+errorMessage;
    private final String cityError = city+errorMessage;
    private final String stateError = state+errorMessage;
    private final String zipCodeError = zipCode+errorMessage;
    private final String ssnNumberErorr = ssnNumber+errorMessage;

    //Error Page
    private final String errorInfoTitle = "//h1[@class='title']";
    private final String errorInfoMessage = "//p[@class='error']";

    //forgotOpenPage
    private final String successPageTitle = "//h1[@class='title']";
    private final String successPageMessage = "//p[contains(text(),'located')]";
    private final String successPageUserName="//h1[@class='title']/following::b[contains(text(),'name')]";
    private final String successPagePassword="//h1[@class='title']/following::b[contains(text(),'word')]";


    RegisterPage registerPage=new RegisterPage(page);

    public ForgotPage(Page page) {
        super(page);
    }
    public String getForgotPageTitle() {
        return forgotPageTitle;
    }

    public String getForgotErrorPageTitle() {
        return forgotErrorPageTitle;
    }

    private String returnNonFormElements(String input){
        switch (input.toLowerCase()) {
            case "forgotlink":
                return forgotLink;
            case "forgottitle":
                return forgotTitle;
            case "forgotmessage":
                return forgotMessage;
            case "errortitle":
                return forgotTitle;
            case "errormessage":
                return forgotMessage;
            case "findmyinfobutton":
                return findMyInfoButton;
            case "successtitle":
                return successPageTitle;
            case "successmessage":
                return successPageMessage;
            case "successusername":
                return successPageUserName;
            case "successpassword":
                return successPagePassword;
            default:
                throw new IllegalArgumentException("Unknown field: " + input);
        }
    }

    private String returningFieldElements(String input,String type){
        String suffix = "";
        if (type == "Error") {
            suffix = errorMessage;
        } else if (type == "FieldName") suffix = fieldInput;
        else if (type == "Field") suffix = "";

        switch (input.toLowerCase()) {
            case "firstname":
                return firstName + suffix;
            case "lastname":
                return lastName + suffix;
            case "address":
                return address + suffix;
            case "city":
                return city + suffix;
            case "state":
                return state + suffix;
            case "zipcode":
                return zipCode + suffix;
            case "ssn":
                return ssnNumber + suffix;
            default:
                throw new IllegalArgumentException("Unknown field: " + input);
        }
    }


    public void verifyInputField(String field) {
        verifyElement(returningFieldElements(field, "Field"));
    }
    public void writeIntoField(String field,String input){
        typeText(returningFieldElements(field,"Field"),input);
    }
    public void verifyInputFieldName(String field) {
        verifyElement(returningFieldElements(field, "FieldName"));
    }
    public String getErrorMessageField(String field) {
        verifyElement( returningFieldElements(field, "Error"));
        return getElementText(returningFieldElements(field,"Error"));
    }

    public void clickFunctionField(String inputField) {
        clickElement(returnNonFormElements(inputField));
    }
    public String getMessageOnPage(String inputField){
        return getElementText(returnNonFormElements(inputField));
    }

}
