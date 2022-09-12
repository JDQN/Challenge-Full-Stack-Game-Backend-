package org.example.cardgame.application.adapter.bus;

import co.com.sofka.domain.generic.DomainEvent;

public class ErrorEvent extends DomainEvent {
    private final String classType;
    private final String message;

    public ErrorEvent(String classType, String message){
        super("cargame.error");
        this.classType = classType;
        this.message = message;
    }

    public String getClassType() {
        return classType;
    }

    public String getMessage() {
        return message;
    }
}