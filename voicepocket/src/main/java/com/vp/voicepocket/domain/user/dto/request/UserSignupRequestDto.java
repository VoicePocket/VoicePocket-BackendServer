package com.vp.voicepocket.domain.user.dto.request;


import com.vp.voicepocket.domain.user.entity.User;
import com.vp.voicepocket.domain.user.entity.enums.UserRole;
import com.vp.voicepocket.domain.user.entity.vo.Email;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
@Schema(title = "User SignUp Request", description = "회원 가입용 입력 모델")
public class UserSignupRequestDto {

    @Schema(title = "email", description = "email 주소", example = "sample@gmail.com")
    @javax.validation.constraints.Email
    private String email;

    @Schema(title = "password", description = "비밀 번호", example = "sample!")
    @NotBlank
    private String password;

    @Schema(title = "name", description = "사용자 이름", example = "김샘플")
    @NotBlank
    private String name;

    @Schema(title = "nickname", description = "사용자 별칭", example = "버그 없는 김샘플 씨")
    @NotBlank
    private String nickName;

    public User toEntity(PasswordEncoder passwordEncoder) { // password를 encoding하여 엔티티 생성
        return User.builder()
            .email(Email.from(email))
            .password(passwordEncoder.encode(password))
            .nickname(nickName)
            .name(name)
            .role(UserRole.ROLE_USER)
            .build();
    }
}
