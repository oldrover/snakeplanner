package org.snakeplanner.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;

@Entity
@PropertyStrategy(mutable = false)
public class Event {

    @ClusteringColumn
    private final UUID id;
    @PartitionKey
    private final UUID snakeId;

    private final String type;

    private final LocalDate date;

    private final String info;

    public Event(UUID id, UUID snakeId, String type, LocalDate date, String info) {
        this.id = id;
        this.snakeId = snakeId;
        this.type = type;
        this.date = date;
        this.info = info;
    }

    public UUID getId() {
        return id;
    }

    public UUID getSnakeId() {
        return snakeId;
    }

    public String getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getInfo() {
        return info;
    }
}
