package org.snakeplanner.controller;

import java.util.UUID;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import org.eclipse.microprofile.jwt.JsonWebToken;
import org.snakeplanner.dto.CreateUserDto;
import org.snakeplanner.dto.LoginDto;
import org.snakeplanner.dto.SnakeUserDto;
import org.snakeplanner.entity.SnakeUser;
import org.snakeplanner.service.SnakeUserService;

@Path("/api/users")
public class SnakeUserResource {

  @Inject SnakeUserService snakeUserService;
  @Inject JsonWebToken jwt;

  @POST
  @PermitAll
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createUser(CreateUserDto createUserDto) {

    try {
      createUserDto.setId(UUID.randomUUID());
      snakeUserService.saveUser(convertFromDto(createUserDto));
      return Response.ok(buildCreateResponse(createUserDto)).build();
    } catch (InternalServerErrorException exception) {
      return Response.status(400).header("Reason", "email already taken").build();
    }

  }

  @POST
  @Path("login")
  @PermitAll
  @Consumes(MediaType.APPLICATION_JSON)
  public Response loginUser(LoginDto loginDto) {
    String jwt = snakeUserService.generateUserJWT(loginDto.getEmail());

    return Response.ok().header("Authorization", "Bearer "+ jwt).build();
  }

  @GET
  @Path("test")
  @RolesAllowed({"User", "Admin"})
  @Produces(MediaType.TEXT_PLAIN)
  public String helloRolesAllowed() {
    return jwt.getName();
  }

  @GET
  @Path("/{id}")
  //@RolesAllowed("User")
  public SnakeUserDto getUserById(@PathParam("id") UUID id) {
    return convertToDto(snakeUserService.getUserById(id));
  }

  @DELETE
  @Path("/{id}")
  //@RolesAllowed("User")
  public void deleteUserById(@PathParam("id") UUID id) {
    snakeUserService.deleteUserById(id);
  }


  private SnakeUserDto convertToDto(SnakeUser user) {
    return new SnakeUserDto(user.getId(), user.getEmail());
  }

  private SnakeUser convertFromDto(CreateUserDto createUserDto) {
    return new SnakeUser(createUserDto.getId(), createUserDto.getEmail(), createUserDto.getPassword());
  }

  private SnakeUserDto buildCreateResponse(CreateUserDto createUserDto) {
    return new SnakeUserDto(createUserDto.getId(), createUserDto.getEmail());
  }
}
