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
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;

    public FriendResponseDto(Friend friend) {
        this.id = friend.getId();
        this.request_from = friend.getRequest_from();
        this.request_to = friend.getRequest_to();
        this.status = friend.getStatus();
        this.createdDate = friend.getCreatedDate();
        this.modifiedDate = friend.getModifiedDate();
    }

    public FriendResponseDto(Long id, User request_from, User request_to, Long status, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.request_from = request_from;
        this.request_to = request_to;
        this.status = status;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
