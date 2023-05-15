package com.vp.voicepocket.domain.friend.controller;

import com.vp.voicepocket.domain.friend.dto.request.FriendRequestDto;
import com.vp.voicepocket.domain.friend.dto.response.FriendResponseDto;
import com.vp.voicepocket.domain.friend.service.FriendService;
import com.vp.voicepocket.global.common.response.model.SingleResult;
import com.vp.voicepocket.global.common.response.service.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Friend")
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class FriendController {

    private final FriendService friendService;
    private final ResponseService responseService;
    //TODO:
    // 0. Email 기반 User 유무 파악
    // 1. Email 기반 친구 요청
    // 2. 이미 친구인지의 여부 and 요청을 보낸 상태인지 확인 필요
    // 3. 위 조건 다 맞으면 Friends Table 에 insert (from: 요청 보낸 유저, to: 요청 받을 유저, status:1L)
    @Parameter(
            name = "X-AUTH-TOKEN",
            description = "로그인 성공 후 AccessToken",
            required = true,
            schema = @Schema(type = "string"),
            in = ParameterIn.HEADER)
    @Operation(summary = "친구 요청", description = "친구 요청을 합니다.")
    @PostMapping("/friend/request")
    public SingleResult<FriendResponseDto> friendRequest(
            @RequestHeader("X-AUTH-TOKEN") String accessToken,
            @Parameter(description = "친구 요청 DTO", required = true)
            @RequestBody FriendRequestDto friendRequestDto) {
        FriendResponseDto friendResponseDto = friendService.requestFriend(friendRequestDto, accessToken);
        return responseService.getSingleResult(friendResponseDto);
    }


}
