package org.example.cardgame.domain.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.domain.values.Carta;
import org.example.cardgame.domain.values.JugadorId;

import java.util.Set;

/**
 * The type Cartas asignadas a jugador.
 */
public class CartasAsignadasAJugador extends DomainEvent {
    private final JugadorId ganadorId;
    private final Integer puntos;
    private final Set<Carta> cartasApuesta;

    /**
     * Instantiates a new Cartas asignadas a jugador.
     *
     * @param ganadorId     the ganador id
     * @param puntos        the puntos
     * @param cartasApuesta the cartas apuesta
     */
    public CartasAsignadasAJugador(JugadorId ganadorId, Integer puntos, Set<Carta> cartasApuesta) {
        super("cardgame.cartasasignadasajugador");
        this.ganadorId = ganadorId;
        this.puntos = puntos;
        this.cartasApuesta = cartasApuesta;
    }

    /**
     * Gets puntos.
     *
     * @return the puntos
     */
    public Integer getPuntos() {
        return puntos;
    }

    /**
     * Gets ganador id.
     *
     * @return the ganador id
     */
    public JugadorId getGanadorId() {
        return ganadorId;
    }

    /**
     * Gets cartas apuesta.
     *
     * @return the cartas apuesta
     */
    public Set<Carta> getCartasApuesta() {
        return cartasApuesta;
    }
}
