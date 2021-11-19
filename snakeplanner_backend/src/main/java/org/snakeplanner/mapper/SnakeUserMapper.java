package org.snakeplanner.mapper;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import org.snakeplanner.dao.SnakeUserDao;

@Mapper
public interface SnakeUserMapper {

  @DaoFactory
  SnakeUserDao userDao();
}
