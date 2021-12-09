package org.snakeplanner.controller;

import java.util.UUID;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.snakeplanner.dto.CreateUserDto;
import org.snakeplanner.dto.LoginDto;
import org.snakeplanner.dto.SnakeUserDto;
import org.snakeplanner.entity.SnakeUser;
import org.snakeplanner.service.SnakeUserService;

@Path("/api/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SnakeUserResource {

  @Inject SnakeUserService snakeUserService;
  @Inject
  JsonWebToken jsonWebToken;

  private final Integer expirationTime = 900;

  @POST
  @PermitAll  
  public Response createUser(CreateUserDto createUserDto) {
    try {
      if(!snakeUserService.isEmailAvailable(createUserDto.getEmail())){
        throw new InternalServerErrorException();
      }

      createUserDto.setId(UUID.randomUUID());
      SnakeUser snakeUser = snakeUserService.generateUserWithHashedPassword(createUserDto);
      snakeUserService.saveUser(snakeUser);

      return Response
              .ok(buildCreateResponse(createUserDto))
              .build();

    } catch (InternalServerErrorException exception) {
      return Response
              .ok("Email already taken")
              .build();
    }
  }

  @POST
  @Path("login")
  @PermitAll  
  public Response loginUser(LoginDto loginDto) {
    String jwt;
    try {
      SnakeUser sn = snakeUserService.loginUser(loginDto);
      SnakeUserDto foundUser = convertToDto(sn);


      jwt = snakeUserService.generateUserJWT(foundUser.getEmail(), expirationTime);

      return Response
              .ok(foundUser)
              .header("Authentication", "Bearer " + jwt)
              .build();

    } catch (Exception exception){
      return Response
              .ok("User not found/Credentials error")
              .build();
    }

  }

  @GET
  @Path("/{id}")
  @RolesAllowed("User")
  public Response getUserById(@PathParam("id") UUID id, @Context SecurityContext ctx) {
    System.out.println("Expiration Time: " + jsonWebToken.getExpirationTime());
    try {
      SnakeUserDto foundUser = convertToDto(snakeUserService.getUserByEmailAndId(ctx.getUserPrincipal().getName(), id));
      return Response
              .ok(foundUser)
              .build();

    } catch(InternalServerErrorException exception) {
      return Response
              .ok("User not found")
              .build();

    }
  }

  @DELETE
  @Path("/{id}")
  @RolesAllowed("User")
  public Response deleteUserById(@PathParam("id") UUID id, @Context SecurityContext ctx) {
    try {
      snakeUserService.deleteUserByEmailAndId(ctx.getUserPrincipal().getName(), id);
      return Response
              .ok()
              .build();
      
      }catch(InternalServerErrorException exception) {
      return Response
              .ok("You don't have permission")
              .build();
    }
  }

  private SnakeUserDto convertToDto(SnakeUser user) {
    return new SnakeUserDto(user.getId(), user.getEmail());
  }

  /*private SnakeUser convertFromDto(CreateUserDto createUserDto) {
    return new SnakeUser( createUserDto.getEmail(),createUserDto.getId(), salt, createUserDto.getPassword());
  }*/

  private SnakeUserDto buildCreateResponse(CreateUserDto createUserDto) {
    return new SnakeUserDto(createUserDto.getId(), createUserDto.getEmail());
  }
}
