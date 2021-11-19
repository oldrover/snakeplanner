package org.snakeplanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class SnakeDto {

  @JsonProperty private String ownerId;

  @JsonIgnore private UUID snakeId;

  @JsonProperty private String name;

  @JsonProperty private String species;

  @JsonProperty private String sex;

  @JsonProperty private Integer birthYear;

  @JsonProperty private Float weight;

  @JsonProperty private Float size;

  @JsonProperty private String image;

  public SnakeDto() {}

  public SnakeDto(
      String ownerId,
      UUID snakeId,
      String name,
      String species,
      String sex,
      Integer birthYear,
      Float weight,
      Float size,
      String image) {

    this.ownerId = ownerId;
    this.snakeId = snakeId;
    this.name = name;
    this.species = species;
    this.sex = sex;
    this.birthYear = birthYear;
    this.weight = weight;
    this.size = size;
    this.image = image;
  }

  public UUID getSnakeId() {
    return this.snakeId;
  }

  public void setSnakeId(UUID snakeId) {
    this.snakeId = snakeId;
  }

  public String getOwnerId() {
    return this.ownerId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSpecies() {
    return this.species;
  }

  public void setSpecies(String species) {
    this.species = species;
  }

  public String getSex() {
    return this.sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public Integer getBirthYear() {
    return this.birthYear;
  }

  public void setBirthYear(Integer birthYear) {
    this.birthYear = birthYear;
  }

  public Float getWeight() {
    return this.weight;
  }

  public void setWeight(Float weight) {
    this.weight = weight;
  }

  public Float getSize() {
    return this.size;
  }

  public void setSize(Float size) {
    this.size = size;
  }

  public String getImage() {
    return this.image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}
