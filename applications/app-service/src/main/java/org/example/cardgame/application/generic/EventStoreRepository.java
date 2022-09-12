package org.example.cardgame.application.generic;

import co.com.sofka.domain.generic.DomainEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventStoreRepository {

    Flux<DomainEvent> getEventsBy(String aggregateName, String aggregateRootId);


    Mono<Void> saveEvent(String aggregateName, String aggregateRootId, StoredEvent storedEvent);

}