package com.vp.voicepocket.domain.friend.dto.request;

import com.vp.voicepocket.domain.friend.entity.Friend;
import com.vp.voicepocket.domain.friend.entity.Status;
import com.vp.voicepocket.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestDto {
    private String email;

    public Friend toEntity(User request_from, User request_to, Status status) {
        return Friend.builder().requestFrom(request_from).requestTo(request_to).status(status).build();
    }
}
