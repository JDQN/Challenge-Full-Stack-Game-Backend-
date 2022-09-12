package org.example.cardgame.domain.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.domain.values.JugadorId;

/**
 * The type Juego finalizado.
 */
public class JuegoFinalizado extends DomainEvent {
    private final JugadorId jugadorId;
    private final String alias;

    /**
     * Instantiates a new Juego finalizado.
     *
     * @param jugadorId the jugador id
     * @param alias     the alias
     */
    public JuegoFinalizado(JugadorId jugadorId, String alias) {
        super("cardgame.juegofinalizado");
        this.jugadorId = jugadorId;
        this.alias = alias;
    }

    /**
     * Gets jugador id.
     *
     * @return the jugador id
     */
    public JugadorId getJugadorId() {
        return jugadorId;
    }

    /**
     * Gets alias.
     *
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }
}
