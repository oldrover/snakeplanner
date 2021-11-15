package org.snakeplanner.dao;

import java.util.UUID;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Update;

import org.snakeplanner.entity.SnakeUser;

import com.datastax.oss.driver.api.mapper.annotations.Select;

@Dao
public interface SnakeUserDao {

    @Update
    void update(SnakeUser snakeUser);

    @Select
    SnakeUser findById(UUID id);

    @Delete(entityClass = SnakeUser.class)
    void delete(UUID id);
    
}
