package org.example.cardgame.application.handle.materialize;

import org.bson.Document;
import org.example.cardgame.application.handle.model.MazoViewModel;
import org.example.cardgame.domain.events.CartaQuitadaDelMazo;
import org.example.cardgame.domain.events.CartasAsignadasAJugador;
import org.example.cardgame.domain.events.JugadorAgregado;

import org.example.cardgame.domain.values.Carta;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@EnableAsync
@Configuration
public class MazoMaterializeHandle {
    private static final String COLLECTION_VIEW = "mazoview";

    private final ReactiveMongoTemplate template;

    public MazoMaterializeHandle(ReactiveMongoTemplate template) {
        this.template = template;
    }

    //TODO: handle Jugador Agregado
    @EventListener
    public void handleJugadorAgregado(JugadorAgregado event) {
        var mazo = event.getMazo().value();
        var data = new Document();
        var cartas = new ArrayList<>();
        data.put("uid", event.getJuegoId().value());
        data.put("juegoId", event.aggregateRootId());
        data.put("cantidad", mazo.cartas().size());
        data.put("fecha", Instant.now());

        mazo.cartas().forEach(new Consumer<Carta>() {
            @Override
            public void accept(Carta carta) {
                var documentCarta = new Document();
                documentCarta.put("poder", carta.value().poder());
                documentCarta.put("cartaId", carta.value().cartaId().value());
                documentCarta.put("estaHabilitada", carta.value().estaHabilitada());
                documentCarta.put("estaOculta", carta.value().estaOculta());
                cartas.add(documentCarta);
            }
        });
        data.put("cartas", cartas);

        template.save(data, COLLECTION_VIEW).block();
    }

    @EventListener
    public void handleCartaQuitadaDelMazo(CartaQuitadaDelMazo event){
        var query = filterByUidAndId(event.getJugadorId().value(), event.aggregateRootId());
        template.findOne(query, MazoViewModel.class, COLLECTION_VIEW)
                .subscribe(mazoViewModel -> {
                    var data = new Update();
                    var cartaSet =  mazoViewModel.getCartas();
                    cartaSet.removeIf(carta -> event.getCarta().value().cartaId().value().equals(carta.getCartaId()));
                    data.set("cartas", cartaSet);
                    data.set("fecha", Instant.now());
                    template.updateFirst(query, data, COLLECTION_VIEW).block();
                });
    }

    @EventListener
    public void handleCartasAsignadasAJugador(CartasAsignadasAJugador event){
        var query = filterByUidAndId(event.getGanadorId().value(), event.aggregateRootId());
        var data = new Update();

        var mazo = event.getCartasApuesta().stream().map(carta -> {
            var c = new MazoViewModel.Carta();
            c.setCartaId(carta.value().cartaId().value());
            c.setEstaHabilitada(carta.value().estaHabilitada());
            c.setEstaOculta(carta.value().estaOculta());
            c.setPoder(carta.value().poder());
            return c;
        }).collect(Collectors.toSet());

        var mazoViewModel = template.findOne(query, MazoViewModel.class, COLLECTION_VIEW).block();
        Optional.ofNullable(mazoViewModel).ifPresent((model) -> {
            var cartaSet =  model.getCartas();
            cartaSet.addAll(mazo);
            data.set("cartas", cartaSet);
            data.set("fecha", Instant.now());
            template.updateFirst(query, data, COLLECTION_VIEW).block();
        });
    }

    private Query filterByUidAndId(String uid, String juegoId) {
        return new Query(
                Criteria.where("juegoId").is(juegoId).and("uid").is(uid)
        );
    }


}
