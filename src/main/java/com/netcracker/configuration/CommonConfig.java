package com.netcracker.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.service.ProducerService;
import com.netcracker.service.impl.NativeProducerServiceImpl;
import com.rabbitmq.client.ConnectionFactory;
import io.quarkus.arc.DefaultBean;
import lombok.RequiredArgsConstructor;
import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CommonConfig {

    private final Logger logger;

    @Produces
    public ModelMapper mapper() {
        return new ModelMapper();
    }

    @Produces
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Produces
    public ConnectionFactory factory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        return factory;
    }

    @Produces
    @DefaultBean
    public ProducerService producerService() {
        return new NativeProducerServiceImpl(objectMapper(), factory(), logger);
    }
}
