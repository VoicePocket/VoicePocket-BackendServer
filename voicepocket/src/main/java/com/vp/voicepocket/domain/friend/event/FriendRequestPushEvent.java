package com.vp.voicepocket.domain.friend.event;

import com.vp.voicepocket.domain.friend.entity.Friend;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendRequestPushEvent {
    private Friend friend;
}
