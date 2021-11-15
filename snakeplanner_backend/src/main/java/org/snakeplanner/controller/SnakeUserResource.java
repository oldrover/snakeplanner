package org.snakeplanner.controller;

import java.util.UUID;

import javax.inject.Inject;

import javax.ws.rs.*;

import org.snakeplanner.dto.SnakeUserDto;
import org.snakeplanner.entity.SnakeUser;
import org.snakeplanner.service.SnakeUserService;


@Path("/users")
public class SnakeUserResource {

    @Inject SnakeUserService snakeUserService;

    @POST    
    public void add(SnakeUserDto user) {
        user.setId(UUID.randomUUID().toString());
        snakeUserService.saveUser(convertFromDto(user));
    }

    @GET
    @Path("/{id}")        
    public SnakeUserDto getById(@PathParam("id") UUID id) { 
        return convertToDto(snakeUserService.getUserById(id));
    }

    @DELETE
    @Path("/{id}")
    public void deleteById(@PathParam("id") UUID id) {
        snakeUserService.deleteUserById(id);
    }

    private SnakeUserDto convertToDto(SnakeUser user) {
        return new SnakeUserDto(user.getId().toString(), user.getEmail(), user.getPassword());
      }
    
      private SnakeUser convertFromDto(SnakeUserDto snakeUserDto) {
        return new SnakeUser(UUID.fromString(snakeUserDto.getId()), snakeUserDto.getEmail(), snakeUserDto.getPassword());
      }
}