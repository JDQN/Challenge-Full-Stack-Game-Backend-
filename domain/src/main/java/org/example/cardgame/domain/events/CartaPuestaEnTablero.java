package org.example.cardgame.domain.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.domain.values.Carta;
import org.example.cardgame.domain.values.JugadorId;
import org.example.cardgame.domain.values.TableroId;

/**
 * The type Carta puesta en tablero.
 */
public class CartaPuestaEnTablero extends DomainEvent {
    private final TableroId tableroId;
    private final JugadorId jugadorId;
    private final Carta carta;

    /**
     * Instantiates a new Carta puesta en tablero.
     *
     * @param tableroId the tablero id
     * @param jugadorId the jugador id
     * @param carta     the carta
     */
    public CartaPuestaEnTablero(TableroId tableroId, JugadorId jugadorId, Carta carta) {
        super("cardgame.ponercartaentablero");
        this.tableroId = tableroId;
        this.jugadorId = jugadorId;
        this.carta = carta;
    }

    /**
     * Gets tablero id.
     *
     * @return the tablero id
     */
    public TableroId getTableroId() {
        return tableroId;
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
     * Gets carta.
     *
     * @return the carta
     */
    public Carta getCarta() {
        return carta;
    }
}
