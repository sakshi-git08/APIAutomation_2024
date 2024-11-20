package com.qa.api.tests.goRest;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.JsonUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class GetUserDetailsUsingDeserializeTest extends BaseTest {

    @Test
    public void getUserDetails() {

        Response response = restClient.get(BASE_URL_GOREST, "/public/v2/users", null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        User[] userRes = JsonUtils.deserialize(response, User[].class);
        for (User user : userRes) {
            System.out.println("ID : " + user.getId());
            System.out.println("NAME : " + user.getName());
            System.out.println("EMAIL : " + user.getEmail());
            System.out.println("STATUS : " + user.getStatus());
            System.out.println("GENDER : " + user.getGender());

            System.out.println("----------------------");

        }

    }
}
