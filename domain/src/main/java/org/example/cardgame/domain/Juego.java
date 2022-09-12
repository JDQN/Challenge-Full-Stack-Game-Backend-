package org.example.cardgame.domain;

import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import org.example.cardgame.domain.events.*;
import org.example.cardgame.domain.values.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The type Juego.
 */
public class Juego extends AggregateEvent<JuegoId> {
    /**
     * The Jugadores.
     */
    protected Map<JugadorId, Jugador> jugadores;
    /**
     * The Tablero.
     */
    protected Tablero tablero;
    /**
     * The Ganador.
     */
    protected Jugador ganador;
    /**
     * The Ronda.
     */
    protected Ronda ronda;
    /**
     * The Jugador principal.
     */
    protected JugadorId jugadorPrincipal;

    /**
     * Instantiates a new Juego.
     *
     * @param id             the id
     * @param uid            the uid
     * @param jugadorFactory the jugador factory
     */
    public Juego(JuegoId id, JugadorId uid, JugadorFactory jugadorFactory) {
        super(id);
        appendChange(new JuegoCreado(uid)).apply();
        jugadorFactory.getJugadores()
                .forEach(jugador ->
                        appendChange(new JugadorAgregado(jugador.identity(), jugador.alias(), jugador.mazo()))
                );
        subscribe(new JuegoEventChange(this));
    }

    private Juego(JuegoId id) {
        super(id);
        subscribe(new JuegoEventChange(this));
    }

    /**
     * From juego.
     *
     * @param juegoId the juego id
     * @param events  the events
     * @return the juego
     */
    public static Juego from(JuegoId juegoId, List<DomainEvent> events) {
        var juego = new Juego(juegoId);
        events.forEach(juego::applyEvent);
        return juego;
    }

    /**
     * Crear tablero.
     */
    public void crearTablero() {
        var id = new TableroId();
        appendChange(new TableroCreado(id, jugadores.keySet())).apply();
    }

    /**
     * Crear ronda.
     *
     * @param ronda  the ronda
     * @param tiempo the tiempo
     */
    public void crearRonda(Ronda ronda, Integer tiempo) {
        appendChange(new RondaCreada(ronda, tiempo)).apply();
    }

    /**
     * Cambiar tiempo del tablero.
     *
     * @param tableroId the tablero id
     * @param tiempo    the tiempo
     */
    public void cambiarTiempoDelTablero(TableroId tableroId, Integer tiempo) {
        appendChange(new TiempoCambiadoDelTablero(tableroId, tiempo)).apply();
    }

    /**
     * Poner carta en tablero.
     *
     * @param tableroId the tablero id
     * @param jugadorId the jugador id
     * @param carta     the carta
     */
    public void ponerCartaEnTablero(TableroId tableroId, JugadorId jugadorId, Carta carta) {
        appendChange(new CartaPuestaEnTablero(tableroId, jugadorId, carta)).apply();
        appendChange(new CartaQuitadaDelMazo(jugadorId, carta)).apply();
    }

    /**
     * Quitar carta en tablero.
     *
     * @param tableroId the tablero id
     * @param jugadorId the jugador id
     * @param carta     the carta
     */
    public void quitarCartaEnTablero(TableroId tableroId, JugadorId jugadorId, Carta carta) {
        appendChange(new CartaQuitadaDelTablero(tableroId, jugadorId, carta)).apply();
    }

    /**
     * Ronda ronda.
     *
     * @return the ronda
     */
    public Ronda ronda() {
        return ronda;
    }

    /**
     * Tablero tablero.
     *
     * @return the tablero
     */
    public Tablero tablero() {
        return tablero;
    }

    /**
     * Jugadores map.
     *
     * @return the map
     */
    public Map<JugadorId, Jugador> jugadores() {
        return jugadores;
    }

    /**
     * Iniciar ronda.
     */
    public void iniciarRonda() {
        appendChange(new RondaIniciada()).apply();
    }


    /**
     * Asignar cartas a ganador.
     *
     * @param ganadorId     the ganador id
     * @param puntos        the puntos
     * @param cartasApuesta the cartas apuesta
     */
    public void asignarCartasAGanador(JugadorId ganadorId, Integer puntos, Set<Carta> cartasApuesta) {
        appendChange(new CartasAsignadasAJugador(ganadorId, puntos, cartasApuesta)).apply();
    }

    /**
     * Terminar ronda.
     *
     * @param tableroId  the tablero id
     * @param jugadorIds the jugador ids
     */
    public void terminarRonda(TableroId tableroId, Set<JugadorId> jugadorIds) {
        appendChange(new RondaTerminada(tableroId, jugadorIds)).apply();

    }

    /**
     * Finalizar juego.
     *
     * @param jugadorId the jugador id
     * @param alias     the alias
     */
    public void finalizarJuego(JugadorId jugadorId, String alias) {
        appendChange(new JuegoFinalizado(jugadorId, alias)).apply();

    }
}
