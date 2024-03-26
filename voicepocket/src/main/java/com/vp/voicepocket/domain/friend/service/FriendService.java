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
import com.vp.voicepocket.domain.user.dto.response.UserResponseDto;
import com.vp.voicepocket.domain.user.entity.User;
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

    private final JwtProvider jwtProvider;

    @Transactional
    public FriendResponseDto requestFriend(FriendRequestDto friendRequestDto, String accessToken) {
        Authentication authentication = getAuthByAccessToken(accessToken);

        User toUser = userRepository.findByEmail(friendRequestDto.getEmail())
            .orElseThrow(CUserNotFoundException::new);

        User fromUser = userRepository.findById(Long.parseLong(authentication.getName()))
            .orElseThrow(CUserNotFoundException::new);

        if (friendRepository.findByRequest(fromUser, toUser, Status.ONGOING).isPresent() ||
            friendRepository.findByRequest(fromUser, toUser, Status.ACCEPT).isPresent()) {
            throw new CFriendRequestOnGoingException();
        }

        Friend friendRequest = friendRequestDto.toEntity(fromUser, toUser, Status.ONGOING);

        eventPublisher.publishEvent(new FriendRequestPushEvent(friendRequest));

        return mapFriendEntityToFriendResponseDTO(friendRepository.save(friendRequest));
    }

    private Authentication getAuthByAccessToken(String accessToken) {
        // 만료된 access token 인지 확인
        if (!jwtProvider.validationToken(accessToken)) {
            throw new CAccessTokenException();
        }

        // AccessToken 에서 Username (pk) 가져오기
        return jwtProvider.getAuthentication(accessToken);
    }

    private FriendResponseDto mapFriendEntityToFriendResponseDTO(Friend friend) {
        return FriendResponseDto.builder()
            .id(friend.getId())
            .request_from(new UserResponseDto(friend.getRequestFrom()))
            .request_to(new UserResponseDto(friend.getRequestTo()))
            .status(friend.getStatus())
            .build();
    }

    @Transactional
    public List<FriendResponseDto> checkRequest(String accessToken) {
        Authentication authentication = getAuthByAccessToken(accessToken);

        User toUser = userRepository.findById(Long.parseLong(authentication.getName()))
            .orElseThrow(CUserNotFoundException::new);

        return friendRepository.findByToUser(toUser, Status.ONGOING)   // 없을 때 공백 리스트를 반환하기
            .stream()
            .map(this::mapFriendEntityToFriendResponseDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public List<FriendResponseDto> checkResponse(String accessToken) {
        Authentication authentication = getAuthByAccessToken(accessToken);

        User fromUser = userRepository.findById(Long.parseLong(authentication.getName()))
            .orElseThrow(CUserNotFoundException::new);

        return friendRepository.findByFromUser(fromUser, Status.ACCEPT)   // 없을 때 공백 리스트를 반환하기
            .stream()
            .map(this::mapFriendEntityToFriendResponseDTO)
            .collect(Collectors.toList());
    }


    @Transactional
    public void update(FriendRequestDto friendRequestDto, String accessToken, Status status) {
        Authentication authentication = getAuthByAccessToken(accessToken);

        User fromUser = userRepository.findByEmail(friendRequestDto.getEmail())
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
        User toUser = userRepository.findByEmail(friendRequestDto.getEmail())
            .orElseThrow(CUserNotFoundException::new);

        Friend friend = friendRepository.findByRequest(fromUser, toUser, status)
            .orElseThrow(CFriendRequestNotExistException::new);

        friendRepository.delete(friend);
    }
}
