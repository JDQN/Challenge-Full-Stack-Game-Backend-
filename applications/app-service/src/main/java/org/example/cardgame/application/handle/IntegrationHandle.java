package org.example.cardgame.application.handle;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.application.generic.EventBus;
import org.example.cardgame.application.generic.EventStoreRepository;
import org.example.cardgame.application.generic.StoredEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
public class IntegrationHandle implements Function<Flux<DomainEvent>, Mono<Void> > {
    private final EventStoreRepository repository;
    private final StoredEvent.EventSerializer eventSerializer;
    private final EventBus eventBus;

    public IntegrationHandle(EventStoreRepository repository, StoredEvent.EventSerializer eventSerializer, EventBus eventBus) {
        this.repository = repository;
        this.eventSerializer = eventSerializer;
        this.eventBus = eventBus;
    }

    @Override
    public Mono<Void> apply(Flux<DomainEvent> events){
        return events.flatMap(domainEvent -> {
            var stored = StoredEvent.wrapEvent(domainEvent, eventSerializer);
            return repository.saveEvent("game", domainEvent.aggregateRootId(), stored)
                    .thenReturn(domainEvent);
        }).doOnNext(eventBus::publish).then();
    }

    public Mono<Void> publishError(Throwable errorEvent){
        return Mono.create((callback) -> {
            eventBus.publishError(errorEvent);
            callback.success();
        });
    }

}