package org.example.cardgame.domain.values;

import co.com.sofka.domain.generic.Identity;

/**
 * The type Juego id.
 */
public class JuegoId extends Identity {
    /**
     * Instantiates a new Juego id.
     *
     * @param juegoId the juego id
     */
    public JuegoId(String juegoId) {
        super(juegoId);
    }

    /**
     * Instantiates a new Juego id.
     */
    public JuegoId() {

    }

    /**
     * Of juego id.
     *
     * @param juegoId the juego id
     * @return the juego id
     */
    public static JuegoId of(String juegoId) {
        return new JuegoId(juegoId);
    }
}
