package Pages;

import config.ConfigReader;
import utils.TestDataGenerator;
import java.util.LinkedHashMap;
import java.util.Map;

public class RegistrationFormData {

    /**
     * Returns a map of registration fields and their values.
     * Uses InputField enum as keys.
     */
    public static Map<InputField, String> getFormData(String firstName, String lastName) {
        Map<InputField, String> formData = new LinkedHashMap<>();

        formData.put(InputField.FirstName, (firstName == null || firstName.isEmpty()) ? TestDataGenerator.randomFirstName() : firstName);
        formData.put(InputField.LastName, (lastName == null || lastName.isEmpty()) ? TestDataGenerator.randomLastName() : lastName);
        formData.put(InputField.Address, TestDataGenerator.randomStreet());
        formData.put(InputField.City, TestDataGenerator.randomCity());
        formData.put(InputField.State, TestDataGenerator.randomState());
        formData.put(InputField.ZipCode, TestDataGenerator.randomZipCode());
        formData.put(InputField.SSN, TestDataGenerator.randomSSN());
        formData.put(InputField.UserName, TestDataGenerator.randomUsernameWithFullName(firstName,lastName));
        formData.put(InputField.Password, ConfigReader.getProperty("bank.password"));
        formData.put(InputField.ConfirmPassword, ConfigReader.getProperty("bank.password"));

        return formData;
    }
}
