package com.vp.voicepocket.domain.message.controller;

import com.vp.voicepocket.domain.message.exception.CTaskNotFinishedException;
import com.vp.voicepocket.domain.message.exception.CTaskNotFoundException;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Tag(name = "Message")
@RequestMapping("/api")
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
    @PostMapping("/tts/send")
    public SingleResult<Message> send(@Valid @RequestBody Message message) {
        messageService.sendMessage(message);
        return responseService.getSingleResult(message);
    }

    @Parameter(
            name = "X-AUTH-TOKEN",
            description = "로그인 성공 후 AccessToken",
            required = true,
            schema = @Schema(type = "string"),
            in = ParameterIn.HEADER)
    @Operation(summary = "TTS Task id 체크", description = "uuid로 TTS task id를 얻어옵니다.")
    @GetMapping("/tts/status/uuid/{uuid}")
    public SingleResult<String> getTaskId(@PathVariable String uuid) {
        String taskId = messageService.getTaskId(uuid);
        if (taskId == null) throw new CTaskNotFoundException();

        return responseService.getSingleResult(taskId);
    }

    @Parameter(
            name = "X-AUTH-TOKEN",
            description = "로그인 성공 후 AccessToken",
            required = true,
            schema = @Schema(type = "string"),
            in = ParameterIn.HEADER)
    @Operation(summary = "TTS Task 체크", description = "task id로 TTS 작업의 진행상태를 봅니다.")
    @GetMapping("/tts/status/taskId/{taskId}")
    public SingleResult<String> getTaskStatus(@PathVariable String taskId) {
        String status = messageService.getTaskStatus(taskId);
        if (status == null) throw new CTaskNotFinishedException();

        return responseService.getSingleResult(status);
    }
}
