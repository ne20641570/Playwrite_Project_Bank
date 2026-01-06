package Pages;

import base.BasePage;
import com.aventstack.extentreports.model.Test;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.apache.poi.ss.formula.atp.Switch;
import utils.FormFieldSelectors;
import utils.InputField;

public class RegisterPage extends BasePage {

    private final String fieldInput="/ancestor::tr/child::td/b";
    private final String errorMessage ="/ancestor::tr/child::td/child::span";
    private final FormFieldSelectors selectors;
    //page Titles
    private String registerPageTitle = "ParaBank | Register for Free Online Account Access";
    private String welcomePageTitle = "ParaBank | Customer Created";

    //register button
    private final String registerButton = "//input[@value='Register']";
    private final String registerLink = "//a[contains(text(),'Register')]";

    //Register Page Header
    private final String registerTitle="//h1[@class='title']";
    private final String registerMessage="//p[contains(text(),'sign-up')]";

    //input fields
    private final String firstName = "//input[@id='customer.firstName']";
    private final String lastName = "//input[@id='customer.lastName']";
    private final String address = "//input[@id='customer.address.street']";
    private final String city = "//input[@id='customer.address.city']";
    private final String state = "//input[@id='customer.address.state']";
    private final String zipCode = "//input[@id='customer.address.zipCode']";
    private final String phoneNumber = "//input[@id='customer.phoneNumber']";
    private final String ssnNumber = "//input[@id='customer.ssn']";
    private final String userName = "//input[@id='customer.username']";
    private final String password = "//input[@id='customer.password']";
    private final String confirmPassword = "//input[@id='repeatedPassword']";

    //Welcome Page
    private final String welcomeTitle = "//h1[@class='title']";
    private final String welcomeSuccessMessage = "//p[contains(text(),'success')]";

    public String getPasswordError() {
        return passwordError;
    }

    public String getConfirmPasswordError() {
        return confirmPasswordError;
    }

    public String getUserNameError() {
        return userNameError;
    }

    public String getSsnNumberErorr() {
        return ssnNumberErorr;
    }

    public String getPhoneNumberError() {
        return phoneNumberError;
    }

    public String getZipCodeError() {
        return zipCodeError;
    }

    public String getStateError() {
        return stateError;
    }

    public String getCityError() {
        return cityError;
    }

    public String getAddressError() {
        return addressError;
    }

    public String getFirstNameError() {
        return firstNameError;
    }

    public String getLastNameError() {
        return lastNameError;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getSsnNumber() {
        return ssnNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getWelcomePageTitle() {
        return welcomePageTitle;
    }

    public String getRegisterPageTitle() {
        return registerPageTitle;
    }

    //input Filed Names
    private final String firstNameField = firstName+fieldInput;
    private final String lastNameField = lastName+fieldInput;
    private final String addressField = address+fieldInput;
    private final String cityField = city+fieldInput;
    private final String stateField = state+fieldInput;
    private final String zipCodeField = zipCode+fieldInput;
    private final String phoneNumberField = phoneNumber+fieldInput;
    private final String ssnNumberField = ssnNumber+fieldInput;
    private final String userNameField = userName+fieldInput;
    private final String passwordField = password+fieldInput;
    private final String confirmPasswordField = confirmPassword+fieldInput;

    //input Filed Names
    private final String firstNameError = firstName+errorMessage;
    private final String lastNameError = lastName+errorMessage;
    private final String addressError = address+errorMessage;
    private final String cityError = city+errorMessage;
    private final String stateError = state+errorMessage;
    private final String zipCodeError = zipCode+errorMessage;
    private final String phoneNumberError = phoneNumber+errorMessage;
    private final String ssnNumberErorr = ssnNumber+errorMessage;
    private final String userNameError = userName+errorMessage;
    private final String passwordError = password+errorMessage;
    private final String confirmPasswordError = confirmPassword+errorMessage;



    public RegisterPage(Page page) {
        super(page);
        selectors = new FormFieldSelectors(
                "firstName", "lastName", "address", "city", "state", "zipCode",
                "phoneNumber", "ssnNumber", "userName", "password", "confirmPassword",
                "FieldInputSuffix", "ErrorMessageSuffix"
        );
    }



    private String getSelector(String field, String isError) {
        String suffix = "";
        if (isError == "Error") {
            suffix = errorMessage;
        } else if (isError == "FieldName") suffix = fieldInput;
        else if (isError == "Field") suffix = "";

        switch (field.toLowerCase()) {
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
            case "phonenumber":
                return phoneNumber + suffix;
            case "ssn":
                return ssnNumber + suffix;
            case "username":
                return userName + suffix;
            case "password":
                return password + suffix;
            case "confirmpassword":
                return confirmPassword + suffix;
            default:
                throw new IllegalArgumentException("Unknown field: " + field);
        }
    }

    private void getInputField(InputField field) {
        verifyInputFields(selectors.getSelector(field, "Field"));
    }
    private void getInputFieldName(InputField field) {
        verifyInputFields(selectors.getSelector(field, "FieldName"));
    }

    private String getErrorField(InputField field) {
        verifyInputFields( selectors.getSelector(field, "Error"));
        return getElementText(selectors.getSelector(field,"Error"));
    }

    public void verifyInputFields(String inputField){
        verifyElement(inputField);
    }

    private void verifyInputField(String inputField) {
        switch (inputField.toLowerCase()) {
            case "firstname":
                verifyElement(firstNameField);
                verifyElement(firstName);
                break;
            case "lastname":
                verifyElement(lastNameField);
                verifyElement(lastName);
                break;
            case "address":
                verifyElement(addressField);
                verifyElement(address);
                break;
            case "city":
                verifyElement(cityField);
                verifyElement(city);
                break;
            case "state":
                verifyElement(stateField);
                verifyElement(state);
                break;
            case "zipcode":
                verifyElement(zipCodeField);
                verifyElement(zipCode);
                break;
            case "phonenumber":
                verifyElement(phoneNumberField);
                verifyElement(phoneNumber);
                break;
            case "ssn":
                verifyElement(ssnNumberField);
                verifyElement(ssnNumber);
                break;
            case "username":
                verifyElement(userNameField);
                verifyElement(userName);
                break;
            case "password":
                verifyElement(passwordField);
                verifyElement(password);
                break;
            case "confirmpassword":
                verifyElement(confirmPasswordField);
                verifyElement(confirmPassword);
                break;
            default:
                break;
        }
    }

    private String verifyInputErrorMessage(String inputField) {
        switch (inputField.toLowerCase()) {
            case "firstname":
                verifyElement(firstNameError);
                return firstNameError;
//                break;
            case "lastname":
                verifyElement(lastNameError);
                return lastNameError;
//                break;
            case "address":
                verifyElement(addressError);
                return addressError;
//                break;
            case "city":
                verifyElement(cityError);
                return cityError;
//                break;
            case "state":
                verifyElement(stateError);
                return stateError;
//                break;
            case "zipcode":
                verifyElement(zipCodeError);
                return zipCodeError;
//                break;
            case "phonenumber":
                verifyElement(phoneNumberError);
                return phoneNumberError;
//                break;
            case "ssn":
                verifyElement(ssnNumberErorr);
                return ssnNumberErorr;
//                break;
            case "username":
                verifyElement(userNameError);
                return userNameError;
//                break;
            case "password":
                verifyElement(passwordError);
                return passwordError;
//                break;
            case "confirmpassword":
                verifyElement(confirmPasswordError);
                return confirmPasswordError;
//                break;
            default:

                return null;
//                break;
        }
    }
    private void clickingFunction(String inputField) {
        switch (inputField.toLowerCase()) {
            case "firstname":
                clickElement(firstName);
                break;
            case "lastname":
                clickElement(lastName);
                break;
            case "address":
                clickElement(address);
                break;
            case "city":
                clickElement(city);
                break;
            case "state":
                clickElement(state);
                break;
            case "zipcode":
                clickElement(zipCode);
                break;
            case "phonenumber":
                clickElement(phoneNumber);
                break;
            case "ssn":
                clickElement(ssnNumber);
                break;
            case "username":
                clickElement(userName);
                break;
            case "password":
                clickElement(password);
                break;
            case "confirmpassword":
                clickElement(confirmPassword);
                break;
            case "registerbutton":
                clickElement(registerButton);
                break;
            case "registerlink":
                clickElement(registerLink);
                break;
            default:

                break;
        }
    }
private void writeIntoInputField(String inputField,String inputMessage) {
        switch (inputField.toLowerCase()) {
            case "firstname":
                typeText(firstName,inputMessage);
                break;
            case "lastname":
                typeText(lastName,inputMessage);
                break;
            case "address":
                typeText(address,inputMessage);
                break;
            case "city":
                typeText(city,inputMessage);
                break;
            case "state":
                typeText(state,inputMessage);
                break;
            case "zipcode":
                typeText(zipCode,inputMessage);
                break;
            case "phonenumber":
                typeText(phoneNumber,inputMessage);
                break;
            case "ssn":
                typeText(ssnNumber,inputMessage);
                break;
            case "username":
                typeText(userName,inputMessage);
                break;
            case "password":
                typeText(password,inputMessage);
                break;
            case "confirmpassword":
                typeText(confirmPassword,inputMessage);
                break;
            default:

                break;
        }
    }

    public void verifyFieldPresence(String field){
        verifyInputField(field);
    }
    public void clickFunctionOnField(String inputField) {
        clickingFunction(inputField);
    }
    public String verifyErrorMessage(String field){
        clickElement(registerButton);
        return getElementText(verifyInputErrorMessage(field));
    }
    public void fillForm(String field,String inputMessage){
        writeIntoInputField(field,inputMessage);
    }

    public void verifyFieldPresences(String locator,String type) {
        verifyElement(getLocator(locator,type));
    }

    private String getLocator(String inputField, String fieldType){
        return getSelector(inputField,fieldType);
    }

    public String verifyWelComePage(String field){
        switch (field.toLowerCase()){
            case "welcometitle":
                verifyElement(welcomeTitle);
                return getElementText(welcomeTitle);
//                break;
            case "welcomesuccessmessage":
                verifyElement(welcomeSuccessMessage);
                return getElementText(welcomeSuccessMessage);
            case "registertitle":
                verifyElement(registerTitle);
                return getElementText(registerTitle);
            case "registermessage":
                verifyElement(registerMessage);
                return getElementText(registerMessage);
//                break;
            default:
                return "null";
        }
    }


}
