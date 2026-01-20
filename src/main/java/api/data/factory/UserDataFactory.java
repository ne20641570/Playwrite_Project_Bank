package api.data.factory;

import api.data.generators.RandomDataGenerator;
import api.models.user.User;

import java.util.Random;
import java.util.UUID;

public class UserDataFactory {

    public static User createRandomUser() {
        User user = new User();
        String unique = UUID.randomUUID().toString().substring(0, 10);

        user.username = RandomDataGenerator.randomUsername();
        user.firstName = RandomDataGenerator.randomFirstName();
        user.lastName = RandomDataGenerator.randomLastName();
        String forEmail = user.firstName.substring(new Random().nextInt(user.firstName.length()))+user.lastName.substring(new Random().nextInt(user.lastName.length()));
        user.email = user.username+"_"+ forEmail + "@test.com";
        user.password = "password123";
        user.phone = RandomDataGenerator.randomPhone();
        user.userStatus = 1;

        return user;
    }
}
