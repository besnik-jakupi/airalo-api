package me.airalo.myportal;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import me.airalo.myportal.payloads.PayloadsAiralo;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AiraloApi {

    private static final String PORTAL_URL = "https://sandbox-partners-api.airalo.com/v2/";
    private static final String CONTENT_TYPE_PARAM = "application/json";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_TYPE = "Bearer ";
    public static Faker faker = new Faker();

    public static Response postAuthEndpoints(PayloadsAiralo body, String endpoint) {
        return
                given().log().all()
                        .baseUri(PORTAL_URL + endpoint)
                        .contentType(CONTENT_TYPE_PARAM)
                        .body(body)
                        .when()
                        .post()
                        .then().log().all()
                        .extract().response();
    }

    public static Response getApi(String token, String endpoint) {
        return
                given().log().all()
                        .baseUri(PORTAL_URL)
                        .contentType(CONTENT_TYPE_PARAM)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TYPE + token)
                        .when()
                        .get(endpoint)
                        .then().log().all()
                        .extract().response();
    }

    public static Response postApiWithFormData(String token, String endpoint, Map<String, String> formParams) {
        RequestSpecification request = given().log().all()
                .baseUri(PORTAL_URL + endpoint)
                .header(AUTHORIZATION_HEADER, AUTHORIZATION_TYPE + token);

        for (Map.Entry<String, String> entry : formParams.entrySet()) {
            request = request.multiPart(entry.getKey(), entry.getValue(), "text/plain");
        }

        return request
                .when()
                .post()
                .then().log().all()
                .extract().response();
    }

    public static Response getApiBase(String endpoint, String token) {
        return getApi(endpoint, token);
    }
}
