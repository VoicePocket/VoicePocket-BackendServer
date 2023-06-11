package com.vp.voicepocket.domain.fcm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class FCMNotificationRequestDto {
    private String firebaseToken;
    private String title;
    private String body;
    private Map<?, ?> data;
    //private String image;

    @Builder
    public FCMNotificationRequestDto(String firebaseToken, String title, String body, Map<?, ?> data)  {
        this.firebaseToken = firebaseToken;
        this.title = title;
        this.body = body;
        //this.image = image;
        this.data = data;
    }
}
