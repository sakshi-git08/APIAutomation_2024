package com.qa.api.client;

import com.qa.api.constants.AuthType;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Map;

import static io.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;


public class RestClient {
    // Define a ResponseSpecification for successful responses (200 OK)
    private ResponseSpecification responseSpec200 = expect().log().all().statusCode(200);

    private ResponseSpecification responseSpec200Or404 =
            expect().log().all().statusCode(anyOf(equalTo(200), equalTo(404)));

    private ResponseSpecification responseSpec200Or201 =
            expect().log().all().statusCode(anyOf(equalTo(200), equalTo(201)));


    // Define a ResponseSpecification for 201 Created
    private ResponseSpecification responseSpec201 = expect().log().all().statusCode(201);

    private ResponseSpecification responseSpec204 = expect().log().all().statusCode(204);


    // Define a ResponseSpecification for 400 Bad Request
    private ResponseSpecification responseSpec400 = expect().log().all().statusCode(400);

    // Define a ResponseSpecification for 401 Unauthorized
    private ResponseSpecification responseSpec401 = expect().log().all().statusCode(401);

    // Define a ResponseSpecification for 404 Not Found
    private ResponseSpecification responseSpec404 = expect().log().all().statusCode(404);

    // Define a default ResponseSpecification for other cases (can customize later)
    private ResponseSpecification defaultResponseSpec = expect().log().all();

    private RequestSpecification setUpRequest(String baseUrl, AuthType authType, ContentType contentType) {
        RequestSpecification request = RestAssured.given().log().all()
                .baseUri(baseUrl)
                .contentType(contentType)
                .accept(contentType);

        switch (authType) {
            case BEARER_TOKEN:
                request.header("Authorization", "Bearer " + ConfigManager.get("bearerToken"));
                break;
            case NO_AUTH:
                // No authentication header needed
                System.out.println("no auth is required...");
                break;
        }
        return request;
    }

    public <T> Response post(String baseUrl, String endpoint, T body, Map<String, String> queryParam,
                             Map<String, String> pathParams, AuthType authType, ContentType contentType) {
        RequestSpecification request = setUpRequest(baseUrl, authType, contentType);

        applyParams(request, queryParam, pathParams, endpoint);
        return request.body(body).post(endpoint).then().spec(responseSpec200Or201).extract().response();
    }

    public Response get(String baseUrl, String endpoint, Map<String, String> queryParam,
                             Map<String, String> pathParams, AuthType authType, ContentType contentType) {
        RequestSpecification request = setUpRequest(baseUrl, authType, contentType);

        applyParams(request, queryParam, pathParams, endpoint);
        return request.get(endpoint).then().spec(responseSpec200Or404).extract().response();
    }

    public <T> Response put(String baseUrl, String endpoint, T body, Map<String, String> queryParam,
                             Map<String, String> pathParams, AuthType authType, ContentType contentType) {
        RequestSpecification request = setUpRequest(baseUrl, authType, contentType);

        applyParams(request, queryParam, pathParams, endpoint);
        return request.body(body).put(endpoint).then().spec(responseSpec200Or201).extract().response();
    }

    private void applyParams(RequestSpecification request, Map<String, String> queryParams, Map<String, String> pathParams, String endpoint) {
        if (queryParams != null) {
            request.queryParams(queryParams);
        }
        if (pathParams != null) {
            endpoint = replacePathParams(endpoint, pathParams);
        }
    }

    // Helper method to replace path params in the endpoint
    private String replacePathParams(String endpoint, Map<String, String> pathParams) {
        for (Map.Entry<String, String> entry : pathParams.entrySet()) {
            endpoint = endpoint.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return endpoint;
    }

}
