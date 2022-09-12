package org.example.cardgame.application.adapter.bus;


import org.example.cardgame.application.ApplicationConfig;
import org.example.cardgame.application.GsonEventSerializer;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;


@Component
public class RabbitMQEventConsumer {

    private final GsonEventSerializer serializer;

    private final ApplicationEventPublisher applicationEventPublisher;

    public RabbitMQEventConsumer(GsonEventSerializer serializer, ApplicationEventPublisher applicationEventPublisher) {
        this.serializer = serializer;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "juego.handles", durable = "true"),
            exchange = @Exchange(value = ApplicationConfig.EXCHANGE, type = "topic"),
            key = "cardgame.#"
    ))
    public void receivedMessage(Message<String> message) {
        var notification = Notification.from(message.getPayload());
        try {
            var event = serializer.deserialize(
                    notification.getBody(), Class.forName(notification.getType())
            );
            applicationEventPublisher.publishEvent(event);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}
