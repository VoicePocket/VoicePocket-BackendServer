package com.vp.voicepocket.domain.message.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class Message {
    private String type;
    private String uuid;
    private String email;
    private String text;
}