package api.services;

import api.base.BaseApi;
import api.endpoints.PetEndpoints;
import api.models.pet.Pet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.io.File;

public class PetService extends BaseApi {

    public PetService(String baseUrl) {
        super(baseUrl);
    }

    // 1. Add a new pet to the store
    public Response addPet(JSONObject pet) {
        return request.contentType(ContentType.JSON)
                .body(pet)
                .post(PetEndpoints.ADD_PET);
    }
    // 2. Update an existing pet
    public Response updatePet(JSONObject pet) {
        return request
                .body(pet)
                .put(PetEndpoints.UPDATE_PET);
    }
    // 3. Find pets by status
    public Response findPetsByStatus(String status) {
        return request
                .queryParam("status", status)
                .get(PetEndpoints.FIND_BY_STATUS);
    }
    // 4. Find pet by ID
    public Response getPetById(long petId) {
        return request
                .pathParam("petId", petId)
                .get(PetEndpoints.GET_PET_BY_ID);
    }
    // 5. Update pet with form data
    public Response updatePetWithForm(long petId, String name, String status) {
        return request
                .pathParam("petId", petId)
                .formParam("name", name)
                .formParam("status", status)
                .post(PetEndpoints.UPDATE_PET_WITH_FORM);
    }
    // 6. Delete a pet
    public Response deletePet(long petId) {
        return request
                .pathParam("petId", petId)
                .delete(PetEndpoints.DELETE_PET);
    }
    // 7. Upload an image for a pet
    public Response uploadPetImage(long petId, File imageFile) {
        return request
                .pathParam("petId", petId)
                .multiPart("file", imageFile)
                .post(PetEndpoints.UPLOAD_IMAGE);
    }

}
