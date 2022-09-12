package org.example.cardgame.domain;

import co.com.sofka.domain.generic.Entity;
import org.example.cardgame.domain.values.Carta;
import org.example.cardgame.domain.values.JugadorId;
import org.example.cardgame.domain.values.TableroId;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The type Tablero.
 */
public class Tablero extends Entity<TableroId> {
    private final Map<JugadorId, Set<Carta>> partida;
    private Integer tiempoEnSegundos;
    private Boolean estaHabilitado;

    /**
     * Instantiates a new Tablero.
     *
     * @param entityId   the entity id
     * @param jugadorIds the jugador ids
     */
    public Tablero(TableroId entityId, Set<JugadorId> jugadorIds) {
        super(entityId);
        this.partida = new HashMap<>();
        this.estaHabilitado = false;
        jugadorIds.forEach(jugadorId -> partida.put(jugadorId, new HashSet<>()));
    }

    /**
     * Ajustar tiempo.
     *
     * @param tiempo the tiempo
     */
    public void ajustarTiempo(Integer tiempo) {
        this.tiempoEnSegundos = tiempo;
    }


    /**
     * Tiempo integer.
     *
     * @return the integer
     */
    public Integer tiempo() {
        return tiempoEnSegundos;
    }

    /**
     * Adicionar partida.
     *
     * @param jugadorId the jugador id
     * @param carta     the carta
     */
    public void adicionarPartida(JugadorId jugadorId, Carta carta) {
        partida.getOrDefault(jugadorId, new HashSet<>()).add(carta);
    }

    /**
     * Quitar carta.
     *
     * @param jugadorId the jugador id
     * @param carta     the carta
     */
    public void quitarCarta(JugadorId jugadorId, Carta carta) {
        partida.getOrDefault(jugadorId, new HashSet<>()).remove(carta);
    }

    /**
     * Habilitar apuesta.
     */
    public void habilitarApuesta() {
        this.estaHabilitado = true;
    }

    /**
     * Inhabilitar apuesta.
     */
    public void inhabilitarApuesta() {
        this.estaHabilitado = false;
    }

    /**
     * Reiniciar partida.
     */
    public void reiniciarPartida() {
        partida.clear();
    }

    /**
     * Esta habilitado boolean.
     *
     * @return the boolean
     */
    public Boolean estaHabilitado() {
        return estaHabilitado;
    }

    /**
     * Partida map.
     *
     * @return the map
     */
    public Map<JugadorId, Set<Carta>> partida() {
        return partida;
    }
}
