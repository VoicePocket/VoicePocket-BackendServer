package com.vp.voicepocket.domain.friend.exception;

public class CFriendRequestNotExistException extends RuntimeException{
    public CFriendRequestNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CFriendRequestNotExistException(String message) {
        super(message);
    }

    public CFriendRequestNotExistException() {
        super();
    }
}
