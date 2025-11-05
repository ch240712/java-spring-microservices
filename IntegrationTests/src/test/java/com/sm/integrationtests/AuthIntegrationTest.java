package com.sm.integrationtests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class AuthIntegrationTest {
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:4002"; // Api Gateway. RestAssured runs outside of Docker context
    }

    @Test
    public void shouldReturnOKWithValidToken() {
        // Three steps:
        // 1. Arrange - do any setup the test needs
        // 2. Act - code to trigger the action
        // 3. Assert - the result/response is as expected

        String loginPayload = """
                    {
                        "email": "testuser@test.com",
                        "password": "password123"
                    }
                """;

        Response response = given() // arrange
                .contentType("application/json")
                .body(loginPayload)
                .when() // act
                .post("/auth/login")
                .then() // assert
                .statusCode(200)
                .body("token", notNullValue())
                .extract()
                .response();

        response.prettyPrint();
    }

    @Test
    public void shouldReturnUnauthorizedOnInvalidLogin() {
        String loginPayload = """
                    {
                        "email": "testuser@test.com",
                        "password": "wrongpassword"
                    }
                """;

        given() // arrange
                .contentType("application/json")
                .body(loginPayload)
                .when() // act
                .post("/auth/login")
                .then() // assert
                .statusCode(401);
    }
}
