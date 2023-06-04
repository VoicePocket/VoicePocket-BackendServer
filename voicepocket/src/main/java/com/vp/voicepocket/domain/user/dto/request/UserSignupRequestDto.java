package com.vp.voicepocket.domain.user.dto.request;


import com.vp.voicepocket.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "User SignUp Request", description = "회원 가입용 입력 모델")
public class UserSignupRequestDto {
    @Schema(title = "email", description = "email 주소", example = "sample@gmail.com")
    private String email;
    @Schema(title = "password", description = "비밀 번호", example = "sample!")
    private String password;
    @Schema(title = "name", description = "사용자 이름", example = "김샘플")
    private String name;
    @Schema(title = "nickname", description = "사용자 별칭", example = "버그 없는 김샘플 씨")
    private String nickName;

    public User toEntity(PasswordEncoder passwordEncoder) { // password를 encoding하여 엔티티 생성
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickName(nickName)
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }
}
