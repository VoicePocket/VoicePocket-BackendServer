package com.vp.voicepocket.domain.message.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "Output Message 모델", description = "TTS 요청의 수행 결과를 받을 Message 모델")
public class OutputMessage {
    @NotNull
    @Schema(title = "wav file url", description = "생성된 wav 파일의 url")
    private String url;
}
