package com.vp.voicepocket.domain.friend.exception;

public class CFriendRequestOnGoingException extends RuntimeException{
    public CFriendRequestOnGoingException(String message, Throwable cause) {
        super(message, cause);
    }

    public CFriendRequestOnGoingException(String message) {
        super(message);
    }

    public CFriendRequestOnGoingException() {
        super();
    }
}
