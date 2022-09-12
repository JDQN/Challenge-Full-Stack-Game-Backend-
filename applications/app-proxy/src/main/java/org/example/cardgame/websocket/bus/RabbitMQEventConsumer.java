package org.example.cardgame.websocket.bus;


import org.example.cardgame.websocket.ApplicationConfig;
import org.example.cardgame.websocket.GsonEventSerializer;
import org.example.cardgame.websocket.SocketController;
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

    private final SocketController controller;

    public RabbitMQEventConsumer(GsonEventSerializer serializer,  SocketController controller) {
        this.serializer = serializer;
        this.controller = controller;
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "juego.websocket", durable = "true"),
            exchange = @Exchange(value = ApplicationConfig.EXCHANGE, type = "topic"),
            key = "cardgame.#"
    ))
    public void receivedMessage(Message<String> message) {
        var notification = Notification.from(message.getPayload());
        try {
            var event = serializer.deserialize(
                    notification.getBody(), Class.forName(notification.getType())
            );
            controller.send(event.aggregateRootId(), event);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}
