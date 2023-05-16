package com.vp.voicepocket.domain.friend.dto.response;


import com.vp.voicepocket.domain.friend.entity.Friend;
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
    // 생각해보니 오직 Friend entity가 갖고 있는 필드들에 대한 정보만 반환하는 것이 좋아보임..
}
