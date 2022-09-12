package org.example.cardgame.domain.command;

import co.com.sofka.domain.generic.Command;

import java.util.Set;

/**
 * The type Crear ronda command.
 */
public class CrearRondaCommand extends Command {
    private String juegoId;
    private Integer tiempo;
    private Set<String> jugadores;


    /**
     * Gets jugadores.
     *
     * @return the jugadores
     */
    public Set<String> getJugadores() {
        return jugadores;
    }

    /**
     * Sets jugadores.
     *
     * @param jugadores the jugadores
     */
    public void setJugadores(Set<String> jugadores) {
        this.jugadores = jugadores;
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

    /**
     * Gets tiempo.
     *
     * @return the tiempo
     */
    public Integer getTiempo() {
        return tiempo;
    }

    /**
     * Sets tiempo.
     *
     * @param tiempo the tiempo
     */
    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }
}
