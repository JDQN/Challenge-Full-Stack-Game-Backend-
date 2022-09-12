package org.example.cardgame.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class AppService {
    public static void main(String[] args) {
        SpringApplication.run(AppService.class, args);
    }
}
