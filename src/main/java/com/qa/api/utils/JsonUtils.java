package com.qa.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import java.io.IOException;

public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T deserialize(Response response, Class<T> targetClass) {

        try {

            return objectMapper.readValue(response.getBody().asString(), targetClass);
        } catch (IOException e) {

            throw new RuntimeException("failed to deserialize response body to : " + targetClass.getName(), e);
        }
    }
}
