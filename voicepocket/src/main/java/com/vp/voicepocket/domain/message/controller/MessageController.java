package com.vp.voicepocket.domain.message.controller;

import com.vp.voicepocket.domain.message.model.Message;
import com.vp.voicepocket.domain.message.service.MessageService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/send")
    @ResponseBody
    public String send(@RequestBody Message message) {
        messageService.sendMessage(message);
        return "published!";
    }
}
