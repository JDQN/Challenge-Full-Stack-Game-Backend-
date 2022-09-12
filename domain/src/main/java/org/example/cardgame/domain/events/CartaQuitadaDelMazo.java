package org.example.cardgame.domain.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.domain.values.Carta;
import org.example.cardgame.domain.values.JugadorId;

/**
 * The type Carta quitada del mazo.
 */
public class CartaQuitadaDelMazo extends DomainEvent {
    private final JugadorId jugadorId;
    private final Carta carta;

    /**
     * Instantiates a new Carta quitada del mazo.
     *
     * @param jugadorId the jugador id
     * @param carta     the carta
     */
    public CartaQuitadaDelMazo(JugadorId jugadorId, Carta carta) {
        super("cardgame.cartaquitadadelmazo");
        this.jugadorId = jugadorId;
        this.carta = carta;
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
