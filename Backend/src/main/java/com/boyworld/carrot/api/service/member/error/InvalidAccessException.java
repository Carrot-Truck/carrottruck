package com.boyworld.carrot.api.service.member.error;

public class InvalidAccessException extends IllegalArgumentException {

    public InvalidAccessException() {
    }

    public InvalidAccessException(String s) {
        super(s);
    }

}
