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

    private final String email;

    private final String password;  


    public SnakeUser(UUID id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    } 

    public UUID getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    

    

    
}
