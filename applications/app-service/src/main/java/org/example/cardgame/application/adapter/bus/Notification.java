package org.example.cardgame.application.adapter.bus;

import com.google.gson.Gson;

import java.time.Instant;

public class Notification {
    private final String type;
    private final String body;
    private final Instant instant;

    public Notification(String type, String body) {
        this.type = type;
        this.body = body;
        this.instant = Instant.now();
    }
    private Notification(){
        this(null,null);
    }

    public String getType() {
        return type;
    }

    public String getBody() {
        return body;
    }

    public Instant getInstant() {
        return instant;
    }

    public Notification deserialize(String aSerialization) {
        return  new Gson().fromJson(aSerialization, Notification.class);
    }

    public String serialize() {
        return new Gson().toJson(this);
    }

    public static Notification from(String aNotification){
        return new Notification().deserialize(aNotification);
    }
}
