package com.vp.voicepocket.domain.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.vp.voicepocket.domain.fcm.dto.FCMNotificationRequestDto;
import com.vp.voicepocket.domain.user.entity.User;
import com.vp.voicepocket.domain.user.exception.CUserNotFoundException;
import com.vp.voicepocket.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FCMNotificationService {
    private final FirebaseMessaging firebaseMessaging;
    //private final UserRepository userRepository;

    public void sendNotificationByToken(FCMNotificationRequestDto requestDto){
        //User user = userRepository.findById(requestDto.getFirebaseToken()).orElseThrow(CUserNotFoundException::new);
        Notification notification = Notification.builder().setTitle(requestDto.getTitle()).setBody(requestDto.getBody()).build();
        Message message = Message.builder().setToken(requestDto.getFirebaseToken()).setNotification(notification).build();
        try {
            firebaseMessaging.send(message);
            log.info("push 성공");
        } catch (FirebaseMessagingException e) {
            log.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
