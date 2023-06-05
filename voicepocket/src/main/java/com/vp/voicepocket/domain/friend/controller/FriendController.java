package com.vp.voicepocket.domain.friend.controller;

import com.vp.voicepocket.domain.friend.dto.request.FriendRequestDto;
import com.vp.voicepocket.domain.friend.dto.response.FriendResponseDto;
import com.vp.voicepocket.domain.friend.entity.Status;
import com.vp.voicepocket.domain.friend.service.FriendService;
import com.vp.voicepocket.global.common.response.model.CommonResult;
import com.vp.voicepocket.global.common.response.model.ListResult;
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
    @PostMapping("/friend")
    public SingleResult<FriendResponseDto> friendRequest(
            @RequestHeader("X-AUTH-TOKEN") String accessToken,
            @Parameter(description = "친구 요청 DTO", required = true)
            @RequestBody FriendRequestDto friendRequestDto) {
        FriendResponseDto friendResponseDto = friendService.requestFriend(friendRequestDto, accessToken);
        return responseService.getSingleResult(friendResponseDto);
    }

    @Parameter(
            name = "X-AUTH-TOKEN",
            description = "로그인 성공 후 AccessToken",
            required = true,
            schema = @Schema(type = "string"),
            in = ParameterIn.HEADER)
    @Operation(summary = "친구 요청 리스트 확인", description = "나에게 온 친구 요청을 확인합니다.")
    @GetMapping("/friend/requests")
    public ListResult<FriendResponseDto> checkRequest(@RequestHeader("X-AUTH-TOKEN") String accessToken) {
        return responseService.getListResult(friendService.checkRequest(accessToken));
    }

    @Parameter(
            name = "X-AUTH-TOKEN",
            description = "로그인 성공 후 AccessToken",
            required = true,
            schema = @Schema(type = "string"),
            in = ParameterIn.HEADER)
    @Operation(summary = "친구 요청 리스트 확인", description = "나에게 온 친구 요청을 확인합니다.")
    @GetMapping("/friend")
    public ListResult<FriendResponseDto> checkResponse(@RequestHeader("X-AUTH-TOKEN") String accessToken) {
        return responseService.getListResult(friendService.checkResponse(accessToken));
    }

    @Parameter(
            name = "X-AUTH-TOKEN",
            description = "로그인 성공 후 AccessToken",
            required = true,
            schema = @Schema(type = "string"),
            in = ParameterIn.HEADER)
    @Operation(summary = "친구 요청 취소", description = "친구 요청을 취소합니다.")
    @DeleteMapping("/friend")
    public CommonResult deleteRequest(
            @RequestHeader("X-AUTH-TOKEN") String accessToken,
            @Parameter(description = "친구 요청 DTO", required = true)
            @RequestBody FriendRequestDto friendRequestDto){
        friendService.delete(friendRequestDto, accessToken, Status.ONGOING);
        return responseService.getSuccessResult();
    }

    @Parameter(
            name = "X-AUTH-TOKEN",
            description = "로그인 성공 후 AccessToken",
            required = true,
            schema = @Schema(type = "string"),
            in = ParameterIn.HEADER)
    @Operation(summary = "친구 요청 관리", description = "친구 요청을 수락하거나 거절합니다.")
    @PutMapping("/friend/request/{Status}")
    public CommonResult handlingRequest(
            @RequestHeader("X-AUTH-TOKEN") String accessToken,
            @Parameter(description = "친구 요청 DTO", required = true)
            @RequestBody FriendRequestDto friendRequestDto, @PathVariable(name="Status") Status status) {
        friendService.update(friendRequestDto,accessToken, status);
        return responseService.getSuccessResult();
    }

}
