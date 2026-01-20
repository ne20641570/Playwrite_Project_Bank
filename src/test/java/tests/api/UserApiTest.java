package tests.api;

import api.data.factory.UserDataFactory;
import api.models.user.User;
import api.services.UserService;
import com.aventstack.extentreports.ExtentTest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.ConfigReader;
import io.restassured.response.Response;
import listeners.Scenario;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;


@Scenario("User Api Test")
@Test(groups = "UserAPITest")
public class UserApiTest extends BaseApiTest {


    ExtentTest innerTestNode;

    @Test(priority = 1, description = "Create User")
    public void createUsersTest() {
         try {

            innerTestNode = testnode.createNode("Creating Random User");
            User user = UserDataFactory.createRandomUser();
            innerTestNode.info(gson.toJson(user));

            Response response = new UserService(baseUrl)
                    .createUsers(List.of(user));

            innerTestNode = testnode.createNode("Create User Response");
            innerTestNode.info(intoJsonForReport(response));

            Assert.assertEquals(response.statusCode(), 200);

            innerTestNode.pass("User created successfully");
        } catch (Exception | AssertionError e) {
            innerTestNode=innerTestNode.createNode("Error");
            innerTestNode.info(e.getMessage());
            innerTestNode.fail("Create User failed");
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 2, description = "Get User by Username")
    public void getUserByUsernameTest() {
         try {
            innerTestNode = testnode.createNode("Creating Random User");
            UserService service = new UserService(baseUrl);
            User user = UserDataFactory.createRandomUser();
            innerTestNode.info(gson.toJson(user));

            service.createUsers(List.of(user));

            Response response = service.getUserByUsername(user.username);

            innerTestNode = testnode.createNode("Get User Response");
            innerTestNode.info(intoJsonForReport(response));

            Assert.assertEquals(response.statusCode(), 200);
            Assert.assertEquals(response.jsonPath().getString("username"), user.username);

            innerTestNode.pass("Get User by Username successfully completed");
        } catch (Exception | AssertionError e) {
            innerTestNode=innerTestNode.createNode("Error");

            innerTestNode.info(e.getMessage());
            innerTestNode.fail("Get User by Username failed");
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 3, description = "Update User")
    public void updateUserTest() {
         try {
            innerTestNode = testnode.createNode("Creating Random User");
            UserService service = new UserService(baseUrl);
            User user = UserDataFactory.createRandomUser();

            innerTestNode.info(user.toString());
            service.createUsers(List.of(user));

            user.firstName = "UpdatedName";

            innerTestNode = testnode.createNode("Updating User");
            innerTestNode.info("Updated firstName to: " + user.firstName);

            Response response = service.updateUser(user.username, user);

            innerTestNode = testnode.createNode("Update User Response");
            innerTestNode.info(intoJsonForReport(response));

            Assert.assertEquals(response.statusCode(), 200);
            innerTestNode.pass("Update User successfully completed");
        } catch (Exception | AssertionError e) {
            innerTestNode=innerTestNode.createNode("Error");

            innerTestNode.info(e.getMessage());
            innerTestNode.fail("Update User failed");
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 4, description = "Delete User")
    public void deleteUserTest() {
         try {
            innerTestNode = testnode.createNode("Creating Random User");
            UserService service = new UserService(baseUrl);
            User user = UserDataFactory.createRandomUser();

            innerTestNode.info(user.toString());
            service.createUsers(List.of(user));

            Response response = service.deleteUser(user.username);

            innerTestNode = testnode.createNode("Delete User Response");
            innerTestNode.info(intoJsonForReport(response));

            Assert.assertEquals(response.statusCode(), 200);
            innerTestNode.pass("Delete User successfully completed");
        } catch (Exception | AssertionError e) {
            innerTestNode=innerTestNode.createNode("Error");

            innerTestNode.info(e.getMessage());
            innerTestNode.fail("Delete User failed");
            Assert.fail(e.getMessage());
        }
    }
}
