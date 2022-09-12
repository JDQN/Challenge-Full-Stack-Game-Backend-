package org.example.cardgame.domain.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.domain.values.JugadorId;

/**
 * The type Juego creado.
 */
public class JuegoCreado extends DomainEvent {
    private final JugadorId jugadorPrincipal;

    /**
     * Instantiates a new Juego creado.
     *
     * @param jugadorPrincipal the jugador principal
     */
    public JuegoCreado(JugadorId jugadorPrincipal) {
        super("cardgame.juegocreado");
        this.jugadorPrincipal = jugadorPrincipal;
    }

    /**
     * Gets jugador principal.
     *
     * @return the jugador principal
     */
    public JugadorId getJugadorPrincipal() {
        return jugadorPrincipal;
    }
}
