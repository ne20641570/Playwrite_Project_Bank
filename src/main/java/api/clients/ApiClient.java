package api.client;

import com.microsoft.playwright.*;
import java.util.HashMap;
import java.util.Map;

public class ApiClient {

    private static Playwright playwright;
    private static APIRequestContext requestContext;

    public static void getRequestContext(String baseUrl) {
        if (requestContext == null) {
            playwright = Playwright.create();

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");

            requestContext = playwright.request().newContext(
                    new APIRequest.NewContextOptions()
                            .setBaseURL(baseUrl)
                            .setExtraHTTPHeaders(headers)
                            .setIgnoreHTTPSErrors(true)
            );
        }
    }

    public static void close() {
        if (requestContext != null) requestContext.dispose();
        if (playwright != null) playwright.close();
    }
}
