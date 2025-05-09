package airalo.steps;

import io.qameta.allure.Step;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import me.airalo.myportal.AiraloApi;

import java.util.Map;

public class ApiGeneralSteps {

    @Step
    public ValidatableResponse getValidatableApiValidateSchema(String token, String endpoint, int statusCode, String schema) {
        return AiraloApi.getApiBase(token, endpoint)
                .then()
                .statusCode(statusCode)
                .body(JsonSchemaValidator.matchesJsonSchema(schema));
    }

    @Step
    public ValidatableResponse postWithFormData(String token, String endpoint, Map<String, String> formParams, int status, String schema) {
        return AiraloApi.postApiWithFormData(token, endpoint, formParams)
                .then()
                .statusCode(status)
                .body(JsonSchemaValidator.matchesJsonSchema(schema));
    }

}
