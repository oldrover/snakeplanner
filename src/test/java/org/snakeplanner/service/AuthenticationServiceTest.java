package org.snakeplanner.service;

import static org.mockito.Mockito.when;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.snakeplanner.dto.LoginDto;
import org.snakeplanner.entity.SnakeUser;
import org.snakeplanner.repository.dao.SnakeUserDao;

@QuarkusTest
public class AuthenticationServiceTest {

  @Inject AuthenticationService authenticationService;

  @InjectMock HashService hashService;

  @InjectMock SnakeUserDao snakeUserDao;

  private LoginDto loginDto;
  private SnakeUser snakeUser;

  @BeforeEach
  void setUp() {
    UUID uuid = UUID.randomUUID();
    loginDto = new LoginDto("test@test.de", "password");
    snakeUser = new SnakeUser("test@test.de", uuid, "salt", "isHashed");
    when(snakeUserDao.findByEmail("test@test.de")).thenReturn(Optional.of(snakeUser));
    when(hashService.getHashedValue("password", "salt")).thenReturn("isHashed");
  }

  @Test
  public void testAuthenticate() {
    SnakeUser returnedUser = authenticationService.authenticate(loginDto);

    Assertions.assertNotNull(returnedUser);
    Assertions.assertEquals(loginDto.getEmail(), returnedUser.getEmail());
    Assertions.assertEquals("salt", returnedUser.getSalt());
    Assertions.assertEquals("isHashed", returnedUser.getPassword());
  }
}
