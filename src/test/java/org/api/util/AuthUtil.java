package org.api.util;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;

public class AuthUtil {

    public static String getAuthToken() {
        Response response = RestAssured.given()
                .baseUri(Constants.BASE_URI)
                .basePath(Constants.AUTH_ENDPOINT)
                .header("Content-Type", "application/json")
                .body("{\"username\": \"admin\", \"password\": \"password123\"}")
                .when()
                .post();

        JsonPath jsonPath = response.jsonPath();
        String token = jsonPath.getString("token");

        return token;
    }
}
