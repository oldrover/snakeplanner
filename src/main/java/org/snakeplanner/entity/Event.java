package org.snakeplanner.entity;

import com.datastax.oss.driver.api.mapper.annotations.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@PropertyStrategy(mutable = false)
public class Event {

  @PartitionKey private final String snakeId;

  @ClusteringColumn private final UUID eventId;

  private final String type;

  private final LocalDate date;

  private final String info;

  public Event(String snakeId, UUID eventId, String type, LocalDate date, String info) {
    this.snakeId = snakeId;
    this.eventId = eventId;
    this.type = type;
    this.date = date;
    this.info = info;
  }

  public UUID getEventId() {
    return this.eventId;
  }

  public String getSnakeId() {
    return this.snakeId;
  }

  public String getType() {
    return this.type;
  }

  public LocalDate getDate() {
    return this.date;
  }

  public String getInfo() {
    return this.info;
  }
}
