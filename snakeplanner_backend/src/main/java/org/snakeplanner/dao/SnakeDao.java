package org.snakeplanner.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;
import org.snakeplanner.entity.Snake;

import java.util.Optional;
import java.util.UUID;

@Dao
public interface SnakeDao {

    @Update
    void update(Snake snake);

    @Select
    Optional<Snake> findById(String ownerId, UUID id);

    @Select(customWhereClause = "owner_id = :ownerId")
    PagingIterable<Snake> findByOwnerId(String ownerId);

    @Delete(entityClass = Snake.class)
    void delete(String ownerId, UUID id);
    
}
