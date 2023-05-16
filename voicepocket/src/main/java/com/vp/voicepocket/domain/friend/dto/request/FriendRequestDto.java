package com.vp.voicepocket.domain.friend.dto.request;

import com.vp.voicepocket.domain.friend.entity.Friend;
import com.vp.voicepocket.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestDto {
    private String email;

    public Friend toEntity(User request_from, User request_to) {
        return Friend.builder().request_from(request_from).request_to(request_to).status(1L).build();
    }
}
