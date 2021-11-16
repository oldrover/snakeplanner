package org.snakeplanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.json.bind.annotation.JsonbDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class EventDto {

    @JsonIgnore
    private UUID id;

    private UUID snakeId;

    private String type;

    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate date;

    private  String info;

    public EventDto() {
    }

    public EventDto(UUID id, UUID snakeId, String type, LocalDate date, String info) {
        this.id = id;
        this.snakeId = snakeId;
        this.type = type;
        this.date = date;
        this.info = info;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getSnakeId() {
        return snakeId;
    }

    public void setSnakeId(UUID snakeId) {
        this.snakeId = snakeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
