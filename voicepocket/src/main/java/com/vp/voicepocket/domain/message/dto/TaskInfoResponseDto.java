package com.vp.voicepocket.domain.message.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TaskInfoResponseDto {
    private String taskId;
    private String status;
    private String result;
}
