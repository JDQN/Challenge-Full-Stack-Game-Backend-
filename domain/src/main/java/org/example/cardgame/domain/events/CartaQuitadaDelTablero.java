package org.example.cardgame.domain.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.domain.values.Carta;
import org.example.cardgame.domain.values.JugadorId;
import org.example.cardgame.domain.values.TableroId;

/**
 * The type Carta quitada del tablero.
 */
public class CartaQuitadaDelTablero extends DomainEvent {
    private final TableroId tableroId;
    private final JugadorId jugadorId;
    private final Carta carta;

    /**
     * Instantiates a new Carta quitada del tablero.
     *
     * @param tableroId the tablero id
     * @param jugadorId the jugador id
     * @param carta     the carta
     */
    public CartaQuitadaDelTablero(TableroId tableroId, JugadorId jugadorId, Carta carta) {
        super("cardgame.cartaquitadadeltablero");
        this.tableroId = tableroId;
        this.jugadorId = jugadorId;
        this.carta = carta;
    }

    /**
     * Gets carta.
     *
     * @return the carta
     */
    public Carta getCarta() {
        return carta;
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
     * Gets tablero id.
     *
     * @return the tablero id
     */
    public TableroId getTableroId() {
        return tableroId;
    }
}
