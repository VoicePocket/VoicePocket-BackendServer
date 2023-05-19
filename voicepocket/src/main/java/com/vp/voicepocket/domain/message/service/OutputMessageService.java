package com.vp.voicepocket.domain.message.service;

import com.vp.voicepocket.domain.message.model.OutputMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutputMessageService {
    private static final Logger log = LoggerFactory.getLogger(OutputMessageService.class);

    @RabbitListener(queues = "output.queue")
    public void consume(OutputMessage outputMessage) {  // TODO: push message to FCM
        log.info(outputMessage.getUrl());
        System.out.println(outputMessage.getUrl());
    }
}
