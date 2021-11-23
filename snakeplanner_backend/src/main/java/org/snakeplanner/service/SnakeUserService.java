package org.snakeplanner.service;

import java.security.SecureRandom;
import java.util.*;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import org.snakeplanner.dao.SnakeUserDao;
import org.snakeplanner.dto.LoginDto;
import org.snakeplanner.entity.SnakeUser;
import org.snakeplanner.security.GenerateJWT;

@ApplicationScoped
public class SnakeUserService {

  @Inject SnakeUserDao snakeUserDao;
  @Inject HashService hashService;
  @Inject GenerateJWT generateJWT;

  public void saveUser(SnakeUser snakeUser) {

    if (isEmailAvailable(snakeUser.getEmail())) {
      //TODO: generate the Password
      // snakeUser.generatePassword()
      snakeUserDao.update(snakeUser);
    } else {
      throw new InternalServerErrorException();
    }
  }

  public SnakeUser loginUser(LoginDto loginDto) {
    String pwd = loginDto.getPassword();
    Optional<SnakeUser> optionalUser = snakeUserDao.findByEmail(loginDto.getEmail());

    if(optionalUser.isEmpty()) {
      throw new InternalServerErrorException();
    }
    SnakeUser returnedUser = optionalUser.get();
    if(!returnedUser.getPassword().equals(pwd)) {
      throw new InternalServerErrorException();
    }
    return returnedUser;
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

  private Boolean isEmailAvailable(String email) {
    return snakeUserDao.findByEmail(email).isEmpty();
  }

  private String generatePassword(String password) {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    String encodedSalt = Base64.getEncoder().encodeToString(salt);
    return hashService.getHashedValue(password, encodedSalt);
  }

  public String generateUserJWT(String email) {
    return generateJWT.generate(email);
  }
}
