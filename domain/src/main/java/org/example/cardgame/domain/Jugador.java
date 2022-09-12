package org.example.cardgame.domain;

import co.com.sofka.domain.generic.Entity;
import org.example.cardgame.domain.values.Carta;
import org.example.cardgame.domain.values.JugadorId;
import org.example.cardgame.domain.values.Mazo;

import java.util.Objects;

/**
 * The type Jugador.
 */
public class Jugador extends Entity<JugadorId> {
    private final String email;
    private Mazo mazo;

    /**
     * Instantiates a new Jugador.
     *
     * @param entityId the entity id
     * @param email    the email
     * @param mazo     the mazo
     */
    public Jugador(JugadorId entityId, String email, Mazo mazo) {
        super(entityId);
        this.email = Objects.requireNonNull(email);
        this.mazo = Objects.requireNonNull(mazo);
        if (mazo.value().cantidad() <= 0) {
            throw new IllegalArgumentException("El mazo debe contener cartas ");
        }
    }

    /**
     * Agregar carta a mazo.
     *
     * @param carta the carta
     */
    public void agregarCartaAMazo(Carta carta) {
        mazo = mazo.nuevaCarta(carta);
    }

    /**
     * Quitar carta de mazo.
     *
     * @param carta the carta
     */
    public void quitarCartaDeMazo(Carta carta) {
        mazo = mazo.retirarCarta(carta);
    }

    /**
     * Alias string.
     *
     * @return the string
     */
    public String alias() {
        return email;
    }

    /**
     * Mazo mazo.
     *
     * @return the mazo
     */
    public Mazo mazo() {
        return mazo;
    }
}
