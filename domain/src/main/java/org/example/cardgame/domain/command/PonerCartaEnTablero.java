package org.example.cardgame.domain.command;

import co.com.sofka.domain.generic.Command;

/**
 * The type Poner carta en tablero.
 */
public class PonerCartaEnTablero extends Command {
    private String jugadorId;
    private String cartaId;
    private String juegoId;

    /**
     * Gets jugador id.
     *
     * @return the jugador id
     */
    public String getJugadorId() {
        return jugadorId;
    }

    /**
     * Sets jugador id.
     *
     * @param jugadorId the jugador id
     */
    public void setJugadorId(String jugadorId) {
        this.jugadorId = jugadorId;
    }

    /**
     * Gets carta id.
     *
     * @return the carta id
     */
    public String getCartaId() {
        return cartaId;
    }

    /**
     * Sets carta id.
     *
     * @param cartaId the carta id
     */
    public void setCartaId(String cartaId) {
        this.cartaId = cartaId;
    }

    /**
     * Gets juego id.
     *
     * @return the juego id
     */
    public String getJuegoId() {
        return juegoId;
    }

    /**
     * Sets juego id.
     *
     * @param juegoId the juego id
     */
    public void setJuegoId(String juegoId) {
        this.juegoId = juegoId;
    }
}
