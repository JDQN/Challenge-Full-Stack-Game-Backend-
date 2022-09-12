package org.example.cardgame.domain;

import org.example.cardgame.domain.values.JugadorId;
import org.example.cardgame.domain.values.Mazo;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Jugador factory.
 */
public class JugadorFactory {
    private final Set<Jugador> jugadores;

    /**
     * Instantiates a new Jugador factory.
     */
    public JugadorFactory() {
        jugadores = new HashSet<>();
    }

    /**
     * Agregar jugador.
     *
     * @param jugadorId the jugador id
     * @param alias     the alias
     * @param mazo      the mazo
     */
    public void agregarJugador(JugadorId jugadorId, String alias, Mazo mazo) {
        jugadores.add(new Jugador(jugadorId, alias, mazo));
    }

    /**
     * Gets jugadores.
     *
     * @return the jugadores
     */
    protected Set<Jugador> getJugadores() {
        return jugadores;
    }
}
