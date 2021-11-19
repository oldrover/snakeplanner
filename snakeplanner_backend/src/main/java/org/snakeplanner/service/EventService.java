package org.snakeplanner.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import org.snakeplanner.dao.EventDao;
import org.snakeplanner.entity.Event;

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
      throw new NotFoundException();
    }
  }

  public List<Event> getEventsBySnakeId(String snakeId) {
    return eventDao.findBySnakeId(snakeId).all();
  }

  public void deleteEventById(String snakeId, UUID eventId) {
    eventDao.delete(snakeId, eventId);
  }
}
