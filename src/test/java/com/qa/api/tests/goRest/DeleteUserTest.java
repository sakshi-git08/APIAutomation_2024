package com.qa.api.tests.goRest;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteUserTest extends BaseTest {
    static String emailId;

    public String generateEmail(){
        emailId = "testsakshi" + System.currentTimeMillis() +"@gmail.com";
        return emailId;
    }

    @Test
    public void deleteUser(){
        User user = User.builder()
                .name("sakshi")
                .email(generateEmail())
                .gender("female")
                .status("active")
                .build();

        Response response = restClient.post(BASE_URL_GOREST, "/public/v2/users", user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(response.getStatusCode(), 201);

        String userId = response.jsonPath().getString("id");
        System.out.println("user id ==>"+ userId);

        //Get:
        Response getResponse = restClient.get(BASE_URL_GOREST, "/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(getResponse.getStatusCode(), 200);
        Assert.assertEquals(getResponse.jsonPath().getString("id"), userId);
        System.out.println(getResponse.prettyPrint());

        //Delete:
        Response deleteResponse = restClient.delete(BASE_URL_GOREST, "/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(deleteResponse.getStatusCode(), 204);

        Response getResponseAgain = restClient.get(BASE_URL_GOREST, "/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(getResponseAgain.getStatusCode(), 404);

    }
}
