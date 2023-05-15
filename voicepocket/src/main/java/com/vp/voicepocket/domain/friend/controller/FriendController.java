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
