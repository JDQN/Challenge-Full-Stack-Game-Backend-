package org.example.cardgame.application.handle.usecase;


import org.example.cardgame.application.handle.IntegrationHandle;
import org.example.cardgame.domain.events.RondaIniciada;
import org.example.cardgame.usecase.usecase.IniciarCuentaRegresivaUseCase;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import reactor.core.publisher.Mono;


@EnableAsync
@Configuration
public class CuentaRegresivaEventHandle {

    private final IniciarCuentaRegresivaUseCase usecase;

    private final IntegrationHandle handle;

    public CuentaRegresivaEventHandle(IniciarCuentaRegresivaUseCase usecase, IntegrationHandle handle) {
        this.usecase = usecase;
        this.handle = handle;
    }

    @Async
    @EventListener
    public void handleIniciarCuentaRegresiva(RondaIniciada event) {
        handle.apply(usecase.apply(Mono.just(event))).block();
    }


}