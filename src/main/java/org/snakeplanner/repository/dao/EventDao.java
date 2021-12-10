package org.snakeplanner.repository.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;
import java.util.Optional;
import java.util.UUID;
import org.snakeplanner.entity.Event;

@Dao
public interface EventDao {

  @Update
  void update(Event event);

  @Select
  Optional<Event> findById(String snakeId, UUID eventId);

  @Select(customWhereClause = "snake_id = :snakeId")
  PagingIterable<Event> findBySnakeId(String snakeId);

  @Delete(entityClass = Event.class, ifExists = true)
  boolean delete(String snakeId, UUID eventId);
}
