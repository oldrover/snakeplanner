package org.snakeplanner.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.snakeplanner.dto.EventDto;
import org.snakeplanner.entity.Event;
import org.snakeplanner.service.EventService;

@Path("/api/events")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EventResource {

  @Inject EventService eventService;

  @POST
  @RolesAllowed("User")
  public Response createEvent(EventDto eventDto) {
    try {
      eventDto.setEventId(UUID.randomUUID());
      eventService.saveEvent(convertFromDto(eventDto));
      return Response
              .ok("Event created")
              .build();

    }catch (Exception exception) {
      return Response
              .ok("Was not able to create event")
              .build();
    }
  }

  @GET
  @Path("{snakeId}/{eventId}")
  @RolesAllowed("User")
  public Response getEventById(@PathParam("snakeId") String snakeId, @PathParam("eventId") UUID eventId) {
    try {
      EventDto foundEvent =  convertToDto(eventService.getEventById(snakeId, eventId));
      return Response
              .ok(foundEvent)
              .build();
    }catch (InternalServerErrorException exception) {
      return Response
              .ok("Event not found")
              .build();
    }
  }

  @GET
  @Path("{snakeId}")
  @RolesAllowed("User")
  public Response getEventsBySnakeId(@PathParam("snakeId") String snakeId) {
    List<EventDto> eventList = eventService.getEventsBySnakeId(snakeId).stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());

    return Response
            .ok(eventList)
            .build();
  }

  @DELETE
  @Path("/{snakeId}/{eventId}")
  @RolesAllowed("User")
  public Response deleteEventById(
      @PathParam("snakeId") String snakeId, @PathParam("eventId") UUID eventId) {

    if(eventService.deleteEventById(snakeId, eventId)) {
      return Response
              .ok("Event successfully deleted")
              .build();
    }else {
      return Response
              .ok("Was not able to delete the event")
              .build();
    }
  }

  private Event convertFromDto(EventDto eventDto) {
    return new Event(
            eventDto.getSnakeId(),
            eventDto.getEventId(),
            eventDto.getType(),
            eventDto.getDate(),
            eventDto.getInfo());
  }

  private EventDto convertToDto(Event event) {
    return new EventDto(
        event.getSnakeId(), event.getEventId(), event.getType(), event.getDate(), event.getInfo());
  }
}
