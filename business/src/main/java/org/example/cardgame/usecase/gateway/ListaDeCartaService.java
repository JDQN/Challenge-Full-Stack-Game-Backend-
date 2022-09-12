package org.example.cardgame.usecase.gateway;


import org.example.cardgame.usecase.gateway.model.CartaMaestra;
import reactor.core.publisher.Flux;


public interface ListaDeCartaService {
    Flux<CartaMaestra> obtenerCartasDeMarvel();
}
