package org.example.cardgame.usecase.usecase;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.domain.command.PonerCartaEnTablero;
import org.example.cardgame.domain.events.*;
import org.example.cardgame.domain.values.*;
import org.example.cardgame.usecase.gateway.JuegoDomainEventRepository;
import org.junit.jupiter.api.Assertions;
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
class PonerCartaEnTableroUseCaseTest {

	@Mock
	private JuegoDomainEventRepository repository;

	@InjectMocks
	private PonerCartaEnTableroUseCase useCase;

	@Test
	void ponerCarta() {
		//arrange
		var command = new PonerCartaEnTablero();
		command.setCartaId("xxxxx");
		command.setJuegoId("fffff");
		command.setJugadorId("yyyyy");
		when(repository.obtenerEventosPor("fffff")).thenReturn(history());

		StepVerifier.create(useCase.apply(Mono.just(command)))//act
			 .expectNextMatches(domainEvent -> {
				 var event = (CartaPuestaEnTablero) domainEvent;
				 Assertions.assertEquals("yyyyy", event.getJugadorId().value());
				 return "xxxxx".equals(event.getCarta().value().cartaId().value());
			 })
			 .expectNextMatches(domainEvent -> {
				 var event = (CartaQuitadaDelMazo) domainEvent;
				 Assertions.assertEquals("yyyyy", event.getJugadorId().value());
				 return "xxxxx".equals(event.getCarta().value().cartaId().value());
			 })
			 .expectComplete()
			 .verify();
	}

	private Flux<DomainEvent> history() {
		var jugadorId = JugadorId.of("yyyyy");
		var jugador2Id = JugadorId.of("hhhhhh");
		var cartas = Set.of(new Carta(
			 CartaMaestraId.of("xxxxx"),
			 20,
			 false, true
		));
		var ronda = new Ronda(1, Set.of(jugadorId, jugador2Id));
		return Flux.just(
			 new JuegoCreado(jugadorId),
			 new JugadorAgregado(jugadorId, "raul", new Mazo(cartas)),
			 new TableroCreado(new TableroId(), Set.of(jugadorId, jugador2Id)),
			 new RondaCreada(ronda, 30),
			 new RondaIniciada()
		);
	}
}