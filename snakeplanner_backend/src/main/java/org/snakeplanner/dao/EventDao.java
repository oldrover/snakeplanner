package org.snakeplanner.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;
import org.snakeplanner.entity.Event;

import java.util.Optional;
import java.util.UUID;

@Dao
public interface EventDao {

    @Update
    void update(Event event);

    @Select
    Optional<Event> findById(UUID snakeId, UUID id);

    @Select(customWhereClause = "snake_id = :snakeId")
    PagingIterable<Event> findBySnakeId(UUID snakeId);

    @Delete(entityClass = Event.class)
    void delete(UUID snakeId, UUID id);
}
