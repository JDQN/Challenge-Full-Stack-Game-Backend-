package org.example.cardgame.usecase.usecase;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.domain.command.IniciarRondaCommand;
import org.example.cardgame.domain.events.JuegoCreado;
import org.example.cardgame.domain.events.RondaCreada;
import org.example.cardgame.domain.events.RondaIniciada;
import org.example.cardgame.domain.events.TableroCreado;
import org.example.cardgame.domain.values.JugadorId;
import org.example.cardgame.domain.values.Ronda;
import org.example.cardgame.domain.values.TableroId;
import org.example.cardgame.usecase.gateway.JuegoDomainEventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IniciarRondaUseCaseTest {

	@Mock
	private JuegoDomainEventRepository repository;

	@InjectMocks
	private IniciarRondaUseCase useCase;

	@Test
	void iniciarRonda() {
		var command = new IniciarRondaCommand();
		command.setJuegoId("JuegoId1");

		when(repository.obtenerEventosPor("JuegoId1")).thenReturn(history());

		StepVerifier.create(useCase.apply(Mono.just(command)))
			 .expectNextMatches(domainEvent -> {
				 var event = (RondaIniciada) domainEvent;
				 return event.aggregateRootId().equals("JuegoId1");
			 })
			 .expectComplete()
			 .verify();
	}

	private Flux<DomainEvent> history() {
		var event = new JuegoCreado(JugadorId.of("JuegoId1"));
		event.setAggregateRootId("Id1");

		var event2 = new TableroCreado(TableroId.of("TAB1"), Set.of(JugadorId.of("Juanes"), JugadorId.of("Maria"), JugadorId.of("Sandra")));
		event2.setAggregateRootId("Id1");

		var event3 = new RondaCreada(new Ronda(1, event2.getJugadorIds()), 30);
		event3.setAggregateRootId("Id1");

		return Flux.just(event, event2, event3);
	}
}
