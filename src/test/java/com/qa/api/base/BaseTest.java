package com.qa.api.base;

import com.qa.api.client.RestClient;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;

public class BaseTest {

    protected final String BASE_URL_REQ_RES = "https://reqres.in";
    protected final String BASE_URL_GOREST = "https://gorest.co.in";

    protected RestClient restClient;

    @BeforeTest
    public void setUp(@Optional String baseUrl) {
        RestAssured.filters(new AllureRestAssured());

        // Initialize RestClient and any other setup needed before each test
        restClient = new RestClient();
    }

}
