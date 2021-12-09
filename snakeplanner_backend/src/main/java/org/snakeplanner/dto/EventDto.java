package org.snakeplanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.UUID;
import javax.json.bind.annotation.JsonbDateFormat;

public class EventDto {

  @JsonProperty private String snakeId;

  @JsonIgnore private UUID eventId;

  @JsonProperty private String type;

  @JsonProperty
  @JsonbDateFormat("yyyy/MM/dd")
  private LocalDate date;

  @JsonProperty private String info;

  public EventDto() {}

  public EventDto(String snakeId, UUID eventId, String type, LocalDate date, String info) {
    this.snakeId = snakeId;
    this.eventId = eventId;
    this.type = type;
    this.date = date;
    this.info = info;
  }

  public UUID getEventId() {
    return this.eventId;
  }

  public void setEventId(UUID eventId) {
    this.eventId = eventId;
  }

  public String getSnakeId() {
    return this.snakeId;
  }

  public void setSnakeId(String snakeId) {
    this.snakeId = snakeId;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public LocalDate getDate() {
    return this.date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getInfo() {
    return this.info;
  }

  public void setInfo(String info) {
    this.info = info;
  }
}
