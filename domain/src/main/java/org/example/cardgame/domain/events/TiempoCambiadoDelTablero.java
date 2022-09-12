package org.example.cardgame.domain.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.domain.values.TableroId;

/**
 * The type Tiempo cambiado del tablero.
 */
public class TiempoCambiadoDelTablero extends DomainEvent {
    private final TableroId tableroId;
    private final Integer tiempo;

    /**
     * Instantiates a new Tiempo cambiado del tablero.
     *
     * @param tableroId the tablero id
     * @param tiempo    the tiempo
     */
    public TiempoCambiadoDelTablero(TableroId tableroId, Integer tiempo) {
        super("cardgame.tiempocambiadodeltablero");
        this.tableroId = tableroId;
        this.tiempo = tiempo;
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
     * Gets tablero id.
     *
     * @return the tablero id
     */
    public TableroId getTableroId() {
        return tableroId;
    }
}
