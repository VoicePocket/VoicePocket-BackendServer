package com.vp.voicepocket.domain.message.service;

import com.vp.voicepocket.domain.firebase.service.FirestoreService;
import com.vp.voicepocket.domain.message.model.OutputMessage;
import com.vp.voicepocket.domain.user.entity.User;
import com.vp.voicepocket.domain.user.exception.CUserNotFoundException;
import com.vp.voicepocket.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutputMessageService {
    private static final Logger log = LoggerFactory.getLogger(OutputMessageService.class);
    private final UserRepository userRepository;
    private final FirestoreService firestoreService;

    @RabbitListener(queues = "output.queue")
    public void consume(OutputMessage outputMessage) {
        User user = userRepository.findByEmail(outputMessage.getRequestFrom())
                .orElseThrow(CUserNotFoundException::new);

        firestoreService.addWavUrl(user.getEmail(), outputMessage.getRequestTo(), outputMessage.getUrl(), outputMessage.getUuid());
    }
}
