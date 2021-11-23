package org.snakeplanner.service;

import java.security.SecureRandom;
import java.util.*;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import org.snakeplanner.dao.SnakeUserDao;
import org.snakeplanner.dto.CreateUserDto;
import org.snakeplanner.dto.LoginDto;
import org.snakeplanner.dto.SnakeUserDto;
import org.snakeplanner.entity.SnakeUser;
import org.snakeplanner.security.GenerateJWT;

@ApplicationScoped
public class SnakeUserService {

  @Inject SnakeUserDao snakeUserDao;
  @Inject HashService hashService;
  @Inject GenerateJWT generateJWT;
  @Inject AuthenticationService authenticationService;

  public void saveUser(SnakeUser snakeUser) {
    snakeUserDao.update(snakeUser);
  }

  public SnakeUser loginUser(LoginDto loginDto) {
    SnakeUser snakeUser = authenticationService.authenticate(loginDto);

    if(snakeUser == null){
      throw new InternalServerErrorException();
    }
    return snakeUser;
  }

  public SnakeUser getUserByEmailAndId(String email, UUID id) {

    Optional<SnakeUser> optionalUser = snakeUserDao.findByEmailAndId(email, id);

    if (optionalUser.isPresent()) {
      SnakeUser returnedUser = optionalUser.get();
      if(returnedUser.getEmail().equals(email)) {
        return returnedUser;
      } else {
        throw new InternalServerErrorException();
      }
    } else {
      throw new NotFoundException();
    }
  }

  public void deleteUserByEmailAndId(String email, UUID id) {
    SnakeUser returnedUser = getUserByEmailAndId(email, id);
    snakeUserDao.deleteByEmailAndId(returnedUser.getEmail(), returnedUser.getId());
  }

  public Boolean isEmailAvailable(String email) {
    return snakeUserDao.findByEmail(email).isEmpty();
  }

  public SnakeUser generateUserWithHashedPassword(CreateUserDto createUserDto) {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    String encodedSalt = Base64.getEncoder().encodeToString(salt);
    String hashedPassword = hashService.getHashedValue(createUserDto.getPassword(), encodedSalt);
    return new SnakeUser(createUserDto.getEmail(),createUserDto.getId(), encodedSalt, hashedPassword);
  }

  public String generateUserJWT(String email) {
    return generateJWT.generate(email);
  }
}
