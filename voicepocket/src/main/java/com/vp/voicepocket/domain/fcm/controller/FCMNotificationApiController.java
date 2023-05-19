package com.vp.voicepocket.domain.fcm.controller;

import com.vp.voicepocket.domain.fcm.dto.FCMNotificationRequestDto;
import com.vp.voicepocket.domain.fcm.service.FCMNotificationService;
import com.vp.voicepocket.global.common.response.model.CommonResult;
import com.vp.voicepocket.global.common.response.service.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name="FCM")
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class FCMNotificationApiController {

    private final FCMNotificationService fcmNotificationService;
    private final ResponseService responseService;

    @Operation(summary="알람 보내기", description = "Send Notification")
    @PostMapping("/FCM/send")
    public CommonResult sendNotification(@RequestBody FCMNotificationRequestDto requestDto){
        fcmNotificationService.sendNotificationByToken(requestDto);
        return responseService.getSuccessResult();
    }

}
