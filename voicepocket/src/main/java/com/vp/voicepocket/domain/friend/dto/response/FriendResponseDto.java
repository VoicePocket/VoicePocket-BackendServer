package com.vp.voicepocket.domain.friend.dto.response;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FriendResponseDto {
    private final Long id;
    private final Long request_from;
    private final Long request_to;
    private final Long status;
    private final LocalDateTime modifiedDate;
    private final LocalDateTime createdDate;

}
