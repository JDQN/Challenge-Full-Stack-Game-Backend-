package org.example.cardgame.usecase.usecase;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.domain.Juego;
import org.example.cardgame.domain.command.PonerCartaEnTablero;
import org.example.cardgame.domain.values.Carta;
import org.example.cardgame.domain.values.JuegoId;
import org.example.cardgame.domain.values.JugadorId;
import org.example.cardgame.usecase.gateway.JuegoDomainEventRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;


public class PonerCartaEnTableroUseCase extends UseCaseForCommand<PonerCartaEnTablero> {
    private final JuegoDomainEventRepository repository;

    public PonerCartaEnTableroUseCase(JuegoDomainEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<PonerCartaEnTablero> ponerCartaEnTablero) {
        return ponerCartaEnTablero.flatMapMany((command) -> repository
                .obtenerEventosPor(command.getJuegoId())
                .collectList()
                .flatMapIterable(events -> {
                    var juego = Juego.from(JuegoId.of(command.getJuegoId()), events);
                    var tableroId = juego.tablero().identity();
                    var jugadorId = JugadorId.of(command.getJugadorId());
                    var cartasDelJugador = juego.jugadores().get(jugadorId).mazo().value().cartas();
                    var cartaSeleccionado = seleccionarCarta(command.getCartaId(), cartasDelJugador);

                    validarCantidadDelJugador(juego, jugadorId);
                    juego.ponerCartaEnTablero(tableroId, jugadorId, cartaSeleccionado);
                    return juego.getUncommittedChanges();
                }));
    }

    private void validarCantidadDelJugador(Juego juego, JugadorId jugadorId) {
        var cantidad = (long) juego.tablero().partida()
                .get(jugadorId).size();
        if (cantidad >= 2) {
            throw new IllegalArgumentException("No puede poner mas de 2 cartas en el tablero");
        }
    }

    private Carta seleccionarCarta(String cartaId, java.util.Set<Carta> cartasDelJugador) {
        return cartasDelJugador
                .stream()
                .filter(c -> c.value().cartaId().value().equals(cartaId))
                .findFirst()
                .orElseThrow();
    }
}
