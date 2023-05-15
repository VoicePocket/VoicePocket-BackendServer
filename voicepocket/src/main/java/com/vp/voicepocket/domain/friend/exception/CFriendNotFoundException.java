package com.vp.voicepocket.domain.friend.exception;

public class CFriendNotFoundException extends RuntimeException{
    public CFriendNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CFriendNotFoundException(String message) {
        super(message);
    }

    public CFriendNotFoundException() {
        super();
    }
}
