package com.vp.voicepocket.domain.friend.dto.response;


import com.vp.voicepocket.domain.friend.entity.Status;
import com.vp.voicepocket.domain.user.dto.response.UserResponseDto;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class FriendResponseDto {
    private final Long id;
    private final UserResponseDto request_from;
    private final UserResponseDto request_to;
    private final Status status;
    // 생각해보니 오직 Friend entity 가 갖고 있는 필드들에 대한 정보만 반환하는 것이 좋아보임..
}
