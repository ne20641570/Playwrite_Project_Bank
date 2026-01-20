package tests.api;

import api.data.factory.OrderDataFactory;
import api.models.store.Order;
import api.services.StoreService;
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


@Scenario("Store Api Test")
@Test(groups = "StoreAPITest")
public class StoreApiTest extends BaseApiTest {
    ExtentTest innerTestNode;
    
    @Test(priority = 1, description = "Create Store")
    public void placeOrderTest(Method method) {
         try {
            innerTestNode = testnode.createNode("Store Details");
            Order order = OrderDataFactory.createRandomOrder();
            innerTestNode.info(gson.toJson(order));

            Response response = new StoreService(baseUrl).placeOrder(order);

            innerTestNode = testnode.createNode("Store Response");
            innerTestNode.info(intoJsonForReport(response));

            Assert.assertEquals(response.statusCode(), 200);
            Assert.assertEquals(response.jsonPath().getLong("id"), order.id);

            innerTestNode.pass("Creating store successfully completed");
        } catch (Exception | AssertionError e) {
            innerTestNode.info(e.getMessage());
            innerTestNode.fail("Create Store failed");
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 2, description = "Place Order")
    public void createOrder() {
         try {
            StoreService service = new StoreService(baseUrl);
            Order order = OrderDataFactory.createRandomOrder();

            innerTestNode = testnode.createNode("Creating Random Order");
            innerTestNode.info(gson.toJson(order));

            Response response = service.placeOrder(order);

            innerTestNode = testnode.createNode("Reading Order by Service ID");
            innerTestNode.info("Order ID: " + order.id);

            innerTestNode = testnode.createNode("Order Response");
            innerTestNode.info(intoJsonForReport(response));

            Assert.assertEquals(response.statusCode(), 200);
            Assert.assertEquals(response.jsonPath().getLong("id"), order.id);

            innerTestNode.pass("Placing Order successfully completed");
        } catch (Exception | AssertionError e) {
            innerTestNode.info(e.getMessage());
            innerTestNode.fail("Placing Order failed");
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 3, description = "Fetch Order")
    public void getOrderByIdTest() {
         try {
            StoreService service = new StoreService(baseUrl);
            Order order = OrderDataFactory.createRandomOrder();

            innerTestNode = testnode.createNode("Creating Random Order");
            innerTestNode.info(gson.toJson(order));
            service.placeOrder(order);

            Response response = service.getOrderById(order.id);

            innerTestNode = testnode.createNode("Reading Order by Service ID");
            innerTestNode.info("Order ID: " + order.id);

            innerTestNode = testnode.createNode("Order Response");
            innerTestNode.info(intoJsonForReport(response));

            Assert.assertEquals(response.statusCode(), 200);
            Assert.assertEquals(response.jsonPath().getLong("id"), order.id);

            innerTestNode.pass("Fetching Order successfully completed");
        } catch (Exception | AssertionError e) {
            innerTestNode.info(e.getMessage());
            innerTestNode.fail("Fetch Order failed");
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 4, description = "Delete Order")
    public void deleteOrderTest() {
         try {
            StoreService service = new StoreService(baseUrl);
            Order order = OrderDataFactory.createRandomOrder();

            innerTestNode = testnode.createNode("Creating Random Order");
            innerTestNode.info(gson.toJson(order));
            service.placeOrder(order);

            Response deleteResponse = service.deleteOrder(order.id);

            innerTestNode = testnode.createNode("Deleting Order");
            innerTestNode.info("Order ID: " + order.id);

            innerTestNode = testnode.createNode("Delete Response");
            innerTestNode.info(intoJsonForReport(deleteResponse));

            Assert.assertEquals(deleteResponse.statusCode(), 200);

            innerTestNode.pass("Deleting Order successfully completed");
        } catch (Exception | AssertionError e) {
            innerTestNode.info(e.getMessage());
            innerTestNode.fail("Delete Order failed");
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 5, description = "Get Inventory")
    public void getInventoryTest() {
         try {
            Response response = new StoreService(baseUrl).getInventory();

            innerTestNode = testnode.createNode("Inventory Response");
            innerTestNode.info(intoJsonForReport(response));

            Assert.assertEquals(response.statusCode(), 200);
            innerTestNode.pass("Reading inventory successfully completed");
        } catch (Exception | AssertionError e) {
            innerTestNode.info(e.getMessage());
            innerTestNode.fail("Get Inventory failed");
            Assert.fail(e.getMessage());
        }
    }
}
