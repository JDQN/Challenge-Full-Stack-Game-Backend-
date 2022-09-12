package org.example.cardgame.domain.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Set;

/**
 * The type Ronda.
 */
public class Ronda implements ValueObject<Ronda.Props> {
    private final Set<JugadorId> jugadores;
    private final Integer numero;
    private final Boolean estaIniciada;

    /**
     * Instantiates a new Ronda.
     *
     * @param numero    the numero
     * @param jugadores the jugadores
     */
    public Ronda(Integer numero, Set<JugadorId> jugadores) {
        this.jugadores = jugadores;
        this.numero = numero;
        this.estaIniciada = false;
    }

    private Ronda(Integer numero, Set<JugadorId> jugadores, Boolean estaIniciada) {
        this.jugadores = jugadores;
        this.numero = numero;
        this.estaIniciada = estaIniciada;
    }

    /**
     * Iniciar ronda ronda.
     *
     * @return the ronda
     */
    public Ronda iniciarRonda() {
        return new Ronda(this.numero, this.jugadores, true);
    }

    /**
     * Terminar ronda ronda.
     *
     * @return the ronda
     */
    public Ronda terminarRonda() {
        return new Ronda(this.numero, this.jugadores, false);
    }

    /**
     * Incrementar ronda ronda.
     *
     * @param jugadores the jugadores
     * @return the ronda
     */
    public Ronda incrementarRonda(Set<JugadorId> jugadores) {
        return new Ronda(this.numero + 1, jugadores, false);
    }

    @Override
    public Props value() {
        return new Props() {
            @Override
            public Set<JugadorId> jugadores() {
                return jugadores;
            }

            @Override
            public Integer numero() {
                return numero;
            }

            @Override
            public Boolean estaIniciada() {
                return estaIniciada;
            }
        };
    }


    /**
     * The interface Props.
     */
    public interface Props {
        /**
         * Jugadores set.
         *
         * @return the set
         */
        Set<JugadorId> jugadores();

        /**
         * Numero integer.
         *
         * @return the integer
         */
        Integer numero();

        /**
         * Esta iniciada boolean.
         *
         * @return the boolean
         */
        Boolean estaIniciada();
    }
}
