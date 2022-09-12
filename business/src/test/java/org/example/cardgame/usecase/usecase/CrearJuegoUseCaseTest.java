package org.example.cardgame.usecase.usecase;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.domain.command.CrearJuegoCommand;
import org.example.cardgame.domain.events.JuegoCreado;
import org.example.cardgame.domain.events.JugadorAgregado;
import org.example.cardgame.usecase.gateway.ListaDeCartaService;
import org.example.cardgame.usecase.gateway.model.CartaMaestra;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//TODO: hacer prueba
@ExtendWith(MockitoExtension.class)
class CrearJuegoUseCaseTest {

	@Mock
	private ListaDeCartaService listaDeCartaService;

	@InjectMocks
	private CrearJuegoUseCase useaCase;

	@Test
	void crearJuego(){

		//arrange
		var command = new CrearJuegoCommand();
		command.setJuegoId("IdJuego01");
		command.setJugadores(new HashMap<>());
		command.getJugadores().put("jugador01","Juanes");
		command.getJugadores().put("jugador02","Madona");
		command.setJugadorPrincipalId("Id01");

		when(listaDeCartaService.obtenerCartasDeMarvel()).thenReturn(history());

		StepVerifier.create(useaCase.apply(Mono.just(command)))

			 .expectNextMatches(new Predicate<DomainEvent>() {
				 @Override
				 public boolean test(DomainEvent domainEvent) {
					 var event = (JuegoCreado) domainEvent;
					 return "IdJuego01".equals(event.aggregateRootId()) && "Id01".equals(event.getJugadorPrincipal().value());
				 }
			 })

			 .expectNextMatches(domainEvent -> {
				 var event = (JugadorAgregado) domainEvent;
				 assert event.getMazo().value().cantidad().equals(2);
				 return event.getJuegoId().value().equals("jugador01") && event.getAlias().equals("Juanes");
			 })

			 .expectNextMatches(domainEvent -> {
				 var event = (JugadorAgregado) domainEvent;
				 assert event.getMazo().value().cantidad().equals(2);
				 return event.getJuegoId().value().equals("jugador02") && event.getAlias().equals("Madona");
			 })
			 .expectComplete()
			 .verify();
	}

	private Flux<CartaMaestra> history() {

		return  Flux.just(

			 /**
				* Mazo d ecartas jugador 1 Juanes
				*/
			 new CartaMaestra("1","tarjeta1"),
			 new CartaMaestra("2","tarjeta2"),
			 new CartaMaestra("3","tarjeta3"),

			 /**
				* Mazo de cartas jugador 2 Madona
				*/
			 new CartaMaestra("4","tarjeta4"),
			 new CartaMaestra("5","tarjeta5"),
			 new CartaMaestra("6","tarjeta6")

		);
	}
}