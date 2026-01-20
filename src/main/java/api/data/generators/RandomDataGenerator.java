package api.data.generators;

import utils.TestDataGenerator;

import java.time.Instant;
import java.util.*;

public class RandomDataGenerator extends TestDataGenerator {

    private static final Random RANDOM = new Random();

    public static int randomId() {
        return RANDOM.nextInt(999999);
    }

    public static String randomCategoryName() {
        String[] firstNames = {"Dog", "Cat", "Fish", "Cow"};
        return firstNames[RANDOM.nextInt(firstNames.length)];
    }
    public static String randomPetName(String prefix) {
        return prefix + "_" + UUID.randomUUID().toString().substring(0, 6);
    }

    public static String randomEmail() {
        return "user_" + UUID.randomUUID().toString().substring(0, 5) + "@test.com";
    }

    public static String randomPhone() {
        return "9" + (RANDOM.nextInt(900000000) + 100000000);
    }

    public static String currentDate() {
        return Instant.now().toString();
    }

    public static Map<String, String> randomPetData() {

        Map<String, String[]> categoryMap = Map.of(
                "Dog", new String[]{"Bulldog", "Pitbull", "Labrador"},
                "Cat", new String[]{"Persian", "Siamese", "MaineCoon"},
                "Fish", new String[]{"GoldenFish", "Betta", "Guppy"},
                "Cow", new String[]{"Jersey", "Holstein", "Angus"}
        );

        // Pick random category
        List<String> categories = new ArrayList<>(categoryMap.keySet());
        String category = categories.get(RANDOM.nextInt(categories.size()));

        // Pick random breed
        String[] breeds = categoryMap.get(category);
        String breed = breeds[RANDOM.nextInt(breeds.length)];

        // Generate suffix
        String suffix = UUID.randomUUID().toString().substring(0, 6);

        // Final pet name
        String petName = breed + "_" + suffix;

        Map<String, String> petData = new HashMap<>();
        petData.put("category", category);
        petData.put("breed", breed);
        petData.put("petName", petName);

        return petData;
    }

}
