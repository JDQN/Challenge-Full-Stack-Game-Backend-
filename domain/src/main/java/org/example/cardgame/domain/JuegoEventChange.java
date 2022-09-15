package org.example.cardgame.domain;

import co.com.sofka.domain.generic.EventChange;
import org.example.cardgame.domain.events.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

/**
 * The type Juego event change.
 */
public class JuegoEventChange extends EventChange {
	/**
	 * Instantiates a new Juego event change.
	 *
	 * @param juego the juego
	 */
	public JuegoEventChange(Juego juego) {

		apply((JuegoCreado event) -> {
			juego.jugadores = new HashMap<>();
			juego.jugadorPrincipal = event.getJugadorPrincipal();
		});

		apply((JugadorAgregado event) -> {
			juego.jugadores.put(event.getJuegoId(),
				 new Jugador(event.getJuegoId(), event.getAlias(), event.getMazo())
			);
		});

		apply((RondaCreada event) -> {
			if (Objects.isNull(juego.tablero)) {
				throw new IllegalArgumentException("Debe existir el tablero primero");
			}
			juego.ronda = event.getRonda();
			juego.tablero.ajustarTiempo(event.getTiempo());
		});

		apply((TableroCreado event) -> {
			juego.tablero = new Tablero(event.getTableroId(), event.getJugadorIds());
		});

		apply((TiempoCambiadoDelTablero event) -> {
			juego.tablero.ajustarTiempo(event.getTiempo());
		});

		apply((CartaPuestaEnTablero event) -> {
			if(Boolean.FALSE.equals(juego.tablero.estaHabilitado())){
				throw new IllegalArgumentException("No se puedo apostar porque el tablero no esta habilitado");
			}
			juego.tablero.adicionarPartida(event.getJugadorId(), event.getCarta());
		});

		apply((CartaQuitadaDelTablero event) -> {
			juego.tablero.quitarCarta(event.getJugadorId(), event.getCarta());
		});

		apply((CartaQuitadaDelMazo event) -> {
			juego.jugadores.get(event.getJugadorId()).quitarCartaDeMazo(event.getCarta());
		});

		apply((RondaIniciada event) -> {
			if(Objects.isNull(juego.ronda)){
				throw new IllegalArgumentException("Debe existir una ronda creada");
			}
			juego.ronda = juego.ronda.iniciarRonda();
			juego.tablero.habilitarApuesta();
			juego.tablero.partida().forEach((key, Value) -> juego.tablero.partida().put(key,new HashSet<>()));
		});

		apply((RondaTerminada event) -> {
			juego.ronda = juego.ronda.terminarRonda();
			juego.tablero.inhabilitarApuesta();
		});

		apply((CartasAsignadasAJugador event) -> {
			var jugador = juego.jugadores().get(event.getGanadorId());
			event.getCartasApuesta().forEach(jugador::agregarCartaAMazo);
		});

		apply((JuegoFinalizado event) -> {
			juego.ganador = juego.jugadores.get(event.getJugadorId());
		});

	}
}

