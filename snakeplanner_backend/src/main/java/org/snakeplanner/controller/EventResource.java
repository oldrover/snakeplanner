package org.snakeplanner.controller;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.snakeplanner.dto.EventDto;
import org.snakeplanner.entity.Event;
import org.snakeplanner.service.EventService;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/events")
public class EventResource {

    @Inject
    EventService eventService;

    @POST
    public void addEvent(EventDto eventDto) {
        eventService.saveEvent(convertFromDto(eventDto));
    }

    @GET
    @Path("{snakeId}/{id}")
    public EventDto getEventById(@PathParam("snakeId") String snakeId, @PathParam("id") UUID id) {
        return convertToDto(eventService.getEventById(snakeId, id));
    }

    @GET
    @Path("{snakeId}")
    public List<EventDto> getEventsBySnakeId(@PathParam("snakeId") String snakeId) {
        return eventService.getEventsBySnakeId(snakeId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @DELETE
    @Path("/{snakeId}/{eventId}")
    public void deleteEventById(@PathParam("snakeId") String snakeId, @PathParam("eventId") UUID eventId) {
        eventService.deleteEventById(snakeId, eventId);
    }


    private Event convertFromDto(EventDto eventDto) {
        return new Event(eventDto.getSnakeId(), UUID.randomUUID(), eventDto.getType(), eventDto.getDate(), eventDto.getInfo());
    }
    private EventDto convertToDto(Event event) {
        return new EventDto(event.getSnakeId(), event.getEventId(), event.getType(), event.getDate(), event.getInfo());
    }


}
