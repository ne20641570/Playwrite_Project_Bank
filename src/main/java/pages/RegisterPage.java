package pages;

import base.BasePage;
import com.microsoft.playwright.Page;

public class RegisterPage extends BasePage {

    private final String fieldInput="/ancestor::tr/child::td/b";
    private final String errorMessage ="/ancestor::tr/child::td/child::span";
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
    }

    private String getNonFieldSelector(String input){
        switch (input.toLowerCase()) {
            case "welcometitle":
                return welcomeTitle;
            case "welcomesuccessmessage":
                return welcomeSuccessMessage;
            case "registertitle":
                return registerTitle;
            case "registermessage":
                return registerMessage;
            case "registerlink":
                return registerLink;
            case "registerbutton":
                return registerButton;
            default:
                throw new IllegalArgumentException("Unknown field: " + input);
        }
    }
    private String getFieldElements(String input,String type){
        String suffix = "";

        if (type == "Error") suffix = errorMessage;
        else if (type == "FieldName") suffix = fieldInput;
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
            case "phonenumber":
                return phoneNumber + suffix;
            case "username":
                return userName + suffix;
            case "password":
                return password + suffix;
            case "confirmpassword":
                return confirmPassword + suffix;
            default:
                throw new IllegalArgumentException("Unknown field: " + input);
        }
    }
    public void verifyFieldPresence(String field) {
        verifyElementDisplayed(getFieldElements(field,"Field"));
    }
    public void writeIntoField(String field,String input){
        typeText(getFieldElements(field,"Field"),input);
    }
    public void verifyInputFieldName(String field) {
        verifyElementDisplayed(getFieldElements(field, "FieldName"));
    }
    public String verifyErrorMessage(String field) {
        clickFunctionField("RegisterButton");
        verifyElementDisplayed(getFieldElements(field, "Error"));
        return getElementText(getFieldElements(field,"Error"));
    }
    public void clickFunctionField(String inputField) {
        clickElement(getNonFieldSelector(inputField));
    }
    public String getMessageOnPage(String inputField){
        return getElementText(getNonFieldSelector(inputField));
    }





}
