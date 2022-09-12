package org.example.cardgame.application.adapter.repo;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.application.generic.EventStoreRepository;
import org.example.cardgame.domain.values.JuegoId;
import org.example.cardgame.usecase.gateway.JuegoDomainEventRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class MongoJuegoDomainEventRepository implements JuegoDomainEventRepository {
    private final EventStoreRepository repository;

    public MongoJuegoDomainEventRepository(EventStoreRepository repository) {
        this.repository = repository;
    }


    @Override
    public Flux<DomainEvent> obtenerEventosPor(String id) {
        return repository.getEventsBy("game", id);
    }
}
