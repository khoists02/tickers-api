package com.tickers.io.applicationapi.support.events;

public class EntityWillRemoveEvent extends EntityEvent {
    public EntityWillRemoveEvent(Object source, Object entity) {
        super(source, entity);
    }
}
