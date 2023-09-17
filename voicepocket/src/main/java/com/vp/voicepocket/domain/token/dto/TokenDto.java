package com.vp.voicepocket.domain.token.dto;

import com.vp.voicepocket.domain.token.entity.RefreshToken;
import com.vp.voicepocket.domain.user.entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpireDate;

    public RefreshToken toEntity(User user){
        return RefreshToken.builder()
                .key(user.getUserId())
                .token(refreshToken)
                .build();
    }
}
