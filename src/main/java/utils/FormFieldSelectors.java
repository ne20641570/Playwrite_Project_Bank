package utils;


public class FormFieldSelectors {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String city;
    private final String state;
    private final String zipCode;
    private final String phoneNumber;
    private final String ssnNumber;
    private final String userName;
    private final String password;
    private final String confirmPassword;

    private final String fieldInput;
    private final String errorMessage;

    public FormFieldSelectors(String firstName, String lastName, String address, String city,
                              String state, String zipCode, String phoneNumber, String ssnNumber,
                              String userName, String password, String confirmPassword,
                              String fieldInput, String errorMessage) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.ssnNumber = ssnNumber;
        this.userName = userName;
        this.password = password;
        this.confirmPassword = confirmPassword;

        this.fieldInput = fieldInput;
        this.errorMessage = errorMessage;
    }

    // Single getter for both input and error selectors
    public String getSelector(InputField field, String fieldType) {

        String suffix = "";
        if (fieldType.equals("Error")) suffix = errorMessage;
        else if (fieldType.equals("FieldName") ) suffix = fieldInput;
        else if (fieldType.equals("Field")) suffix = "";

        switch (field) {
            case FIRST_NAME: return firstName + suffix;
            case LAST_NAME: return lastName + suffix;
            case ADDRESS: return address + suffix;
            case CITY: return city + suffix;
            case STATE: return state + suffix;
            case ZIP_CODE: return zipCode + suffix;
            case PHONE_NUMBER: return phoneNumber + suffix;
            case SSN: return ssnNumber + suffix;
            case USERNAME: return userName + suffix;
            case PASSWORD: return password + suffix;
            case CONFIRM_PASSWORD: return confirmPassword + suffix;
            default:
                throw new IllegalArgumentException("Unknown field: " + field);
        }
    }
}
