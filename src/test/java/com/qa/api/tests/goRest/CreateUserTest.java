package com.qa.api.tests.goRest;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class CreateUserTest extends BaseTest {

    @Test
    public void testCreateUser() {
        User user = User.builder().
                name("Sakshi")
                .email("sakshiqa@gmail.com")
                .gender("female")
                .status("active").build();

        Response response = restClient.post(BASE_URL_GOREST, "/public/v2/users", user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(response.getStatusCode(), 201);

        String userId = response.jsonPath().getString("id");
        System.out.println("user id ==>"+ userId);

    }
}
