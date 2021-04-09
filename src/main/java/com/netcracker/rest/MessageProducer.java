package com.netcracker.rest;

import com.netcracker.model.dto.MessageDto;
import com.netcracker.model.entity.Message;
import com.netcracker.service.ProducerService;
import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/produce")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageProducer {

    private final ProducerService producerService;
    private final ModelMapper mapper;
    private final Logger logger;

    @Inject
    public MessageProducer(ProducerService producerService, ModelMapper mapper,
                           Logger logger) {
        this.producerService = producerService;
        this.mapper = mapper;
        this.logger = logger;
    }

    @GET
    public String getStatus() {
        return "Active";
    }

    @POST
    public void produceMessage(MessageDto message) {
        logger.debugv("Call produce message. Message {}", message);
        Message entity = mapper.map(message, Message.class);
        producerService.send(entity);
        logger.debugv("Message entity {} sent", entity);
    }
}