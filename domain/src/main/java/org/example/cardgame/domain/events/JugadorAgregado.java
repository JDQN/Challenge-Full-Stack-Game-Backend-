package org.example.cardgame.domain.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.domain.values.JugadorId;
import org.example.cardgame.domain.values.Mazo;

/**
 * The type Jugador agregado.
 */
public class JugadorAgregado extends DomainEvent {
    private final JugadorId identity;
    private final String alias;
    private final Mazo mazo;

    /**
     * Instantiates a new Jugador agregado.
     *
     * @param identity the identity
     * @param alias    the alias
     * @param mazo     the mazo
     */
    public JugadorAgregado(JugadorId identity, String alias, Mazo mazo) {
        super("cardgame.jugadoragregado");
        this.identity = identity;
        this.alias = alias;
        this.mazo = mazo;
    }

    /**
     * Gets juego id.
     *
     * @return the juego id
     */
    public JugadorId getJuegoId() {
        return identity;
    }

    /**
     * Gets alias.
     *
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Gets mazo.
     *
     * @return the mazo
     */
    public Mazo getMazo() {
        return mazo;
    }
}
