package org.snakeplanner.repository.dao;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;
import java.util.Optional;
import java.util.UUID;
import org.snakeplanner.entity.SnakeUser;

@Dao
public interface SnakeUserDao {

  @Update
  void update(SnakeUser snakeUser);

  @Select
  Optional<SnakeUser> findByEmailAndId(String email, UUID id);

  @Select
  Optional<SnakeUser> findByEmail(String email);

  @Delete(entityClass = SnakeUser.class)
  void deleteByEmailAndId(String email, UUID id);
}
