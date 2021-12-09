package org.snakeplanner.controller;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.snakeplanner.entity.Snake;
import org.snakeplanner.security.GenerateJWT;
import org.snakeplanner.service.SnakeService;

import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import java.util.Collections;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestHTTPEndpoint(SnakeResource.class)
public class SnakeResourceTest {

    @InjectMock
    SnakeService snakeService;

    @Inject
    GenerateJWT generateJWT;

    UUID ownerId = UUID.randomUUID();
    UUID uuid = UUID.randomUUID();
    UUID wrongId = UUID.randomUUID();
    String jwt;

    @BeforeEach
    void setUp() {
        Snake snake = new Snake(ownerId.toString(),uuid,"Snake","snake","male",
                2010,(float) 500, (float) 150, "image");
        jwt = generateJWT.generate("test@test.de", 60);

        when(snakeService.getSnakeById(ownerId.toString(), uuid)).thenReturn(snake);
        when(snakeService.getSnakeById(ownerId.toString(), wrongId)).thenThrow(InternalServerErrorException.class);
        when(snakeService.getSnakesByOwnerId(ownerId.toString())).thenReturn(Collections.singletonList(snake));
        when(snakeService.deleteSnakeById(ownerId.toString(), uuid)).thenReturn(true);
        when(snakeService.deleteSnakeById(ownerId.toString(), wrongId)).thenReturn(false);
        }

    @Test
    public void testCreateSnake() {
        String requestBody = "{\n" +
                " \"ownerId\": \"+ ownerId.toString() +\",\n" +
                " \"name\": \"Snake\",\n" +
                " \"species\": \"snake\",\n" +
                " \"sex\": \"male\",\n" +
                " \"birthYear\": \"2010\",\n" +
                " \"weight\": \"500\",\n" +
                " \"size\": \"150\",\n" +
                " \"image\": \"image\"}";

        Response response = given()
                .header("Authorization", "Bearer " + jwt)
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("http://localhost/api/snakes")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Snake created", response.getBody().asPrettyString());
    }

    @Test
    public void testGetSnakeById() {
        Response response = given()
                .header("Authorization", "Bearer " + jwt)
                .pathParam("ownerId", ownerId)
                .pathParam("snakeId", uuid)
                .and()
                .when()
                .get("http://localhost/api/snakes/{ownerId}/{snakeId}")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Snake", response.jsonPath().getString("name"));
        Assertions.assertNotNull(uuid, response.jsonPath().getString("snakeId"));
    }

    @Test
    public void testGetSnakeById_error() {
        Response response = given()
                .header("Authorization", "Bearer " + jwt)
                .pathParam("ownerId", ownerId)
                .pathParam("snakeId", wrongId)
                .and()
                .when()
                .get("http://localhost/api/snakes/{ownerId}/{snakeId}")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Snake not found",response.getBody().asPrettyString());

    }

    @Test
    public void testGetSnakesByOwnerId() {
        Response response = given()
                .header("Authorization", "Bearer " + jwt)
                .pathParam("ownerId", ownerId)
                .and()
                .when()
                .get("http://localhost/api/snakes/{ownerId}")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Snake",response.jsonPath().getString("name[0]"));
        Assertions.assertNotNull(uuid, response.jsonPath().getString("snakeId[0]"));
    }

    @Test
    public void testDeleteSnakeById() {
        Response response = given()
                .header("Authorization", "Bearer " + jwt)
                .pathParam("ownerId", ownerId)
                .pathParam("snakeId", uuid)
                .and()
                .when()
                .delete("http://localhost/api/snakes/{ownerId}/{snakeId}")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Snake successfully deleted", response.getBody().asPrettyString());

    }

    @Test
    public void testDeleteSnakeById_error() {
        Response response = given()
                .header("Authorization", "Bearer " + jwt)
                .pathParam("ownerId", ownerId)
                .pathParam("snakeId", wrongId)
                .and()
                .when()
                .delete("http://localhost/api/snakes/{ownerId}/{snakeId}")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Was not able to delete the snake", response.getBody().asPrettyString());
    }
}
