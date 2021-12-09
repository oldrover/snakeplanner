package org.snakeplanner.controller;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.snakeplanner.entity.Event;
import org.snakeplanner.security.GenerateJWT;
import org.snakeplanner.service.EventService;

import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestHTTPEndpoint(EventResource.class)
public class EventResourceTest {

    @InjectMock
    EventService eventService;

    @Inject
    GenerateJWT generateJWT;

    UUID snakeId = UUID.randomUUID();
    UUID uuid = UUID.randomUUID();
    UUID wrongId = UUID.randomUUID();
    String jwt;

    @BeforeEach
    void setUp() {
        Event event = new Event(snakeId.toString(), null, "feed",
                LocalDate.of(2021,10,20),"jumper");
        jwt = generateJWT.generate("test@test.de", 60);

        when(eventService.getEventById(snakeId.toString(), uuid)).thenReturn(event);
        when(eventService.getEventById(snakeId.toString(), wrongId)).thenThrow(InternalServerErrorException.class);
        when(eventService.getEventsBySnakeId(snakeId.toString())).thenReturn(Collections.singletonList(event));
        when(eventService.deleteEventById(snakeId.toString(), uuid)).thenReturn(true);
        when(eventService.deleteEventById(snakeId.toString(), wrongId)).thenReturn(false);
    }

    @Test
    public void testCreateEvent() {
        String requestBody = "{\n" +
                " \"snakeId\": \"+ snakeId.toString() +\",\n" +
                " \"type\": \"feed\",\n" +
                " \"date\": \"2021.10.20\",\n" +
                " \"info\": \"jumper\"}";

        Response response = given()
                .header("Authorization", "Bearer " + jwt)
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("http://localhost/api/events")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Event created", response.getBody().asPrettyString());
    }

    @Test
    public void testGetEventById() {
        Response response = given()
                .header("Authorization", "Bearer " + jwt)
                .pathParam("snakeId", snakeId)
                .pathParam("eventId", uuid)
                .and()
                .when()
                .get("http://localhost/api/events/{snakeId}/{eventId}")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("feed", response.jsonPath().getString("type"));
        Assertions.assertNotNull(uuid, response.jsonPath().getString("eventId"));
    }

    @Test
    public void testGetSnakeById_error() {
        Response response = given()
                .header("Authorization", "Bearer " + jwt)
                .pathParam("snakeId", snakeId)
                .pathParam("eventId", wrongId)
                .and()
                .when()
                .get("http://localhost/api/events/{snakeId}/{eventId}")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Event not found",response.getBody().asPrettyString());

    }

    @Test
    public void testGetEventsBySnakeId() {
        Response response = given()
                .header("Authorization", "Bearer " + jwt)
                .pathParam("snakeId", snakeId)
                .and()
                .when()
                .get("http://localhost/api/events/{snakeId}")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("feed",response.jsonPath().getString("type[0]"));
        Assertions.assertNotNull(uuid, response.jsonPath().getString("eventId[0]"));
    }

    @Test
    public void testDeleteSnakeById() {
        Response response = given()
                .header("Authorization", "Bearer " + jwt)
                .pathParam("snakeId", snakeId)
                .pathParam("eventId", uuid)
                .and()
                .when()
                .delete("http://localhost/api/events/{snakeId}/{eventId}")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Event successfully deleted", response.getBody().asPrettyString());

    }

    @Test
    public void testDeleteSnakeById_error() {
        Response response = given()
                .header("Authorization", "Bearer " + jwt)
                .pathParam("snakeId", snakeId)
                .pathParam("eventId", wrongId)
                .and()
                .when()
                .delete("http://localhost/api/events/{snakeId}/{eventId}")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Was not able to delete the event", response.getBody().asPrettyString());
    }
}
