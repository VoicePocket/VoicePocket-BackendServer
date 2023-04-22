package com.vp.voicepocket.domain.message.exception;

public class CTaskNotFoundException extends RuntimeException {

    public CTaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CTaskNotFoundException(String message) {
        super(message);
    }

    public CTaskNotFoundException() {
        super();
    }
}

