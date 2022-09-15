package org.example.cardgame.usecase.usecase;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.domain.Juego;
import org.example.cardgame.domain.JugadorFactory;
import org.example.cardgame.domain.command.CrearJuegoCommand;
import org.example.cardgame.domain.values.*;
import org.example.cardgame.usecase.gateway.ListaDeCartaService;
import org.example.cardgame.usecase.gateway.model.CartaMaestra;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class CrearJuegoUseCase extends UseCaseForCommand<CrearJuegoCommand> {
	private final ListaDeCartaService listaDeCartaService;

	public CrearJuegoUseCase(ListaDeCartaService listaDeCartaService) {
		this.listaDeCartaService = listaDeCartaService;
	}

	@Override
	public Flux<DomainEvent> apply(Mono<CrearJuegoCommand> input) {
		return listaDeCartaService.obtenerCartasDeMarvel().collectList()
			 .flatMapMany(cartas -> input.flatMapIterable(command -> {

				 var factory = new JugadorFactory();
				 command.getJugadores()
						.forEach((id, alias) ->
							 factory.agregarJugador(JugadorId.of(id), alias, generarMazo(cartas))
						);
				 var juego = new Juego(
						JuegoId.of(command.getJuegoId()),
						JugadorId.of(command.getJugadorPrincipalId()),
						factory
				 );
				 return juego.getUncommittedChanges();
			 }));
	}

	private Mazo generarMazo(List<CartaMaestra> cartas) {
		Collections.shuffle(cartas);
		var lista = cartas.stream().limit(5)
			 .map(carta -> new Carta(CartaMaestraId.of(carta.getId()), carta.getPoder(), false, true, carta.getUri()))
			 .collect(Collectors.toList());
		cartas.removeIf(cartaMaestra -> lista.stream().anyMatch(carta -> {
			var id = carta.value().cartaId().value();
			return cartaMaestra.getId().equals(id);
		}));
		return new Mazo(new HashSet<>(lista));
	}

}