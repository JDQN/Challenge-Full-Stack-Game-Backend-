package org.example.cardgame.domain.events;

import co.com.sofka.domain.generic.DomainEvent;

/**
 * The type Ronda iniciada.
 */
public class RondaIniciada extends DomainEvent {
    /**
     * Instantiates a new Ronda iniciada.
     */
    public RondaIniciada() {
        super("cardgame.rondainiciada");
    }
}
