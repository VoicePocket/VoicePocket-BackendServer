package com.vp.voicepocket.domain.message.controller;

import com.vp.voicepocket.domain.message.dto.TTSRequestDto;
import com.vp.voicepocket.domain.message.service.InputMessageService;
import com.vp.voicepocket.global.common.response.model.CommonResult;
import com.vp.voicepocket.global.common.response.service.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Tag(name = "Message")
@RequestMapping("/api")
public class MessageController {
    private final InputMessageService inputMessageService;
    private final ResponseService responseService;

    @Parameter(
            name = "X-AUTH-TOKEN",
            description = "로그인 성공 후 AccessToken",
            required = true,
            schema = @Schema(type = "string"),
            in = ParameterIn.HEADER)
    @Operation(summary = "TTS 요청", description = "Text To Speech 서비스를 요청합니다.")
    @PostMapping("/tts/send")
    public CommonResult send(
            @RequestHeader("X-AUTH-TOKEN") String accessToken,
            @Valid @RequestBody TTSRequestDto ttsRequestDto) {
        inputMessageService.sendMessage(accessToken, ttsRequestDto);
        return responseService.getSuccessResult();
    }
}
