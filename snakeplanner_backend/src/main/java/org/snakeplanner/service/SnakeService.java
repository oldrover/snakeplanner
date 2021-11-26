package org.snakeplanner.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;

import org.snakeplanner.repository.dao.SnakeDao;
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
      throw new InternalServerErrorException();
    }
  }

  public List<Snake> getSnakesByOwnerId(String ownerId) {
    return snakeDao.findByOwnerId(ownerId).all();
  }

  public boolean deleteSnakeById(String ownerId, UUID snakeId) {
    return snakeDao.delete(ownerId, snakeId);
  }
}
