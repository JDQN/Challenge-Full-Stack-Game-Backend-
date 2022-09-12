package org.example.cardgame.domain.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

/**
 * The type Carta.
 */
public class Carta implements ValueObject<Carta.Props>, Comparable<Carta> {

    private final CartaMaestraId cartaId;
    private final Boolean estaOculta;
    private final Boolean estaHabilitada;
    private final Integer poder;


    /**
     * Instantiates a new Carta.
     *
     * @param cartaId        the carta id
     * @param poder          the poder
     * @param estaOculta     the esta oculta
     * @param estaHabilitada the esta habilitada
     */
    public Carta(CartaMaestraId cartaId, Integer poder, Boolean estaOculta, Boolean estaHabilitada) {
        this.cartaId = cartaId;
        this.estaOculta = estaOculta;
        this.estaHabilitada = estaHabilitada;
        this.poder = poder;
    }

    @Override
    public Props value() {
        return new Props() {
            @Override
            public CartaMaestraId cartaId() {
                return cartaId;
            }

            @Override
            public Integer poder() {
                return poder;
            }

            @Override
            public Boolean estaOculta() {
                return estaOculta;
            }

            @Override
            public Boolean estaHabilitada() {
                return estaHabilitada;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carta carta = (Carta) o;
        return Objects.equals(cartaId, carta.cartaId) && Objects.equals(estaOculta, carta.estaOculta) && Objects.equals(estaHabilitada, carta.estaHabilitada) && Objects.equals(poder, carta.poder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartaId, estaOculta, estaHabilitada, poder);
    }

    @Override
    public int compareTo(Carta carta) {
        return this.poder - carta.poder;
    }


    /**
     * The interface Props.
     */
    public interface Props {
        /**
         * Carta id carta maestra id.
         *
         * @return the carta maestra id
         */
        CartaMaestraId cartaId();

        /**
         * Poder integer.
         *
         * @return the integer
         */
        Integer poder();

        /**
         * Esta oculta boolean.
         *
         * @return the boolean
         */
        Boolean estaOculta();

        /**
         * Esta habilitada boolean.
         *
         * @return the boolean
         */
        Boolean estaHabilitada();
    }
}
