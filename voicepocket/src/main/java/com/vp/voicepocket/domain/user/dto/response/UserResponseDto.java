package com.vp.voicepocket.domain.user.dto.response;


import com.vp.voicepocket.domain.user.entity.User;
import java.time.LocalDateTime;
import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public class UserResponseDto {
    private final Long userId;
    private final String email;
    private final String name;
    private final String nickName;
    private final String role;
    private final Collection<? extends GrantedAuthority> authorities;
    private final LocalDateTime modifiedDate;

    public UserResponseDto(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickName = user.getNickname();
        this.role = user.getRole().name();
        this.authorities = user.getAuthorities();
        this.modifiedDate = user.getModifiedDate();
    }
}
