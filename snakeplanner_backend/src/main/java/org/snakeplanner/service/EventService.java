package org.snakeplanner.service;

import com.datastax.oss.driver.api.core.PagingIterable;
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

    public Event getEventById(String snakeId, UUID eventId) {
        Optional<Event> eventOptional = eventDao.findById(snakeId, eventId);

        if(eventOptional.isPresent()) {
            return eventOptional.get();
        }else {
            throw new NotFoundException();
        }
    }

    public List<Event> getEventsBySnakeId(String snakeId) {
        return eventDao.findBySnakeId(snakeId).all();

    }

    public void deleteEventById(String snakeId, UUID eventId) {
        eventDao.delete( snakeId, eventId);
    }

}
