package org.example.cardgame.usecase.usecase;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.domain.command.FinalizarRondaCommand;
import org.example.cardgame.domain.events.*;
import org.example.cardgame.domain.values.*;
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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FinalizarRondaUseCaseTest {
	@InjectMocks
	private FinalizarRondaUseCase useCase;

	@Mock
	private JuegoDomainEventRepository repository;

	@Test
	void finalizarRonda() {
		//ARRANGE
		var command = new FinalizarRondaCommand();
		command.setJuegoId("JuegoId1");

		//ACT & ASSERT
		when(repository.obtenerEventosPor("JuegoId1"))
			 .thenReturn(historico());

		//ACT & ASSERT
		StepVerifier
			 .create(useCase.apply(Mono.just(command)))
			 .expectNextMatches(domainEvent -> {
				 var event = (CartasAsignadasAJugador) domainEvent;

				 return event.aggregateRootId().equals("JuegoId1")
						&& event.getGanadorId().equals(JugadorId.of("JugadorId1"))
						&& event.getPuntos().equals(1000)
						&& event.getCartasApuesta().equals(Set.of(new Carta(CartaMaestraId.of("CartaMaestraId1"), 1000, true, true),
						new Carta(CartaMaestraId.of("CartaMaestraId2"), 999, true, true)));
			 }).expectNextMatches(domainEvent -> {
				 var event = (RondaTerminada) domainEvent;
				 return event.aggregateRootId().equals("JuegoId1")
						&& event.getTableroId().equals(TableroId.of("TableroId1"))
						&& event.getJugadorIds().equals(Set.of(JugadorId.of("JugadorId1"), JugadorId.of("JugadorId2")));
			 })
			 .expectComplete()
			 .verify();

	}

	private Flux<DomainEvent> historico() {
		var event = new JuegoCreado(JugadorId.of("JugadorId1"));
		event.setAggregateRootId("JuegoId1");

		var event2 = new JugadorAgregado(
			 JugadorId.of("JugadorId1"), "JuanDa",
			 new Mazo(Set.of(
					new Carta(CartaMaestraId.of("CartaMaestraId1"), 1000, true, true),
					new Carta(CartaMaestraId.of("bbb"), 102, true, true),
					new Carta(CartaMaestraId.of("ccc"), 101, true, true),
					new Carta(CartaMaestraId.of("ddd"), 104, true, true),
					new Carta(CartaMaestraId.of("fff"), 150, true, true)
			 )));
		event2.setAggregateRootId("JuegoId1");

		var event3 = new JugadorAgregado(
			 JugadorId.of("JugadorId2"), "Astrid",
			 new Mazo(Set.of(
					new Carta(CartaMaestraId.of("CartaMaestraId2"), 999, true, true),
					new Carta(CartaMaestraId.of("bbba"), 102, true, true),
					new Carta(CartaMaestraId.of("ccca"), 101, true, true),
					new Carta(CartaMaestraId.of("ddda"), 104, true, true),
					new Carta(CartaMaestraId.of("fffa"), 150, true, true)
			 )));
		event3.setAggregateRootId("JuegoId1");

		var event4 = new TableroCreado(TableroId.of("TableroId1"),
			 Set.of(
					JugadorId.of("JugadorId1"),
					JugadorId.of("JugadorId2")
			 )
		);
		event4.setAggregateRootId("JuegoId1");

		var event5 = new RondaCreada(
			 new Ronda(1,
					Set.of(JugadorId.of("JugadorId1"),
						 JugadorId.of("JugadorId2")
					)
			 ), 80);
		event5.setAggregateRootId("JuegoId1");

		var event6 = new RondaIniciada();
		event6.setAggregateRootId("JuegoId1");

		//JUGADOR 1//
		var event7 = new CartaPuestaEnTablero(event4.getTableroId(), event2.getJugadorId(), new Carta(CartaMaestraId.of("CartaMaestraId1"), 1000, true, true));
		event7.setAggregateRootId("JuegoId1");

		var event8 = new CartaQuitadaDelMazo(event2.getJugadorId(), new Carta(CartaMaestraId.of("CartaMaestraId1"), 1000, true, true));
		event8.setAggregateRootId("JuegoId1");

		//JUGADOR 2//
		var event9 = new CartaPuestaEnTablero(event4.getTableroId(), event3.getJugadorId(), new Carta(CartaMaestraId.of("CartaMaestraId2"), 999, true, true));
		event9.setAggregateRootId("JuegoId1");

		var event10 = new CartaQuitadaDelMazo(event3.getJugadorId(), new Carta(CartaMaestraId.of("CartaMaestraId2"), 999, true, true));
		event10.setAggregateRootId("JuegoId1");

		return Flux.just(event, event2, event3, event4, event5, event6, event7, event8, event9, event10);
	}
}