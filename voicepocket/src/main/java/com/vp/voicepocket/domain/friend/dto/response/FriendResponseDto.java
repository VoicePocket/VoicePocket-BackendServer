package com.vp.voicepocket.domain.friend.dto.response;


import com.vp.voicepocket.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FriendResponseDto {
    private final Long id;
    private final User request_from;
    private final User request_to;
    private final Long status;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;

}
