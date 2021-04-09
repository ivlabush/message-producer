package com.netcracker.service;

import com.netcracker.model.entity.Message;

public interface ProducerService {

    void send(Message message);
}
