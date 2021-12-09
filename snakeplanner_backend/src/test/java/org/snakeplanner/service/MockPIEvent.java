package org.snakeplanner.service;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.core.cql.ColumnDefinitions;
import com.datastax.oss.driver.api.core.cql.ExecutionInfo;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.snakeplanner.entity.Event;
import org.snakeplanner.entity.Snake;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class MockPIEvent implements PagingIterable<Event> {

    private Event event;

    public MockPIEvent(Event event) {
        this.event = event;
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
    public Iterator<Event> iterator() {
        List<Event> list = new ArrayList<>();
        list.add(event);
        return list.iterator();
    }
}
