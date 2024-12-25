package com.tickers.io.applicationapi.support.events;

public class EntityWillUpdateEvent extends EntityEvent {
    public EntityWillUpdateEvent(Object source, Object entity) {
        super(source, entity);
    }
}
