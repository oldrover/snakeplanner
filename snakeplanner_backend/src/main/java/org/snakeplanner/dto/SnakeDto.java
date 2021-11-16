package org.snakeplanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class SnakeDto {

    @JsonIgnore
    private UUID id;

    @JsonProperty
    private UUID ownerId;

    @JsonProperty
    private String name;

    @JsonProperty
    private String species;

    @JsonProperty
    private String sex;

    @JsonProperty
    private Integer birthYear;

    @JsonProperty
    private Float weight;

    @JsonProperty
    private Float size;

    @JsonProperty
    private String image;


    public SnakeDto() {}

    public SnakeDto(UUID id, UUID ownerId, String name, String species, String sex,
                   Integer birthYear, Float weight, Float size, String image) {
      
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.species = species;
        this.sex = sex;
        this.birthYear = birthYear;
        this.weight = weight;
        this.size = size;
        this.image = image;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
