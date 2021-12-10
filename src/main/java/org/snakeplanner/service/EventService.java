package org.snakeplanner.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import org.snakeplanner.entity.Event;
import org.snakeplanner.repository.dao.EventDao;

@ApplicationScoped
public class EventService {

  @Inject EventDao eventDao;

  public void saveEvent(Event event) {
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
