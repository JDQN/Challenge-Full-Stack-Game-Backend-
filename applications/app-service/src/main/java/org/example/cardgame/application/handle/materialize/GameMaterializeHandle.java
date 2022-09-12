package org.example.cardgame.application.handle.materialize;

import co.com.sofka.domain.generic.DomainEvent;

import co.com.sofka.domain.generic.Identity;
import org.bson.Document;
import org.example.cardgame.domain.events.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.Instant;
import java.util.HashMap;
import java.util.stream.Collectors;

@EnableAsync
@Configuration
public class GameMaterializeHandle {
    private static final String COLLECTION_VIEW = "gameview";

    private final ReactiveMongoTemplate template;

    public GameMaterializeHandle(ReactiveMongoTemplate template) {
        this.template = template;
    }


    @EventListener
    public void handleJuegoCreado(JuegoCreado event) {
        var data = new HashMap<>();
        data.put("_id", event.aggregateRootId());
        data.put("fecha", Instant.now());
        data.put("uid", event.getJugadorPrincipal().value());
        data.put("iniciado", false);
        data.put("finalizado", false);
        data.put("cantidadJugadores", 0);
        data.put("jugadores", new HashMap<>());
        template.save(data, COLLECTION_VIEW).block();
    }


    @EventListener
    public void handleJugadorAgregado(JugadorAgregado event) {
        var data = new Update();
        data.set("fecha", Instant.now());
        data.set("jugadores."+event.getJuegoId().value()+".alias", event.getAlias());
        data.set("jugadores."+event.getJuegoId().value()+".jugadorId", event.getJuegoId().value());
        data.inc("cantidadJugadores");
        template.updateFirst(getFilterByAggregateId(event), data, COLLECTION_VIEW).block();
    }

    @EventListener
    public void handleTableroCreado(TableroCreado event) {
        var data = new Update();
        var jugadores = event.getJugadorIds().stream()
                .map(Identity::value)
                .collect(Collectors.toList());

        data.set("fecha", Instant.now());
        data.set("tablero.id", event.getTableroId().value());
        data.set("tablero.cartas", new HashMap<>());
        data.set("tablero.jugadores", jugadores);
        data.set("tablero.habilitado", false);
        data.set("iniciado", true);
        template.updateFirst(getFilterByAggregateId(event),data, COLLECTION_VIEW)
                .block();
    }

    @EventListener
    public void handleCartaPuestaEnTablero(CartaPuestaEnTablero event) {
        var data = new Update();
        var document = new Document();
        var carta = event.getCarta().value();
        var jugadorId = event.getJugadorId().value();
        document.put("cartaId", carta.cartaId().value());
        document.put("estaOculta", carta.estaOculta());
        document.put("poder", carta.poder());
        document.put("estaHabilitada", carta.estaHabilitada());
        document.put("jugadorId", jugadorId);

        data.set("fecha", Instant.now());
        data.push("tablero.cartas."+jugadorId, document);
        template.updateFirst(getFilterByAggregateId(event),data, COLLECTION_VIEW).block();
    }

    @EventListener
    public void handleRondaCreada(RondaCreada event) {
        var data = new Update();
        var ronda = event.getRonda().value();
        var document = new Document();
        var jugadores = ronda.jugadores().stream()
                .map(Identity::value)
                .collect(Collectors.toList());

        document.put("jugadores", jugadores);
        document.put("numero", ronda.numero());

        data.set("fecha", Instant.now());
        data.set("tiempo", event.getTiempo());
        data.set("ronda", document);
        template.updateFirst(getFilterByAggregateId(event),data, COLLECTION_VIEW).block();
    }

    @EventListener
    public void handleTiempoCambiadoDelTablero(TiempoCambiadoDelTablero event){
        var data = new Update();
        data.set("fecha", Instant.now());
        data.set("tiempo", event.getTiempo());
        data.set("ronda.estaIniciada", true);

        template.updateFirst(getFilterByAggregateId(event),data, COLLECTION_VIEW).block();
    }

    @EventListener
    public void handleRondaTerminada(RondaTerminada event){
        var data = new Update();
        data.set("fecha", Instant.now());
        data.set("tiempo", 0);
        data.set("ronda.estaIniciada", false);
        data.set("tablero.cartas", new HashMap<>());
        data.set("tablero.habilitado", false);
        data.set("ronda.jugadores", event.getJugadorIds());

        template.updateFirst(getFilterByAggregateId(event),data, COLLECTION_VIEW).block();
    }

    @EventListener
    public void handleRondaIniciada(RondaIniciada event){
        var data = new Update();
        data.set("fecha", Instant.now());
        data.set("tablero.habilitado", true);

        template.updateFirst(getFilterByAggregateId(event),data, COLLECTION_VIEW).block();
    }

    @EventListener
    public void handleJuegoFinalizado(JuegoFinalizado event){
        var data = new Update();
        data.set("fecha", Instant.now());
        data.set("ganador.alias", event.getAlias());
        data.set("ganador.jugadorId", event.getJugadorId().value());
        data.set("finalizado", true);

        template.updateFirst(getFilterByAggregateId(event),data, COLLECTION_VIEW).block();
    }


    private Query getFilterByAggregateId(DomainEvent event) {
        return new Query(
                Criteria.where("_id").is(event.aggregateRootId())
        );
    }
}
