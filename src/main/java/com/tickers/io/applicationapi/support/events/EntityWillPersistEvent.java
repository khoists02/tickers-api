package com.tickers.io.applicationapi.support.events;

public class EntityWillPersistEvent extends EntityEvent {
    public EntityWillPersistEvent(Object source, Object entity) {
        super(source, entity);
    }
}
