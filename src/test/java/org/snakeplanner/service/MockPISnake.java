package org.snakeplanner.service;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.core.cql.ColumnDefinitions;
import com.datastax.oss.driver.api.core.cql.ExecutionInfo;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.snakeplanner.entity.Snake;

class MockPISnake implements PagingIterable<Snake> {

  private Snake snake;

  public MockPISnake(Snake snake) {
    this.snake = snake;
  }

  @NonNull
  @Override
  public ColumnDefinitions getColumnDefinitions() {
    return null;
  }

  @NonNull
  @Override
  public List<ExecutionInfo> getExecutionInfos() {
    return null;
  }

  @Override
  public boolean isFullyFetched() {
    return false;
  }

  @Override
  public int getAvailableWithoutFetching() {
    return 0;
  }

  @Override
  public boolean wasApplied() {
    return false;
  }

  @NonNull
  @Override
  public Iterator<Snake> iterator() {
    List<Snake> list = new ArrayList<>();
    list.add(snake);
    return list.iterator();
  }
}
