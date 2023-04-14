package com.vp.voicepocket.domain.message.controller;

import com.vp.voicepocket.domain.message.model.Message;
import com.vp.voicepocket.domain.message.service.MessageService;
import com.vp.voicepocket.global.common.response.model.SingleResult;
import com.vp.voicepocket.global.common.response.service.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Tag(name = "Message")
public class MessageController {
    private final MessageService messageService;
    private final ResponseService responseService;

    @Parameter(
            name = "X-AUTH-TOKEN",
            description = "로그인 성공 후 AccessToken",
            required = true,
            schema = @Schema(type = "string"),
            in = ParameterIn.HEADER)
    @Operation(summary = "TTS 요청", description = "Text To Speech 서비스를 요청합니다.")
    @PostMapping("/send")
    public SingleResult<Message> send(@Valid @RequestBody Message message) {
        messageService.sendMessage(message);
        return responseService.getSingleResult(message);
    }
}
