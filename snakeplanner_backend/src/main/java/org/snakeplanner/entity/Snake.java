package org.snakeplanner.entity;

import java.util.UUID;

import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;

@Entity
@PropertyStrategy(mutable = false)
public class Snake {

    @PartitionKey
    private final String ownerId;

    @ClusteringColumn
    private final UUID snakeId;

    private final String name;

    private final String species;

    private final String sex;

    private final Integer birthYear;

    private final Float weight;

    private final Float size;

    private final String image;

    public Snake(String ownerId, UUID snakeId, String name, String species, String sex, Integer birthYear, Float weight, Float size, String image) {
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

    public String getOwnerId() {
        return ownerId;
    }

    public UUID getSnakeId() {
        return this.snakeId;
    }

    public String getName() {
        return this.name;
    }

    public String getSpecies() {
        return this.species;
    }

    public String getSex() {
        return this.sex;
    }

    public Integer getBirthYear() {
        return this.birthYear;
    }

    public Float getWeight() {
        return this.weight;
    }

    public Float getSize() {
        return this.size;
    }

    public String getImage() {
        return this.image;
    }

}