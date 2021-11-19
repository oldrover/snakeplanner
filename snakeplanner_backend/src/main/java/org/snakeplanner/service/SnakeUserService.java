package org.snakeplanner.service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import org.snakeplanner.dao.SnakeUserDao;
import org.snakeplanner.entity.SnakeUser;
import org.snakeplanner.security.GenerateJWT;

@ApplicationScoped
public class SnakeUserService {

  @Inject SnakeUserDao snakeUserDao;
  @Inject HashService hashService;
  @Inject GenerateJWT generateJWT;

  public void saveUser(SnakeUser snakeUser) {

    if (isEmailAvailable(snakeUser.getEmail())) {
      // snakeUser.generatePassword()
      snakeUserDao.update(snakeUser);
    } else {
      throw new InternalServerErrorException("Email already taken");
    }
  }

  public SnakeUser getUserById(UUID id) {
    Optional<SnakeUser> optionalUser = snakeUserDao.findById(id);

    if (optionalUser.isPresent()) {
      return optionalUser.get();
    } else {
      throw new NotFoundException();
    }
  }

  public void deleteUserById(UUID id) {
    snakeUserDao.delete(id);
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
