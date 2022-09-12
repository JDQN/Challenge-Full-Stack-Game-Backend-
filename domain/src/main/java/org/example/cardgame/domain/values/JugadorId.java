package org.example.cardgame.domain.values;

import co.com.sofka.domain.generic.Identity;

/**
 * The type Jugador id.
 */
public class JugadorId extends Identity {

    /**
     * Instantiates a new Jugador id.
     *
     * @param value the value
     */
    public JugadorId(String value) {
        super(value);
    }

    /**
     * Instantiates a new Jugador id.
     */
    public JugadorId() {

    }

    /**
     * Of jugador id.
     *
     * @param value the value
     * @return the jugador id
     */
    public static JugadorId of(String value) {
        return new JugadorId(value);
    }
}
