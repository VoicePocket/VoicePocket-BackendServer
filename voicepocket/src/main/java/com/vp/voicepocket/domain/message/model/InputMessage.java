package com.vp.voicepocket.domain.message.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@Builder
@Schema(title = "Input Message 모델", description = "TTS 요청을 받을 Message 모델")
public class InputMessage {
    @NotNull
    @Schema(title = "Message type", description = "메시지 요청에 따른 타입", example = "ETL")
    private String type;

    @NotNull
    @Schema(title = "Request UUID", description = "TTS 요청에 대한 UUID", example = "550k8400-e29b-41d4-a716-446655440001")
    private String uuid;

    @NotNull
    @Schema(title = "Requesting User Email", description = "음성 합성 요청을 보낸 사용자의 이메일", example = "sender@gmail.com")
    private String requestFrom;

    @NotNull
    @Schema(title = "Requested User Email", description = "음성 합성 요청을 받은 사용자의 이메일", example = "reciever@gmail.com")
    private String requestTo;

    @NotNull
    @Schema(title = "Text", description = "합성을 원하는 문장", example = "테스트 문장입니다.")
    private String text;
}