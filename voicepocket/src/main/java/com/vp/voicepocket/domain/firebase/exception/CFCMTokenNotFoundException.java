package com.vp.voicepocket.domain.firebase.exception;

public class CFCMTokenNotFoundException extends RuntimeException {
    public CFCMTokenNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CFCMTokenNotFoundException(String message) {
        super(message);
    }

    public CFCMTokenNotFoundException() {
        super();
    }
}
