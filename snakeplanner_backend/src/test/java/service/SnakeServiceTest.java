package service;

import com.datastax.oss.driver.api.core.PagingIterable;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.snakeplanner.entity.Snake;
import org.snakeplanner.repository.dao.SnakeDao;
import org.snakeplanner.service.SnakeService;

import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import java.util.*;

import static org.mockito.Mockito.when;

@QuarkusTest
public class SnakeServiceTest {

    @Inject
    SnakeService snakeService;

    @InjectMock
    SnakeDao snakeDao;

    private Snake snake;


    @BeforeEach
    void setUp() {
        UUID ownerId = UUID.randomUUID();
        UUID uuid = UUID.randomUUID();
        snake = new Snake(ownerId.toString(), uuid,"TestName", "TestSpecies",
                "male",2010,(float)500,(float)150,"TestImageSrc");

        when(snakeDao.findById(ownerId.toString(), uuid)).thenReturn(Optional.of(snake));
        when(snakeDao.findById(ownerId.toString(), null)).thenReturn(Optional.empty());
        //when(snakeDao.findByOwnerId(ownerId.toString())).thenReturn();

    }

    @Test
    public void whenFindSnakeById_thenReturnSnake() {
        Snake foundSnake = snakeService.getSnakeById(snake.getOwnerId(), snake.getSnakeId());
        Assertions.assertEquals(snake, foundSnake);
    }

    @Test
    public void whenFindSnakeByWrongId_thenThrowError() {
        Assertions.assertThrows(InternalServerErrorException.class,() -> snakeService.getSnakeById(snake.getOwnerId(),null));
    }

    @Disabled
    @Test
    public void whenFindSnakesByOwnerId_thenReturnSnakes() {
        List<Snake> foundSnake = snakeService.getSnakesByOwnerId(snake.getOwnerId());
        Assertions.assertEquals(snake, foundSnake.get(0));
    }

    @Test
    public void whenDeleteSnake_thenNotThrowException() {
        Assertions.assertDoesNotThrow(() -> snakeService.deleteSnakeById(snake.getOwnerId(), snake.getSnakeId()));

    }


}
