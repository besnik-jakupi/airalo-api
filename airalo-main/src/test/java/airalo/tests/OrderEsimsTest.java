package airalo.tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import airalo.steps.ApiGeneralSteps;
import airalo.steps.specific.ApiAuth;
import org.apache.http.HttpStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OrderEsimsTest {
    private String accessToken;
    private static ValidatableResponse response;
    private static String message, filePath, schema;
    JsonPath jsonPath;
    private static final ApiAuth API_AUTH = new ApiAuth();
    private static final ApiGeneralSteps API_GENERAL_STEPS = new ApiGeneralSteps();

    @BeforeEach
    public void setAccessToken() { accessToken = API_AUTH.getAccessToken(); }

    @Test
    @DisplayName("POST Create order - 200 OK")
    void createOrder() throws IOException {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("quantity", "6");
        formParams.put("package_id", "merhaba-7days-1gb");
        formParams.put("type", "sim");
        formParams.put("description", "Example description to identify the order");

        filePath = "src/main/resources/orderSchema.json";
        schema = new String(Files.readAllBytes(Paths.get(filePath)));

        response = API_GENERAL_STEPS.postWithFormData(accessToken, "orders", formParams, HttpStatus.SC_OK, schema);

        message = response.extract().body().asString();
        jsonPath = response.extract().jsonPath();

        assertThat(message, containsString("message\":\"success"));
        assertThat(jsonPath.getList("data.sims"), hasSize(6));
    }

    @Test
    @DisplayName("Get eSims list- 200 OK")
    void getEsims() throws IOException {
        String endpoint = "sims?include=order&limit=6";

        filePath = "src/main/resources/eSimsSchema.json";
        schema = new String(Files.readAllBytes(Paths.get(filePath)));
        response = API_GENERAL_STEPS.getValidatableApiValidateSchema(accessToken, endpoint, HttpStatus.SC_OK, schema);
        message = response.extract().body().asString();

        assertThat(message, containsString("data"));
        response.body("data", hasSize(6))
                .body("data.simable.package_id", everyItem(equalTo("merhaba-7days-1gb")));
    }

}
