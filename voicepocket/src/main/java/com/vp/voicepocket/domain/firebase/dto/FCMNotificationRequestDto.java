package com.vp.voicepocket.domain.firebase.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FCMNotificationRequestDto {
    private String firebaseToken;
    private String title;
    private String body;
    //private String image;

    @Builder
    public FCMNotificationRequestDto(String firebaseToken, String title, String body)  {
        this.firebaseToken = firebaseToken;
        this.title = title;
        this.body = body;
        //this.image = image;
    }
}
