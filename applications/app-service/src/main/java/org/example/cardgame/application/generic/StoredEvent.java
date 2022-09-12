package org.example.cardgame.application.generic;

import co.com.sofka.domain.generic.DomainEvent;

import java.util.Date;

public class StoredEvent {

    private String eventBody;
    private Date occurredOn;
    private String typeName;

    public StoredEvent() {
    }


    public StoredEvent(String typeName, Date occurredOn, String eventBody) {
        this.setEventBody(eventBody);
        this.setOccurredOn(occurredOn);
        this.setTypeName(typeName);
    }


    public static StoredEvent wrapEvent(DomainEvent domainEvent, EventSerializer eventSerializer) {
        return new StoredEvent(domainEvent.getClass().getCanonicalName(),
                new Date(),
                eventSerializer.serialize(domainEvent)
        );
    }


    public String getEventBody() {
        return eventBody;
    }


    public void setEventBody(String eventBody) {
        this.eventBody = eventBody;
    }


    public Date getOccurredOn() {
        return occurredOn;
    }


    public void setOccurredOn(Date occurredOn) {
        this.occurredOn = occurredOn;
    }


    public String getTypeName() {
        return typeName;
    }


    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


    public DomainEvent deserializeEvent(EventSerializer eventSerializer) {
        try {
            return eventSerializer
                    .deserialize(this.getEventBody(), Class.forName(this.getTypeName()));
        } catch (ClassNotFoundException e) {
            throw new DeserializeException(e.getCause());
        }
    }


    public interface EventSerializer {
        <T extends DomainEvent> T deserialize(String aSerialization, final Class<?> aType);

        String serialize(DomainEvent object);
    }
}