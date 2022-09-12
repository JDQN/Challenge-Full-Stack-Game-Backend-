package org.example.cardgame.domain.command;

import co.com.sofka.domain.generic.Command;

import java.util.Map;

/**
 * The type Crear juego command.
 */
public class CrearJuegoCommand extends Command {
    private String juegoId;
    private Map<String, String> jugadores;
    private String jugadorPrincipalId;

    /**
     * Gets jugadores.
     *
     * @return the jugadores
     */
    public Map<String, String> getJugadores() {
        return jugadores;
    }

    /**
     * Sets jugadores.
     *
     * @param jugadores the jugadores
     */
    public void setJugadores(Map<String, String> jugadores) {
        this.jugadores = jugadores;
    }

    /**
     * Gets jugador principal id.
     *
     * @return the jugador principal id
     */
    public String getJugadorPrincipalId() {
        return jugadorPrincipalId;
    }

    /**
     * Sets jugador principal id.
     *
     * @param jugadorPrincipalId the jugador principal id
     */
    public void setJugadorPrincipalId(String jugadorPrincipalId) {
        this.jugadorPrincipalId = jugadorPrincipalId;
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
