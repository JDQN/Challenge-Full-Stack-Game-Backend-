package org.example.cardgame.usecase.gateway;

import co.com.sofka.domain.generic.DomainEvent;
import reactor.core.publisher.Flux;

public interface JuegoDomainEventRepository {
    Flux<DomainEvent> obtenerEventosPor(String id);

}
