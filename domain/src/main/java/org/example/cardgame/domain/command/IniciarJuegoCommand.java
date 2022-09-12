package org.example.cardgame.domain.command;

import co.com.sofka.domain.generic.Command;

/**
 * The type Iniciar juego command.
 */
public class IniciarJuegoCommand extends Command {
    private String juegoId;

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
