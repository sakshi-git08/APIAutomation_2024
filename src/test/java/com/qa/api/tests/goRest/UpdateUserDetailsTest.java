package com.qa.api.tests.goRest;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UpdateUserDetailsTest extends BaseTest {
    static String emailId;

    public String generateEmail(){
        emailId = "sakshi" + System.currentTimeMillis() +"@gmail.com";
        return emailId;
    }

    @Test
    public void updateUser(){
        User user = User.builder()
                .email(generateEmail())
                .name("Sakshi")
                .gender("female")
                .status("active")
                .build();
        Response postResponse = restClient.post(BASE_URL_GOREST, "/public/v2/users", user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(postResponse.getStatusCode(), 201);
        String userId = postResponse.jsonPath().getString("id");
        System.out.println("user id ==>"+ userId);

        //Get:
        Response getResponse = restClient.get(BASE_URL_GOREST, "/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(getResponse.getStatusCode(), 200);
        Assert.assertEquals(getResponse.jsonPath().getString("id"), userId);
        System.out.println(getResponse.prettyPrint());

        user.setStatus("inactive");
        user.setEmail(generateEmail());

        //Put:
        Response putResponse = restClient.put(BASE_URL_GOREST, "/public/v2/users/"+userId, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(putResponse.getStatusCode(), 200);
        Assert.assertEquals(putResponse.jsonPath().getString("id"), userId);
        Assert.assertEquals(putResponse.jsonPath().getString("status"), user.getStatus());
        Assert.assertEquals(putResponse.jsonPath().getString("email"), user.getEmail());
        System.out.println(putResponse.prettyPrint());
    }
}
