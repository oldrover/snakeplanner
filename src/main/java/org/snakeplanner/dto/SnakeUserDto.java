package org.snakeplanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class SnakeUserDto {

  @JsonIgnore private UUID id;

  @JsonProperty private String email;

  public SnakeUserDto() {}

  public SnakeUserDto(UUID id, String email) {
    this.id = id;
    this.email = email;
  }

  public UUID getId() {
    return this.id;
  }

  public String getEmail() {
    return this.email;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
