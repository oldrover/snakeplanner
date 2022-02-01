package org.snakeplanner.service;

import static org.mockito.Mockito.when;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.snakeplanner.entity.Event;
import org.snakeplanner.entity.Snake;
import org.snakeplanner.entity.SnakeUser;
import org.snakeplanner.repository.dao.EventDao;

@QuarkusTest
public class EventServiceTest {

  @Inject EventService eventService;

  @InjectMock SnakeUserService snakeUserService;
  @InjectMock SnakeService snakeService;
  @InjectMock EventDao eventDao;

  private Event event;

  @BeforeEach
  void setUp() {
    UUID snakeId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    UUID uuid = UUID.randomUUID();
    event = new Event(snakeId.toString(), uuid, "weight", LocalDate.of(2021, 10, 20), "400");

    MockPIEvent mockPIEvent = new MockPIEvent(event);
    when(eventDao.findById(snakeId.toString(), uuid)).thenReturn(Optional.of(event));
    when(eventDao.findById(snakeId.toString(), null)).thenReturn(Optional.empty());
    when(eventDao.findBySnakeId(snakeId.toString())).thenReturn(mockPIEvent);
    when(snakeUserService.getUserByEmail("test@test.de"))
        .thenReturn(new SnakeUser("test@test.de", userId, null, null));
    when(snakeService.getSnakeById(userId.toString(), snakeId))
        .thenReturn(new Snake(userId.toString(), snakeId, "", "", "", 0, (float) 0, (float) 0, ""));
  }

  @Test
  public void whenSaveEvent_thenShouldNotThrowError() {
    Assertions.assertDoesNotThrow(() -> eventService.saveEvent(event, "test@test.de"));
  }

  @Test
  public void whenFindEventById_thenReturnEvent() {
    Event foundEvent = eventService.getEventById(event.getSnakeId(), event.getEventId());
    Assertions.assertEquals(event, foundEvent);
  }

  @Test
  public void whenFindEventByWrongId_thenThrowError() {
    Assertions.assertThrows(
        InternalServerErrorException.class,
        () -> eventService.getEventById(event.getSnakeId(), null));
  }

  @Test
  public void whenFindEventsBySnakeId_thenReturnEvents() {
    List<Event> foundEvents = eventService.getEventsBySnakeId(event.getSnakeId());
    Assertions.assertNotNull(foundEvents);
    Assertions.assertEquals(foundEvents.get(0), event);
  }

  @Test
  public void whenDeleteEvent_thenNotThrowException() {
    Assertions.assertDoesNotThrow(
        () -> eventService.deleteEventById(event.getSnakeId(), event.getEventId()));
  }
}
