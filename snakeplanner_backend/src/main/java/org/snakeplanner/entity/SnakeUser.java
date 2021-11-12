package org.snakeplanner.entity;

import java.util.UUID;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;


@Entity
@PropertyStrategy(mutable = false)
public class SnakeUser {

    @PartitionKey
    private final UUID id;

    private final String name;

    private final String password;  


    public SnakeUser(UUID id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    } 

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    

    

    
}
