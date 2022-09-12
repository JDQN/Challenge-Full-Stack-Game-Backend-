package org.example.cardgame.application.handle;

import org.example.cardgame.domain.command.*;
import org.example.cardgame.usecase.usecase.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
@Configuration
public class CommandHandle {
    private final IntegrationHandle integrationHandle;
    private final ErrorHandler errorHandler;

    public CommandHandle(IntegrationHandle integrationHandle, ErrorHandler errorHandler) {
        this.integrationHandle = integrationHandle;
        this.errorHandler = errorHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> crear(CrearJuegoUseCase usecase) {

        return route(
                POST("/juego/crear").and(accept(MediaType.APPLICATION_JSON)),
                request -> usecase.andThen(integrationHandle)
                        .apply(request.bodyToMono(CrearJuegoCommand.class))
                        .then(ServerResponse.ok().build())
                        .onErrorResume(errorHandler::badRequest)

        );
    }


    @Bean
    public RouterFunction<ServerResponse> iniciar(IniciarJuegoUseCase usecase) {
        return route(
                POST("/juego/iniciar").and(accept(MediaType.APPLICATION_JSON)),
                request -> usecase.andThen(integrationHandle)
                        .apply(request.bodyToMono(IniciarJuegoCommand.class))
                        .then(ServerResponse.ok().build())
                        .onErrorResume(errorHandler::badRequest)

        );
    }

    @Bean
    public RouterFunction<ServerResponse> iniciarRonda(IniciarRondaUseCase usecase) {
        return route(
                POST("/juego/ronda/iniciar").and(accept(MediaType.APPLICATION_JSON)),
                request -> usecase.andThen(integrationHandle)
                        .apply(request.bodyToMono(IniciarRondaCommand.class))
                        .then(ServerResponse.ok().build())
                        .onErrorResume(errorHandler::badRequest)

        );
    }


    @Bean
    public RouterFunction<ServerResponse> poner(PonerCartaEnTableroUseCase usecase) {
        return route(
                POST("/juego/poner").and(accept(MediaType.APPLICATION_JSON)),
                request -> usecase.andThen(integrationHandle)
                        .apply(request.bodyToMono(PonerCartaEnTablero.class))
                        .then(ServerResponse.ok().build())
                        .onErrorResume(errorHandler::badRequest)

        );
    }


    @Bean
    public RouterFunction<ServerResponse> crearRonda(CrearRondaUseCase usecase) {
        return route(
                POST("/juego/crear/ronda").and(accept(MediaType.APPLICATION_JSON)),
                request -> usecase.andThen(integrationHandle)
                        .apply(request.bodyToMono(CrearRondaCommand.class))
                        .then(ServerResponse.ok().build())
                        .onErrorResume(errorHandler::badRequest)

        );
    }



}
