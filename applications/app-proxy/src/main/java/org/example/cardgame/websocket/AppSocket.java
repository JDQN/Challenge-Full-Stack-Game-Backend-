package org.example.cardgame.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableWebSocket
public class AppSocket {
    public static void main(String[] args) {
        SpringApplication.run(AppSocket.class, args);
    }
}
