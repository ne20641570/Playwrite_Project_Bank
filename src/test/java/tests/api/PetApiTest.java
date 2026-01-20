package tests.api;

import api.data.generators.RandomDataGenerator;
import api.models.pet.*;
import api.services.PetService;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.ConfigReader;
import extentReporter.ReportManager;
import io.restassured.response.Response;
import listeners.Scenario;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static api.data.factory.PetDataFactory.petDetails;
import static api.endpoints.PetEndpoints.FIND_STATUS_01;

@Scenario("Pet Api Test")
@Test(groups = "PetAPITest",priority = 1)
public class PetApiTest extends BaseApiTest {
    private long searchPetId;
    ExtentTest innerTestNode;
    
    ExtentTest innerTestNodes;


        @Test(priority = 1, description = "Addition of new pet")
    public void addPetTest() {
        PetService petService = new PetService(baseUrl);
         ;
        try {
            innerTestNode=testnode.createNode("Pet Details");
            JSONObject pet = petDetails();
            innerTestNode= innerTestNode.info(String.valueOf(pet));
            System.out.println("requestBody: "+gson.toJson(pet));

            Response response = petService.addPet(pet);
//            System.out.println("response: "+response.asPrettyString());
            innerTestNode.info(intoJsonForReport(response));

            Assert.assertEquals(response.statusCode(), 200);
            innerTestNode.pass("Pet has been added successfully");

        }catch (Exception | AssertionError e){
            System.out.println(e.getMessage());
            innerTestNode.info(e.getMessage());
            innerTestNode.fail("Adding pet has been failed");
        }
    }
    @Test(priority = 2, description = "Fetch all pets by status")
    public void findPetByStatusTest() {
         ;
        PetService petService = new PetService(baseUrl);
        try {
            Response response = petService.findPetsByStatus(FIND_STATUS_01);

            innerTestNode = testnode.createNode("Fetch Details");
            innerTestNode = innerTestNode.info("request status: " + FIND_STATUS_01);
//        System.out.println("request status: "+FIND_STATUS_01);
//        System.out.println(response.asPrettyString());
            innerTestNode = testnode.createNode("Response");
            innerTestNode.info(intoJsonForReport(response));
            List<Integer> ids = response.jsonPath().getList("id");
            if (ids == null || ids.isEmpty()) {
                throw new RuntimeException("No pet IDs found in response");
            }
            Random random = new Random();
            Number selected = ids.get(random.nextInt(ids.size()));
            searchPetId = selected.longValue();
            System.out.println(searchPetId);
            innerTestNode=testnode.createNode("Storing searchPetID");
            innerTestNode = innerTestNode.info("storing searchPetId as: " + searchPetId);
            Assert.assertEquals(response.statusCode(), 200);
        }catch (Exception | AssertionError e){
            System.out.println(e.getMessage());
            innerTestNode.info(e.getMessage());
            innerTestNode.fail("Fetch pets by status has been failed");
        }
    }

    @Test(priority = 3, description = "Updating of existing pet")
    public void updatePetTest() {

         ;
        PetService petService = new PetService(baseUrl);
        try {
            innerTestNode = testnode.createNode("Update Details of Existing pet");
            JSONObject pet = petDetails();
            pet.put("name", pet.get("name")+" updated");
            innerTestNode.info("request Body: "+gson.toJson(pet));
            Response response = petService.updatePet(pet);
//            System.out.println(response.asPrettyString());
            innerTestNode=testnode.createNode("Response for UpdateDetails");
            innerTestNode.info(intoJsonForReport(response));
            Assert.assertEquals(response.statusCode(), 200);
        }catch (Exception | AssertionError e){
            System.out.println(e.getMessage());
            innerTestNode.info(e.getMessage());
            innerTestNode.fail("Update Details has been failed");
        }
    }
    @Test(priority = 4, dependsOnMethods = "findPetByStatusTest", description = "Get Pet details by Id (fetching id By findPetByStatusTest)")
    public void getPetByIdTest() {
         ;
        PetService petService = new PetService(baseUrl);
        try {

            innerTestNode = testnode.createNode("Fetch Details By Id:");
            innerTestNode = innerTestNode.info("Id: " + searchPetId);
            Response response = petService.getPetById(searchPetId);
            innerTestNode = testnode.createNode("Response :");
            innerTestNode = innerTestNode.info(intoJsonForReport(response));
//            System.out.println(intoJsonForReport(response));
            Assert.assertEquals(response.statusCode(), 200);
        }catch (Exception | AssertionError e){
            System.out.println(e.getMessage());
            innerTestNode.info(e.getMessage());
            innerTestNode.fail("get pet id has been failed");
        }
    }

}
