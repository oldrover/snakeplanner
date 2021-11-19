package org.snakeplanner.mapper;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import org.snakeplanner.dao.SnakeDao;

@Mapper
public interface SnakeMapper {

  @DaoFactory
  SnakeDao snakeDao();
}
