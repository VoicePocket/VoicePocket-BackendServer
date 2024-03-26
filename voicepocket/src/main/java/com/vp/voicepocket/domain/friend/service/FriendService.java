package com.vp.voicepocket.domain.friend.service;

import com.vp.voicepocket.domain.friend.dto.request.FriendRequestDto;
import com.vp.voicepocket.domain.friend.dto.response.FriendResponseDto;
import com.vp.voicepocket.domain.friend.entity.Friend;
import com.vp.voicepocket.domain.friend.entity.Status;
import com.vp.voicepocket.domain.friend.event.FriendAcceptPushEvent;
import com.vp.voicepocket.domain.friend.event.FriendRequestPushEvent;
import com.vp.voicepocket.domain.friend.exception.CFriendRequestNotExistException;
import com.vp.voicepocket.domain.friend.exception.CFriendRequestOnGoingException;
import com.vp.voicepocket.domain.friend.repository.FriendRepository;
import com.vp.voicepocket.domain.token.config.JwtProvider;
import com.vp.voicepocket.domain.token.exception.CAccessTokenException;
import com.vp.voicepocket.domain.user.entity.User;
import com.vp.voicepocket.domain.user.entity.vo.Email;
import com.vp.voicepocket.domain.user.exception.CUserNotFoundException;
import com.vp.voicepocket.domain.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final ApplicationEventPublisher eventPublisher;

    // TODO: Will Remove
    private final JwtProvider jwtProvider;

    @Transactional
    public FriendResponseDto requestFriend(Email toUserEmail, String accessToken) {
        // TODO: Security Refactoring 이후 제거 - 24.03.26
        Authentication authentication = getAuthByAccessToken(accessToken);
        Long fromUserId = Long.parseLong(authentication.getName());

        User fromUser = userRepository.findById(fromUserId)
            .orElseThrow(CUserNotFoundException::new);

        User toUser = userRepository.findByEmail(toUserEmail.getValue())
            .orElseThrow(CUserNotFoundException::new);

        friendRepository.findByRequestUsers(fromUserId, toUser).ifPresent(friend -> {
            throw new CFriendRequestOnGoingException();
        });

        var friendRequest = friendRepository.save(
            Friend.builder().requestFrom(fromUser).requestTo(toUser).build());

        eventPublisher.publishEvent(new FriendRequestPushEvent(friendRequest));

        return FriendResponseDto.from(friendRequest);
    }

    private Authentication getAuthByAccessToken(String accessToken) {
        // 만료된 access token 인지 확인
        if (!jwtProvider.validationToken(accessToken)) {
            throw new CAccessTokenException();
        }

        // AccessToken 에서 Username (pk) 가져오기
        return jwtProvider.getAuthentication(accessToken);
    }

    @Transactional
    public List<FriendResponseDto> checkRequest(String accessToken) {
        Authentication authentication = getAuthByAccessToken(accessToken);

        User toUser = userRepository.findById(Long.parseLong(authentication.getName()))
            .orElseThrow(CUserNotFoundException::new);

        return friendRepository.findByToUser(toUser, Status.ONGOING)   // 없을 때 공백 리스트를 반환하기
            .stream()
            .map(FriendResponseDto::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public List<FriendResponseDto> checkResponse(String accessToken) {
        Authentication authentication = getAuthByAccessToken(accessToken);

        User fromUser = userRepository.findById(Long.parseLong(authentication.getName()))
            .orElseThrow(CUserNotFoundException::new);

        return friendRepository.findByFromUser(fromUser, Status.ACCEPT)   // 없을 때 공백 리스트를 반환하기
            .stream()
            .map(FriendResponseDto::from)
            .collect(Collectors.toList());
    }


    @Transactional
    public void update(FriendRequestDto friendRequestDto, String accessToken, Status status) {
        Authentication authentication = getAuthByAccessToken(accessToken);

        User fromUser = userRepository.findByEmail(friendRequestDto.getEmail().getValue())
            .orElseThrow(CUserNotFoundException::new);
        User toUser = userRepository.findById(Long.parseLong(authentication.getName()))
            .orElseThrow(CUserNotFoundException::new);

        Friend friendRequest = friendRepository.findByRequest(fromUser, toUser, Status.ONGOING)
            .orElseThrow(CFriendRequestNotExistException::new);
        friendRequest.updateStatus(status);

        if (status.equals(Status.ACCEPT)) {
            eventPublisher.publishEvent(new FriendAcceptPushEvent(friendRequest));
        }
    }

    @Transactional
    public void delete(FriendRequestDto friendRequestDto, String accessToken, Status status) {
        Authentication authentication = getAuthByAccessToken(accessToken);

        User fromUser = userRepository.findById(Long.parseLong(authentication.getName()))
            .orElseThrow(CUserNotFoundException::new);
        User toUser = userRepository.findByEmail(friendRequestDto.getEmail().getValue())
            .orElseThrow(CUserNotFoundException::new);

        Friend friend = friendRepository.findByRequest(fromUser, toUser, status)
            .orElseThrow(CFriendRequestNotExistException::new);

        friendRepository.delete(friend);
    }
}
