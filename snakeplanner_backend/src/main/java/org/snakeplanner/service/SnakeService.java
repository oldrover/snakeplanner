package org.snakeplanner.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import org.snakeplanner.dao.SnakeDao;
import org.snakeplanner.entity.Snake;

@ApplicationScoped
public class SnakeService {

  @Inject SnakeDao snakeDao;

  public void saveSnake(Snake snake) {
    snakeDao.update(snake);
  }

  public Snake getSnakeById(String ownerId, UUID snakeId) {
    Optional<Snake> snakeOptional = snakeDao.findById(ownerId, snakeId);

    if (snakeOptional.isPresent()) {
      return snakeOptional.get();
    } else {
      throw new NotFoundException();
    }
  }

  public List<Snake> getSnakeByOwnerId(String ownerId) {
    return snakeDao.findByOwnerId(ownerId).all();
  }

  public void deleteSnakeById(String ownerId, UUID snakeId) {
    snakeDao.delete(ownerId, snakeId);
  }
}
