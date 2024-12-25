package com.tickers.io.applicationapi.support.events;

public class EntityPersistedEvent extends EntityEvent {
    public EntityPersistedEvent(Object source, Object entity) {
        super(source, entity);
    }
}
