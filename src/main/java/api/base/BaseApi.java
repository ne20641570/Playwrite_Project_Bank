package api.base;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class BaseApi {
    protected RequestSpecification request;


    protected String getBaseUrl() {
        String baseUrl = System.getProperty("base.url");

        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = System.getenv("BASE_URL");
        }

        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new RuntimeException("Base URL is not configured");
        }

        return baseUrl;
    }
    public BaseApi(String baseUrl) {
        RestAssured.baseURI = baseUrl;
        request = RestAssured .given() .relaxedHTTPSValidation().header("Content-Type", "application/json");
    }

    public void setAuthToken(String token) {
        request = RestAssured
                .given()
                .header("Authorization", "Bearer " + token);
    }
}