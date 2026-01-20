package api.data.factory;

import api.data.generators.RandomDataGenerator;
import api.endpoints.PetEndpoints;
import api.models.pet.*;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class PetDataFactory {
    public static JSONObject petDetails(){
        Map<String, String> petData = RandomDataGenerator.randomPetData();
        JSONObject category = new JSONObject();
        int id=RandomDataGenerator.randomId();
        category.put("id" ,id);
        category.put("name",  petData.get("category"));

        JSONObject tag = new JSONObject();
        tag.put("id" ,id);
        tag.put("name",  petData.get("breed"));

        JSONObject pet = new JSONObject();
        pet.put("id" ,id);
        pet.put("category",category);
        pet.put("name",petData.get("petName"));
        pet.put("photoUrls", List.of("https://test.com/dog.jpg"));
        pet.put("tags", List.of(tag));
        pet.put("status" , PetEndpoints.FIND_STATUS_01);
        return pet;
    }
}
