package com.vp.voicepocket.domain.message.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "Output Message 모델", description = "TTS 요청의 수행 결과를 받을 Message 모델")
public class OutputMessage {
    @NotNull
    @Schema(title = "Requesting User", description = "음성 합성을 요청한 사용자")
    private String requestFrom;

    @NotNull
    @Schema(title = "TTS 요청에 대한 결과", description = "TTS 요청에 대한 성공/실패 여부")
    private String result;

    @NotNull
    @Schema(title = "wav url", description = "합성된 음성 url")
    private String url;
}
