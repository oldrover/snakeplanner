package org.snakeplanner.service;

import org.snakeplanner.dao.SnakeDao;
import org.snakeplanner.entity.Snake;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class SnakeService {

    @Inject SnakeDao snakeDao;

    public void saveSnake(Snake snake) {
        snakeDao.update(snake);
    }

    public Snake getSnakeById(UUID ownerId,UUID id) {
        Optional<Snake> snakeOptional = snakeDao.findById(ownerId, id);

        if(snakeOptional.isPresent()) {
            return snakeOptional.get();
        }else {
            throw new NotFoundException();
        }
    }

    public List<Snake> getSnakeByOwnerId(UUID ownerId) {
        return snakeDao.findByOwnerId(ownerId).all();
    }

    public void deleteSnakeById(UUID ownerId, UUID id) {        
        snakeDao.delete( ownerId, id);
    }
    
}
