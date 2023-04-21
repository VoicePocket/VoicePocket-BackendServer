package com.vp.voicepocket.domain.message.exception;

public class CTaskNotFinishedException extends RuntimeException {

    public CTaskNotFinishedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CTaskNotFinishedException(String message) {
        super(message);
    }

    public CTaskNotFinishedException() {
        super();
    }
}
