package com.netcracker.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.model.entity.Message;
import com.netcracker.service.ProducerService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ProducerServiceImpl implements ProducerService {

    private static final String QUEUE_NAME = "message-queue";
    private final ObjectMapper mapper;
    private final ConnectionFactory factory;
    private final Logger logger;

    @Override
    public void send(Message message) {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            String stringMessage = mapper.writeValueAsString(message);
            channel.basicPublish("", QUEUE_NAME, null, stringMessage.getBytes());
            logger.debugv("Published message {} to queue {}", stringMessage, QUEUE_NAME);
        } catch (TimeoutException | IOException e) {
            logger.error("Exception occurred on event publishing. Exception: ", e);
        }
    }
}
