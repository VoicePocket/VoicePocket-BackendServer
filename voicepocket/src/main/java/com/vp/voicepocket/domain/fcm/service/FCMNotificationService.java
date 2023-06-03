package com.vp.voicepocket.domain.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.vp.voicepocket.domain.fcm.dto.FCMNotificationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class FCMNotificationService {
    private final FirebaseMessaging firebaseMessaging;

    public void sendNotificationByToken(FCMNotificationRequestDto requestDto){
        Notification notification = Notification.builder()
                .setTitle(requestDto.getTitle())
                .setBody(requestDto.getBody())
                .build();

        Message message = Message.builder()
                .setToken(requestDto.getFirebaseToken())
                .setNotification(notification)
                .build();

        try {
            firebaseMessaging.send(message);
            log.info("push 성공");
        } catch (FirebaseMessagingException e) {
            log.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * push message 와 data 를 함께 보내는 메소드
     * @param requestDto
     * @param data
     */
    public void sendNotificationWithData(FCMNotificationRequestDto requestDto, Map<String, String> data) {
        Notification notification = Notification.builder()
                .setTitle(requestDto.getTitle())
                .setBody(requestDto.getBody())
                .build();

        Message message = Message.builder()
                .setToken(requestDto.getFirebaseToken())
                .setNotification(notification)
                .putAllData(data)   // data 추가
                .build();

        try {
            firebaseMessaging.send(message);
            log.info("push 성공");
        } catch (FirebaseMessagingException e) {
            log.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
