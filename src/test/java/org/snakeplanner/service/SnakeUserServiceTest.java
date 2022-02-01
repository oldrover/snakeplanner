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
import org.snakeplanner.dto.CreateUserDto;
import org.snakeplanner.dto.LoginDto;
import org.snakeplanner.entity.SnakeUser;
import org.snakeplanner.repository.dao.SnakeUserDao;

@QuarkusTest
public class SnakeUserServiceTest {

  @Inject SnakeUserService snakeUserService;

  @InjectMock SnakeUserDao snakeUserDao;

  @InjectMock AuthenticationService authenticationService;

  private SnakeUser snakeUser;
  private LoginDto loginDto;
  private CreateUserDto createUserDto;

  @BeforeEach
  void setUp() {
    UUID uuid = UUID.randomUUID();
    snakeUser = new SnakeUser("test@test.de", uuid, "salt", "password");
    loginDto = new LoginDto("test@test.de", "password");
    createUserDto = new CreateUserDto(null, "test@test.de", "password");

    when(snakeUserDao.findByEmailAndId("test@test.de", uuid)).thenReturn(Optional.of(snakeUser));
    when(snakeUserDao.findByEmail("test@test.de")).thenReturn(Optional.of(snakeUser));

    when(authenticationService.authenticate(loginDto)).thenReturn(snakeUser);
  }

  @Test
  public void whenSaveUser_thenShouldNotThrowError() {
    Assertions.assertDoesNotThrow(() -> snakeUserService.saveUser(snakeUser));
  }

  @Test
  public void whenLogin_thenUserShouldBeFound() {
    SnakeUser foundUser = snakeUserService.loginUser(loginDto);
    Assertions.assertNotNull(foundUser);
    Assertions.assertEquals(snakeUser, foundUser);
  }

  @Test
  public void whenFindByEmailAndId_thenUserShouldBeFound() {
    SnakeUser foundUser =
        snakeUserService.getUserByEmailAndId(snakeUser.getEmail(), snakeUser.getId());
    Assertions.assertNotNull(foundUser);
    Assertions.assertEquals(snakeUser, foundUser);
  }

  @Test
  public void whenDeleteUser_thenNotThrowException() {
    Assertions.assertDoesNotThrow(
        () -> snakeUserService.deleteUserByEmailAndId(snakeUser.getEmail(), snakeUser.getId()));
  }

  @Test
  public void whenEmailIsTaken_thenReturnFalse() {
    Assertions.assertFalse(snakeUserService.isEmailAvailable(snakeUser.getEmail()));
  }

  @Test
  public void whenGetUserByEmail_themReturnUser() {
    SnakeUser foundUser = snakeUserService.getUserByEmail(snakeUser.getEmail());
    Assertions.assertNotNull(foundUser);
    Assertions.assertEquals(snakeUser, foundUser);
  }

  @Test
  public void whenGenerateUserWithHashedPassword_thenReturnUserWithHashedPassword() {
    SnakeUser foundUser = snakeUserService.generateUserWithHashedPassword(createUserDto);

    Assertions.assertEquals(createUserDto.getEmail(), foundUser.getEmail());
    Assertions.assertNotEquals(createUserDto.getPassword(), foundUser.getPassword());
  }
}
