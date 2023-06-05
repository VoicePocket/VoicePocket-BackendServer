package com.vp.voicepocket.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(title = "User Login Request", description = "사용자 로그인 입력 모델")
public class UserLoginRequestDto {
    @Schema(title = "email", description = "사용자 이메일", example = "sample@gmail.com")
    private String email;

    @Schema(title = "password", description = "사용자 패스워드", example = "sample!")
    private String password;
}
