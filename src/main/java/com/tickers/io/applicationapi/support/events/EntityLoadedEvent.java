package com.tickers.io.applicationapi.support.events;

public class EntityLoadedEvent extends EntityEvent {
    public EntityLoadedEvent(Object source, Object entity) {
        super(source, entity);
    }
}
