package org.example.cardgame.domain.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.domain.values.JugadorId;
import org.example.cardgame.domain.values.TableroId;

import java.util.Set;

/**
 * The type Ronda terminada.
 */
public class RondaTerminada extends DomainEvent {
	private final TableroId tableroId;
	private final Set<JugadorId> jugadorIds;

	public RondaTerminada(TableroId tableroId, Set<JugadorId> jugadorIds) {
		super("cardgame.rondaterminada");
		this.tableroId = tableroId;
		this.jugadorIds = jugadorIds;
	}

	public TableroId getTableroId() {
		return tableroId;
	}

	public Set<JugadorId> getJugadorIds() {
		return jugadorIds;
	}
}
