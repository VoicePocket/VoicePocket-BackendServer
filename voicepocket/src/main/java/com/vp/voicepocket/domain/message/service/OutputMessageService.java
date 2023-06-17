package com.vp.voicepocket.domain.message.service;

import com.vp.voicepocket.domain.fcm.dto.FCMNotificationRequestDto;
import com.vp.voicepocket.domain.fcm.entity.FCMUserToken;
import com.vp.voicepocket.domain.fcm.exception.CFCMTokenNotFoundException;
import com.vp.voicepocket.domain.fcm.repository.FCMRepository;
import com.vp.voicepocket.domain.fcm.service.FCMNotificationService;
import com.vp.voicepocket.domain.message.model.OutputMessage;
import com.vp.voicepocket.domain.user.entity.User;
import com.vp.voicepocket.domain.user.exception.CUserNotFoundException;
import com.vp.voicepocket.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OutputMessageService {
    private static final Logger log = LoggerFactory.getLogger(OutputMessageService.class);
    private static final String TTS_REQUEST = "음성 합성 요청 알림";
    private static final String WAV_URL = "wavUrl";
    private static final String PUSH_TYPE = "ID";
    private final UserRepository userRepository;
    private final FCMRepository fcmRepository;
    private final FCMNotificationService fcmNotificationService;

    @RabbitListener(queues = "output.queue")
    public void consume(OutputMessage outputMessage) {
        // user 를 찾고
        User user = userRepository.findByEmail(outputMessage.getRequestFrom())
                .orElseThrow(CUserNotFoundException::new);

        // fcm token 을 얻고
        FCMUserToken fcmEntity = fcmRepository.findByUserId(user)
                .orElseThrow(CFCMTokenNotFoundException::new);
        String fcmToken = fcmEntity.getFireBaseToken();

        // fcm token 으로 push 알림을 보냄
        String messageBody = outputMessage.getRequestFrom() + " " + outputMessage.getResult();

        Map<String, String> data = new HashMap<>();
        data.put(WAV_URL, outputMessage.getUrl());
        data.put(PUSH_TYPE, "3");

        FCMNotificationRequestDto requestDto = FCMNotificationRequestDto.builder()
                .firebaseToken(fcmToken)
                .title(TTS_REQUEST)
                .body(messageBody)
                .build();

        fcmNotificationService.sendNotificationWithData(requestDto, data);
    }
}
