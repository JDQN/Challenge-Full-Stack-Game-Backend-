package org.example.cardgame.domain.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.domain.values.JugadorId;
import org.example.cardgame.domain.values.TableroId;

import java.util.Set;

/**
 * The type Tablero creado.
 */
public class TableroCreado extends DomainEvent {
    private final TableroId tableroId;
    private final Set<JugadorId> jugadorIds;


    /**
     * Instantiates a new Tablero creado.
     *
     * @param tableroId  the tablero id
     * @param jugadorIds the jugador ids
     */
    public TableroCreado(TableroId tableroId, Set<JugadorId> jugadorIds) {
        super("cardgame.tablerocreado");
        this.tableroId = tableroId;
        this.jugadorIds = jugadorIds;
    }

    /**
     * Gets jugador ids.
     *
     * @return the jugador ids
     */
    public Set<JugadorId> getJugadorIds() {
        return jugadorIds;
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
