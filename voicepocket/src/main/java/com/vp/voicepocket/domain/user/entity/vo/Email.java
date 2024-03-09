package com.vp.voicepocket.domain.user.entity.vo;

import java.util.regex.Pattern;
import lombok.Getter;

@Getter
public class Email {

    // RFC 2822 Email Validation
    private static final Pattern PATTERN = Pattern.compile(
        "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
    private final String value;

    private Email(String value) {
        this.value = value;
    }

    public static Email from(String email) {
        if (!PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
        return new Email(email);
    }

}
