package org.snakeplanner.repository.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.snakeplanner.entity.Snake;

@Dao
public interface SnakeDao {

  @Update
  void update(Snake snake);

  @Select
  Optional<Snake> findById(String ownerId, UUID snakeId);

  @Select(customWhereClause = "owner_id = :ownerId")
  PagingIterable<Snake> findByOwnerId(String ownerId);

  @Delete(entityClass = Snake.class, ifExists = true)
  boolean delete(String ownerId, UUID snakeId);
}
