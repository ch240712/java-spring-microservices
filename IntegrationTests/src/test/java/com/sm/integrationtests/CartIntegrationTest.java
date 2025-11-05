package com.sm.integrationtests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CartIntegrationTest {
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:4002"; // Api Gateway
    }

    @Test
    public void shouldReturnCartItemsWithValidToken() {
        // Get a token
        String loginPayload = """
                    {
                        "email": "testuser@test.com",
                        "password": "password123"
                    }
                """;

        // This shared code would typically go into a helper class
        String token = given() // arrange
                .contentType("application/json")
                .body(loginPayload)
                .when() // act
                .post("/auth/login")
                .then() // assert
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("token");

        Response response = given()
                .header("Authorization", "Bearer " + token) // Use token to get customer's cart items
                .when()
                .get("/api/cart/223e4567-e89b-12d3-a456-426614174008")
                .then()
                .statusCode(200)
                .body("items", notNullValue())
                .extract()
                .response();

        response.prettyPrint();
    }
}
