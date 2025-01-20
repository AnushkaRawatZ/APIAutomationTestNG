package org.api.util;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RestUtil {
    private final RequestSpecification requestSpecification;
    private final String fullRequestUrl;
    private final RequestSpecBuilder requestSpecBuilder;

    public RestUtil(String baseUri, String basePath, String requestBody) {
        requestSpecBuilder = new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .setBaseUri(baseUri)
                .setBasePath(basePath);
        requestSpecification = requestSpecBuilder
                .setBody(requestBody)
                .build();

        this.fullRequestUrl = baseUri + basePath;
    }


}
