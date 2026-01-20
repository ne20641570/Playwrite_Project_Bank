package tests.db;

import com.aventstack.extentreports.ExtentTest;
import db.dao.UserDao;
import db.model.User;
import listeners.Scenario;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.InputField;
import utils.TestDataGenerator;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static db.client.DbClient.getConnection;

@Scenario("Users Table CRUD Operations")
@Test(groups = "UserDBTest")
public class UserDbTest extends BaseDbTest {

    private String userName;
    private String testDescription;
    private int priority;

    private ExtentTest testnode;
    private ExtentTest innerNode;
    private ExtentTest innerNodes;

    private final UserDao userDao = new UserDao(getConnection());

    @BeforeMethod
    public void setupBeforeMethod(Method method, ITestContext context) throws IOException {
        testDescription = method.getAnnotation(Test.class).description();
        priority = method.getAnnotation(Test.class).priority();
    }

    // ---------------- CREATE TABLE ----------------

    @Test(priority = 1, description = "Create users table")
    public void createTable() {
        testnode = testTL.get().createNode("TC" + priority + "_" + testDescription);
        innerNode = testnode.createNode("DB Table Creation");

        try {
            userDao.createTableIfNotExists();
            innerNode.pass("Users table created or already exists");
        } catch (Exception | AssertionError e) {
            innerNode.fail("Table creation failed: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    // ---------------- CREATE USER ----------------

    @Test(priority = 2, description = "Create user")
    public void createUser() {
        testnode = testTL.get().createNode("TC" + priority + "_" + testDescription);
        try {
            innerNode=testnode.createNode("Creating User");
            User user= new User();
            user.setFirstName(TestDataGenerator.randomFirstName());
            user.setLastName(TestDataGenerator.randomLastName());
            user.setAddress(TestDataGenerator.randomStreet());
            user.setCity(TestDataGenerator.randomCity());
            user.setState(TestDataGenerator.randomState());
            user.setZipCode(TestDataGenerator.randomZipCode());
            user.setPhoneNumber(TestDataGenerator.randomPhoneNumber());
            user.setSsn(TestDataGenerator.randomSSN());
            userName = TestDataGenerator.randomUsername();

            int rows = userDao.insertUser(
                    user.getFirstName(),
                    user.getLastName(),
                    user.getAddress(),
                    user.getCity(),
                    user.getState(),
                    user.getZipCode(),
                    user.getPhoneNumber(),
                    user.getSsn(),
                    userName
            );

            Assert.assertEquals(rows, 1, "Insert failed");
            innerNode.info("User created successfully: " + userName);
            innerNode.info("user details Are: ");
            innerNode.info("ID: " + user.getId() + ", " +
                    "FirstName: " + user.getFirstName() + ", " +
                    "LastName: " + user.getLastName() + ", " +
                    "Address: " + user.getAddress() + ", " +
                    "City: " + user.getCity() + ", " +
                    "State: " + user.getState() + ", " +
                    "ZipCode: " + user.getZipCode() + ", " +
                    "PhoneNumber: " + user.getPhoneNumber() + ", " +
                    "SSN: " + user.getSsn() + ", " +
                    "UserName: " + user.getUserName());
            innerNode.pass("User Creation is successfully completed");
        } catch (Exception | AssertionError e) {
            innerNode.createNode("Error");
            innerNode.fail("User creation failed: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    // ---------------- READ ALL USERS ----------------

    @Test(priority = 3, description = "Read all users")
    public void readAllUsers() {
        testnode = testTL.get().createNode("TC" + priority + "_" + testDescription);
        try {
            innerNode=testnode.createNode("Reading all users");
            List<String> userNames = userDao.getAllUserNames();
            Assert.assertFalse(userNames.isEmpty(), "No users found in DB");

            innerNode.info("Users found: \n" + String.join("\n", userNames));

            userName = userNames.get(
                    ThreadLocalRandom.current().nextInt(userNames.size())
            );

            innerNode.info("Random user selected: " + userName);
            innerNode.pass("Reading all user is successfully completed");
        } catch (Exception | AssertionError e) {
            innerNode.createNode("Error");
            innerNode.fail("Reading users failed: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    // ---------------- READ SINGLE USER BY USER NAME----------------

    @Test(priority = 4, description = "Read user by userName")
    public void readUserByName() {
        testnode = testTL.get().createNode("TC" + priority + "_" + testDescription);

        try {
            innerNode=testnode.createNode("userName Read in TC_3");
            Assert.assertNotNull(userName, "Username is null from previous test");
            innerNode.info("Random user selected: " + userName);
            Assert.assertTrue(userDao.isUserExists(userName));
            innerNode.info(userName+" is exists");

            User user =userDao.getUserByUserName(userName);

            innerNode=testnode.createNode("Reading UserDetails of user: "+userName);
            innerNode.info("ID: " + user.getId() + ", " +
                    "FirstName: " + user.getFirstName() + ", " +
                    "LastName: " + user.getLastName() + ", " +
                    "Address: " + user.getAddress() + ", " +
                    "City: " + user.getCity() + ", " +
                    "State: " + user.getState() + ", " +
                    "ZipCode: " + user.getZipCode() + ", " +
                    "PhoneNumber: " + user.getPhoneNumber() + ", " +
                    "SSN: " + user.getSsn() + ", " +
                    "UserName: " + user.getUserName());
            innerNode.pass("Reading user by User Name is successfully completed");

        } catch (Exception | AssertionError e) {
            innerNode.createNode("Error");
            innerNode.fail("User read failed: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    // ---------------- READ SINGLE USER BY ID----------------

    @Test(priority = 5, description = "Read user by Id")
    public void readUserById() {
        testnode = testTL.get().createNode("TC" + priority + "_" + testDescription);

        try {
            // Step 1: Read all IDs
            innerNode = testnode.createNode("Read All User IDs");
            List<Integer> ids = userDao.getAllUserIds();
            Assert.assertFalse(ids.isEmpty(), "No IDs found in DB");
            StringBuilder allId = new StringBuilder();
            for (Integer id : ids) {
                allId.append(id).append("\n");
            }
            innerNode.info("User IDs in DB: " +  allId);

            // Step 2: Pick a random ID
            Random random = new Random();
            int randomId = ids.get(random.nextInt(ids.size()));
            innerNode.info("Randomly selected user ID: " + randomId);

            // Step 3: Fetch user by ID
            User user = userDao.getUserById(randomId);
            Assert.assertNotNull(user, "User not found for ID: " + randomId);

            // Step 4: Log user details
            innerNode = testnode.createNode("Reading User Details by ID: "+randomId);
            innerNode.info("ID: " + user.getId() + ", " +
                    "FirstName: " + user.getFirstName() + ", " +
                    "LastName: " + user.getLastName() + ", " +
                    "Address: " + user.getAddress() + ", " +
                    "City: " + user.getCity() + ", " +
                    "State: " + user.getState() + ", " +
                    "ZipCode: " + user.getZipCode() + ", " +
                    "PhoneNumber: " + user.getPhoneNumber() + ", " +
                    "SSN: " + user.getSsn() + ", " +
                    "UserName: " + user.getUserName());

            innerNode.pass("Reading user by ID successfully completed");

        } catch (Exception | AssertionError e) {
            innerNode.createNode("Error");
            innerNode.fail("User read by ID failed: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }


    // ---------------- UPDATE USER ----------------

    @Test(priority = 6, description = "Update user address")
    public void updateUserAddress() {
        testnode = testTL.get().createNode("TC" + priority + "_" + testDescription);

        try {
            innerNode=testnode.createNode("Getting Random UserName to Update");
            List<String> userNames = userDao.getAllUserNames();
            userName = userNames.get(
                    ThreadLocalRandom.current().nextInt(userNames.size())
            );
            Assert.assertNotNull(userName, "Username is null");
            innerNode.info("user name to be updated is: "+userName);

            String newStreet = TestDataGenerator.randomStreet();
            String newCity = TestDataGenerator.randomCity();
            String newState = TestDataGenerator.randomState();
            String newZip = TestDataGenerator.randomZipCode();

            innerNode = testnode.createNode("updating address of user: "+userName);
            innerNodes = innerNode.createNode("Data to be updated");
            innerNodes.info(
                    "Street: " + newStreet + "\n" +
                            "City: " + newCity + "\n" +
                            "State: " + newState + "\n" +
                            "ZipCode: " + newZip
            );
            int rows = userDao.updateAddress(newStreet, newCity, newState, newZip, userName);

            User updatedUser = userDao.getUserByUserName(userName);
            Assert.assertNotNull(updatedUser, "Updated user not found");
            innerNode = innerNode.createNode("Updated User Details");
            innerNode.info(
                    "ID: " + updatedUser.getId() + "\n" +
                            "FirstName: " + updatedUser.getFirstName() + "\n" +
                            "LastName: " + updatedUser.getLastName() + "\n" +
                            "Address: " + updatedUser.getAddress() + "\n" +
                            "City: " + updatedUser.getCity() + "\n" +
                            "State: " + updatedUser.getState() + "\n" +
                            "ZipCode: " + updatedUser.getZipCode() + "\n" +
                            "PhoneNumber: " + updatedUser.getPhoneNumber() + "\n" +
                            "SSN: " + updatedUser.getSsn() + "\n" +
                            "UserName: " + updatedUser.getUserName()
            );
            Assert.assertEquals(rows, 1);
            innerNode.pass("User address updated for: " + userName);

        } catch (Exception | AssertionError e) {
            innerNode.createNode("Error");
            innerNode.fail("Update failed: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    // ---------------- PATCH USER ----------------

    @Test(priority = 7, description = "Patch user phone")
    public void patchUserPhone() {
        testnode = testTL.get().createNode("TC" + priority + "_" + testDescription);

        try {
            // Step 1: Check userName
            innerNode = testnode.createNode("Selecting User for Phone Patch");
            Assert.assertNotNull(userName, "Username is null");
            innerNode.info("User selected for phone patch: " + userName);

            // Step 2: Generate new phone number
            String newPhone = TestDataGenerator.randomPhoneNumber();

            innerNode = testnode.createNode("patch operation for user: "+userName);
            innerNodes=innerNode.createNode("Data to be patched with Phone");
            innerNodes.info("New Phone Number: " + newPhone);

            // Step 3: Perform patch
            int rows = userDao.patchPhoneNumber(newPhone, userName);
            Assert.assertEquals(rows, 1, "Patch affected unexpected number of rows");

            // Step 4: Fetch updated user details
            User updatedUser = userDao.getUserByUserName(userName);
            Assert.assertNotNull(updatedUser, "Updated user not found");

            // Step 5: Log updated user details
            innerNodes = innerNode.createNode("Updated User Details After Phone Patch");
            innerNodes.info(
                    "ID: " + updatedUser.getId() + "\n" +
                            "FirstName: " + updatedUser.getFirstName() + "\n" +
                            "LastName: " + updatedUser.getLastName() + "\n" +
                            "Address: " + updatedUser.getAddress() + "\n" +
                            "City: " + updatedUser.getCity() + "\n" +
                            "State: " + updatedUser.getState() + "\n" +
                            "ZipCode: " + updatedUser.getZipCode() + "\n" +
                            "PhoneNumber: " + updatedUser.getPhoneNumber() + "\n" +
                            "SSN: " + updatedUser.getSsn() + "\n" +
                            "UserName: " + updatedUser.getUserName()
            );

            innerNodes.pass("User phone patched successfully for: " + userName);

        } catch (Exception | AssertionError e) {
            innerNode.createNode("Error");
            innerNode.fail("Patch failed: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }


    // ---------------- DELETE USER ----------------

    @Test(priority = 8, description = "Delete user")
    public void deleteUser() {
        testnode = testTL.get().createNode("TC" + priority + "_" + testDescription);

        try {
            innerNode=testnode.createNode("Getting Random UserName to Update");
            List<String> userNames = userDao.getAllUserNames();
            userName = userNames.get(
                    ThreadLocalRandom.current().nextInt(userNames.size())
            );
            Assert.assertNotNull(userName, "Username is null");
            innerNode.info("user name to be updated is: "+userName);

            int rows = userDao.deleteUser(userName);
            Assert.assertEquals(rows, 1);

            innerNode.pass("User deleted successfully: " + userName);

        } catch (Exception | AssertionError e) {
            innerNode.createNode("Error");
            innerNode.fail("Delete failed: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }
}
