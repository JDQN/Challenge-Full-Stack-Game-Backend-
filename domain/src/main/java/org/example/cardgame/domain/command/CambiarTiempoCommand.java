package org.example.cardgame.domain.command;

import co.com.sofka.domain.generic.Command;
import org.example.cardgame.domain.values.JuegoId;
import org.example.cardgame.domain.values.TableroId;

/**
 * The type Cambiar tiempo command.
 */
public class CambiarTiempoCommand extends Command {
    private JuegoId juegoId;
    private TableroId tableroId;
    private Integer tiempo;

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

    /**
     * Gets juego id.
     *
     * @return the juego id
     */
    public JuegoId getJuegoId() {
        return juegoId;
    }

    /**
     * Sets juego id.
     *
     * @param juegoId the juego id
     */
    public void setJuegoId(JuegoId juegoId) {
        this.juegoId = juegoId;
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
     * Sets tablero id.
     *
     * @param tableroId the tablero id
     */
    public void setTableroId(TableroId tableroId) {
        this.tableroId = tableroId;
    }
}
