package org.snakeplanner.entity;

import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;
import java.util.UUID;

@Entity
@PropertyStrategy(mutable = false)
public class SnakeUser {

  @PartitionKey private final String email;

  @ClusteringColumn private final UUID id;

  private final String salt;

  private final String password;

  public SnakeUser(String email, UUID id, String salt, String password) {
    this.email = email;
    this.id = id;
    this.salt = salt;
    this.password = password;
  }

  public UUID getId() {
    return this.id;
  }

  public String getEmail() {
    return this.email;
  }

  public String getSalt() {
    return salt;
  }

  public String getPassword() {
    return this.password;
  }
}
