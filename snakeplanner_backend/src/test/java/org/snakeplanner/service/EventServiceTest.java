package org.snakeplanner.service;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.snakeplanner.entity.Event;
import org.snakeplanner.entity.Snake;
import org.snakeplanner.repository.dao.EventDao;
import org.snakeplanner.service.EventService;

import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@QuarkusTest
public class EventServiceTest {

    @Inject
    EventService eventService;

    @InjectMock
    EventDao eventDao;

    private Event event;

    @BeforeEach
    void setUp() {
        UUID snakeId = UUID.randomUUID();
        UUID uuid = UUID.randomUUID();
        event = new Event(snakeId.toString(), uuid, "feed",
                LocalDate.of(2021,10,20),"jumper");

        MockPIEvent mockPIEvent = new MockPIEvent(event);
        when(eventDao.findById(snakeId.toString(), uuid)).thenReturn(Optional.of(event));
        when(eventDao.findById(snakeId.toString(), null)).thenReturn(Optional.empty());
        when(eventDao.findBySnakeId(snakeId.toString())).thenReturn(mockPIEvent);
    }

    @Test
    public void whenFindEventById_thenReturnEvent() {
        Event foundEvent = eventService.getEventById(event.getSnakeId(), event.getEventId());
        Assertions.assertEquals(event, foundEvent);
    }

    @Test
    public void whenFindEventByWrongId_thenThrowError() {
        Assertions.assertThrows(InternalServerErrorException.class,() -> eventService.getEventById(event.getSnakeId(),null));
    }

    @Test
    public void whenFindEventsBySnakeId_thenReturnEvents() {
        List<Event> foundEvent = eventService.getEventsBySnakeId(event.getSnakeId());
    }

    @Test
    public void whenDeleteEvent_thenNotThrowException() {
        Assertions.assertDoesNotThrow(() -> eventService.deleteEventById(event.getSnakeId(), event.getEventId()));

    }
}
