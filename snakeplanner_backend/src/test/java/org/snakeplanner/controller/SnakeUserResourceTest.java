package org.snakeplanner.controller;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;

import io.quarkus.test.junit.mockito.InjectMock;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.snakeplanner.dto.CreateUserDto;
import org.snakeplanner.dto.LoginDto;
import org.snakeplanner.entity.SnakeUser;
import org.snakeplanner.security.GenerateJWT;
import org.snakeplanner.service.SnakeUserService;


import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestHTTPEndpoint(SnakeUserResource.class)
public class SnakeUserResourceTest {

    @InjectMock
    SnakeUserService snakeUserService;

    @Inject
    GenerateJWT generateJWT;

    UUID uuid = UUID.randomUUID();
    UUID wrongId = UUID.randomUUID();
    String jwt;

    @BeforeEach
    void setUp() {
        CreateUserDto createUserDto = new CreateUserDto(null, "test@test.de", "password");
        SnakeUser snakeUser = new SnakeUser("test@test.de", uuid, "salt", "password");
        jwt = generateJWT.generate("test@test.de", 60);
        when(snakeUserService.isEmailAvailable("test@test.de")).thenReturn(true);
        when(snakeUserService.isEmailAvailable("wrong@test.de")).thenReturn(false);
        when(snakeUserService.generateUserWithHashedPassword(createUserDto)).thenReturn(snakeUser);
        when(snakeUserService.loginUser(any(LoginDto.class))).thenReturn(snakeUser);
        when(snakeUserService.getUserByEmailAndId("test@test.de", uuid)).thenReturn(snakeUser);
        when(snakeUserService.getUserByEmailAndId("test@test.de", wrongId)).thenThrow(InternalServerErrorException.class);
    }


    @Test
    public void testCreateUser() {
        String requestBody = "{\n" +
                " \"email\": \"test@test.de\",\n" +
                " \"password\": \"password\"}";

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("http://localhost/api/users")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("test@test.de", response.jsonPath().getString("email"));
        Assertions.assertNotNull(response.jsonPath().getString("id"));
    }


    @Test
    public void testCreateUser_error() {
        String requestBody = "{\n" +
                " \"email\": \"wrong@test.de\",\n" +
                " \"password\": \"password\"}";

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("http://localhost/api/users")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Email already taken",response.getBody().asPrettyString());

    }

    @Test
    public void testLoginUser() {
        String requestBody = "{\n" +
                " \"email\": \"test@test.de\",\n" +
                " \"password\": \"password\"}";

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("http://localhost/api/users/login")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("test@test.de", response.jsonPath().getString("email"));
        Assertions.assertNotNull(response.jsonPath().getString("id"));
        Assertions.assertNotNull(response.header("Authentication"));
    }

    @Test
    public void testGetUserById() {
        Response response = given()
                .header("Authorization", "Bearer " + jwt)
                .pathParam("id", uuid)
                .and()
                .when()
                .get("http://localhost/api/users/{id}")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("test@test.de", response.jsonPath().getString("email"));
        Assertions.assertNotNull(response.jsonPath().getString("id"));
    }

    @Test
    public void testGetUserById_error() {
        Response response = given()
                .header("Authorization", "Bearer " + jwt)
                .pathParam("id", wrongId)
                .and()
                .when()
                .get("http://localhost/api/users/{id}")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("User not found",response.getBody().asPrettyString());

    }

    @Test
    public void testDeleteUserById() {
        Response response = given()
                .header("Authorization", "Bearer " + jwt)
                .pathParam("id", uuid)
                .and()
                .when()
                .delete("http://localhost/api/users/{id}")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
    }

}
