package org.snakeplanner.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import org.snakeplanner.entity.Event;
import org.snakeplanner.entity.Snake;
import org.snakeplanner.entity.SnakeUser;
import org.snakeplanner.repository.dao.EventDao;

@ApplicationScoped
public class EventService {

  @Inject EventDao eventDao;
  @Inject SnakeService snakeService;
  @Inject SnakeUserService snakeUserService;

  public void saveEvent(Event event, String email) {

    if (event.getType().equals("weight")) {
      SnakeUser user = snakeUserService.getUserByEmail(email);
      if (user != null) {
        Snake snake =
            snakeService.getSnakeById(user.getId().toString(), UUID.fromString(event.getSnakeId()));
        Snake updatedSnake =
            new Snake(
                snake.getOwnerId(),
                snake.getSnakeId(),
                snake.getName(),
                snake.getSpecies(),
                snake.getSex(),
                snake.getBirthYear(),
                Float.parseFloat(event.getInfo()),
                snake.getSize(),
                snake.getImage());
        snakeService.saveSnake(updatedSnake);
      }
    }
    eventDao.update(event);
  }

  public Event getEventById(String snakeId, UUID eventId) {
    Optional<Event> eventOptional = eventDao.findById(snakeId, eventId);

    if (eventOptional.isPresent()) {
      return eventOptional.get();
    } else {
      throw new InternalServerErrorException();
    }
  }

  public List<Event> getEventsBySnakeId(String snakeId) {
    return eventDao.findBySnakeId(snakeId).all();
  }

  public boolean deleteEventById(String snakeId, UUID eventId) {
    return eventDao.delete(snakeId, eventId);
  }
}
