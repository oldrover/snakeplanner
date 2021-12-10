package org.snakeplanner.service;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.snakeplanner.dto.LoginDto;
import org.snakeplanner.entity.SnakeUser;
import org.snakeplanner.repository.dao.SnakeUserDao;

@ApplicationScoped
public class AuthenticationService {

  @Inject SnakeUserDao snakeUserDao;
  @Inject HashService hashService;

  public SnakeUser authenticate(LoginDto loginDto) {
    String email = loginDto.getEmail();
    String password = loginDto.getPassword();

    Optional<SnakeUser> optionalSnakeUser = snakeUserDao.findByEmail(email);
    if (optionalSnakeUser.isPresent()) {
      SnakeUser snakeUser = optionalSnakeUser.get();
      String encodedSalt = snakeUser.getSalt();
      String hashedPassword = hashService.getHashedValue(password, encodedSalt);
      if (snakeUser.getPassword().equals(hashedPassword)) {
        return snakeUser;
      }
    }
    return null;
  }
}
