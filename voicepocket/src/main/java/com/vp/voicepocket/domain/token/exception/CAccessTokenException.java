package com.vp.voicepocket.domain.token.exception;

public class CAccessTokenException extends RuntimeException {
    public CAccessTokenException() {
        super();
    }

    public CAccessTokenException(String message) {
        super(message);
    }

    public CAccessTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
