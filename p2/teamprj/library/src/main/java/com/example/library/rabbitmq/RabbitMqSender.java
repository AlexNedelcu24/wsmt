package com.example.library.rabbitmq;

import com.example.library.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqSender {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMqSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqSender.class);

    public String send(Message message) {
        logger.info(new StringBuilder().append("RabbitMQ Sender - Sending car: ").append(message).toString());
        return (String) rabbitTemplate.convertSendAndReceive(exchange,routingkey, message);
    }
}
