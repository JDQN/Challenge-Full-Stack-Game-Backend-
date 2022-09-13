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
		command.setJuegoId("XXXX");

		//ACT & ASSERT
		when(repository.obtenerEventosPor("XXXX"))
			 .thenReturn(historico());

		//ACT & ASSERT
		StepVerifier
			 .create(useCase.apply(Mono.just(command)))
			 .expectNextMatches(domainEvent -> {
				 var event = (CartasAsignadasAJugador) domainEvent;

				 return event.aggregateRootId().equals("XXXX")
						&& event.getGanadorId().equals(JugadorId.of("AAAA"))
						&& event.getPuntos().equals(1000)
						&& event.getCartasApuesta().equals(Set.of(new Carta(CartaMaestraId.of("CARTAÑERY"), 1000, true, true),
						new Carta(CartaMaestraId.of("CARTAPERRY"), 999, true, true)));
			 }).expectNextMatches(domainEvent -> {
				 var event = (RondaTerminada) domainEvent;
				 return event.aggregateRootId().equals("XXXX")
						&& event.getTableroId().equals(TableroId.of("LLLL"))
						&& event.getJugadorIds().equals(Set.of(JugadorId.of("AAAA"), JugadorId.of("BBBB")));
			 })
			 .expectComplete()
			 .verify();

	}

	private Flux<DomainEvent> historico() {
		var event = new JuegoCreado(JugadorId.of("AAAA"));
		event.setAggregateRootId("XXXX");

		var event2 = new JugadorAgregado(
			 JugadorId.of("AAAA"), "JOAQUIN",
			 new Mazo(Set.of(
					new Carta(CartaMaestraId.of("CARTAÑERY"), 1000, true, true),
					new Carta(CartaMaestraId.of("bbb"), 102, true, true),
					new Carta(CartaMaestraId.of("ccc"), 101, true, true),
					new Carta(CartaMaestraId.of("ddd"), 104, true, true),
					new Carta(CartaMaestraId.of("fff"), 150, true, true),
					new Carta(CartaMaestraId.of("ggg"), 160, true, true)
			 )));
		event2.setAggregateRootId("XXXX");

		var event3 = new JugadorAgregado(
			 JugadorId.of("BBBB"), "MATI",
			 new Mazo(Set.of(
					new Carta(CartaMaestraId.of("CARTAPERRY"), 999, true, true),
					new Carta(CartaMaestraId.of("bbba"), 102, true, true),
					new Carta(CartaMaestraId.of("ccca"), 101, true, true),
					new Carta(CartaMaestraId.of("ddda"), 104, true, true),
					new Carta(CartaMaestraId.of("fffa"), 150, true, true),
					new Carta(CartaMaestraId.of("ggga"), 160, true, true)
			 )));
		event3.setAggregateRootId("XXXX");

		var event4 = new TableroCreado(TableroId.of("LLLL"),
			 Set.of(
					JugadorId.of("AAAA"),
					JugadorId.of("BBBB")
			 )
		);
		event4.setAggregateRootId("XXXX");

		var event5 = new RondaCreada(
			 new Ronda(1,
					Set.of(JugadorId.of("AAAA"),
						 JugadorId.of("BBBB")
					)
			 ), 80);
		event5.setAggregateRootId("XXXX");

		var event6 = new RondaIniciada();
		event6.setAggregateRootId("XXXX");

		//JUGADOR 1//
		var event7 = new CartaPuestaEnTablero(event4.getTableroId(), event2.getJugadorId(), new Carta(CartaMaestraId.of("CARTAÑERY"), 1000, true, true));
		event7.setAggregateRootId("XXXX");

		var event8 = new CartaQuitadaDelMazo(event2.getJugadorId(), new Carta(CartaMaestraId.of("CARTAÑERY"), 1000, true, true));
		event8.setAggregateRootId("XXXX");

		//JUGADOR 2//
		var event9 = new CartaPuestaEnTablero(event4.getTableroId(), event3.getJugadorId(), new Carta(CartaMaestraId.of("CARTAPERRY"), 999, true, true));
		event9.setAggregateRootId("XXXX");

		var event10 = new CartaQuitadaDelMazo(event3.getJugadorId(), new Carta(CartaMaestraId.of("CARTAPERRY"), 999, true, true));
		event10.setAggregateRootId("XXXX");

		return Flux.just(event, event2, event3, event4, event5, event6, event7, event8, event9, event10);
	}
}