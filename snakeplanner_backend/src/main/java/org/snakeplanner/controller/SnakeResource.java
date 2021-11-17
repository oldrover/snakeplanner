package org.snakeplanner.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.snakeplanner.dto.SnakeDto;
import org.snakeplanner.entity.Snake;
import org.snakeplanner.service.SnakeService;

@Path("/snakes")
public class SnakeResource {

    @Inject SnakeService snakeService;

    @POST
    public void addSnake(SnakeDto snakeDto) {
        snakeService.saveSnake(convertFromDto(snakeDto));
    }

    @GET
    @Path("/{ownerId}/{id}")
    public SnakeDto getSnakeById(@PathParam("ownerId") String ownerId, @PathParam("snakeId") UUID snakeId) {
        return convertToDto(snakeService.getSnakeById(ownerId, snakeId));
    }

    @GET
    @Path("/{ownerId}")
    public List<SnakeDto> getSnakesByOwnerId(@PathParam("ownerId") String ownerId) {
        return snakeService.getSnakeByOwnerId(ownerId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @DELETE
    @Path("/{ownerId}/{id}")
    public void deleteSnakeById(@PathParam("ownerId") String ownerId, @PathParam("snakeId") UUID snakeId) {
        snakeService.deleteSnakeById(ownerId, snakeId);
    }

    private  SnakeDto convertToDto(Snake snake) {
        return new SnakeDto(snake.getOwnerId(), snake.getSnakeId(), snake.getName(), snake.getSpecies(), snake.getSex(),
                snake.getBirthYear(), snake.getWeight(), snake.getSize(), snake.getImage());

    }

    private  Snake convertFromDto(SnakeDto snakeDto) {
        return new Snake(snakeDto.getOwnerId(), UUID.randomUUID(), snakeDto.getName(), snakeDto.getSpecies(), snakeDto.getSex(),
                snakeDto.getBirthYear(), snakeDto.getWeight(), snakeDto.getSize(), snakeDto.getImage());                
    }



    

    
    
}
