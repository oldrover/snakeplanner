package org.snakeplanner.service;

import org.snakeplanner.dao.EventDao;
import org.snakeplanner.entity.Event;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class EventService {

    @Inject
    EventDao eventDao;

    public void saveEvent(Event event) {
        eventDao.update(event);
    }

    public Event getEventById(UUID snakeId, UUID id) {
        Optional<Event> eventOptional = eventDao.findById(snakeId, id);

        if(eventOptional.isPresent()) {
            return eventOptional.get();
        }else {
            throw new NotFoundException();
        }
    }

    public List<Event> getEventsBySnakeId(UUID snakeId) {
        return eventDao.findBySnakeId(snakeId).all();
    }

    public void deleteEventById(UUID snakeId, UUID id) {
        eventDao.delete( snakeId, id);
    }

}
