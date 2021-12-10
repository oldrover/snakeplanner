package org.snakeplanner.repository.mapper;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import org.snakeplanner.repository.dao.EventDao;

@Mapper
public interface EventMapper {

  @DaoFactory
  EventDao eventDao();
}
