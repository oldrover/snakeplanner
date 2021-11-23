package org.snakeplanner.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.snakeplanner.dto.SnakeDto;
import org.snakeplanner.entity.Snake;
import org.snakeplanner.service.SnakeService;

@Path("/api/snakes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SnakeResource {

  @Inject SnakeService snakeService;

  @POST
  @RolesAllowed("User")
  public Response addSnake(SnakeDto snakeDto) {
    try {
      snakeDto.setSnakeId(UUID.randomUUID());
      snakeService.saveSnake(convertFromDto(snakeDto));
      return Response
              .ok("Snake created")
              .build();
    }catch(InternalServerErrorException exception) {
      return Response
              .ok("Was not able to create Snake")
              .build();
    }
  }

  @GET
  @Path("/{ownerId}/{snakeId}")
  @RolesAllowed("User")
  public Response getSnakeById(@PathParam("ownerId") String ownerId, @PathParam("snakeId") UUID snakeId) {
    try {
      SnakeDto foundSnake = convertToDto(snakeService.getSnakeById(ownerId, snakeId));
      return Response
              .ok(foundSnake)
              .build();
       }catch(InternalServerErrorException exception) {
      return Response
              .ok("Snake not found")
              .build();
    }
  }

  @GET
  @Path("/{ownerId}")
  @RolesAllowed("User")
  public Response getSnakesByOwnerId(@PathParam("ownerId") String ownerId) {
    List<SnakeDto> snakeList = snakeService.getSnakeByOwnerId(ownerId).stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());

    return Response
            .ok(snakeList)
            .build();
  }

  @DELETE
  @Path("/{ownerId}/{snakeId}")
  @RolesAllowed("User")
  public Response deleteSnakeById(@PathParam("ownerId") String ownerId, @PathParam("snakeId") UUID snakeId) {

      if(snakeService.deleteSnakeById(ownerId, snakeId)) {
        return Response
                .ok("Snake successfully deleted")
                .build();
      } else {
        return Response
                .ok("Was not able to delete the snake")
                .build();
      }
  }

  private SnakeDto convertToDto(Snake snake) {
    return new SnakeDto(
        snake.getOwnerId(),
        snake.getSnakeId(),
        snake.getName(),
        snake.getSpecies(),
        snake.getSex(),
        snake.getBirthYear(),
        snake.getWeight(),
        snake.getSize(),
        snake.getImage());
  }

  private Snake convertFromDto(SnakeDto snakeDto) {
    return new Snake(
        snakeDto.getOwnerId(),
        snakeDto.getSnakeId(),
        snakeDto.getName(),
        snakeDto.getSpecies(),
        snakeDto.getSex(),
        snakeDto.getBirthYear(),
        snakeDto.getWeight(),
        snakeDto.getSize(),
        snakeDto.getImage());
  }
}
