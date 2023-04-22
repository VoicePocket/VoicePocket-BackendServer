package com.vp.voicepocket.domain.message.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TaskIdResponseDto {
    private String uuid;
    private String taskId;
}
