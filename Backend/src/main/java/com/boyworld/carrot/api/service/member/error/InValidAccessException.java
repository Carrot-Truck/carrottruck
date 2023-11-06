package com.boyworld.carrot.api.service.member.error;

public class InValidAccessException extends IllegalArgumentException {

    public InValidAccessException() {
    }

    public InValidAccessException(String s) {
        super(s);
    }

}
